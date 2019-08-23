package cn.com.platform.platform.controller.platform01;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.common.manager.AsyncManager;
import cn.com.platform.common.manager.factory.AsyncFactory;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.common.session.UserSessionInfo;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.platform.form.platform01.Platform01010201Form;
import cn.com.platform.platform.mapper.common01.PlatformMember01Mapper;
import cn.com.platform.platform.model.common.PlatformMember;
import cn.com.platform.platform.model.common.PlatformMemberQuery;
import cn.com.platform.platform.model.common.PlatformMemberQuery.Criteria;
import cn.com.platform.platform.service.platform01.Platform010102Service;
import cn.com.platform.platform.service.platform01.Platform01010201RegisterService;
import cn.com.platform.util.CaptchaUtil;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.IpUtil;
import cn.com.platform.web.BaseController;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 
 * 普通用户注册处理
 * 
 * @author 唐友成
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platform01010201")
public class Platform01010201Controller extends BaseController {

  @Autowired
  Platform010102Service platform010102Service;
  @Autowired
  PlatformMember01Mapper platformMember01Mapper;
  @Autowired
  Platform01010201RegisterService platform01010201RegisterService;

  @ModelAttribute
  public Platform01010201Form setPlatform01010201Form(@ModelAttribute Platform01010201Form inForm) {
    return inForm;
  }

  /**
   * 会员注册初始化
   * 
   * @param response
   * @param session
   * @param user
   * @throws SystemException
   */
  @RequestMapping(value = "/")
  public String index(Platform01010201Form inForm) {
    return viewThymeleaf("/platform/platform/platform01/platform01010201");
  }

  /**
   * 会员注册验证处理
   * 
   * @param inForm 表单内容
   * @param request 请求内容
   * @return
   * @throws SystemException 异常的场合
   */
  @ResponseBody
  @PostMapping("/checkRegister")
  public EasyJson<Object> generalLogin(Platform01010201Form inForm, HttpServletRequest request)
      throws SystemException {
    EasyJson<Object> easyJson = new EasyJson<Object>();
    // 验证信息
    List<Object> checkInfos = new ArrayList<Object>();

    // 用户登录ID检测是否重复
    PlatformMemberQuery platformzMemberQuery = new PlatformMemberQuery();
    Criteria criteria = platformzMemberQuery.createCriteria();
    criteria.andMemberLoginIdEqualTo(inForm.getMemberLoginId());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformMember> memberUsers = platformMember01Mapper.selectByBaseModel(platformzMemberQuery);
    if (!memberUsers.isEmpty() && memberUsers.size() > 0) {
      checkInfos.add("用户登录编号已存在");
    }
    // 密码验证
    if (!inForm.memberPassword.equals(inForm.memberPasswordConfirm)) {
      checkInfos.add("两次输入的密码不一致");
    }
    // 验证码验证
    if (!CaptchaUtil.ver(inForm.captchaText, request)) {
      checkInfos.add("验证码错误");
    }
    // 验证失败的场合，返回
    if (!ObjectUtils.isEmpty(checkInfos)) {
      easyJson.setSuccess(false);
      easyJson.setObj(checkInfos);
      return easyJson;
    }
    // 验证结束清除session中的验证码
    CaptchaUtil.clear(request);

    // 注册
    easyJson = platform01010201RegisterService.doRegister(inForm);

    // 注册成功的场合，在会话中保存允许登录的变量
    if (easyJson.getSuccess()) {

      Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
      UserSessionInfo userSessionInfo =
          new UserSessionInfo(inForm.getMemberLoginId(), "dumy", grantedAuthorities);
      String agent = request.getHeader("User-Agent");
      // 解析agent字符串
      UserAgent userAgent = UserAgent.parseUserAgentString(agent);
      // 获取浏览器对象
      Browser browser = userAgent.getBrowser();
      userSessionInfo.setBrowser(browser);

      // IP地址
      String ipAddress = IpUtil.getIpAddr(request);
      userSessionInfo.setIpAddress(ipAddress);

      // 获取操作系统对象
      OperatingSystem operatingSystem = userAgent.getOperatingSystem();
      userSessionInfo.setOperatingSystem(operatingSystem);

      // 记录用户登陆日志
      AsyncManager.me().execute(
          AsyncFactory.saveLoginInfo(userSessionInfo, FlagStatus.Enable.toString(), "登陆系统成功"));
      UserSessionUtils.putSession(CommonConstantsIF.MEMBER_LOGIN_PERMIT, true);
    }

    return easyJson;
  }
}

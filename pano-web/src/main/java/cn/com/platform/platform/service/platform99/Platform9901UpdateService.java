package cn.com.platform.platform.service.platform99;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import cn.com.platform.framework.common.session.UserSessionInfo;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.platform.form.platform99.Platform9901Form;
import cn.com.platform.platform.mapper.common01.PlatformOnlineUser01Mapper;
import cn.com.platform.platform.model.common.PlatformOnlineUser;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.SpringUtils;
import cn.com.platform.web.BaseService;

/**
 * 在线用户更新service
 * 
 * @author 唐友成
 * @date 2018-12-21
 */
@Service
public class Platform9901UpdateService extends BaseService {

  @Autowired
  PlatformOnlineUser01Mapper platformOnlineUser01Mapper;

  public EasyJson<PlatformOnlineUser> doDelete(Platform9901Form inForm) {

    EasyJson<PlatformOnlineUser> easyJson = new EasyJson<PlatformOnlineUser>();
    if (!ObjectUtils.isEmpty(inForm.onlineUserList)) {
      for (PlatformOnlineUser onlineUser : inForm.onlineUserList) {
        String sessionId = ObjectUtils.getDisplayString(onlineUser.getSessionId());
        String loginId = ObjectUtils.getDisplayString(onlineUser.getLoginId());
        if (sessionId.equals(UserSessionUtils.getSessionId())) {
          easyJson.setMsg("当前登陆用户无法强退");
          continue;
        }
        // 删除该用户数据库中的数据
        platformOnlineUser01Mapper.deleteByPrimaryKey(sessionId);

        // 清空内存中的信息
        SessionRegistry sessionRegistry = SpringUtils.getBean("sessionRegistry");
        // 获取session中所有的用户信息和用户的会话一览信息
        List<Object> users = sessionRegistry.getAllPrincipals();
        for (Object principal : users) {
          if (principal instanceof UserSessionInfo) {
            final UserSessionInfo loggedUser = (UserSessionInfo) principal;
            if (loginId.equals(loggedUser.getUsername())) {
              List<SessionInformation> sessionsInfo =
                  sessionRegistry.getAllSessions(principal, false); // false代表不包含过期session
              if (null != sessionsInfo && sessionsInfo.size() > 0) {
                for (SessionInformation sessionInformation : sessionsInfo) {
                  // 终了当前会话
                  sessionInformation.expireNow();
                }
              }
            }
          }
        }

      }
    }

    if (ObjectUtils.isEmpty(easyJson.getMsg())) {
      easyJson.setMsg("操作成功");
    }
    return easyJson;
  }
}

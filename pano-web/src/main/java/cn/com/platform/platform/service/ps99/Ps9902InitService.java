package cn.com.platform.platform.service.ps99;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Maps;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.platform.dto.ps99.Ps9902F01Dto;
import cn.com.platform.platform.form.ps99.Ps9902Form;
import cn.com.platform.platform.mapper.common01.PlatformMenu01Mapper;
import cn.com.platform.platform.model.common01.PlatformMenu01Model;
import cn.com.platform.web.BaseService;

/**
 * 会员首页初期化Service。
 * 
 * @author 唐友成
 * @since 2017年10月31日
 */
@Service
public class Ps9902InitService extends BaseService {

  @Autowired
  private PlatformMenu01Mapper platformMenu01Mapper;

  /**
   * 初期化处理。
   * 
   * @param inForm 密码变更页面form
   * @throws Exception 异常的场合
   */
  public void doInit(Ps9902Form inForm) {

    createMenuTop(inForm);

    // 基础党建
//    Ps9902F01Dto ps9901Dto = new Ps9902F01Dto();
//    ps9901Dto.menuId = "platforma";
//    ps9901Dto.menuName = "基层党建";
//    ps9901Dto.webFont = "<span class=\"icon-platform-party\" aria-hidden=\"true\"></span>";
//    inForm.menuTopList.add(ps9901Dto);
//    // 基础功能
//    ps9901Dto = new Ps9902F01Dto();
//    ps9901Dto.menuId = "platformb";
//    ps9901Dto.menuName = "基础功能";
//    ps9901Dto.webFont = "<span class=\"icon-platform-base\" aria-hidden=\"true\"></span>";
//    inForm.menuTopList.add(ps9901Dto);
//    // 居民服务
//    ps9901Dto = new Ps9902F01Dto();
//    ps9901Dto.menuId = "platformc";
//    ps9901Dto.menuName = "居民服务";
//    ps9901Dto.webFont = "<span class=\"icon-platform-resident\" aria-hidden=\"true\"></span>";
//    inForm.menuTopList.add(ps9901Dto);
//    // 社区应急
//    ps9901Dto = new Ps9902F01Dto();
//    ps9901Dto.menuId = "platformd";
//    ps9901Dto.menuName = "社区应急";
//    ps9901Dto.webFont = "<span class=\"icon-platform-emergency\" aria-hidden=\"true\"></span>";
//    inForm.menuTopList.add(ps9901Dto);
//
//    // 系统功能
//    ps9901Dto = new Ps9902F01Dto();
//    ps9901Dto.menuId = "platform";
//    ps9901Dto.menuName = "系统管理";
//    ps9901Dto.webFont = "<span class=\"icon-platform-emergency\" aria-hidden=\"true\"></span>";
//    inForm.menuTopList.add(ps9901Dto);
  }

  /**
   * 顶层菜单做成。
   * 
   * @param inForm 门户页面form
   * @throws Exception 异常的场合
   */
  private void createMenuTop(Ps9902Form inForm) {
    // 查询条件设定
    Map<String, Object> conditions = Maps.newHashMap();
    List<String> roleIds = UserSessionUtils.getUserRoleIds();
    if (roleIds.contains("tenant_manager")) {
      conditions.put("roleIds", null);
    } else {
      conditions.put("roleIds", roleIds);
    }
    conditions.put("deleteFlag", FlagStatus.Disable.toString());
    conditions.put("distinct", true);
    conditions.put("orderByClause", "MENU.DISP_FLAG ASC");
    List<PlatformMenu01Model> topMenus = platformMenu01Mapper.selectTopMenu(conditions);
    if (topMenus != null) {
      for (PlatformMenu01Model sczMenu : topMenus) {
        Ps9902F01Dto ps9902Dto = new Ps9902F01Dto();
        ps9902Dto.menuId = sczMenu.getMenuId();
        ps9902Dto.menuName = sczMenu.getMenuName();
        ps9902Dto.webFont = sczMenu.getWebFont();
        inForm.menuTopList.add(ps9902Dto);
      }
    }
  }
}

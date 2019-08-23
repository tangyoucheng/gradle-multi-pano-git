package cn.com.platform.platform.service.ps99;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Maps;
import cn.com.platform.framework.common.StandardConstantsIF;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.platform.dto.ps99.Ps9901F02Dto;
import cn.com.platform.platform.form.ps99.Ps9901Form;
import cn.com.platform.platform.mapper.common01.PlatformAdminMenu01Mapper;
import cn.com.platform.platform.model.common01.PlatformAdminMenu01Model;
import cn.com.platform.web.BaseService;

/**
 * 管理员用户菜单查询。
 * 
 * @author 唐友成
 * @date 2019-07-06
 *
 */
@Service
public class Ps9901SearchService extends BaseService {

  @Autowired
  private PlatformAdminMenu01Mapper platformAdminMenu01Mapper;

  /**
   * 登录处理。
   * 
   */
  public void doSearch(Ps9901Form inForm) {
    createMenuLeft(inForm);
    // createMenuLeftDemo(inForm);
  }

  /**
   * 左侧竖直菜单。
   * 
   * @param inForm 门户页面form
   * @throws Exception 异常的场合
   */
  private void createMenuLeft(Ps9901Form inForm) {
    Ps9901F02Dto menuTwoDto;
    Ps9901F02Dto menuThreeDto;
    Ps9901F02Dto menuFourDto;

    // 查询条件设定
    Map<String, Object> conditions = Maps.newHashMap();
    inForm.selectTopMenuId = "platform";
    conditions.put("parentMenuId", inForm.selectTopMenuId);
    List<String> roleIds = UserSessionUtils.getUserRoleIds();
    // 系统平台管理员的场合
    if (roleIds.contains(StandardConstantsIF.ROLE_SYSTEM_MANAGER)) {
      conditions.put("roleIds", null);
    } else {
      conditions.put("roleIds", roleIds);
    }
    conditions.put("distinct", true);
    conditions.put("orderByClause", "MENU.DISP_FLAG ASC");

    List<PlatformAdminMenu01Model> menuListTwo = platformAdminMenu01Mapper.selectSubMenu(conditions);
    if (menuListTwo != null) {
      for (PlatformAdminMenu01Model menuTwo : menuListTwo) {
        menuTwoDto = new Ps9901F02Dto();
        menuTwoDto.menuId = menuTwo.getMenuId();
        menuTwoDto.menuName = menuTwo.getMenuName();
        menuTwoDto.webFont = menuTwo.getWebFont();
        menuTwoDto.url = menuTwo.getMenuUrl();

        if (StringUtils.isEmpty(menuTwo.getMenuUrl())) { // 第三层菜单
          conditions.replace("parentMenuId", menuTwo.getMenuId());
          List<PlatformAdminMenu01Model> menuThreeList =
              platformAdminMenu01Mapper.selectSubMenu(conditions);
          if (menuThreeList != null) {
            for (PlatformAdminMenu01Model menuThree : menuThreeList) {
              menuThreeDto = new Ps9901F02Dto();
              menuThreeDto.menuId = menuThree.getMenuId();
              menuThreeDto.menuName = menuThree.getMenuName();
              menuThreeDto.webFont = menuThree.getWebFont();
              menuThreeDto.url = menuThree.getMenuUrl();

              if (StringUtils.isEmpty(menuThree.getMenuUrl())) { // 第四层菜单
                conditions.replace("parentMenuId", menuThree.getMenuId());
                List<PlatformAdminMenu01Model> menuFourLink =
                    platformAdminMenu01Mapper.selectSubMenu(conditions);
                if (menuFourLink != null) {
                  for (PlatformAdminMenu01Model menuFour : menuFourLink) {
                    menuFourDto = new Ps9901F02Dto();
                    menuFourDto.menuId = menuFour.getMenuId();
                    menuFourDto.menuName = menuFour.getMenuName();
                    menuFourDto.webFont = menuFour.getWebFont();
                    menuFourDto.url = menuFour.getMenuUrl();

                    menuThreeDto.menuLinkList.add(menuFourDto);
                  }
                }
              }

              menuTwoDto.menuLinkList.add(menuThreeDto);
            }
          }
        }
        inForm.menuMiddleList.add(menuTwoDto);
      }
    }
  }

  /**
   * 左侧竖直菜单。
   * 
   * @param inForm 门户页面form
   * @throws Exception 异常的场合
   */
  private void createMenuLeftDemo(Ps9901Form inForm) {
    Ps9901F02Dto ps990102Dto;

    ps990102Dto = new Ps9901F02Dto();
    ps990102Dto.menuId = "system_conf";
    ps990102Dto.menuName = "系统管理";
    ps990102Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    Ps9901F02Dto menuLinkDto = new Ps9901F02Dto();
    menuLinkDto.menuId = "admin_conf";
    menuLinkDto.menuName = "管理员列表";
    menuLinkDto.url = "admin/platform0201/";
    ps990102Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9901F02Dto();
    menuLinkDto.menuId = "role_conf";
    menuLinkDto.menuName = "角色管理";
    menuLinkDto.url = "admin/platform0202/";
    ps990102Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9901F02Dto();
    menuLinkDto.menuId = "menu_conf";
    menuLinkDto.menuName = "菜单权限管理";
    menuLinkDto.url = "admin/platform0203/";
    ps990102Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    ps990102Dto.menuLinkList.add(menuLinkDto);

    // 公司信息管理
    inForm.menuMiddleList.add(ps990102Dto);
    ps990102Dto = new Ps9901F02Dto();
    ps990102Dto.menuId = "user_conf";
    ps990102Dto.menuName = "公司信息管理";
    ps990102Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    menuLinkDto = new Ps9901F02Dto();
    menuLinkDto.menuId = "company_list";
    menuLinkDto.menuName = "公司管理";
    menuLinkDto.url = "admin/platform0399/";
    ps990102Dto.menuLinkList.add(menuLinkDto);

    inForm.menuMiddleList.add(ps990102Dto);
    ps990102Dto = new Ps9901F02Dto();
    ps990102Dto.menuId = "monitor_conf";
    ps990102Dto.menuName = "系统监测";
    ps990102Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    menuLinkDto = new Ps9901F02Dto();
    menuLinkDto.menuId = "online_user_list";
    menuLinkDto.menuName = "在线用户";
    menuLinkDto.url = "admin/platform9901/";
    ps990102Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9901F02Dto();
    menuLinkDto.menuId = "druid_monitor";
    menuLinkDto.menuName = "数据监控";
    menuLinkDto.url = "admin/platform9902/";
    ps990102Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9901F02Dto();
    menuLinkDto.menuId = "server_monitor";
    menuLinkDto.menuName = "服务监控";
    menuLinkDto.url = "admin/platform9903/";
    ps990102Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9901F02Dto();
    menuLinkDto.menuId = "cronJob";
    menuLinkDto.menuName = "定时任务";
    menuLinkDto.url = "admin/platform9905/";
    ps990102Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9901F02Dto();
    menuLinkDto.menuId = "repeatJob";
    menuLinkDto.menuName = "重复任务";
    menuLinkDto.url = "admin/platform9906/";
    ps990102Dto.menuLinkList.add(menuLinkDto);

    Ps9901F02Dto subMenu = new Ps9901F02Dto();
    subMenu.menuId = "monitor_log_conf";
    subMenu.menuName = "日志管理";
    subMenu.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    menuLinkDto = new Ps9901F02Dto();
    menuLinkDto.menuId = "monitor_log_login_conf";
    menuLinkDto.menuName = "登录日志";
    menuLinkDto.url = "admin/platform9904/";
    subMenu.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9901F02Dto();
    menuLinkDto.menuId = "job_log";
    menuLinkDto.menuName = "任务日志";
    menuLinkDto.url = "admin/platform9907/";
    subMenu.menuLinkList.add(menuLinkDto);
    ps990102Dto.menuLinkList.add(subMenu);

    inForm.menuMiddleList.add(ps990102Dto);
  }

}

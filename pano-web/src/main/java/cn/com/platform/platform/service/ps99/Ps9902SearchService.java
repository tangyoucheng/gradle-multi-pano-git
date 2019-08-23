package cn.com.platform.platform.service.ps99;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Maps;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.StandardConstantsIF;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.platform.dto.ps99.Ps9902F02Dto;
import cn.com.platform.platform.form.ps99.Ps9902Form;
import cn.com.platform.platform.mapper.common01.PlatformMenu01Mapper;
import cn.com.platform.platform.model.common01.PlatformMenu01Model;
import cn.com.platform.web.BaseService;

/**
 * 会员菜单查询。
 * 
 * @author 唐友成
 * @date 2018-08-19
 *
 */
@Service
public class Ps9902SearchService extends BaseService {

  @Autowired
  private PlatformMenu01Mapper platformMenu01Mapper;

  /**
   * 登录处理。
   * 
   */
  public void doSearch(Ps9902Form inForm) {

    createMenuLeft(inForm);
    if ("platform".equalsIgnoreCase(inForm.getSelectTopMenuId())) {
      // 共通示例
      Ps9902F02Dto ps990202Dto = new Ps9902F02Dto();
      ps990202Dto.menuId = "platform98";
      ps990202Dto.menuName = "共通示例";
      ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
      
      Ps9902F02Dto menuLinkDto = new Ps9902F02Dto();
      menuLinkDto.menuId = "platform9801";
      menuLinkDto.menuName = "一览高级查询V2.0";
      menuLinkDto.url = "member/platform9801/";
      ps990202Dto.menuLinkList.add(menuLinkDto);
      
      menuLinkDto = new Ps9902F02Dto();
      menuLinkDto.menuId = "platform9802";
      menuLinkDto.menuName = "新增复杂表 V2.0";
      menuLinkDto.url = "member/platform9802/";
      ps990202Dto.menuLinkList.add(menuLinkDto);
      
      menuLinkDto = new Ps9902F02Dto();
      menuLinkDto.menuId = "platform9803";
      menuLinkDto.menuName = "服务说明";
      menuLinkDto.url = "member/platform9803/";
      ps990202Dto.menuLinkList.add(menuLinkDto);
      

      menuLinkDto = new Ps9902F02Dto();
      menuLinkDto.menuId = "operateLog";
      menuLinkDto.menuName = "操作历史";
      menuLinkDto.url = "member/operateLog/";
      ps990202Dto.menuLinkList.add(menuLinkDto);
      
      menuLinkDto = new Ps9902F02Dto();
      menuLinkDto.menuId = "fileUpload";
      menuLinkDto.menuName = "文件上传下载删除API";
      menuLinkDto.url = "/file/index/";
      ps990202Dto.menuLinkList.add(menuLinkDto);

      inForm.menuMiddleList.add(ps990202Dto);
      
    }
    
//    if ("platforma".equalsIgnoreCase(inForm.getSelectTopMenuId())) { // 基层党建
//      getPartyMenu(inForm);
//    }
//    if ("platformb".equalsIgnoreCase(inForm.getSelectTopMenuId())) { // 基层功能
//      getBasisMenu(inForm);
//    }
//    if ("platformc".equalsIgnoreCase(inForm.getSelectTopMenuId())) { // 居民服务
//      getResidentMenu(inForm);
//    }
//    if ("platformd".equalsIgnoreCase(inForm.getSelectTopMenuId())) { // 社区应急
//      getEmergencyMenu(inForm);
//    }
//    if ("platform".equalsIgnoreCase(inForm.getSelectTopMenuId())) { // 系統功能
//      getSystemMenu(inForm);
//    }
  }

  /**
   * 左侧竖直菜单。
   * 
   * @param inForm 门户页面form
   * @throws Exception 异常的场合
   */
  private void createMenuLeft(Ps9902Form inForm) {
    Ps9902F02Dto menuTwoDto;
    Ps9902F02Dto menuThreeDto;
    Ps9902F02Dto menuFourDto;

    // 查询条件设定
    Map<String, Object> conditions = Maps.newHashMap();
    conditions.put("parentMenuId", inForm.selectTopMenuId);
    List<String> roleIds = UserSessionUtils.getUserRoleIds();
    // 系统平台管理员的场合
    if (roleIds.contains(StandardConstantsIF.ROLE_TENANT_MANAGER)) {
      conditions.put("roleIds", null);
    } else {
      conditions.put("roleIds", roleIds);
    }
    conditions.put("distinct", true);
    conditions.put("orderByClause", "MENU.DISP_FLAG ASC");

    List<PlatformMenu01Model> menuListTwo = platformMenu01Mapper.selectSubMenu(conditions);
    if (menuListTwo != null) {
      for (PlatformMenu01Model menuTwo : menuListTwo) {
        menuTwoDto = new Ps9902F02Dto();
        menuTwoDto.menuId = menuTwo.getMenuId();
        menuTwoDto.menuName = menuTwo.getMenuName();
        menuTwoDto.webFont = menuTwo.getWebFont();
        menuTwoDto.url = menuTwo.getMenuUrl();

        if (StringUtils.isEmpty(menuTwo.getMenuUrl())) { // 第三层菜单
          conditions.replace("parentMenuId", menuTwo.getMenuId());
          List<PlatformMenu01Model> menuThreeList = platformMenu01Mapper.selectSubMenu(conditions);
          if (menuThreeList != null) {
            for (PlatformMenu01Model menuThree : menuThreeList) {
              menuThreeDto = new Ps9902F02Dto();
              menuThreeDto.menuId = menuThree.getMenuId();
              menuThreeDto.menuName = menuThree.getMenuName();
              menuThreeDto.webFont = menuThree.getWebFont();
              menuThreeDto.url = menuThree.getMenuUrl();

              if (StringUtils.isEmpty(menuThree.getMenuUrl())) { // 第四层菜单
                conditions.replace("parentMenuId", menuThree.getMenuId());
                List<PlatformMenu01Model> menuFourLink = platformMenu01Mapper.selectSubMenu(conditions);
                if (menuFourLink != null) {
                  for (PlatformMenu01Model menuFour : menuFourLink) {
                    menuFourDto = new Ps9902F02Dto();
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
   * 基础党建。
   */
  public void getPartyMenu(Ps9902Form inForm) {
    Ps9902F02Dto ps990202Dto;
    Ps9902F02Dto menuLinkDto;

    // 党组织管理
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "system_conf";
    ps990202Dto.menuName = "党组织管理";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "admin_conf";
    menuLinkDto.menuName = "党建组织";
    menuLinkDto.url = "member/platform0101/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "emp";
    menuLinkDto.menuName = "党建工作";
    menuLinkDto.url = "member/emp/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "role_conf";
    menuLinkDto.menuName = "特色党建";
    menuLinkDto.url = "member/platform0102/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "menu_conf";
    menuLinkDto.menuName = "党建工作";
    menuLinkDto.url = "member/platform0103/";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    inForm.menuMiddleList.add(ps990202Dto);

    // 党员管理
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "party02";
    ps990202Dto.menuName = "党员管理";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0201";
    menuLinkDto.menuName = "党员管理";
    menuLinkDto.url = "member/platform0301/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0203";
    menuLinkDto.menuName = "发展党员";
    menuLinkDto.url = "member/platform0301/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0204";
    menuLinkDto.menuName = "教育学习";
    menuLinkDto.url = "member/platform0301/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    inForm.menuMiddleList.add(ps990202Dto);

    // 组织生活管理
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "party03";
    ps990202Dto.menuName = "组织生活管理";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0301";
    menuLinkDto.menuName = "党员活动";
    menuLinkDto.url = "member/platform0364/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0302";
    menuLinkDto.menuName = "干部管理";
    menuLinkDto.url = "member/platform0364/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0303";
    menuLinkDto.menuName = "党费管理";
    menuLinkDto.url = "member/platform0364/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0304";
    menuLinkDto.menuName = "民生生活管理";
    menuLinkDto.url = "member/platform0364/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0305";
    menuLinkDto.menuName = "组织生活会";
    menuLinkDto.url = "member/platform0364/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0306";
    menuLinkDto.menuName = "民主评议党员";
    menuLinkDto.url = "member/platform0364/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    inForm.menuMiddleList.add(ps990202Dto);

    // 党建日常工作
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "party04";
    ps990202Dto.menuName = "党建日常工作";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0401";
    menuLinkDto.menuName = "日常办公";
    menuLinkDto.url = "member/platform0401/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0402";
    menuLinkDto.menuName = "综合管理";
    menuLinkDto.url = "member/platform0402/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0403";
    menuLinkDto.menuName = "工作安排";
    menuLinkDto.url = "member/platform0402/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0404";
    menuLinkDto.menuName = "调查研究";
    menuLinkDto.url = "member/platform0402/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    inForm.menuMiddleList.add(ps990202Dto);

    // 宣传
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "party05";
    ps990202Dto.menuName = "宣传";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0501";
    menuLinkDto.menuName = "宣传阵地";
    menuLinkDto.url = "member/platform9902/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    // 宣传阵地
    // Ps9902F02Dto subMenu = new Ps9902F02Dto();
    // subMenu.menuId = "party06";
    // subMenu.menuName = "纪委";
    // subMenu.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    // menuLinkDto = new Ps9902F02Dto();
    // menuLinkDto.menuId = "party0601";
    // menuLinkDto.menuName = "登录日志";
    // menuLinkDto.url = "member/platform9904/";
    // subMenu.menuLinkList.add(menuLinkDto);
    // menuLinkDto = new Ps9902F02Dto();
    // menuLinkDto.menuId = "party0602";
    // menuLinkDto.menuName = "任务日志";
    // menuLinkDto.url = "member/platform9907/";
    // subMenu.menuLinkList.add(menuLinkDto);
    // ps990202Dto.menuLinkList.add(subMenu);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0502";
    menuLinkDto.menuName = "通知公告";
    menuLinkDto.url = "member/platform9902/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0503";
    menuLinkDto.menuName = "简报";
    menuLinkDto.url = "member/platform9903/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0504";
    menuLinkDto.menuName = "党建活动";
    menuLinkDto.url = "member/platform9905/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0505";
    menuLinkDto.menuName = "党建归纳";
    menuLinkDto.url = "member/platform9906/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0506";
    menuLinkDto.menuName = "七一表彰";
    menuLinkDto.url = "member/platform9906/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    inForm.menuMiddleList.add(ps990202Dto);

    // 纪委
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "party06";
    ps990202Dto.menuName = "纪委";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0601";
    menuLinkDto.menuName = "业务学习";
    menuLinkDto.url = "member/party0601/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0602";
    menuLinkDto.menuName = "党规党纪";
    menuLinkDto.url = "member/party0602/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0603";
    menuLinkDto.menuName = "党员监督";
    menuLinkDto.url = "member/party0603/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "party0604";
    menuLinkDto.menuName = "党风建设";
    menuLinkDto.url = "member/party0604/";
    menuLinkDto.menuName = "党员评议";
    menuLinkDto.url = "member/party0601/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    inForm.menuMiddleList.add(ps990202Dto);

  };

  /** 基础功能 */
  private void getBasisMenu(Ps9902Form inForm) {

    Ps9902F02Dto ps990202Dto;
    Ps9902F02Dto menuLinkDto;
    // 任务管理
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "basis01";
    ps990202Dto.menuName = "任务管理";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    // 子菜单内容
    // menuLinkDto = new Ps9902F02Dto();
    // menuLinkDto.menuId = "menu_conf";
    // menuLinkDto.menuName = "居民服务2";
    // menuLinkDto.url = "member/platform0103/";
    // ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    // ps990202Dto.menuLinkList.add(menuLinkDto);
    inForm.menuMiddleList.add(ps990202Dto);

    // 工作日志
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "basis02";
    ps990202Dto.menuName = "工作日志";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    // 子菜单内容

    inForm.menuMiddleList.add(ps990202Dto);

    // 社区基本情况
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "basis03";
    ps990202Dto.menuName = "社区基本情况";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";

    inForm.menuMiddleList.add(ps990202Dto);

    // 社区家庭与人口
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "basis04";
    ps990202Dto.menuName = "社区家庭与人口";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";

    inForm.menuMiddleList.add(ps990202Dto);

    // 社区社会组织
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "basis05";
    ps990202Dto.menuName = "社区家庭与人口";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";

    inForm.menuMiddleList.add(ps990202Dto);

    // 社区营造
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "basis06";
    ps990202Dto.menuName = "社区家庭与人口";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";

    inForm.menuMiddleList.add(ps990202Dto);
  }

  /** 居民服务 */
  private void getResidentMenu(Ps9902Form inForm) {

    Ps9902F02Dto ps990202Dto;
    Ps9902F02Dto menuLinkDto;

    // 基础功能
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "platformc01";
    ps990202Dto.menuName = "基础功能";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "comment_page";
    menuLinkDto.menuName = "服务指南管理";
    menuLinkDto.url = "member/platformc0101/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "comment_page_approve";
    menuLinkDto.menuName = "服务指南审核";
    menuLinkDto.url = "member/platformc0102/";
    ps990202Dto.menuLinkList.add(menuLinkDto);

    inForm.menuMiddleList.add(ps990202Dto);
    // 计划生育工作
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "resident01";
    ps990202Dto.menuName = "计划生育工作";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";

    inForm.menuMiddleList.add(ps990202Dto);
    // 民政工作
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "resident02";
    ps990202Dto.menuName = "民政工作";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "platformc0203";
    menuLinkDto.menuName = "特殊老人服务登记";
    menuLinkDto.url = "member/platformc0203/";
    ps990202Dto.menuLinkList.add(menuLinkDto);

    inForm.menuMiddleList.add(ps990202Dto);

    // 劳动保障工作
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "resident03";
    ps990202Dto.menuName = "劳动保障工作";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "platformc0301";
    menuLinkDto.menuName = "就业困难人员认定申请";
    menuLinkDto.url = "member/platformc0301/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    inForm.menuMiddleList.add(ps990202Dto);

    // 计划生育工作
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "resident04";
    ps990202Dto.menuName = "残疾人工作";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";

    inForm.menuMiddleList.add(ps990202Dto);

    // 计划生育工作
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "resident05";
    ps990202Dto.menuName = "教育工作";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";

    inForm.menuMiddleList.add(ps990202Dto);

    // 文体工作
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "resident06";
    ps990202Dto.menuName = "文体工作";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";

    inForm.menuMiddleList.add(ps990202Dto);


    // 计划生育工作
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "resident07";
    ps990202Dto.menuName = "住房保障工作";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";

    inForm.menuMiddleList.add(ps990202Dto);


    // 城市管理工作
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "resident08";
    ps990202Dto.menuName = "城市管理工作";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";

    inForm.menuMiddleList.add(ps990202Dto);

    // 计划生育工作
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "resident09";
    ps990202Dto.menuName = "卫生服务工作";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";

    inForm.menuMiddleList.add(ps990202Dto);

    // 计划生育工作
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "resident010";
    ps990202Dto.menuName = "综合治理";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";

    inForm.menuMiddleList.add(ps990202Dto);


    // 计划生育工作
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "resident011";
    ps990202Dto.menuName = "司法工作";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";

    inForm.menuMiddleList.add(ps990202Dto);

    // 计划生育工作
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "resident012";
    ps990202Dto.menuName = "信访工作";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";

    inForm.menuMiddleList.add(ps990202Dto);


    // 计划生育工作
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "resident013";
    ps990202Dto.menuName = "安全监督工作";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";

    inForm.menuMiddleList.add(ps990202Dto);


    // 计划生育工作
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "resident014";
    ps990202Dto.menuName = "流动人口工作";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";

    inForm.menuMiddleList.add(ps990202Dto);

    // 计划生育工作
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "resident015";
    ps990202Dto.menuName = "食药监工作";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";

    inForm.menuMiddleList.add(ps990202Dto);

    // 计划生育工作
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "resident016";
    ps990202Dto.menuName = "文明城市";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";

    inForm.menuMiddleList.add(ps990202Dto);

    // 残疾人工作
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "resident017";
    ps990202Dto.menuName = "残疾人工作";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "resident01701";
    menuLinkDto.menuName = "贫困精神残疾人免费申请";
    menuLinkDto.url = "member/platformc0401/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "resident01701";
    menuLinkDto.menuName = "残疾人大病医疗报销";
    menuLinkDto.url = "member/platformc0402/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "fileUpload";
    menuLinkDto.menuName = "文件上传下载删除API";
    menuLinkDto.url = "/file/index/";
    ps990202Dto.menuLinkList.add(menuLinkDto);

    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "platformc0403";
    menuLinkDto.menuName = "大病互助保险政府购买";
    menuLinkDto.url = "member/platformc0403/";
    ps990202Dto.menuLinkList.add(menuLinkDto);

    inForm.menuMiddleList.add(ps990202Dto);
  }

  /** 社区应急 */
  private void getEmergencyMenu(Ps9902Form inForm) {

    Ps9902F02Dto ps990202Dto;
    Ps9902F02Dto menuLinkDto;

    //
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "emergency01";
    ps990202Dto.menuName = "应急管理";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";

    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "platformd0202";
    menuLinkDto.menuName = "应急预案";
    menuLinkDto.url = "member/platformd0202/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    // 应急预案编制
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "yjya_list";
    menuLinkDto.menuName = "应急预案编制";
    menuLinkDto.url = "member/platformd0201/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    inForm.menuMiddleList.add(ps990202Dto);

    // 应急队伍
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "platformd01";
    ps990202Dto.menuName = "应急队伍";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    // 队伍管理制度
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "platformd0101";
    menuLinkDto.menuName = "队伍管理制度";
    menuLinkDto.url = "member/platformd0101/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    inForm.menuMiddleList.add(ps990202Dto);
  }

  /** 系統管理 */
  private void getSystemMenu(Ps9902Form inForm) {

    Ps9902F02Dto ps990202Dto;
    Ps9902F02Dto menuLinkDto;

    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "system_conf";
    ps990202Dto.menuName = "基础信息管理";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "admin_conf";
    menuLinkDto.menuName = "用户管理";
    menuLinkDto.url = "member/platform0101/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "role_conf";
    menuLinkDto.menuName = "角色管理";
    menuLinkDto.url = "member/platform0102/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "menu_conf";
    menuLinkDto.menuName = "菜单权限管理";
    menuLinkDto.url = "member/platform0103/";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    inForm.menuMiddleList.add(ps990202Dto);

    // 公司信息管理
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "user_conf";
    ps990202Dto.menuName = "公司信息管理";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "member_list";
    menuLinkDto.menuName = "公司部门用户";
    menuLinkDto.url = "member/platform0201/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "role_list";
    menuLinkDto.menuName = "公司部门管理";
    menuLinkDto.url = "member/platform0199/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    inForm.menuMiddleList.add(ps990202Dto);

    // // 社区管理
    // ps990202Dto = new Ps9902F02Dto();
    // ps990202Dto.menuId = "system02";
    // ps990202Dto.menuName = "社区管理";
    // ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    //
    // menuLinkDto = new Ps9902F02Dto();
    // menuLinkDto.menuId = "system_list";
    // menuLinkDto.menuName = "社区一栏";
    // menuLinkDto.url = "member/platform0199/";
    // ps990202Dto.menuLinkList.add(menuLinkDto);


    // 基础数据管理
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "dic_conf";
    ps990202Dto.menuName = "基础数据";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "dic_list";
    menuLinkDto.menuName = "通用字典表";
    menuLinkDto.url = "member/platform0301/";
    ps990202Dto.menuLinkList.add(menuLinkDto);

    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "dic_list";
    menuLinkDto.menuName = "字典表platform0302";
    menuLinkDto.url = "member/platform0302/";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    inForm.menuMiddleList.add(ps990202Dto);


    // 工作流程管理
    ps990202Dto = new Ps9902F02Dto();
    ps990202Dto.menuId = "workflow-management";
    ps990202Dto.menuName = "工作流程管理";
    ps990202Dto.webFont = "<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>";
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "01";
    menuLinkDto.menuName = "模型列表";
    menuLinkDto.url = "act/goActModel";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "02";
    menuLinkDto.menuName = "流程管理";
    menuLinkDto.url = "act/goAct";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "03";
    menuLinkDto.menuName = "请假流程";
    menuLinkDto.url = CommonConstantsIF.URI_BASE_MEMBER + "/" + "leave/showLeave";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "04";
    menuLinkDto.menuName = "待办任务";
    menuLinkDto.url = CommonConstantsIF.URI_BASE_MEMBER + "/" + "leave/showTask";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    menuLinkDto = new Ps9902F02Dto();
    menuLinkDto.menuId = "05";
    menuLinkDto.menuName = "已办任务";
    menuLinkDto.url = CommonConstantsIF.URI_BASE_MEMBER + "/" + "leave/showHiTask";
    ps990202Dto.menuLinkList.add(menuLinkDto);
    inForm.menuMiddleList.add(ps990202Dto);
  }
}

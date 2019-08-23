package cn.com.platform.platform.service.platform02;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Maps;
import cn.com.platform.platform.mapper.common01.PlatformAdminMenu01Mapper;
import cn.com.platform.platform.mapper.platform02.Platform0203Mapper;
import cn.com.platform.platform.model.common01.PlatformAdminMenu01Model;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.platform.dto.platform02.Platform0203F01Dto;
import cn.com.platform.platform.form.platform02.Platform0203Form;
import cn.com.platform.web.BaseService;

/**
 * 菜单查询service。
 * 
 * @author 唐友成
 * @date 2019-07-06
 *
 */
@Service
public class Platform0203SearchService extends BaseService {

  @Autowired
  PlatformAdminMenu01Mapper platformzAdminMenu01Mapper;
  @Autowired
  Platform0203Mapper platform0203Mapper;

  /**
   * 检索菜单信息。
   * 
   * @param inForm Platform0203Form
   * @return
   */
  public void doSearch(Platform0203Form inForm) {
    // 菜单取得
    if (StringUtils.isNoneEmpty(inForm.roleCode)) {
      Map<String, Object> conditions = Maps.newHashMap();
      conditions.put("deleteFlag", FlagStatus.Disable.toString());
      conditions.put("parentMenuId", "platform");
      conditions.put("roleId", inForm.roleCode);
      conditions.put("orderByClause", "MENU.DISP_FLAG ASC");
      // 一级菜单检索
      List<PlatformAdminMenu01Model> menuTopList = platform0203Mapper.selectRoleMenus(conditions);

      inForm.menuList = new ArrayList<Platform0203F01Dto>();
      if (menuTopList != null) {
        for (int i = 0; i < menuTopList.size(); i++) {
          PlatformAdminMenu01Model menuTop = menuTopList.get(i);
          Platform0203F01Dto menuTopDto = new Platform0203F01Dto();
          int numTop = i + 1;
          menuTopDto.id = Objects.toString(numTop);
          menuTopDto.pId = Objects.toString(0);
          menuTopDto.name = menuTop.getMenuName();
          menuTopDto.menuId = menuTop.getMenuId();
          menuTopDto.open = true;
          if (StringUtils.isEmpty(menuTop.roleMenuId)) {
            menuTopDto.checked = false;
          } else {
            menuTopDto.checked = true;
          }
          inForm.menuList.add(menuTopDto);

          // 二级菜单检索
          conditions.put("parentMenuId", menuTop.getMenuId());
          List<PlatformAdminMenu01Model> menuTwoList = platform0203Mapper.selectRoleMenus(conditions);
          if (menuTwoList != null) {
            for (int j = 0; j < menuTwoList.size(); j++) {
              PlatformAdminMenu01Model menuTwo = menuTwoList.get(j);
              Platform0203F01Dto menuTwoDto = new Platform0203F01Dto();
              int num = j + 1;
              menuTwoDto.id = menuTopDto.id + num;
              menuTwoDto.pId = menuTopDto.id;
              menuTwoDto.name = menuTwo.getMenuName();
              menuTwoDto.menuId = menuTwo.getMenuId();
              menuTwoDto.open = true;
              if (StringUtils.isEmpty(menuTwo.roleMenuId)) {
                menuTwoDto.checked = false;
              } else {
                menuTwoDto.checked = true;
              }
              inForm.menuList.add(menuTwoDto);

              // 三级菜单检索
              conditions.replace("parentMenuId", menuTwo.getMenuId());
              List<PlatformAdminMenu01Model> menuThreeList = platform0203Mapper.selectRoleMenus(conditions);
              if (menuThreeList != null) {
                for (int k = 0; k < menuThreeList.size(); k++) {
                  PlatformAdminMenu01Model menuThree = menuThreeList.get(k);
                  Platform0203F01Dto menuThreeDto = new Platform0203F01Dto();
                  int numThree = k + 1;
                  menuThreeDto.id = menuTwoDto.id + numThree;
                  menuThreeDto.pId = menuTwoDto.id;
                  menuThreeDto.name = menuThree.getMenuName();
                  menuThreeDto.menuId = menuThree.getMenuId();
                  menuThreeDto.open = false;
                  if (StringUtils.isEmpty(menuThree.roleMenuId)) {
                    menuThreeDto.checked = false;
                  } else {
                    menuThreeDto.checked = true;
                  }
                  inForm.menuList.add(menuThreeDto);

                  // 四级菜单检索
                  conditions.replace("parentMenuId", menuThree.getMenuId());
                  List<PlatformAdminMenu01Model> menuFourList =
                      platform0203Mapper.selectRoleMenus(conditions);
                  if (menuFourList != null) {
                    for (int m = 0; m < menuFourList.size(); m++) {
                      PlatformAdminMenu01Model menuFour = menuFourList.get(m);
                      Platform0203F01Dto menuFourDto = new Platform0203F01Dto();
                      int numFour = m + 1;
                      menuFourDto.id = menuThreeDto.id + numFour;
                      menuFourDto.pId = menuThreeDto.id;
                      menuFourDto.name = menuFour.getMenuName();
                      menuFourDto.menuId = menuFour.getMenuId();
                      menuFourDto.open = false;
                      if (StringUtils.isEmpty(menuFour.roleMenuId)) {
                        menuFourDto.checked = false;
                      } else {
                        menuFourDto.checked = true;
                      }
                      inForm.menuList.add(menuFourDto);
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

  }

}

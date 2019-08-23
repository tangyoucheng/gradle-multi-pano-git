package cn.com.platform.platformz.service.platformz01;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Maps;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.platform.mapper.common01.PlatformMenu01Mapper;
import cn.com.platform.platform.model.common01.PlatformMenu01Model;
import cn.com.platform.platformz.dto.platformz01.Platformz0103F01Dto;
import cn.com.platform.platformz.form.platformz01.Platformz0103Form;
import cn.com.platform.platformz.mapper.platformz01.Platformz0103Mapper;
import cn.com.platform.web.BaseService;

/**
 * 菜单查询service。
 * 
 * @author
 *
 */
@Service
public class Platformz0103SearchService extends BaseService {

  @Autowired
  PlatformMenu01Mapper platformzMenu01Mapper;
  @Autowired
  Platformz0103Mapper platformz0103Mapper;

  /**
   * 检索菜单信息。
   * 
   * @param inForm Platformz0103Form
   */
  public void doSearch(Platformz0103Form inForm) {
    // 菜单取得
    if (StringUtils.isNoneEmpty(inForm.roleCode)) {
      Map<String, Object> conditions = Maps.newHashMap();
      conditions.put("deleteFlag", FlagStatus.Disable.toString());
      conditions.put("parentMenuId", null);
      conditions.put("roleId", inForm.roleCode);
      conditions.put("orderByClause", "MENU.DISP_FLAG ASC");
      // 一级菜单检索
      List<PlatformMenu01Model> menuTopList = platformz0103Mapper.selectRoleMenus(conditions);

      inForm.menuList = new ArrayList<Platformz0103F01Dto>();
      if (menuTopList != null) {
        for (int i = 0; i < menuTopList.size(); i++) {
          PlatformMenu01Model menuTop = menuTopList.get(i);
          Platformz0103F01Dto menuTopDto = new Platformz0103F01Dto();
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
          List<PlatformMenu01Model> menuTwoList = platformz0103Mapper.selectRoleMenus(conditions);
          if (menuTwoList != null) {
            for (int j = 0; j < menuTwoList.size(); j++) {
              PlatformMenu01Model menuTwo = menuTwoList.get(j);
              Platformz0103F01Dto menuTwoDto = new Platformz0103F01Dto();
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
              List<PlatformMenu01Model> menuThreeList = platformz0103Mapper.selectRoleMenus(conditions);
              if (menuThreeList != null) {
                for (int k = 0; k < menuThreeList.size(); k++) {
                  PlatformMenu01Model menuThree = menuThreeList.get(k);
                  Platformz0103F01Dto menuThreeDto = new Platformz0103F01Dto();
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
                  List<PlatformMenu01Model> menuFourList = platformz0103Mapper.selectRoleMenus(conditions);
                  if (menuFourList != null) {
                    for (int m = 0; m < menuFourList.size(); m++) {
                      PlatformMenu01Model menuFour = menuFourList.get(m);
                      Platformz0103F01Dto menuFourDto = new Platformz0103F01Dto();
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

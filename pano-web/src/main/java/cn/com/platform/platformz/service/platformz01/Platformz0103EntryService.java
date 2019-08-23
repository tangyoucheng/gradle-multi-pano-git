package cn.com.platform.platformz.service.platformz01;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.framework.util.FwJsonUtil;
import cn.com.platform.platform.dto.platform02.Platform0203F01Dto;
import cn.com.platform.platform.mapper.common01.PlatformRoleMenu01Mapper;
import cn.com.platform.platform.model.common.PlatformRoleMenu;
import cn.com.platform.platform.model.common.PlatformRoleMenuQuery;
import cn.com.platform.platformz.form.platformz01.Platformz0103Form;
import cn.com.platform.web.BaseService;

/**
 * 菜单权限管理保存Service。
 * 
 * @since 2018年06月21日
 */
@Service
public class Platformz0103EntryService extends BaseService {

  @Autowired
  public PlatformRoleMenu01Mapper platformzRoleMenu01Mapper;

  /**
   * 更新处理。
   * 
   * @param inForm 菜单权限form
   * @throws Exception 异常的场合
   */
  public void doEntry(Platformz0103Form inForm) throws Exception {

    if (StringUtils.isEmpty(inForm.roleCode)) {
      return;
    }
    // 旧权限删除
    PlatformRoleMenuQuery conditions = new PlatformRoleMenuQuery();
    conditions.createCriteria().andRoleIdEqualTo(inForm.roleCode);
    platformzRoleMenu01Mapper.deleteByBaseModel(conditions);
    // 新权限保存
    if (StringUtils.isNotEmpty(inForm.menuListJson)) {
      List<Platform0203F01Dto> dataListFromJosn =
          FwJsonUtil.jsonToList(inForm.menuListJson, Platform0203F01Dto.class);
      if (dataListFromJosn != null) {
        for (Platform0203F01Dto cis0203F01Dto : dataListFromJosn) {
          String menuId = cis0203F01Dto.menuId;
          if (menuId != null) {
            PlatformRoleMenu sczMenuRole = new PlatformRoleMenu();
            sczMenuRole.setRoleId(inForm.roleCode);
            sczMenuRole.setMenuId(menuId);
            createAudit(sczMenuRole);
            platformzRoleMenu01Mapper.insert(sczMenuRole);
          }
        }
      }
    }
  }
}

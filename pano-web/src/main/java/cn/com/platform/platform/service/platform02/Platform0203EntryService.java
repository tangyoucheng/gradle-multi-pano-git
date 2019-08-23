package cn.com.platform.platform.service.platform02;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.com.platform.platform.model.common.PlatformAdminRoleMenu;
import cn.com.platform.platform.model.common.PlatformAdminRoleMenuQuery;
import cn.com.platform.framework.util.FwJsonUtil;
import cn.com.platform.platform.dto.platform02.Platform0203F01Dto;
import cn.com.platform.platform.form.platform02.Platform0203Form;
import cn.com.platform.platform.mapper.common01.PlatformAdminRoleMenu01Mapper;
import cn.com.platform.web.BaseService;

/**
 * 菜单权限管理保存Service。
 * 
 * @since 2018年06月21日
 */
@Service
public class Platform0203EntryService extends BaseService {

  @Autowired
  public PlatformAdminRoleMenu01Mapper platformzAdminRoleMenu01Mapper;

  /**
   * 更新处理。
   * 
   * @param inForm 菜单权限form
   * @throws Exception 异常的场合
   */
  public void doEntry(Platform0203Form inForm) throws Exception {

    if (StringUtils.isEmpty(inForm.roleCode)) {
      return;
    }
    // 旧权限删除
    PlatformAdminRoleMenuQuery conditions = new PlatformAdminRoleMenuQuery();
    conditions.createCriteria().andRoleIdEqualTo(inForm.roleCode);
    platformzAdminRoleMenu01Mapper.deleteByBaseModel(conditions);
    // 新权限保存
    if (StringUtils.isNotEmpty(inForm.menuListJson)) {
      List<Platform0203F01Dto> dataListFromJosn =
          FwJsonUtil.jsonToList(inForm.menuListJson, Platform0203F01Dto.class);
      if (dataListFromJosn != null) {
        for (Platform0203F01Dto platform0203F01Dto : dataListFromJosn) {
          String menuId = platform0203F01Dto.menuId;
          if (menuId != null) {
            PlatformAdminRoleMenu sczMenuRole = new PlatformAdminRoleMenu();
            sczMenuRole.setRoleId(inForm.roleCode);
            sczMenuRole.setMenuId(menuId);
            createAudit(sczMenuRole);
            platformzAdminRoleMenu01Mapper.insert(sczMenuRole);
          }
        }
      }
    }
  }
}

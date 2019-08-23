package cn.com.platform.platform.service.platform02;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.platform.model.common.PlatformAdminRole;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.StandardConstantsIF;
import cn.com.platform.platform.form.platform02.Platform020203Form;
import cn.com.platform.platform.mapper.common01.PlatformAdminRole01Mapper;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 角色查询service。
 * 
 * @author
 *
 */
@Service
public class Platform020203SearchService extends BaseService {

  @Autowired
  private PlatformAdminRole01Mapper platformzAdminRole01Mapper;

  /**
   * 检索角色。
   * 
   * @param inForm Platform020203Form
   * @return
   */
  public EasyJson<PlatformAdminRole> doSearch(Platform020203Form inForm) {

    // SQL文的参数
    HashMap<String, Object> parameter = new HashMap<String, Object>();
    // 排除系统平台角色
    parameter.put("roleId", StandardConstantsIF.ROLE_SYSTEM_MANAGER);
    // 删除标识
    parameter.put("deleteFlag", FlagStatus.Disable.toString());
    // 排序
    parameter.put("orderByClause", getOrderInfo(inForm));
    // 数据索引
    parameter.put("offSet", (inForm.pageNumber - 1) * inForm.pageSize);
    // 每页的条数
    parameter.put("pageSize", inForm.pageSize);

    // 总件数
    inForm.recordCount = platformzAdminRole01Mapper.selectRolesCount(parameter);
    if (inForm.recordCount > 0) {
      // 每页数据
      inForm.roleList = platformzAdminRole01Mapper.selectRolesInfo(parameter);
    }

    EasyJson<PlatformAdminRole> easyJson = new EasyJson<PlatformAdminRole>();
    easyJson.setRows(inForm.roleList);
    easyJson.setTotal(inForm.recordCount);
    return easyJson;
  }

  /**
   * 取得排序信息。
   * 
   * @param inForm 银行网点form
   * @return 排序信息
   */
  private String getOrderInfo(Platform020203Form inForm) {
    Map<Object, Object> orderkeyInfo = new HashMap<>();
    orderkeyInfo.put("roleName", "CONVERT( ROLE_NAME USING gbk ) COLLATE gbk_chinese_ci");
    if (orderkeyInfo.get(inForm.sortName) == null) {
      return null;
    }
    return orderkeyInfo.get(inForm.sortName) + " " + inForm.sortOrder;
  }
}

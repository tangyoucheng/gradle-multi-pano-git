package cn.com.platform.platformz.service.platformz01;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.platform.mapper.common01.PlatformRole01Mapper;
import cn.com.platform.platform.model.common.PlatformRole;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.StandardConstantsIF;
import cn.com.platform.platformz.form.platformz01.Platformz010203Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 角色查询service。
 * 
 * @author
 *
 */
@Service
public class Platformz010203SearchService extends BaseService {

  @Autowired
  private PlatformRole01Mapper platformzMemberRole01Mapper;

  /**
   * 检索处理。
   * 
   * @param inForm Platformz0102Form
   * @return
   */
  public EasyJson<PlatformRole> doSearch(Platformz010203Form inForm) {

    // SQL文的参数
    HashMap<String, Object> parameter = new HashMap<String, Object>();
    // 排除租户管理员角色
    parameter.put("roleId", StandardConstantsIF.ROLE_TENANT_MANAGER);
    // 删除标识
    parameter.put("deleteFlag", FlagStatus.Disable.toString());
    // 排序
    parameter.put("orderByClause", getOrderInfo(inForm));
    // 数据索引
    parameter.put("offSet", (inForm.pageNumber - 1) * inForm.pageSize);
    // 每页的条数
    parameter.put("pageSize", inForm.pageSize);

    // 总件数
    inForm.recordCount = platformzMemberRole01Mapper.selectRolesCount(parameter);
    if (inForm.recordCount > 0) {
      // 每页数据
      inForm.roleList = platformzMemberRole01Mapper.selectRolesInfo(parameter);
    }

    EasyJson<PlatformRole> easyJson = new EasyJson<PlatformRole>();
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
  private String getOrderInfo(Platformz010203Form inForm) {
    Map<Object, Object> orderkeyInfo = new HashMap<>();
    orderkeyInfo.put("roleName", "CONVERT( ROLE_NAME USING gbk ) COLLATE gbk_chinese_ci");
    if (orderkeyInfo.get(inForm.sortName) == null) {
      return null;
    }
    return orderkeyInfo.get(inForm.sortName) + " " + inForm.sortOrder;
  }
}
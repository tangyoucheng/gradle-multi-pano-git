package cn.com.platform.platformz.service.platformz02;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.platform.mapper.common01.PlatformRole01Mapper;
import cn.com.platform.platform.model.common.PlatformRole;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.platformz.form.platformz02.Platformz020203Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 角色查询service
 * 
 * @author
 *
 */
@Service
public class Platformz020203SearchService extends BaseService {

  @Autowired
  private PlatformRole01Mapper platformzRole01Mapper;

  public EasyJson<PlatformRole> doSearch(Platformz020203Form inForm) {

    // SQL文的参数
    HashMap<String, Object> parameter = new HashMap<String, Object>();
    // 删除标识
    parameter.put("deleteFlag", FlagStatus.Disable.toString());
    // 排序
    parameter.put("orderByClause", getOrderInfo(inForm));
    // 数据索引
    parameter.put("offSet", (inForm.pageNumber - 1) * inForm.pageSize);
    // 每页的条数
    parameter.put("pageSize", inForm.pageSize);

    // 总件数
    inForm.recordCount = platformzRole01Mapper.selectRolesCount(parameter);
    if (inForm.recordCount > 0) {
      // 每页数据
      inForm.roleList = platformzRole01Mapper.selectRolesInfo(parameter);
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
  private String getOrderInfo(Platformz020203Form inForm) {
    Map<Object, Object> orderkeyInfo = new HashMap<>();
    orderkeyInfo.put("roleName", "CONVERT( ROLE_NAME USING gbk ) COLLATE gbk_chinese_ci");
    if (orderkeyInfo.get(inForm.sortName) == null) {
      return null;
    }
    return orderkeyInfo.get(inForm.sortName) + " " + inForm.sortOrder;
  }
}

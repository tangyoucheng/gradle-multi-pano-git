package cn.com.platform.platformz.service.platformz02;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.platform.mapper.common01.PlatformCompany01Mapper;
import cn.com.platform.platform.model.common.PlatformCompany;
import cn.com.platform.platformz.form.platformz02.Platformz0299Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 公司管理查询service 。
 * 
 * @author 代仁宗
 * @date 2019-06-17
 */
@Service
public class Platformz0299SearchService extends BaseService {
  // 自定义SQL
  @Autowired
  PlatformCompany01Mapper platformCompany01Mapper;

  /**
   * 查询。
   * 
   * @param inForm form
   * @return 提示信息
   */
  public EasyJson<PlatformCompany> doSearch(Platformz0299Form inForm) {

    // SQL文的参数
    HashMap<String, Object> parameter = new HashMap<String, Object>();
    // 公司名
    parameter.put("companyName", inForm.getCompanyName());
    // 删除标识
    parameter.put("deleteFlag", FlagStatus.Disable.toString());
    // 排序
    parameter.put("orderByClause", getOrderInfo(inForm));
    // 数据索引
    parameter.put("offSet", (inForm.pageNumber - 1) * inForm.pageSize);
    // 每页的条数
    parameter.put("pageSize", inForm.pageSize);

    // 总件数 selectCompanyCount为xml的ID名
    inForm.recordCount = platformCompany01Mapper.selectCompanyCount(parameter);
    if (inForm.recordCount > 0) {
      // 每页数据
      inForm.companyList = platformCompany01Mapper.selectCompanyInfo(parameter);
    }

    EasyJson<PlatformCompany> easyJson = new EasyJson<PlatformCompany>();
    easyJson.setRows(inForm.companyList);
    easyJson.setTotal(inForm.recordCount);
    return easyJson;
  }

  /**
   * 取得排序信息。
   * 
   * @param inForm form
   * @return 排序信息
   */
  private String getOrderInfo(Platformz0299Form inForm) {
    Map<Object, Object> orderkeyInfo = new HashMap<>();
    orderkeyInfo.put("companyId", "company_id");
    // 中文排序
    orderkeyInfo.put("companyName", "CONVERT( company_name USING gbk ) COLLATE gbk_chinese_ci");
    if (orderkeyInfo.get(inForm.sortName) == null) {
      return null;
    }
    return orderkeyInfo.get(inForm.sortName) + " " + inForm.sortOrder;
  }
}

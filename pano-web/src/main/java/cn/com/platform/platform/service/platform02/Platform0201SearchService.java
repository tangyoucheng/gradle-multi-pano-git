package cn.com.platform.platform.service.platform02;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.platform.model.common.PlatformAdminUser;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.platform.form.platform02.Platform0201Form;
import cn.com.platform.platform.mapper.common01.PlatformAdminUser01Mapper;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 管理员管理查询service
 * 
 * @author 唐友成
 * @date 2018-08-10
 */
@Service
public class Platform0201SearchService extends BaseService {

  @Autowired
  PlatformAdminUser01Mapper platformzAdmin01Mapper;

  public EasyJson<PlatformAdminUser> doSearch(Platform0201Form inForm) {

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
    inForm.recordCount = platformzAdmin01Mapper.selectUsersCount(parameter);
    if (inForm.recordCount > 0) {
      // 每页数据
      inForm.adminList = platformzAdmin01Mapper.selectUsersInfo(parameter);
    }

    EasyJson<PlatformAdminUser> easyJson = new EasyJson<PlatformAdminUser>();
    easyJson.setRows(inForm.adminList);
    easyJson.setTotal(inForm.recordCount);
    return easyJson;
  }

  /**
   * 取得排序信息。
   * 
   * @param inForm 银行网点form
   * @return 排序信息
   */
  private String getOrderInfo(Platform0201Form inForm) {
    Map<Object, Object> orderkeyInfo = new HashMap<>();
    orderkeyInfo.put("adminLoginId", "admin_login_id");
    orderkeyInfo.put("adminName", "CONVERT( admin_name USING gbk ) COLLATE gbk_chinese_ci");
    orderkeyInfo.put("adminEmail", "admin_email");
    if (orderkeyInfo.get(inForm.sortName) == null) {
      return null;
    }
    return orderkeyInfo.get(inForm.sortName) + " " + inForm.sortOrder;
  }
}

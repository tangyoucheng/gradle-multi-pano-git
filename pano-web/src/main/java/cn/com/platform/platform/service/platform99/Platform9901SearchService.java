package cn.com.platform.platform.service.platform99;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.platform.model.common.PlatformOnlineUser;
import cn.com.platform.platform.form.platform99.Platform9901Form;
import cn.com.platform.platform.mapper.common01.PlatformOnlineUser01Mapper;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 在线用户查询service
 * 
 * @author 唐友成
 * @date 2018-12-21
 */
@Service
public class Platform9901SearchService extends BaseService {

  @Autowired
  PlatformOnlineUser01Mapper platformOnlineUser01Mapper;

  public EasyJson<PlatformOnlineUser> doSearch(Platform9901Form inForm) {

    // SQL文的参数
    HashMap<String, Object> parameter = new HashMap<String, Object>();
    // 登陆用户ID
    parameter.put("loginId", inForm.getLoginId());
    // IP地址
    parameter.put("ipAddress", inForm.getIpAddress());
    // 排序
    parameter.put("orderByClause", getOrderInfo(inForm));
    // 数据索引
    parameter.put("offSet", (inForm.pageNumber - 1) * inForm.pageSize);
    // 每页的条数
    parameter.put("pageSize", inForm.pageSize);

    // 总件数
    inForm.recordCount = platformOnlineUser01Mapper.selectUsersCount(parameter);
    if (inForm.recordCount > 0) {
      // 每页数据
      inForm.onlineUserList = platformOnlineUser01Mapper.selectUsersInfo(parameter);
    }

    EasyJson<PlatformOnlineUser> easyJson = new EasyJson<PlatformOnlineUser>();
    easyJson.setRows(inForm.onlineUserList);
    easyJson.setTotal(inForm.recordCount);
    return easyJson;
  }

  /**
   * 取得排序信息。
   * 
   * @param inForm 银行网点form
   * @return 排序信息
   */
  private String getOrderInfo(Platform9901Form inForm) {
    Map<Object, Object> orderkeyInfo = new HashMap<>();
    orderkeyInfo.put("loginId", "login_id");
    orderkeyInfo.put("lastAccessDate", "last_access_date");
    if (orderkeyInfo.get(inForm.sortName) == null) {
      return null;
    }
    return orderkeyInfo.get(inForm.sortName) + " " + inForm.sortOrder;
  }
}

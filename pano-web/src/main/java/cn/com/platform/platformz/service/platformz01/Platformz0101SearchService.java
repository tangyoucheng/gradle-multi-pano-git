package cn.com.platform.platformz.service.platformz01;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.platform.mapper.common01.PlatformMember01Mapper;
import cn.com.platform.platform.model.common.PlatformMember;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.platformz.form.platformz01.Platformz0101Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 用户管理查询service
 * 
 * @author 唐友成
 * @date 2018-08-10
 */
@Service
public class Platformz0101SearchService extends BaseService {

  @Autowired
  PlatformMember01Mapper platformzMember01Mapper;

  public EasyJson<PlatformMember> doSearch(Platformz0101Form inForm) {

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
    inForm.recordCount = platformzMember01Mapper.selectMembersCount(parameter);
    if (inForm.recordCount > 0) {
      // 每页数据
      inForm.memberList = platformzMember01Mapper.selectMembersInfo(parameter);
    }

    EasyJson<PlatformMember> easyJson = new EasyJson<PlatformMember>();
    easyJson.setRows(inForm.memberList);
    easyJson.setTotal(inForm.recordCount);
    return easyJson;
  }

  /**
   * 取得排序信息。
   * 
   * @param inForm 银行网点form
   * @return 排序信息
   */
  private String getOrderInfo(Platformz0101Form inForm) {
    Map<Object, Object> orderkeyInfo = new HashMap<>();
    orderkeyInfo.put("memberLoginId", "member_login_id");
    orderkeyInfo.put("memberName", "CONVERT( member_name USING gbk ) COLLATE gbk_chinese_ci");
    orderkeyInfo.put("memberEmail", "member_email");
    if (orderkeyInfo.get(inForm.sortName) == null) {
      return null;
    }
    return orderkeyInfo.get(inForm.sortName) + " " + inForm.sortOrder;
  }
}

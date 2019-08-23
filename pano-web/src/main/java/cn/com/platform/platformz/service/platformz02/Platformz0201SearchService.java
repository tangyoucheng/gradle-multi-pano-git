package cn.com.platform.platformz.service.platformz02;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.platform.mapper.common01.PlatformMember01Mapper;
import cn.com.platform.platform.model.common.PlatformMember;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.platformz.form.platformz02.Platformz0201Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 公司用户查询service
 * 
 * @author 代仁宗
 * @date 2019-06-18
 */
@Service
public class Platformz0201SearchService extends BaseService {

  
  @Autowired
  PlatformMember01Mapper platformzMember01Mapper;

  public EasyJson<PlatformMember> doSearch(Platformz0201Form inForm) {

    // SQL文的参数
    HashMap<String, Object> parameter = new HashMap<String, Object>();
    // 公司用户登录ID角色
    parameter.put("memberLoginId", inForm.getMemberLoginId());
    // 公司用户姓名
    parameter.put("memberName", inForm.getMemberName());
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
      inForm.memberRoleList = platformzMember01Mapper.selectMembersInfo(parameter);
    }

    EasyJson<PlatformMember> easyJson = new EasyJson<PlatformMember>();
    easyJson.setRows(inForm.memberRoleList);
    easyJson.setTotal(inForm.recordCount);
    return easyJson;
  }

  /**
   * 取得排序信息。
   * 
   * @param inForm 银行网点form
   * @return 排序信息
   */
  private String getOrderInfo(Platformz0201Form inForm) {
    Map<Object, Object> orderkeyInfo = new HashMap<>();
    orderkeyInfo.put("memberLoginId", "member_login_id");
    //中文排序需要转码
    orderkeyInfo.put("memberName", "CONVERT( member_name USING gbk ) COLLATE gbk_chinese_ci");
    orderkeyInfo.put("memberPhone", "member_phone");
    orderkeyInfo.put("memberEmail", "member_email");
    if (orderkeyInfo.get(inForm.sortName) == null) {
      return null;
    }
    return orderkeyInfo.get(inForm.sortName) + " " + inForm.sortOrder;
  }
}

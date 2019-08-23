package cn.com.platform.common.operatelog.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import cn.com.platform.common.operatelog.form.OperateLogForm;
import cn.com.platform.framework.code.BusinessType;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.util.MessageUtils;
import cn.com.platform.web.BaseService;

/**
 * 操作日志共通 初始化service 。
 * 
 * @author 代仁宗
 * @date 2019-07-09
 *
 */

@Service
public class OperateLogInitService extends BaseService {
  /**
   * 初期化。
   * 
   * @param inForm form
   */
  public void doInit(OperateLogForm inForm) {
    // 类别
    List<CodeValueRecord> typeList = new ArrayList<CodeValueRecord>();
    typeList.add(new CodeValueRecord(BusinessType.OTHER.toString(),
        MessageUtils.getMessage(BusinessType.OTHER.getMessageId())));
    typeList.add(new CodeValueRecord(BusinessType.INSERT.toString(),
        MessageUtils.getMessage(BusinessType.INSERT.getMessageId())));
    typeList.add(new CodeValueRecord(BusinessType.UPDATE.toString(),
        MessageUtils.getMessage(BusinessType.UPDATE.getMessageId())));
    typeList.add(new CodeValueRecord(BusinessType.DELETE.toString(),
        MessageUtils.getMessage(BusinessType.DELETE.getMessageId())));
    inForm.businessTypeList = typeList;
    inForm.setParameterModuleId(inForm.getModuleId());
    inForm.setParameterRecordId(inForm.getRecordId());
  }
}

package cn.com.platform.common.operatelog.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.common.operatelog.form.OperateLogViewForm;
import cn.com.platform.framework.code.BusinessType;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.common.StandardConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.util.FwCodeUtil;
import cn.com.platform.platform.mapper.common01.PlatformOperateLog01Mapper;
import cn.com.platform.platform.model.common.PlatformOperateLogQuery;
import cn.com.platform.platform.model.common.PlatformOperateLogQuery.Criteria;
import cn.com.platform.platform.model.common.PlatformOperateLog;
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
public class OperateLogViewInitService extends BaseService {

  @Autowired
  PlatformOperateLog01Mapper platformOperateLog01Mapper;

  /**
   * 初期化。
   * 
   * @param inForm form
   */
  public void doInit(OperateLogViewForm inForm) throws SystemException {
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

    PlatformOperateLogQuery ciszOperateLogQuery = new PlatformOperateLogQuery();
    Criteria criteria = ciszOperateLogQuery.createCriteria();
    criteria.andOperateIdEqualTo(inForm.getOperateId());
    // 检索数据库数据
    List<PlatformOperateLog> ciszOperateLogs =
        platformOperateLog01Mapper.selectByBaseModel(ciszOperateLogQuery);

    if (!ciszOperateLogs.isEmpty() && ciszOperateLogs.size() == 1) {
      PlatformOperateLog ciszOperateLog = ciszOperateLogs.get(0);
      inForm.setModuleId(ciszOperateLog.getModuleId());
      inForm.setRecordId(ciszOperateLog.getRecordId());;
      inForm.setOperateId(ciszOperateLog.getOperateId());
      // 操作时间
      if (ciszOperateLog.getOperateTime() != null) {
        String operateTime = ciszOperateLog.getOperateTime()
            .format(DateTimeFormatter.ofPattern(StandardConstantsIF.DATE_FORMART_YMDHMS_DASH));
        inForm.setOperateTime(operateTime);
      }
      inForm.setOperateName(ciszOperateLog.getOperateName());;
      inForm.setOperateParam(ciszOperateLog.getOperateParam());
      // inForm.setBusinessType(ciszOperateLog.getBusinessType().toString());
      // 查询出枚举 对应相应 文字
      BusinessType businessType =
          FwCodeUtil.stringToEnum(BusinessType.class, ciszOperateLog.getBusinessType().toString());
      inForm.setBusinessType(MessageUtils.getMessage(businessType.getMessageId()));

      inForm.setOperateParam(ciszOperateLog.getOperateParam());
    }
  }
}

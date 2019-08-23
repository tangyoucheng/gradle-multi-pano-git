package cn.com.platform.common.operatelog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.common.operatelog.form.OperateLogForm;
import cn.com.platform.platform.mapper.common01.PlatformOperateLog01Mapper;
import cn.com.platform.platform.model.common01.PlatformOperateLog01Model;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 操作日志共通 初始化service 。
 * 
 * @author 代仁宗
 * @date 2019-07-10
 *
 */

@Service
public class OperateLogParameteService extends BaseService {

  @Autowired
  PlatformOperateLog01Mapper platformOperateLog01Mapper;

  /**
   * 查询一览数据 。
   * 
   * @param inForm 。
   * @return 一览信息
   */
  public EasyJson<PlatformOperateLog01Model> doParameter(OperateLogForm inForm) {
    inForm.setParameterModuleId(inForm.getModuleId());
    EasyJson<PlatformOperateLog01Model> easyJson = new EasyJson<PlatformOperateLog01Model>();
    return easyJson;
  }
}

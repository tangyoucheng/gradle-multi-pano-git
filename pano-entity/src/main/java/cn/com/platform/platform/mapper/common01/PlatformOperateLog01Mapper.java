package cn.com.platform.platform.mapper.common01;

import java.util.HashMap;
import java.util.List;
import cn.com.platform.platform.mapper.common.PlatformOperateLogMapper;
import cn.com.platform.platform.model.common.PlatformOperateLog;
import cn.com.platform.platform.model.common.PlatformOperateLogQuery;
import cn.com.platform.platform.model.common01.PlatformOperateLog01Model;

public interface PlatformOperateLog01Mapper extends PlatformOperateLogMapper {

  /**
   * 通过基础model删除数据 。
   *
   * @mbg.generated
   */
  int deleteByBaseModel(PlatformOperateLogQuery example);

  /**
   * 通过基础model检索数据。
   *
   * @mbg.generated
   */
  List<PlatformOperateLog> selectByBaseModel(PlatformOperateLogQuery example);

  /**
   * 检索未被删除的所有数据 。
   * 
   * @param parameter parameter
   * 
   */
  long selectOperateLogCount(HashMap<?, ?> parameter);

  /**
   * 按查询条件检索未被删除的所有数据。
   * 
   * @param parameter parameter
   * @return
   */
  List<PlatformOperateLog01Model> selectOperateLogInfo(HashMap<?, ?> parameter);

  /**
   * 通过ID 查询数据
   * @return 
   *
   * @mbg.generated
   */
  PlatformOperateLog selectById(String id);
}

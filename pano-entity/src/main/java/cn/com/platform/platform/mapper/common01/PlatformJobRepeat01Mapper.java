package cn.com.platform.platform.mapper.common01;

import java.util.HashMap;
import java.util.List;
import cn.com.platform.platform.mapper.common.PlatformJobRepeatMapper;
import cn.com.platform.platform.model.common01.PlatformJobRepeat01Model;

public interface PlatformJobRepeat01Mapper extends PlatformJobRepeatMapper {

  /**
   * 检索重复任务的数量
   * 
   * @param parameter
   * @return
   */
  long selectJobsCount(HashMap<?, ?> parameter);


  /**
   * 检索重复任务
   * 
   * @param parameter
   * @return
   */
  List<PlatformJobRepeat01Model> selectJobsInfo(HashMap<?, ?> parameter);

  /**
   * 查询所有调度任务
   * 
   * @return 调度任务列表
   */
  public List<PlatformJobRepeat01Model> selectJobAll();
}

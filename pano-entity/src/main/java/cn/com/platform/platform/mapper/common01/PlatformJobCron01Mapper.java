package cn.com.platform.platform.mapper.common01;

import java.util.HashMap;
import java.util.List;
import cn.com.platform.platform.mapper.common.PlatformJobCronMapper;
import cn.com.platform.platform.model.common01.PlatformJobCron01Model;

public interface PlatformJobCron01Mapper extends PlatformJobCronMapper {

  /**
   * 检索定时任务的数量
   * 
   * @param parameter
   * @return
   */
  long selectJobsCount(HashMap<?, ?> parameter);


  /**
   * 检索定时任务
   * 
   * @param parameter
   * @return
   */
  List<PlatformJobCron01Model> selectJobsInfo(HashMap<?, ?> parameter);

  /**
   * 查询所有调度任务
   * 
   * @return 调度任务列表
   */
  public List<PlatformJobCron01Model> selectJobAll();
}

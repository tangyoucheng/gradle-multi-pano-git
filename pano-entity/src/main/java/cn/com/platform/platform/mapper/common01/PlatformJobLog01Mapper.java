package cn.com.platform.platform.mapper.common01;

import java.util.HashMap;
import java.util.List;
import cn.com.platform.platform.mapper.common.PlatformJobLogMapper;
import cn.com.platform.platform.model.common.PlatformJobLog;

public interface PlatformJobLog01Mapper extends PlatformJobLogMapper {
  /**
   * 检索定时任务日志的数量
   * 
   * @param parameter
   * @return
   */
  long selectJobLogsCount(HashMap<?, ?> parameter);


  /**
   * 检索定时任务日志
   * 
   * @param parameter
   * @return
   */
  List<PlatformJobLog> selectJobLogsInfo(HashMap<?, ?> parameter);
  /**
   * 获取quartz调度器日志的计划任务
   * 
   * @param jobLog 调度日志信息
   * @return 调度任务日志集合
   */
  public List<PlatformJobLog> selectJobLogList(PlatformJobLog jobLog);

  /**
   * 通过调度任务日志ID查询调度信息
   * 
   * @param jobLogId 调度任务日志ID
   * @return 调度任务日志对象信息
   */
  public PlatformJobLog selectJobLogById(String jobLogId);

  /**
   * 新增任务日志
   * 
   * @param jobLog 调度日志信息
   * @return 结果
   */
  public int insertJobLog(PlatformJobLog jobLog);

  /**
   * 批量删除调度日志信息
   * 
   * @param ids 需要删除的数据ID
   * @return 结果
   */
  public int deleteJobLogByIds(String[] ids);

  /**
   * 删除任务日志
   * 
   * @param jobId 调度日志ID
   * @return 结果
   */
  public int deleteJobLogById(String jobId);

  /**
   * 清空任务日志
   */
  public void cleanJobLog();
}
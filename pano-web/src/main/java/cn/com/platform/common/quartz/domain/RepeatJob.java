package cn.com.platform.common.quartz.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 重复任务dto
 * 
 * @author 王笃鹏
 * @date 2018-08-20
 *
 */
@Data
public class RepeatJob implements Serializable {

  /** serialVersionUID */
  private static final long serialVersionUID = 1L;
  /** 任务ID */
  private String jobId;;
  /** 任务名称 */
  private String jobName;
  /** 任务组名 */
  private String jobGroup;
  /** 任务方法 */
  private String methodName;
  /** 方法参数 */
  private String methodParams;
  /** 重复次数 */
  private String repeatCount;
  /** 重复间隔 */
  private String repeatInterval;
  /** 开始时间 */
  private Date startDate;;
  /** 结束时间 */
  private Date endDate;
  /** 优先级 */
  private String priority;
  /** 计划执行错误策略 1：立即执行 2；执行一次 3：放弃执行 */
  private String misfirePolicy;
  /** 任务状态 1：正常 0：暂停 */
  private String jobStatus;
  /** 备注 */
  private String remark;
  /** 删除标识 1：删除 0：未删除 */
  private String deleteFlag;
  /** 创建者 */
  private String createUserId;
  /** 创建日期 */
  private Date createDate;
  /** 最终更新者 */
  private String lastUpdateUserId;
  /** 最终更新日期 */
  private Date lastUpdateDate;
}

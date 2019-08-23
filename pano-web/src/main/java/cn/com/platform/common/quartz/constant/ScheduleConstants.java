package cn.com.platform.common.quartz.constant;

/**
 * 任务调度通用常量
 * 
 * @author ruoyi
 */
public interface ScheduleConstants
{
    public static final String TASK_CLASS_NAME = "__TASK_CLASS_NAME__";

    public static final String TASK_PROPERTIES = "__TASK_PROPERTIES__";

    /** 默认 */
    public static final String MISFIRE_DEFAULT = "0";

    /** 立即触发执行 */
    public static final String CRON_MISFIRE_IGNORE_MISFIRES = "1";

    /** 触发一次执行 */
    public static final String CRON_MISFIRE_FIRE_AND_PROCEED = "2";

    /** 不触发立即执行 */
    public static final String CRON_MISFIRE_DO_NOTHING = "3";
    
    /** 以当前时间为触发频率立即触发执行 */
    public static final String REPEAT_MISFIRE_FIRE_NOW = "1";
    
    /** 以错过的第一个频率时间立刻开始执行 */
    public static final String REPEAT_MISFIRE_IGNORE_MISFIRES = "2";
    
    /**  */
    @Deprecated
    public static final String REPEAT_MISFIRE_NEXTWITH_EXISTINGCOUNT = "3";
    
    /**  */
    @Deprecated
    public static final String REPEAT_MISFIRE_NEXTWITH_REMAININGCOUNT = "4";
    
    /**  */
    @Deprecated
    public static final String REPEAT_MISFIRE_NOWWITH_EXISTINGCOUNT = "5";
    
    /**  */
    @Deprecated
    public static final String REPEAT_MISFIRE_NOWWITH_REMAININGCOUNT = "6";
}

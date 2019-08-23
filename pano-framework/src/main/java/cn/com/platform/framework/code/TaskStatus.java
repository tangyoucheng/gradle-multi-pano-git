package cn.com.platform.framework.code;

import cn.com.platform.framework.util.FwCodeUtil;

/**
 * 任务状态。
 * TaskStatus
 * 
 * @author admin
 */
public enum TaskStatus implements EnumConstants {

  /**
   * 申請待ち
   */
  APPLYWAIT("applywait","待申请"),

  /**
   * 申請
   */
  APPLY("apply",""),

  /**
   * 再申請待ち
   */
  REAPPLYWAIT("reapplywait",""),

  /**
   * 再申請
   */
  REAPPLY("reapply",""),

  /**
   * 承認待ち
   */
  APPROVEWAIT("approvewait","审核中"),

  /**
   * 承認
   */
  APPROVE("approve","已同意"),

  /**
   * 承認終了
   */
  APPROVEEND("approveend",""),

  /**
   * 否認
   */
  DENY("deny","已否决"),

  /**
   * 差戻し
   */
  SENDBACK("sendback","已退回"),

  /**
   * 引戻し
   */
  PULLBACK("pullback","已撤回"),

  /**
   * 分岐
   */
  BRANCH("branch",""),

  /**
   * 結合
   */
  UNION("union",""),

  /**
   * 結合失敗
   */
  UNIONFAILED("unionfailed",""),

  /**
   * システムによる処理完了
   */
  SYSTEMCOMPLETE("systemcomplete",""),

  /**
   * 案件操作
   */
  MATTERHANDLE("matterhandle",""),

  /**
   * 案件開始待ち
   */
  MATTERSTARTWAIT("matterstartwait",""),

  /**
   * 案件開始
   */
  MATTERSTART("matterstart",""),

  /**
   * 案件終了待ち
   */
  MATTERCOMPLETEWAIT("mattercompletewait",""),

  /**
   * 案件終了
   */
  MATTERCOMPLETE("mattercomplete",""),

  /**
   * 保留
   */
  RESERVE("reserve",""),

  /**
   * 保留状態
   */
  RESERVEWAIT("reservewait",""),

  /**
   * 保留解除
   */
  RESERVECANCEL("reservecancel",""),

  /**
   * 取止め
   */
  DISCONTINUE("discontinue",""),

  /**
   * 起票
   */
  DRAFT("draft",""),

  /**
   * 申請（復元）
   */
  APPLYRESTORE("applyrestore",""),

  /**
   * 再申請（復元）
   */
  REAPPLYRESTORE("reapplyrestore","");

  private final String code;
  private final String messageId;

  private TaskStatus(final String code, final String messageId) {
    this.code = code;
    this.messageId = messageId;
  }

  /**
   * 获取声明中包含enum定数的代码值。
   * 
   * @return String 声明中包含enum定数的代码值
   */
  @Override
  public String toString() {
    return this.code;
  }

  /**
   * 获取TaskStatus。
   * 
   * @param code 代码值
   * @return TaskStatus 标识状态enum定数
   */
  public static TaskStatus getEnum(final String code) {
    return FwCodeUtil.stringToEnum(TaskStatus.class, code);
  }

  /**
   * 获取信息ID。<br>
   * 信息ID是设置在信息属性文件中的值。<br>
   * 
   * @return String 信息ID
   */
  public String getMessageId() {
    return this.messageId;
  }
}

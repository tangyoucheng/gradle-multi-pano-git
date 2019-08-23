/*
 * Copyright(c) 2011
 */

package cn.com.platform.framework.common.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * サービスフレームワークアプリケーション例外。
 * <p>
 * サービスフレームワーク内にてアプリケーション例外を生成する場合はこの例外クラスをスローする。
 * </p>
 *
 * @author t.d.m
 */
public class ApplicationException extends Throwable {

  /**
   * 串行版本号。
   */
  private static final long serialVersionUID = 5120912413704313431L;

  /**
   * 例外の発生原因となったメッセージリスト。
   */
  private List<String> errorMessages = new ArrayList<String>();

  /**
   * ダイアログボタン押下時に実行するコールバックファンクション名。
   */
  private String callBackFunction = null;

  /**
   * ApplicationException を構築します。
   */
  public ApplicationException() {
    super();
  }

  /**
   * 指定された詳細メッセージを示す ApplicationException を構築します。
   *
   * @param message 詳細メッセージ
   */
  public ApplicationException(String message) {
    super(message);
  }

  /**
   * 指定された原因を持つ ApplicationException を構築します。
   *
   * @param throwable 原因
   */
  public ApplicationException(Throwable throwable) {
    super(throwable);
  }

  /**
   * 指定された詳細メッセージと原因を持つ ApplicationException を構築します。
   *
   * @param message 詳細メッセージ
   * @param throwable 原因
   */
  public ApplicationException(String message, Throwable throwable) {
    super(message, throwable);
  }

  /**
   * 例外の発生原因となったメッセージリストを取得します。
   *
   * @return メッセージリスト
   */
  public List<String> getErrorMessages() {
    return errorMessages;
  }

  /**
   * 例外の発生原因となったメッセージリストを設定します。
   *
   * @param errorMsg メッセージリスト
   */
  public void setErrorMessages(List<String> errorMsg) {
    errorMessages = errorMsg;
  }

  /**
   * 取得回调函数。
   * 
   * @return ダイアログボタン押下時に実行するコールバックファンクション名。
   */
  public final String getCallBackFunction() {
    return callBackFunction;
  }

  /**
   * 设定回调函数。
   * 
   * @param callBackFunc ダイアログボタン押下時に実行するコールバックファンクション名。
   */
  public final void setCallBackFunction(String callBackFunc) {
    callBackFunction = callBackFunc;
  }
}

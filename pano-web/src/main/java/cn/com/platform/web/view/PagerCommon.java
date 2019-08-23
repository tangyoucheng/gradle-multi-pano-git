package cn.com.platform.web.view;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.servlet.jsp.JspException;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;

public class PagerCommon implements Serializable {

  private static final long serialVersionUID = 5729832874890369508L;

  private String formId;
  private String formAction;
  private String pageSize;
  private String pageStartRowNo = "0";
  private String recordCount = "-1";
  /* uniqueフラグ */
  private boolean uniqueFlag;
  /* ajax分页处理方法 */
  private String ajaxPageMethod;

  /**
   * 编辑分页共通信息
   * 
   * @param pagerForm
   * @return
   * @throws JspException
   */
  public String[] doEditPageInfo(PagerForm pagerForm) throws JspException {

    String[] _result = new String[] {"", ""};
    StringBuilder strShow = new StringBuilder();
    this.formId = pagerForm.getFormId();
    this.formAction = pagerForm.getFormAction();
    this.pageStartRowNo = pagerForm.getPageStartRowNo();
    this.recordCount = pagerForm.getRecordCount();
    this.pageSize = pagerForm.getPageSize();
    this.uniqueFlag = pagerForm.isUniqueFlag();
    this.ajaxPageMethod = pagerForm.getAjaxPageMethod();

    int totalPages = NumberUtils.parseNumber(recordCount, Integer.class)
        / NumberUtils.parseNumber(pageSize, Integer.class);
    long mod = NumberUtils.parseNumber(recordCount, Integer.class)
        % NumberUtils.parseNumber(pageSize, Integer.class);
    if (mod > 0) {
      totalPages++;
    }

    int currentPage = getCurrentPageNo();
    if (totalPages == 0) {
      currentPage = 0;
    }

    int maxPage = 5;
    int lefthalfMaxPage = NumberUtils.convertNumberToTargetClass((maxPage - 1) / 2, Integer.class);
    int righthalfMaxPage = 5 - lefthalfMaxPage;
    strShow.append("<div id='");
    strShow.append(getFormId());
    if (recordCount.equals("0")) {
      strShow.append("_pageShowInfo' class='align-C' style='display:none;'>");
    } else {
      strShow.append("_pageShowInfo' class='align-C'>");
    }
    // ページ
    strShow.append("<font color='blue'>");
    strShow.append(currentPage);
    strShow.append(" / ");
    strShow.append(totalPages);
    strShow.append(" 页　</font>");

    if (currentPage <= 1) {
      // 先頭ページ、前へ
      strShow.append("<label>[首页]");
      strShow.append("[上一页]</label>　");
    } else if (currentPage > 1) {
      // 先頭ページ
      strShow.append("[<a href='javascript:void(1);' ");
      this.appendAttribute(strShow, "onClick", getOnclickScript(1));
      strShow.append("><font color='blue'>首页</font></a>]");
      // 前へ
      strShow.append("[<a href='javascript:void(1);' ");
      this.appendAttribute(strShow, "onClick", getOnclickScript(getCurrentPageNo() - 1));
      strShow.append("><font color='blue'>上一页</font></a>]　");
    }

    if (totalPages > 0 && totalPages <= maxPage) {
      for (int i = 1; i <= totalPages; i++) {
        strShow.append(getNumberOnclickScript(i));
      }
    } else if (totalPages > maxPage) {
      if (currentPage <= lefthalfMaxPage) {
        for (int i = 1; i <= maxPage; i++) {
          strShow.append(getNumberOnclickScript(i));
        }
      } else if (currentPage > lefthalfMaxPage && currentPage <= totalPages - righthalfMaxPage) {
        for (int i = currentPage - lefthalfMaxPage; i < currentPage + righthalfMaxPage; i++) {
          strShow.append(getNumberOnclickScript(i));
        }
      } else {
        for (int i = totalPages - maxPage + 1; i <= totalPages; i++) {
          strShow.append(getNumberOnclickScript(i));
        }
      }
    }

    if (currentPage == totalPages || totalPages == 0) {
      // 次へ、最終ページ
      strShow.append("<label>[下一页]");
      strShow.append("[末页]</label>");
    } else if (currentPage != totalPages && totalPages != 0) {
      // 次へ
      strShow.append("[<a href='javascript:void(1);' ");
      this.appendAttribute(strShow, "onClick", getOnclickScript(getCurrentPageNo() + 1));
      strShow.append("><font color='blue'>下一页</font></a>]");
      // 最終ページ
      strShow.append("[<a href='javascript:void(1);' ");
      this.appendAttribute(strShow, "onClick", getOnclickScript(getLastPageNo()));
      strShow.append("><font color='blue'>末页</font></a>]");
    }
    strShow.append("</div>");
    _result[0] = strShow.toString();

    if (uniqueFlag) {
      StringBuilder strHid = new StringBuilder();
      strHid.append("<div id='");
      strHid.append(getFormId());
      strHid.append("_pageHiddenInfo' >");
      strHid.append(renderHiddenElement(getFormId() + "_hidPageStartRowNo", "pageStartRowNo",
          pageStartRowNo));
      strHid
          .append(renderHiddenElement(getFormId() + "_hidRecordCount", "recordCount", recordCount));
      strHid.append(renderHiddenElement(getFormId() + "_hidPageSize", "pageSize", pageSize));
      strHid.append("</div>");
      _result[1] = strHid.toString();
    }
    return _result;
  }

  /**
   * onclick時のjavascriptを生成する。
   * 
   * @param _pageNo ページ番号
   * @return onclick時のjavascript
   */
  private String getOnclickScript(int _pageNo) {

    // form属性が指定されている場合、そのまま使用
    String formName = getFormId();

    // ページ情報設定スクリプト
    StringBuilder pageScript = new StringBuilder();

    pageScript.append("jQuery('#");
    pageScript.append(getFormId());
    pageScript.append("_hidPageStartRowNo').val('");
    pageScript.append(getPageStartRowNo(_pageNo));
    pageScript.append("'); ");

    pageScript.append("jQuery('#");
    pageScript.append(getFormId());
    pageScript.append("_hidRecordCount').val('");
    pageScript.append(getRecordCount());
    pageScript.append("'); ");

    pageScript.append("jQuery('#");
    pageScript.append(getFormId());
    pageScript.append("_hidPageSize').val('");
    pageScript.append(getPageSize());
    pageScript.append("'); ");

    if (ObjectUtils.isEmpty(ajaxPageMethod)) {
      pageScript.append("jQuery('#");
      pageScript.append(formName);
      pageScript.append("').attr('action', ");

      pageScript.append("'");
      pageScript.append(this.formAction);
      pageScript.append("');");

      pageScript.append("jQuery('#");
      pageScript.append(formName);
      pageScript.append("').submit(); ");
    } else {
      pageScript.append(ajaxPageMethod);
      pageScript.append("();");
    }

    pageScript.append("return false;");

    return pageScript.toString();
  }

  /**
   * onclick時のjavascriptを生成する。
   * 
   * @param _pageNo ページ番号
   * @return onclick時のjavascript
   */
  private String getNumberOnclickScript(int _pageNo) {

    // ページ情報設定スクリプト
    StringBuilder pageScript = new StringBuilder();

    if (_pageNo == getCurrentPageNo()) {
      pageScript.append(
          "[<span style='color:#FF0000; border: 1px solid #cccccc; font-weight:bold; width:15px;text-align: center;'>");
      pageScript.append(_pageNo);
      pageScript.append("</span>]　");
    } else {
      pageScript.append("[<a href='javascript:void(1);' ");
      this.appendAttribute(pageScript, "onClick", getOnclickScript(_pageNo));
      pageScript.append("><font color='blue'>");
      pageScript.append(_pageNo);
      pageScript.append("</font></a>]　");
    }

    return pageScript.toString();
  }

  /**
   * 現在の表示ページ番号を取得する。
   * 
   * @return 現在の表示ページ番号
   */
  private int getCurrentPageNo() {

    if (NumberUtils.parseNumber(pageStartRowNo, Integer.class) < 0
        || NumberUtils.parseNumber(recordCount, Integer.class) < 0) {
      return -1;
    }

    // 取得商和余数
    BigDecimal[] pageNo = NumberUtils.parseNumber(pageStartRowNo, BigDecimal.class)
        .divideAndRemainder(NumberUtils.parseNumber(pageSize, BigDecimal.class));
    if (pageNo[1].intValue() > 0) {
      return pageNo[0].intValue() + 2;
    } else {
      return pageNo[0].intValue() + 1;
    }
  }

  /**
   * 指定ページ番号の開始行番号を取得する。
   * 
   * @param _pageNo ページ番号
   * @return 現在ページの開始行番号
   */
  private int getPageStartRowNo(int _pageNo) {

    if (NumberUtils.parseNumber(getRecordCount(), Integer.class) > 0) {
      return (NumberUtils.parseNumber(pageSize, Integer.class) * _pageNo)
          - NumberUtils.parseNumber(pageSize, Integer.class);
    }

    return 0;
  }

  /**
   * 現在ページの終了行番号を取得する。
   * 
   * @return 現在ページの終了行番号
   */
  private int getPageEndRowNo() {

    if (NumberUtils.parseNumber(getRecordCount(), Integer.class) > 0) {
      int pageEndNo = NumberUtils.parseNumber(pageSize, Integer.class) * getCurrentPageNo();

      if (NumberUtils.parseNumber(getRecordCount(), Integer.class) < pageEndNo) {
        return NumberUtils.parseNumber(getRecordCount(), Integer.class);
      }
      return pageEndNo;
    }

    return 0;
  }

  /**
   * 最終ページ番号を取得する。
   * <p>
   * 最終ページ番号を取得する。<br>
   * </p>
   * 
   * @return 最終ページ番号
   */
  private int getLastPageNo() {

    int lastPage = 1;

    if (NumberUtils.parseNumber(getRecordCount(), Integer.class) > 0) {
      lastPage = NumberUtils.parseNumber(getRecordCount(), Integer.class)
          / NumberUtils.parseNumber(pageSize, Integer.class);
      if ((NumberUtils.parseNumber(getRecordCount(), Integer.class)
          % NumberUtils.parseNumber(pageSize, Integer.class)) != 0) {
        lastPage++;
      }
    }

    return lastPage;
  }

  /**
   * hiddenタイプのINPUTタグを生成する。
   * 
   * @param _id フィールドID
   * @param _name フィールド名
   * @param _value 値
   * @return INPUTタグ
   */
  private String renderHiddenElement(String _id, String _name, String _value) {

    StringBuilder handler = new StringBuilder();

    handler.append("<INPUT");
    appendAttribute(handler, "type", "hidden");
    appendAttribute(handler, "id", _id);
    appendAttribute(handler, "name", _name);
    appendAttribute(handler, "value", _value);

    handler.append("/>");

    return handler.toString();
  }

  /**
   * 属性を生成.
   * 
   * @param _handlers 追加先バッファ
   * @param _name 属性名
   * @param _value 属性値
   */
  private void appendAttribute(StringBuilder _handlers, String _name, Object _value) {
    if (_value != null && !ObjectUtils.isEmpty(_value.toString())) {
      _handlers.append(" ");
      _handlers.append(_name);
      _handlers.append("=\"");
      _handlers.append(_value);
      _handlers.append("\"");
    }
  }

  public String getFormId() {
    return formId;
  }

  public void setFormId(String formId) {
    this.formId = formId;
  }

  public String getFormAction() {
    return formAction;
  }

  public void setFormAction(String formAction) {
    this.formAction = formAction;
  }

  public String getPageSize() {
    return pageSize;
  }

  public void setPageSize(String pageSize) {
    this.pageSize = pageSize;
  }

  public String getPageStartRowNo() {
    return pageStartRowNo;
  }

  public void setPageStartRowNo(String pageStartRowNo) {
    this.pageStartRowNo = pageStartRowNo;
  }

  public String getRecordCount() {
    return recordCount;
  }

  public void setRecordCount(String recordCount) {
    this.recordCount = recordCount;
  }

  public boolean isUniqueFlag() {
    return uniqueFlag;
  }

  public void setUniqueFlag(boolean uniqueFlag) {
    this.uniqueFlag = uniqueFlag;
  }

  public String getAjaxPageMethod() {
    return ajaxPageMethod;
  }

  public void setAjaxPageMethod(String ajaxPageMethod) {
    this.ajaxPageMethod = ajaxPageMethod;
  }

}

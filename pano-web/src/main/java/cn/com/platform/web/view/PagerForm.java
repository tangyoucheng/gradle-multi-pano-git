package cn.com.platform.web.view;

import java.io.Serializable;

public class PagerForm implements Serializable {
    
    private static final long serialVersionUID = 5729832874890369508L;
    
    private String formId;
    private String formAction;
    private String pageSize;
    private String pageStartRowNo = "0";
    private String recordCount = "-1";
    /** uniqueフラグ */
    private boolean uniqueFlag;
    /* ajax分页处理方法 */
    private String ajaxPageMethod;
    
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

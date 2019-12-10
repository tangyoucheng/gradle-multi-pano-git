<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="form-group form-row" id="div_text_info">
    <label class="col-form-label col-sm-1 text-left" for="txt_textInfo">变更前文本</label>
    <div class="col-sm-5">
        <form:textarea id="txt_notes" path="oldTextflowInfo" cssClass="form-control" htmlEscape="true" maxlength="2000"
            rows="3" placeholder="" style="overflow: auto;" readonly="true"/>
    </div>
</div>
<div class="form-group form-row" id="div_text_info">
    <label class="col-form-label col-sm-1 text-left" for="txt_textInfo">变更后文本</label>
    <div class="col-sm-5">
        <form:textarea id="txt_notes" path="textflowInfo" cssClass="form-control" htmlEscape="true" maxlength="2000"
            rows="3" placeholder="" style="overflow: auto;" />
    </div>
</div>

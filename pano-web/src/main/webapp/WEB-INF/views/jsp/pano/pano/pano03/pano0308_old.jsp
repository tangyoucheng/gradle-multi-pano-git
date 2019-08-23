<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.cdic.form.ic03.Ic0308Form"%>
<%@ page import="cn.com.cdic.cnst.CommonConstants"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp"%>
<imui:head>
    <title>展览目录编辑</title>
</imui:head>
<script src="cdic/js/ic03/ic0308.js"></script>

<div class="imui-form-container" style="width: 90%;">
    <imui:dialog id="ic0308Finish" modal="true" autoOpen="false" onClose="closeDialog();">
        <div class="imui-form-container-narrow" style="border: none;">
            <h2>设定成功！</h2>
            <div class="imui-operation-parts">
                <input type="button" value="确定" id="ic0308-confirm-button" class="imui-medium-button" />
            </div>
        </div>
    </imui:dialog>
    <form id="ic0308Form" action="ic03/ic0308" method="post">
        <input type="hidden" id="expositionId_0308" name="expositionId_0308" value="${expositionId_0308}" />


        <div id="buttonsTitleDiv" class="imui-chapter-title"
            style="background-color: rgb(251, 251, 251); margin-top: 10px">
            <h2>展览目录一览</h2>
        </div>

        <input type="button" value="全选" id="selectAll" class="imui-button imui-running-button" />
        <input type="button" value="全解除" id="cancelAll" class="imui-button imui-running-button" />

        <div id="buttonslistDiv" style="width: 100%; height: 420px; margin: 5px; overflow-y: scroll">
            <table id="ic0308ListDataTable" style="width: 100%; margin-top: 2px;" border="1"
                class="imui-table-sort eq_nowrap_hidden">
                <thead>
                    <tr>
                        <th class="align-C valign-M" style="width: 30px;">选择</th>
                        <th class="align-C valign-M" style="width: 150px;">缩略图</th>
                        <th class="align-C valign-M" style="width: auto;">场景名称</th>
                        <th class="align-C valign-M" style="width: auto;">缩略图信息</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${thumbInfo}" var="searchResult" varStatus="searchResultStatus">
                        <tr id="ic0305Tr_${searchResultStatus.index}">
                            <td style="width: 30px; text-align: center; vertical-align: middle;">
                                <input type="checkbox" name="thumbSelected[${searchResultStatus.index}]" id="chkBox"
                                    value="${searchResult.panoramaId}" ${searchResult.thumbnailShowFlag} />
                            </td>
                            <td align="center";>
                                <img id="img_${searchResultStatus.index}" src="${searchResult.panoramaPath}"
                                    style="width: 80px; height: 80px;" />
                            </td>
                            <td>${searchResult.panoramaName}</td>
                            <td>
                                <input type="text" maxlength="5" name="ic0308ThumbNote[${searchResultStatus.index}]"
                                    value="${searchResult.thumbNote}" title="${searchResult.thumbNote}"
                                    style="width: 90%;" />
                                <input type="hidden" name="ic0308ThumbNoteHidden[${searchResultStatus.index}]"
                                    value="${searchResult.panoramaId}" style="width: 90%;" />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="imui-operation-parts">
            <input type="button" value="保存" id="button-ic0308-confirm" class="imui-medium-button" />
        </div>
    </form>
</div>
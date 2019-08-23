<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.cdic.form.ic01.Ic0110Form"%>
<%@ page import="cn.com.framework.utils.*"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp" %>

<% String tenantId = CommonUtil.getLoginAccountContext().getTenantId(); %>
<% boolean isTenant =false;
if(CommonUtil.isLoginUserArchiveUser("tenant_manager")||CommonUtil.isLoginUserArchiveUser("role_scene_admin")){
    isTenant =true;
} %>
<imui:head>
    <title>展览一览画面</title>
</imui:head>

<script src="product_vr/js/vr01/vr0110.js"></script>

<!--画面标题 -->
<div class="imui-title">
    <h1>展览一览</h1>
</div>
                        
<div id="setSound"></div>

<!--内容区域-->
<div class="imui-form-container" style="width: 95%;padding-bottom: 0px;">
    <imui:dialog id="ic0110ShowShortCutUrl" modal="true" autoOpen="false" width="400" title="对外链接">
        <div class="imui-form-container" style="border: none;">
            <table style="table-layout:fixed;" class="imui-form">
                <tr>
                    <th class="align-R valign-t" style="width: 60px;">外部链接：</th>
                    <td id="ic0110SelectedShortCutUrl" style="word-wrap:break-word; word-break:break-all"></td>
                </tr>
            </table>
            <div class="imui-operation-parts">
                <input type="button" value="复制链接" id="button-copy-confirm" class="imui-medium-button" />
            </div>
        </div>
    </imui:dialog>

    <imui:dialog id="ic0110Releasing" modal="true" autoOpen="false" width="400">
        <div class="imui-form-container" style="border: none;">
            <table>
                <tr>
                    <td>
                        <img src="framework/image/loading.gif" style="width:40px; height:40px"/>
                    </td>
                    <td style="vertical-align: middle;">
                        <h2 style="font-weight:bold;margin-bottom: 5px;">&nbsp;&nbsp;&nbsp;&nbsp;发布中，请勿刷新或关闭该窗口。</h2>
                    </td>
                </tr>
            </table>
        </div>
    </imui:dialog>
    <imui:dialog id="ic0110ReleaseFinish" modal="true" autoOpen="false" width="400" onClose="closeDialogAndRefresh">
        <div class="imui-form-container" style="border: none;">
            <h2 style="font-weight:bold;margin-bottom: 5px;">指定的展览发布成功！</h2>
            <table style="table-layout:fixed;" class="imui-form">
                <tr>
                    <th class="align-R valign-t" style="width: 60px;">外部链接：</th>
                    <td id="selectedShortCutUrl" style="word-wrap:break-word; word-break:break-all"></td>
                </tr>
            </table>
            <div class="imui-operation-parts">
                <input type="button" value="复制链接" id="button-releaseFinish-copy-confirm" class="imui-medium-button" />
            </div>
        </div>
    </imui:dialog>
                          
    <form id="search-form" action="ic01/ic0110"  method="post">
    <input type="hidden" name="pageStartRowNoFromIc0104" value="${pageStartRowNoFromIc0104}"/>
    
    <div id="contactlist-imuigadgetbar_base" class="imui-gadgetbar">
        <div> 
            <div class="imui-chapter-title" style="background-color:rgb(251, 251, 251)">
                <!-- 検索条件 -->
                <h2>检索条件</h2>
            </div>
     
            <div style="font-weight:bold;color:red;margin-bottom: 5px;">
               <html:errors ></html:errors>
            </div>
            
            <div id="imui-5i7lik3z4oxyc6x">
                <table class="imui-form-search-condition" >
                    <tbody>
                        <tr>
                            <th class="" style="width:15%;"><label>编号：</label></th>
                            <td style="width:35%;vertical-align:middle" >
                                <imui:textbox id="textbox" name="condition_expositionId" value="${condition_expositionId}" autofocus="true"/>
                            </td>
                            <th class="" style="width:15%;"><label>名称：</label></th>
                            <td style="width:35%;vertical-align:middle" >
                                <imui:textbox id="textbox" name="condition_expositionName" value="${condition_expositionName}" autofocus="true"/>
                            </td>
                        </tr>
                        <tr>
                            <th class="" style="width:15%;"><label>开展时间：</label></th>
                            <td> <fw:calendar id="condition_expositionStartDate" name="condition_expositionStartDate" value="${condition_expositionStartDate }" 
                            style="width: 147px; background-color:rgba(237, 237, 237, 1);"></fw:calendar></td>
                            <th class="" style="width:15%;"><label>撤展时间：</label></th>
                            <td> <fw:calendar id="condition_expositionEndDate" name="condition_expositionEndDate" value="${condition_expositionEndDate }" 
                            style="width: 147px; background-color:rgba(237, 237, 237, 1);"></fw:calendar></td>
                        </tr>
                        <tr>
                            <th class="" style="width:15%;"><label>状态：</label></th>
                            <td style="width:auto;vertical-align:middle" >
                                <fw:select id="expositionStatus" name="condition_expositionStatus" selectedValue="${condition_expositionStatus}"  list="${condition_expositionStatusSelectInfo}" blank="false" style="width: 155px;"/>
                            </td>
                            <th class="" style="width:15%;"><label>模式：</label></th>
                            <td style="width:35%;vertical-align:middle" >
                       			<fw:radio name="vrFlag" selectedValue="${vrFlag}" list="${condition_expositionModelSelectInfo}" /> 
                     		</td>
                        </tr>
                    </tbody>
                </table>
                <div class="align-R" >
                    <!-- 検索 -->
                    <input id="search-button" value='检索' class="imui-medium-button" type="button" />
                </div>
            </div>
        </div>
    </div>
     
    <div class="imui-gadget" style="margin-top: 8px;">
        <div class="imui-chapter-title" style="background-color:rgb(251, 251, 251);">
            <h2>展览一览</h2>
        </div>
        
        <c:if test="<%=isTenant%>">
	            <div style="width: 100%; height: 420px; margin: 0px; overflow: hidden;">
		            <table style="width: 100%;" border="1" class="imui-table-sort eq_nowrap_hidden" >
		            <tr>
		                <th class="align-C valign-M" style="width:20px;" rowspan="2">NO</th>
		                <th class="align-C valign-M" style="width:17%;" rowspan="2">名称</th>
		                <th class="align-C valign-M" style="width:13%;" rowspan="2">编号</th>
		                <th class="align-C valign-M" style="width:40px;" rowspan="2">状态</th>
		                <th class="align-C valign-M" style="width:100px;" rowspan="2">开展时间</th>
		                <th class="align-C valign-M" style="width:100px;" rowspan="2">撤展时间</th>
		                <th class="align-C valign-M" style="width:50px;" rowspan="2">场景编辑</th>
		                <th class="align-C valign-M" style="width:130px;border-bottom:1px;">发布时间</th>
		                <th class="align-C valign-M" style="width:40px;border-bottom:1px;" >发布</th>
		                <th class="align-C valign-M" style="width:55px;border-bottom:1px;" >下载</th>
		                <th class="align-C valign-M" style="width:40px;border-bottom:1px;" >URL</th>
		                <th class="align-C valign-M" style="width:40px;border-bottom:1px;" >预览</th>
		               
		            </tr>
		            <tr>   
		            	<th class="align-C valign-M" style="width:130px;">vr发布时间</th>
		            	<th class="align-C valign-M" style="width:40px;" >vr发布</th>
		                <th class="align-C valign-M" style="width:40px;" >vr下载</th>
		                <th class="align-C valign-M" style="width:45px;" >vr_URL</th>
		                <th class="align-C valign-M" style="width:40px;" >vr预览</th></tr>
		            <c:forEach items="${expositionInfo}" var="searchResult" varStatus="searchResultStatus">
		                <tr>
		                    <td style="text-align: center;" rowspan="2">${searchResultStatus.index + 1 +pageStartRowNo}
		                    </td>
		                    
		                    <td title="${searchResult.expositionName}" class="align-l valign-M" rowspan="2">
		                            ${searchResult.expositionName}
		                    </td>
		                    <td title="${searchResult.expositionId}" class="align-l valign-M" style="width:15%;" rowspan="2">${searchResult.expositionId}</td>
		                    
		                    <td title="${searchResult.status}" class="align-l valign-M" style="width:15%;" rowspan="2">${searchResult.status}</td>
		                    <c:if test="${empty searchResult.expositionStartDate}">
                                <td class="align-l valign-M" rowspan="2"></td>
                            </c:if>
		                    <c:if test="${not empty searchResult.expositionStartDate}">
                                <td title="${searchResult.expositionStartDate}" class="align-l valign-M" style="width:15%;" rowspan="2">${searchResult.expositionStartDate}</td>
                            </c:if>
                            <c:if test="${empty searchResult.expositionEndDate}">
                                <td class="align-l valign-M" rowspan="2"></td>
                            </c:if>
		                    <c:if test="${not empty searchResult.expositionEndDate}">
		                        <td title="${searchResult.expositionEndDate}" class="align-l valign-M" style="width:15%;" rowspan="2">${searchResult.expositionEndDate}</td>
                            </c:if>
		                    <td class="align-C valign-M" rowspan="2">
		                           <span  style="cursor: pointer;" class="im-ui-icon-common-16-settings" onclick="editPanorama('${searchResult.expositionId}')"></span>
		                    </td>
		                    <c:if test="${empty searchResult.releaseDate}">
		                        <td class="align-l valign-M"></td>
		                    </c:if>
		                    <c:if test="${not empty searchResult.releaseDate}">
		                        <td title='<imartj2ee:Format value="${searchResult.releaseDate}" format="yyyy年MM月dd日 HH:mm" />' 
		                            class="align-l valign-M">
		                                <imartj2ee:Format value="${searchResult.releaseDate}" format="yyyy年MM月dd日 HH:mm" />
		                        </td>
		                    </c:if>
		                    
		                    
		                    <c:if test="${searchResult.panoramaCount eq 0}">
		                        <td class="align-C valign-M" ><span class="im-ui-icon-common-16-disallow"></span></td>
		                    </c:if>
		                    <c:if test="${not (searchResult.panoramaCount eq 0)}">
		                        <td class="align-C valign-M" >
		                                        <a  href="javascript:void(0);" onclick="doRealeaseExpro('<%=tenantId %>','${searchResult.expositionId}')" >
		                                                <span class="im-ui-icon-menu-16-menu-export"></span>
		                                        </a>
		                        </td>
		                    </c:if>
		                    
		                    <td class="align-C valign-M" >
		                        <c:if test="${empty searchResult.releaseDate}">
		                            <span class="im-ui-icon-common-16-disallow"></span>
		                        </c:if>
		                        <c:if test="${not empty searchResult.releaseDate}">
		                               <a  href="javascript:void(0);" onclick="doDownloadExposition('${searchResult.expositionId}')" >
		                                   <span class="im-ui-icon-common-16-file-download"></span>
		                               </a>
		                        </c:if>
		                    </td>
		                    
		                    
		                    <td class="align-C valign-M" >
		                         <c:if test="${empty searchResult.releaseDate}">
		                                <span class="im-ui-icon-common-16-disallow"></span>
		                            </c:if>
		                            <c:if test="${not empty searchResult.releaseDate}">
		                                <a  href="javascript:void(0);" onclick="doCreateShortCutUrl('${searchResult.expositionId}')" >
		                                    <span class="im-ui-icon-menu-16-document-external"></span>
		                                </a>
		                         </c:if>
		                    </td>
		                    
		                    <td class="align-C valign-M" >
		                         <c:if test="${empty searchResult.releaseDate}">
		                                <span class="im-ui-icon-common-16-disallow"></span>
		                            </c:if>
		                            <c:if test="${not empty searchResult.releaseDate}">
		                                <a href="statictour/${searchResult.expositionId}/index.html" target="blank" >
		                                    <span class="im-ui-icon-common-16-preview"></span>
		                                </a>
		                         </c:if>
		                    </td>
		                    
		                   
		                </tr>
		                
		                <tr>
		                	<c:if test="${empty searchResult.releaseDate}">
		                        <td class="align-l valign-M"></td>
		                    </c:if>
		                    <c:if test="${not empty searchResult.releaseDate}">
		                        <td title='<imartj2ee:Format value="${searchResult.releaseDate}" format="yyyy年MM月dd日 HH:mm" />' 
		                            class="align-l valign-M">
		                                <imartj2ee:Format value="${searchResult.releaseDate}" format="yyyy年MM月dd日 HH:mm" />
		                        </td>
		                    </c:if>
		                    <c:if test="${searchResult.panoramaCount eq 0}">
		                        <td class="align-C valign-M" ><span class="im-ui-icon-common-16-disallow"></span></td>
		                    </c:if>
		                    <c:if test="${not (searchResult.panoramaCount eq 0)}">
		                        <td class="align-C valign-M" >
		                                        <a  href="javascript:void(0);" onclick="doRealeaseExpro_vr('<%=tenantId %>','${searchResult.expositionId}')" >
		                                                <span class="im-ui-icon-menu-16-menu-export"></span>
		                                        </a>
		                        </td>
		                    </c:if>
		                    
		                     <td class="align-C valign-M" >
		                        <c:if test="${empty searchResult.releaseDate}">
		                            <span class="im-ui-icon-common-16-disallow"></span>
		                        </c:if>
		                        <c:if test="${not empty searchResult.releaseDate}">
		                               <a  href="javascript:void(0);" onclick="doDownloadExposition_vr('${searchResult.expositionId}')" >
		                                   <span class="im-ui-icon-common-16-file-download"></span>
		                               </a>
		                        </c:if>
		                    </td>
		                    
		                    <td class="align-C valign-M" >
		                         <c:if test="${empty searchResult.releaseDate}">
		                                <span class="im-ui-icon-common-16-disallow"></span>
		                            </c:if>
		                            <c:if test="${not empty searchResult.releaseDate}">
		                                <a  href="javascript:void(0);" onclick="doCreateShortCutUrl_vr('${searchResult.expositionId}')" >
		                                    <span class="im-ui-icon-menu-16-document-external"></span>
		                                </a>
		                         </c:if>
		                    </td>
		                    
		                    <td class="align-C valign-M" >
		                         <c:if test="${empty searchResult.releaseDate}">
		                                <span class="im-ui-icon-common-16-disallow"></span>
		                            </c:if>
		                            <c:if test="${not empty searchResult.releaseDate}">
		                                <a href="statictour/${searchResult.expositionId}_vr/index.html" target="blank" >
		                                    <span class="im-ui-icon-common-16-preview"></span>
		                                </a>
		                         </c:if>
		                    </td>
		                </tr>
		            </c:forEach>
		            </table>
		            <c:if test="${not empty expositionInfo}">
		                <fwui:page formId="search-form" formAction="ic01/ic0110/doPage" uniqueFlag="true"/>
		            </c:if>
		        </div>
        </c:if>
        
		<c:if test="<%=!isTenant%>">
                <div style="width: 100%; height: 420px; margin: 0px; overflow: hidden;">
                    <table style="width: 100%;" border="1" class="imui-table-sort eq_nowrap_hidden" >
	                    
	                    	<tr>
		                        <th class="align-C valign-M" style="width:38px;" rowspan="2">NO</th>
		                        <th class="align-C valign-M" style="width:17%;" rowspan="2">名称</th>
		                        <th class="align-C valign-M" style="width:13%;" rowspan="2">编号</th>
		                        <th class="align-C valign-M" style="width:40px;" rowspan="2">状态</th>
		                        <th class="align-C valign-M" style="width:130px;" rowspan="2">开展时间</th>
		                        <th class="align-C valign-M" style="width:130px;" rowspan="2">撤展时间</th>
		                        <th class="align-C valign-M" style="width:60px;" rowspan="2">场景编辑</th>
		                        <th class="align-C valign-M" style="width:130px;border-bottom:1px;">发布时间</th>
		                        <th class="align-C valign-M" style="width:55px;border-bottom:1px;" >发布</th>
		                        <th class="align-C valign-M" style="width:55px;border-bottom:1px;" >下载</th>
		                        <th class="align-C valign-M" style="width:55px;border-bottom:1px;" >URL</th>
		                        <th class="align-C valign-M" style="width:70px;border-bottom:1px;" >预览</th>
		                        
		                    </tr>
		                    <tr>   
				            	<th class="align-C valign-M" style="width:130px;">vr发布时间</th>
				            	<th class="align-C valign-M" style="width:40px;" >vr发布</th>
				                <th class="align-C valign-M" style="width:40px;" >vr下载</th>
				                <th class="align-C valign-M" style="width:45px;" >vr_URL</th>
				                <th class="align-C valign-M" style="width:40px;" >vr预览</th>
				             </tr>
		                    <c:forEach items="${expositionInfo}" var="searchResult" varStatus="searchResultStatus">
		                        <tr>
		                            <td style="text-align: center;" rowspan="2">${searchResultStatus.index + 1 + pageStartRowNo}
		                            </td>
		                            
		                            <td title="${searchResult.expositionName}" class="align-l valign-M" rowspan="2">
		                                ${searchResult.expositionName}
		                            </td>
		                            <td title="${searchResult.expositionId}" class="align-l valign-M" style="width:15%;" rowspan="2">${searchResult.expositionId}</td>
		                            
		                            
		                            <td title="${searchResult.status}" class="align-l valign-M" style="width:15%;" rowspan="2">${searchResult.status}</td>
		                            <c:if test="${empty searchResult.expositionStartDate}">
		                                <td class="align-l valign-M" rowspan="2"></td>
		                            </c:if>
		                            <c:if test="${not empty searchResult.expositionStartDate}">
		                                <td title="${searchResult.expositionStartDate}" class="align-l valign-M" style="width:15%;" rowspan="2">${searchResult.expositionStartDate}</td>
		                            </c:if>
		                            <c:if test="${empty searchResult.expositionEndDate}">
		                                <td class="align-l valign-M" rowspan="2"></td>
		                            </c:if>
		                            <c:if test="${not empty searchResult.expositionEndDate}">
		                                <td title="${searchResult.expositionEndDate}" class="align-l valign-M" style="width:15%;" rowspan="2">${searchResult.expositionEndDate}</td>
		                            </c:if>
		                            <td class="align-C valign-M" rowspan="2">
		                                <c:if test="${searchResult.sceneEditAcess eq '1'}">
		                                   <span  style="cursor: pointer;" class="im-ui-icon-common-16-settings" onclick="editPanorama('${searchResult.expositionId}')"></span>
		                                </c:if>
		                                <c:if test="${empty searchResult.sceneEditAcess}">
		                                   <span class="im-ui-icon-common-16-disallow"></span>
		                                </c:if>
		                            </td>
		                            
		                            
		                            <c:if test="${empty searchResult.releaseDate}">
		                                <td class="align-l valign-M"></td>
		                            </c:if>
		                            <c:if test="${not empty searchResult.releaseDate}">
		                                <td title='<imartj2ee:Format value="${searchResult.releaseDate}" format="yyyy年MM月dd日 HH:mm" />' 
		                                    class="align-l valign-M">
		                                        <imartj2ee:Format value="${searchResult.releaseDate}" format="yyyy年MM月dd日 HH:mm" />
		                                </td>
		                            </c:if>
		                            
		                            <c:if test="${searchResult.panoramaCount eq 0}">
		                                <td class="align-C valign-M" ><span class="im-ui-icon-common-16-disallow"></span></td>
		                            </c:if>
		                            <c:if test="${not (searchResult.panoramaCount eq 0)}">
		                                <td class="align-C valign-M" >
		                                          <c:if test="${searchResult.sceneEditAcess eq '1' }">
		                                                <a  href="javascript:void(0);" onclick="doRealeaseExpro('<%=tenantId %>','${searchResult.expositionId}')" >
		                                                        <span class="im-ui-icon-menu-16-menu-export"></span>
		                                                </a>                                  
		                                        </c:if>
		                                          <c:if test="${empty searchResult.sceneEditAcess}">
		                                             <span class="im-ui-icon-common-16-disallow"></span>
		                                          </c:if>
		                                </td>
		                            </c:if>
		                            
		                            <td class="align-C valign-M" >
		                                <c:if test="${empty searchResult.releaseDate or empty searchResult.sceneEditAcess}">
		                                    <span class="im-ui-icon-common-16-disallow"></span>
		                                </c:if>
		                                <c:if test="${(not empty searchResult.releaseDate) and searchResult.sceneEditAcess eq '1'}">
		                                       <a  href="javascript:void(0);" onclick="doDownloadExposition('${searchResult.expositionId}')" >
		                                           <span class="im-ui-icon-common-16-file-download"></span>
		                                       </a>
		                                </c:if>
		                            </td>
		                            
		                            <td class="align-C valign-M" >
		                                 <c:if test="${empty searchResult.releaseDate or empty searchResult.sceneEditAcess}">
		                                        <span class="im-ui-icon-common-16-disallow"></span>
		                                    </c:if>
		                                    <c:if test="${(not empty searchResult.releaseDate) and searchResult.sceneEditAcess eq '1'}">
		                                        <a  href="javascript:void(0);" onclick="doCreateShortCutUrl('${searchResult.expositionId}')" >
		                                            <span class="im-ui-icon-menu-16-document-external"></span>
		                                        </a>
		                                 </c:if>
		                            </td>
		                            <td class="align-C valign-M" >
		                                 <c:if test="${empty searchResult.releaseDate or empty searchResult.sceneEditAcess}">
		                                        <span class="im-ui-icon-common-16-disallow"></span>
		                                    </c:if>
		                                    <c:if test="${(not empty searchResult.releaseDate) and searchResult.sceneEditAcess eq '1'}">
		                                        <a href="statictour/${searchResult.expositionId}/index.html" target="blank" >
		                                            <span class="im-ui-icon-common-16-preview"></span>
		                                        </a>
		                                 </c:if>
		                            </td>
	                            </tr>
		        
	                            
	                            <tr>
		                            <c:if test="${empty searchResult.releaseDate}">
		                                <td class="align-l valign-M"></td>
		                            </c:if>
		                            <c:if test="${not empty searchResult.releaseDate}">
		                                <td title='<imartj2ee:Format value="${searchResult.releaseDate}" format="yyyy年MM月dd日 HH:mm" />' 
		                                    class="align-l valign-M">
		                                        <imartj2ee:Format value="${searchResult.releaseDate}" format="yyyy年MM月dd日 HH:mm" />
		                                </td>
		                            </c:if>
		                            
		                            <c:if test="${searchResult.panoramaCount eq 0}">
		                                <td class="align-C valign-M" ><span class="im-ui-icon-common-16-disallow"></span></td>
		                            </c:if>
		                            <c:if test="${not (searchResult.panoramaCount eq 0)}">
		                                <td class="align-C valign-M" >
		                                          <c:if test="${searchResult.sceneEditAcess eq '1' }">
		                                                <a  href="javascript:void(0);" onclick="doRealeaseExpro('<%=tenantId %>','${searchResult.expositionId}')" >
		                                                        <span class="im-ui-icon-menu-16-menu-export"></span>
		                                                </a>                                  
		                                        </c:if>
		                                          <c:if test="${empty searchResult.sceneEditAcess}">
		                                             <span class="im-ui-icon-common-16-disallow"></span>
		                                          </c:if>
		                                </td>
		                            </c:if>
		                            
		                            <td class="align-C valign-M" >
		                                <c:if test="${empty searchResult.releaseDate or empty searchResult.sceneEditAcess}">
		                                    <span class="im-ui-icon-common-16-disallow"></span>
		                                </c:if>
		                                <c:if test="${(not empty searchResult.releaseDate) and searchResult.sceneEditAcess eq '1'}">
		                                       <a  href="javascript:void(0);" onclick="doDownloadExposition('${searchResult.expositionId}')" >
		                                           <span class="im-ui-icon-common-16-file-download"></span>
		                                       </a>
		                                </c:if>
		                            </td>
		                            
		                            <td class="align-C valign-M" >
		                                 <c:if test="${empty searchResult.releaseDate or empty searchResult.sceneEditAcess}">
		                                        <span class="im-ui-icon-common-16-disallow"></span>
		                                    </c:if>
		                                    <c:if test="${(not empty searchResult.releaseDate) and searchResult.sceneEditAcess eq '1'}">
		                                        <a  href="javascript:void(0);" onclick="doCreateShortCutUrl('${searchResult.expositionId}')" >
		                                            <span class="im-ui-icon-menu-16-document-external"></span>
		                                        </a>
		                                 </c:if>
		                            </td>
		        
		                            <td class="align-C valign-M" >
		                                 <c:if test="${empty searchResult.releaseDate or empty searchResult.sceneEditAcess}">
		                                        <span class="im-ui-icon-common-16-disallow"></span>
		                                    </c:if>
		                                    <c:if test="${(not empty searchResult.releaseDate) and searchResult.sceneEditAcess eq '1'}">
		                                        <a href="statictour/${searchResult.expositionId}/index.html" target="blank" >
		                                            <span class="im-ui-icon-common-16-preview"></span>
		                                        </a>
		                                 </c:if>
		                            </td>
		                        </tr>
		                    </c:forEach>
                    </table>
                    <c:if test="${not empty expositionInfo}">
                        <fwui:page formId="search-form" formAction="ic01/ic0110/doPage" uniqueFlag="true"/>
                    </c:if>
                </div>
        </c:if>
		
    </div>    
    </form>
    
</div>

<!-- submit处理 START -->
<form method="POST" action="home" id="back-form">
</form>

<form method="POST" action="ic01/ic0104" id="goto-ic0104-form">
    <input type="hidden" id="hidpExpositionId" name="expositionId" />
    <input type="hidden" id="hidpExpositionName" name="expositionName" />
    <input type="hidden" name="thisFlagIsFromIc0110" id="thisFlagIsFromIc0110" value=""/>
    <input type="hidden" name="pageStartRowNo"  value="${pageStartRowNo}"/>
</form>

<form id="downloadExpositionForm" action="ic01/ic0110/doDownload">
    <input type="hidden" id="ic0101SelectedExpId" name="selectedExpositionId" value=""/>
</form>

<form id="downloadVrExpositionForm" action="ic01/ic0110/doDownload_vr">
    <input type="hidden" id="ic0101SelectedExpId_vr" name="selectedExpositionId" value=""/>
</form>

<!-- submit处理 END -->
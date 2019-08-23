<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="name" type="java.lang.String" required="true"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="list" type="java.util.List" required="false"%>
<%@ attribute name="selectedValue" type="java.lang.String" required="false"%>
<%@ attribute name="disabled" type="java.lang.Boolean" required="false"%>
<%@ attribute name="onChange" type="java.lang.String" required="false"%>
<%@ attribute name="style" type="java.lang.String" required="false"%>
<%@ attribute name="className" type="java.lang.String" required="false"%>
<%@ attribute name="blank" type="java.lang.Boolean" required="false"%>
<%@ attribute name="multiple" type="java.lang.Boolean" required="false"%>
<%@ attribute name="required" type="java.lang.Boolean" required="false"%>
<%
  String disabledString = "";
    if (disabled != null && disabled == true) {
        disabledString = "disabled=\"disabled\"";
    }
    String multipleString = "";
    if (multiple != null && multiple == true) {
        multipleString = "multiple=\"multiple\"";
    }
    String requiredString = "";
    if (required != null && required == true) {
        requiredString = "required=\"required\"";
    }
    boolean blankOption = true;
    if (blank != null) {
        blankOption = (boolean) blank;
    }
%>
<select name="${name}" id="${id}" class="selectpicker ${className}" data-live-search="false" 
    data-size="10" onChange="${onChange}" <%=disabledString%> <%=multipleString%> <%=requiredString%>>
    <%
      if (blankOption) {
    %>
    <option label="" value="" />
    <%
      }
    %>
    <c:forEach var="data" items="${list}">
        <c:choose>
            <c:when test="${ data.recordCode.equals(selectedValue) }">
                <option value="${data.recordCode}" selected="selected">${data.recordValue}</option>
            </c:when>
            <c:otherwise>
                <option value="${data.recordCode}">${data.recordValue}</option>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</select>
<script type="text/javascript">
     var selectObjectId = '<%=id%>';
    // 刷新select
    $("#" + selectObjectId).selectpicker('refresh');
    $("#" + selectObjectId).selectpicker('render');
    //$("#" + selectObjectId).selectpicker('val', selectedValue);
</script>

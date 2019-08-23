<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="name" type="java.lang.String" required="true"%>
<%@ attribute name="list" type="java.util.List" required="true"%>
<%@ attribute name="selectedValue" type="java.lang.String" required="true"%>
<%@ attribute name="disabled" type="java.lang.Boolean" required="false"%>
<%@ attribute name="onClick" type="java.lang.String" required="false"%>
<%@ attribute name="style" type="java.lang.String" required="false"%>
<%@ attribute name="className" type="java.lang.String" required="false"%>
<%
  /**
   * ラジオボタン
   */
  String disabledString = "";
  if (disabled != null && disabled == true) {
    disabledString = "disabled=\"disabled\"";
  }
%>
<div class="form-control border-0 pl-0">
    <c:forEach var="data" items="${list}">
        <c:choose>
            <c:when test="${ data.value.equals(selectedValue) }">
                <label class="radio-inline" for="${name}-${data.value}">
                    <input id="${name}-${data.value}" name="${name}" value="${data.value}" type="radio"
                        checked="checked" <%=disabledString %> onclick="${onClick}"/>${data.label}</label>
            </c:when>
            <c:otherwise>
                <label class="radio-inline" for="${name}-${data.value}">
                    <input id="${name}-${data.value}" name="${name}" value="${data.value}" type="radio"
                        <%=disabledString %>  onclick="${onClick}"/>${data.label}</label>
            </c:otherwise>
        </c:choose>
        &nbsp;
    </c:forEach>
</div>
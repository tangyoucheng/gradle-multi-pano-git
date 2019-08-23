<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ attribute name="id" type="java.lang.String" required="false"%>
<%@ attribute name="src" type="java.lang.String" required="true"%>
<%@ attribute name="style" type="java.lang.String" required="false"%>
<%@ attribute name="className" type="java.lang.String" required="false"%>
<c:choose>
    <c:when test="${not empty src }">
        <img id="${id }" style="${style}" class="${className}" src="${src }" />
    </c:when>
    <c:otherwise>
        <img id="${id }" style="${style}" class="${className}" src="<c:url value="/static/cis/images/common/defaultPic.png"/>" />
    </c:otherwise>
</c:choose>

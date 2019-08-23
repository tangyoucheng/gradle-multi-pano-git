<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="platform" tagdir="/WEB-INF/tags/platform"%>
<%@ taglib prefix="platformui" uri="/platformTagLib"%>
<%@ page import="cn.com.platform.framework.code.FlagStatus"%>
<%@ page import="cn.com.platform.common.CommonConstantsIF"%>
<!DOCTYPE html >
<script type="text/javascript">
    // 定数设定
    var PlatformConstants = {};
    //有效
    PlatformConstants.flagStatus_Enable = '<c:out value="${FlagStatus.Enable.toString()}"></c:out>';
    //无效
    PlatformConstants.flagStatus_Disable = '<c:out value="${FlagStatus.Disable.toString()}"></c:out>';
    //普通用户URI
    PlatformConstants.URI_BASE_MEMBER = '<c:out value="${CommonConstantsIF.URI_BASE_MEMBER}"></c:out>';
    //后台管理员URI
    PlatformConstants.URI_BASE_ADMIN = '<c:out value="${CommonConstantsIF.URI_BASE_ADMIN}"></c:out>';
    //AP服务器临时文件夹
    PlatformConstants.APP_SERVER_TEMP_SESSION_FOLDER = '<c:out value="${CommonConstantsIF.APP_SERVER_TEMP_SESSION_FOLDER}"></c:out>';

    function getContextPath(path) {
        if (path) {
            return '<c:url value="/' + path + '"/>';
        } else {
            return '<c:url value="/"/>';
        }
    }
    function getMemberContextPath(path) {
        if (path) {
            path = PlatformConstants.URI_BASE_MEMBER + "/" + path;
            return '<c:url value="/' + path + '"/>';
        } else {
            return '<c:url value="/' + PlatformConstants.URI_BASE_MEMBER + '/' + '"/>';
        }
    }
    function getAdminContextPath(path) {
        if (path) {
            path = PlatformConstants.URI_BASE_ADMIN + "/" + path;
            return '<c:url value="/' + path + '"/>';
        } else {
            return '<c:url value="/' + PlatformConstants.URI_BASE_ADMIN + '/' + '"/>';
        }
    }
</script>

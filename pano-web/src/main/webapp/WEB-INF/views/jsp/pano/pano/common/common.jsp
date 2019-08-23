<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<%@ taglib prefix="pano" tagdir="/WEB-INF/tags/pano"%>
<%@ page import="cn.com.pano.pano.common.code.MaterialType"%>
<%@ page import="cn.com.pano.pano.common.code.HotspotType"%>
<%@ page import="cn.com.pano.pano.common.code.HotspotUrlType"%>
<%@ page import="cn.com.pano.pano.common.PanoConstantsIF"%>

<!DOCTYPE html >
<script type="text/javascript">
    // 定数设定
    var PanoConstants = {};
    //有效
//     PanoConstants.flagStatus_Enable = '<c:out value="${FlagStatus.Enable.toString()}"></c:out>';
    //无效
//     PanoConstants.flagStatus_Disable = '<c:out value="${FlagStatus.Disable.toString()}"></c:out>';
    //普通用户URI
//     PanoConstants.URI_BASE_MEMBER = '<c:out value="${CommonConstantsIF.URI_BASE_MEMBER}"></c:out>';
    //后台管理员URI
//     PanoConstants.URI_BASE_ADMIN = '<c:out value="${CommonConstantsIF.URI_BASE_ADMIN}"></c:out>';

    PanoConstants.VAL_MATERIAL_TYPE_IMAGE = '<c:out value="${MaterialType.IMAGE.toString()}"></c:out>';
    PanoConstants.VAL_MATERIAL_TYPE_SOUND = '<c:out value="${MaterialType.SOUND.toString()}"></c:out>';
    PanoConstants.VAL_MATERIAL_TYPE_VIDEO = '<c:out value="${MaterialType.VIDEO.toString()}"></c:out>';
    PanoConstants.VAL_MATERIAL_TYPE_HOTSPOT_CHANGE_SCENE = '<c:out value="${MaterialType.HOTSPOT_CHANGE_SCENE.toString()}"></c:out>';
    PanoConstants.VAL_MATERIAL_TYPE_HOTSPOT_IMAGE = '<c:out value="${MaterialType.HOTSPOT_IMAGE.toString()}"></c:out>';
    PanoConstants.VAL_MATERIAL_TYPE_HOTSPOT_LOGO = '<c:out value="${MaterialType.HOTSPOT_LOGO.toString()}"></c:out>';
    PanoConstants.VAL_MATERIAL_TYPE_FLOW_INFO_IMAGE = '<c:out value="${MaterialType.FLOW_INFO_IMAGE.toString()}"></c:out>';
    PanoConstants.VAL_MATERIAL_TYPE_FLOW_INFO_TEXT = '<c:out value="${MaterialType.FLOW_INFO_TEXT.toString()}"></c:out>';
    PanoConstants.VAL_MATERIAL_TYPE_IMAGE_TEXT = '<c:out value="${MaterialType.IMAGE_TEXT.toString()}"></c:out>';
    PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_POLYGON = '<c:out value="${HotspotType.POLYGON.toString()}"></c:out>';
    PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_CHANGE_SCENE = '<c:out value="${HotspotType.CHANGE_SCENE.toString()}"></c:out>';
    PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_IMAGE = '<c:out value="${HotspotType.IMAGE.toString()}"></c:out>';
    PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_LOGO = '<c:out value="${HotspotType.LOGO.toString()}"></c:out>';
    PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_MUSIC = '<c:out value="${HotspotType.MUSIC.toString()}"></c:out>';
    PanoConstants.VAL_HOTSPOT_URL_TYPE_PANORAMA = '<c:out value="${HotspotUrlType.PANORAMA.toString()}"></c:out>';
    PanoConstants.VAL_HOTSPOT_URL_TYPE_IMAGE = '<c:out value="${HotspotUrlType.IMAGE.toString()}"></c:out>';
    PanoConstants.VAL_HOTSPOT_URL_TYPE_SOUND = '<c:out value="${HotspotUrlType.SOUND.toString()}"></c:out>';
    PanoConstants.VAL_HOTSPOT_URL_TYPE_VIDEO = '<c:out value="${HotspotUrlType.VIDEO.toString()}"></c:out>';
    PanoConstants.VAL_HOTSPOT_URL_TYPE_LINK = '<c:out value="${HotspotUrlType.LINK.toString()}"></c:out>';
    PanoConstants.VAL_HOTSPOT_URL_TYPE_TEXT_IMAGE = '<c:out value="${HotspotUrlType.TEXT_IMAGE.toString()}"></c:out>';
    PanoConstants.VAL_EXPOSITION_COOMMANDTYPE_MINMAP = '<c:out value="${PanoConstantsIF.EXPOSITION_COOMMANDTYPE_MINMAP}"></c:out>';
    PanoConstants.VAL_EXPOSITION_COOMMANDTYPE_FLOW_INFO = '<c:out value="${PanoConstantsIF.EXPOSITION_COOMMANDTYPE_FLOW_INFO}"></c:out>';
    PanoConstants.VAL_EXPOSITION_COOMMANDTYPE_TOOL = '<c:out value="${PanoConstantsIF.EXPOSITION_COOMMANDTYPE_TOOL}"></c:out>';
    PanoConstants.VAL_MATERIAL_BELONGTYPE_EXPOSITION = '<c:out value="${PanoConstantsIF.MATERIAL_BELONGTYPE_EXPOSITION}"></c:out>';

    PanoConstants.VAL_WAITING = "Waiting...";

    PanoConstants.VAL_EMBEDPANO_SWF = 'static/pano/pano/common/viewer/panorama.swf';
    PanoConstants.VAL_PANORAMA_POSITIONICON_MAP = 'static/pano/pano/common/images/positionicon/position_icon_map.png';
    PanoConstants.VAL_PANORAMA_POSITIONICON_RED = 'static/pano/pano/common/images/positionicon/position_icon_red.png';
    PanoConstants.VAL_PANORAMA_POSITIONICON_GREEN = 'static/pano/pano/common/images/positionicon/position_icon_green.png';
    PanoConstants.VAL_PANORAMA_POSITIONICON_GUIDE = 'static/pano/pano/common/images/positionicon/position_icon_guide.png';
    PanoConstants.VAL_IMAGE_POSITIONICON_RED = 'static/pano/pano/common/images/positionicon/position_icon_red.png';
    PanoConstants.VAL_IMAGE_POSITIONICON_GREEN = 'static/pano/pano/common/images/positionicon/position_icon_green.png';

    // TODO 测试图片
    PanoConstants.VAL_TEMP_MINI_MAP = 'static/pano/pano/common/template/imagemap.png';
    PanoConstants.VAL_IMAGE_MAPPOINT = 'static/pano/pano/common/template/mappoint_orange.png';
    PanoConstants.VAL_IMAGE_MAPPOINT_BLUE = 'static/pano/pano/common/template/mappoint_blue.png';
    PanoConstants.VAL_IMAGE_MAPPOINT_PINK = 'static/pano/pano/common/template/mappoint_pink.png';
    PanoConstants.VAL_IMAGE_MAPPOINT_PURPLE = 'static/pano/pano/common/template/mappoint_purple.png';

    PanoConstants.VAL_IMAGE_MAPPOINT_ACTIVE = 'static/pano/pano/common/template/mappointactive.png';
    PanoConstants.VAL_IMAGE_LOGO = 'static/pano/pano/common/template/logo.png';
    PanoConstants.VAL_ARROW2_LEFT = 'static/pano/pano/common/template/arrow2_l.png';
    PanoConstants.VAL_ARROW2_RIGHT = 'static/pano/pano/common/template/arrow2_r.png';
</script>

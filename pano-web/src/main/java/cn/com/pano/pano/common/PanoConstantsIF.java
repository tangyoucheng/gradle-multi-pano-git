/*
 * Copyright(c) 2011
 */

package cn.com.pano.pano.common;

import cn.com.pano.pano.common.config.ConfigPanoProperties;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.common.config.ConfigProperties;

public class PanoConstantsIF extends CommonConstantsIF {

  /** 共通：房间图片缩略图的宽。 */
  public static final String PANO_IMAGE_THUMBNAIL_WIDTH =
      ConfigPanoProperties.init().getPanoImageThumbnailWidth();
  /** 共通：房间图片缩略图的高。 */
  public static final String PANO_IMAGE_THUMBNAIL_HEIGHT =
      ConfigPanoProperties.init().getPanoImageThumbnailHeight();


  /** 定数．素材文件夹名。 */
  public static final String MATERIAL_FOLDER_NAME = "material/";

  /** 定数．文件服务器路径.后台管理用。 */
  public static final String VAL_PUBLIC_DIRECTORY_W = ConfigProperties.init().getStoragesPath()
      + ConfigProperties.init().getStoragePublcPath() + "file_w/{0}";
  /** 定数．文件服务器全景图路径.后台管理用。 */
  public static final String VAL_PUBLIC_DIRECTORY_W_PANORAMA =
      ConfigProperties.init().getStoragesPath() + ConfigProperties.init().getStoragePublcPath()
          + "file_w/{0}/panoramas/{1}";
  /** 定数．应用服务器预加载文件路径.后台管理用。 */
  public static final String VAL_PUBLIC_DIRECTORY_W_PRELOADFILE =
      ConfigProperties.init().getStoragesPath() + ConfigProperties.init().getStoragePublcPath()
          + "file_w/{0}/preloadFile/";
  /** 定数．文件服务器图片路径.后台管理用。 */
  public static final String VAL_PUBLIC_DIRECTORY_W_IMAGE =
      ConfigProperties.init().getStoragesPath() + ConfigProperties.init().getStoragePublcPath()
          + "file_w/{0}/images/{1}";
  /** 定数．文件服务器声音文件路径.后台管理用 。 */
  public static final String VAL_PUBLIC_DIRECTORY_W_SOUND =
      ConfigProperties.init().getStoragesPath() + ConfigProperties.init().getStoragePublcPath()
          + "file_w/{0}/sounds/{1}";
  /** 定数．文件服务器视频文件路径.后台管理用。 */
  public static final String VAL_PUBLIC_DIRECTORY_W_VIDEO =
      ConfigProperties.init().getStoragesPath() + ConfigProperties.init().getStoragePublcPath()
          + "file_w/{0}/videos/{1}";

  /** 定数．应用服务器文件路径.后台管理用。 */
  public static final String VAL_APP_SERVER_W =
      APP_SERVER_TEMP_SESSION_FOLDER + "/{0}/file_w_app/{1}";
  /** 定数．应用服务器全景图路径.后台管理用。 */
  public static final String VAL_APP_SERVER_W_PANORAMA =
      APP_SERVER_TEMP_SESSION_FOLDER + "/{0}/file_w_app/{1}/panoramas/{2}";
  /** 定数．应用服务器预加载文件路径.后台管理用。 */
  public static final String VAL_APP_SERVER_W_PRELOADFILE =
      APP_SERVER_TEMP_SESSION_FOLDER + "/{0}/file_w_app/{1}/preloadFile/";
  /** 定数．应用服务器图片路径.后台管理用。 */
  public static final String VAL_APP_SERVER_W_IMAGE =
      APP_SERVER_TEMP_SESSION_FOLDER + "/{0}/file_w_app/{1}/images/{2}";
  /** 定数．应用服务器声音文件路径.后台管理用。 */
  public static final String VAL_APP_SERVER_W_SOUND =
      APP_SERVER_TEMP_SESSION_FOLDER + "/{0}/file_w_app/{1}/sounds/{2}";
  /** 定数．应用服务器视频文件路径.后台管理用。 */
  public static final String VAL_APP_SERVER_W_VIDEO =
      APP_SERVER_TEMP_SESSION_FOLDER + "/{0}/file_w_app/{1}/videos/{2}";

  // TODO 废弃 START
  /** 定数．应用服务器全景图路径.后台管理时预览用。 */
  public static final String VAL_APP_SERVER_W_TEMP_PANORAMA = "file_w_app/{0}/panoramas/temp/{1}";
  /** 定数．应用服务器预加载文件路径.后台管理时预览用 。 */
  public static final String VAL_APP_SERVER_W_TEMP_PRELOADFILE = "file_w_app/{0}/preloadFile/temp/";
  /** 定数．应用服务器图片路径.后台管理时预览用。 */
  public static final String VAL_APP_SERVER_W_TEMP_IMAGE = "file_w_app/{0}/images/temp/{1}";
  /** 定数．应用服务器声音文件路径.后台管理时预览用 。 */
  public static final String VAL_APP_SERVER_W_TEMP_SOUND = "file_w_app/{0}/sounds/temp/{1}";
  /** 定数．应用服务器视频文件路径.后台管理时预览用。 */
  public static final String VAL_APP_SERVER_W_TEMP_VIDEO = "file_w_app/{0}/videos/temp/{1}";
  // TODO 废弃 END

  /** 定数．文件服务器全景图路径.浏览模式用。 */
  public static final String VAL_PUBLIC_DIRECTORY_R_PANORAMA =
      ConfigProperties.init().getStoragesPath() + ConfigProperties.init().getStoragePublcPath()
          + "file_r/{0}/panoramas/{1}";
  /** 定数．文件服务器图片路径.浏览模式用。 */
  public static final String VAL_PUBLIC_DIRECTORY_R_IMAGE =
      ConfigProperties.init().getStoragesPath() + ConfigProperties.init().getStoragePublcPath()
          + "file_r/{0}/images/{1}";
  /** 定数．文件服务器声音文件路径.浏览模式用 。 */
  public static final String VAL_PUBLIC_DIRECTORY_R_SOUND =
      ConfigProperties.init().getStoragesPath() + ConfigProperties.init().getStoragePublcPath()
          + "file_r/{0}/sounds/{1}";
  /** 定数．文件服务器视频文件路径.浏览模式用。 */
  public static final String VAL_PUBLIC_DIRECTORY_R_VIDEO =
      ConfigProperties.init().getStoragesPath() + ConfigProperties.init().getStoragePublcPath()
          + "file_r/{0}/videos/{1}";

  /** 定数．应用服务器全景图路径.浏览模式用。 */
  // public static final String VAL_APP_SERVER_R_PANORAMA = "file_r/{0}/panoramas/{1}";
  /** 定数．应用服务器图片路径.浏览模式用。 */
  // public static final String VAL_APP_SERVER_R_IMAGE = "file_r/{0}/images/{1}";
  /** 定数．应用服务器声音文件路径.浏览模式用。 */
  // public static final String VAL_APP_SERVER_R_SOUND = "file_r/{0}/sounds/{1}";
  /** 定数．应用服务器视频文件路径.浏览模式用 。 */
  // public static final String VAL_APP_SERVER_R_VIDEO = "file_r/{0}/videos/{1}";

  /** 定数．第一个场景。 */
  // public static final String START_SCENE_FLAG_YES = "1";
  /** 定数．不是第一个场景。 */
  // public static final String START_SCENE_FLAG_NO = "0";

  /** 定数．删除。 */
  // public static final String DELETE_FLAG_YES = "1";
  /** 定数．没有删除。 */
  // public static final String DELETE_FLAG_NO = "0";

  /** 定数．引进展。 */
  // public static final String EXPOSITION_TYPE_INTRODUCED = "0";
  /** 定数．外展。 */
  // public static final String EXPOSITION_TYPE_FOREIGN = "1";

  /** 定数．导航热点是推荐路线点。 */
  public static final String RECOMMEND_FLAG_YES = "1";
  /** 定数．导航热点不是推荐路线点。 */
  public static final String RECOMMEND_FLAG_NO = "2";

  /** 定数．地图使用中。 */
  public static final String MAP_USE_STATE_YES = "1";
  /** 定数．地图未使用。 */
  public static final String MAP_USE_STATE_NO = "0";

  /** 定数．素材种类.平面图。 */
  public static final String MATERIAL_TYPE_IMAGE = "1";
  /** 定数．素材种类.声音。 */
  public static final String MATERIAL_TYPE_SOUND = "2";
  /** 定数．素材种类.视频 。 */
  public static final String MATERIAL_TYPE_VIDEO = "3";
  /** 定数．素材种类.场景切换。 */
  public static final String MATERIAL_TYPE_HOTSPOT_CHANGE_SCENE = "4";
  /** 定数．素材种类.普通热点。 */
  public static final String MATERIAL_TYPE_HOTSPOT_IMAGE = "5";
  /** 定数．素材种类.LOGO热点。 */
  public static final String MATERIAL_TYPE_HOTSPOT_LOGO = "6";
  /** 定数．素材种类.图片浮动信息层。 */
  public static final String MATERIAL_TYPE_FLOW_INFO_IMAGE = "7";
  /** 定数．素材种类.文字浮动信息层。 */
  public static final String MATERIAL_TYPE_FLOW_INFO_TEXT = "8";
  /** 定数．素材种类.图文信息。 */
  public static final String MATERIAL_TYPE_IMAGE_TEXT = "9";

  /** 定数．展览预加载文件种类.视频。 */
  public static final String PRELOAD_FILE_TYPE_VIDEO = "1";
  /** 定数．展览预加载文件种类.图片。 */
  public static final String PRELOAD_FILE_TYPE_IMAGE = "0";

  /** 定数．热点种类.多边形热点。 */
  public static final String HOTSPOT_TYPE_HOTSPOT_POLYGON = "1";
  /** 定数．热点种类.导航热点。 */
  public static final String HOTSPOT_TYPE_HOTSPOT_CHANGE_SCENE = "2";
  /** 定数．热点种类.普通热点 。 */
  public static final String HOTSPOT_TYPE_HOTSPOT_IMAGE = "3";
  /** 定数．热点种类.LOGO热点。 */
  public static final String HOTSPOT_TYPE_HOTSPOT_LOGO = "4";
  /** 定数．热点种类.首个场景里被设为推荐路线的点。 */
  public static final String HOTSPOT_TYPE_HOTSPOT_FOR_FIRST_SCENE_RECOMMEND = "5";
  /** 定数．热点种类.音频热点。 */
  public static final String HOTSPOT_TYPE_HOTSPOT_MUSIC = "6";

  /** 定数．热点URL种类.全景图 。 */
  public static final String HOTSPOT_URL_TYPE_PANORAMA = "1";
  /** 定数．热点URL种类.素材图。 */
  public static final String HOTSPOT_URL_TYPE_IMAGE = "2";
  /** 定数．热点URL种类.声音。 */
  public static final String HOTSPOT_URL_TYPE_SOUND = "3";
  /** 定数．热点URL种类.视频 。 */
  public static final String HOTSPOT_URL_TYPE_VIDEO = "4";
  /** 定数．热点URL种类.外部链接。 */
  public static final String HOTSPOT_URL_TYPE_LINK = "5";
  /** 定数．热点URL种类.图文。 */
  public static final String HOTSPOT_URL_TYPE_TEXT_IMAGE = "6";

  /** 定数．素材归属种类.当前展览素材。 */
  public static final String MATERIAL_BELONGTYPE_EXPOSITION = "1";
  /** 定数．素材归属种类.公用素材。 */
  public static final String MATERIAL_BELONGTYPE_COMMON = "2";

  /** 定数．整体效果类型.导航图操作。 */
  public static final String EXPOSITION_COOMMANDTYPE_MINMAP = "1";
  /** 定数．整体效果类型.浮动效果操作。 */
  public static final String EXPOSITION_COOMMANDTYPE_FLOW_INFO = "2";
  /** 定数．整体效果类型.场景工具操作。 */
  public static final String EXPOSITION_COOMMANDTYPE_TOOL = "3";

  /** 定数．展览状态.规划中。 */
  // public static final String EXPOSITION_STATUS_PALN = "0";
  /** 定数．展览状态.进行中。 */
  // public static final String EXPOSITION_STATUS_PROCESS = "1";
  /** 定数．展览状态.已结束。 */
  // public static final String EXPOSITION_STATUS_END = "2";

  /** 定数．非VR展 。 */
  // public static final String EXPOSITION_VR_FLAG_NO = "0";
  /** 定数．VR展。 */
  // public static final String EXPOSITION_VR_FLAG_YES = "1";

  /** 定数．show_l.xml。 */
  public static final String PANOS_SHOW_L_XML = "/show_l.xml";

  /** 定数．登记场景页面。 */
  public static final String REGISTER_SCENE_FLAG = "1";
  /** 定数．编辑场景页面。 */
  public static final String EDIT_SCENE_FLAG = "2";
  /** 定数．登记场景页面 。 */
  public static final String VAL_PUBLIC_DIRECTORY_PANOS_L = "panos_l";
  /** 定数．编辑场景页面。 */
  public static final String VAL_PUBLIC_DIRECTORY_PANOS_R = "panos_r";
  /** 定数．左眼模板 。 */
  public static final String TEMPLATE_SHOW_L = "static/pano/pano/common/template/show_l.xml";
  /** 定数．右眼模板。 */
  public static final String TEMPLATE_SHOW_R = "static/pano/pano/common/template/show_r.xml";

}

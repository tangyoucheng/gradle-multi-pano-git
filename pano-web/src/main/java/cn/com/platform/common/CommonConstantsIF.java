/*
 * Copyright(c) 2011
 */

package cn.com.platform.common;

import cn.com.platform.common.config.ConfigProperties;
import cn.com.platform.framework.common.StandardConstantsIF;

public class CommonConstantsIF extends StandardConstantsIF {

  public String storagesPath;

  /** 先頭表示区分：無。 */
  String SENTO_HYOJI_KUBUN_NASHI = "0";
  /** 先頭表示区分：空白。 */
  String SENTO_HYOJI_KUBUN_SPACE = "1";
  /** 先頭表示区分：全て。 */
  String SENTO_HYOJI_KUBUN_SUBETE = "2";
  /** 先頭表示区分：指定なし。 */
  String SENTO_HYOJI_KUBUN_SHITEI_NASI = "3";
  /** 公開标识：公開しない。 */
  String KOKAI_FLG_KOKAI_SHINAI = "0";
  /** 公開标识：公開する。 */
  String KOKAI_FLG_KOKAI_SURU = "1";

  /** 会话ID 会员登陆可。 */
  public static final String MEMBER_LOGIN_PERMIT = "memberLoginPermit";
  /** 会话ID 后台管理员登陆可。 */
  public static final String ADMIN_LOGIN_PERMIT = "adminLoginPermit";

  /** 普通用户URI。 */
  public static final String URI_BASE_HOME = "home";
  /** 普通用户URI。 */
  public static final String URI_BASE_MEMBER = "member";
  /** 后台管理员URI。 */
  public static final String URI_BASE_ADMIN = "admin";

  public static final String FILE_SAVE_PATH = ConfigProperties.init().getStorageWebappPath();

  /** APP服务器侧房屋图片存储位置{0}用户ID/{1}房源ID。 */
  public static final String WEBAPP_PATH_ROOM_PATH =
      ConfigProperties.init().getStorageWebappPath() + "room/{0}/{1}";
  /** APP服务器侧房屋图片存储位置{0}用户ID/{1}房源ID/{2}图片ID。 */
  public static final String WEBAPP_PATH_ROOM_IMG =
      ConfigProperties.init().getStorageWebappPath() + "room/{0}/{1}/{2}";
  /** 文件服务器侧房屋图片存储位置{0}用户ID/{1}房源ID。 */
  public static final String STORAGE_PATH_ROOM_IMG = ConfigProperties.init().getStoragesPath()
      + ConfigProperties.init().getStoragePublcPath() + "room/{0}/{1}";

  /** APP服务器侧评论图片存储位置{0}用户ID/{1}评论ID。 */
  public static final String WEBAPP_PATH_COMMENT_PATH =
      ConfigProperties.init().getStorageWebappPath() + "comment/{0}/{1}";
  /** APP服务器侧评论图片存储位置{0}用户ID/{1}评论ID/{2}图片ID。 */
  public static final String WEBAPP_PATH_COMMENT_IMG =
      ConfigProperties.init().getStorageWebappPath() + "comment/{0}/{1}/{2}";
  /** 文件服务器侧评论图片存储位置{0}用户ID/{1}评论ID。 */
  public static final String STORAGE_PATH_COMMENT_IMG = ConfigProperties.init().getStoragesPath()
      + ConfigProperties.init().getStoragePublcPath() + "comment/{0}/{1}";

  /** 共通：房间图片缩略图的宽。 */
  public static final String ROOM_IMAGE_THUMBNAIL_WIDTH =
      ConfigProperties.init().getRoomImageThumbnailWidth();
  /** 共通：房间图片缩略图的高。 */
  public static final String ROOM_IMAGE_THUMBNAIL_HEIGHT =
      ConfigProperties.init().getRoomImageThumbnailHeight();
  /** 共通：房间图片的宽。 */
  public static final String ROOM_IMAGE_WIDTH = ConfigProperties.init().getRoomImageWidth();
  /** 共通：房间图片的高。 */
  public static final String ROOM_IMAGE_HEIGHT = ConfigProperties.init().getRoomImageHeight();

  /** 共通：评论图片缩略图的尺寸。 */
  public static final String COMMENT_IMAGE_THUMBNAIL_SIZE =
      ConfigProperties.init().getCommentImageThumbnailSize();
  /** 共通：评论图片的尺寸。 */
  public static final String COMMENT_IMAGE_SIZE = ConfigProperties.init().getCommentImageSize();

  /** 共通：在线用户数据库同步时间间隔。 */
  public static final String ONLINE_USER_DB_SYN_CPERIOD = ConfigProperties.init().getDbSyncPeriod();
}

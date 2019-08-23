/*
 */

package cn.com.pano.pano.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.MessageFormat;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.springframework.util.FastByteArrayOutputStream;
import com.google.common.collect.Lists;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.code.ExpositionStatus;
import cn.com.pano.pano.common.code.ExpositionType;
import cn.com.pano.pano.common.code.MaterialType;
import cn.com.platform.framework.code.CodeModel;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.framework.util.FwCodeUtil;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.util.MessageUtils;

/**
 * 共通工具类。
 * 
 * @author 唐友成
 * @date 2019-08-02
 * 
 */
public class PanoCommonUtil {

  /**
   * 素材类别区分值List取得。
   * 
   * @return 区分值List
   */
  public static List<CodeValueRecord> getMateialTypeList(boolean blankFlag) {
    List<CodeValueRecord> result = Lists.newArrayList();
    if (blankFlag) {
      result.add(new CodeValueRecord("", "全部"));
    }
    // 信息图
    MaterialType materialType =
        FwCodeUtil.stringToEnum(MaterialType.class, MaterialType.IMAGE.toString());
    result.add(new CodeValueRecord(MaterialType.IMAGE.toString(),
        MessageUtils.getMessage(materialType.getMessageId())));
    // 音乐
    materialType = FwCodeUtil.stringToEnum(MaterialType.class, MaterialType.SOUND.toString());
    result.add(new CodeValueRecord(MaterialType.SOUND.toString(),
        MessageUtils.getMessage(materialType.getMessageId())));
    // 视频
    materialType = FwCodeUtil.stringToEnum(MaterialType.class, MaterialType.VIDEO.toString());
    result.add(new CodeValueRecord(MaterialType.VIDEO.toString(),
        MessageUtils.getMessage(materialType.getMessageId())));
    // 场景切换图标
    materialType =
        FwCodeUtil.stringToEnum(MaterialType.class, MaterialType.HOTSPOT_CHANGE_SCENE.toString());
    result.add(new CodeValueRecord(MaterialType.HOTSPOT_CHANGE_SCENE.toString(),
        MessageUtils.getMessage(materialType.getMessageId())));
    // 普通热点
    materialType =
        FwCodeUtil.stringToEnum(MaterialType.class, MaterialType.HOTSPOT_IMAGE.toString());
    result.add(new CodeValueRecord(MaterialType.HOTSPOT_IMAGE.toString(),
        MessageUtils.getMessage(materialType.getMessageId())));
    // LOGO热点
    materialType =
        FwCodeUtil.stringToEnum(MaterialType.class, MaterialType.HOTSPOT_LOGO.toString());
    result.add(new CodeValueRecord(MaterialType.HOTSPOT_LOGO.toString(),
        MessageUtils.getMessage(materialType.getMessageId())));
    // 图片浮动信息层
    materialType =
        FwCodeUtil.stringToEnum(MaterialType.class, MaterialType.FLOW_INFO_IMAGE.toString());
    result.add(new CodeValueRecord(MaterialType.FLOW_INFO_IMAGE.toString(),
        MessageUtils.getMessage(materialType.getMessageId())));
    // 文字浮动信息层
    materialType =
        FwCodeUtil.stringToEnum(MaterialType.class, MaterialType.FLOW_INFO_TEXT.toString());
    result.add(new CodeValueRecord(MaterialType.FLOW_INFO_TEXT.toString(),
        MessageUtils.getMessage(materialType.getMessageId())));
    // 图文信息
    materialType = FwCodeUtil.stringToEnum(MaterialType.class, MaterialType.IMAGE_TEXT.toString());
    result.add(new CodeValueRecord(MaterialType.IMAGE_TEXT.toString(),
        MessageUtils.getMessage(materialType.getMessageId())));
    return result;
  }

  /**
   * 展览类别区分值List取得。
   * 
   * @return 区分值List
   */
  public static List<CodeValueRecord> getExpositionTypeList(boolean blankFlag) {
    List<CodeValueRecord> result = Lists.newArrayList();
    if (blankFlag) {
      result.add(new CodeValueRecord("", "全部"));
    }
    // 引进展
    ExpositionType expositionType =
        FwCodeUtil.stringToEnum(ExpositionType.class, ExpositionType.IMPORT.toString());
    result.add(new CodeValueRecord(ExpositionType.IMPORT.toString(),
        MessageUtils.getMessage(expositionType.getMessageId())));
    // 外展
    expositionType =
        FwCodeUtil.stringToEnum(ExpositionType.class, ExpositionType.OUTPUT.toString());
    result.add(new CodeValueRecord(ExpositionType.OUTPUT.toString(),
        MessageUtils.getMessage(expositionType.getMessageId())));
    return result;
  }

  /**
   * 展览状态区分值List取得。
   * 
   * @return 区分值List
   */
  public static List<CodeValueRecord> getExpositionStatusList(boolean blankFlag) {
    List<CodeValueRecord> result = Lists.newArrayList();
    if (blankFlag) {
      result.add(new CodeValueRecord("", "全部"));
    }
    // 规划中
    ExpositionStatus expositionStatus =
        FwCodeUtil.stringToEnum(ExpositionStatus.class, ExpositionStatus.PLANNING.toString());
    result.add(new CodeValueRecord(ExpositionStatus.PLANNING.toString(),
        MessageUtils.getMessage(expositionStatus.getMessageId())));
    // 进行中
    expositionStatus =
        FwCodeUtil.stringToEnum(ExpositionStatus.class, ExpositionStatus.PROGRESS.toString());
    result.add(new CodeValueRecord(ExpositionStatus.PROGRESS.toString(),
        MessageUtils.getMessage(expositionStatus.getMessageId())));
    // 已结束
    expositionStatus =
        FwCodeUtil.stringToEnum(ExpositionStatus.class, ExpositionStatus.OVER.toString());
    result.add(new CodeValueRecord(ExpositionStatus.OVER.toString(),
        MessageUtils.getMessage(expositionStatus.getMessageId())));
    return result;
  }

  /**
   * 展览状态区分值List取得。
   * 
   * @return 区分值List
   */
  public static List<CodeValueRecord> getVrTypeList(boolean blankFlag) {
    List<CodeValueRecord> result = Lists.newArrayList();
    if (blankFlag) {
      result.add(new CodeValueRecord("", "全部"));
    }
    result = Lists.newArrayList();
    result.add(new CodeValueRecord(FlagStatus.Disable.toString(), "非VR展"));
    result.add(new CodeValueRecord(FlagStatus.Enable.toString(), "VR展"));
    return result;
  }

  /**
   * 枚举型转List。
   * 
   * @return 区分值List
   */
  public static List<CodeValueRecord> getCodeMessageList(Class targetClass, boolean blankFlag) {
    List<CodeValueRecord> result = Lists.newArrayList();
    if (blankFlag) {
      result.add(new CodeValueRecord("", "全部"));
    }
    CodeModel[] list = FwCodeUtil.getCodeMessageIdList(targetClass);
    for (CodeModel codeModel : list) {
      result.add(new CodeValueRecord(codeModel.getCode(),
          MessageUtils.getMessage(codeModel.getMessageId())));
    }
    return result;
  }



  /**
   * 機能:ログインユーザが指定のロールを持つかどうか判定。 説明: 備考:
   * 
   * @param roleId ロールID @return 持つ：TRUE;持たない：FALSE @throws
   */
  public static boolean isLoginUserArchiveUser(String roleId) {
    boolean result = false;
    List<String> roleIds = UserSessionUtils.getUserRoleIds();
    if (null != roleIds && roleIds.size() > 0) {
      for (String s : roleIds) {
        // ロールの判定
        if (s.equals(roleId)) {
          return true;
        }
      }
    }
    return result;
  }



  /**
   * 拷贝素材文件到APP服务器。
   * 
   * @param expositionId 素材所属路径（公共素材，展览素材）
   */
  public static void copyMaterialFromStorageToApp(String expositionId) throws Exception {

    // 展览素材******
    String materialFolderPath = expositionId;

    String destAppMaterialRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W,
        UserSessionUtils.getSessionId(), PanoConstantsIF.MATERIAL_FOLDER_NAME + materialFolderPath);
    File destAppMaterialRelativeFile =
        new File(FwFileUtils.getAbsolutePath(destAppMaterialRelativePath));

    // 素材的storage路径
    String srcPublicMaterialPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W,
        "/" + PanoConstantsIF.MATERIAL_FOLDER_NAME + materialFolderPath);
    File srcPublicMaterialFile = new File(srcPublicMaterialPath);
    // 从Storage拷贝素材到APP服务器
    if (srcPublicMaterialFile.exists() && srcPublicMaterialFile.isDirectory()) {
      FileUtils.copyDirectory(srcPublicMaterialFile, destAppMaterialRelativeFile, true);
    }

    // 公共素材******
    // 定义素材保存时的文件夹路径
    materialFolderPath = "common_material";
    // 获取APP服务器侧素材文件目录。
    destAppMaterialRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W,
        UserSessionUtils.getSessionId(), PanoConstantsIF.MATERIAL_FOLDER_NAME + materialFolderPath);
    destAppMaterialRelativeFile =
        new File(FwFileUtils.getAbsolutePath(destAppMaterialRelativePath));
    // 素材的storage路径
    srcPublicMaterialPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W,
        "/" + PanoConstantsIF.MATERIAL_FOLDER_NAME + materialFolderPath);
    srcPublicMaterialFile = new File(srcPublicMaterialPath);
    // 从Storage拷贝素材到APP服务器
    if (srcPublicMaterialFile.exists() && srcPublicMaterialFile.isDirectory()) {
      FileUtils.copyDirectory(srcPublicMaterialFile, destAppMaterialRelativeFile, true);
    }
  }


  /**
   * 深度克隆，克隆的对象和原来是一个。
   * 
   * @param orig
   * @return
   */
  public static Object deepClone(Object orig) {

    Object obj = null;

    try {
      // Write the object out to a byte array 输出流 深度拷贝对象
      FastByteArrayOutputStream fbos = new FastByteArrayOutputStream();
      // 对象输出流
      ObjectOutputStream out = new ObjectOutputStream(fbos);
      // 把传进来的对象 写入流中，然后这个流是和拷贝对象的流 连接的 所以这个要拷贝的对象 已经交给这个对象
      out.writeObject(orig);
      out.flush();
      out.close();

      // Retrieve（检索，恢复） an input stream from the byte array and read
      // a copy of the object back in.
      // 从这个拷贝对象的流中 获取输入流
      ObjectInputStream in = new ObjectInputStream(fbos.getInputStream());
      // 从输入流中读出这个对象
      obj = in.readObject();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
    }
    // 返回拷贝的对象
    return obj;
  }



}

package cn.com.pano.pano.service.pano03;

import java.io.File;
import java.text.MessageFormat;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.code.MaterialType;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.form.pano03.Pano0301Form;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 素材登录操作。
 * 
 * @author tangzhenzong
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0301EntryService extends BaseService {

  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;

  /**
   * 登录素材。
   * 
   * @param inForm Pano0301Form
   */
  public EasyJson<Object> doInsertMaterial(Pano0301Form inForm) throws Exception {
    // 如果是声音文件
    PanoMaterial panoMaterial = new PanoMaterial();
    panoMaterial.materialPath = "";

    // 定义素材保存时的文件夹路径
    String materialFolderPath = "common_material";
    // 判断该素材是公共素材还是展览素材
    // 如果素材归属类型有选定，并为展览素材，则设展览文件夹路径为展览素材
    if (!ObjectUtils.isEmpty(inForm.materialBelongType)
        && PanoConstantsIF.MATERIAL_BELONGTYPE_EXPOSITION.equals(inForm.materialBelongType)) {
      materialFolderPath = inForm.expositionId;
    }
    if (MaterialType.SOUND.toString().equals(inForm.materialTypeId)) {
      // 获取APP服务器侧文件目录。
      String srcAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_SOUND,
          UserSessionUtils.getSessionId(),
          "pano0301/" + PanoConstantsIF.MATERIAL_FOLDER_NAME + materialFolderPath,
          inForm.materialId + "/");
      // storage路径
      String destPublicPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_SOUND,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + materialFolderPath, inForm.materialId + "/");
      File srcAppDirectory = new File(FwFileUtils.getAbsolutePath(srcAppRelativePath));
      if (srcAppDirectory.exists() && srcAppDirectory.listFiles().length > 0) {
        // 拷贝文件到publicStorage服务器
        FileUtils.copyDirectory(srcAppDirectory, new File(destPublicPath), true);
      }

      // 素材路径
      panoMaterial.materialPath = PanoConstantsIF.MATERIAL_FOLDER_NAME + materialFolderPath
          + "/sounds/" + inForm.materialId + "/" + inForm.materialId + "."
          + FilenameUtils.getExtension(srcAppDirectory.list()[0]);


      // 获取APP服务器侧文件目录。
      String destAppRelativePath = MessageFormat.format(
          PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_SOUND, UserSessionUtils.getSessionId(),
          PanoConstantsIF.MATERIAL_FOLDER_NAME + materialFolderPath, inForm.materialId + "/");
      // 拷贝素材到APP服务器
      FileUtils.copyDirectory(new File(destPublicPath),
          new File(FwFileUtils.getAbsolutePath(destAppRelativePath)), true);

    } else if (MaterialType.VIDEO.toString().equals(inForm.materialTypeId)) {
      // 获取APP服务器侧文件目录。
      String srcAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_VIDEO,
          UserSessionUtils.getSessionId(),
          "pano0301/" + PanoConstantsIF.MATERIAL_FOLDER_NAME + materialFolderPath,
          inForm.materialId + "/");
      // storage路径
      String destPublicPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_VIDEO,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + materialFolderPath, inForm.materialId + "/");
      File srcAppDirectory = new File(srcAppRelativePath);
      if (srcAppDirectory.exists() && srcAppDirectory.listFiles().length > 0) {
        // 拷贝文件到publicStorage服务器
        FileUtils.copyDirectory(srcAppDirectory, new File(destPublicPath), true);
      }

      // 素材路径
      panoMaterial.materialPath = PanoConstantsIF.MATERIAL_FOLDER_NAME + materialFolderPath
          + "/videos/" + inForm.materialId + "/" + inForm.materialId + "."
          + FilenameUtils.getExtension(srcAppDirectory.list()[0]);


      // 获取APP服务器侧文件目录。
      String destAppRelativePath = MessageFormat.format(
          PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_VIDEO, UserSessionUtils.getSessionId(),
          PanoConstantsIF.MATERIAL_FOLDER_NAME + materialFolderPath, inForm.materialId + "/");
      // 拷贝素材到APP服务器
      FileUtils.copyDirectory(new File(destPublicPath),
          new File(FwFileUtils.getAbsolutePath(destAppRelativePath)), true);

    } else if (MaterialType.FLOW_INFO_TEXT.toString().equals(inForm.materialTypeId)) {
      // 如果是文字浮动效果
      panoMaterial.flowTextInfo = inForm.textflowInfo;
      panoMaterial.materialPath = "dummy";
    } else { // 图片备份
      // 获取APP服务器侧文件目录。
      String srcAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
          UserSessionUtils.getSessionId(),
          "pano0301/" + PanoConstantsIF.MATERIAL_FOLDER_NAME + materialFolderPath,
          inForm.materialId + "/");
      // storage路径
      String destPublicPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + materialFolderPath, inForm.materialId + "/");
      File srcAppDirectory = new File(FwFileUtils.getAbsolutePath(srcAppRelativePath));
      if (srcAppDirectory.exists() && srcAppDirectory.listFiles().length > 0) {
        // 拷贝文件到publicStorage服务器
        FileUtils.copyDirectory(srcAppDirectory, new File(destPublicPath), true);
      }

      // 判断是否是gif图，如果是则再做拆分拼接操作
      File srcAppFile = srcAppDirectory.listFiles()[0];
      String srcAppFileName = srcAppFile.getName();
      if (srcAppFileName.endsWith(".gif")) {
        String gifPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + materialFolderPath,
            inForm.materialId + "/" + srcAppFileName);

        srcAppFileName = srcAppFileName.substring(0, srcAppFileName.lastIndexOf(".")) + ".png";
        String gifDestPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + materialFolderPath,
            inForm.materialId + "/" + srcAppFileName);
        // 返回生成小png图片的宽，高，帧数，延迟时间
        String[] result = FileServiceUtil.splitAndSaveGif(gifPath, gifDestPath);
        if (result.length == 4) {
          panoMaterial.gifWidth = result[0];
          panoMaterial.gifHeight = result[1];
          panoMaterial.gifFrameCount = result[2];
          panoMaterial.gifDelayTime = result[3];
        }
      }

      // 素材路径
      panoMaterial.materialPath = PanoConstantsIF.MATERIAL_FOLDER_NAME + materialFolderPath
          + "/images/" + inForm.materialId + "/" + inForm.materialId + "."
          + FilenameUtils.getExtension(srcAppDirectory.list()[0]);


      // 获取APP服务器侧文件目录。
      String destAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
          UserSessionUtils.getSessionId(),
          PanoConstantsIF.MATERIAL_FOLDER_NAME + materialFolderPath, inForm.materialId + "/");
      // 拷贝素材到APP服务器
      FileUtils.copyDirectory(new File(destPublicPath),
          new File(FwFileUtils.getAbsolutePath(destAppRelativePath)), true);

      // 图文的时候,保存文字信息
      if (PanoConstantsIF.MATERIAL_TYPE_IMAGE_TEXT.equals(inForm.materialTypeId)) {
        panoMaterial.textInfo = inForm.textInfo;
      }
    }

    panoMaterial.materialId = inForm.materialId;
    panoMaterial.expositionId = materialFolderPath;
    panoMaterial.materialTypeId = inForm.materialTypeId;
    panoMaterial.materialName = inForm.materialName;
    panoMaterial.notes = inForm.notes;
    createAudit(panoMaterial);
    panoMaterial01Mapper.insert(panoMaterial);

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("登录成功！");
    return easyJson;
  }

}

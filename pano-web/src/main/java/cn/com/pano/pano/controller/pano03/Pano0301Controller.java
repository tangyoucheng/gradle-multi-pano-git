package cn.com.pano.pano.controller.pano03;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.code.MaterialType;
import cn.com.pano.pano.dto.pano03.Pano0301Dto;
import cn.com.pano.pano.form.pano03.Pano0301Form;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.service.pano03.Pano0301EntryService;
import cn.com.pano.pano.service.pano03.Pano0301InitService;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseController;

/**
 * 场景上素材新规处理。
 * 
 * @author tangzhenzong
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0301")
public class Pano0301Controller extends BaseController {
  @Autowired
  public Pano0301EntryService pano0301EntryService;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;
  @Autowired
  public Pano0301InitService pano0301InitService;

  @ModelAttribute
  public Pano0301Form setPano0301Form(@ModelAttribute Pano0301Form inForm) {
    return inForm;
  }

  /**
   * 初期显示处理。
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(Pano0301Form inForm) throws Exception {
    pano0301InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano03/pano0301");
  }

  /**
   * 登录处理。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doEntry")
  public EasyJson<Object> doEntry(Pano0301Form inForm) throws Exception {
    return pano0301EntryService.doInsertMaterial(inForm);
  }

  /**
   * 文件上传。
   * 
   * @throws Exception 异常的场合
   * @return
   */
  @ResponseBody
  @PostMapping("/doUpload")
  public EasyJson<Object> doUpload(Pano0301Form inForm) throws Exception {

    EasyJson<Object> easyJson = new EasyJson<Object>();
    // 统一生设定为true（包含部分成功，全部失败，全部成功三种情况）
    easyJson.setSuccess(true);
    // 成功上传图片的保存路径
    List<Pano0301Dto> imagesPath = Lists.newArrayList();
    // 验证信息
    List<Object> checkInfos = Lists.newArrayList();

    MultipartFile[] files = inForm.getUploadFile();
    if (ObjectUtils.isEmpty(files)) {
      checkInfos.add("上传文件不能为空。");
      easyJson.setObj(checkInfos);
      return easyJson;
    }

    String commonFileIconPath =
        FwFileUtils.getAbsolutePath("static/platform/common/images/commonFileIcon.png");
    File commonFileIconFile = new File(commonFileIconPath);
    String newThumbFile = inForm.materialId + "_dummythumb.png";
    String newThumbFileRelativePath = null;

    // 上传文件
    for (int i = 0; i < files.length; i++) {
      // 图片类型正则表表达式验证
      long fileSize = files[i].getSize();
      double mb = 1024 * 1024;
      // 图片超过2M的场合
      if (fileSize / mb > 5) {
        checkInfos.add("照片[" + files[i].getOriginalFilename() + "]超过5M，请重新选择。");
        continue;
      }

      String materialFileName =
          inForm.materialId + "." + FilenameUtils.getExtension(files[i].getOriginalFilename());
      Pano0301Dto pano0301Dto = new Pano0301Dto();

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
        File srcAppDirectory =
            new File(FwFileUtils.getAbsolutePath(srcAppRelativePath + materialFileName));
        // 拷贝文件到APP服务器
        FileUtils.copyInputStreamToFile(files[i].getInputStream(), srcAppDirectory);
        // 拷贝公共文件图标到APP服务器
        newThumbFileRelativePath = srcAppRelativePath + newThumbFile;
        FileUtils.copyFile(commonFileIconFile,
            new File(FwFileUtils.getAbsolutePath(newThumbFileRelativePath)));
        
        pano0301Dto.uploadFile = srcAppRelativePath + materialFileName;
        pano0301Dto.thumbFile = newThumbFileRelativePath;
      } else if (MaterialType.VIDEO.toString().equals(inForm.materialTypeId)) {
        // 获取APP服务器侧文件目录。
        String srcAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_VIDEO,
            UserSessionUtils.getSessionId(),
            "pano0301/" + PanoConstantsIF.MATERIAL_FOLDER_NAME + materialFolderPath,
            inForm.materialId + "/");
        File srcAppDirectory =
            new File(FwFileUtils.getAbsolutePath(srcAppRelativePath + materialFileName));
        // 拷贝文件到APP服务器
        FileUtils.copyInputStreamToFile(files[i].getInputStream(), srcAppDirectory);
        // 拷贝公共文件图标到APP服务器
        newThumbFileRelativePath = srcAppRelativePath + newThumbFile;
        FileUtils.copyFile(commonFileIconFile,
            new File(FwFileUtils.getAbsolutePath(newThumbFileRelativePath)));
        
        pano0301Dto.uploadFile = srcAppRelativePath + materialFileName;
        pano0301Dto.thumbFile = newThumbFileRelativePath;
      } else if (MaterialType.FLOW_INFO_TEXT.toString().equals(inForm.materialTypeId)) {
        // 如果是文字浮动效果
        // panoMaterial.flowTextInfo = inForm.textflowInfo;
        // panoMaterial.materialPath = "dummy";
      } else { // 图片备份
        // 获取APP服务器侧文件目录。
        String srcAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
            UserSessionUtils.getSessionId(),
            "pano0301/" + PanoConstantsIF.MATERIAL_FOLDER_NAME + materialFolderPath,
            inForm.materialId + "/");
        File srcAppDirectory =
            new File(FwFileUtils.getAbsolutePath(srcAppRelativePath + materialFileName));
        // 拷贝文件到APP服务器
        FileUtils.copyInputStreamToFile(files[i].getInputStream(), srcAppDirectory);
        // 拷贝公共文件图标到APP服务器
        newThumbFileRelativePath = srcAppRelativePath + newThumbFile;
        FileUtils.copyFile(commonFileIconFile,
            new File(FwFileUtils.getAbsolutePath(newThumbFileRelativePath)));
        
        pano0301Dto.uploadFile = srcAppRelativePath + materialFileName;
        pano0301Dto.thumbFile = newThumbFileRelativePath;
      }

      imagesPath.add(pano0301Dto);
    }

    easyJson.setObj(checkInfos);
    easyJson.setRows(imagesPath);
    return easyJson;
  }

  /**
   * 文件删除。
   * 
   * @throws Exception 异常的场合
   * @return
   */
  @ResponseBody
  @PostMapping("/doDeleteFile")
  public EasyJson<Object> doDeleteFile(Pano0301Form inForm) throws SystemException {

    EasyJson<Object> easyJson = new EasyJson<Object>();


    if (!ObjectUtils.isEmpty(inForm.getDeleteFilePath())) {

      try {
        // 删除文件
        String deleteFilePath = FwFileUtils.getAbsolutePath(inForm.getDeleteFilePath());
        File deleteFile = new File(deleteFilePath);
        FileUtils.forceDelete(deleteFile.getParentFile());
      } catch (IOException e1) {
        // throw new SystemException(e1);
        easyJson.setSuccess(false);
        easyJson.setMsg("文件删除失败！");
        return easyJson;
      }
    } else {
      easyJson.setSuccess(false);
      easyJson.setMsg("文件删除失败！");
      return easyJson;
    }
    easyJson.setSuccess(true);
    easyJson.setMsg("文件删除成功！");
    return easyJson;
  }

  /**
   * 取得临时文件处理。
   * 
   * @return
   */
  // @ResponseBody
  // @RequestMapping("/doGetTempFile")
  // public EasyJson<Object> doGetTempFile(Pano0301Form inForm) throws Exception {
  // String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
  // inForm.expositionId, inForm.upload_key + "/");
  // String result =
  // FileServiceUtil.copyDirFromSessionStorageToAppServer(inForm.upload_key, _destPath);
  // EasyJson<Object> easyJson = new EasyJson<Object>();
  // easyJson.setObj(result);
  // return easyJson;
  // }

  /**
   * 删除临时文件处理。
   * 
   * @return
   */
  // @RequestMapping("/doDeleteTempFile")
  // public String doDeleteTempFile(Pano0301Form inForm) throws Exception {
  //
  // String filePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
  // inForm.expositionId, inForm.upload_key + "/");
  // FileUtils.deleteDirectory(new File(FwFileUtils.getAbsolutePath(filePath)));
  // return null;
  // }

  /**
   * 关联验证
   * 
   * @return
   */
  // public ActionMessages validateDoEntry() {
  // ActionMessages errors = new ActionMessages();
  // if (!CheckUtils.isEmpty(inForm.materialId)) {
  // // 检查场景上图片种类编号是否重复
  // PanoMaterial panoMaterial = panoMaterial01Mapper.findById(inForm.materialId);
  // if (icWMMaterial != null && inForm.materialId.equals(icWMMaterial.materialId)) {
  // errors.add("materialId", new ActionMessage("eqpt.errors.E0001", panoMaterial.materialId));
  // }
  //
  // if (!CheckUtils.isAlphaNum(inForm.materialId)
  // || !CheckUtils.isHalfWidth(inForm.materialId)) {
  // errors.add("materialId", new ActionMessage("framework.errors.E0003", "素材编号"));
  // }
  // }
  //
  // return errors;
  //
  // }

}

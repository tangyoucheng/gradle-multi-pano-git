package cn.com.pano.pano.controller.pano02;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.form.pano02.Pano0201Form;
import cn.com.pano.pano.service.pano02.Pano0201EntryService;
import cn.com.pano.pano.service.pano02.Pano0201InitService;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseController;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * 新建全景图首图。
 * 
 * @author ouyangzidu
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0201")
public class Pano0201Controller extends BaseController {
  @Autowired
  public Pano0201InitService pano0201InitService;
  @Autowired
  public Pano0201EntryService pano0201EntryService;

  @ModelAttribute
  public Pano0201Form setPano0201Form(@ModelAttribute Pano0201Form inForm) {
    return inForm;
  }

  /**
   * 初期显示处理。
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(Pano0201Form inForm) throws Exception {
    pano0201InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano02/pano0201");
  }

  /**
   * 全景图录入。
   * 
   * @throws Exception 异常的场合
   * @return
   */
  @ResponseBody
  @RequestMapping("/doEntry")
  public EasyJson<Object> doEntry(Pano0201Form inForm) throws Exception {
    return pano0201EntryService.doInsertFirstPano(inForm);
  }

  /**
   * 取得全景图临时文件处理。
   * 
   * @throws Exception 异常的场合
   * @return
   */
  @ResponseBody
  @RequestMapping("/doGetTempFile")
  public EasyJson<Object> doGetTempFile(Pano0201Form inForm) throws Exception {

    String destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_PANORAMA,
        inForm.expositionId, inForm.panoramaId + "/");
    String result = FileServiceUtil.saveTempPanoramaFileToAppServer(destPath, inForm.panoramaId);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 取得最新的音乐名称。
   * 
   * @throws Exception 异常的场合
   * @return
   */
  @ResponseBody
  @RequestMapping("/doRefreshSoundName")
  public EasyJson<Object> doRefreshSoundName(Pano0201Form inForm) throws Exception {
    String result = pano0201EntryService.doRefreshSoundName(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 场景图片上传。
   * 
   * @throws Exception 异常的场合
   * @return
   */
  @ResponseBody
  @PostMapping("/doUpload")
  public EasyJson<Object> doUpload(Pano0201Form inForm) throws SystemException {

    EasyJson<Object> easyJson = new EasyJson<Object>();
    // 统一生设定为true（包含部分成功，全部失败，全部成功三种情况）
    easyJson.setSuccess(true);
    // 成功上传图片的保存路径
    List<Object> imagesPath = Lists.newArrayList();
    // 验证信息
    List<Object> checkInfos = Lists.newArrayList();

    MultipartFile[] files = inForm.getPanoImg();
    if (ObjectUtils.isEmpty(files)) {
      checkInfos.add("上传图片不能为空。");
      easyJson.setObj(checkInfos);
      return easyJson;
    }
    // 上传文件
    for (int i = 0; i < files.length; i++) {
      // 图片类型正则表表达式验证
      String originalFilename = files[i].getOriginalFilename();
      String fileTypeReg = "(\\.)(jpg|jpeg)$";
      Pattern fileTypePattern = Pattern.compile(fileTypeReg);
      Matcher fileTypeMatcher = fileTypePattern.matcher(originalFilename);
      // 正则表达式验证不通过的场合
      if (!fileTypeMatcher.find()) {
        checkInfos.add("照片[" + files[i].getOriginalFilename() + "]的类型不正确，请重新选择。");
        continue;
      }
      long fileSize = files[i].getSize();
      double mb = 1024 * 1024;
      // 图片超过2M的场合
      if (fileSize / mb > 5) {
        checkInfos.add("照片[" + files[i].getOriginalFilename() + "]超过5M，请重新选择。");
        continue;
      }
      // 文件名后缀。
      String fileExtension = FilenameUtils.getExtension(originalFilename);
      // 图片id
      // String imgId = FwStringUtils.getUniqueId();
      // // 新文件名。
      // String newFileName = imgId + "." + fileExtension;
      // 获取APP服务器侧文件目录。
      String newFileRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          UserSessionUtils.getSessionId(), "pano0201/" + inForm.getExpositionId(),
          inForm.getPanoramaId() + "/" + PanoConstantsIF.VAL_PUBLIC_DIRECTORY_PANOS_L + "/"
              + originalFilename);
      String newFilePath = FwFileUtils.getAbsolutePath(newFileRelativePath);
      File newFile = new File(newFilePath);

      try {
        // 复制文件
        if (!newFile.exists()) {
          newFile.getParentFile().mkdirs();
        }
        BufferedImage bufferedImage = ImageIO.read(files[i].getInputStream());
        files[i].transferTo(newFile);


        // 缩略图做成
        // 新缩略文件。
        String newThumbFileRelativePath =
            MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
                UserSessionUtils.getSessionId(), "pano0201/" + inForm.getExpositionId(),
                inForm.getPanoramaId() + "/" + PanoConstantsIF.VAL_PUBLIC_DIRECTORY_PANOS_L + "/"
                    + FilenameUtils.getBaseName(originalFilename) + "-dummythumb" + "."
                    + fileExtension);
        String newThumbFilePath = FwFileUtils.getAbsolutePath(newThumbFileRelativePath);

        File newThumbFile = new File(newThumbFilePath);
        int imageThumbnailWidth =
            NumberUtils.parseNumber(PanoConstantsIF.PANO_IMAGE_THUMBNAIL_WIDTH, Integer.class);
        int imageThumbnailHeight =
            NumberUtils.parseNumber(PanoConstantsIF.PANO_IMAGE_THUMBNAIL_HEIGHT, Integer.class);
        Thumbnails.of(bufferedImage)
            .sourceRegion(Positions.CENTER, bufferedImage.getWidth(), bufferedImage.getHeight())
            .size(imageThumbnailWidth, imageThumbnailHeight).outputQuality(1.0)
            .toFile(newThumbFile);

        imagesPath.add(newFileRelativePath);
      } catch (Exception e1) {
        // 文件保存发生异常的场合
        // throw new SystemException(e1);
        checkInfos.add("照片[" + files[i].getOriginalFilename() + "]上传失败，请重新选择。");
      }
    }

    // 做成全景文件
    if (ObjectUtils.isEmpty(checkInfos)) {
      String xmlPath = FwFileUtils.getAbsolutePath(MessageFormat.format(
          PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA, UserSessionUtils.getSessionId(),
          "pano0201/" + inForm.getExpositionId(), inForm.getPanoramaId() + "/" + "show_l.xml"));

      String xmlTemplatePath = FwFileUtils.getAbsolutePath(PanoConstantsIF.TEMPLATE_SHOW_L);
      File xmlTemplateFile = new File(xmlTemplatePath);
      InputStream inputStream = null;
      OutputStream outputStream = null;
      try {
        inputStream = new FileInputStream(xmlTemplateFile);
        outputStream = new FileOutputStream(xmlPath);
        FileCopyUtils.copy(inputStream, outputStream);
      } catch (Exception e) {
        throw new SystemException(e);
      } finally {
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outputStream);
      }
    }

    easyJson.setObj(checkInfos);
    easyJson.setRows(imagesPath);
    return easyJson;
  }

  /**
   * 场景图片删除。
   * 
   * @throws Exception 异常的场合
   * @return
   */
  @ResponseBody
  @PostMapping("/doDeleteImage")
  public EasyJson<Object> doDeleteImage(Pano0201Form inForm) throws SystemException {

    EasyJson<Object> easyJson = new EasyJson<Object>();


    if (!ObjectUtils.isEmpty(inForm.getDeleteImgPath())) {

      try {
        // 删除图片
        String deleteFilePath = FwFileUtils.getAbsolutePath(inForm.getDeleteImgPath());
        File deleteFile = new File(deleteFilePath);
        FileUtils.forceDelete(deleteFile);
        // 删除缩略图
        String deleteFileBaseName = FilenameUtils.getBaseName(deleteFilePath);
        File deleteThumbFile = new File(
            deleteFilePath.replace(deleteFileBaseName, deleteFileBaseName + "-dummythumb"));
        FileUtils.forceDelete(deleteThumbFile);
      } catch (IOException e1) {
        // throw new SystemException(e1);
        easyJson.setSuccess(false);
        easyJson.setMsg("图片删除失败！");
        return easyJson;
      }
    } else {
      easyJson.setSuccess(false);
      easyJson.setMsg("图片删除失败！");
      return easyJson;
    }
    easyJson.setSuccess(true);
    easyJson.setMsg("图片删除成功！");
    return easyJson;
  }

}

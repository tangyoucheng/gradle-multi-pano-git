package cn.com.pano.pano.controller.pano03;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano03.Pano0306Form;
import cn.com.pano.pano.service.pano03.Pano0306InitService;
import cn.com.pano.pano.service.pano03.Pano0306UpdateService;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseController;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * 导航图编辑。
 * 
 * @author 唐友成
 * @date 2019-08-19
 *
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0306")
public class Pano0306Controller extends BaseController {
  @Autowired
  public Pano0306InitService pano0306InitService;
  @Autowired
  public Pano0306UpdateService pano0306UpdateService;

  @ModelAttribute
  public Pano0306Form setPano0306Form(@ModelAttribute Pano0306Form inForm) {
    return inForm;
  }

  /**
   * 初期显示处理。
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(Pano0306Form inForm) throws Exception {
    // 初期化
    pano0306InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano03/pano0306");
  }

  /**
   * 更新处理。
   * 
   * @param inForm Pano0306Form
   */
  @ResponseBody
  @RequestMapping("/doUpdate")
  public EasyJson<Object> doUpdate(Pano0306Form inForm) throws Exception {

    return pano0306UpdateService.doUpdataExpositionMap(inForm);
  }

  /**
   * 删除处理。
   * 
   * @param inForm Pano0306Form
   */
  @ResponseBody
  @RequestMapping("/doDelete")
  public EasyJson<Object> doDelete(Pano0306Form inForm) throws Exception {
    return pano0306UpdateService.doDeleteExpositionMap(inForm);
  }

  /**
   * 场景图片上传。
   * 
   * @throws Exception 异常的场合
   * @return
   */
  @ResponseBody
  @PostMapping("/doUpload")
  public EasyJson<Object> doUpload(Pano0306Form inForm) throws SystemException {

    EasyJson<Object> easyJson = new EasyJson<Object>();
    // 统一生设定为true（包含部分成功，全部失败，全部成功三种情况）
    easyJson.setSuccess(true);
    // 成功上传图片的保存路径
    List<Object> imagesPath = Lists.newArrayList();
    // 验证信息
    List<Object> checkInfos = Lists.newArrayList();

    MultipartFile[] files = inForm.getMapImg();
    if (ObjectUtils.isEmpty(files)) {
      checkInfos.add("上传图片不能为空。");
      easyJson.setObj(checkInfos);
      return easyJson;
    }
    // 上传文件
    for (int i = 0; i < files.length; i++) {
      // 图片类型正则表表达式验证
      String originalFilename = files[i].getOriginalFilename();
      String fileTypeReg = "(\\.)(jpg|jpeg|png)$";
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
      String newFileRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
          UserSessionUtils.getSessionId(), "pano0306/" + inForm.pano0306ExpositionId,
          inForm.pano0306ExpositionMapId + "/" + inForm.pano0306ExpositionMapId + "."
              + fileExtension);
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
        String newThumbFileRelativePath = MessageFormat.format(
            PanoConstantsIF.VAL_APP_SERVER_W_IMAGE, UserSessionUtils.getSessionId(),
            "pano0306/" + inForm.pano0306ExpositionId, inForm.pano0306ExpositionMapId + "/"
                + inForm.pano0306ExpositionMapId + "-dummythumb" + "." + fileExtension);
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
  public EasyJson<Object> doDeleteImage(Pano0306Form inForm) throws SystemException {

    EasyJson<Object> easyJson = new EasyJson<Object>();


    if (!ObjectUtils.isEmpty(inForm.getDeleteImgPath())) {

      try {
        // 删除文件
        String deleteFilePath = FwFileUtils.getAbsolutePath(inForm.getDeleteImgPath());
        File deleteFile = new File(deleteFilePath);
        FileUtils.forceDelete(deleteFile.getParentFile());
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

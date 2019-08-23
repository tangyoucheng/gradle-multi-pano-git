package cn.com.pano.pano.common.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.xmlbeans.impl.common.IOUtil;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties.Storage;
import org.springframework.stereotype.Service;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.util.GifDecoder;
import cn.com.platform.util.FwFileUtils;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * 共通処理。
 * 
 * @author kaima
 * @version 1.00
 * @since 1.00
 */
@Service
public class FileCommonService {

  /**
   * PublicStorageファイル保存
   * 
   * @param key キー
   * @param _destPath ファイルパス (sample: equipment/panorama/)
   * @return ファイルパス
   * @throws SystemException
   */
  public String saveImageToPublicStorage(String _key, String _destPath) throws SystemException {
    // try {
    //
    // Collection<SessionScopeStorage> fileList = getSessionScopeStorageFileList(_key);
    // if (CollectionUtil.isNotEmpty(fileList)) {
    //
    // Storage<PublicStorage> dir = new PublicStorage(_destPath);
    // dir.makeDirectories();
    // int fileIndex = 0;
    // String fileName = "";
    // // 文件保存
    // for (SessionScopeStorage file : fileList) {
    //
    // fileName = file.getName();
    // if (fileName.contains(".")) {
    // if (_key.contains("/")) {
    // String[] _keyDatas = _key.split("/");
    // fileName = _keyDatas[_keyDatas.length - 1] + fileIndex + "."
    // + fileName.split("\\.")[fileName.split("\\.").length - 1];
    // } else {
    // fileName =
    // _key + fileIndex + "." + fileName.split("\\.")[fileName.split("\\.").length - 1];
    // }
    // }
    //
    // String filePath = _destPath + fileName;
    //
    // Storage<PublicStorage> saveFile = new PublicStorage(filePath);
    //
    // InputStream inputStream = file.open();
    // OutputStream out = saveFile.create();
    //
    // try {
    // IOUtil.transfer(inputStream, out);
    //
    // } finally {
    // IOUtil.closeCloseableQuietly(out);
    // IOUtil.closeCloseableQuietly(inputStream);
    // }
    //
    // fileIndex++;
    // }
    //
    // // 删除临时图文件
    // clearSessionScopeStorage(_key);
    // return fileName;
    // }
    // } catch (IOException e) {
    // throw new SystemException("文件上传失败！");
    // }
    return "";

  }

  /**
   * SessionStorageからAPPサーバまでディレクトリをコピーする
   * 
   * @param _srcPath ファイルパス (sample: equipment/panorama/)
   * @param _destPath ファイルパス (sample: equipment/panorama/)
   * @return ファイルパス
   * @throws SystemException
   */
  public String copyDirFromSessionStorageToAppServer(String _key, String _destPath)
      throws SystemException {
    String _result = "";
    // try {
    //
    // Collection<SessionScopeStorage> fileList = getSessionScopeStorageFileList(_key);
    // if (CollectionUtil.isNotEmpty(fileList)) {
    //
    // int fileIndex = 0;
    // // 文件保存
    // for (SessionScopeStorage file : fileList) {
    //
    // String fileName = file.getName();
    // if (fileName.contains(".")) {
    // if (_key.contains("/")) {
    // String[] _keyDatas = _key.split("/");
    // fileName = _keyDatas[_keyDatas.length - 1] + fileIndex + "."
    // + fileName.split("\\.")[fileName.split("\\.").length - 1];
    // } else {
    // fileName =
    // _key + fileIndex + "." + fileName.split("\\.")[fileName.split("\\.").length - 1];
    // }
    // }
    //
    // String filePath = FileUtils.getAbsolutePath(_destPath + fileName);
    // // 出力先ファイル
    // File destFile_ = FileUtils.mkdirsParent(filePath);
    // InputStream inputStream = file.open();
    // OutputStream out = new FileOutputStream(destFile_);
    //
    // _result = _destPath + fileName;
    // try {
    // IOUtil.transfer(inputStream, out);
    // } finally {
    // IOUtil.closeCloseableQuietly(out);
    // IOUtil.closeCloseableQuietly(inputStream);
    // }
    //
    // fileIndex++;
    // }
    //
    // }
    // } catch (IOException e) {
    // throw new SystemException("文件上传失败！");
    // }
    return _result;
  }

  /**
   * PublicStorageからAPPサーバまでディレクトリをコピーする
   * 
   * @param _srcPath ファイルパス (sample: equipment/panorama/)
   * @param _destPath ファイルパス (sample: equipment/panorama/)
   * @return ファイルパス
   * @throws SystemException
   */
  public boolean copyDirFromPublicStorageToAppServer(String _srcPath, String _destPath)
      throws SystemException {
    boolean _result = false;
    // try {
    // Collection<PublicStorage> fileList = new PublicStorage(_srcPath).listStorages();
    //
    // if (CollectionUtil.isNotEmpty(fileList)) {
    // // 全景图的图片保存
    // for (PublicStorage file : fileList) {
    // if (file.isFile()) {
    //
    // String filePath = FileUtils.getAbsolutePath(_destPath + file.getName());
    // // 出力先ファイル
    // File destFile_ = FileUtils.mkdirsParent(filePath);
    // InputStream inputStream = file.open();
    // OutputStream out = new FileOutputStream(destFile_);
    // try {
    // IOUtil.transfer(inputStream, out);
    // } finally {
    // IOUtil.closeCloseableQuietly(out);
    // IOUtil.closeCloseableQuietly(inputStream);
    // }
    //
    // }
    // if (file.isDirectory()) {
    // copyDirFromPublicStorageToAppServer(_srcPath + "/" + file.getName() + "/",
    // _destPath + "/" + file.getName() + "/");
    // }
    //
    // }
    // _result = true;
    // }
    // } catch (IOException e) {
    // throw new SystemException("获取文件失败！");
    // }
    return _result;

  }

  /**
   * createPanoramaThumbImage
   * 
   * @param _srcPath ファイルパス (sample: equipment/panorama/)
   * @param _destPath ファイルパス (sample: equipment/panorama/)
   * @return ファイルパス
   * @throws SystemException
   */
  public boolean createPanoramaThumbImageToAppServer(String _srcPath, String _destPath)
      throws SystemException {
    boolean _result = false;
    // try {
    // Collection<PublicStorage> fileList = new PublicStorage(_srcPath).listStorages();
    //
    // if (CollectionUtil.isNotEmpty(fileList)) {
    // // 为每个场景生成缩略图
    // for (PublicStorage file : fileList) {
    // if (file.isFile()) {
    // String filePath = FileUtils.getAbsolutePath(_destPath + file.getName());
    // // 出力先ファイル
    // File destFile_ = FileUtils.mkdirsParent(filePath);
    // InputStream inputStream = file.open();
    // OutputStream out = new FileOutputStream(destFile_);
    // try {
    // IOUtil.transfer(inputStream, out);
    // } finally {
    // IOUtil.closeCloseableQuietly(out);
    // IOUtil.closeCloseableQuietly(inputStream);
    // }
    // // 生成缩略图
    // String panoramaFileName = file.getName();
    // String _position = panoramaFileName.substring(panoramaFileName.lastIndexOf(".") - 1,
    // panoramaFileName.lastIndexOf("."));
    // if (CheckUtils.isEqual("f", _position.toLowerCase())) {
    //
    // try {
    // inputStream = file.open();
    // BufferedImage bufferedImage = ImageIO.read(inputStream);
    // int squareHeight = Math.min(bufferedImage.getHeight(), bufferedImage.getWidth());
    // int imageWidth = 80;
    // int imageHeight = 80;
    // File destThumbFile_ =
    // FileUtils.mkdirsParent(destFile_.getParentFile().getParent() + "/thumb.jpg");
    // Thumbnails.of(bufferedImage)
    // .sourceRegion(Positions.CENTER, squareHeight, squareHeight)
    // .size(imageWidth, imageHeight).toFile(destThumbFile_);
    // } finally {
    // IOUtil.closeCloseableQuietly(inputStream);
    // }
    // }
    // }
    // if (file.isDirectory()) {
    // copyDirFromPublicStorageToAppServer(_srcPath + "/" + file.getName() + "/",
    // _destPath + "/" + file.getName() + "/");
    // }
    //
    // }
    //
    // _result = true;
    // }
    // } catch (IOException e) {
    // throw new SystemException("获取文件失败！");
    // }
    return _result;

  }

  /**
   * PublicStorage下转移文件
   * 
   * @param _srcPath ファイルパス (sample: equipment/panorama/)
   * @param _destPath ファイルパス (sample: equipment/panorama/)
   * @return 結果
   * @throws SystemException
   */
  public boolean copyDirInPublicStorage(String _srcPath, String _destPath) throws SystemException {
    boolean returnfilePath = false;
    // try {
    //
    // Collection<PublicStorage> fileList = new PublicStorage(_srcPath).listStorages();
    // if (CollectionUtil.isNotEmpty(fileList)) {
    //
    // Storage<PublicStorage> destDir = new PublicStorage(_destPath);
    // destDir.makeDirectories();
    //
    // // 文件保存
    // for (PublicStorage file : fileList) {
    //
    // if (file.isFile()) {
    // Storage<PublicStorage> saveFile = new PublicStorage(_destPath + file.getName());
    //
    // InputStream inputStream = file.open();
    // OutputStream out = saveFile.create();
    // try {
    // IOUtil.transfer(inputStream, out);
    // } finally {
    // IOUtil.closeCloseableQuietly(out);
    // IOUtil.closeCloseableQuietly(inputStream);
    // }
    // }
    // if (file.isDirectory()) {
    // copyDirInPublicStorage(_srcPath + "/" + file.getName() + "/",
    // _destPath + "/" + file.getName() + "/");
    // }
    // }
    // returnfilePath = true;
    // }
    //
    //
    // } catch (IOException e) {
    // throw new SystemException("文件上传失败！");
    // }
    return returnfilePath;

  }

  /**
   * 保存全景图文件到PublicStorage。
   * 
   * @param srcPath ファイルパス (sample: equipment/panorama/)
   * @param destPath ファイルパス (sample: equipment/panorama/)
   * @return ファイルパス
   * @throws SystemException
   */
  public String savePanoramaFileToPublicStorage(String srcPath, String destPath)
      throws SystemException {
    String returnfilePath = "";
    try {
      Collection<File> fileList =
          FileUtils.listFiles(new File(FwFileUtils.getAbsolutePath(srcPath)), null, false);
      if (ObjectUtils.isNotEmpty(fileList)) {

        // publicStorage文件夹做成
        File destFolderPath = new File(FwFileUtils.getAbsolutePath(destPath));
        destFolderPath.mkdirs();

        int width = 256; // 图片宽度
        int height = 256; // 图片高度
        int mobileWidth = 1024; // 手机端图片宽度
        int mobileHeight = 1024; // 手机端图片高度
        int[] imageArrayLeft = new int[width * height];
        int[] imageArrayFront = new int[width * height];
        int[] imageArrayRight = new int[width * height];
        int[] imageArrayBack = new int[width * height];
        int[] imageArrayUp = new int[width * height];
        int[] imageArrayDown = new int[width * height];

        // 全景图的图片保存
        for (File file : fileList) {

          // 跳过展示用的图片缩略图
          if (file.getName().toLowerCase().contains("dummythumb")) {
            FileUtils.forceDelete(file);
            continue;
          }

          String panoramaFileName = file.getName();
          String position = panoramaFileName.substring(panoramaFileName.lastIndexOf(".") - 1,
              panoramaFileName.lastIndexOf("."));
          panoramaFileName = "sphere_" + position + ".jpg";
          String filePath =
              destPath + PanoConstantsIF.VAL_PUBLIC_DIRECTORY_PANOS_L + "/" + panoramaFileName;
          // 拷贝文件到publicStorage
          FileUtils.copyFile(file, new File(filePath));

          // 生成缩略图
          if (Objects.equals("f", position.toLowerCase())) {

            // inputStream = file.open();
            BufferedImage bufferedImage = ImageIO.read(file);
            int squareHeight = Math.min(bufferedImage.getHeight(), bufferedImage.getWidth());
            int imageWidth = 80;
            int imageHeight = 80;

            BufferedImage imageNew =
                new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
            imageNew = Thumbnails.of(bufferedImage)
                .sourceRegion(Positions.CENTER, squareHeight, squareHeight)
                .size(imageWidth, imageHeight).asBufferedImage();

            // 拷贝文件到publicStorage
            FileUtils.copyInputStreamToFile(getImageStream(imageNew, "jpg"),
                new File(destPath + "thumb.jpg"));

          }

          // 生成预览图
          if ("l".equals(position)) {
            // 读取第一张图片
            BufferedImage imageLeft = ImageIO.read(file);
            BufferedImage imageNew =
                new BufferedImage(mobileWidth, mobileHeight, BufferedImage.TYPE_INT_RGB);
            imageNew = Thumbnails.of(imageLeft).size(mobileWidth, mobileHeight).asBufferedImage();

            // 拷贝文件到publicStorage
            FileUtils.copyInputStreamToFile(getImageStream(imageNew, "jpg"), new File(
                destPath + PanoConstantsIF.VAL_PUBLIC_DIRECTORY_PANOS_L + "/mobile_l.jpg"));

            imageLeft = Thumbnails.of(imageLeft).size(width, height).asBufferedImage();
            // 从图片中读取RGB
            imageArrayLeft = imageLeft.getRGB(0, 0, width, height, imageArrayLeft, 0, width);
          }
          if ("f".equals(position)) {
            // 读取第二张图片
            BufferedImage imageFront = ImageIO.read(file);
            BufferedImage imageNew =
                new BufferedImage(mobileWidth, mobileHeight, BufferedImage.TYPE_INT_RGB);
            imageNew = Thumbnails.of(imageFront).size(mobileWidth, mobileHeight).asBufferedImage();

            // 拷贝文件到publicStorage
            FileUtils.copyInputStreamToFile(getImageStream(imageNew, "jpg"), new File(
                destPath + PanoConstantsIF.VAL_PUBLIC_DIRECTORY_PANOS_L + "/mobile_f.jpg"));

            imageFront = Thumbnails.of(imageFront).size(width, height).asBufferedImage();
            imageArrayFront = imageFront.getRGB(0, 0, width, height, imageArrayFront, 0, width);
          }
          if ("r".equals(position)) {
            // 读取第三张图片
            BufferedImage imageRight = ImageIO.read(file);
            BufferedImage imageNew =
                new BufferedImage(mobileWidth, mobileHeight, BufferedImage.TYPE_INT_RGB);
            imageNew = Thumbnails.of(imageRight).size(mobileWidth, mobileHeight).asBufferedImage();

            // 拷贝文件到publicStorage
            FileUtils.copyInputStreamToFile(getImageStream(imageNew, "jpg"), new File(
                destPath + PanoConstantsIF.VAL_PUBLIC_DIRECTORY_PANOS_L + "/mobile_r.jpg"));

            imageRight = Thumbnails.of(imageRight).size(width, height).asBufferedImage();
            imageArrayRight = imageRight.getRGB(0, 0, width, height, imageArrayRight, 0, width);
          }
          if ("b".equals(position)) {
            // 对第四张图片做相同的处理
            BufferedImage imageBack = ImageIO.read(file);
            BufferedImage imageNew =
                new BufferedImage(mobileWidth, mobileHeight, BufferedImage.TYPE_INT_RGB);
            imageNew = Thumbnails.of(imageBack).size(mobileWidth, mobileHeight).asBufferedImage();

            // 拷贝文件到publicStorage
            FileUtils.copyInputStreamToFile(getImageStream(imageNew, "jpg"), new File(
                destPath + PanoConstantsIF.VAL_PUBLIC_DIRECTORY_PANOS_L + "/mobile_b.jpg"));

            imageBack = Thumbnails.of(imageBack).size(width, height).asBufferedImage();
            imageArrayBack = imageBack.getRGB(0, 0, width, height, imageArrayBack, 0, width);
          }
          if ("u".equals(position)) {
            // 对第五张图片做相同的处理
            BufferedImage imageUp = ImageIO.read(file);
            BufferedImage imageNew =
                new BufferedImage(mobileWidth, mobileHeight, BufferedImage.TYPE_INT_RGB);
            imageNew = Thumbnails.of(imageUp).size(mobileWidth, mobileHeight).asBufferedImage();

            // 拷贝文件到publicStorage
            FileUtils.copyInputStreamToFile(getImageStream(imageNew, "jpg"), new File(
                destPath + PanoConstantsIF.VAL_PUBLIC_DIRECTORY_PANOS_L + "/mobile_u.jpg"));

            imageUp = Thumbnails.of(imageUp).size(width, height).asBufferedImage();
            imageArrayUp = imageUp.getRGB(0, 0, width, height, imageArrayUp, 0, width);
          }
          if ("d".equals(position)) {
            // 对第六张图片做相同的处理
            BufferedImage imageDown = ImageIO.read(file);
            BufferedImage imageNew =
                new BufferedImage(mobileWidth, mobileHeight, BufferedImage.TYPE_INT_RGB);
            imageNew = Thumbnails.of(imageDown).size(mobileWidth, mobileHeight).asBufferedImage();

            // 拷贝文件到publicStorage
            FileUtils.copyInputStreamToFile(getImageStream(imageNew, "jpg"), new File(
                destPath + PanoConstantsIF.VAL_PUBLIC_DIRECTORY_PANOS_L + "/mobile_d.jpg"));

            imageDown = Thumbnails.of(imageDown).size(width, height).asBufferedImage();
            imageArrayDown = imageDown.getRGB(0, 0, width, height, imageArrayDown, 0, width);
          }

        }
        // 生成新图片
        BufferedImage imageNew = new BufferedImage(width, height * 6, BufferedImage.TYPE_INT_RGB);
        imageNew.setRGB(0, 0, width, height, imageArrayLeft, 0, width);
        imageNew.setRGB(0, height, width, height, imageArrayFront, 0, width);
        imageNew.setRGB(0, height * 2, width, height, imageArrayRight, 0, width);
        imageNew.setRGB(0, height * 3, width, height, imageArrayBack, 0, width);
        imageNew.setRGB(0, height * 4, width, height, imageArrayUp, 0, width);
        imageNew.setRGB(0, height * 5, width, height, imageArrayDown, 0, width);

        imageNew = Thumbnails.of(imageNew).size(256, 256 * 6).asBufferedImage();

        // 拷贝文件到publicStorage
        FileUtils.copyInputStreamToFile(getImageStream(imageNew, "jpg"),
            new File(destPath + "preview.jpg"));

        // 全景图的XML做成
        String xmlPath = FwFileUtils.getAbsolutePath(PanoConstantsIF.TEMPLATE_SHOW_L);
        File xmlFile = new File(xmlPath);
        // 拷贝文件到publicStorage
        FileUtils.copyFile(xmlFile, new File(destPath + xmlFile.getName()));

        // 音频热点popup_show.xml做成
        String popupXmlPath =
            FwFileUtils.getAbsolutePath("static/pano/pano/common/template/popup_show.xml");
        File popupXmlFile = new File(popupXmlPath);
        // 拷贝文件到publicStorage
        FileUtils.copyFile(popupXmlFile, new File(destPath + popupXmlFile.getName()));

        // returnfilePath = _destPath + "show/show.xml";
      }
      // 将左眼文件内容复制到右眼中
      FileUtils.copyDirectory(new File(destPath + PanoConstantsIF.VAL_PUBLIC_DIRECTORY_PANOS_L),
          new File(destPath + PanoConstantsIF.VAL_PUBLIC_DIRECTORY_PANOS_R));

      // 做成右眼的show_r.xml
      String xmlPath = FwFileUtils.getAbsolutePath(PanoConstantsIF.TEMPLATE_SHOW_R);
      File xmlFile = new File(xmlPath);
      // 拷贝文件到publicStorage
      FileUtils.copyFile(xmlFile, new File(destPath + xmlFile.getName()));

    } catch (IOException e) {
      throw new SystemException("文件上传失败！");
    }

    return returnfilePath;
  }

  /**
   * get file's InputStream。
   * 
   * @param bi BufferedImage
   * @param imageType 传入文件后缀
   * @return
   */
  public InputStream getImageStream(BufferedImage bi, String imageType) {
    InputStream is = null;
    bi.flush();
    ByteArrayOutputStream bs = new ByteArrayOutputStream();
    ImageOutputStream imOut;
    try {
      imOut = ImageIO.createImageOutputStream(bs);
      ImageIO.write(bi, imageType, imOut);
      is = new ByteArrayInputStream(bs.toByteArray());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return is;
  }


  /**
   * 保存全景临时文件到应用服务器
   * 
   * @param _destPath ファイルパス (sample: equipment/panorama/)
   * @param key キー
   * @return ファイルパス
   * @throws SystemException
   */
  public String saveTempPanoramaFileToAppServer(String _destPath, String _key)
      throws SystemException {
    String result = "";
    // try {
    //
    // Collection<SessionScopeStorage> fileList = getSessionScopeStorageFileList(_key);
    // if (CollectionUtil.isNotEmpty(fileList)) {
    //
    // // 全景图的图片保存
    // for (SessionScopeStorage file : fileList) {
    //
    // String panoramaFileName = file.getName();
    // String _position = panoramaFileName.substring(panoramaFileName.lastIndexOf(".") - 1,
    // panoramaFileName.lastIndexOf("."));
    // panoramaFileName = "sphere_" + _position + ".jpg";
    // String filePath = FileUtils.getAbsolutePath(
    // _destPath + FrameworkConstants.VAL_PUBLIC_DIRECTORY_PANOS + panoramaFileName);
    // // 出力先ファイル
    // File destFile_ = FileUtils.mkdirsParent(filePath);
    //
    // InputStream inputStream = file.open();
    // OutputStream out = new FileOutputStream(destFile_);
    // try {
    // IOUtil.transfer(inputStream, out);
    // } finally {
    // IOUtil.closeCloseableQuietly(out);
    // IOUtil.closeCloseableQuietly(inputStream);
    // }
    // }
    // String filePath = FileUtils.getAbsolutePath(_destPath + "show_l.xml");
    //
    // String xmlPath = FileUtils.getAbsolutePath("framework/panorama/template/show_l.xml");
    // File XmlFile = new File(xmlPath);
    //
    // InputStream inputStream = new FileInputStream(XmlFile);
    // OutputStream out = new FileOutputStream(filePath);
    // try {
    // IOUtil.transfer(new FileInputStream(XmlFile), out);
    // } finally {
    // IOUtil.closeCloseableQuietly(out);
    // IOUtil.closeCloseableQuietly(inputStream);
    // }
    //
    // result = _destPath + "show_l.xml";
    // }
    //
    // } catch (IOException e) {
    // throw new SystemException("文件上传失败！");
    // }
    return result;
  }

  /**
   * 从PublicStorage取得全景图文件
   * 
   * @param _srcPath 元ファイルパス (sample: equipment/file/)
   * @param _destPath ファイルパス (sample: equipment/file/)
   * @param _key キー (sample: test/test0)
   * @return ファイルパス
   * @throws SystemException
   */
  public boolean getPanoramaFileFromPublicStorage(String _srcPath, String _destPath)
      throws SystemException {
    boolean _result = false;
    // try {
    // Collection<PublicStorage> fileList = new PublicStorage(_srcPath).filesStorages();
    //
    // if (CollectionUtil.isNotEmpty(fileList)) {
    // // 旧文件删除
    // // FileUtils.deleteFolder(FileUtils.getAbsolutePath(_destPath));
    // // 全景图的图片保存
    // for (PublicStorage file : fileList) {
    //
    // String filePath = FileUtils.getAbsolutePath(_destPath + file.getName());
    //
    // // 出力先ファイル
    // File destFile_ = FileUtils.mkdirsParent(filePath);
    //
    // InputStream inputStream = file.open();
    // OutputStream out = new FileOutputStream(destFile_);
    // try {
    // IOUtil.transfer(inputStream, out);
    // } finally {
    // IOUtil.closeCloseableQuietly(out);
    // IOUtil.closeCloseableQuietly(inputStream);
    // }
    // }
    //
    // fileList = new PublicStorage(_srcPath + FrameworkConstants.VAL_PUBLIC_DIRECTORY_PANOS)
    // .filesStorages();
    //
    // if (CollectionUtil.isNotEmpty(fileList)) {
    // // 全景图的图片保存
    // for (PublicStorage file : fileList) {
    //
    // String filePath = FileUtils.getAbsolutePath(
    // _destPath + FrameworkConstants.VAL_PUBLIC_DIRECTORY_PANOS + file.getName());
    //
    // // 出力先ファイル
    // File destFile_ = FileUtils.mkdirsParent(filePath);
    //
    // InputStream inputStream = file.open();
    // OutputStream out = new FileOutputStream(destFile_);
    // try {
    // IOUtil.transfer(inputStream, out);
    // } finally {
    // IOUtil.closeCloseableQuietly(out);
    // IOUtil.closeCloseableQuietly(inputStream);
    // }
    // }
    // }
    // _result = true;
    // }
    // } catch (IOException e) {
    // throw new SystemException("获取全景图文件失败！");
    // }
    return _result;
  }


  /**
   * SessionScopeStorageクリア
   * 
   * @param key キー
   * @throws SystemException
   */
  public void clearSessionScopeStorage(String key) throws SystemException {

    // try {
    //
    // Storage<SessionScopeStorage> dir = new SessionScopeStorage(
    // PanoCommonUtil.getLoginAccountContext().getTenantId() + "/" + key + "/");
    // if (!dir.remove(true)) {
    // throw new SystemException("文件删除失败！");
    // }
    //
    // } catch (IOException e) {
    // throw new SystemException("文件删除失败！");
    // }
  }

  /**
   * PublicStorageクリア
   * 
   * @param key キー
   * @throws SystemException
   */
  public void deletePublicStorageFolder(String _srcPath) throws SystemException {

    // try {
    // Storage<PublicStorage> dir = new PublicStorage(_srcPath + "/");
    // if (dir.exists() && !dir.remove(true)) {
    // throw new SystemException("文件删除失败！");
    // }
    //
    // } catch (IOException e) {
    // throw new SystemException("文件删除失败！");
    // }
  }

  /**
   * AppServer内转移文件
   * 
   * @param _srcPath
   * @param _destPath
   * @return
   * @throws Exception
   */
  public boolean copyDirInAppServer(String _srcPath, String _destPath) throws Exception {
    boolean returnfilePath = false;
    // try {
    // String[] srcFileList = new File(FileUtils.getAbsolutePath(_srcPath)).list();
    // if (srcFileList != null && srcFileList.length > 0) {
    // // 文件保存
    // for (String srcFileName : srcFileList) {
    //
    // File srcFile = new File(FileUtils.getAbsolutePath(_srcPath) + "/" + srcFileName);
    //
    // if (srcFile.isFile()) {
    // String destFilePath = FileUtils.getAbsolutePath(_destPath, srcFileName);
    // File saveFile = FileUtils.mkdirsParent(destFilePath);
    // InputStream inputStream = new FileInputStream(srcFile);
    // OutputStream out = new FileOutputStream(saveFile);
    // try {
    // IOUtil.transfer(inputStream, out);
    // } finally {
    // IOUtil.closeCloseableQuietly(out);
    // IOUtil.closeCloseableQuietly(inputStream);
    // }
    // }
    // if (srcFile.isDirectory()) {
    // copyDirInAppServer(_srcPath + "/" + srcFileName + "/",
    // _destPath + "/" + srcFileName + "/");
    // }
    // }
    // returnfilePath = true;
    // }
    // } catch (IOException e) {
    // throw new SystemException("文件拷贝失败！");
    // }
    return returnfilePath;
  }

  /**
   * 把gif图片按帧拆分成jpg图片
   * 
   * @param gifFileFullPath String 小gif图片(路径+名称)
   * @param destPngFile String 生成小png图片的路径(路径+名称)
   * @return String[] 返回生成小png图片的宽，高，帧数，延迟时间
   */
  public String[] splitGif(String gifFileFullPath, String destPngFile) throws IOException {

    String[] frameInfo = new String[] {};

    // 获取publicStorage文件
    // File gifFile = new File(FwFileUtils.getAbsolutePath(gifFileFullPath));
    String gifFileFullName = FwFileUtils.getAbsolutePath(gifFileFullPath);

    GifDecoder decoder = new GifDecoder();
    // 读取文件
    decoder.read(gifFileFullName);
    // 得到frame的个数
    int num = decoder.getFrameCount();
    // 图片帧数小于一帧，不做拆分存图操作
    if (num <= 1) {
      return frameInfo;
    }
    // 得到延迟时间
    int delay = decoder.getDelay(0);

    // 取得gif图片的分辨率
    BufferedImage sourceImage = ImageIO.read(new File(gifFileFullName));
    int width = sourceImage.getWidth();
    int height = sourceImage.getHeight();
    int[] imageArray = new int[width * height];

    // 生成新图片,ARGB用于生成背景透明的gif，非透明也可以使用
    BufferedImage imageNew = new BufferedImage(width, height * num, BufferedImage.TYPE_INT_ARGB);

    for (int i = 0; i < num; i++) {
      // 得到帧
      BufferedImage frame = decoder.getFrame(i);
      imageArray = frame.getRGB(0, 0, width, height, imageArray, 0, width);
      imageNew.setRGB(0, height * i, width, height, imageArray, 0, width);
    }

    String destPngFileFullName = FwFileUtils.getAbsolutePath(destPngFile);

    FileUtils.copyInputStreamToFile(getImageStream(imageNew, "png"), new File(destPngFileFullName));

    frameInfo = new String[] {Objects.toString(width), Objects.toString(height),
        Objects.toString(num - 1), Objects.toString(delay * 0.001)};
    return frameInfo;
  }
}

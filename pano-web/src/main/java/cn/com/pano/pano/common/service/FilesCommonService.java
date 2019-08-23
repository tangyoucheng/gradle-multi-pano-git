package cn.com.pano.pano.common.service;

import java.io.IOException;
import cn.com.platform.framework.common.exception.SystemException;

/**
 * 共通処理。
 * 
 * @author kaima
 * @version 1.00
 * @since 1.00
 */
public class FilesCommonService {


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
//    try {
//      Collection<PublicStorage> fileList = new PublicStorage(_srcPath).listStorages();
//
//      if (CollectionUtil.isNotEmpty(fileList)) {
//        // 全景图的图片保存
//        for (PublicStorage file : fileList) {
//          if (file.isFile()) {
//
//            String filePath = FileUtils.getAbsolutePath(_destPath + file.getName());
//            // 出力先ファイル
//            File destFile_ = FileUtils.mkdirsParent(filePath);
//            InputStream inputStream = file.open();
//            OutputStream out = new FileOutputStream(destFile_);
//            try {
//              IOUtil.transfer(inputStream, out);
//            } finally {
//              IOUtil.closeCloseableQuietly(out);
//              IOUtil.closeCloseableQuietly(inputStream);
//            }
//
//          }
//          if (file.isDirectory()) {
//            copyDirFromPublicStorageToAppServer(_srcPath + "/" + file.getName() + "/",
//                _destPath + "/" + file.getName() + "/");
//          }
//
//        }
//        _result = true;
//      }
//    } catch (IOException e) {
//      throw new SystemException("获取文件失败！");
//    }
    return _result;

  }


  /**
   * 保存全景图文件到PublicStorage
   * 
   * @param _destPath ファイルパス (sample: equipment/panorama/)
   * @param key キー (sample: test/test0)
   * @return ファイルパス
   * @throws SystemException
   */
  public String savePanoramaFileToPublicStorage(String _destPath, String _key, String subFolderName,
      String index) throws SystemException {
    String returnfilePath = "";
    boolean flg = false;
//    try {
//
//      Collection<SessionScopeStorage> fileList =
//          getSessionScopeStorageFileList(_key, subFolderName);
//      if (CollectionUtil.isNotEmpty(fileList)) {
//
//        Storage<PublicStorage> dir = new PublicStorage(_destPath + "/" + subFolderName + "/");
//        dir.makeDirectories();
//
//        InputStream fileLeft = null;
//        InputStream fileFront = null;
//        InputStream fileRight = null;
//        InputStream fileBack = null;
//        InputStream fileUp = null;
//        InputStream fileDown = null;
//        int width = 256; // 图片宽度
//        int height = 256; // 图片高度
//        int mobileWidth = 1024; // 手机端图片宽度
//        int mobileHeight = 1024; // 手机端图片高度
//        int[] imageArrayLeft = new int[width * height];
//        int[] imageArrayFront = new int[width * height];
//        int[] imageArrayRight = new int[width * height];
//        int[] imageArrayBack = new int[width * height];
//        int[] imageArrayUp = new int[width * height];
//        int[] imageArrayDown = new int[width * height];
//
//        // 全景图的图片保存
//        for (SessionScopeStorage file : fileList) {
//
//          String panoramaFileName = file.getName();
//          String _position = panoramaFileName.substring(panoramaFileName.lastIndexOf(".") - 1,
//              panoramaFileName.lastIndexOf("."));
//          panoramaFileName = "sphere_" + _position + ".jpg";
//          String filePath = _destPath + "/" + subFolderName + "/" + panoramaFileName;
//
//          Storage<PublicStorage> saveFile = new PublicStorage(filePath);
//
//          InputStream inputStream = file.open();
//          OutputStream out = saveFile.create();
//          try {
//            IOUtil.transfer(inputStream, out);
//          } finally {
//            IOUtil.closeCloseableQuietly(out);
//            IOUtil.closeCloseableQuietly(inputStream);
//          }
//
//          // 生成缩略图
//          if (CheckUtils.isEqual("f", _position.toLowerCase())) {
//
//            try {
//              inputStream = file.open();
//              BufferedImage bufferedImage = ImageIO.read(inputStream);
//              int squareHeight = Math.min(bufferedImage.getHeight(), bufferedImage.getWidth());
//              int imageWidth = 80;
//              int imageHeight = 80;
//
//              Storage<PublicStorage> saveLeftFile = new PublicStorage(_destPath + "/thumb.jpg");
//              OutputStream leftFileOut = saveLeftFile.create();
//              try {
//                BufferedImage imageNew =
//                    new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
//                imageNew = Thumbnails.of(bufferedImage)
//                    .sourceRegion(Positions.CENTER, squareHeight, squareHeight)
//                    .size(imageWidth, imageHeight).asBufferedImage();
//                IOUtil.transfer(getImageStream(imageNew, "jpg"), leftFileOut);
//              } finally {
//                IOUtil.closeCloseableQuietly(leftFileOut);
//              }
//
//            } finally {
//              IOUtil.closeCloseableQuietly(inputStream);
//            }
//          }
//          // 生成预览图
//          if ("l".equals(_position)) {
//            // 读取第一张图片
//            fileLeft = file.open();
//            BufferedImage imageLeft = ImageIO.read(fileLeft);
//            IOUtil.closeCloseableQuietly(fileLeft);
//
//            Storage<PublicStorage> imageMobileLeft =
//                new PublicStorage(_destPath + "/" + subFolderName + "/mobile_l.jpg");
//            OutputStream imageMobileLeftOut = imageMobileLeft.create();
//            try {
//              BufferedImage imageNew =
//                  new BufferedImage(mobileWidth, mobileHeight, BufferedImage.TYPE_INT_RGB);
//              imageNew = Thumbnails.of(imageLeft).size(mobileWidth, mobileHeight).asBufferedImage();
//              IOUtil.transfer(getImageStream(imageNew, "jpg"), imageMobileLeftOut);
//            } finally {
//              IOUtil.closeCloseableQuietly(imageMobileLeftOut);
//            }
//
//            imageLeft = Thumbnails.of(imageLeft).size(width, height).asBufferedImage();
//            // 从图片中读取RGB
//            imageArrayLeft = imageLeft.getRGB(0, 0, width, height, imageArrayLeft, 0, width);
//          }
//          if ("f".equals(_position)) {
//            // 读取第二张图片
//            fileFront = file.open();
//            BufferedImage imageFront = ImageIO.read(fileFront);
//            IOUtil.closeCloseableQuietly(fileFront);
//
//            Storage<PublicStorage> imageMobileFront =
//                new PublicStorage(_destPath + "/" + subFolderName + "/mobile_f.jpg");
//            OutputStream imageMobileFrontOut = imageMobileFront.create();
//            try {
//              BufferedImage imageNew =
//                  new BufferedImage(mobileWidth, mobileHeight, BufferedImage.TYPE_INT_RGB);
//              imageNew =
//                  Thumbnails.of(imageFront).size(mobileWidth, mobileHeight).asBufferedImage();
//              IOUtil.transfer(getImageStream(imageNew, "jpg"), imageMobileFrontOut);
//            } finally {
//              IOUtil.closeCloseableQuietly(imageMobileFrontOut);
//            }
//
//            imageFront = Thumbnails.of(imageFront).size(width, height).asBufferedImage();
//            imageArrayFront = imageFront.getRGB(0, 0, width, height, imageArrayFront, 0, width);
//          }
//          if ("r".equals(_position)) {
//            // 读取第三张图片
//            fileRight = file.open();
//            BufferedImage imageRight = ImageIO.read(fileRight);
//            IOUtil.closeCloseableQuietly(fileRight);
//            Storage<PublicStorage> imageMobileRight =
//                new PublicStorage(_destPath + "/" + subFolderName + "/mobile_r.jpg");
//            OutputStream imageMobileRightOut = imageMobileRight.create();
//            try {
//              BufferedImage imageNew =
//                  new BufferedImage(mobileWidth, mobileHeight, BufferedImage.TYPE_INT_RGB);
//              imageNew =
//                  Thumbnails.of(imageRight).size(mobileWidth, mobileHeight).asBufferedImage();
//              IOUtil.transfer(getImageStream(imageNew, "jpg"), imageMobileRightOut);
//            } finally {
//              IOUtil.closeCloseableQuietly(imageMobileRightOut);
//            }
//
//            imageRight = Thumbnails.of(imageRight).size(width, height).asBufferedImage();
//            imageArrayRight = imageRight.getRGB(0, 0, width, height, imageArrayRight, 0, width);
//          }
//          if ("b".equals(_position)) {
//            // 对第四张图片做相同的处理
//            fileBack = file.open();
//            BufferedImage imageBack = ImageIO.read(fileBack);
//            IOUtil.closeCloseableQuietly(fileBack);
//
//            Storage<PublicStorage> imageMobileBack =
//                new PublicStorage(_destPath + "/" + subFolderName + "/mobile_b.jpg");
//            OutputStream imageMobileBackOut = imageMobileBack.create();
//            try {
//              BufferedImage imageNew =
//                  new BufferedImage(mobileWidth, mobileHeight, BufferedImage.TYPE_INT_RGB);
//              imageNew = Thumbnails.of(imageBack).size(mobileWidth, mobileHeight).asBufferedImage();
//              IOUtil.transfer(getImageStream(imageNew, "jpg"), imageMobileBackOut);
//            } finally {
//              IOUtil.closeCloseableQuietly(imageMobileBackOut);
//            }
//
//            imageBack = Thumbnails.of(imageBack).size(width, height).asBufferedImage();
//            imageArrayBack = imageBack.getRGB(0, 0, width, height, imageArrayBack, 0, width);
//          }
//          if ("u".equals(_position)) {
//            // 对第五张图片做相同的处理
//            fileUp = file.open();
//            BufferedImage imageUp = ImageIO.read(fileUp);
//            IOUtil.closeCloseableQuietly(fileUp);
//
//            Storage<PublicStorage> imageMobileUp =
//                new PublicStorage(_destPath + "/" + subFolderName + "/mobile_u.jpg");
//            OutputStream imageMobileUpOut = imageMobileUp.create();
//            try {
//              BufferedImage imageNew =
//                  new BufferedImage(mobileWidth, mobileHeight, BufferedImage.TYPE_INT_RGB);
//              imageNew = Thumbnails.of(imageUp).size(mobileWidth, mobileHeight).asBufferedImage();
//              IOUtil.transfer(getImageStream(imageNew, "jpg"), imageMobileUpOut);
//            } finally {
//              IOUtil.closeCloseableQuietly(imageMobileUpOut);
//            }
//
//            imageUp = Thumbnails.of(imageUp).size(width, height).asBufferedImage();
//            imageArrayUp = imageUp.getRGB(0, 0, width, height, imageArrayUp, 0, width);
//          }
//          if ("d".equals(_position)) {
//            // 对第六张图片做相同的处理
//            fileDown = file.open();
//            BufferedImage imageDown = ImageIO.read(fileDown);
//            IOUtil.closeCloseableQuietly(fileDown);
//
//            Storage<PublicStorage> imageMobileDown =
//                new PublicStorage(_destPath + "/" + subFolderName + "/mobile_d.jpg");
//            OutputStream imageMobileDownOut = imageMobileDown.create();
//            try {
//              BufferedImage imageNew =
//                  new BufferedImage(mobileWidth, mobileHeight, BufferedImage.TYPE_INT_RGB);
//              imageNew = Thumbnails.of(imageDown).size(mobileWidth, mobileHeight).asBufferedImage();
//              IOUtil.transfer(getImageStream(imageNew, "jpg"), imageMobileDownOut);
//            } finally {
//              IOUtil.closeCloseableQuietly(imageMobileDownOut);
//            }
//
//            imageDown = Thumbnails.of(imageDown).size(width, height).asBufferedImage();
//            imageArrayDown = imageDown.getRGB(0, 0, width, height, imageArrayDown, 0, width);
//          }
//
//        }
//
//        if (VrConstants.VAL_PUBLIC_DIRECTORY_PANOS_L.equals(subFolderName)) {
//          if (VrConstants.REGISTER_SCENE_FLAG.equals(index)) {
//            flg = true;
//          }
//          // 生成新图片
//          BufferedImage imageNew = new BufferedImage(width, height * 6, BufferedImage.TYPE_INT_RGB);
//          imageNew.setRGB(0, 0, width, height, imageArrayLeft, 0, width);
//          imageNew.setRGB(0, height, width, height, imageArrayFront, 0, width);
//          imageNew.setRGB(0, height * 2, width, height, imageArrayRight, 0, width);
//          imageNew.setRGB(0, height * 3, width, height, imageArrayBack, 0, width);
//          imageNew.setRGB(0, height * 4, width, height, imageArrayUp, 0, width);
//          imageNew.setRGB(0, height * 5, width, height, imageArrayDown, 0, width);
//          Storage<PublicStorage> savePreviewFile = new PublicStorage(_destPath + "/preview.jpg");
//          OutputStream previewFileOut = savePreviewFile.create();
//          try {
//            imageNew = Thumbnails.of(imageNew).size(256, 256 * 6).asBufferedImage();
//            IOUtil.transfer(getImageStream(imageNew, "jpg"), previewFileOut);
//          } finally {
//            IOUtil.closeCloseableQuietly(previewFileOut);
//          }
//        }
//
//        // 全景图的XML做成
//        dir = new PublicStorage(_destPath);
//        dir.makeDirectories();
//
//        String xmlPath = "";
//        if (VrConstants.VAL_PUBLIC_DIRECTORY_PANOS_L.equals(subFolderName)) {
//          xmlPath = FileUtils.getAbsolutePath("framework/panorama/template/show_l.xml");
//        }
//        if (VrConstants.VAL_PUBLIC_DIRECTORY_PANOS_R.equals(subFolderName)) {
//          xmlPath = FileUtils.getAbsolutePath("framework/panorama/template/show_r.xml");
//        }
//        File XmlFile = new File(xmlPath);
//        String filePath = _destPath + XmlFile.getName();
//
//        Storage<PublicStorage> saveFile = new PublicStorage(filePath);
//
//        InputStream inputStream = new FileInputStream(XmlFile);
//        OutputStream out = saveFile.create();
//        try {
//          IOUtil.transfer(new FileInputStream(XmlFile), out);
//        } finally {
//          IOUtil.closeCloseableQuietly(out);
//          IOUtil.closeCloseableQuietly(inputStream);
//        }
//
//        // 音频热点popup_show.xml做成
//        if (VrConstants.VAL_PUBLIC_DIRECTORY_PANOS_L.equals(subFolderName)) {
//          String popup_xmlPath =
//              FileUtils.getAbsolutePath("framework/panorama/template/popup_show.xml");
//          File popup_XmlFile = new File(popup_xmlPath);
//          String popup_filePath = _destPath + popup_XmlFile.getName();
//
//          Storage<PublicStorage> popup_saveFile = new PublicStorage(popup_filePath);
//
//          InputStream popup_inputStream = new FileInputStream(popup_XmlFile);
//          OutputStream popup_out = popup_saveFile.create();
//          try {
//            IOUtil.transfer(new FileInputStream(popup_XmlFile), popup_out);
//          } finally {
//            IOUtil.closeCloseableQuietly(popup_out);
//            IOUtil.closeCloseableQuietly(popup_inputStream);
//          }
//        }
//      }
//
//      if (flg) {
//        // 登记文件时，将左眼文件内容复制到右眼中
//        copyDirInPublicStorage(_destPath + "/panos_l/", _destPath + "/panos_r/");
//        // 做成右眼的show_r.xml
//        String xmlPath = FileUtils.getAbsolutePath("framework/panorama/template/show_r.xml");
//        File XmlFile = new File(xmlPath);
//        String filePath = _destPath + XmlFile.getName();
//
//        Storage<PublicStorage> saveFile = new PublicStorage(filePath);
//
//        InputStream inputStream = new FileInputStream(XmlFile);
//        OutputStream out = saveFile.create();
//        try {
//          IOUtil.transfer(new FileInputStream(XmlFile), out);
//        } finally {
//          IOUtil.closeCloseableQuietly(out);
//          IOUtil.closeCloseableQuietly(inputStream);
//        }
//      }
//
//    } catch (IOException e) {
//      throw new SystemException("文件上传失败！");
//    }
    return returnfilePath;

  }


  /**
   * 保存全景临时文件到应用服务器
   * 
   * @param _destPath ファイルパス (sample: equipment/panorama/)
   * @param key キー
   * @return ファイルパス
   * @throws SystemException
   */
  public String saveTempPanoramaFileToAppServer(String _destPath, String _key, String subFolderName)
      throws SystemException {
    String result = "";
//    try {
//
//      Collection<SessionScopeStorage> fileList =
//          getSessionScopeStorageFileList(_key, subFolderName);
//      if (CollectionUtil.isNotEmpty(fileList)) {
//
//        // 全景图的图片保存
//        for (SessionScopeStorage file : fileList) {
//
//          String panoramaFileName = file.getName();
//          String _position = panoramaFileName.substring(panoramaFileName.lastIndexOf(".") - 1,
//              panoramaFileName.lastIndexOf("."));
//          panoramaFileName = "sphere_" + _position + ".jpg";
//          String filePath =
//              FileUtils.getAbsolutePath(_destPath + "/" + subFolderName + "/" + panoramaFileName);
//          // 出力先ファイル
//          File destFile_ = FileUtils.mkdirsParent(filePath);
//
//          InputStream inputStream = file.open();
//          OutputStream out = new FileOutputStream(destFile_);
//          try {
//            IOUtil.transfer(inputStream, out);
//          } finally {
//            IOUtil.closeCloseableQuietly(out);
//            IOUtil.closeCloseableQuietly(inputStream);
//          }
//        }
//        // String filePath = FileUtils.getAbsolutePath(_destPath + "show.xml");
//        //
//        // String xmlPath = FileUtils.getAbsolutePath("framework/panorama/template/show.xml");
//
//        String filePath = "";
//
//        String xmlPath = "";
//
//        if (VrConstants.VAL_PUBLIC_DIRECTORY_PANOS_L.equals(subFolderName)) {
//          filePath = FileUtils.getAbsolutePath(_destPath + CommonConstants.PANOS_SHOW_L_XML);
//          xmlPath = FileUtils.getAbsolutePath("framework/panorama/template/show_l.xml");
//        }
//        if (VrConstants.VAL_PUBLIC_DIRECTORY_PANOS_R.equals(subFolderName)) {
//          filePath = FileUtils.getAbsolutePath(_destPath + "show_r.xml");
//          xmlPath = FileUtils.getAbsolutePath("framework/panorama/template/show_r.xml");
//        }
//
//        File XmlFile = new File(xmlPath);
//
//        InputStream inputStream = new FileInputStream(XmlFile);
//        OutputStream out = new FileOutputStream(filePath);
//        try {
//          IOUtil.transfer(new FileInputStream(XmlFile), out);
//        } finally {
//          IOUtil.closeCloseableQuietly(out);
//          IOUtil.closeCloseableQuietly(inputStream);
//        }
//
//        // result = _destPath + "show.xml";
//
//        if (VrConstants.VAL_PUBLIC_DIRECTORY_PANOS_L.equals(subFolderName)) {
//          result = _destPath + CommonConstants.PANOS_SHOW_L_XML;
//        }
//        if (VrConstants.VAL_PUBLIC_DIRECTORY_PANOS_R.equals(subFolderName)) {
//          result = _destPath + "show_r.xml";
//        }
//
//      }
//
//    } catch (IOException e) {
//      throw new SystemException("文件上传失败！");
//    }
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
//    try {
//      Collection<PublicStorage> fileList = new PublicStorage(_srcPath).filesStorages();
//
//      if (CollectionUtil.isNotEmpty(fileList)) {
//        // 旧文件删除
//        // FileUtils.deleteFolder(FileUtils.getAbsolutePath(_destPath));
//        // 全景图的图片保存
//        for (PublicStorage file : fileList) {
//
//          String filePath = FileUtils.getAbsolutePath(_destPath + file.getName());
//
//          // 出力先ファイル
//          File destFile_ = FileUtils.mkdirsParent(filePath);
//
//          InputStream inputStream = file.open();
//          OutputStream out = new FileOutputStream(destFile_);
//          try {
//            IOUtil.transfer(inputStream, out);
//          } finally {
//            IOUtil.closeCloseableQuietly(out);
//            IOUtil.closeCloseableQuietly(inputStream);
//          }
//        }
//
//        fileList = new PublicStorage(_srcPath + "/panos_l/").filesStorages();
//
//        if (CollectionUtil.isNotEmpty(fileList)) {
//          // 全景图的图片保存
//          for (PublicStorage file : fileList) {
//
//            String filePath = FileUtils.getAbsolutePath(_destPath + "/panos_l/" + file.getName());
//
//            // 出力先ファイル
//            File destFile_ = FileUtils.mkdirsParent(filePath);
//
//            InputStream inputStream = file.open();
//            OutputStream out = new FileOutputStream(destFile_);
//            try {
//              IOUtil.transfer(inputStream, out);
//            } finally {
//              IOUtil.closeCloseableQuietly(out);
//              IOUtil.closeCloseableQuietly(inputStream);
//            }
//          }
//        }
//
//        fileList = new PublicStorage(_srcPath + "/panos_r/").filesStorages();
//        if (CollectionUtil.isNotEmpty(fileList)) {
//          // 全景图的图片保存
//          for (PublicStorage file : fileList) {
//
//            String filePath = FileUtils.getAbsolutePath(_destPath + "/panos_r/" + file.getName());
//
//            // 出力先ファイル
//            File destFile_ = FileUtils.mkdirsParent(filePath);
//
//            InputStream inputStream = file.open();
//            OutputStream out = new FileOutputStream(destFile_);
//            try {
//              IOUtil.transfer(inputStream, out);
//            } finally {
//              IOUtil.closeCloseableQuietly(out);
//              IOUtil.closeCloseableQuietly(inputStream);
//            }
//          }
//        }
//        _result = true;
//      }
//    } catch (IOException e) {
//      throw new SystemException("获取全景图文件失败！");
//    }
    return _result;
  }


  /**
   * SessionScopeStorageクリア
   * 
   * @param key キー
   * @throws SystemException
   */
  public void clearSessionScopeStorage(String key) throws SystemException {

//    try {
//
//      Storage<SessionScopeStorage> dir = new SessionScopeStorage(
//          CommonUtil.getLoginAccountContext().getTenantId() + "/" + key + "/");
//      if (!dir.remove(true)) {
//        throw new SystemException("文件删除失败！");
//      }
//
//    } catch (IOException e) {
//      throw new SystemException("文件删除失败！");
//    }
  }

  /**
   * PublicStorageクリア
   * 
   * @param key キー
   * @throws SystemException
   */
  public void deletePublicStorageFolder(String _srcPath) throws SystemException {

//    try {
//      Storage<PublicStorage> dir = new PublicStorage(_srcPath + "/");
//      if (dir.exists() && !dir.remove(true)) {
//        throw new SystemException("文件删除失败！");
//      }
//
//    } catch (IOException e) {
//      throw new SystemException("文件删除失败！");
//    }
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
//    try {
//      String[] srcFileList = new File(FileUtils.getAbsolutePath(_srcPath)).list();
//      if (srcFileList != null && srcFileList.length > 0) {
//        // 文件保存
//        for (String srcFileName : srcFileList) {
//
//          File srcFile = new File(FileUtils.getAbsolutePath(_srcPath) + "/" + srcFileName);
//
//          if (srcFile.isFile()) {
//            String destFilePath = FileUtils.getAbsolutePath(_destPath, srcFileName);
//            File saveFile = FileUtils.mkdirsParent(destFilePath);
//            InputStream inputStream = new FileInputStream(srcFile);
//            OutputStream out = new FileOutputStream(saveFile);
//            try {
//              IOUtil.transfer(inputStream, out);
//            } finally {
//              IOUtil.closeCloseableQuietly(out);
//              IOUtil.closeCloseableQuietly(inputStream);
//            }
//          }
//          if (srcFile.isDirectory()) {
//            copyDirInAppServer(_srcPath + "/" + srcFileName + "/",
//                _destPath + "/" + srcFileName + "/");
//          }
//        }
//        returnfilePath = true;
//      }
//    } catch (IOException e) {
//      throw new SystemException("文件拷贝失败！");
//    }
    return returnfilePath;
  }

  /**
   * 把gif图片按帧拆分成jpg图片
   * 
   * @param _gifFileFullPath String 小gif图片(路径+名称)
   * @param _destPngFile String 生成小png图片的路径(路径+名称)
   * @return String[] 返回生成小png图片的宽，高，帧数，延迟时间
   * @throws IOException
   */
  public String[] splitGif(String _gifFileFullPath, String _destPngFile) throws IOException {

    String[] frameInfo = new String[] {};
//    PublicStorage srcFile = new PublicStorage(_gifFileFullPath);
//    GifDecoder decoder = new GifDecoder();
//    // 读取文件
//    decoder.read(srcFile.open());
//    // 得到frame的个数
//    int num = decoder.getFrameCount();
//    // 图片帧数小于一帧，不做拆分存图操作
//    if (num <= 1) {
//      return frameInfo;
//    }
//    // 得到延迟时间
//    int delay = decoder.getDelay(0);
//
//    // 取得gif图片的分辨率
//    InputStream srcFileInputStream = srcFile.open();
//    BufferedImage sourceImage = ImageIO.read(srcFileInputStream);
//    int width = sourceImage.getWidth();
//    int height = sourceImage.getHeight();
//    int[] imageArray = new int[width * height];
//
//    // 生成新图片,ARGB用于生成背景透明的gif，非透明也可以使用
//    BufferedImage imageNew = new BufferedImage(width, height * num, BufferedImage.TYPE_INT_ARGB);
//
//    for (int i = 0; i < num; i++) {
//      // 得到帧
//      BufferedImage frame = decoder.getFrame(i);
//      imageArray = frame.getRGB(0, 0, width, height, imageArray, 0, width);
//      imageNew.setRGB(0, height * i, width, height, imageArray, 0, width);
//    }
//
//    Storage<PublicStorage> saveFile = new PublicStorage(_destPngFile);
//
//    InputStream inputStream = getImageStream(imageNew, "png");
//    OutputStream out = saveFile.create();
//    try {
//      IOUtil.transfer(inputStream, out);
//
//    } finally {
//      IOUtil.closeCloseableQuietly(out);
//      IOUtil.closeCloseableQuietly(inputStream);
//      IOUtil.closeCloseableQuietly(srcFileInputStream);
//    }
//
//    frameInfo = new String[] {StringUtils.defaultString(width), StringUtils.defaultString(height),
//        StringUtils.defaultString(num - 1), StringUtils.defaultString(delay * 0.001)};
    return frameInfo;
  }

}

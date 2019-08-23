package cn.com.pano.pano.common.utils;

import cn.com.pano.pano.common.service.FileCommonService;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.util.SpringUtils;

/**
 * 共通処理。
 * 
 * @author 唐友成
 * @date 2019-08-07
 */
public class FileServiceUtil {

  /** ロジック。 */
  protected static FileCommonService logic = SpringUtils.getBean(FileCommonService.class);

  /**
   * PublicStorageファイル保存。
   * 
   * @param key キー
   * @param destPath ファイルパス (sample: equipment/file/)
   * @return ファイルパス
   * @throws SystemException
   */
  public static String saveImageToPublicStorage(String key, String destPath)
      throws SystemException {
    return logic.saveImageToPublicStorage(key, destPath);
  }

  /**
   * SessionStorageからAPPサーバまでディレクトリをコピーする。
   * 
   * @param key キー
   * @param destPath ファイルパス (sample: equipment/file/)
   * @return ファイルパス
   * @throws SystemException
   */
  public static String copyDirFromSessionStorageToAppServer(String key, String destPath)
      throws SystemException {
    return logic.copyDirFromSessionStorageToAppServer(key, destPath);
  }

  /**
   * PublicStorageからAPPサーバまでディレクトリをコピーする。
   * 
   * @param srcPath 元ファイルパス (sample: equipment/file/)
   * @param destPath ファイルパス (sample: equipment/file/)
   * @return ファイルパス
   * @throws SystemException
   */
  public static boolean copyDirFromPublicStorageToAppServer(String srcPath, String destPath)
      throws SystemException {
    return logic.copyDirFromPublicStorageToAppServer(srcPath, destPath);
  }

  /**
   * createPanoramaThumbImage。
   * 
   * @param srcPath 元ファイルパス (sample: equipment/file/)
   * @param destPath ファイルパス (sample: equipment/file/)
   * @return ファイルパス
   * @throws SystemException
   */
  public static boolean createPanoramaThumbImageToAppServer(String srcPath, String destPath)
      throws SystemException {
    return logic.createPanoramaThumbImageToAppServer(srcPath, destPath);
  }

  /**
   * PublicStorage下转移文件。
   * 
   * @param srcPath 元ファイルパス (sample: equipment/file/)
   * @param destPath ファイルパス (sample: equipment/file/)
   * @return ファイルパス
   * @throws SystemException
   */
  public static boolean copyDirInPublicStorage(String srcPath, String destPath)
      throws SystemException {
    return logic.copyDirInPublicStorage(srcPath, destPath);
  }


  /**
   * PublicPanoramaファイル保存。
   * 
   * @param srcPath 元ファイルパス (sample: equipment/file/)
   * @param destPath ファイルパス (sample: equipment/file/)
   * @return ファイルパス
   * @throws SystemException
   */
  public static String savePanoramaFileToPublicStorage(String srcPath, String destPath)
      throws SystemException {
    return logic.savePanoramaFileToPublicStorage(srcPath, destPath);
  }


  /**
   * PublicPanoramaファイル保存。
   * 
   * @param destPath ファイルパス
   * @param key キー
   * @return ファイルパス
   * @throws SystemException
   */
  public static String saveTempPanoramaFileToAppServer(String destPath, String key)
      throws SystemException {
    return logic.saveTempPanoramaFileToAppServer(destPath, key);
  }


  /**
   * PublicStorageファイル取得。
   * 
   * @param srcPath 元ファイルパス (sample: equipment/file/)
   * @param destPath ファイルパス (sample: equipment/file/)
   * @return ファイルパス
   * @throws SystemException
   */
  public static boolean getPanoramaFileFromPublicStorage(String srcPath, String destPath)
      throws SystemException {
    return logic.getPanoramaFileFromPublicStorage(srcPath, destPath);
  }



  /**
   * SessionScopeStorageクリア。
   * 
   * @param key キー
   * @throws SystemException
   */
  public static void clearSessionScopeStorage(String key) throws SystemException {
    logic.clearSessionScopeStorage(key);
  }

  /**
   * PublicStorage下文件删除。
   * 
   * @param key キー
   * @throws SystemException
   */
  public static void deletePublicStorageFolder(String srcPath) throws SystemException {
    logic.deletePublicStorageFolder(srcPath);
  }

  /**
   * AppServer内转移文件。
   * 
   * @param srcPath
   * @param destPath
   * @return
   * @throws Exception
   */
  public static boolean copyDirInAppServer(String srcPath, String destPath) throws Exception {
    return logic.copyDirInAppServer(srcPath, destPath);
  }

  /**
   * gif图拆分拼接为png图。
   * 
   * @param gifFileFullPath
   * @param destThunbFile
   * @return
   * @throws Exception
   */
  public static String[] splitAndSaveGif(String gifFileFullPath, String destThunbFile)
      throws Exception {
    return logic.splitGif(gifFileFullPath, destThunbFile);
  }
}

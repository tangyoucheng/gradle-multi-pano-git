package cn.com.pano.pano.common.utils;

import cn.com.pano.pano.common.service.FilesCommonService;
import cn.com.platform.framework.common.exception.SystemException;

/**
 * 共通処理。
 * 
 * @author kaima
 * @version 1.00
 * @since 1.00
 */
public class FilesServiceUtil {

  /** ロジック。 */
  protected static FilesCommonService fileslogic;


  /**
   * PublicStorageからAPPサーバまでディレクトリをコピーする。
   * 
   * @param _srcPath 元ファイルパス (sample: equipment/file/)
   * @param _destPath ファイルパス (sample: equipment/file/)
   * @return ファイルパス
   * @throws C2CommonApplicationException
   */
  public static boolean copyDirFromPublicStorageToAppServer(String _srcPath, String _destPath)
      throws SystemException {
    return fileslogic.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);
  }


  /**
   * PublicPanoramaファイル保存。
   * 
   * @param _destPath 相対パス (sample: equipment/panorama/)
   * @param key キー (sample: test/test0)
   * @param file ファイル
   * @return ファイルパス
   * @throws C2CommonApplicationException
   */
  public static String savePanoramaFileToPublicStorage(String _destPath, String key,
      String subFolderName, String index) throws SystemException {
    return fileslogic.savePanoramaFileToPublicStorage(_destPath, key, subFolderName, index);
  }

  /**
   * PublicPanoramaファイル保存。
   * 
   * @param _destPath ファイルパス
   * @param key キー
   * @return ファイルパス
   * @throws C2CommonApplicationException
   */
  public static String saveTempPanoramaFileToAppServer(String _destPath, String key,
      String subFolderName) throws SystemException {
    return fileslogic.saveTempPanoramaFileToAppServer(_destPath, key, subFolderName);
  }


  /**
   * PublicStorageファイル取得。
   * 
   * @param _srcPath 元ファイルパス (sample: equipment/file/)
   * @param _destPath ファイルパス (sample: equipment/file/)
   * @return ファイルパス
   * @throws C2CommonApplicationException
   */
  public static boolean getPanoramaFileFromPublicStorage(String _srcPath, String _destPath)
      throws SystemException {
    return fileslogic.getPanoramaFileFromPublicStorage(_srcPath, _destPath);
  }

  /**
   * SessionScopeStorageクリア。
   * 
   * @param key キー
   * @throws C2CommonApplicationException
   */
  public static void clearSessionScopeStorage(String key) throws SystemException {
    fileslogic.clearSessionScopeStorage(key);
  }

  /**
   * PublicStorage下文件删除。
   * 
   * @param key キー
   * @throws C2CommonApplicationException
   */
  public static void deletePublicStorageFolder(String _srcPath) throws SystemException {
    fileslogic.deletePublicStorageFolder(_srcPath);
  }

  /**
   * AppServer内转移文件。
   * 
   * @param _srcPath
   * @param _destPath
   * @return
   * @throws Exception
   */
  public static boolean copyDirInAppServer(String _srcPath, String _destPath) throws Exception {
    return fileslogic.copyDirInAppServer(_srcPath, _destPath);
  }

  /**
   * gif图拆分拼接为png图。
   * 
   * @param _gifFileFullPath
   * @param _destThunbFile
   * @return
   * @throws Exception
   */
  public static String[] splitAndSaveGif(String _gifFileFullPath, String _destThunbFile)
      throws Exception {
    return fileslogic.splitGif(_gifFileFullPath, _destThunbFile);
  }
}

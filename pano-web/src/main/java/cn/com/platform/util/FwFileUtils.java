/*
 * Copyright(c) 2011
 */

package cn.com.platform.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import cn.com.platform.framework.common.StandardConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.common.session.UserSessionUtils;

/**
 * ファイル操作用ユーティリティ。
 * 
 * @author admin
 */
public class FwFileUtils {

  /**
   * 絶対パス取得。
   * 
   * @param paths パス構成要素
   * @return 絶対パス
   */
  public static String getAbsolutePath(String... paths) {

    // ファイルパス生成
    File path = new File(Objects.toString(paths[0]));

    // パス要素を連結
    for (int i = 1; i < paths.length; ++i) {
      path = new File(path.getPath(), Objects.toString(paths[i]));
    }

    // 相対パスの場合は「Context配下」と判断
    if (!path.isAbsolute()) {
      ServletContext servletContext =
          ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
              .getServletContext();
      path = new File(servletContext.getRealPath(System.getProperty("file.separator")),
          path.getPath());
    }

    // 絶対パスを返す
    return path.getAbsolutePath();

  }

  /**
   * コンテキストパス取得。
   * 
   * @param paths パス構成要素
   * @return コンテキストパス
   */
  public static String getContextPath(String... paths) {

    // ファイルパス生成
    File path = new File(Objects.toString(paths[0]));

    // パス要素を連結
    for (int i = 1; i < paths.length; ++i) {
      path = new File(path.getPath(), Objects.toString(paths[i]));
    }
    // String resultPath_ = path_.getPath().replace("\\", "/");

    ServletContext servletContext =
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
            .getServletContext();
    String resultPath = servletContext.getContextPath() + path.getPath().replace("\\", "/");

    return resultPath;

  }

  /**
   * 相対パス取得。
   * 
   * @param paths パス構成要素
   * @return 相対パス
   */
  public static String getRelativePath(String... paths) {

    // ファイルパス生成
    File path = new File(Objects.toString(paths[0]));

    // パス要素を連結
    for (int i = 1; i < paths.length; ++i) {
      path = new File(path.getPath(), Objects.toString(paths[i]));
    }

    // 絶対パスを返す
    return path.getPath();

  }

  /**
   * ファイル親フォルダ作成。
   * 
   * @param filePath ファイルパス.
   * @return パラメータで渡されたファイル.
   */
  public static final File mkdirsParent(String filePath) {
    return mkdirsParent(new File(getAbsolutePath(filePath)));
  }

  /**
   * ファイル親フォルダ作成。
   * 
   * @param file ファイル.
   * @return パラメータで渡されたファイルをそのまま返す.
   */
  public static final File mkdirsParent(File file) {

    // 親フォルダ取得
    File parent = file.getParentFile();

    // 親フォルダ存在チェック
    if (parent != null && !parent.exists()) {
      file.getParentFile().mkdirs();
    }

    // ファイル返却
    return file;

  }

  /**
   * ファイル存在チェック。
   * 
   * @param path ファイルパス.
   * @return true:存在する
   */
  public static final Boolean existsFile(String path) {

    // 親フォルダ取得
    File file = new File(getAbsolutePath(path));

    // ファイル存在チェック
    return file.exists() && file.isFile();

  }


  /**
   * zipファイル作成用変数。
   */
  protected static byte buf[] = new byte[1024];

  /**
   * zipファイルを作成。
   * 
   * @param jos ZipOutputStream
   * @param file 保存先ファイル名
   * @param pathName 作業フォルダパス
   * @param zipFileName ファイル名
   * @throws Exception 系统例外
   */
  private static void recurseFiles(ZipOutputStream jos, File file, String pathName,
      String zipFileName) throws Exception {
    if (file.isDirectory()) {
      pathName = pathName + file.getName() + "/";
      try {
        jos.putNextEntry(new ZipEntry(pathName));
      } catch (IOException e) {
        throw new IOException(e);
      }
      String[] fileNames = file.list();
      if (fileNames != null) {
        // 作業フォルダにファイルをzip圧縮する
        for (int i = 0; i < fileNames.length; i++) {
          recurseFiles(jos, new File(file, fileNames[i]), pathName, "");
        }
      }
    } else {
      ZipEntry jarEntry = null;
      if (zipFileName == null || zipFileName.length() == 0) {
        jarEntry = new ZipEntry(pathName + file.getName());
      } else {
        jarEntry = new ZipEntry(zipFileName);
      }
      if (file.exists() && file.isFile()) {
        FileInputStream fin = null;
        BufferedInputStream in = null;
        try {
          fin = new FileInputStream(file);
          in = new BufferedInputStream(fin);
          jos.putNextEntry(jarEntry);

          int len = 0;
          while ((len = in.read(buf)) >= 0) {
            jos.write(buf, 0, len);
          }
        } catch (FileNotFoundException e) {
          throw new FileNotFoundException();
        } catch (IOException e) {
          throw new IOException(e);
        } finally {

          try {
            if (in != null) {
              in.close();
            }

          } catch (IOException e) {
            e.printStackTrace();
          }
          try {
            if (fin != null) {
              fin.close();
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }

      }

      // ZIP出力ストリームをクローズ
      jos.closeEntry();
    }
  }

  /**
   * zipファイルを作成。
   * 
   * @param files 保存先ファイルリスト
   * @param zipFile 保存先ファイル名(必ず完全パスを含む)
   * @throws IOException 系统例外
   */
  public static void toZip(List<File> files, File zipFile) throws Exception {

    ZipOutputStream jos = null;
    try {
      jos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile), 1024));
      for (int i = 0; i < files.size(); i++) {
        recurseFiles(jos, files.get(i), "", "");
      }
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException();
    } catch (IOException e) {
      throw new IOException(e);
    } finally {
      try {
        if (jos != null) {
          jos.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * zipファイルを作成。
   * 
   * @param files 保存先ファイルリスト 例えば：<String, File>ZIP内ファイル名、ファイル
   * @param zipFile 保存先ファイル名(必ず完全パスを含む)
   * @throws IOException 系统例外
   */
  public static void toZip(Map<String, File> files, File zipFile) throws Exception {

    ZipOutputStream jos = null;
    try {
      if (!existsFile(zipFile.getAbsolutePath())) {
        mkdirsParent(zipFile.getAbsolutePath());
      }
      jos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile), 1024));
      for (Map.Entry<String, File> entry : files.entrySet()) {
        recurseFiles(jos, entry.getValue(), entry.getValue().getPath(), entry.getKey());
      }
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException();
    } catch (IOException e) {
      throw new IOException(e);
    } finally {
      try {
        if (jos != null) {
          jos.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * <p>
   * 编辑下载的临时文件路径。
   * </p>
   * 
   * @param fileName 文件名
   * @param byteArray 文件内容
   * @return String 临时文件相对路径
   * @throws SystemException 例外的场合
   */
  public static String createTempExcelFile(String fileName, byte[] byteArray)
      throws SystemException {

    DateTimeFormatter df =
        DateTimeFormatter.ofPattern(StandardConstantsIF.DATE_FORMAT_YYYYMMDDHHMMSS);
    String outputFileName = fileName.concat(df.format(LocalDateTime.now()));
    // 追加文件后缀
    outputFileName = outputFileName.concat(".xlsx");

    String tempFileRelativePath =
        MessageFormat.format(StandardConstantsIF.APP_SERVER_TEMP_DOWNLOAD_FOLDER,
            UserSessionUtils.getSessionId(), outputFileName);


    // 取得临时文件的绝对路径
    File tempOutputFile = new File(FwFileUtils.getAbsolutePath(tempFileRelativePath));
    if (!tempOutputFile.getParentFile().isDirectory()) {
      tempOutputFile.getParentFile().mkdirs();
    }
    // 做成临时文件
    OutputStream tempOutputStream = null;
    try {
      tempOutputStream = new FileOutputStream(FwFileUtils.getAbsolutePath(tempFileRelativePath));
      tempOutputStream.write(byteArray);
      tempOutputStream.close();
    } catch (Exception e) {
      throw new SystemException(e);
    } finally {
      if (tempOutputStream != null) {
        try {
          tempOutputStream.close();
        } catch (IOException e) {
          throw new SystemException(e);
        }
      }
    }
    return tempFileRelativePath;
  }

}

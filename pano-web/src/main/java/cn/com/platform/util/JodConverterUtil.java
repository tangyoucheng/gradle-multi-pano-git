package cn.com.platform.util;

import java.io.File;
import org.jodconverter.DocumentConverter;
import org.jodconverter.office.OfficeException;
import cn.com.platform.web.context.JodWebappContext;

public class JodConverterUtil {

  /**
   * 文件转换。
   * 
   * @param srcFile 源文件文件
   * @param destFile 转换后文件
   * @throws Exception 异常的场合
   */
  public static void convert(File srcFile, File destFile) throws Exception {

    try {
      DocumentConverter converter = JodWebappContext.get().getDocumentConverter();
      // 转换文档
      converter.convert(srcFile).to(destFile).execute();
    } catch (OfficeException e1) {
      throw new Exception(e1);
    }
  }
}

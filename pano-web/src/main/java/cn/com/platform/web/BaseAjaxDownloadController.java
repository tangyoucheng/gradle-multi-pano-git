package cn.com.platform.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.platform.util.FwFileUtils;

/**
 * web层通用数据处理。
 * 
 * @author 唐友成
 * @date 2019-07-04
 */
@Controller
public class BaseAjaxDownloadController {


  /**
   * ajax方式下载文件。
   */
  @RequestMapping("/ajaxDownload")
  public String ajaxDownload(HttpServletResponse response, HttpServletRequest request) {


    String ajaxDownloadFileName = (String) request.getParameter("ajaxDownloadFileName");
    File temoFile = new File(FwFileUtils.getAbsolutePath(ajaxDownloadFileName));

    InputStream tempInputStream = null;
    OutputStream out = null;
    try {
      tempInputStream = new FileInputStream(temoFile);
      // 获取输出流并输出文件
      out = response.getOutputStream();
      int readbyte = 0;
      byte[] buffer = new byte[4096];
      while ((readbyte = tempInputStream.read(buffer)) > 0) {
        out.write(buffer, 0, readbyte);
      }
      tempInputStream.close();
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (tempInputStream != null) {
          tempInputStream.close();
        }
        if (out != null) {
          out.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

}

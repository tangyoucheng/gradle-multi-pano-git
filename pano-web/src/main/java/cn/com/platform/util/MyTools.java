package cn.com.platform.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.com.platform.framework.common.exception.SystemException;

/**
 * 个人工具类
 * 
 * @author xiongcheng
 *
 */
public class MyTools {

  /**
   * 加密 用户的 cookie值 第一步 拼接hash id:password:currentTime:key 第二步 加密hash md5加密 第三步 得到cookie
   * id:nickname:currentTime:md5(hash)
   * 
   * @param id 用户id
   * @param password 用户密码
   * @param key 密匙
   * @return
   */
  public static String encodeCookie(int id, String password, String key) {
    // 获取当前时间戳
    long currentTime = System.currentTimeMillis();
    String text = id + ":" + password + ":" + currentTime + ":" + key;
    String md5Hash = MyTools.getMD5(text); // 获取加密的text值
    // 得到cookie的值
    String cookieValue = id + ":" + currentTime + ":" + md5Hash;
    return cookieValue;
  }

  /**
   * MD5加密
   * 
   * @param message 要进行MD5加密的字符串
   * @return 加密结果为32位字符串
   */
  public static String getMD5(String message) {
    MessageDigest messageDigest = null;
    StringBuffer md5StrBuff = new StringBuffer();
    try {
      messageDigest = MessageDigest.getInstance("MD5");
      messageDigest.reset();
      messageDigest.update(message.getBytes(StandardCharsets.UTF_8.name()));

      byte[] byteArray = messageDigest.digest();
      for (int i = 0; i < byteArray.length; i++) {
        if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
          md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
        else
          md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
      }
    } catch (Exception e) {
      throw new RuntimeException();
    }
    return md5StrBuff.toString().toUpperCase();// 字母大写
  }

  /**
   * 设置cookie
   * 
   * @param response
   * @param name cookie名字
   * @param value cookie值
   * @param maxAge cookie生命周期 以秒为单位
   */
  public static void addCookie(HttpServletResponse response, String name, String value,
      int maxAge) {
    Cookie cookie = new Cookie(name, value);
    cookie.setPath("/");
    if (maxAge > 0) {
      cookie.setMaxAge(maxAge);
    }
    response.addCookie(cookie);
  }

  /**
   * 根据cookie名字 获取该cookie
   * 
   * @param cookies
   * @param name
   * @return
   */
  public static Cookie getCookieByName(Cookie[] cookies, String name) {

    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(name)) {
        return cookie;
      }
    }
    return null;
  }


  /**
   * 返回json数据给 easyui表格显示
   * 
   * @param response
   * @param easyJson 单独为easyui json表格 封装的json信息
   * @throws Exception
   */
  public static void writeEasyJson(HttpServletResponse response, EasyJson easyJson)
      throws SystemException {
    response.setContentType("text/html;charset=" + StandardCharsets.UTF_8.name());
    // 允许跨域访问
    response.setHeader("Access-Control-Allow-Origin", "*");

    // 获取mapper对象
    ObjectMapper mapper = new ObjectMapper();
    DateFormat bf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    mapper = mapper.setDateFormat(bf);


    PrintWriter out;
    try {
      out = response.getWriter();
      out.println(mapper.writeValueAsString(easyJson));
    } catch (IOException e1) {
      throw new SystemException(e1);
    }
    out.flush();
    out.close();
  }


  /**
   * 产生随机的六位数
   * 
   * @return
   */
  public static String getSix() {
    Random rad = new Random();

    String result = Objects.toString(rad.nextInt(1000000));

    if (result.length() != 6) {
      return getSix();
    }
    return result;
  }


  public static void writeImg(File imgFile, HttpServletResponse response) {
    // 声明输出流
    OutputStream os = null;
    // 声明输入流
    FileInputStream fis = null;
    try {
      // 获取响应输出流
      os = response.getOutputStream();
      // 文件获取流
      fis = new FileInputStream(imgFile);
      response.setHeader("Content-Type", "image/*");// 设置响应的媒体类型，这样浏览器会识别出响应的是图片
      // 写入数据
      os.write(IOUtils.toByteArray(fis));
      response.flushBuffer();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != os)
        try {
          os.close();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      if (null != fis)
        try {
          fis.close();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
    }
  }

  /***
   * 生成订单号
   * 
   * @return
   */
  public static String getNumberForPK() {
    String id = "";
    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
    String temp = sf.format(new Date());
    int random = (int) (Math.random() * 10000);
    id = temp + random;
    return id;
  }

  /**
   * 获取两个时间的小时差值
   * 
   * @param startTime 开始时间
   * @param endTime 结束时间
   * @return
   * @throws ParseException
   */
  public static int getDistanceHours(Date startTime, Date endTime) throws ParseException {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    long form = format.parse(format.format(startTime)).getTime();
    long to = format.parse(format.format(endTime)).getTime();
    int hours = (int) ((to - form) / (1000 * 60 * 60));
    return hours;
  }
}

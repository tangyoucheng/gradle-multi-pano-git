package captcha;

import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import cn.com.platform.framework.captcha.Captcha;
import cn.com.platform.framework.captcha.SpecCaptcha;

public class TestCaptcha {
  public static void outputPng() throws Exception {

    OutputStream outputStream = new FileOutputStream(new File("D:/aa.png"));

    // 三个参数分别为宽、高、位数
    SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);

    // 设置字体
    specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32)); // 有默认字体，可以不用设置

    // 设置类型，纯数字、纯字母、字母数字混合
    specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);

    // 生成的验证码
    String code = specCaptcha.text();

    // 输出图片流
    specCaptcha.out(outputStream);
  }

  public static void main(String[] args) throws Exception {
    TestCaptcha.outputPng();
  }
}

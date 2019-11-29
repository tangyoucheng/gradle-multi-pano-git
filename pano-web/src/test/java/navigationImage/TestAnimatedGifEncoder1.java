package navigationImage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import cn.com.platform.framework.util.AnimatedGifEncoder1;

/**
 * 
 * PNG导航图转GIF导航图<br>
 * 参照 【https://github.com/tangyoucheng/animatedgifencoder-1】
 * 
 * @author 唐友成
 * @date 2019-11-29
 *
 */
public class TestAnimatedGifEncoder1 {

  public static final void main(String[] args) throws Exception {
    InputStream inputStreamSrcFile = null;
    try {

      // bin/main/
      // navigationImage.class.getClassLoader().getResource("")
      // bin/test/
      // navigationImage.class.getResource("")
      // PNG图片
      String pngFileName = "arrow128x128-3200.png";
      // String pngFileName = "hotspot_ani_black_64x64x20.png";
      // String pngFileName = "hotspot_ani_white_64x64x20.png";
      File srcFile = new File(TestAnimatedGifEncoder.class.getResource("").getFile() + pngFileName);
      // GIF图片
      String gifFileName = "arrow128x128.gif";
      // String gifFileName = "hotspot_ani_black_64x64.gif";
      // String gifFileName = "hotspot_ani_white_64x64.gif";
      FileOutputStream outputStreamFile = new FileOutputStream(
          TestAnimatedGifEncoder.class.getResource("").getFile() + gifFileName);


      AnimatedGifEncoder1 encoder = new AnimatedGifEncoder1();
      // encoder.start("testout.gif");
      // encoder.setDelay(1000);// 1 frame per 500/ms
      encoder.start(TestAnimatedGifEncoder.class.getResource("").getFile() + gifFileName);
      encoder.setDelay(100);// 1 frame per 100/ms
      encoder.setTransparent(Color.WHITE);
      encoder.setRepeat(0); // 0:ループする -1:ループしない

      // 加载原始PNG导航图
      inputStreamSrcFile = TestAnimatedGifEncoder.class.getResourceAsStream(pngFileName);
      BufferedImage image = ImageIO.read(inputStreamSrcFile);

      int gifWidth = image.getWidth();
      // int gifHeight = image.getHeight();
      int gifHeight = image.getWidth();
      int frameCount = image.getHeight() / image.getWidth();

      for (int i = 0; i < frameCount; i++) {
        // int[] pixels = new int[gifWidth * gifHeight];
        // image.getRGB(0, gifHeight * i, gifWidth, gifHeight, pixels, 0, gifWidth);
        // encoder.addFrame(pixels, gifWidth, gifHeight);


        BufferedImage img = new BufferedImage(gifWidth, gifHeight, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g2d = img.createGraphics();
//        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, gifWidth, gifHeight);
//        g2d.setColor(Color.BLACK);
//        g2d.drawOval(0, i, 100, 100);
        encoder.addFrame(img);
        g2d.dispose();
      }
      encoder.finish();

    } finally {
      if (inputStreamSrcFile != null) {
        inputStreamSrcFile.close();
      }
    }
  }

}

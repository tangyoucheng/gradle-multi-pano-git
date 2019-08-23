package cn.com.pano.panovr.dto.panovr02;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 为热点添加素材Dto
 * 
 * @author ouyangzidou
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PanoVr020902Dto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // 热点图的路径
    public String hotspotImagePath;
    
    // 判断该素材是否是gif图并是否有生产对应的png图
    public String hasPngImage;
    // gif宽
    public String gifWidth;
    // gif高
    public String gifHeight;
    // gif帧数
    public String gifFrame;
    // gif间隔
    public String gifDelayTime;
    
}

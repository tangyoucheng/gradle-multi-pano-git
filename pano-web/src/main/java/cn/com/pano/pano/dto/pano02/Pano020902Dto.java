package cn.com.pano.pano.dto.pano02;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.pano.dto.pano01.Pano0104Dto;
import cn.com.pano.pano.form.pano01.Pano0104Form;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.platform.framework.common.CodeValueRecord;
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
public class Pano020902Dto implements Serializable {
    
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

package cn.com.pano.pano.dto.pano01;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.pano.form.pano01.Pano0104Form;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.platform.framework.common.CodeValueRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author yangyuzhen
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Pano0110Dto extends PanoPanoramaHotspot implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // 素材ID
    public String materialId;
    // 素材路径
    public String materialPath;
    // 热点的素材图结果集
    public List<String> hotspotMaterialPath;
    // gif宽
    public String gifWidth;
    // gif高
    public String gifHeight;
    // gif帧数
    public String gifFrameCount;
    // gif间隔
    public String gifDelayTime;
    // 导航热点跳转至下一场景的视角点
    public String panoramaHlookat;
    public String panoramaVlookat;
}

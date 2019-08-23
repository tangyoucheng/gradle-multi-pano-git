package cn.com.pano.pano.dto.pano01;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.pano.form.pano01.Pano0104Form;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.platform.framework.common.CodeValueRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * APIリザルトDTOクラス.
 * 
 * @author A [TCSJ]
 * @version 1.0
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Pano010101dto implements Serializable {
    
    /** デフォルトシリアルーバージョンID */
    private static final long serialVersionUID = 1L;
    // 热点top
    public String planeMapHotSpotTop;
    // 热点left
    public String planeMapHotSpotLeft;
    // 热点width
    public String planeMapHotSpotWidth;
    // 热点height
    public String planeMapHotSpotHeight;
    
    // 图ID
    public String mapId;
    // 父图ID
    public String mapParentId;
    // 图累类型ID
    public String mapTypeId;
    // 图路径
    public String mapPath;
    // 图上热点的ID
    public String mapHotspotId;
    
}

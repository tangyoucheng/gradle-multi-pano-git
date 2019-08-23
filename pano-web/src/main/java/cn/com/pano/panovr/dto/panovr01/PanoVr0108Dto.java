package cn.com.pano.panovr.dto.panovr01;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author tangzhenzong
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PanoVr0108Dto extends PanoPanoramaHotspot implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // 全景图热点详细信息集合
    public String hotspotPointListJson;
    
    // 多边形热点对应坐标点
    public List<PanoPolygonHotspot> pointList;
    
}

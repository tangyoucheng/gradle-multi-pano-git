package cn.com.pano.panovr.dto.panovr02;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
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
public class PanoVr0203Dto extends PanoPanoramaHotspot implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // 原料类型ID
    public String materialTypeId;
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
    // 多边形热点对应坐标点
    public List<PanoPolygonHotspot> pointList;
    //
    public String materialPath;
    // 音乐热点第二张图的id
    public String secondHotspotImageId;
    //
    public String firstHotspotImageId;
    //
    public String firstSortkey;
    //
    public String secondSortkey;
}

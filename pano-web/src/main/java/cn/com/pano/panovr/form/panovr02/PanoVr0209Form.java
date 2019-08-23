package cn.com.pano.panovr.form.panovr02;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.panovr.dto.panovr02.PanoVr020901Dto;
import cn.com.pano.panovr.dto.panovr02.PanoVr020902Dto;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 热点大小编辑Form
 * 
 * @author tangzhenzong
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PanoVr0209Form extends AbstractForm implements Serializable {
    private static final long serialVersionUID = 1L;
    // 展览ID
    public String expositionIdForPanoVr0209;
    // 展览名称
    public String expositionName;
    // 全景图ID
    public String panoramaIdForPanoVr0209;
    // 全景图名称
    public String panoramaName;
    // 全景图路径
    public String panoramaPath;
    // 选中热点的ID
    public String vr0209hotspotId;
    // 是否为推荐线路点
    public String recommendFlag;
    // 热点上的推荐信息
    public String recommendInfo;
    // 展览下统一的推荐信息
    public String expoRecommendInfo;
    // 导航热点下的信息
    public String expoHotspotTooltipInfo;
    // 导航热点上的统一提示信息
    public String ExpoGoSceneInfo;
    // 热点大小信息集
    public List<CodeValueRecord> hotspotSizeInfo;
    // 热点大小值
    public String hotspotScale;
    // 热点类型
    public String vr0209HotspotType;
    // // 热点图片路径
    // public String hotspotImagePath;
    // 热点坐标
    public String hotspotAth;
    public String hotspotAtv;
    // 视角参数
    public String positionAthForEdit;
    public String positionAtvForEdit;
    // 浮动信息层ID
    public String vr0209flowFileId;
    // 浮动信息层类型
    public String vr0209flowFileType;
    // 图片浮动信息路径
    public String vr0209flowFilePath;
    // 文字浮动信息内容
    public String vr0209flowFileInfo;
    // 浮动层坐标
    public String vr0209layerX;
    public String vr0209layerY;
    // 所传数据是否来自PanoVr0105
    public String commandTypeFromPanoVr0105;
    // 工具条按钮集合
    public List<PanoVr020901Dto> buttonsInfo;
    // 跳转到此场景时上个场景的导航点ID
    public String vr0209TheLastedSceneHotspotId;
    public String lastedHotspotIdFrom0105;
    // 设置了推荐路线点后传回该点ID
    public String vr0209RecommendHotspotId;
    public String currentHotspotIdFrom0105;
    // 来自热点编辑画面的热点坐标和热点imageUrl
    public String vr0203HotspotName;
    public String vr0203HotspotAth;
    public String vr0203HotspotAtv;
    
    public String vr0203MusicHotspot;
    public String seconfhotspotImageIdPanoVr0203;
    public String firsthotspotImageIdPanoVr0203;
    public String firstSortKeyPanoVr0203;
    public String secondSortKeyPanoVr0203;
    
    // 热点的第一张图
    public PanoVr020902Dto firstImageInfo;
    public String firstImageInfoJson;
    // 热点的第二张图
    public PanoVr020902Dto secondImageInfo;
    public String secondImageInfoJson;
    
    // 单张图热点信息
    public PanoVr020902Dto hotspotImageInfo;
    public String hotspotImageInfoJson;
    
    public String vr0203HotspotImageId;
}

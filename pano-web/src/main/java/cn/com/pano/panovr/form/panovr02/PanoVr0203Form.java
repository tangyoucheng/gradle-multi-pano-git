package cn.com.pano.panovr.form.panovr02;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.panovr.dto.panovr02.PanoVr0203Dto;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 全景图热点编辑Form
 * 
 * @author ouyangzidou
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PanoVr0203Form extends AbstractForm implements Serializable {
    
    private static final long serialVersionUID = 1L;
    // 热点ID
    public String hotspotId;
    // 热点大小
    public String hotspotScale;
    // 展览ID
    public String expositionId;
    // 展览名称
    public String expositionName;
    // 全景图路径
    public String panoramaPath;
    // 热点所在全景图的ID
    public String panoramaId;
    // 热点所在全景图的名称
    public String panoramaName;
    // 热点Json
    public String spotInfoListJson;
    // 编辑后热点
    public String spotInfoListSubmitJson;
    // 热点结果集
    public List<PanoVr0203Dto> spotInfoList;
    // 是否取得全景图
    public boolean showCurrentMapExists;
    // 传往编辑画面的视角点坐标
    public String positionAthForEdit;
    public String positionAtvForEdit;
    // 通过上个导航热点跳转到下一个场景时，用该变量存储上个导航热点的ID
    public String theLastedSceneHotspotId;
    // 当前应该显示的推荐路线点ID
    public String currentRecommendHotspotId;
    
    // 音频单点热点的第二个图片的id
    public String  pano0208seconfMaterialId;
    // 音频单点热点的顺序
    public String hotspotUrlInfoJsonPano0208;
}

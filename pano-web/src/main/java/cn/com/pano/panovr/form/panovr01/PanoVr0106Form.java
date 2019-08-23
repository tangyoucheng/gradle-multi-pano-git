package cn.com.pano.panovr.form.panovr01;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 场景导航图编辑Form
 * 
 * @author ouyangzidou
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PanoVr0106Form extends AbstractForm implements Serializable {
    
    private static final long serialVersionUID = 1L;
    // 展览ID
    public String expositionId;
    // 导航图ID
    public String expositionMapId;
    // 展览地图名称
    public String vr0106expositionMapName;
    // 导航图路径
    public String expositionMapPath;
    // 展览名称
    public String expositionName;
    // 全景图路径
    public String panoramaPath;
    // 热点所在全景图的ID
    public String panoramaId;
    // 热点所在全景图的名称
    public String panoramaName;
    // 展览下是否有导航图
    public boolean miniMapCheck;
    // 热点JSON
    public String miniMapSpotInfoListJson;
    // 热点结果集
    public List<PanoExpositionMapHotspot> miniMapSpotInfoList;
    // 通过上个导航热点跳转到下一个场景时，用该变量存储上个导航热点的ID
    public String theLastedSceneHotspotId;
    // 当前应该显示的推荐路线点ID
    public String currentRecommendHotspotId;
}

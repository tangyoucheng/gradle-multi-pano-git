package cn.com.pano.panovr.form.panovr02;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 为导航热点添加全景图Form
 * 
 * @author ouyangzidou
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PanoVr0204Form extends AbstractForm implements Serializable {
    
    private static final long serialVersionUID = 1L;
    // 选中的素材Id
    public String selectedMaterialId;
    // 选中的全景图ID
    public String panoramaId;
    // 全景图下拉框
    public List<PanoPanorama> panoramaList;
    // 图的路径
    public String panoramaPath;
    // 被选中的热点ID
    public String selectedHotspotId;
    // 展览ID
    public String expositionId;
    // 展览名称
    public String expositionName;
    // 父画面当前全景图Id
    public String currentPanoramaId;
    // 热点的提示信息
    public String hotspotTooltip;
    // 热点全局的提示信息
    public String expoGoSceneTooltip;
    /* 分页显示信息 */
    public String[] pageShowInfos;
    // 被选中全景图ID
    public String selectedPanoramaId;
    // 传往编辑画面的视角点坐标
    public String positionAthForEdit;
    public String positionAtvForEdit;
    // 保存上一个导航热点ID和当前显示的推荐路线点ID
    public String vr0204LastHotspotId;
    public String vr0204CurrentHotspotId;
}

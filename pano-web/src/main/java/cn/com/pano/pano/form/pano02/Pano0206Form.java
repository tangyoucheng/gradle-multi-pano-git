package cn.com.pano.pano.form.pano02;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import cn.com.pano.pano.dto.pano01.Pano0104Dto;
import cn.com.pano.pano.form.pano01.Pano0104Form;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 场景一览Form
 * 
 * @author ouyangzidou
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Pano0206Form extends AbstractForm implements Serializable {
    
    private static final long serialVersionUID = 1L;
    // 全景图信息结果集
    public List<PanoPanorama> panoramaInfo;
    // 展览ID
    public String expositionId;
    // 展览名称
    public String expositionName;
    // 上个画面的全景图ID
    public String  pano0206PanoramaId;
    // 检索条件-全景图ID
    public String panoramaId;
    // 检索条件-全景图名称
    public String panoramaName;
    // 删除用全景图ID
    public String panoramaIdForDelete;
    // 全景图路径
    public String panoramaPath;
    // 备注
    public String notes;
    // 最终更新日期
    public Timestamp lastUpdateDate;
    // 地图ID
    public String expositionMapId;
    // 场景信息json
    public String panoramaInfoJson;
    /* 分页显示信息 */
    public String[] pageShowInfos;
    // 通过上个导航热点跳转到下一个场景时，用该变量存储上个导航热点的ID
    public String theLastedSceneHotspotId;
    // 当前应该显示的推荐路线点ID
    public String currentRecommendHotspotId;
    
}

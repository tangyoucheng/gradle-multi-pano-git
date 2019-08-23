package cn.com.pano.panovr.form.panovr01;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.panovr.dto.panovr01.PanoVr0108Dto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author tangzhenzong
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PanoVr0108Form implements Serializable {
    
    private static final long serialVersionUID = 1L;
    // 展览ID
    public String expositionId;
    // 展览名称
    public String expositionName;
    
    // 全景图ID
    public String panoramaId;
    // 全景图名称
    public String panoramaName;
    // 当前要显示的图路径
    public String panoramaPath;
    // 当前要显示的图是否存在标识
    public boolean showCurrentMapExists;
    // 热点json
    public String spotInfoListJson;
    // 热点结果集
    public List<PanoVr0108Dto> spotInfoList;
    // 传往编辑画面的视角点坐标
    public String positionAthForEdit;
    public String positionAtvForEdit;
    // 通过上个导航热点跳转到下一个场景时，用该变量存储上个导航热点的ID
    public String theLastedSceneHotspotId;
    // 当前应该显示的推荐路线点ID
    public String currentRecommendHotspotId;
    
}

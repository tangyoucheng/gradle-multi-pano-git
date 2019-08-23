package cn.com.pano.panovr.form.panovr02;

import java.io.Serializable;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 场景编辑Form
 * 
 * @author ouyangzidou
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PanoVr0202Form extends AbstractForm implements Serializable {
    
    private static final long serialVersionUID = 1L;
    public String spotInfoListJson;
    // 通过小地图迁移后进行删除操作时传来的全景图ID
    public String panoramaIdForDelete;
    // 展览ID
    public String expositionId;
    // 展览名称
    public String expositionName;
    // 全景图ID
    public String panoramaId;
    // 全景图名称
    public String panoramaName;
    // 全景图路径
    public String panoramaPath;
    // 新全景图路径
    public String newPanoramaPath;
    // 是否选择了音乐
    public String panoMusicSelect;
    // 全景图说明
    public String notes;
    // 是否取得全景图
    public boolean showCurrentMapExists;
    // 是否为首个场景
    public String startSceneFlag;
    public String isStartScene;
    // 场景背景音乐名
    public String panoramaBackGroundSoundName;
    // 从声音素材设定画面返回的素材ID
    public String materialIdFromPano0208;
    // 从父画面传过来的flag，用作认定不同的父画面，使用不同的删除操作
    public String flagFromParentPage;
    // 通过上个导航热点跳转到下一个场景时，用该变量存储上个导航热点的ID
    public String vr0202TheLastedSceneHotspotId;
    // 当前应该显示的推荐路线点ID
    public String vr0202CurrentRecommendHotspotId;
    
    public String vr0202subFolderName;
}

package cn.com.pano.panovr.form.panovr01;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 导航图操作画面Form
 * 
 * @author ouyangzidou
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PanoVr0105Form extends AbstractForm implements Serializable {
    
    private static final long serialVersionUID = 1L;
    // 展览ID
    public String expositionId;
    // 导航图ID
    public String expositionMapId;
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
    // 编辑后热点
    public String spotInfoListSubmitJson;
    // 导航图上被选中的热点ID
    public String selectedHotspotId;
    // 导航图上选中的热点的雷达的角度
    public String radarHeading;
    // 展览浮动信息ID
    public String flowInfoFileId;
    // 展览浮动信息类型
    public String flowInfoType;
    // 展览图片浮动信息路径
    public String flowInfoFilePath;
    // 展览文字浮动信息内容
    public String flowInfoFileInfo;
    // 展览浮动信息横坐标
    public String flowInfoFileX;
    // 展览浮动信息纵坐标
    public String flowInfoFileY;
    // 浮动信息大小
    public String flowInfoFileScale;
    // 指令类型
    public String commandType;
    // 指令类型列表
    public List<CodeValueRecord> commandTypeList;
    // 编辑后的工具条所选按钮
    public String selectedButtons;
    // 通过上个导航热点跳转到下一个场景时，用该变量存储上个导航热点的ID
    public String theLastedSceneHotspotId;
    // 当前应该显示的推荐路线点ID
    public String currentRecommendHotspotId;
    // 传往编辑画面的视角点坐标
    public String positionAthForEdit;
    public String positionAtvForEdit;
}

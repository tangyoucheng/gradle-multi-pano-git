package cn.com.pano.pano.form.pano02;

import java.io.Serializable;
import java.util.List;
import com.google.common.collect.Lists;
import cn.com.pano.pano.model.common01.PanoHotspotUrl01Model;
import cn.com.pano.pano.model.common01.PanoMaterial01Model;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 为热点添加素材Form。
 * 
 * @author ouyangzidou
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Pano0205Form extends AbstractForm implements Serializable {

  private static final long serialVersionUID = 1L;
  // 选中的素材Id
  public String selectedMaterialId;
  // 图片素材结果集
  public List<PanoMaterial01Model> materialInfo;
  // 视频素材结果集
  public List<PanoMaterial01Model> videoMaterialInfo;
  // 已有素材结果集
  public String existedMaterialInfoJson;
  public List<PanoMaterial01Model> existedMaterialInfo = Lists.newArrayList();
  // 图的路径
  public String materialPath;
  // 被选中的热点ID
  public String selectedHotspotId;
  // 展览ID
  public String expositionId;
  // 展览名称
  public String expositionName;
  // 父画面当前全景图Id
  public String currentPanoramaId;
  /* 分页显示信息 */
  public String[] pageShowInfos;
  // 热点的提示信息
  public String hotspotTooltip;
  // 选中的素材信息结果集
  public List<PanoMaterial01Model> selectedInfo;
  // 选中的素材信息结果集
  public String hotspotUrlInfoJson;
  public List<PanoHotspotUrl01Model> hotspotUrlInfoList;
  // 是否为多边形
  public String isPolygon;
  // 多边形颜色
  public String polygonFillcolor;
  // 多边形透明度
  public String polygonFillalpha;
  // 传往编辑画面的视角点坐标
  public String positionAthForEdit;
  public String positionAtvForEdit;
  // url种类
  public String urlType;
  // url种类
  public List<CodeValueRecord> urlTypeList;
  // 外部链接地址
  public String externalLinkAddress;
  // 通过上个导航热点跳转到下一个场景时，用该变量存储上个导航热点的ID
  // public String pano0205TheLastedSceneHotspotId;
  // 当前应该显示的推荐路线点ID
  // public String pano0205RecommendHotspotId;
  // 素材文件归属
  public String materialBelongType;
  // 素材归属类型radiobox
  public List<CodeValueRecord> materialBelongTypeList;
  // 已有的音频素材ID
  public String existedSoundId;
  // 已有的视频素材ID
  public String existedVideoId;
  // 检索发起来自radiobox
  public String theCommandFromRadiobox;
  // 图文材质List
  public List<PanoMaterial01Model> textImgList = Lists.newArrayList();
  // 选择图文材质List
  public List<String> selectList = Lists.newArrayList();
}

package cn.com.pano.pano.form.pano02;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.pano.dto.pano02.Pano0208Dto;
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
public class Pano0208Form extends AbstractForm implements Serializable {

  private static final long serialVersionUID = 1L;
  
  // 展览ID
  public String expositionId;
  // 场景ID
  public String panoramaId;
  // 素材ID 检索条件
  public String materialId;
  // 素材名 检索条件
  public String materialName;
  // 素材种类ID 检索条件
  public String materialTypeId;


  // 被选中的热点ID
  public String selectedHotspotId;

  // 热点的提示信息
  public String hotspotTooltip;
  /* 素材种类单选框 */
  public List<CodeValueRecord> panoMaterialList;
  // 素材结果集
  public List<PanoMaterial01Model> materialInfo;
  public String materialInfoJson;
  /* 分页显示信息 */
  public String[] pageShowInfos;
  // 被选中的素材ID
  public String selectedMaterialId;
  // 图的路径
  public String materialPath;
  // 数据是否来自新建场景画面
  public String dataFromPano0201;
  // 数据是否来自新建展览画面
  public String dataFromPano0101;
  // 数据是否来自场景编辑画面
  public String dataFromPano0202;
  // 数据是否来自展览编辑画面
  public String dataFromPano0103;
  // 数据是否来自展览编辑画面
  public String dataFromPano0109;
  // 素材文件归属
  public String materialBelongType;
  // 素材归属类型radiobox
  public List<CodeValueRecord> materialBelongTypeList;
  
  // 单点热点种类(前页面传过来的热点类型)
  public String hotspotOldType;
  // 单点热点种类选择
  public String hotspotTypeChoice;
  // 单点热点种类选择radiobox
  public List<CodeValueRecord> hotspotTypeChoiceList;
  
  // 已有素材结果集
  public List<Pano0208Dto> existedMaterialInfo;
  // 选中的素材信息结果集
  public String hotspotUrlInfoJson;

  // 音频热点的信息
  public String seconfhotspotImageIdPano0203;
  public String firsthotspotImageIdPano0203;
  public String firstSortKeyPano0203;
  public String secondSortKeyPano0203;
  public String pano0203HotspotAth;
  public String pano0203HotspotAtv;

  // 热点编辑画面再编辑flg
  public String reEdit;

}

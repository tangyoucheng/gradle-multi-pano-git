package cn.com.pano.pano.form.pano03;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import cn.com.pano.pano.model.common01.PanoMaterial01Model;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 素材一览 Form
 * 
 * @author tangzhenzong
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Pano0302Form extends AbstractForm implements Serializable {

  private static final long serialVersionUID = 1L;
  // 展览ID
  public String expositionId;
  // 展览名称
  public String expositionName;
  // 全景图ID
  public String pano0302PanoramaId;
  // 素材ID 检索条件
  public String materialId;
  // 素材名 检索条件
  public String materialName;
  // 素材结果集
  public List<PanoMaterial01Model> materialInfo;
  /* 分页显示信息 */
  public String[] pageShowInfos;
  // 素材结果集序列化
  public String materialInfoJson;
  // 种类结果集对应字段
  public HashMap<String, String> materialTypeMap;
  // 素材类型下拉框信息
  public List<CodeValueRecord> materialTypeSelectInfo;

  public String materialTypeId;
  // 素材文件归属
  public String materialBelongType;
  // 素材归属类型radiobox
  public List<CodeValueRecord> materialBelongTypeList;
  // 是否由展览编辑里打开素材一览
  public String openFromPano0104;

}

package cn.com.pano.pano.form.pano03;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonIgnoreProperties({"uploadFile"}) // 序列化时忽略的属性名称集合 ，加载类上，给出的属性都不序列化
@EqualsAndHashCode(callSuper = false)
public class Pano0303Form extends AbstractForm {

  private static final long serialVersionUID = 1L;
  /* 素材ID */
  public String materialId;
  /* 素材名称 */
  public String materialName;
  // 素材归属
  public String materialBelongType;
  // 素材归属类型radiobox
  public List<CodeValueRecord> materialBelongTypeList;
  // 素材种类
  public String materialTypeId;
  // 素材种类单选框
  public List<CodeValueRecord> materialTypeList;
  
  /* 备注 */
  public String notes;
  // 展览ID
  public String expositionId;
  // 展览名称
  public String expositionName;
  // 父画面当前全景图Id
  public String currentPanoramaId;
  // 素材图
  public String materialPath;
  // 文字浮动信息内容
  public String textflowInfo;
  // 原有文字浮动信息
  public String oldTextflowInfo;
  /* 原有图的路径 */
  public String oldmaterialPath;
  
  // 图文的文字信息-变更前
  public String oldTextInfo;
  // 图文的文字信息
  public String textInfo;
  
  // gif宽-变更前
  public String oldGifWidth;
  // gif高-变更前
  public String oldGifHeight;
  // gif帧数-变更前
  public String oldGifFrameCount;
  // gif间隔-变更前
  public String oldGifDelayTime;
  // gif宽
  public String gifWidth;
  // gif高
  public String gifHeight;
  // gif帧数
  public String gifFrameCount;
  // gif间隔
  public String gifDelayTime;

  /** 图片(前台传后台用)。 */
  public MultipartFile[] uploadFile;
  public String deleteFilePath;
  
}

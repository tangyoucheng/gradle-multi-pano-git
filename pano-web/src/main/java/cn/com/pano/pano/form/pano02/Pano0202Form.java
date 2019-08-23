package cn.com.pano.pano.form.pano02;

import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cn.com.pano.pano.model.common01.PanoPanorama01Model;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 场景编辑Form。
 * 
 * @author ouyangzidou
 * 
 */
@Data
@JsonIgnoreProperties({"panoImg"}) // 序列化时忽略的属性名称集合 ，加载类上，给出的属性都不序列化
@EqualsAndHashCode(callSuper = false)
public class Pano0202Form extends AbstractForm implements Serializable {

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
  // 缩略图显示信息
  public String thumbNote;
  // 全景图说明
  public String notes;
  
  // 是否取得全景图
  public boolean showCurrentMapExists;

  // 音乐素材的ID
  public String panoramaSoundId;
  // 场景背景音乐名
  public String panoramaSoundName;

  // TODO 废弃 是否选择了音乐
  public String panoMusicSelect;
  // TODO 废弃 是否为首个场景
  public String startSceneFlag;
  public String isStartScene;

  // TODO 废弃 场景背景音乐名
  public String panoramaBackGroundSoundName;
  // TODO 废弃 从声音素材设定画面返回的素材ID
  public String materialIdFromPano0208;
  // TODO 废弃 从父画面传过来的flag，用作认定不同的父画面，使用不同的删除操作
  public String flagFromParentPage;
  // TODO 废弃 通过上个导航热点跳转到下一个场景时，用该变量存储上个导航热点的ID
  public String pano0202TheLastedSceneHotspotId;
  // TODO 废弃 当前应该显示的推荐路线点ID
  public String pano0202CurrentRecommendHotspotId;

  /** 图片(前台传后台用)。 */
  public MultipartFile[] panoImg;
  public String deleteImgPath;
  /** 图片(后台传前台用)。 */
  public PanoPanorama01Model[] panoImgList;
}

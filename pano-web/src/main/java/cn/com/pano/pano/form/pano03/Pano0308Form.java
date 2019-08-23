package cn.com.pano.pano.form.pano03;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.pano.model.common01.PanoPanorama01Model;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 展览目录编辑Form。
 * 
 * @author shiwei
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Pano0308Form extends AbstractForm implements Serializable {
  private static final long serialVersionUID = 1L;
  // 展览ID
  public String expositionId;
  // 缩略图集合
  public List<PanoPanorama01Model> thumbInfoList;
  // 选中的缩略图集合
  public List<String> thumbSelectedList;
}

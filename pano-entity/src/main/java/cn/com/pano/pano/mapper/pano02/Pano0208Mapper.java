package cn.com.pano.pano.mapper.pano02;

import java.util.HashMap;
import java.util.List;
import cn.com.pano.pano.model.common01.PanoMaterial01Model;

/**
 * 素材检索。
 * 
 * @author tangzhenzong
 * 
 */

public interface Pano0208Mapper {
  /**
   * 从数据库中检索素材信息。
   * 
   * @param conditions
   * @return
   */
  public long selectMaterialInfoCount(HashMap<?, ?> conditions);

  /**
   * 从数据库中查找素材信息。
   * 
   * @param condition
   * @return
   */
  public List<PanoMaterial01Model> selectMaterialInfo(HashMap<?, ?> condition);

}

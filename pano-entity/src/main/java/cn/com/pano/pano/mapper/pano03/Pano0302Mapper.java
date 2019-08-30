package cn.com.pano.pano.mapper.pano03;

import java.util.HashMap;
import java.util.List;
import cn.com.pano.pano.model.common01.PanoMaterial01Model;

/**
 * 素材检索。
 * 
 * @author tangzhenzong
 * 
 */
public interface Pano0302Mapper {

  /**
   * 从数据库中检索到的素材总数。
   * 
   * @param conditions 检索条件
   * @return
   */
  public long selectMaterialInfoCount(HashMap<?, ?> conditions);

  /**
   * 从数据库中查找素材信息。
   * 
   * @param condition 检索条件
   * @return
   */
  public List<PanoMaterial01Model> selectMaterialInfo(HashMap<?, ?> condition);

}

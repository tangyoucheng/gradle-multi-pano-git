package cn.com.pano.pano.mapper.pano03;

import java.util.HashMap;
import java.util.List;
import cn.com.pano.pano.model.common.PanoPanorama;

/**
 * 全景图一览Service。
 * 
 */
public interface Pano0307Mapper {
  /**
   * 展览下的全景图场景一览件数取得
   * 
   * @param _conditions
   * @return
   * @throws Exception
   */
  public int selectPanoramaInfoCount(HashMap<?, ?> conditions);

  /**
   * 检索展览下的全景图场景
   * 
   * @param _conditions
   * @param pageStartRowNo
   * @return
   */
  public List<PanoPanorama> selectPanoramaInfo(int pageStartRowNo, HashMap<?, ?> conditons);
}

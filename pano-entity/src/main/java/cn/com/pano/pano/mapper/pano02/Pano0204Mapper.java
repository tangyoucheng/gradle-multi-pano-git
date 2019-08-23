package cn.com.pano.pano.mapper.pano02;

import java.util.HashMap;
import java.util.List;
import cn.com.pano.pano.model.common.PanoPanorama;

/**
 * 
 * 展览全景图检索Service
 * 
 */
public interface Pano0204Mapper {
  /**
   * 从数据库中检索指定展览下所有全景图的信息
   * 
   * @param _conditions
   * @param pageStartRowNo
   * @return
   * @throws Exception
   */
  public List<PanoPanorama> selectPanoInfo(HashMap<?, ?> condition, int pageStartRowNo);

  /**
   * 素材库中的全景图一览件数取得
   * 
   * @param _conditions
   * @return
   * @throws Exception
   */
  public int selectPanoInfoCount(HashMap<?, ?> conditions);

}

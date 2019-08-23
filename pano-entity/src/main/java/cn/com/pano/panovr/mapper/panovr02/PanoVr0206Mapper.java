package cn.com.pano.panovr.mapper.panovr02;

import java.util.HashMap;
import java.util.List;
import cn.com.pano.pano.model.common.PanoPanorama;

/**
 * 
 * 展览全景图检索Service
 * 
 */
public interface PanoVr0206Mapper  {
  /**
   * 从数据库中检索指定展览下所有全景图的信息
   * 
   * @param _conditions
   * @param pageStartRowNo
   * @return
   * @throws Exception
   */
  public int selectPanoramaInfoCount(HashMap<?, ?> conditions);

  /**
   * 从数据库中检索指定展览下所有全景图的信息
   * 
   * @param condition
   * @param pageStartRowNo
   * @return
   */
  public List<PanoPanorama> selectPanoramaInfo(HashMap<?, ?> condition);
}

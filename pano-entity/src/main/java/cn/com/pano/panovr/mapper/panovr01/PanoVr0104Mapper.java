package cn.com.pano.panovr.mapper.panovr01;

import java.util.HashMap;
import java.util.List;
import cn.com.pano.pano.model.common.PanoHotspotUrl;
import cn.com.pano.pano.model.common.PanoPanorama;

/**
 * 
 * 展览全景图相关检索的Service
 * 
 */
public interface PanoVr0104Mapper {
  /**
   * 从数据库中检索展览下全部全景图信息
   * 
   * @param _conditions
   * @param pageStartRowNo
   * @return
   * @throws Exception
   */
  public List<PanoPanorama> selectPanoInfo(HashMap<?, ?> condition);

  /**
   * 检索为首图的全景图
   * 
   * @param condition
   * @return
   * @throws Exception
   */
  public PanoPanorama selectStartScenePanoInfo(HashMap<?, ?> condition);

  /**
   * 检索热点下的Url信息，取得urlId对应全景图ID
   * 
   * @param condition
   * @return
   * @throws Exception
   */
  public PanoHotspotUrl selectHotspotUrlInfo(HashMap<?, ?> condition);
}

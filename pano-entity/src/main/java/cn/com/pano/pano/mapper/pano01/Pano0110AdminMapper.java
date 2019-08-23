package cn.com.pano.pano.mapper.pano01;

import java.util.HashMap;
import java.util.List;
import cn.com.pano.pano.model.common01.PanoExposition01Model;

/**
 * 
 * 展览会一览画面Service
 * 
 */
public interface Pano0110AdminMapper {
  /**
   * 从数据库中检索展览会信息
   * 
   * @param _conditions
   * @return
   * @throws Exception
   */
  public int selectExpositionInfoCount(HashMap<?, ?> _conditions);

  /**
   * 从数据库中检索展览会信息
   * 
   * @param _conditions
   * @param pageStartRowNo
   * @return
   * @throws Exception
   */
  public List<PanoExposition01Model> selectExpositionInfo(HashMap<?, ?> _conditions,
      int pageStartRowNo);

  /**
   * 发布展览会信息
   * 
   * @param _conditions
   * @param pageStartRowNo
   * @return
   * @throws Exception
   */
  public int releasePanoMExposition(HashMap<?, ?> _conditions);

  /**
   * 发布全景图信息
   * 
   * @param _conditions
   * @return
   * @throws Exception
   */
  public int releasePanoMPanorama(HashMap<?, ?> _conditions);

  /**
   * 发布全景图上热点信息
   * 
   * @param _condition
   * @return
   * @throws Exception
   */
  public int releasePanoMPanoramaHotspot(HashMap<?, ?> _condition);

  /**
   * 发布多边形热点位置信息
   * 
   * @param _condition
   * @return
   * @throws Exception
   */
  public int releasePanoMPolygonHotspot(HashMap<?, ?> _condition);
}

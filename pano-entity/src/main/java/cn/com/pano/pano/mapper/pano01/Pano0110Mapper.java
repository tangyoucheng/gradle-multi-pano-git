package cn.com.pano.pano.mapper.pano01;

import java.util.HashMap;
import java.util.List;
import cn.com.pano.pano.model.common01.PanoExposition01Model;

/**
 * 展览一览画面Service。
 * 
 */
public interface Pano0110Mapper {
  /**
   * 从数据库中检索展览信息。
   * 
   * @param conditions 检索条件
   * @return
   */
  public long selectExpositionInfoCount(HashMap<?, ?> conditions);

  /**
   * 从数据库中检索展览信息。
   * 
   * @param conditions 检索条件
   * @return
   */
  public List<PanoExposition01Model> selectExpositionInfo(HashMap<?, ?> conditions);

  /**
   * 发布展览信息。
   * 
   * @param _conditions
   * @param pageStartRowNo
   * @throws Exception
   * @return
   */
  public int releasePanoMExposition(HashMap<?, ?> _conditions);

  /**
   * 发布全景图信息。
   * 
   * @param _conditions
   * @throws Exception
   * @return
   */
  public int releasePanoMPanorama(HashMap<?, ?> _conditions);

  /**
   * 发布全景图上热点信息。
   * 
   * @param _condition
   * @throws Exception
   * @return
   */
  public int releasePanoMPanoramaHotspot(HashMap<?, ?> _condition);

  /**
   * 发布多边形热点位置信息。
   * 
   * @param _condition
   * @throws Exception
   * @return
   */
  public int releasePanoMPolygonHotspot(HashMap<?, ?> _condition);

  /**
   * 从数据库中检索热点Id。
   * 
   * @param conditions
   * @throws Exception
   * @return
   */
  public List<PanoExposition01Model> selectHotspot(String selectedExpositionId);

  /**
   * 从数据库中查找图文信息。
   * 
   * @param selectedExpositionId
   * @return
   */
  public List<PanoExposition01Model> selectTxtImg(HashMap<?, ?> _condition);
}

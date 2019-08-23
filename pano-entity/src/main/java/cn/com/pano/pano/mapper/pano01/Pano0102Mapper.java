package cn.com.pano.pano.mapper.pano01;

import java.util.HashMap;
import java.util.List;
import cn.com.pano.pano.model.common01.PanoExposition01Model;

/**
 * 
 * 展览一览画面Service
 * 
 */
public interface Pano0102Mapper {
  /**
   * 从数据库中检索展览信息
   * 
   * @param _conditions
   * @return
   * @throws Exception
   */
  public long selectExpositionInfoCount(HashMap<?, ?> _conditions);

  /**
   * 从数据库中检索展览信息
   * 
   * @param _conditions
   * @param pageStartRowNo
   * @return
   * @throws Exception
   */
  public List<PanoExposition01Model> selectExpositionInfo(HashMap<?, ?> _conditions);
}

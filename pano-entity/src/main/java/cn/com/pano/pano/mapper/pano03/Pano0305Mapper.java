package cn.com.pano.pano.mapper.pano03;

import java.util.HashMap;
import java.util.List;
import cn.com.pano.pano.model.common.PanoExpositionMap;

/**
 * 地图一览Service。
 * 
 */
public interface Pano0305Mapper {
  /**
   * 展览下的地图一览件数取得
   * 
   * @param _conditions
   * @return
   * @throws Exception
   */
  public int selectExpositionMapInfoCount(HashMap<?, ?> conditions);

  /**
   * 检索展览中的地图
   * 
   * @param _conditions
   * @param pageStartRowNo
   * @return
   */
  public List<PanoExpositionMap> selectExpositionMapInfo(int pageStartRowNo,
      HashMap<?, ?> conditons);

  /**
   * 删除指定地图上热点信息
   */
  public void deleteMiniMapHotspot(HashMap<?, ?> conditions);

  /**
   * 删除指定地图信息
   */
  public void deleteMiniMap(HashMap<?, ?> conditions);

  /**
   * 更新旧地图上热点信息到新地图上
   */
  public void updateMiniMapHotspot(HashMap<?, ?> conditions);
}

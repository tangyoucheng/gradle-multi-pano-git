package cn.com.pano.pano.mapper.common01;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import cn.com.pano.pano.mapper.common.PanoPanoramaHotspotMapper;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common.PanoPanoramaHotspotQuery;
import cn.com.pano.pano.model.common01.PanoPanoramaHotspot01Model;

/**
 * 全景图热点坐标检索Service。
 * 
 */
public interface PanoPanoramaHotspot01Mapper extends PanoPanoramaHotspotMapper {

  /**
   * 通过基础model删除数据。
   * 
   * @param conditions 检索条件
   *
   */
  long deleteByBaseModel(PanoPanoramaHotspotQuery conditions);

  /**
   * 通过基础model检索数据。
   * 
   * @param conditions 检索条件
   *
   */
  List<PanoPanoramaHotspot01Model> selectByBaseModel(PanoPanoramaHotspotQuery conditions);

  /**
   * 通过基础model更新数据。
   *
   * @param record 更新数据
   * @param baseCondition 条件
   * @return
   */
  int updateByBaseModelSelective(@Param("record") PanoPanoramaHotspot record,
      @Param("baseCondition") PanoPanoramaHotspotQuery baseCondition);

  /**
   * 通过基础model更新数据。
   *
   * @param record 更新数据
   * @param baseCondition 条件
   */
  int updateByBaseModel(@Param("record") PanoPanoramaHotspot record,
      @Param("baseCondition") PanoPanoramaHotspotQuery baseCondition);

  /**
   * 根据展览ID 查询。
   * 
   * @param conditions 条件
   * @return
   */
  public List<PanoPanoramaHotspot01Model> selectPanoramaHostSpotByExpositionId(
      HashMap<?, ?> conditions);

  /**
   * 以热点URL种类和热点ID为条件，更新对应图上热点URL种类。
   * 
   * @param _conditions
   * @return
   * @return
   * @throws Exception
   */
  public void updateHotspotUrlType(HashMap<?, ?> conditions);

  /**
   * 全景图热点坐标检索
   * 
   * @param _conditions
   * @return
   * @throws Exception
   */
  public List<PanoPanoramaHotspot> selectHotSpot(HashMap<?, ?> conditions);

  /**
   * 全景图热点坐标检索
   * 
   * @param _conditions
   * @return
   * @throws Exception
   */
  public List<PanoPanoramaHotspot> selectPolygonHotSpot(HashMap<?, ?> conditions);

  /**
   * 删除指定全景图下的全部热点
   * 
   * @param _conditions
   * @return
   * @return
   * @throws Exception
   */
  public void deleteHotSpot(HashMap<?, ?> conditions);

  /**
   * 根据展览ID 查询
   * 
   * @param conditions
   * @return
   */
  public List<PanoPanoramaHotspot> selectHostSpotByExpositionId(HashMap<?, ?> conditions);

  /**
   * 清空所有被设为推荐路径的点的推荐状态和信息
   * 
   * @param _conditions
   * @return
   * @return
   * @throws Exception
   */
  public void updateAllRecommendInfo(HashMap<?, ?> conditions);

  /**
   * 全景图热点及其相关素材信息的检索
   * 
   * @param _conditions
   * @return
   * @throws Exception
   */
  public List<PanoPanoramaHotspot01Model> selectHotSpotAndMaterialInfo(HashMap<?, ?> conditions);

  /**
   * 全景图热点及其相关素材信息的检索
   * 
   * @param _conditions
   * @return
   * @throws Exception
   */
  public List<PanoPanoramaHotspot01Model> selectHotSpotMaterialInfo(HashMap<?, ?> conditions);

  /**
   * 以素材为条件，删除对应的热点。
   * 
   * @param _conditions
   * @return
   * @return
   * @throws Exception
   */
  public void deleteHotSpotByMaterial(HashMap<?, ?> conditions);

}

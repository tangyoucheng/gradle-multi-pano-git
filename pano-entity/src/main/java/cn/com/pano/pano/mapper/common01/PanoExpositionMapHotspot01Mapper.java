package cn.com.pano.pano.mapper.common01;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import cn.com.pano.pano.mapper.common.PanoExpositionMapHotspotMapper;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspotQuery;
import cn.com.pano.pano.model.common01.PanoExpositionMapHotspot01Model;

/**
 * 检索使用中地图のサービスクラスです。
 * 
 */
public interface PanoExpositionMapHotspot01Mapper extends PanoExpositionMapHotspotMapper {

  /**
   * 通过基础model删除数据。
   * 
   * @param conditions 检索条件
   *
   */
  long deleteByBaseModel(PanoExpositionMapHotspotQuery conditions);

  /**
   * 通过基础model检索数据。
   * 
   * @param conditions 检索条件
   *
   */
  List<PanoExpositionMapHotspot01Model> selectByBaseModel(PanoExpositionMapHotspotQuery conditions);

  /**
   * 根据expositionId获取展览地图热点信息。
   * 
   * @param conditions 检索条件
   * @return
   */
  public List<PanoExpositionMapHotspot01Model> selectByExpositionId(HashMap<?, ?> conditions);

  /**
   * 根据展会地图ID查询。
   * 
   * @param conditions 检索条件
   * @return
   */
  public List<PanoExpositionMapHotspot01Model> selectByExpositionMapId(HashMap<?, ?> conditions);


  /**
   * 通过基础model更新数据。
   *
   * @param record 更新数据
   * @param baseCondition 条件
   * @return
   */
  int updateByBaseModelSelective(@Param("record") PanoExpositionMapHotspot record,
      @Param("baseCondition") PanoExpositionMapHotspotQuery baseCondition);


  /**
   * 通过基础model更新数据。
   *
   * @param record 更新数据
   * @param baseCondition 条件
   * @return
   */
  int updateByBaseModel(@Param("record") PanoExpositionMapHotspot record,
      @Param("baseCondition") PanoExpositionMapHotspotQuery baseCondition);

  /**
   * 更改全部flg为1
   * 
   * @param _conditions
   * @throws Exception
   */
  public void changeDeleteFlag(HashMap<?, ?> conditions);

  /**
   * 删除flg为1的热点
   * 
   * @throws Exception
   */
  public void deleteSpot(HashMap<?, ?> conditions) throws Exception;

  /**
   * 检索到指定地图下的全部热点
   * 
   * @throws Exception
   */
  public List<PanoExpositionMapHotspot> selectMapHotspotInfo(HashMap<?, ?> conditions);

  /**
   * 根据全景图ID查询与之对应的地图热点
   * 
   * @param _condition
   * @return
   */
  public PanoExpositionMapHotspot selectByPanoramaId(HashMap<?, ?> _condition);

}

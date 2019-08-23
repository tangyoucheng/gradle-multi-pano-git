package cn.com.pano.pano.mapper.common01;

import java.util.HashMap;
import java.util.List;
import cn.com.pano.pano.mapper.common.PanoExpositionMapMapper;
import cn.com.pano.pano.model.common.PanoExpositionMap;
import cn.com.pano.pano.model.common.PanoExpositionMapQuery;
import cn.com.pano.pano.model.common.PanoHotspotUrlQuery;
import cn.com.pano.pano.model.common01.PanoExpositionMap01Model;

/**
 * 检索使用中地图のサービスクラスです。
 * 
 */
public interface PanoExpositionMap01Mapper extends PanoExpositionMapMapper {

  /**
   * 通过基础model删除数据。
   * 
   * @param conditions 检索条件
   *
   */
  long deleteByBaseModel(PanoExpositionMapQuery conditions);

  /**
   * 通过基础model检索数据。
   * 
   * @param conditions 检索条件
   *
   */
  List<PanoExpositionMap01Model> selectByBaseModel(PanoExpositionMapQuery conditions);

  /**
   * 检索展览中正被使用的地图
   * 
   * @param _conditions
   * @param pageStartRowNo
   * @return
   */
  public PanoExpositionMap selectExpositionMapInfo(HashMap<?, ?> conditons);

  /**
   * 检索指定展览下的地图
   * 
   * @param _conditions
   * @param pageStartRowNo
   * @return
   */
  public List<PanoExpositionMap> selectCurrentExpositionMapInfo(HashMap<?, ?> conditons);

  /**
   * 根据展览ID 查询
   * 
   * @param _expositionId
   * @return
   */
  public List<PanoExpositionMap> findByExpositionId(String _expositionId);
}

package cn.com.pano.pano.mapper.common01;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import cn.com.pano.pano.mapper.common.PanoPanoramaMapper;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common.PanoPanoramaQuery;
import cn.com.pano.pano.model.common01.PanoPanorama01Model;

/**
 * {@link PanoPanorama}のサービスクラスです。
 * 
 */
public interface PanoPanorama01Mapper extends PanoPanoramaMapper {

  /**
   * 通过基础model删除数据。
   * 
   * @param conditions 检索条件
   *
   */
  long deleteByBaseModel(PanoPanoramaQuery conditions);

  /**
   * 通过基础model检索数据。
   * 
   * @param conditions 检索条件
   *
   */
  List<PanoPanorama01Model> selectByBaseModel(PanoPanoramaQuery conditions);


  /**
   * 通过基础model更新数据。
   *
   * @param record 更新数据
   * @param baseCondition 条件
   * @return
   */
  int updateByBaseModelSelective(@Param("record") PanoPanorama record,
      @Param("baseCondition") PanoPanoramaQuery baseCondition);


  /**
   * 通过基础model更新数据。
   *
   * @param record 更新数据
   * @param baseCondition 条件
   * @return
   */
  int updateByBaseModel(@Param("record") PanoPanorama record,
      @Param("baseCondition") PanoPanoramaQuery baseCondition);

  /**
   * 检索热点下可以链接的场景。
   * 
   * @param conditions 检索条件
   *
   */
  List<PanoPanorama01Model> selectHotspotPanorama(HashMap<?, ?> conditions);

  /**
   * 检索地图热点下可以链接的场景。
   * 
   * @param conditions 检索条件
   *
   */
  List<PanoPanorama01Model> selectMapHotspotPanorama(HashMap<?, ?> conditions);

  /**
   * 检索为首图的全景图
   * 
   * @param condition
   * @return
   * @throws Exception
   */
  public PanoPanorama selectStartScenePanoInfo(HashMap<?, ?> condition);

  /**
   * 根据展览ID 查询
   * 
   * @param _expositionId
   * @return
   */
  public List<PanoPanorama> findByExpositionId(HashMap<?, ?> condition);

  /**
   * 修改旧数据全景图path
   * 
   * @return
   */
  public void updatePanoramaPath();
}

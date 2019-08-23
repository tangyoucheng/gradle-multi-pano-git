package cn.com.pano.pano.mapper.common01;

import java.util.List;
import cn.com.pano.pano.mapper.common.PanoMaterialMapper;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoMaterialQuery;
import cn.com.pano.pano.model.common01.PanoMaterial01Model;

/**
 * {@link Pano MMaterial}のサービスクラスです。
 * 
 */
public interface PanoMaterial01Mapper extends PanoMaterialMapper {

  /**
   * 通过基础model删除数据。
   * @param conditions 检索条件
   *
   */
  long deleteByBaseModel(PanoMaterialQuery conditions);

  /**
   * 通过基础model检索数据。
   * @param conditions 检索条件
   *
   */
  List<PanoMaterial01Model> selectByBaseModel(PanoMaterialQuery conditions);
  /**
   * 根据展览ID 查询
   * 
   * @param _expositionId
   * @return
   */
  public List<PanoMaterial> findByExpositionId(String _expositionId);
}

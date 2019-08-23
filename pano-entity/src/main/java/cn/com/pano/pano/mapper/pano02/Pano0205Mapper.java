package cn.com.pano.pano.mapper.pano02;

import java.util.HashMap;
import java.util.List;
import cn.com.pano.pano.model.common01.PanoMaterial01Model;

/**
 * Eq020805のサービスクラスです。
 * 
 */
public interface Pano0205Mapper {

  /**
   * 检索素材库中的平面图（图文）。
   * 
   * @param conditons 检索条件
   * @return
   */
  public List<PanoMaterial01Model> selectTextImgMaterialInfo(HashMap<?, ?> conditons);

  /**
   * 素材库中的平面图（普通图片）一览件数取得。
   * 
   * @param conditions 检索条件
   * @return
   */
  public long selectMaterialInfoCount(HashMap<?, ?> conditions);

  /**
   * 检索素材库中的平面图（普通图片）。
   * 
   * @param conditons 检索条件
   * @return
   */
  public List<PanoMaterial01Model> selectMaterialInfo(HashMap<?, ?> conditons);


  /**
   * 删除全景图的热点信息
   * 
   * @throws Exception
   */
  public void deleteVtourSpot(HashMap<?, ?> conditions);
}

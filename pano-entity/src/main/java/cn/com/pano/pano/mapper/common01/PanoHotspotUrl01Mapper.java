package cn.com.pano.pano.mapper.common01;

import java.util.HashMap;
import java.util.List;
import cn.com.pano.pano.mapper.common.PanoHotspotUrlMapper;
import cn.com.pano.pano.model.common.PanoHotspotUrl;
import cn.com.pano.pano.model.common.PanoHotspotUrlQuery;
import cn.com.pano.pano.model.common01.PanoHotspotUrl01Model;

/**
 * {@link PanoHotspotUrl}のサービスクラスです。
 * 
 */
public interface PanoHotspotUrl01Mapper extends PanoHotspotUrlMapper {

  /**
   * 通过基础model删除数据。
   * 
   * @param conditions 检索条件
   *
   */
  long deleteByBaseModel(PanoHotspotUrlQuery conditions);

  /**
   * 通过基础model检索数据。
   * 
   * @param conditions 检索条件
   *
   */
  List<PanoHotspotUrl01Model> selectByBaseModel(PanoHotspotUrlQuery conditions);

  /**
   * 根据展览ID 查询。
   * 
   * @param conditions 检索条件
   * @return
   */
  public List<PanoHotspotUrl> selectHotspotUrlByExpositionId(HashMap<?, ?> conditions);

  /**
   * 以素材为条件，删除对应的热点URL信息。
   * 
   * @param _conditions
   * @return
   */
  public void deleteUrlInfoByMaterial(HashMap<?, ?> _conditions);
  

  /**
   * 根据全景图ID 查询
   * 
   * @param _conditions
   * @return
   */
  public List<PanoHotspotUrl> selectByPanoramaId(HashMap<?, ?> _conditions);

  /**
   * 查询指定热点下的所有url信息
   * 
   * @param _conditions
   * @return
   */
  public List<PanoHotspotUrl> selectByHotspotId(HashMap<?, ?> _conditions);

  /**
   * 设置当前热点的所属URL信息删除标识为1
   * 
   * @param _conditions
   * @return
   */
  public void changeDeleteFlag(HashMap<?, ?> _conditions);

  /**
   * 变更热点类型时解除当前热点下所属的素材图
   * 
   * @param _conditions
   * @return
   */
  public void clearHotspotMaterialImage(HashMap<?, ?> _conditions);

  /**
   * 音乐热点情况，删除音乐热点下除sortKey等于0的所有Hotspoturl信息
   * 
   * @param _conditions
   * @return
   */
  public void deleteMusicHotspotUrlInfo(HashMap<?, ?> _conditions);

  /**
   * 删除当前音乐热点下sortKey等于0的Hotspoturl信息
   * 
   * @param _conditions
   * @return
   */
  public void deleteMusic(HashMap<?, ?> _conditions);

  /**
   * 查询指定素材下的所有热点url信息
   * 
   * @param _conditions
   * @return
   */
  public List<PanoHotspotUrl> findByMaterialId(HashMap<?, ?> _conditions);

  /**
   * 如果是展览会音乐素材，以素材ID为条件，至空展览会音乐ID。
   * 
   * @param _conditions
   * @return
   */
  public void updateExpoSoundIdByMaterial(HashMap<?, ?> _conditions);

  /**
   * 如果是场景音乐素材，以素材ID为条件，至空场景音乐ID。
   * 
   * @param _conditions
   * @return
   */
  public void updatePanoSoundIdByMaterial(HashMap<?, ?> _conditions);

}

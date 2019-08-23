package cn.com.pano.pano.mapper.common01;

import java.util.HashMap;
import java.util.List;
import cn.com.pano.pano.mapper.common.PanoPolygonHotspotMapper;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.pano.pano.model.common.PanoPolygonHotspotQuery;
import cn.com.pano.pano.model.common01.PanoPolygonHotspot01Model;

/**
 * {@link PanoPolygonHotspot}のサービスクラスです。
 */
public interface PanoPolygonHotspot01Mapper extends PanoPolygonHotspotMapper {

  /**
   * 通过基础model删除数据。
   * @param conditions 检索条件
   *
   */
  long deleteByBaseModel(PanoPolygonHotspotQuery conditions);

  /**
   * 通过基础model检索数据。
   * @param conditions 检索条件
   *
   */
  List<PanoPolygonHotspot01Model> selectByBaseModel(PanoPolygonHotspotQuery conditions);
  
    /**
     * 根据展览ID查询
     * 
     * @param _conditions
     * @return
     */
    public List<PanoPolygonHotspot> selectByExpositionId(HashMap<?, ?> _conditions) ;
    
    /**
     * 根据多边形ID查找多边形热点详细信息
     * 
     * @param _conditions
     * @return
     * @throws Exception
     */
    public List<PanoPolygonHotspot> selectBypolygonId(HashMap<?, ?> _conditions) ;
    
    /**
     * 根据热点多边形热点对应的ID清空对应数据
     * 
     * @param _conditions
     */
    public void clearPanoPolygonHotspot(HashMap<?, ?> _conditions) ;
    
}

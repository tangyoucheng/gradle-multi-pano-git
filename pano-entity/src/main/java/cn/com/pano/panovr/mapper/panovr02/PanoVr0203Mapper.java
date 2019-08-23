package cn.com.pano.panovr.mapper.panovr02;

import java.util.HashMap;
import java.util.List;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;

/**
 * 热点保存Serviceのサービスクラスです。
 * 
 */
public interface PanoVr0203Mapper {
    
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
    public void deleteSpot(HashMap<?, ?> conditions) ;
    
    /**
     * 删除全景图的热点信息
     * 
     * @throws Exception
     */
    public void deleteVtourSpot(HashMap<?, ?> conditions);
    
    /**
     * 取得要删除的点
     * 
     * @throws Exception
     */
    public List<PanoPanoramaHotspot> selectDeleteSpot(HashMap<?, ?> conditions) ;
}

package cn.com.pano.pano.mapper.common01;

import java.util.List;
import cn.com.pano.pano.mapper.common.PanoProcessItemsMapper;
import cn.com.pano.pano.model.common.PanoProcessItems;

/**
 * {@link PanoProcessItems}のサービスクラスです。
 * 
 */
public interface PanoProcessItems01Mapper extends PanoProcessItemsMapper {
    
    /**
     * 取得待处理事项的总件数。
     * 
     * @return エンティティのリスト
     */
    public int selectProcessItemsCount() ;
    
    /**
     * 按条件检索当前页的数据。
     * 
     * @return エンティティのリスト
     */
    public List<PanoProcessItems> findAllOrderByCondition(int _pageStartRowNo) ;
    
   ;
}

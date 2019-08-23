package cn.com.pano.pano.mapper.common01;

import java.util.HashMap;
import cn.com.pano.pano.mapper.common.PanoGroupRoleUserMapper;
import cn.com.pano.pano.model.common.PanoExposition;

/**
 * {@link PanoExposition}のサービスクラスです。
 * 
 */
public interface PanoGroupRoleUser01Mapper extends PanoGroupRoleUserMapper {
    
    /**
     * 用户删除。
     * 
     * @param expositionId 識別子
     * @return エンティティ
     */
    public void deleteUsers(HashMap<?, ?> conditions) ;
}

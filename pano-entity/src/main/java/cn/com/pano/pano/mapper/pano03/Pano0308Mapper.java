package cn.com.pano.pano.mapper.pano03;

import java.util.HashMap;
import java.util.List;
import cn.com.pano.pano.model.common.PanoPanorama;

/**
 * 缩略图检索
 * 
 * @author shiwei
 * 
 */

public interface Pano0308Mapper {
  /**
   * 
   * @param conditions
   * @return
   * @throws Exception
   */
  public int selectThumblInfoCount(HashMap<?, ?> conditions);

  /**
   * 从数据库中查找缩略图信息
   * 
   * @param condition
   * @param pageStartRowNo
   * @return
   */
  public List<PanoPanorama> selectThumblInfo(HashMap<?, ?> condition, int pageStartRowNo);

  /**
   * 更新要显示的缩略图标志
   * 
   * @param condition
   * @return
   */
  public int updateShowFlag(HashMap<?, ?> conditions);

  /**
   * 清除缩略图标志
   * 
   * @param condition
   * @return
   */
  public int clearShowFlag(HashMap<?, ?> conditions);
}

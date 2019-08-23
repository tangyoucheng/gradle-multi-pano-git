package cn.com.pano.pano.mapper.pano01;

import java.util.HashMap;

/**
 * 多边形热点保存的Service。
 * 
 * @author tangzhenzong
 * 
 */
public interface Pano0108Mapper {

  /**
   * 删除flg为1的热点(多边形的场合)。
   * 
   * @param conditions 条件
   */
  public Long deletePolygonPoint(HashMap<?, ?> conditions);

}

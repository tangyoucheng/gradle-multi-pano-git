package cn.com.pano.panovr.mapper.panovr01;

import java.util.HashMap;

/**
 * 多边形热点保存的Service
 * 
 * @author tangzhenzong
 * 
 */
public interface PanoVr0108Mapper  {

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
  public void deleteSpot(HashMap<?, ?> conditions);

  /**
   * 删除flg为1的热点(多边形的场合)
   * 
   * @throws Exception
   */
  public void deletePolygon(HashMap<?, ?> conditions);

}

/*
 * Copyright(c) 2016
 */

package cn.com.platform.platform.form.ps99;

import java.util.ArrayList;
import java.util.List;
import cn.com.platform.platform.dto.ps99.Ps9901F01Dto;
import cn.com.platform.platform.dto.ps99.Ps9901F02Dto;
import lombok.Data;


/**
 * 管理员后台管理画面form。
 * 
 * @author 唐友成
 * @date 2018-08-19
 */
@Data
public class Ps9901Form {

  /** 选中的顶层菜单ID。 */
  public String selectTopMenuId;
  /** 选中的顶层菜单名。 */
  public String selectTopMenuName;
  /** 当前区域。 */
  public String currrentLocal;
  /** 顶层菜单。 */
  public List<Ps9901F01Dto> menuTopList = new ArrayList<Ps9901F01Dto>();
  /** 纵向菜单。 */
  public List<Ps9901F02Dto> menuMiddleList = new ArrayList<Ps9901F02Dto>();

}

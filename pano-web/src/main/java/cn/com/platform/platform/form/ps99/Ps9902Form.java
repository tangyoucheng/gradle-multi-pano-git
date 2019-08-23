/*
 * Copyright(c) 2016
 */

package cn.com.platform.platform.form.ps99;

import java.util.ArrayList;
import java.util.List;
import cn.com.platform.platform.dto.ps99.Ps9902F01Dto;
import cn.com.platform.platform.dto.ps99.Ps9902F02Dto;
import lombok.Data;


/**
 * 会员后台管理画面form。
 * 
 * @author 唐友成
 * @date 2018-08-19
 */
@Data
public class Ps9902Form {

  /** 选中的顶层菜单ID。 */
  public String selectTopMenuId;
  /** 选中的顶层菜单名。 */
  public String selectTopMenuName;
  /** 当前区域。 */
  public String currrentLocal;
  /** 顶层菜单。 */
  public List<Ps9902F01Dto> menuTopList = new ArrayList<Ps9902F01Dto>();
  /** 纵向菜单。 */
  public List<Ps9902F02Dto> menuMiddleList = new ArrayList<Ps9902F02Dto>();
  /** 菜单显示顺。 */
  private String dispFlag;

}

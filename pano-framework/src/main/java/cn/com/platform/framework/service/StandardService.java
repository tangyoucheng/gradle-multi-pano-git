package cn.com.platform.framework.service;

import cn.com.platform.framework.code.SortOrderType;
import cn.com.platform.framework.form.AbstractForm;
import cn.com.platform.framework.util.FwNumberUtils;

/**
 * 共通抽象类。
 * 
 * @author kaima
 */
public abstract class StandardService {



  /**
   * 件数验证。
   * 
   * @param _form AbstractForm
   * @param _count 件数
   */
  protected final void checkCount(AbstractForm _form, long _count) {
    // 0件的场合
    if (_count == 0) {
      // 每页第一条数据的行号
      _form.pageStartRowNo = 0;
      // 总件数
      _form.recordCount = 0;
    } else {
      // 最后也数据不存在的场合，显示前一页数据
      if (_form.pageStartRowNo > _count) {
        int pageStartRowNo = (int) (((_count - 1) / _form.pageSize) * _form.pageSize) + 1;
        // 每页第一条数据的行号
        _form.pageStartRowNo = pageStartRowNo;
      }
      // 总件数
      _form.recordCount = FwNumberUtils.toBigDecimal(_count).intValue();
    }

  }

  /**
   * 件数验证。
   * 
   * @param _form AbstractForm
   * @param _count 件数
   */
  protected final void checkCountAjax(AbstractForm _form, long _count) {
    // 0件的场合
    if (_count == 0) {
      // 每页第一条数据的行号
      _form.pageStartRowNo = 0;
      // 总件数
      _form.recordCount = 0;
    } else {
      // 最后也数据不存在的场合，显示前一页数据
      if (_form.pageStartRowNo > _count) {
        int pageStartRowNo = (int) (((_count - 1) / _form.pageSize) * _form.pageSize) + 1;
        // 每页第一条数据的行号
        _form.pageStartRowNo = pageStartRowNo;
      }

      // 总件数
      _form.recordCount = FwNumberUtils.toBigDecimal(_count).intValue();
    }
  }


  /**
   * 取得排序类型。
   * 
   * @param _enum Enum
   * @param inForm 临时保存一览form
   * @return 排序类型
   */
  public boolean isSortName(Enum _enum, AbstractForm inForm) {
    String matterType = _enum.toString().replaceAll("_", "").toLowerCase();
    if (inForm.sortName != null && matterType.equals(inForm.sortName.toLowerCase())) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 取得排序类型。
   * 
   * @param inForm 临时保存一览form
   * @return 案件属性的排序类型
   */
  public boolean isAsc(AbstractForm inForm) {
    if (SortOrderType.ASC.toString().toLowerCase().equals(inForm.sortOrder)) {
      return true;
    } else {
      return false;
    }
  }

}

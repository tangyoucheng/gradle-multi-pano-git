package cn.com.platform.framework.form;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cn.com.platform.framework.common.StandardConstantsIF;
import lombok.Data;

/**
 * 標準の抽象フォーム。
 * 
 * @author 唐友成
 * @date 2018-08-27
 * 
 */
@Data
@JsonIgnoreProperties(value = {"editBeforDataJson"})
public class AbstractForm implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 返回的前页面的IframeID。 */
  public String returnTargetIframe;

  /** 变更前数据序列化信息。 */
  public String editBeforeDataJson;
  /** 输出日志时模块主键。 */
  public String logModuleId;
  /** 输出日志时数据记录的主键。 */
  public String logRecordId;

  /** 每页件数。 */
  public int pageSize = StandardConstantsIF.SMALL_PAGE_SIZE;
  /** 数据总件数。 */
  public long recordCount;
  /** 分页工具条的显示信息。 */
  public String[] pageShowInfos;

  /** 页码。 */
  public int pageNumber;
  /** 每页第一条行号。 */
  public int pageStartRowNo;

  /** bootstrap table 排序用字段。 */
  public String sortName;
  public String sortOrder;

  /** 数据过滤条件。 */
  public List<Object> uniqueKeyList;
}

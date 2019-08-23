package cn.com.platform.util;

import java.io.Serializable;
import java.util.List;
import org.assertj.core.util.Lists;

/**
 * 封装的JSON对象。
 * 
 * @author 唐友成
 * @date 2019-08-21
 *
 * @param <T> 泛型。
 */
public class EasyJson<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  private Boolean success = true;
  private String msg;
  private Object obj;
  private Boolean online = true;

  /** 总件数。 */
  private long total = 0;
  /** bootstrap table 1.15.4以后，非数组的场合返回的明细数据。 */
  private List<?> rows = Lists.newArrayList();
  /** bootstrap table 1.15.4以后，数组的场合返回的明细数据。 */
  private List<?> data = Lists.newArrayList();

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Object getObj() {
    return obj;
  }

  public void setObj(Object obj) {
    this.obj = obj;
  }

  public Boolean getOnline() {
    return online;
  }

  public void setOnline(Boolean online) {
    this.online = online;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public List<?> getRows() {
    return rows;
  }

  public void setRows(List<?> rows) {
    this.rows = rows;
  }

  public List<?> getData() {
    return data;
  }

  public void setData(List<?> data) {
    this.data = data;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("EasyJson [success=");
    builder.append(success);
    builder.append(", msg=");
    builder.append(msg);
    builder.append(", obj=");
    builder.append(obj);
    builder.append(", online=");
    builder.append(online);
    builder.append(", total=");
    builder.append(total);
    builder.append(", rows=");
    builder.append(rows);
    builder.append(", data=");
    builder.append(data);
    builder.append("]");
    return builder.toString();
  }


}

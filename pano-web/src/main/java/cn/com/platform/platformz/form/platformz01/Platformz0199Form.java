package cn.com.platform.platformz.form.platformz01;

import java.util.List;
import cn.com.platform.framework.form.AbstractForm;
import cn.com.platform.platform.model.common01.PlatformDepartment01Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 社区用户form
 * 
 * @author
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Platformz0199Form extends AbstractForm {
  /** 社区名 */
  private String departmentName;
  
  /** 结果集。 */
  public List<PlatformDepartment01Model> departmentList;
  
}

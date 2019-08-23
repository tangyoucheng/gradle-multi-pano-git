package cn.com.platform.platform.model.common01;

import cn.com.platform.platform.model.common.PlatformDepartment;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PlatformDepartment01Model extends PlatformDepartment {
  /** 节点id。 */
  public String id;
  /** 父节点id。 */
  public String parentId;
  /** 叶子节点社区名。 */
  public String leafDepartmentName;
  
}
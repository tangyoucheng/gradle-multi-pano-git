package cn.com.platform.platformz.form.platformz01;

import lombok.Data;

/**
 * 社区新增form
 * 
 * @author 代仁宗、
 * @date 2018-08-24
 */
@Data
public class Platformz019901Form {

  /** 公司ID。 */
  public String companyId;
  /** 社区ID。 */
  public String departmentId;
  /** 社区名称 */
  public String departmentName;
  /** 社区阶层。 */
  public String departmentHierarchy;
  /** 上级社区。 */
  public String parentDepartmentId;
  public String parentDepartmentName;

}

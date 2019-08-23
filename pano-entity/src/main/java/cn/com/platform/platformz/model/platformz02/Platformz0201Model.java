package cn.com.platform.platformz.model.platformz02;

import cn.com.platform.platform.model.common.PlatformMember;

public class Platformz0201Model extends PlatformMember {
  /** 多表查询时添加主表外字段 */
  /** 公司角色id。 */
  private String roleId;
  /** 公司角色name。 */
  private String roleName;

  /** 是否是公司直属部门。 */
  private String isDirectDepartment;

  /** 公司部门 id。 */
  private String departmentId;
  /** 公司部门 name。 */
  private String departmentName;

  /** 公司 id。 */
  private String companyId;
  /** 公司 name。 */
  private String companyName;

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getIsDirectDepartment() {
    return isDirectDepartment;
  }

  public void setIsDirectDepartment(String isDirectDepartment) {
    this.isDirectDepartment = isDirectDepartment;
  }

  public String getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(String departmentId) {
    this.departmentId = departmentId;
  }

  public String getDepartmentName() {
    return departmentName;
  }

  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }
}

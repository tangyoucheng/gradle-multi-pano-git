package cn.com.platform.platform.form.platform03;

import java.util.List;
import cn.com.platform.framework.form.AbstractForm;
import cn.com.platform.platform.model.common.PlatformCompany;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门一栏form
 * @author
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Platform0399Form extends AbstractForm{
  /** 公司名 */
  private String companyName;
  /** 结果集。 */
  public List<PlatformCompany> companyList;
}

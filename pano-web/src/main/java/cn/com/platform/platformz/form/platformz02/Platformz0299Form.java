package cn.com.platform.platformz.form.platformz02;

import java.util.List;
import cn.com.platform.framework.form.AbstractForm;
import cn.com.platform.platform.model.common.PlatformCompany;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公司一栏form
 * 
 * @author 唐友成
 * @date 2019-07-31
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Platformz0299Form extends AbstractForm {
  /** 公司名 */
  private String companyName;
  /** 结果集。 */
  public List<PlatformCompany> companyList;
}

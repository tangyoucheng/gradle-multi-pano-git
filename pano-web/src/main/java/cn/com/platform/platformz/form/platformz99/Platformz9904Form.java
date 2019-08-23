package cn.com.platform.platformz.form.platformz99;

import java.util.List;
import cn.com.platform.framework.form.AbstractForm;
import cn.com.platform.platform.model.common.PlatformLoginInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户登陆日志管理form
 * 
 * @author 唐友成
 * @date 2018-12-21
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Platformz9904Form extends AbstractForm {

  /** 登陆用户ID。 */
  private String loginId;
  /** IP地址。 */
  private String ipAddress;

  /** 结果集。 */
  public List<PlatformLoginInfo> loginInfoList;
}

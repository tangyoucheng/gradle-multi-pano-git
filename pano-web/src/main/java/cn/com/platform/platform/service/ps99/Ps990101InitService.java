package cn.com.platform.platform.service.ps99;

import org.springframework.stereotype.Service;
import cn.com.platform.framework.service.StandardService;
import cn.com.platform.platform.form.ps99.Ps990101Form;
import cn.com.platform.web.BaseService;

/**
 * 管理员密码变更页面初期化Service。
 * 
 * @author 唐友成
 * @since 2017年10月31日
 */
@Service
public class Ps990101InitService extends BaseService {

  /**
   * 初期化处理。
   * 
   * @param inForm 密码变更页面form
   * @throws Exception 异常的场合
   */
  public void doInit(Ps990101Form inForm) throws Exception {}
}

package cn.com.platform.platformz.service.platformz99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import cn.com.platform.platform.mapper.common01.PlatformLoginInfo01Mapper;
import cn.com.platform.platformz.form.platformz99.Platformz9904Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 用户登陆日志更新service
 * 
 * @author 唐友成
 * @date 2018-12-21
 */
@Service
public class Platformz9904UpdateService extends BaseService {

  @Autowired
  PlatformLoginInfo01Mapper platformLoginInfo01Mapper;

  /**
   * 用户登陆日志删除
   * 
   * @param inForm
   * @return
   */
  public EasyJson<Object> doDelete(Platformz9904Form inForm) {

    if (!ObjectUtils.isEmpty(inForm.uniqueKeyList)) {
      for (Object infoId : inForm.uniqueKeyList) {
        platformLoginInfo01Mapper.deleteByPrimaryKey(ObjectUtils.getDisplayString(infoId));
      }
    }

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setSuccess(true);
    easyJson.setMsg("删除成功");
    return easyJson;
  }


  /**
   * 用户登陆日志清空
   * 
   * @param inForm
   * @return
   */
  public EasyJson<Object> doClear(Platformz9904Form inForm) {

    platformLoginInfo01Mapper.clearLoginInfo();

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setSuccess(true);
    easyJson.setMsg("清空成功");
    return easyJson;
  }
}

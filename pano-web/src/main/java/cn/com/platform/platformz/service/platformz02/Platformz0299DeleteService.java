package cn.com.platform.platformz.service.platformz02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.mapper.common01.PlatformCompany01Mapper;
import cn.com.platform.platform.model.common.PlatformCompany;
import cn.com.platform.platformz.form.platformz02.Platformz0299Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 公司管理删除service。
 * 
 * @author 代仁宗
 * @date 2019-06-17
 *
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Platformz0299DeleteService extends BaseService {

  @Autowired
  PlatformCompany01Mapper platformzCompany01Mapper;

  /**
   * 删除。
   * 
   * @param inForm Platformz0299Form
   * @throws SystemException 异常的场合
   */
  public EasyJson<PlatformCompany> doDelete(Platformz0299Form inForm) throws SystemException {
    if (!ObjectUtils.isEmpty(inForm.uniqueKeyList)) {
      for (Object companyId : inForm.uniqueKeyList) {
        // 检索用户信息
        PlatformCompany platformzCompany =
            platformzCompany01Mapper.selectByPrimaryKey(ObjectUtils.getDisplayString(companyId));
        if (platformzCompany != null) {
          // 删除用户信息
          platformzCompany01Mapper.deleteByPrimaryKey(platformzCompany.companyId);
        }
      }
    }
    EasyJson<PlatformCompany> easyJson = new EasyJson<PlatformCompany>();
    easyJson.setSuccess(true);
    easyJson.setMsg("删除成功");
    return easyJson;
  }

}

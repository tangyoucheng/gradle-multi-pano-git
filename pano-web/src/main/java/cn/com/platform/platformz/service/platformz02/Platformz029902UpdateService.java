package cn.com.platform.platformz.service.platformz02;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.mapper.common01.PlatformCompany01Mapper;
import cn.com.platform.platform.model.common.PlatformCompany;
import cn.com.platform.platform.model.common.PlatformCompanyQuery;
import cn.com.platform.platform.model.common.PlatformCompanyQuery.Criteria;
import cn.com.platform.platformz.form.platformz02.Platformz029902Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 角色更新service
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
@Service
@Transactional(rollbackFor=Throwable.class)
public class Platformz029902UpdateService extends BaseService {

  @Autowired
  PlatformCompany01Mapper platformzCompany01Mapper;

  public EasyJson<PlatformCompany> doUpdate(Platformz029902Form inForm) throws SystemException {

    PlatformCompanyQuery platformzCompanyQuery = new PlatformCompanyQuery();
    Criteria criteria = platformzCompanyQuery.createCriteria();
    criteria.andCompanyIdEqualTo(inForm.getCompanyId());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformCompany> companys = platformzCompany01Mapper.selectByBaseModel(platformzCompanyQuery);

    if (companys.isEmpty() || companys.size() == 0) {
      EasyJson<PlatformCompany> easyJson = new EasyJson<PlatformCompany>();
      easyJson.setSuccess(false);
      easyJson.setMsg("公司已被删除");
      return easyJson;
    } else {
      PlatformCompany platformzCompany = companys.get(0);
      platformzCompany.setCompanyName(inForm.getCompanyName());
      updateAudit(platformzCompany);
      // 更新
      platformzCompany01Mapper.updateByPrimaryKey(platformzCompany);
    }

    EasyJson<PlatformCompany> easyJson = new EasyJson<PlatformCompany>();
    easyJson.setSuccess(true);
    easyJson.setMsg("更新成功");
    return easyJson;
  }
}

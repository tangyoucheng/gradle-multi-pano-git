package cn.com.platform.platform.service.platform03;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.platform.platform.model.common.PlatformCompany;
import cn.com.platform.platform.model.common.PlatformCompanyQuery;
import cn.com.platform.platform.model.common.PlatformCompanyQuery.Criteria;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.framework.util.FwDateUtils;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.platform.form.platform03.Platform039901Form;
import cn.com.platform.platform.mapper.common01.PlatformCompany01Mapper;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 公司管理新增service
 * 
 * @author 代仁宗
 * @date 2019-06-17
 */
@Service
@Transactional(rollbackFor=Throwable.class)
public class Platform039901EntryService extends BaseService {

  @Autowired
  PlatformCompany01Mapper platformzCompany01Mapper;

  public EasyJson<PlatformCompany> doEntry(Platform039901Form inForm) throws SystemException {

    PlatformCompanyQuery platformzCompanyQuery = new PlatformCompanyQuery();
    Criteria criteria = platformzCompanyQuery.createCriteria();
    criteria.andCompanyNameEqualTo(inForm.getCompanyName());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformCompany> companys = platformzCompany01Mapper.selectByBaseModel(platformzCompanyQuery);

    if (!companys.isEmpty() && companys.size() > 0) {
      EasyJson<PlatformCompany> easyJson = new EasyJson<PlatformCompany>();
      easyJson.setSuccess(false);
      easyJson.setMsg("公司已存在");
      return easyJson;
    } else {
      PlatformCompany platformzCompany = new PlatformCompany();
      platformzCompany.setCompanyId(FwStringUtils.getUniqueId());
      platformzCompany.setCompanyName(inForm.getCompanyName());

      createAudit(platformzCompany);
      // 新建
      platformzCompany01Mapper.insert(platformzCompany);
    }

    EasyJson<PlatformCompany> easyJson = new EasyJson<PlatformCompany>();
    easyJson.setSuccess(true);
    easyJson.setMsg("登录成功");
    return easyJson;
  }
}

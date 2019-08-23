package cn.com.platform.platform.service.platform03;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.com.platform.platform.model.common.PlatformCompany;
import cn.com.platform.platform.model.common.PlatformCompanyQuery;
import cn.com.platform.platform.model.common.PlatformCompanyQuery.Criteria;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.form.platform03.Platform039902Form;
import cn.com.platform.platform.mapper.common01.PlatformCompany01Mapper;
import cn.com.platform.web.BaseService;

/**
 * 公司管理更新service。
 * 
 * @author 代仁宗
 * @date 2019-06-17
 *
 */
@Service
public class Platform039902InitService extends BaseService {

  @Autowired
  PlatformCompany01Mapper platformzCompany01Mapper;

  /**
   * 初始化处理。
   * 
   * @param inForm Platform039902Form
   * @throws SystemException 异常的场合
   */
  public void doInit(Platform039902Form inForm) throws SystemException {

    PlatformCompanyQuery platformzCompanyQuery = new PlatformCompanyQuery();
    Criteria criteria = platformzCompanyQuery.createCriteria();
    criteria.andCompanyIdEqualTo(inForm.getCompanyId());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformCompany> companys = platformzCompany01Mapper.selectByBaseModel(platformzCompanyQuery);

    if (!companys.isEmpty() && companys.size() == 1) {
      PlatformCompany platformzCompany = companys.get(0);
      inForm.setCompanyId(platformzCompany.getCompanyId());
      inForm.setCompanyName(platformzCompany.getCompanyName());
    }

    // 保存原始数据
    try {
      inForm.editBeforeDataJson = objectMapper.writeValueAsString(inForm);
    } catch (JsonProcessingException e) {
      throw new SystemException(e);
    }

  }
}

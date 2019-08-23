package cn.com.platform.platform.mapper.common01;

import java.util.HashMap;
import java.util.List;
import cn.com.platform.platform.mapper.common.PlatformCompanyMapper;
import cn.com.platform.platform.model.common.PlatformCompany;
import cn.com.platform.platform.model.common.PlatformCompanyQuery;

/**
 * 公司管理Mapper 。
 * 
 * @author 代仁宗
 * @date 2019-06-17
 *
 */
public interface PlatformCompany01Mapper extends PlatformCompanyMapper {

  /**
   * 通过基础model删除数据 。
   *
   * @mbg.generated
   */
  int deleteByBaseModel(PlatformCompanyQuery example);

  /**
   * 通过基础model检索数据 。
   *
   * @mbg.generated
   */
  List<PlatformCompany> selectByBaseModel(PlatformCompanyQuery example);


  /**
   * 检索未被删除的所有公司的数量 。
   * 
   * @param parameter parameter
   * @return
   */
  long selectCompanyCount(HashMap<?, ?> parameter);


  /**
   * 检索未被删除的所有公司 。
   * 
   * @param parameter parameter
   * @return
   */
  List<PlatformCompany> selectCompanyInfo(HashMap<?, ?> parameter);
}

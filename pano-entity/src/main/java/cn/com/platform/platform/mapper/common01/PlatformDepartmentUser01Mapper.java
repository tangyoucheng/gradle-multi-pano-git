package cn.com.platform.platform.mapper.common01;

import java.util.List;
import cn.com.platform.platform.mapper.common.PlatformDepartmentUserMapper;
import cn.com.platform.platform.model.common.PlatformDepartmentUser;
import cn.com.platform.platform.model.common.PlatformDepartmentUserQuery;

public interface PlatformDepartmentUser01Mapper extends PlatformDepartmentUserMapper {

    /**
     * 通过基础model删除数据
     *
     * @mbg.generated
     */
    int deleteByBaseModel(PlatformDepartmentUserQuery example);

    /**
     * 通过基础model检索数据
     *
     * @mbg.generated
     */
    List<PlatformDepartmentUser> selectByBaseModel(PlatformDepartmentUserQuery example);
    /**
     * 伦理删除部门-用户表
     * 
     * @param memberId
     */
    void deleteByDepartmentUserId(String memberId);
  }
package cn.com.platform.platform.mapper.common;

import cn.com.platform.platform.model.common.PlatformMember;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlatformMemberMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_member
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String memberId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_member
     *
     * @mbg.generated
     */
    int insert(PlatformMember record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_member
     *
     * @mbg.generated
     */
    int insertSelective(PlatformMember record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_member
     *
     * @mbg.generated
     */
    PlatformMember selectByPrimaryKey(String memberId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_member
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PlatformMember record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_member
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PlatformMember record);
}
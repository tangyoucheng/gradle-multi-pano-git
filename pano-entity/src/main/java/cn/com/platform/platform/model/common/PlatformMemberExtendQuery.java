package cn.com.platform.platform.model.common;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlatformMemberExtendQuery {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table platform_member_extend
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table platform_member_extend
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table platform_member_extend
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_member_extend
     *
     * @mbg.generated
     */
    public PlatformMemberExtendQuery() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_member_extend
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_member_extend
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_member_extend
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_member_extend
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_member_extend
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_member_extend
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_member_extend
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_member_extend
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_member_extend
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_member_extend
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table platform_member_extend
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andMemberIdIsNull() {
            addCriterion("MEMBER_ID is null");
            return (Criteria) this;
        }

        public Criteria andMemberIdIsNotNull() {
            addCriterion("MEMBER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andMemberIdEqualTo(String value) {
            addCriterion("MEMBER_ID =", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotEqualTo(String value) {
            addCriterion("MEMBER_ID <>", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThan(String value) {
            addCriterion("MEMBER_ID >", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThanOrEqualTo(String value) {
            addCriterion("MEMBER_ID >=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThan(String value) {
            addCriterion("MEMBER_ID <", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThanOrEqualTo(String value) {
            addCriterion("MEMBER_ID <=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLike(String value) {
            addCriterion("MEMBER_ID like", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotLike(String value) {
            addCriterion("MEMBER_ID not like", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdIn(List<String> values) {
            addCriterion("MEMBER_ID in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotIn(List<String> values) {
            addCriterion("MEMBER_ID not in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdBetween(String value1, String value2) {
            addCriterion("MEMBER_ID between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotBetween(String value1, String value2) {
            addCriterion("MEMBER_ID not between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterStatusIsNull() {
            addCriterion("MEMBER_DEREGISTER_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterStatusIsNotNull() {
            addCriterion("MEMBER_DEREGISTER_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterStatusEqualTo(String value) {
            addCriterion("MEMBER_DEREGISTER_STATUS =", value, "memberDeregisterStatus");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterStatusNotEqualTo(String value) {
            addCriterion("MEMBER_DEREGISTER_STATUS <>", value, "memberDeregisterStatus");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterStatusGreaterThan(String value) {
            addCriterion("MEMBER_DEREGISTER_STATUS >", value, "memberDeregisterStatus");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterStatusGreaterThanOrEqualTo(String value) {
            addCriterion("MEMBER_DEREGISTER_STATUS >=", value, "memberDeregisterStatus");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterStatusLessThan(String value) {
            addCriterion("MEMBER_DEREGISTER_STATUS <", value, "memberDeregisterStatus");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterStatusLessThanOrEqualTo(String value) {
            addCriterion("MEMBER_DEREGISTER_STATUS <=", value, "memberDeregisterStatus");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterStatusLike(String value) {
            addCriterion("MEMBER_DEREGISTER_STATUS like", value, "memberDeregisterStatus");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterStatusNotLike(String value) {
            addCriterion("MEMBER_DEREGISTER_STATUS not like", value, "memberDeregisterStatus");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterStatusIn(List<String> values) {
            addCriterion("MEMBER_DEREGISTER_STATUS in", values, "memberDeregisterStatus");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterStatusNotIn(List<String> values) {
            addCriterion("MEMBER_DEREGISTER_STATUS not in", values, "memberDeregisterStatus");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterStatusBetween(String value1, String value2) {
            addCriterion("MEMBER_DEREGISTER_STATUS between", value1, value2, "memberDeregisterStatus");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterStatusNotBetween(String value1, String value2) {
            addCriterion("MEMBER_DEREGISTER_STATUS not between", value1, value2, "memberDeregisterStatus");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterReasonIsNull() {
            addCriterion("MEMBER_DEREGISTER_REASON is null");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterReasonIsNotNull() {
            addCriterion("MEMBER_DEREGISTER_REASON is not null");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterReasonEqualTo(String value) {
            addCriterion("MEMBER_DEREGISTER_REASON =", value, "memberDeregisterReason");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterReasonNotEqualTo(String value) {
            addCriterion("MEMBER_DEREGISTER_REASON <>", value, "memberDeregisterReason");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterReasonGreaterThan(String value) {
            addCriterion("MEMBER_DEREGISTER_REASON >", value, "memberDeregisterReason");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterReasonGreaterThanOrEqualTo(String value) {
            addCriterion("MEMBER_DEREGISTER_REASON >=", value, "memberDeregisterReason");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterReasonLessThan(String value) {
            addCriterion("MEMBER_DEREGISTER_REASON <", value, "memberDeregisterReason");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterReasonLessThanOrEqualTo(String value) {
            addCriterion("MEMBER_DEREGISTER_REASON <=", value, "memberDeregisterReason");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterReasonLike(String value) {
            addCriterion("MEMBER_DEREGISTER_REASON like", value, "memberDeregisterReason");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterReasonNotLike(String value) {
            addCriterion("MEMBER_DEREGISTER_REASON not like", value, "memberDeregisterReason");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterReasonIn(List<String> values) {
            addCriterion("MEMBER_DEREGISTER_REASON in", values, "memberDeregisterReason");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterReasonNotIn(List<String> values) {
            addCriterion("MEMBER_DEREGISTER_REASON not in", values, "memberDeregisterReason");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterReasonBetween(String value1, String value2) {
            addCriterion("MEMBER_DEREGISTER_REASON between", value1, value2, "memberDeregisterReason");
            return (Criteria) this;
        }

        public Criteria andMemberDeregisterReasonNotBetween(String value1, String value2) {
            addCriterion("MEMBER_DEREGISTER_REASON not between", value1, value2, "memberDeregisterReason");
            return (Criteria) this;
        }

        public Criteria andMemberRegisterTimeIsNull() {
            addCriterion("MEMBER_REGISTER_TIME is null");
            return (Criteria) this;
        }

        public Criteria andMemberRegisterTimeIsNotNull() {
            addCriterion("MEMBER_REGISTER_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andMemberRegisterTimeEqualTo(LocalDateTime value) {
            addCriterion("MEMBER_REGISTER_TIME =", value, "memberRegisterTime");
            return (Criteria) this;
        }

        public Criteria andMemberRegisterTimeNotEqualTo(LocalDateTime value) {
            addCriterion("MEMBER_REGISTER_TIME <>", value, "memberRegisterTime");
            return (Criteria) this;
        }

        public Criteria andMemberRegisterTimeGreaterThan(LocalDateTime value) {
            addCriterion("MEMBER_REGISTER_TIME >", value, "memberRegisterTime");
            return (Criteria) this;
        }

        public Criteria andMemberRegisterTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("MEMBER_REGISTER_TIME >=", value, "memberRegisterTime");
            return (Criteria) this;
        }

        public Criteria andMemberRegisterTimeLessThan(LocalDateTime value) {
            addCriterion("MEMBER_REGISTER_TIME <", value, "memberRegisterTime");
            return (Criteria) this;
        }

        public Criteria andMemberRegisterTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("MEMBER_REGISTER_TIME <=", value, "memberRegisterTime");
            return (Criteria) this;
        }

        public Criteria andMemberRegisterTimeIn(List<LocalDateTime> values) {
            addCriterion("MEMBER_REGISTER_TIME in", values, "memberRegisterTime");
            return (Criteria) this;
        }

        public Criteria andMemberRegisterTimeNotIn(List<LocalDateTime> values) {
            addCriterion("MEMBER_REGISTER_TIME not in", values, "memberRegisterTime");
            return (Criteria) this;
        }

        public Criteria andMemberRegisterTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("MEMBER_REGISTER_TIME between", value1, value2, "memberRegisterTime");
            return (Criteria) this;
        }

        public Criteria andMemberRegisterTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("MEMBER_REGISTER_TIME not between", value1, value2, "memberRegisterTime");
            return (Criteria) this;
        }

        public Criteria andMemberRealNameIsNull() {
            addCriterion("MEMBER_REAL_NAME is null");
            return (Criteria) this;
        }

        public Criteria andMemberRealNameIsNotNull() {
            addCriterion("MEMBER_REAL_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andMemberRealNameEqualTo(String value) {
            addCriterion("MEMBER_REAL_NAME =", value, "memberRealName");
            return (Criteria) this;
        }

        public Criteria andMemberRealNameNotEqualTo(String value) {
            addCriterion("MEMBER_REAL_NAME <>", value, "memberRealName");
            return (Criteria) this;
        }

        public Criteria andMemberRealNameGreaterThan(String value) {
            addCriterion("MEMBER_REAL_NAME >", value, "memberRealName");
            return (Criteria) this;
        }

        public Criteria andMemberRealNameGreaterThanOrEqualTo(String value) {
            addCriterion("MEMBER_REAL_NAME >=", value, "memberRealName");
            return (Criteria) this;
        }

        public Criteria andMemberRealNameLessThan(String value) {
            addCriterion("MEMBER_REAL_NAME <", value, "memberRealName");
            return (Criteria) this;
        }

        public Criteria andMemberRealNameLessThanOrEqualTo(String value) {
            addCriterion("MEMBER_REAL_NAME <=", value, "memberRealName");
            return (Criteria) this;
        }

        public Criteria andMemberRealNameLike(String value) {
            addCriterion("MEMBER_REAL_NAME like", value, "memberRealName");
            return (Criteria) this;
        }

        public Criteria andMemberRealNameNotLike(String value) {
            addCriterion("MEMBER_REAL_NAME not like", value, "memberRealName");
            return (Criteria) this;
        }

        public Criteria andMemberRealNameIn(List<String> values) {
            addCriterion("MEMBER_REAL_NAME in", values, "memberRealName");
            return (Criteria) this;
        }

        public Criteria andMemberRealNameNotIn(List<String> values) {
            addCriterion("MEMBER_REAL_NAME not in", values, "memberRealName");
            return (Criteria) this;
        }

        public Criteria andMemberRealNameBetween(String value1, String value2) {
            addCriterion("MEMBER_REAL_NAME between", value1, value2, "memberRealName");
            return (Criteria) this;
        }

        public Criteria andMemberRealNameNotBetween(String value1, String value2) {
            addCriterion("MEMBER_REAL_NAME not between", value1, value2, "memberRealName");
            return (Criteria) this;
        }

        public Criteria andMemberIdcardIsNull() {
            addCriterion("MEMBER_IDCARD is null");
            return (Criteria) this;
        }

        public Criteria andMemberIdcardIsNotNull() {
            addCriterion("MEMBER_IDCARD is not null");
            return (Criteria) this;
        }

        public Criteria andMemberIdcardEqualTo(String value) {
            addCriterion("MEMBER_IDCARD =", value, "memberIdcard");
            return (Criteria) this;
        }

        public Criteria andMemberIdcardNotEqualTo(String value) {
            addCriterion("MEMBER_IDCARD <>", value, "memberIdcard");
            return (Criteria) this;
        }

        public Criteria andMemberIdcardGreaterThan(String value) {
            addCriterion("MEMBER_IDCARD >", value, "memberIdcard");
            return (Criteria) this;
        }

        public Criteria andMemberIdcardGreaterThanOrEqualTo(String value) {
            addCriterion("MEMBER_IDCARD >=", value, "memberIdcard");
            return (Criteria) this;
        }

        public Criteria andMemberIdcardLessThan(String value) {
            addCriterion("MEMBER_IDCARD <", value, "memberIdcard");
            return (Criteria) this;
        }

        public Criteria andMemberIdcardLessThanOrEqualTo(String value) {
            addCriterion("MEMBER_IDCARD <=", value, "memberIdcard");
            return (Criteria) this;
        }

        public Criteria andMemberIdcardLike(String value) {
            addCriterion("MEMBER_IDCARD like", value, "memberIdcard");
            return (Criteria) this;
        }

        public Criteria andMemberIdcardNotLike(String value) {
            addCriterion("MEMBER_IDCARD not like", value, "memberIdcard");
            return (Criteria) this;
        }

        public Criteria andMemberIdcardIn(List<String> values) {
            addCriterion("MEMBER_IDCARD in", values, "memberIdcard");
            return (Criteria) this;
        }

        public Criteria andMemberIdcardNotIn(List<String> values) {
            addCriterion("MEMBER_IDCARD not in", values, "memberIdcard");
            return (Criteria) this;
        }

        public Criteria andMemberIdcardBetween(String value1, String value2) {
            addCriterion("MEMBER_IDCARD between", value1, value2, "memberIdcard");
            return (Criteria) this;
        }

        public Criteria andMemberIdcardNotBetween(String value1, String value2) {
            addCriterion("MEMBER_IDCARD not between", value1, value2, "memberIdcard");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNull() {
            addCriterion("REMARKS is null");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNotNull() {
            addCriterion("REMARKS is not null");
            return (Criteria) this;
        }

        public Criteria andRemarksEqualTo(String value) {
            addCriterion("REMARKS =", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotEqualTo(String value) {
            addCriterion("REMARKS <>", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThan(String value) {
            addCriterion("REMARKS >", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("REMARKS >=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThan(String value) {
            addCriterion("REMARKS <", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThanOrEqualTo(String value) {
            addCriterion("REMARKS <=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLike(String value) {
            addCriterion("REMARKS like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotLike(String value) {
            addCriterion("REMARKS not like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksIn(List<String> values) {
            addCriterion("REMARKS in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotIn(List<String> values) {
            addCriterion("REMARKS not in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksBetween(String value1, String value2) {
            addCriterion("REMARKS between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotBetween(String value1, String value2) {
            addCriterion("REMARKS not between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIsNull() {
            addCriterion("DELETE_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIsNotNull() {
            addCriterion("DELETE_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagEqualTo(Boolean value) {
            addCriterion("DELETE_FLAG =", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotEqualTo(Boolean value) {
            addCriterion("DELETE_FLAG <>", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagGreaterThan(Boolean value) {
            addCriterion("DELETE_FLAG >", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("DELETE_FLAG >=", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLessThan(Boolean value) {
            addCriterion("DELETE_FLAG <", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("DELETE_FLAG <=", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIn(List<Boolean> values) {
            addCriterion("DELETE_FLAG in", values, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotIn(List<Boolean> values) {
            addCriterion("DELETE_FLAG not in", values, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("DELETE_FLAG between", value1, value2, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("DELETE_FLAG not between", value1, value2, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNull() {
            addCriterion("CREATE_USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNotNull() {
            addCriterion("CREATE_USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdEqualTo(String value) {
            addCriterion("CREATE_USER_ID =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(String value) {
            addCriterion("CREATE_USER_ID <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(String value) {
            addCriterion("CREATE_USER_ID >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_USER_ID >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(String value) {
            addCriterion("CREATE_USER_ID <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(String value) {
            addCriterion("CREATE_USER_ID <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLike(String value) {
            addCriterion("CREATE_USER_ID like", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotLike(String value) {
            addCriterion("CREATE_USER_ID not like", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<String> values) {
            addCriterion("CREATE_USER_ID in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<String> values) {
            addCriterion("CREATE_USER_ID not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(String value1, String value2) {
            addCriterion("CREATE_USER_ID between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(String value1, String value2) {
            addCriterion("CREATE_USER_ID not between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("CREATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("CREATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(LocalDateTime value) {
            addCriterion("CREATE_DATE =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(LocalDateTime value) {
            addCriterion("CREATE_DATE <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(LocalDateTime value) {
            addCriterion("CREATE_DATE >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("CREATE_DATE >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(LocalDateTime value) {
            addCriterion("CREATE_DATE <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("CREATE_DATE <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<LocalDateTime> values) {
            addCriterion("CREATE_DATE in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<LocalDateTime> values) {
            addCriterion("CREATE_DATE not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("CREATE_DATE between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("CREATE_DATE not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdIsNull() {
            addCriterion("LAST_UPDATE_USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdIsNotNull() {
            addCriterion("LAST_UPDATE_USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdEqualTo(String value) {
            addCriterion("LAST_UPDATE_USER_ID =", value, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdNotEqualTo(String value) {
            addCriterion("LAST_UPDATE_USER_ID <>", value, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdGreaterThan(String value) {
            addCriterion("LAST_UPDATE_USER_ID >", value, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("LAST_UPDATE_USER_ID >=", value, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdLessThan(String value) {
            addCriterion("LAST_UPDATE_USER_ID <", value, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdLessThanOrEqualTo(String value) {
            addCriterion("LAST_UPDATE_USER_ID <=", value, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdLike(String value) {
            addCriterion("LAST_UPDATE_USER_ID like", value, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdNotLike(String value) {
            addCriterion("LAST_UPDATE_USER_ID not like", value, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdIn(List<String> values) {
            addCriterion("LAST_UPDATE_USER_ID in", values, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdNotIn(List<String> values) {
            addCriterion("LAST_UPDATE_USER_ID not in", values, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdBetween(String value1, String value2) {
            addCriterion("LAST_UPDATE_USER_ID between", value1, value2, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdNotBetween(String value1, String value2) {
            addCriterion("LAST_UPDATE_USER_ID not between", value1, value2, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateIsNull() {
            addCriterion("LAST_UPDATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateIsNotNull() {
            addCriterion("LAST_UPDATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateEqualTo(LocalDateTime value) {
            addCriterion("LAST_UPDATE_DATE =", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateNotEqualTo(LocalDateTime value) {
            addCriterion("LAST_UPDATE_DATE <>", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateGreaterThan(LocalDateTime value) {
            addCriterion("LAST_UPDATE_DATE >", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("LAST_UPDATE_DATE >=", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateLessThan(LocalDateTime value) {
            addCriterion("LAST_UPDATE_DATE <", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("LAST_UPDATE_DATE <=", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateIn(List<LocalDateTime> values) {
            addCriterion("LAST_UPDATE_DATE in", values, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateNotIn(List<LocalDateTime> values) {
            addCriterion("LAST_UPDATE_DATE not in", values, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("LAST_UPDATE_DATE between", value1, value2, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("LAST_UPDATE_DATE not between", value1, value2, "lastUpdateDate");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table platform_member_extend
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table platform_member_extend
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
package cn.com.platform.platform.model.common;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlatformAdminUserQuery {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table platform_admin_user
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table platform_admin_user
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table platform_admin_user
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_user
     *
     * @mbg.generated
     */
    public PlatformAdminUserQuery() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_user
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_user
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_user
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_user
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_user
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_user
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_user
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
     * This method corresponds to the database table platform_admin_user
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
     * This method corresponds to the database table platform_admin_user
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_user
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
     * This class corresponds to the database table platform_admin_user
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

        public Criteria andAdminIdIsNull() {
            addCriterion("ADMIN_ID is null");
            return (Criteria) this;
        }

        public Criteria andAdminIdIsNotNull() {
            addCriterion("ADMIN_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAdminIdEqualTo(String value) {
            addCriterion("ADMIN_ID =", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdNotEqualTo(String value) {
            addCriterion("ADMIN_ID <>", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdGreaterThan(String value) {
            addCriterion("ADMIN_ID >", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdGreaterThanOrEqualTo(String value) {
            addCriterion("ADMIN_ID >=", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdLessThan(String value) {
            addCriterion("ADMIN_ID <", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdLessThanOrEqualTo(String value) {
            addCriterion("ADMIN_ID <=", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdLike(String value) {
            addCriterion("ADMIN_ID like", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdNotLike(String value) {
            addCriterion("ADMIN_ID not like", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdIn(List<String> values) {
            addCriterion("ADMIN_ID in", values, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdNotIn(List<String> values) {
            addCriterion("ADMIN_ID not in", values, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdBetween(String value1, String value2) {
            addCriterion("ADMIN_ID between", value1, value2, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdNotBetween(String value1, String value2) {
            addCriterion("ADMIN_ID not between", value1, value2, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminLoginIdIsNull() {
            addCriterion("ADMIN_LOGIN_ID is null");
            return (Criteria) this;
        }

        public Criteria andAdminLoginIdIsNotNull() {
            addCriterion("ADMIN_LOGIN_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAdminLoginIdEqualTo(String value) {
            addCriterion("ADMIN_LOGIN_ID =", value, "adminLoginId");
            return (Criteria) this;
        }

        public Criteria andAdminLoginIdNotEqualTo(String value) {
            addCriterion("ADMIN_LOGIN_ID <>", value, "adminLoginId");
            return (Criteria) this;
        }

        public Criteria andAdminLoginIdGreaterThan(String value) {
            addCriterion("ADMIN_LOGIN_ID >", value, "adminLoginId");
            return (Criteria) this;
        }

        public Criteria andAdminLoginIdGreaterThanOrEqualTo(String value) {
            addCriterion("ADMIN_LOGIN_ID >=", value, "adminLoginId");
            return (Criteria) this;
        }

        public Criteria andAdminLoginIdLessThan(String value) {
            addCriterion("ADMIN_LOGIN_ID <", value, "adminLoginId");
            return (Criteria) this;
        }

        public Criteria andAdminLoginIdLessThanOrEqualTo(String value) {
            addCriterion("ADMIN_LOGIN_ID <=", value, "adminLoginId");
            return (Criteria) this;
        }

        public Criteria andAdminLoginIdLike(String value) {
            addCriterion("ADMIN_LOGIN_ID like", value, "adminLoginId");
            return (Criteria) this;
        }

        public Criteria andAdminLoginIdNotLike(String value) {
            addCriterion("ADMIN_LOGIN_ID not like", value, "adminLoginId");
            return (Criteria) this;
        }

        public Criteria andAdminLoginIdIn(List<String> values) {
            addCriterion("ADMIN_LOGIN_ID in", values, "adminLoginId");
            return (Criteria) this;
        }

        public Criteria andAdminLoginIdNotIn(List<String> values) {
            addCriterion("ADMIN_LOGIN_ID not in", values, "adminLoginId");
            return (Criteria) this;
        }

        public Criteria andAdminLoginIdBetween(String value1, String value2) {
            addCriterion("ADMIN_LOGIN_ID between", value1, value2, "adminLoginId");
            return (Criteria) this;
        }

        public Criteria andAdminLoginIdNotBetween(String value1, String value2) {
            addCriterion("ADMIN_LOGIN_ID not between", value1, value2, "adminLoginId");
            return (Criteria) this;
        }

        public Criteria andAdminNameIsNull() {
            addCriterion("ADMIN_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAdminNameIsNotNull() {
            addCriterion("ADMIN_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAdminNameEqualTo(String value) {
            addCriterion("ADMIN_NAME =", value, "adminName");
            return (Criteria) this;
        }

        public Criteria andAdminNameNotEqualTo(String value) {
            addCriterion("ADMIN_NAME <>", value, "adminName");
            return (Criteria) this;
        }

        public Criteria andAdminNameGreaterThan(String value) {
            addCriterion("ADMIN_NAME >", value, "adminName");
            return (Criteria) this;
        }

        public Criteria andAdminNameGreaterThanOrEqualTo(String value) {
            addCriterion("ADMIN_NAME >=", value, "adminName");
            return (Criteria) this;
        }

        public Criteria andAdminNameLessThan(String value) {
            addCriterion("ADMIN_NAME <", value, "adminName");
            return (Criteria) this;
        }

        public Criteria andAdminNameLessThanOrEqualTo(String value) {
            addCriterion("ADMIN_NAME <=", value, "adminName");
            return (Criteria) this;
        }

        public Criteria andAdminNameLike(String value) {
            addCriterion("ADMIN_NAME like", value, "adminName");
            return (Criteria) this;
        }

        public Criteria andAdminNameNotLike(String value) {
            addCriterion("ADMIN_NAME not like", value, "adminName");
            return (Criteria) this;
        }

        public Criteria andAdminNameIn(List<String> values) {
            addCriterion("ADMIN_NAME in", values, "adminName");
            return (Criteria) this;
        }

        public Criteria andAdminNameNotIn(List<String> values) {
            addCriterion("ADMIN_NAME not in", values, "adminName");
            return (Criteria) this;
        }

        public Criteria andAdminNameBetween(String value1, String value2) {
            addCriterion("ADMIN_NAME between", value1, value2, "adminName");
            return (Criteria) this;
        }

        public Criteria andAdminNameNotBetween(String value1, String value2) {
            addCriterion("ADMIN_NAME not between", value1, value2, "adminName");
            return (Criteria) this;
        }

        public Criteria andAdminPasswordIsNull() {
            addCriterion("ADMIN_PASSWORD is null");
            return (Criteria) this;
        }

        public Criteria andAdminPasswordIsNotNull() {
            addCriterion("ADMIN_PASSWORD is not null");
            return (Criteria) this;
        }

        public Criteria andAdminPasswordEqualTo(String value) {
            addCriterion("ADMIN_PASSWORD =", value, "adminPassword");
            return (Criteria) this;
        }

        public Criteria andAdminPasswordNotEqualTo(String value) {
            addCriterion("ADMIN_PASSWORD <>", value, "adminPassword");
            return (Criteria) this;
        }

        public Criteria andAdminPasswordGreaterThan(String value) {
            addCriterion("ADMIN_PASSWORD >", value, "adminPassword");
            return (Criteria) this;
        }

        public Criteria andAdminPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("ADMIN_PASSWORD >=", value, "adminPassword");
            return (Criteria) this;
        }

        public Criteria andAdminPasswordLessThan(String value) {
            addCriterion("ADMIN_PASSWORD <", value, "adminPassword");
            return (Criteria) this;
        }

        public Criteria andAdminPasswordLessThanOrEqualTo(String value) {
            addCriterion("ADMIN_PASSWORD <=", value, "adminPassword");
            return (Criteria) this;
        }

        public Criteria andAdminPasswordLike(String value) {
            addCriterion("ADMIN_PASSWORD like", value, "adminPassword");
            return (Criteria) this;
        }

        public Criteria andAdminPasswordNotLike(String value) {
            addCriterion("ADMIN_PASSWORD not like", value, "adminPassword");
            return (Criteria) this;
        }

        public Criteria andAdminPasswordIn(List<String> values) {
            addCriterion("ADMIN_PASSWORD in", values, "adminPassword");
            return (Criteria) this;
        }

        public Criteria andAdminPasswordNotIn(List<String> values) {
            addCriterion("ADMIN_PASSWORD not in", values, "adminPassword");
            return (Criteria) this;
        }

        public Criteria andAdminPasswordBetween(String value1, String value2) {
            addCriterion("ADMIN_PASSWORD between", value1, value2, "adminPassword");
            return (Criteria) this;
        }

        public Criteria andAdminPasswordNotBetween(String value1, String value2) {
            addCriterion("ADMIN_PASSWORD not between", value1, value2, "adminPassword");
            return (Criteria) this;
        }

        public Criteria andAdminEmailIsNull() {
            addCriterion("ADMIN_EMAIL is null");
            return (Criteria) this;
        }

        public Criteria andAdminEmailIsNotNull() {
            addCriterion("ADMIN_EMAIL is not null");
            return (Criteria) this;
        }

        public Criteria andAdminEmailEqualTo(String value) {
            addCriterion("ADMIN_EMAIL =", value, "adminEmail");
            return (Criteria) this;
        }

        public Criteria andAdminEmailNotEqualTo(String value) {
            addCriterion("ADMIN_EMAIL <>", value, "adminEmail");
            return (Criteria) this;
        }

        public Criteria andAdminEmailGreaterThan(String value) {
            addCriterion("ADMIN_EMAIL >", value, "adminEmail");
            return (Criteria) this;
        }

        public Criteria andAdminEmailGreaterThanOrEqualTo(String value) {
            addCriterion("ADMIN_EMAIL >=", value, "adminEmail");
            return (Criteria) this;
        }

        public Criteria andAdminEmailLessThan(String value) {
            addCriterion("ADMIN_EMAIL <", value, "adminEmail");
            return (Criteria) this;
        }

        public Criteria andAdminEmailLessThanOrEqualTo(String value) {
            addCriterion("ADMIN_EMAIL <=", value, "adminEmail");
            return (Criteria) this;
        }

        public Criteria andAdminEmailLike(String value) {
            addCriterion("ADMIN_EMAIL like", value, "adminEmail");
            return (Criteria) this;
        }

        public Criteria andAdminEmailNotLike(String value) {
            addCriterion("ADMIN_EMAIL not like", value, "adminEmail");
            return (Criteria) this;
        }

        public Criteria andAdminEmailIn(List<String> values) {
            addCriterion("ADMIN_EMAIL in", values, "adminEmail");
            return (Criteria) this;
        }

        public Criteria andAdminEmailNotIn(List<String> values) {
            addCriterion("ADMIN_EMAIL not in", values, "adminEmail");
            return (Criteria) this;
        }

        public Criteria andAdminEmailBetween(String value1, String value2) {
            addCriterion("ADMIN_EMAIL between", value1, value2, "adminEmail");
            return (Criteria) this;
        }

        public Criteria andAdminEmailNotBetween(String value1, String value2) {
            addCriterion("ADMIN_EMAIL not between", value1, value2, "adminEmail");
            return (Criteria) this;
        }

        public Criteria andAdminStatusIsNull() {
            addCriterion("ADMIN_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andAdminStatusIsNotNull() {
            addCriterion("ADMIN_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andAdminStatusEqualTo(String value) {
            addCriterion("ADMIN_STATUS =", value, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusNotEqualTo(String value) {
            addCriterion("ADMIN_STATUS <>", value, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusGreaterThan(String value) {
            addCriterion("ADMIN_STATUS >", value, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusGreaterThanOrEqualTo(String value) {
            addCriterion("ADMIN_STATUS >=", value, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusLessThan(String value) {
            addCriterion("ADMIN_STATUS <", value, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusLessThanOrEqualTo(String value) {
            addCriterion("ADMIN_STATUS <=", value, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusLike(String value) {
            addCriterion("ADMIN_STATUS like", value, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusNotLike(String value) {
            addCriterion("ADMIN_STATUS not like", value, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusIn(List<String> values) {
            addCriterion("ADMIN_STATUS in", values, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusNotIn(List<String> values) {
            addCriterion("ADMIN_STATUS not in", values, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusBetween(String value1, String value2) {
            addCriterion("ADMIN_STATUS between", value1, value2, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusNotBetween(String value1, String value2) {
            addCriterion("ADMIN_STATUS not between", value1, value2, "adminStatus");
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
     * This class corresponds to the database table platform_admin_user
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
     * This class corresponds to the database table platform_admin_user
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
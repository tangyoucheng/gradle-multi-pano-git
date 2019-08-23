package cn.com.pano.pano.model.common;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PanoHotspotUrlQuery {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pano_hotspot_url
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pano_hotspot_url
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pano_hotspot_url
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_hotspot_url
     *
     * @mbg.generated
     */
    public PanoHotspotUrlQuery() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_hotspot_url
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_hotspot_url
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_hotspot_url
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_hotspot_url
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_hotspot_url
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_hotspot_url
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_hotspot_url
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
     * This method corresponds to the database table pano_hotspot_url
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
     * This method corresponds to the database table pano_hotspot_url
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_hotspot_url
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
     * This class corresponds to the database table pano_hotspot_url
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

        public Criteria andHotspotIdIsNull() {
            addCriterion("hotspot_id is null");
            return (Criteria) this;
        }

        public Criteria andHotspotIdIsNotNull() {
            addCriterion("hotspot_id is not null");
            return (Criteria) this;
        }

        public Criteria andHotspotIdEqualTo(String value) {
            addCriterion("hotspot_id =", value, "hotspotId");
            return (Criteria) this;
        }

        public Criteria andHotspotIdNotEqualTo(String value) {
            addCriterion("hotspot_id <>", value, "hotspotId");
            return (Criteria) this;
        }

        public Criteria andHotspotIdGreaterThan(String value) {
            addCriterion("hotspot_id >", value, "hotspotId");
            return (Criteria) this;
        }

        public Criteria andHotspotIdGreaterThanOrEqualTo(String value) {
            addCriterion("hotspot_id >=", value, "hotspotId");
            return (Criteria) this;
        }

        public Criteria andHotspotIdLessThan(String value) {
            addCriterion("hotspot_id <", value, "hotspotId");
            return (Criteria) this;
        }

        public Criteria andHotspotIdLessThanOrEqualTo(String value) {
            addCriterion("hotspot_id <=", value, "hotspotId");
            return (Criteria) this;
        }

        public Criteria andHotspotIdLike(String value) {
            addCriterion("hotspot_id like", value, "hotspotId");
            return (Criteria) this;
        }

        public Criteria andHotspotIdNotLike(String value) {
            addCriterion("hotspot_id not like", value, "hotspotId");
            return (Criteria) this;
        }

        public Criteria andHotspotIdIn(List<String> values) {
            addCriterion("hotspot_id in", values, "hotspotId");
            return (Criteria) this;
        }

        public Criteria andHotspotIdNotIn(List<String> values) {
            addCriterion("hotspot_id not in", values, "hotspotId");
            return (Criteria) this;
        }

        public Criteria andHotspotIdBetween(String value1, String value2) {
            addCriterion("hotspot_id between", value1, value2, "hotspotId");
            return (Criteria) this;
        }

        public Criteria andHotspotIdNotBetween(String value1, String value2) {
            addCriterion("hotspot_id not between", value1, value2, "hotspotId");
            return (Criteria) this;
        }

        public Criteria andHotspotUrlObjectIdIsNull() {
            addCriterion("hotspot_url_object_id is null");
            return (Criteria) this;
        }

        public Criteria andHotspotUrlObjectIdIsNotNull() {
            addCriterion("hotspot_url_object_id is not null");
            return (Criteria) this;
        }

        public Criteria andHotspotUrlObjectIdEqualTo(String value) {
            addCriterion("hotspot_url_object_id =", value, "hotspotUrlObjectId");
            return (Criteria) this;
        }

        public Criteria andHotspotUrlObjectIdNotEqualTo(String value) {
            addCriterion("hotspot_url_object_id <>", value, "hotspotUrlObjectId");
            return (Criteria) this;
        }

        public Criteria andHotspotUrlObjectIdGreaterThan(String value) {
            addCriterion("hotspot_url_object_id >", value, "hotspotUrlObjectId");
            return (Criteria) this;
        }

        public Criteria andHotspotUrlObjectIdGreaterThanOrEqualTo(String value) {
            addCriterion("hotspot_url_object_id >=", value, "hotspotUrlObjectId");
            return (Criteria) this;
        }

        public Criteria andHotspotUrlObjectIdLessThan(String value) {
            addCriterion("hotspot_url_object_id <", value, "hotspotUrlObjectId");
            return (Criteria) this;
        }

        public Criteria andHotspotUrlObjectIdLessThanOrEqualTo(String value) {
            addCriterion("hotspot_url_object_id <=", value, "hotspotUrlObjectId");
            return (Criteria) this;
        }

        public Criteria andHotspotUrlObjectIdLike(String value) {
            addCriterion("hotspot_url_object_id like", value, "hotspotUrlObjectId");
            return (Criteria) this;
        }

        public Criteria andHotspotUrlObjectIdNotLike(String value) {
            addCriterion("hotspot_url_object_id not like", value, "hotspotUrlObjectId");
            return (Criteria) this;
        }

        public Criteria andHotspotUrlObjectIdIn(List<String> values) {
            addCriterion("hotspot_url_object_id in", values, "hotspotUrlObjectId");
            return (Criteria) this;
        }

        public Criteria andHotspotUrlObjectIdNotIn(List<String> values) {
            addCriterion("hotspot_url_object_id not in", values, "hotspotUrlObjectId");
            return (Criteria) this;
        }

        public Criteria andHotspotUrlObjectIdBetween(String value1, String value2) {
            addCriterion("hotspot_url_object_id between", value1, value2, "hotspotUrlObjectId");
            return (Criteria) this;
        }

        public Criteria andHotspotUrlObjectIdNotBetween(String value1, String value2) {
            addCriterion("hotspot_url_object_id not between", value1, value2, "hotspotUrlObjectId");
            return (Criteria) this;
        }

        public Criteria andSortKeyIsNull() {
            addCriterion("sort_key is null");
            return (Criteria) this;
        }

        public Criteria andSortKeyIsNotNull() {
            addCriterion("sort_key is not null");
            return (Criteria) this;
        }

        public Criteria andSortKeyEqualTo(BigDecimal value) {
            addCriterion("sort_key =", value, "sortKey");
            return (Criteria) this;
        }

        public Criteria andSortKeyNotEqualTo(BigDecimal value) {
            addCriterion("sort_key <>", value, "sortKey");
            return (Criteria) this;
        }

        public Criteria andSortKeyGreaterThan(BigDecimal value) {
            addCriterion("sort_key >", value, "sortKey");
            return (Criteria) this;
        }

        public Criteria andSortKeyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sort_key >=", value, "sortKey");
            return (Criteria) this;
        }

        public Criteria andSortKeyLessThan(BigDecimal value) {
            addCriterion("sort_key <", value, "sortKey");
            return (Criteria) this;
        }

        public Criteria andSortKeyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sort_key <=", value, "sortKey");
            return (Criteria) this;
        }

        public Criteria andSortKeyIn(List<BigDecimal> values) {
            addCriterion("sort_key in", values, "sortKey");
            return (Criteria) this;
        }

        public Criteria andSortKeyNotIn(List<BigDecimal> values) {
            addCriterion("sort_key not in", values, "sortKey");
            return (Criteria) this;
        }

        public Criteria andSortKeyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sort_key between", value1, value2, "sortKey");
            return (Criteria) this;
        }

        public Criteria andSortKeyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sort_key not between", value1, value2, "sortKey");
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
     * This class corresponds to the database table pano_hotspot_url
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
     * This class corresponds to the database table pano_hotspot_url
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
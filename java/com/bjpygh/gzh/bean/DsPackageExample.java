package com.bjpygh.gzh.bean;

import java.util.ArrayList;
import java.util.List;

public class DsPackageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DsPackageExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

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

        public Criteria andPackageidIsNull() {
            addCriterion("packageid is null");
            return (Criteria) this;
        }

        public Criteria andPackageidIsNotNull() {
            addCriterion("packageid is not null");
            return (Criteria) this;
        }

        public Criteria andPackageidEqualTo(Integer value) {
            addCriterion("packageid =", value, "packageid");
            return (Criteria) this;
        }

        public Criteria andPackageidNotEqualTo(Integer value) {
            addCriterion("packageid <>", value, "packageid");
            return (Criteria) this;
        }

        public Criteria andPackageidGreaterThan(Integer value) {
            addCriterion("packageid >", value, "packageid");
            return (Criteria) this;
        }

        public Criteria andPackageidGreaterThanOrEqualTo(Integer value) {
            addCriterion("packageid >=", value, "packageid");
            return (Criteria) this;
        }

        public Criteria andPackageidLessThan(Integer value) {
            addCriterion("packageid <", value, "packageid");
            return (Criteria) this;
        }

        public Criteria andPackageidLessThanOrEqualTo(Integer value) {
            addCriterion("packageid <=", value, "packageid");
            return (Criteria) this;
        }

        public Criteria andPackageidIn(List<Integer> values) {
            addCriterion("packageid in", values, "packageid");
            return (Criteria) this;
        }

        public Criteria andPackageidNotIn(List<Integer> values) {
            addCriterion("packageid not in", values, "packageid");
            return (Criteria) this;
        }

        public Criteria andPackageidBetween(Integer value1, Integer value2) {
            addCriterion("packageid between", value1, value2, "packageid");
            return (Criteria) this;
        }

        public Criteria andPackageidNotBetween(Integer value1, Integer value2) {
            addCriterion("packageid not between", value1, value2, "packageid");
            return (Criteria) this;
        }

        public Criteria andDsNameIsNull() {
            addCriterion("ds_name is null");
            return (Criteria) this;
        }

        public Criteria andDsNameIsNotNull() {
            addCriterion("ds_name is not null");
            return (Criteria) this;
        }

        public Criteria andDsNameEqualTo(String value) {
            addCriterion("ds_name =", value, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameNotEqualTo(String value) {
            addCriterion("ds_name <>", value, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameGreaterThan(String value) {
            addCriterion("ds_name >", value, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameGreaterThanOrEqualTo(String value) {
            addCriterion("ds_name >=", value, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameLessThan(String value) {
            addCriterion("ds_name <", value, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameLessThanOrEqualTo(String value) {
            addCriterion("ds_name <=", value, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameLike(String value) {
            addCriterion("ds_name like", value, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameNotLike(String value) {
            addCriterion("ds_name not like", value, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameIn(List<String> values) {
            addCriterion("ds_name in", values, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameNotIn(List<String> values) {
            addCriterion("ds_name not in", values, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameBetween(String value1, String value2) {
            addCriterion("ds_name between", value1, value2, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsNameNotBetween(String value1, String value2) {
            addCriterion("ds_name not between", value1, value2, "dsName");
            return (Criteria) this;
        }

        public Criteria andDsTypeIsNull() {
            addCriterion("ds_type is null");
            return (Criteria) this;
        }

        public Criteria andDsTypeIsNotNull() {
            addCriterion("ds_type is not null");
            return (Criteria) this;
        }

        public Criteria andDsTypeEqualTo(String value) {
            addCriterion("ds_type =", value, "dsType");
            return (Criteria) this;
        }

        public Criteria andDsTypeNotEqualTo(String value) {
            addCriterion("ds_type <>", value, "dsType");
            return (Criteria) this;
        }

        public Criteria andDsTypeGreaterThan(String value) {
            addCriterion("ds_type >", value, "dsType");
            return (Criteria) this;
        }

        public Criteria andDsTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ds_type >=", value, "dsType");
            return (Criteria) this;
        }

        public Criteria andDsTypeLessThan(String value) {
            addCriterion("ds_type <", value, "dsType");
            return (Criteria) this;
        }

        public Criteria andDsTypeLessThanOrEqualTo(String value) {
            addCriterion("ds_type <=", value, "dsType");
            return (Criteria) this;
        }

        public Criteria andDsTypeLike(String value) {
            addCriterion("ds_type like", value, "dsType");
            return (Criteria) this;
        }

        public Criteria andDsTypeNotLike(String value) {
            addCriterion("ds_type not like", value, "dsType");
            return (Criteria) this;
        }

        public Criteria andDsTypeIn(List<String> values) {
            addCriterion("ds_type in", values, "dsType");
            return (Criteria) this;
        }

        public Criteria andDsTypeNotIn(List<String> values) {
            addCriterion("ds_type not in", values, "dsType");
            return (Criteria) this;
        }

        public Criteria andDsTypeBetween(String value1, String value2) {
            addCriterion("ds_type between", value1, value2, "dsType");
            return (Criteria) this;
        }

        public Criteria andDsTypeNotBetween(String value1, String value2) {
            addCriterion("ds_type not between", value1, value2, "dsType");
            return (Criteria) this;
        }

        public Criteria andModelsIsNull() {
            addCriterion("models is null");
            return (Criteria) this;
        }

        public Criteria andModelsIsNotNull() {
            addCriterion("models is not null");
            return (Criteria) this;
        }

        public Criteria andModelsEqualTo(String value) {
            addCriterion("models =", value, "models");
            return (Criteria) this;
        }

        public Criteria andModelsNotEqualTo(String value) {
            addCriterion("models <>", value, "models");
            return (Criteria) this;
        }

        public Criteria andModelsGreaterThan(String value) {
            addCriterion("models >", value, "models");
            return (Criteria) this;
        }

        public Criteria andModelsGreaterThanOrEqualTo(String value) {
            addCriterion("models >=", value, "models");
            return (Criteria) this;
        }

        public Criteria andModelsLessThan(String value) {
            addCriterion("models <", value, "models");
            return (Criteria) this;
        }

        public Criteria andModelsLessThanOrEqualTo(String value) {
            addCriterion("models <=", value, "models");
            return (Criteria) this;
        }

        public Criteria andModelsLike(String value) {
            addCriterion("models like", value, "models");
            return (Criteria) this;
        }

        public Criteria andModelsNotLike(String value) {
            addCriterion("models not like", value, "models");
            return (Criteria) this;
        }

        public Criteria andModelsIn(List<String> values) {
            addCriterion("models in", values, "models");
            return (Criteria) this;
        }

        public Criteria andModelsNotIn(List<String> values) {
            addCriterion("models not in", values, "models");
            return (Criteria) this;
        }

        public Criteria andModelsBetween(String value1, String value2) {
            addCriterion("models between", value1, value2, "models");
            return (Criteria) this;
        }

        public Criteria andModelsNotBetween(String value1, String value2) {
            addCriterion("models not between", value1, value2, "models");
            return (Criteria) this;
        }

        public Criteria andTrainTimeIsNull() {
            addCriterion("train_time is null");
            return (Criteria) this;
        }

        public Criteria andTrainTimeIsNotNull() {
            addCriterion("train_time is not null");
            return (Criteria) this;
        }

        public Criteria andTrainTimeEqualTo(String value) {
            addCriterion("train_time =", value, "trainTime");
            return (Criteria) this;
        }

        public Criteria andTrainTimeNotEqualTo(String value) {
            addCriterion("train_time <>", value, "trainTime");
            return (Criteria) this;
        }

        public Criteria andTrainTimeGreaterThan(String value) {
            addCriterion("train_time >", value, "trainTime");
            return (Criteria) this;
        }

        public Criteria andTrainTimeGreaterThanOrEqualTo(String value) {
            addCriterion("train_time >=", value, "trainTime");
            return (Criteria) this;
        }

        public Criteria andTrainTimeLessThan(String value) {
            addCriterion("train_time <", value, "trainTime");
            return (Criteria) this;
        }

        public Criteria andTrainTimeLessThanOrEqualTo(String value) {
            addCriterion("train_time <=", value, "trainTime");
            return (Criteria) this;
        }

        public Criteria andTrainTimeLike(String value) {
            addCriterion("train_time like", value, "'trainTime");
            return (Criteria) this;
        }

        public Criteria andTrainTimeNotLike(String value) {
            addCriterion("train_time not like", value, "trainTime");
            return (Criteria) this;
        }

        public Criteria andTrainTimeIn(List<String> values) {
            addCriterion("train_time in", values, "trainTime");
            return (Criteria) this;
        }

        public Criteria andTrainTimeNotIn(List<String> values) {
            addCriterion("train_time not in", values, "trainTime");
            return (Criteria) this;
        }

        public Criteria andTrainTimeBetween(String value1, String value2) {
            addCriterion("train_time between", value1, value2, "trainTime");
            return (Criteria) this;
        }

        public Criteria andTrainTimeNotBetween(String value1, String value2) {
            addCriterion("train_time not between", value1, value2, "trainTime");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(Integer value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(Integer value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(Integer value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(Integer value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(Integer value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<Integer> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<Integer> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(Integer value1, Integer value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andMustProtectionIsNull() {
            addCriterion("must_protection is null");
            return (Criteria) this;
        }

        public Criteria andMustProtectionIsNotNull() {
            addCriterion("must_protection is not null");
            return (Criteria) this;
        }

        public Criteria andMustProtectionEqualTo(Byte value) {
            addCriterion("must_protection =", value, "mustProtection");
            return (Criteria) this;
        }

        public Criteria andMustProtectionNotEqualTo(Byte value) {
            addCriterion("must_protection <>", value, "mustProtection");
            return (Criteria) this;
        }

        public Criteria andMustProtectionGreaterThan(Byte value) {
            addCriterion("must_protection >", value, "mustProtection");
            return (Criteria) this;
        }

        public Criteria andMustProtectionGreaterThanOrEqualTo(Byte value) {
            addCriterion("must_protection >=", value, "mustProtection");
            return (Criteria) this;
        }

        public Criteria andMustProtectionLessThan(Byte value) {
            addCriterion("must_protection <", value, "mustProtection");
            return (Criteria) this;
        }

        public Criteria andMustProtectionLessThanOrEqualTo(Byte value) {
            addCriterion("must_protection <=", value, "mustProtection");
            return (Criteria) this;
        }

        public Criteria andMustProtectionIn(List<Byte> values) {
            addCriterion("must_protection in", values, "mustProtection");
            return (Criteria) this;
        }

        public Criteria andMustProtectionNotIn(List<Byte> values) {
            addCriterion("must_protection not in", values, "mustProtection");
            return (Criteria) this;
        }

        public Criteria andMustProtectionBetween(Byte value1, Byte value2) {
            addCriterion("must_protection between", value1, value2, "mustProtection");
            return (Criteria) this;
        }

        public Criteria andMustProtectionNotBetween(Byte value1, Byte value2) {
            addCriterion("must_protection not between", value1, value2, "mustProtection");
            return (Criteria) this;
        }

        public Criteria andReservationIsNull() {
            addCriterion("reservation is null");
            return (Criteria) this;
        }

        public Criteria andReservationIsNotNull() {
            addCriterion("reservation is not null");
            return (Criteria) this;
        }

        public Criteria andReservationEqualTo(String value) {
            addCriterion("reservation =", value, "reservation");
            return (Criteria) this;
        }

        public Criteria andReservationNotEqualTo(String value) {
            addCriterion("reservation <>", value, "reservation");
            return (Criteria) this;
        }

        public Criteria andReservationGreaterThan(String value) {
            addCriterion("reservation >", value, "reservation");
            return (Criteria) this;
        }

        public Criteria andReservationGreaterThanOrEqualTo(String value) {
            addCriterion("reservation >=", value, "reservation");
            return (Criteria) this;
        }

        public Criteria andReservationLessThan(String value) {
            addCriterion("reservation <", value, "reservation");
            return (Criteria) this;
        }

        public Criteria andReservationLessThanOrEqualTo(String value) {
            addCriterion("reservation <=", value, "reservation");
            return (Criteria) this;
        }

        public Criteria andReservationLike(String value) {
            addCriterion("reservation like", value, "reservation");
            return (Criteria) this;
        }

        public Criteria andReservationNotLike(String value) {
            addCriterion("reservation not like", value, "reservation");
            return (Criteria) this;
        }

        public Criteria andReservationIn(List<String> values) {
            addCriterion("reservation in", values, "reservation");
            return (Criteria) this;
        }

        public Criteria andReservationNotIn(List<String> values) {
            addCriterion("reservation not in", values, "reservation");
            return (Criteria) this;
        }

        public Criteria andReservationBetween(String value1, String value2) {
            addCriterion("reservation between", value1, value2, "reservation");
            return (Criteria) this;
        }

        public Criteria andReservationNotBetween(String value1, String value2) {
            addCriterion("reservation not between", value1, value2, "reservation");
            return (Criteria) this;
        }

        public Criteria andBrandIsNull() {
            addCriterion("brand is null");
            return (Criteria) this;
        }

        public Criteria andBrandIsNotNull() {
            addCriterion("brand is not null");
            return (Criteria) this;
        }

        public Criteria andBrandEqualTo(String value) {
            addCriterion("brand =", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotEqualTo(String value) {
            addCriterion("brand <>", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandGreaterThan(String value) {
            addCriterion("brand >", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandGreaterThanOrEqualTo(String value) {
            addCriterion("brand >=", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandLessThan(String value) {
            addCriterion("brand <", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandLessThanOrEqualTo(String value) {
            addCriterion("brand <=", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandLike(String value) {
            addCriterion("brand like", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotLike(String value) {
            addCriterion("brand not like", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandIn(List<String> values) {
            addCriterion("brand in", values, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotIn(List<String> values) {
            addCriterion("brand not in", values, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandBetween(String value1, String value2) {
            addCriterion("brand between", value1, value2, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotBetween(String value1, String value2) {
            addCriterion("brand not between", value1, value2, "brand");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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
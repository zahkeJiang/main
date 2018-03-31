package com.bjpygh.gzh.bean;

import java.util.ArrayList;
import java.util.List;

public class RecommendExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RecommendExample() {
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

        public Criteria andRecommendIsNull() {
            addCriterion("recommend is null");
            return (Criteria) this;
        }

        public Criteria andRecommendIsNotNull() {
            addCriterion("recommend is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendEqualTo(Long value) {
            addCriterion("recommend =", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotEqualTo(Long value) {
            addCriterion("recommend <>", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendGreaterThan(Long value) {
            addCriterion("recommend >", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendGreaterThanOrEqualTo(Long value) {
            addCriterion("recommend >=", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendLessThan(Long value) {
            addCriterion("recommend <", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendLessThanOrEqualTo(Long value) {
            addCriterion("recommend <=", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendIn(List<Long> values) {
            addCriterion("recommend in", values, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotIn(List<Long> values) {
            addCriterion("recommend not in", values, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendBetween(Long value1, Long value2) {
            addCriterion("recommend between", value1, value2, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotBetween(Long value1, Long value2) {
            addCriterion("recommend not between", value1, value2, "recommend");
            return (Criteria) this;
        }

        public Criteria andShortTermIsNull() {
            addCriterion("short_term is null");
            return (Criteria) this;
        }

        public Criteria andShortTermIsNotNull() {
            addCriterion("short_term is not null");
            return (Criteria) this;
        }

        public Criteria andShortTermEqualTo(String value) {
            addCriterion("short_term =", value, "shortTerm");
            return (Criteria) this;
        }

        public Criteria andShortTermNotEqualTo(String value) {
            addCriterion("short_term <>", value, "shortTerm");
            return (Criteria) this;
        }

        public Criteria andShortTermGreaterThan(String value) {
            addCriterion("short_term >", value, "shortTerm");
            return (Criteria) this;
        }

        public Criteria andShortTermGreaterThanOrEqualTo(String value) {
            addCriterion("short_term >=", value, "shortTerm");
            return (Criteria) this;
        }

        public Criteria andShortTermLessThan(String value) {
            addCriterion("short_term <", value, "shortTerm");
            return (Criteria) this;
        }

        public Criteria andShortTermLessThanOrEqualTo(String value) {
            addCriterion("short_term <=", value, "shortTerm");
            return (Criteria) this;
        }

        public Criteria andShortTermLike(String value) {
            addCriterion("short_term like", value, "shortTerm");
            return (Criteria) this;
        }

        public Criteria andShortTermNotLike(String value) {
            addCriterion("short_term not like", value, "shortTerm");
            return (Criteria) this;
        }

        public Criteria andShortTermIn(List<String> values) {
            addCriterion("short_term in", values, "shortTerm");
            return (Criteria) this;
        }

        public Criteria andShortTermNotIn(List<String> values) {
            addCriterion("short_term not in", values, "shortTerm");
            return (Criteria) this;
        }

        public Criteria andShortTermBetween(String value1, String value2) {
            addCriterion("short_term between", value1, value2, "shortTerm");
            return (Criteria) this;
        }

        public Criteria andShortTermNotBetween(String value1, String value2) {
            addCriterion("short_term not between", value1, value2, "shortTerm");
            return (Criteria) this;
        }

        public Criteria andWorkDayIsNull() {
            addCriterion("work_day is null");
            return (Criteria) this;
        }

        public Criteria andWorkDayIsNotNull() {
            addCriterion("work_day is not null");
            return (Criteria) this;
        }

        public Criteria andWorkDayEqualTo(String value) {
            addCriterion("work_day =", value, "workDay");
            return (Criteria) this;
        }

        public Criteria andWorkDayNotEqualTo(String value) {
            addCriterion("work_day <>", value, "workDay");
            return (Criteria) this;
        }

        public Criteria andWorkDayGreaterThan(String value) {
            addCriterion("work_day >", value, "workDay");
            return (Criteria) this;
        }

        public Criteria andWorkDayGreaterThanOrEqualTo(String value) {
            addCriterion("work_day >=", value, "workDay");
            return (Criteria) this;
        }

        public Criteria andWorkDayLessThan(String value) {
            addCriterion("work_day <", value, "workDay");
            return (Criteria) this;
        }

        public Criteria andWorkDayLessThanOrEqualTo(String value) {
            addCriterion("work_day <=", value, "workDay");
            return (Criteria) this;
        }

        public Criteria andWorkDayLike(String value) {
            addCriterion("work_day like", value, "workDay");
            return (Criteria) this;
        }

        public Criteria andWorkDayNotLike(String value) {
            addCriterion("work_day not like", value, "workDay");
            return (Criteria) this;
        }

        public Criteria andWorkDayIn(List<String> values) {
            addCriterion("work_day in", values, "workDay");
            return (Criteria) this;
        }

        public Criteria andWorkDayNotIn(List<String> values) {
            addCriterion("work_day not in", values, "workDay");
            return (Criteria) this;
        }

        public Criteria andWorkDayBetween(String value1, String value2) {
            addCriterion("work_day between", value1, value2, "workDay");
            return (Criteria) this;
        }

        public Criteria andWorkDayNotBetween(String value1, String value2) {
            addCriterion("work_day not between", value1, value2, "workDay");
            return (Criteria) this;
        }

        public Criteria andCustomizeIsNull() {
            addCriterion("customize is null");
            return (Criteria) this;
        }

        public Criteria andCustomizeIsNotNull() {
            addCriterion("customize is not null");
            return (Criteria) this;
        }

        public Criteria andCustomizeEqualTo(String value) {
            addCriterion("customize =", value, "customize");
            return (Criteria) this;
        }

        public Criteria andCustomizeNotEqualTo(String value) {
            addCriterion("customize <>", value, "customize");
            return (Criteria) this;
        }

        public Criteria andCustomizeGreaterThan(String value) {
            addCriterion("customize >", value, "customize");
            return (Criteria) this;
        }

        public Criteria andCustomizeGreaterThanOrEqualTo(String value) {
            addCriterion("customize >=", value, "customize");
            return (Criteria) this;
        }

        public Criteria andCustomizeLessThan(String value) {
            addCriterion("customize <", value, "customize");
            return (Criteria) this;
        }

        public Criteria andCustomizeLessThanOrEqualTo(String value) {
            addCriterion("customize <=", value, "customize");
            return (Criteria) this;
        }

        public Criteria andCustomizeLike(String value) {
            addCriterion("customize like", value, "customize");
            return (Criteria) this;
        }

        public Criteria andCustomizeNotLike(String value) {
            addCriterion("customize not like", value, "customize");
            return (Criteria) this;
        }

        public Criteria andCustomizeIn(List<String> values) {
            addCriterion("customize in", values, "customize");
            return (Criteria) this;
        }

        public Criteria andCustomizeNotIn(List<String> values) {
            addCriterion("customize not in", values, "customize");
            return (Criteria) this;
        }

        public Criteria andCustomizeBetween(String value1, String value2) {
            addCriterion("customize between", value1, value2, "customize");
            return (Criteria) this;
        }

        public Criteria andCustomizeNotBetween(String value1, String value2) {
            addCriterion("customize not between", value1, value2, "customize");
            return (Criteria) this;
        }

        public Criteria andScaleIsNull() {
            addCriterion("scale is null");
            return (Criteria) this;
        }

        public Criteria andScaleIsNotNull() {
            addCriterion("scale is not null");
            return (Criteria) this;
        }

        public Criteria andScaleEqualTo(String value) {
            addCriterion("scale =", value, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleNotEqualTo(String value) {
            addCriterion("scale <>", value, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleGreaterThan(String value) {
            addCriterion("scale >", value, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleGreaterThanOrEqualTo(String value) {
            addCriterion("scale >=", value, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleLessThan(String value) {
            addCriterion("scale <", value, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleLessThanOrEqualTo(String value) {
            addCriterion("scale <=", value, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleLike(String value) {
            addCriterion("scale like", value, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleNotLike(String value) {
            addCriterion("scale not like", value, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleIn(List<String> values) {
            addCriterion("scale in", values, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleNotIn(List<String> values) {
            addCriterion("scale not in", values, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleBetween(String value1, String value2) {
            addCriterion("scale between", value1, value2, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleNotBetween(String value1, String value2) {
            addCriterion("scale not between", value1, value2, "scale");
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

        public Criteria andPriceEqualTo(String value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(String value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(String value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(String value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(String value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(String value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLike(String value) {
            addCriterion("price like", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotLike(String value) {
            addCriterion("price not like", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<String> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<String> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(String value1, String value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(String value1, String value2) {
            addCriterion("price not between", value1, value2, "price");
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
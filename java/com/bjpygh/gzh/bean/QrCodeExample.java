package com.bjpygh.gzh.bean;

import java.util.ArrayList;
import java.util.List;

public class QrCodeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public QrCodeExample() {
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andTicketIsNull() {
            addCriterion("ticket is null");
            return (Criteria) this;
        }

        public Criteria andTicketIsNotNull() {
            addCriterion("ticket is not null");
            return (Criteria) this;
        }

        public Criteria andTicketEqualTo(String value) {
            addCriterion("ticket =", value, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketNotEqualTo(String value) {
            addCriterion("ticket <>", value, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketGreaterThan(String value) {
            addCriterion("ticket >", value, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketGreaterThanOrEqualTo(String value) {
            addCriterion("ticket >=", value, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketLessThan(String value) {
            addCriterion("ticket <", value, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketLessThanOrEqualTo(String value) {
            addCriterion("ticket <=", value, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketLike(String value) {
            addCriterion("ticket like", value, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketNotLike(String value) {
            addCriterion("ticket not like", value, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketIn(List<String> values) {
            addCriterion("ticket in", values, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketNotIn(List<String> values) {
            addCriterion("ticket not in", values, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketBetween(String value1, String value2) {
            addCriterion("ticket between", value1, value2, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketNotBetween(String value1, String value2) {
            addCriterion("ticket not between", value1, value2, "ticket");
            return (Criteria) this;
        }

        public Criteria andConcernIsNull() {
            addCriterion("concern is null");
            return (Criteria) this;
        }

        public Criteria andConcernIsNotNull() {
            addCriterion("concern is not null");
            return (Criteria) this;
        }

        public Criteria andConcernEqualTo(Integer value) {
            addCriterion("concern =", value, "concern");
            return (Criteria) this;
        }

        public Criteria andConcernNotEqualTo(Integer value) {
            addCriterion("concern <>", value, "concern");
            return (Criteria) this;
        }

        public Criteria andConcernGreaterThan(Integer value) {
            addCriterion("concern >", value, "concern");
            return (Criteria) this;
        }

        public Criteria andConcernGreaterThanOrEqualTo(Integer value) {
            addCriterion("concern >=", value, "concern");
            return (Criteria) this;
        }

        public Criteria andConcernLessThan(Integer value) {
            addCriterion("concern <", value, "concern");
            return (Criteria) this;
        }

        public Criteria andConcernLessThanOrEqualTo(Integer value) {
            addCriterion("concern <=", value, "concern");
            return (Criteria) this;
        }

        public Criteria andConcernIn(List<Integer> values) {
            addCriterion("concern in", values, "concern");
            return (Criteria) this;
        }

        public Criteria andConcernNotIn(List<Integer> values) {
            addCriterion("concern not in", values, "concern");
            return (Criteria) this;
        }

        public Criteria andConcernBetween(Integer value1, Integer value2) {
            addCriterion("concern between", value1, value2, "concern");
            return (Criteria) this;
        }

        public Criteria andConcernNotBetween(Integer value1, Integer value2) {
            addCriterion("concern not between", value1, value2, "concern");
            return (Criteria) this;
        }

        public Criteria andUnconcernIsNull() {
            addCriterion("unconcern is null");
            return (Criteria) this;
        }

        public Criteria andUnconcernIsNotNull() {
            addCriterion("unconcern is not null");
            return (Criteria) this;
        }

        public Criteria andUnconcernEqualTo(Integer value) {
            addCriterion("unconcern =", value, "unconcern");
            return (Criteria) this;
        }

        public Criteria andUnconcernNotEqualTo(Integer value) {
            addCriterion("unconcern <>", value, "unconcern");
            return (Criteria) this;
        }

        public Criteria andUnconcernGreaterThan(Integer value) {
            addCriterion("unconcern >", value, "unconcern");
            return (Criteria) this;
        }

        public Criteria andUnconcernGreaterThanOrEqualTo(Integer value) {
            addCriterion("unconcern >=", value, "unconcern");
            return (Criteria) this;
        }

        public Criteria andUnconcernLessThan(Integer value) {
            addCriterion("unconcern <", value, "unconcern");
            return (Criteria) this;
        }

        public Criteria andUnconcernLessThanOrEqualTo(Integer value) {
            addCriterion("unconcern <=", value, "unconcern");
            return (Criteria) this;
        }

        public Criteria andUnconcernIn(List<Integer> values) {
            addCriterion("unconcern in", values, "unconcern");
            return (Criteria) this;
        }

        public Criteria andUnconcernNotIn(List<Integer> values) {
            addCriterion("unconcern not in", values, "unconcern");
            return (Criteria) this;
        }

        public Criteria andUnconcernBetween(Integer value1, Integer value2) {
            addCriterion("unconcern between", value1, value2, "unconcern");
            return (Criteria) this;
        }

        public Criteria andUnconcernNotBetween(Integer value1, Integer value2) {
            addCriterion("unconcern not between", value1, value2, "unconcern");
            return (Criteria) this;
        }

        public Criteria andOnconcernIsNull() {
            addCriterion("onconcern is null");
            return (Criteria) this;
        }

        public Criteria andOnconcernIsNotNull() {
            addCriterion("onconcern is not null");
            return (Criteria) this;
        }

        public Criteria andOnconcernEqualTo(Integer value) {
            addCriterion("onconcern =", value, "onconcern");
            return (Criteria) this;
        }

        public Criteria andOnconcernNotEqualTo(Integer value) {
            addCriterion("onconcern <>", value, "onconcern");
            return (Criteria) this;
        }

        public Criteria andOnconcernGreaterThan(Integer value) {
            addCriterion("onconcern >", value, "onconcern");
            return (Criteria) this;
        }

        public Criteria andOnconcernGreaterThanOrEqualTo(Integer value) {
            addCriterion("onconcern >=", value, "onconcern");
            return (Criteria) this;
        }

        public Criteria andOnconcernLessThan(Integer value) {
            addCriterion("onconcern <", value, "onconcern");
            return (Criteria) this;
        }

        public Criteria andOnconcernLessThanOrEqualTo(Integer value) {
            addCriterion("onconcern <=", value, "onconcern");
            return (Criteria) this;
        }

        public Criteria andOnconcernIn(List<Integer> values) {
            addCriterion("onconcern in", values, "onconcern");
            return (Criteria) this;
        }

        public Criteria andOnconcernNotIn(List<Integer> values) {
            addCriterion("onconcern not in", values, "onconcern");
            return (Criteria) this;
        }

        public Criteria andOnconcernBetween(Integer value1, Integer value2) {
            addCriterion("onconcern between", value1, value2, "onconcern");
            return (Criteria) this;
        }

        public Criteria andOnconcernNotBetween(Integer value1, Integer value2) {
            addCriterion("onconcern not between", value1, value2, "onconcern");
            return (Criteria) this;
        }

        public Criteria andConcernedIsNull() {
            addCriterion("concerned is null");
            return (Criteria) this;
        }

        public Criteria andConcernedIsNotNull() {
            addCriterion("concerned is not null");
            return (Criteria) this;
        }

        public Criteria andConcernedEqualTo(Integer value) {
            addCriterion("concerned =", value, "concerned");
            return (Criteria) this;
        }

        public Criteria andConcernedNotEqualTo(Integer value) {
            addCriterion("concerned <>", value, "concerned");
            return (Criteria) this;
        }

        public Criteria andConcernedGreaterThan(Integer value) {
            addCriterion("concerned >", value, "concerned");
            return (Criteria) this;
        }

        public Criteria andConcernedGreaterThanOrEqualTo(Integer value) {
            addCriterion("concerned >=", value, "concerned");
            return (Criteria) this;
        }

        public Criteria andConcernedLessThan(Integer value) {
            addCriterion("concerned <", value, "concerned");
            return (Criteria) this;
        }

        public Criteria andConcernedLessThanOrEqualTo(Integer value) {
            addCriterion("concerned <=", value, "concerned");
            return (Criteria) this;
        }

        public Criteria andConcernedIn(List<Integer> values) {
            addCriterion("concerned in", values, "concerned");
            return (Criteria) this;
        }

        public Criteria andConcernedNotIn(List<Integer> values) {
            addCriterion("concerned not in", values, "concerned");
            return (Criteria) this;
        }

        public Criteria andConcernedBetween(Integer value1, Integer value2) {
            addCriterion("concerned between", value1, value2, "concerned");
            return (Criteria) this;
        }

        public Criteria andConcernedNotBetween(Integer value1, Integer value2) {
            addCriterion("concerned not between", value1, value2, "concerned");
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
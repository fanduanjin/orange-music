package cn.fan.model.music;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SongExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table song
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table song
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table song
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    public SongExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
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
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table song
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table song
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("`type` is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("`type` is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andMidIsNull() {
            addCriterion("mid is null");
            return (Criteria) this;
        }

        public Criteria andMidIsNotNull() {
            addCriterion("mid is not null");
            return (Criteria) this;
        }

        public Criteria andMidEqualTo(String value) {
            addCriterion("mid =", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidNotEqualTo(String value) {
            addCriterion("mid <>", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidGreaterThan(String value) {
            addCriterion("mid >", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidGreaterThanOrEqualTo(String value) {
            addCriterion("mid >=", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidLessThan(String value) {
            addCriterion("mid <", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidLessThanOrEqualTo(String value) {
            addCriterion("mid <=", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidLike(String value) {
            addCriterion("mid like", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidNotLike(String value) {
            addCriterion("mid not like", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidIn(List<String> values) {
            addCriterion("mid in", values, "mid");
            return (Criteria) this;
        }

        public Criteria andMidNotIn(List<String> values) {
            addCriterion("mid not in", values, "mid");
            return (Criteria) this;
        }

        public Criteria andMidBetween(String value1, String value2) {
            addCriterion("mid between", value1, value2, "mid");
            return (Criteria) this;
        }

        public Criteria andMidNotBetween(String value1, String value2) {
            addCriterion("mid not between", value1, value2, "mid");
            return (Criteria) this;
        }

        public Criteria andPlatIdIsNull() {
            addCriterion("plat_Id is null");
            return (Criteria) this;
        }

        public Criteria andPlatIdIsNotNull() {
            addCriterion("plat_Id is not null");
            return (Criteria) this;
        }

        public Criteria andPlatIdEqualTo(Integer value) {
            addCriterion("plat_Id =", value, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdNotEqualTo(Integer value) {
            addCriterion("plat_Id <>", value, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdGreaterThan(Integer value) {
            addCriterion("plat_Id >", value, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("plat_Id >=", value, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdLessThan(Integer value) {
            addCriterion("plat_Id <", value, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdLessThanOrEqualTo(Integer value) {
            addCriterion("plat_Id <=", value, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdIn(List<Integer> values) {
            addCriterion("plat_Id in", values, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdNotIn(List<Integer> values) {
            addCriterion("plat_Id not in", values, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdBetween(Integer value1, Integer value2) {
            addCriterion("plat_Id between", value1, value2, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdNotBetween(Integer value1, Integer value2) {
            addCriterion("plat_Id not between", value1, value2, "platId");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andSubTitleIsNull() {
            addCriterion("sub_title is null");
            return (Criteria) this;
        }

        public Criteria andSubTitleIsNotNull() {
            addCriterion("sub_title is not null");
            return (Criteria) this;
        }

        public Criteria andSubTitleEqualTo(String value) {
            addCriterion("sub_title =", value, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleNotEqualTo(String value) {
            addCriterion("sub_title <>", value, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleGreaterThan(String value) {
            addCriterion("sub_title >", value, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleGreaterThanOrEqualTo(String value) {
            addCriterion("sub_title >=", value, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleLessThan(String value) {
            addCriterion("sub_title <", value, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleLessThanOrEqualTo(String value) {
            addCriterion("sub_title <=", value, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleLike(String value) {
            addCriterion("sub_title like", value, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleNotLike(String value) {
            addCriterion("sub_title not like", value, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleIn(List<String> values) {
            addCriterion("sub_title in", values, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleNotIn(List<String> values) {
            addCriterion("sub_title not in", values, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleBetween(String value1, String value2) {
            addCriterion("sub_title between", value1, value2, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleNotBetween(String value1, String value2) {
            addCriterion("sub_title not between", value1, value2, "subTitle");
            return (Criteria) this;
        }

        public Criteria andAlbumPlatIdIsNull() {
            addCriterion("album_plat_id is null");
            return (Criteria) this;
        }

        public Criteria andAlbumPlatIdIsNotNull() {
            addCriterion("album_plat_id is not null");
            return (Criteria) this;
        }

        public Criteria andAlbumPlatIdEqualTo(Integer value) {
            addCriterion("album_plat_id =", value, "albumPlatId");
            return (Criteria) this;
        }

        public Criteria andAlbumPlatIdNotEqualTo(Integer value) {
            addCriterion("album_plat_id <>", value, "albumPlatId");
            return (Criteria) this;
        }

        public Criteria andAlbumPlatIdGreaterThan(Integer value) {
            addCriterion("album_plat_id >", value, "albumPlatId");
            return (Criteria) this;
        }

        public Criteria andAlbumPlatIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("album_plat_id >=", value, "albumPlatId");
            return (Criteria) this;
        }

        public Criteria andAlbumPlatIdLessThan(Integer value) {
            addCriterion("album_plat_id <", value, "albumPlatId");
            return (Criteria) this;
        }

        public Criteria andAlbumPlatIdLessThanOrEqualTo(Integer value) {
            addCriterion("album_plat_id <=", value, "albumPlatId");
            return (Criteria) this;
        }

        public Criteria andAlbumPlatIdIn(List<Integer> values) {
            addCriterion("album_plat_id in", values, "albumPlatId");
            return (Criteria) this;
        }

        public Criteria andAlbumPlatIdNotIn(List<Integer> values) {
            addCriterion("album_plat_id not in", values, "albumPlatId");
            return (Criteria) this;
        }

        public Criteria andAlbumPlatIdBetween(Integer value1, Integer value2) {
            addCriterion("album_plat_id between", value1, value2, "albumPlatId");
            return (Criteria) this;
        }

        public Criteria andAlbumPlatIdNotBetween(Integer value1, Integer value2) {
            addCriterion("album_plat_id not between", value1, value2, "albumPlatId");
            return (Criteria) this;
        }

        public Criteria andMvidIsNull() {
            addCriterion("mvid is null");
            return (Criteria) this;
        }

        public Criteria andMvidIsNotNull() {
            addCriterion("mvid is not null");
            return (Criteria) this;
        }

        public Criteria andMvidEqualTo(String value) {
            addCriterion("mvid =", value, "mvid");
            return (Criteria) this;
        }

        public Criteria andMvidNotEqualTo(String value) {
            addCriterion("mvid <>", value, "mvid");
            return (Criteria) this;
        }

        public Criteria andMvidGreaterThan(String value) {
            addCriterion("mvid >", value, "mvid");
            return (Criteria) this;
        }

        public Criteria andMvidGreaterThanOrEqualTo(String value) {
            addCriterion("mvid >=", value, "mvid");
            return (Criteria) this;
        }

        public Criteria andMvidLessThan(String value) {
            addCriterion("mvid <", value, "mvid");
            return (Criteria) this;
        }

        public Criteria andMvidLessThanOrEqualTo(String value) {
            addCriterion("mvid <=", value, "mvid");
            return (Criteria) this;
        }

        public Criteria andMvidLike(String value) {
            addCriterion("mvid like", value, "mvid");
            return (Criteria) this;
        }

        public Criteria andMvidNotLike(String value) {
            addCriterion("mvid not like", value, "mvid");
            return (Criteria) this;
        }

        public Criteria andMvidIn(List<String> values) {
            addCriterion("mvid in", values, "mvid");
            return (Criteria) this;
        }

        public Criteria andMvidNotIn(List<String> values) {
            addCriterion("mvid not in", values, "mvid");
            return (Criteria) this;
        }

        public Criteria andMvidBetween(String value1, String value2) {
            addCriterion("mvid between", value1, value2, "mvid");
            return (Criteria) this;
        }

        public Criteria andMvidNotBetween(String value1, String value2) {
            addCriterion("mvid not between", value1, value2, "mvid");
            return (Criteria) this;
        }

        public Criteria andLanguageIsNull() {
            addCriterion("`language` is null");
            return (Criteria) this;
        }

        public Criteria andLanguageIsNotNull() {
            addCriterion("`language` is not null");
            return (Criteria) this;
        }

        public Criteria andLanguageEqualTo(Integer value) {
            addCriterion("`language` =", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageNotEqualTo(Integer value) {
            addCriterion("`language` <>", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageGreaterThan(Integer value) {
            addCriterion("`language` >", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageGreaterThanOrEqualTo(Integer value) {
            addCriterion("`language` >=", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageLessThan(Integer value) {
            addCriterion("`language` <", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageLessThanOrEqualTo(Integer value) {
            addCriterion("`language` <=", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageIn(List<Integer> values) {
            addCriterion("`language` in", values, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageNotIn(List<Integer> values) {
            addCriterion("`language` not in", values, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageBetween(Integer value1, Integer value2) {
            addCriterion("`language` between", value1, value2, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageNotBetween(Integer value1, Integer value2) {
            addCriterion("`language` not between", value1, value2, "language");
            return (Criteria) this;
        }

        public Criteria andGenreIsNull() {
            addCriterion("genre is null");
            return (Criteria) this;
        }

        public Criteria andGenreIsNotNull() {
            addCriterion("genre is not null");
            return (Criteria) this;
        }

        public Criteria andGenreEqualTo(Integer value) {
            addCriterion("genre =", value, "genre");
            return (Criteria) this;
        }

        public Criteria andGenreNotEqualTo(Integer value) {
            addCriterion("genre <>", value, "genre");
            return (Criteria) this;
        }

        public Criteria andGenreGreaterThan(Integer value) {
            addCriterion("genre >", value, "genre");
            return (Criteria) this;
        }

        public Criteria andGenreGreaterThanOrEqualTo(Integer value) {
            addCriterion("genre >=", value, "genre");
            return (Criteria) this;
        }

        public Criteria andGenreLessThan(Integer value) {
            addCriterion("genre <", value, "genre");
            return (Criteria) this;
        }

        public Criteria andGenreLessThanOrEqualTo(Integer value) {
            addCriterion("genre <=", value, "genre");
            return (Criteria) this;
        }

        public Criteria andGenreIn(List<Integer> values) {
            addCriterion("genre in", values, "genre");
            return (Criteria) this;
        }

        public Criteria andGenreNotIn(List<Integer> values) {
            addCriterion("genre not in", values, "genre");
            return (Criteria) this;
        }

        public Criteria andGenreBetween(Integer value1, Integer value2) {
            addCriterion("genre between", value1, value2, "genre");
            return (Criteria) this;
        }

        public Criteria andGenreNotBetween(Integer value1, Integer value2) {
            addCriterion("genre not between", value1, value2, "genre");
            return (Criteria) this;
        }

        public Criteria andPublicTimeIsNull() {
            addCriterion("public_time is null");
            return (Criteria) this;
        }

        public Criteria andPublicTimeIsNotNull() {
            addCriterion("public_time is not null");
            return (Criteria) this;
        }

        public Criteria andPublicTimeEqualTo(Date value) {
            addCriterionForJDBCDate("public_time =", value, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("public_time <>", value, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("public_time >", value, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("public_time >=", value, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeLessThan(Date value) {
            addCriterionForJDBCDate("public_time <", value, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("public_time <=", value, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeIn(List<Date> values) {
            addCriterionForJDBCDate("public_time in", values, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("public_time not in", values, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("public_time between", value1, value2, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("public_time not between", value1, value2, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPricePlayIsNull() {
            addCriterion("price_play is null");
            return (Criteria) this;
        }

        public Criteria andPricePlayIsNotNull() {
            addCriterion("price_play is not null");
            return (Criteria) this;
        }

        public Criteria andPricePlayEqualTo(Boolean value) {
            addCriterion("price_play =", value, "pricePlay");
            return (Criteria) this;
        }

        public Criteria andPricePlayNotEqualTo(Boolean value) {
            addCriterion("price_play <>", value, "pricePlay");
            return (Criteria) this;
        }

        public Criteria andPricePlayGreaterThan(Boolean value) {
            addCriterion("price_play >", value, "pricePlay");
            return (Criteria) this;
        }

        public Criteria andPricePlayGreaterThanOrEqualTo(Boolean value) {
            addCriterion("price_play >=", value, "pricePlay");
            return (Criteria) this;
        }

        public Criteria andPricePlayLessThan(Boolean value) {
            addCriterion("price_play <", value, "pricePlay");
            return (Criteria) this;
        }

        public Criteria andPricePlayLessThanOrEqualTo(Boolean value) {
            addCriterion("price_play <=", value, "pricePlay");
            return (Criteria) this;
        }

        public Criteria andPricePlayIn(List<Boolean> values) {
            addCriterion("price_play in", values, "pricePlay");
            return (Criteria) this;
        }

        public Criteria andPricePlayNotIn(List<Boolean> values) {
            addCriterion("price_play not in", values, "pricePlay");
            return (Criteria) this;
        }

        public Criteria andPricePlayBetween(Boolean value1, Boolean value2) {
            addCriterion("price_play between", value1, value2, "pricePlay");
            return (Criteria) this;
        }

        public Criteria andPricePlayNotBetween(Boolean value1, Boolean value2) {
            addCriterion("price_play not between", value1, value2, "pricePlay");
            return (Criteria) this;
        }

        public Criteria andMediaMidIsNull() {
            addCriterion("media_mid is null");
            return (Criteria) this;
        }

        public Criteria andMediaMidIsNotNull() {
            addCriterion("media_mid is not null");
            return (Criteria) this;
        }

        public Criteria andMediaMidEqualTo(String value) {
            addCriterion("media_mid =", value, "mediaMid");
            return (Criteria) this;
        }

        public Criteria andMediaMidNotEqualTo(String value) {
            addCriterion("media_mid <>", value, "mediaMid");
            return (Criteria) this;
        }

        public Criteria andMediaMidGreaterThan(String value) {
            addCriterion("media_mid >", value, "mediaMid");
            return (Criteria) this;
        }

        public Criteria andMediaMidGreaterThanOrEqualTo(String value) {
            addCriterion("media_mid >=", value, "mediaMid");
            return (Criteria) this;
        }

        public Criteria andMediaMidLessThan(String value) {
            addCriterion("media_mid <", value, "mediaMid");
            return (Criteria) this;
        }

        public Criteria andMediaMidLessThanOrEqualTo(String value) {
            addCriterion("media_mid <=", value, "mediaMid");
            return (Criteria) this;
        }

        public Criteria andMediaMidLike(String value) {
            addCriterion("media_mid like", value, "mediaMid");
            return (Criteria) this;
        }

        public Criteria andMediaMidNotLike(String value) {
            addCriterion("media_mid not like", value, "mediaMid");
            return (Criteria) this;
        }

        public Criteria andMediaMidIn(List<String> values) {
            addCriterion("media_mid in", values, "mediaMid");
            return (Criteria) this;
        }

        public Criteria andMediaMidNotIn(List<String> values) {
            addCriterion("media_mid not in", values, "mediaMid");
            return (Criteria) this;
        }

        public Criteria andMediaMidBetween(String value1, String value2) {
            addCriterion("media_mid between", value1, value2, "mediaMid");
            return (Criteria) this;
        }

        public Criteria andMediaMidNotBetween(String value1, String value2) {
            addCriterion("media_mid not between", value1, value2, "mediaMid");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table song
     *
     * @mbg.generated do_not_delete_during_merge Sun May 16 22:00:30 CST 2021
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table song
     *
     * @mbg.generated Sun May 16 22:00:30 CST 2021
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
package com.mtr.njusthelper.entity;

import java.util.*;

public class ArticleExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<ArticleExample.Criteria> oredCriteria;
    
    public ArticleExample() {
        this.oredCriteria = new ArrayList<ArticleExample.Criteria>();
    }
    
    public void setOrderByClause(final String orderByClause) {
        this.orderByClause = orderByClause;
    }
    
    public String getOrderByClause() {
        return this.orderByClause;
    }
    
    public void setDistinct(final boolean distinct) {
        this.distinct = distinct;
    }
    
    public boolean isDistinct() {
        return this.distinct;
    }
    
    public List<ArticleExample.Criteria> getOredCriteria() {
        return this.oredCriteria;
    }
    
    public void or(final ArticleExample.Criteria criteria) {
        this.oredCriteria.add(criteria);
    }
    
    public ArticleExample.Criteria or() {
        final ArticleExample.Criteria criteria = this.createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }
    
    public ArticleExample.Criteria createCriteria() {
        final ArticleExample.Criteria criteria = this.createCriteriaInternal();
        if (this.oredCriteria.size() == 0) {
            this.oredCriteria.add(criteria);
        }
        return criteria;
    }
    
    protected ArticleExample.Criteria createCriteriaInternal() {
        final ArticleExample.Criteria criteria = new ArticleExample.Criteria();
        return criteria;
    }
    
    public void clear() {
        this.oredCriteria.clear();
        this.orderByClause = null;
        this.distinct = false;
    }
}

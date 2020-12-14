package com.mtr.njusthelper.entity;

import java.util.*;

public class LikeExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<LikeExample.Criteria> oredCriteria;
    
    public LikeExample() {
        this.oredCriteria = new ArrayList<LikeExample.Criteria>();
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
    
    public List<LikeExample.Criteria> getOredCriteria() {
        return this.oredCriteria;
    }
    
    public void or(final LikeExample.Criteria criteria) {
        this.oredCriteria.add(criteria);
    }
    
    public LikeExample.Criteria or() {
        final LikeExample.Criteria criteria = this.createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }
    
    public LikeExample.Criteria createCriteria() {
        final LikeExample.Criteria criteria = this.createCriteriaInternal();
        if (this.oredCriteria.size() == 0) {
            this.oredCriteria.add(criteria);
        }
        return criteria;
    }
    
    protected LikeExample.Criteria createCriteriaInternal() {
        final LikeExample.Criteria criteria = new LikeExample.Criteria();
        return criteria;
    }
    
    public void clear() {
        this.oredCriteria.clear();
        this.orderByClause = null;
        this.distinct = false;
    }
}

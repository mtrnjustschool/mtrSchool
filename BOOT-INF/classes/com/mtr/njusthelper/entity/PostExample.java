package com.mtr.njusthelper.entity;

import java.util.*;

public class PostExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<PostExample.Criteria> oredCriteria;
    
    public PostExample() {
        this.oredCriteria = new ArrayList<PostExample.Criteria>();
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
    
    public List<PostExample.Criteria> getOredCriteria() {
        return this.oredCriteria;
    }
    
    public void or(final PostExample.Criteria criteria) {
        this.oredCriteria.add(criteria);
    }
    
    public PostExample.Criteria or() {
        final PostExample.Criteria criteria = this.createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }
    
    public PostExample.Criteria createCriteria() {
        final PostExample.Criteria criteria = this.createCriteriaInternal();
        if (this.oredCriteria.size() == 0) {
            this.oredCriteria.add(criteria);
        }
        return criteria;
    }
    
    protected PostExample.Criteria createCriteriaInternal() {
        final PostExample.Criteria criteria = new PostExample.Criteria();
        return criteria;
    }
    
    public void clear() {
        this.oredCriteria.clear();
        this.orderByClause = null;
        this.distinct = false;
    }
}

package com.mtr.njusthelper.entity;

import java.util.*;

public class WeaccountExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<WeaccountExample.Criteria> oredCriteria;
    
    public WeaccountExample() {
        this.oredCriteria = new ArrayList<WeaccountExample.Criteria>();
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
    
    public List<WeaccountExample.Criteria> getOredCriteria() {
        return this.oredCriteria;
    }
    
    public void or(final WeaccountExample.Criteria criteria) {
        this.oredCriteria.add(criteria);
    }
    
    public WeaccountExample.Criteria or() {
        final WeaccountExample.Criteria criteria = this.createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }
    
    public WeaccountExample.Criteria createCriteria() {
        final WeaccountExample.Criteria criteria = this.createCriteriaInternal();
        if (this.oredCriteria.size() == 0) {
            this.oredCriteria.add(criteria);
        }
        return criteria;
    }
    
    protected WeaccountExample.Criteria createCriteriaInternal() {
        final WeaccountExample.Criteria criteria = new WeaccountExample.Criteria();
        return criteria;
    }
    
    public void clear() {
        this.oredCriteria.clear();
        this.orderByClause = null;
        this.distinct = false;
    }
}

package com.mtr.njusthelper.entity;

import java.util.*;

public class ManagerExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<ManagerExample.Criteria> oredCriteria;
    
    public ManagerExample() {
        this.oredCriteria = new ArrayList<ManagerExample.Criteria>();
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
    
    public List<ManagerExample.Criteria> getOredCriteria() {
        return this.oredCriteria;
    }
    
    public void or(final ManagerExample.Criteria criteria) {
        this.oredCriteria.add(criteria);
    }
    
    public ManagerExample.Criteria or() {
        final ManagerExample.Criteria criteria = this.createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }
    
    public ManagerExample.Criteria createCriteria() {
        final ManagerExample.Criteria criteria = this.createCriteriaInternal();
        if (this.oredCriteria.size() == 0) {
            this.oredCriteria.add(criteria);
        }
        return criteria;
    }
    
    protected ManagerExample.Criteria createCriteriaInternal() {
        final ManagerExample.Criteria criteria = new ManagerExample.Criteria();
        return criteria;
    }
    
    public void clear() {
        this.oredCriteria.clear();
        this.orderByClause = null;
        this.distinct = false;
    }
}

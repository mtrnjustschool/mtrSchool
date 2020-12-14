package com.mtr.njusthelper.entity;

import java.util.*;

public class StartdateExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<StartdateExample.Criteria> oredCriteria;
    
    public StartdateExample() {
        this.oredCriteria = new ArrayList<StartdateExample.Criteria>();
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
    
    public List<StartdateExample.Criteria> getOredCriteria() {
        return this.oredCriteria;
    }
    
    public void or(final StartdateExample.Criteria criteria) {
        this.oredCriteria.add(criteria);
    }
    
    public StartdateExample.Criteria or() {
        final StartdateExample.Criteria criteria = this.createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }
    
    public StartdateExample.Criteria createCriteria() {
        final StartdateExample.Criteria criteria = this.createCriteriaInternal();
        if (this.oredCriteria.size() == 0) {
            this.oredCriteria.add(criteria);
        }
        return criteria;
    }
    
    protected StartdateExample.Criteria createCriteriaInternal() {
        final StartdateExample.Criteria criteria = new StartdateExample.Criteria();
        return criteria;
    }
    
    public void clear() {
        this.oredCriteria.clear();
        this.orderByClause = null;
        this.distinct = false;
    }
}

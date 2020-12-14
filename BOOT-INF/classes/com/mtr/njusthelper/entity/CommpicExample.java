package com.mtr.njusthelper.entity;

import java.util.*;

public class CommpicExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<CommpicExample.Criteria> oredCriteria;
    
    public CommpicExample() {
        this.oredCriteria = new ArrayList<CommpicExample.Criteria>();
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
    
    public List<CommpicExample.Criteria> getOredCriteria() {
        return this.oredCriteria;
    }
    
    public void or(final CommpicExample.Criteria criteria) {
        this.oredCriteria.add(criteria);
    }
    
    public CommpicExample.Criteria or() {
        final CommpicExample.Criteria criteria = this.createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }
    
    public CommpicExample.Criteria createCriteria() {
        final CommpicExample.Criteria criteria = this.createCriteriaInternal();
        if (this.oredCriteria.size() == 0) {
            this.oredCriteria.add(criteria);
        }
        return criteria;
    }
    
    protected CommpicExample.Criteria createCriteriaInternal() {
        final CommpicExample.Criteria criteria = new CommpicExample.Criteria();
        return criteria;
    }
    
    public void clear() {
        this.oredCriteria.clear();
        this.orderByClause = null;
        this.distinct = false;
    }
}

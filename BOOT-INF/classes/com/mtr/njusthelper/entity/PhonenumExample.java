package com.mtr.njusthelper.entity;

import java.util.*;

public class PhonenumExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<PhonenumExample.Criteria> oredCriteria;
    
    public PhonenumExample() {
        this.oredCriteria = new ArrayList<PhonenumExample.Criteria>();
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
    
    public List<PhonenumExample.Criteria> getOredCriteria() {
        return this.oredCriteria;
    }
    
    public void or(final PhonenumExample.Criteria criteria) {
        this.oredCriteria.add(criteria);
    }
    
    public PhonenumExample.Criteria or() {
        final PhonenumExample.Criteria criteria = this.createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }
    
    public PhonenumExample.Criteria createCriteria() {
        final PhonenumExample.Criteria criteria = this.createCriteriaInternal();
        if (this.oredCriteria.size() == 0) {
            this.oredCriteria.add(criteria);
        }
        return criteria;
    }
    
    protected PhonenumExample.Criteria createCriteriaInternal() {
        final PhonenumExample.Criteria criteria = new PhonenumExample.Criteria();
        return criteria;
    }
    
    public void clear() {
        this.oredCriteria.clear();
        this.orderByClause = null;
        this.distinct = false;
    }
}

package com.mtr.njusthelper.entity;

import java.util.*;

public class PictureExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<PictureExample.Criteria> oredCriteria;
    
    public PictureExample() {
        this.oredCriteria = new ArrayList<PictureExample.Criteria>();
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
    
    public List<PictureExample.Criteria> getOredCriteria() {
        return this.oredCriteria;
    }
    
    public void or(final PictureExample.Criteria criteria) {
        this.oredCriteria.add(criteria);
    }
    
    public PictureExample.Criteria or() {
        final PictureExample.Criteria criteria = this.createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }
    
    public PictureExample.Criteria createCriteria() {
        final PictureExample.Criteria criteria = this.createCriteriaInternal();
        if (this.oredCriteria.size() == 0) {
            this.oredCriteria.add(criteria);
        }
        return criteria;
    }
    
    protected PictureExample.Criteria createCriteriaInternal() {
        final PictureExample.Criteria criteria = new PictureExample.Criteria();
        return criteria;
    }
    
    public void clear() {
        this.oredCriteria.clear();
        this.orderByClause = null;
        this.distinct = false;
    }
}

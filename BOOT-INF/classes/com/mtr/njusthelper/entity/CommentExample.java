package com.mtr.njusthelper.entity;

import java.util.*;

public class CommentExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<CommentExample.Criteria> oredCriteria;
    
    public CommentExample() {
        this.oredCriteria = new ArrayList<CommentExample.Criteria>();
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
    
    public List<CommentExample.Criteria> getOredCriteria() {
        return this.oredCriteria;
    }
    
    public void or(final CommentExample.Criteria criteria) {
        this.oredCriteria.add(criteria);
    }
    
    public CommentExample.Criteria or() {
        final CommentExample.Criteria criteria = this.createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }
    
    public CommentExample.Criteria createCriteria() {
        final CommentExample.Criteria criteria = this.createCriteriaInternal();
        if (this.oredCriteria.size() == 0) {
            this.oredCriteria.add(criteria);
        }
        return criteria;
    }
    
    protected CommentExample.Criteria createCriteriaInternal() {
        final CommentExample.Criteria criteria = new CommentExample.Criteria();
        return criteria;
    }
    
    public void clear() {
        this.oredCriteria.clear();
        this.orderByClause = null;
        this.distinct = false;
    }
}

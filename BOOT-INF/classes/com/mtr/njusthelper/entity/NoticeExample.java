package com.mtr.njusthelper.entity;

import java.util.*;

public class NoticeExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<NoticeExample.Criteria> oredCriteria;
    
    public NoticeExample() {
        this.oredCriteria = new ArrayList<NoticeExample.Criteria>();
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
    
    public List<NoticeExample.Criteria> getOredCriteria() {
        return this.oredCriteria;
    }
    
    public void or(final NoticeExample.Criteria criteria) {
        this.oredCriteria.add(criteria);
    }
    
    public NoticeExample.Criteria or() {
        final NoticeExample.Criteria criteria = this.createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }
    
    public NoticeExample.Criteria createCriteria() {
        final NoticeExample.Criteria criteria = this.createCriteriaInternal();
        if (this.oredCriteria.size() == 0) {
            this.oredCriteria.add(criteria);
        }
        return criteria;
    }
    
    protected NoticeExample.Criteria createCriteriaInternal() {
        final NoticeExample.Criteria criteria = new NoticeExample.Criteria();
        return criteria;
    }
    
    public void clear() {
        this.oredCriteria.clear();
        this.orderByClause = null;
        this.distinct = false;
    }
}

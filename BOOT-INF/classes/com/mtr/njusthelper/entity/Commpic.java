package com.mtr.njusthelper.entity;

public class Commpic
{
    private Integer id;
    private Integer commentid;
    private String picurl;
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public Integer getCommentid() {
        return this.commentid;
    }
    
    public void setCommentid(final Integer commentid) {
        this.commentid = commentid;
    }
    
    public String getPicurl() {
        return this.picurl;
    }
    
    public void setPicurl(final String picurl) {
        this.picurl = ((picurl == null) ? null : picurl.trim());
    }
}

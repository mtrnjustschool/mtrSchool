package com.mtr.njusthelper.entity;

public class Picture
{
    private Integer id;
    private Integer postid;
    private String picurl;
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public Integer getPostid() {
        return this.postid;
    }
    
    public void setPostid(final Integer postid) {
        this.postid = postid;
    }
    
    public String getPicurl() {
        return this.picurl;
    }
    
    public void setPicurl(final String picurl) {
        this.picurl = ((picurl == null) ? null : picurl.trim());
    }
}

package com.mtr.njusthelper.entity;

public class Like
{
    private Integer id;
    private String openid;
    private Integer postid;
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public String getOpenid() {
        return this.openid;
    }
    
    public void setOpenid(final String openid) {
        this.openid = ((openid == null) ? null : openid.trim());
    }
    
    public Integer getPostid() {
        return this.postid;
    }
    
    public void setPostid(final Integer postid) {
        this.postid = postid;
    }
}

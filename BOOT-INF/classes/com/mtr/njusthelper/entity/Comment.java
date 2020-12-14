package com.mtr.njusthelper.entity;

import java.util.*;

public class Comment
{
    private Integer id;
    private String openid;
    private Integer postid;
    private String content;
    private Date createtime;
    private Integer pid;
    
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
    
    public String getContent() {
        return this.content;
    }
    
    public void setContent(final String content) {
        this.content = ((content == null) ? null : content.trim());
    }
    
    public Date getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(final Date createtime) {
        this.createtime = createtime;
    }
    
    public Integer getPid() {
        return this.pid;
    }
    
    public void setPid(final Integer pid) {
        this.pid = pid;
    }
}

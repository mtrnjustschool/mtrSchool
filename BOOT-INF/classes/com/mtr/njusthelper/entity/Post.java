package com.mtr.njusthelper.entity;

import java.util.*;

public class Post
{
    private Integer id;
    private String openid;
    private Integer label;
    private String content;
    private Date createtime;
    private String title;
    private String firstpic;
    private Integer hot;
    private Integer likenum;
    private Integer commentnum;
    
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
    
    public Integer getLabel() {
        return this.label;
    }
    
    public void setLabel(final Integer label) {
        this.label = label;
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
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(final String title) {
        this.title = ((title == null) ? null : title.trim());
    }
    
    public String getFirstpic() {
        return this.firstpic;
    }
    
    public void setFirstpic(final String firstpic) {
        this.firstpic = ((firstpic == null) ? null : firstpic.trim());
    }
    
    public Integer getHot() {
        return this.hot;
    }
    
    public void setHot(final Integer hot) {
        this.hot = hot;
    }
    
    public Integer getLikenum() {
        return this.likenum;
    }
    
    public void setLikenum(final Integer likenum) {
        this.likenum = likenum;
    }
    
    public Integer getCommentnum() {
        return this.commentnum;
    }
    
    public void setCommentnum(final Integer commentnum) {
        this.commentnum = commentnum;
    }
}

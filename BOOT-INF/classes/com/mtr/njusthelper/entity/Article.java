package com.mtr.njusthelper.entity;

import java.util.*;

public class Article
{
    private Integer id;
    private String title;
    private String brief;
    private String author;
    private String time;
    private String url;
    private Integer views;
    private Date modifytime;
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(final String title) {
        this.title = ((title == null) ? null : title.trim());
    }
    
    public String getBrief() {
        return this.brief;
    }
    
    public void setBrief(final String brief) {
        this.brief = ((brief == null) ? null : brief.trim());
    }
    
    public String getAuthor() {
        return this.author;
    }
    
    public void setAuthor(final String author) {
        this.author = ((author == null) ? null : author.trim());
    }
    
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = ((time == null) ? null : time.trim());
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(final String url) {
        this.url = ((url == null) ? null : url.trim());
    }
    
    public Integer getViews() {
        return this.views;
    }
    
    public void setViews(final Integer views) {
        this.views = views;
    }
    
    public Date getModifytime() {
        return this.modifytime;
    }
    
    public void setModifytime(final Date modifytime) {
        this.modifytime = modifytime;
    }
}

package com.mtr.njusthelper.entity;

public class PostWithBLOBs extends Post
{
    private String content;
    private String title;
    
    @Override
    public String getContent() {
        return this.content;
    }
    
    @Override
    public void setContent(final String content) {
        this.content = ((content == null) ? null : content.trim());
    }
    
    @Override
    public String getTitle() {
        return this.title;
    }
    
    @Override
    public void setTitle(final String title) {
        this.title = ((title == null) ? null : title.trim());
    }
}

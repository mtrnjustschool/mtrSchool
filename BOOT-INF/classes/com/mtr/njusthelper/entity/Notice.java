package com.mtr.njusthelper.entity;

public class Notice
{
    private String notice;
    
    public String getNotice() {
        return this.notice;
    }
    
    public void setNotice(final String notice) {
        this.notice = ((notice == null) ? null : notice.trim());
    }
}

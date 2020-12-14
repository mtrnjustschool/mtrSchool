package com.mtr.njusthelper.entity;

public class Phonenum
{
    private String phonenum;
    
    public String getPhonenum() {
        return this.phonenum;
    }
    
    public void setPhonenum(final String phonenum) {
        this.phonenum = ((phonenum == null) ? null : phonenum.trim());
    }
}

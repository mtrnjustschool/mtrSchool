package com.mtr.njusthelper.entity;

public class Weaccount
{
    private String openid;
    private String nickname;
    private String avatarurl;
    private Integer gender;
    private String city;
    private String province;
    private Integer ban;
    private String studentnum;
    
    public String getOpenid() {
        return this.openid;
    }
    
    public void setOpenid(final String openid) {
        this.openid = ((openid == null) ? null : openid.trim());
    }
    
    public String getNickname() {
        return this.nickname;
    }
    
    public void setNickname(final String nickname) {
        this.nickname = ((nickname == null) ? null : nickname.trim());
    }
    
    public String getAvatarurl() {
        return this.avatarurl;
    }
    
    public void setAvatarurl(final String avatarurl) {
        this.avatarurl = ((avatarurl == null) ? null : avatarurl.trim());
    }
    
    public Integer getGender() {
        return this.gender;
    }
    
    public void setGender(final Integer gender) {
        this.gender = gender;
    }
    
    public String getCity() {
        return this.city;
    }
    
    public void setCity(final String city) {
        this.city = ((city == null) ? null : city.trim());
    }
    
    public String getProvince() {
        return this.province;
    }
    
    public void setProvince(final String province) {
        this.province = ((province == null) ? null : province.trim());
    }
    
    public Integer getBan() {
        return this.ban;
    }
    
    public void setBan(final Integer ban) {
        this.ban = ban;
    }
    
    public String getStudentnum() {
        return this.studentnum;
    }
    
    public void setStudentnum(final String studentnum) {
        this.studentnum = ((studentnum == null) ? null : studentnum.trim());
    }
}

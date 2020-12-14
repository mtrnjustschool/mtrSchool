package com.mtr.njusthelper.entity;

public class Startdate
{
    private String startdate;
    private String term;
    
    public String getStartdate() {
        return this.startdate;
    }
    
    public void setStartdate(final String startdate) {
        this.startdate = ((startdate == null) ? null : startdate.trim());
    }
    
    public String getTerm() {
        return this.term;
    }
    
    public void setTerm(final String term) {
        this.term = ((term == null) ? null : term.trim());
    }
}

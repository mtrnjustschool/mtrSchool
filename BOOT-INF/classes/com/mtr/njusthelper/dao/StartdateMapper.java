package com.mtr.njusthelper.dao;

import org.apache.ibatis.annotations.*;
import com.mtr.njusthelper.entity.*;
import java.util.*;

@Mapper
public interface StartdateMapper
{
    int insert(final Startdate record);
    
    int insertSelective(final Startdate record);
    
    List<Startdate> selectByExample(final StartdateExample example);
}

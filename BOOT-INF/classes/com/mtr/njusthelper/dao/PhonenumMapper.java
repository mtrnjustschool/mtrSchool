package com.mtr.njusthelper.dao;

import org.apache.ibatis.annotations.*;
import com.mtr.njusthelper.entity.*;
import java.util.*;

@Mapper
public interface PhonenumMapper
{
    int insert(final Phonenum record);
    
    int insertSelective(final Phonenum record);
    
    List<Phonenum> selectByExample(final PhonenumExample example);
}

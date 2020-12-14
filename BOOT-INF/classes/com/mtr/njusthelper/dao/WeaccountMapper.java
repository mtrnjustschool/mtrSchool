package com.mtr.njusthelper.dao;

import org.apache.ibatis.annotations.*;
import com.mtr.njusthelper.entity.*;
import java.util.*;

@Mapper
public interface WeaccountMapper
{
    int deleteByPrimaryKey(final String openid);
    
    int insert(final Weaccount record);
    
    int insertSelective(final Weaccount record);
    
    List<Weaccount> selectByExample(final WeaccountExample example);
    
    Weaccount selectByPrimaryKey(final String openid);
    
    int updateByPrimaryKeySelective(final Weaccount record);
    
    int updateByPrimaryKey(final Weaccount record);
}

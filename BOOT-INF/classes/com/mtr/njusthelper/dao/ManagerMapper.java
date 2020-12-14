package com.mtr.njusthelper.dao;

import org.apache.ibatis.annotations.*;
import com.mtr.njusthelper.entity.*;
import java.util.*;

@Mapper
public interface ManagerMapper
{
    int deleteByPrimaryKey(final Integer id);
    
    int insert(final Manager record);
    
    int insertSelective(final Manager record);
    
    List<Manager> selectByExample(final ManagerExample example);
    
    Manager selectByPrimaryKey(final Integer id);
    
    int updateByPrimaryKeySelective(final Manager record);
    
    int updateByPrimaryKey(final Manager record);
}

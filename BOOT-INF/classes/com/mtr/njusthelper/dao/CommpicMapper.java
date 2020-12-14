package com.mtr.njusthelper.dao;

import org.apache.ibatis.annotations.*;
import com.mtr.njusthelper.entity.*;
import java.util.*;

@Mapper
public interface CommpicMapper
{
    int deleteByPrimaryKey(final Integer id);
    
    int insert(final Commpic record);
    
    int insertSelective(final Commpic record);
    
    List<Commpic> selectByExample(final CommpicExample example);
    
    Commpic selectByPrimaryKey(final Integer id);
    
    int updateByPrimaryKeySelective(final Commpic record);
    
    int updateByPrimaryKey(final Commpic record);
}

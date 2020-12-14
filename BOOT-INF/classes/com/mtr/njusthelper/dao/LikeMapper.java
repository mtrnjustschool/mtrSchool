package com.mtr.njusthelper.dao;

import org.apache.ibatis.annotations.*;
import com.mtr.njusthelper.entity.*;
import java.util.*;

@Mapper
public interface LikeMapper
{
    int deleteByPrimaryKey(final Integer id);
    
    int insert(final Like record);
    
    int insertSelective(final Like record);
    
    List<Like> selectByExample(final LikeExample example);
    
    Like selectByPrimaryKey(final Integer id);
    
    int updateByPrimaryKeySelective(final Like record);
    
    int updateByPrimaryKey(final Like record);
}

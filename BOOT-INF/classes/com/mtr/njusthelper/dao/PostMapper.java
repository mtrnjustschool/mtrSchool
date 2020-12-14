package com.mtr.njusthelper.dao;

import org.apache.ibatis.annotations.*;
import com.mtr.njusthelper.entity.*;
import java.util.*;

@Mapper
public interface PostMapper
{
    int deleteByPrimaryKey(final Integer id);
    
    int insert(final Post record);
    
    int insertSelective(final Post record);
    
    List<Post> selectByExample(final PostExample example);
    
    Post selectByPrimaryKey(final Integer id);
    
    int updateByPrimaryKeySelective(final Post record);
    
    int updateByPrimaryKey(final Post record);
}

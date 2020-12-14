package com.mtr.njusthelper.dao;

import org.apache.ibatis.annotations.*;
import com.mtr.njusthelper.entity.*;
import java.util.*;

@Mapper
public interface CommentMapper
{
    int deleteByPrimaryKey(final Integer id);
    
    int insert(final Comment record);
    
    int insertSelective(final Comment record);
    
    List<Comment> selectByExample(final CommentExample example);
    
    Comment selectByPrimaryKey(final Integer id);
    
    int updateByPrimaryKeySelective(final Comment record);
    
    int updateByPrimaryKey(final Comment record);
}

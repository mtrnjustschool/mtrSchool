package com.mtr.njusthelper.dao;

import org.apache.ibatis.annotations.*;
import com.mtr.njusthelper.entity.*;
import java.util.*;

@Mapper
public interface ArticleMapper
{
    int deleteByPrimaryKey(final Integer id);
    
    int insert(final Article record);
    
    int insertSelective(final Article record);
    
    List<Article> selectByExample(final ArticleExample example);
    
    Article selectByPrimaryKey(final Integer id);
    
    int updateByPrimaryKeySelective(final Article record);
    
    int updateByPrimaryKey(final Article record);
}

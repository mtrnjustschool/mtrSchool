package com.mtr.njusthelper.dao;

import org.apache.ibatis.annotations.*;
import com.mtr.njusthelper.entity.*;
import java.util.*;

@Mapper
public interface PictureMapper
{
    int deleteByPrimaryKey(final Integer id);
    
    int insert(final Picture record);
    
    int insertSelective(final Picture record);
    
    List<Picture> selectByExample(final PictureExample example);
    
    Picture selectByPrimaryKey(final Integer id);
    
    int updateByPrimaryKeySelective(final Picture record);
    
    int updateByPrimaryKey(final Picture record);
}

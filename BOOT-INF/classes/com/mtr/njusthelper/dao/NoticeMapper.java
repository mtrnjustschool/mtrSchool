package com.mtr.njusthelper.dao;

import org.apache.ibatis.annotations.*;
import com.mtr.njusthelper.entity.*;
import java.util.*;

@Mapper
public interface NoticeMapper
{
    int insert(final Notice record);
    
    int insertSelective(final Notice record);
    
    List<Notice> selectByExample(final NoticeExample example);
}

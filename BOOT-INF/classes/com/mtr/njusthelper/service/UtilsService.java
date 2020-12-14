package com.mtr.njusthelper.service;

import org.springframework.stereotype.*;
import com.mtr.njusthelper.dao.*;
import org.springframework.beans.factory.annotation.*;
import com.alibaba.fastjson.*;
import com.mtr.njusthelper.entity.*;

@Service
public class UtilsService
{
    private NoticeMapper noticeMapper;
    private PhonenumMapper phonenumMapper;
    
    @Autowired
    public UtilsService(final NoticeMapper noticeMapper, final PhonenumMapper phonenumMapper) {
        this.noticeMapper = noticeMapper;
        this.phonenumMapper = phonenumMapper;
    }
    
    public JSONObject getnotice() {
        final JSONObject jsonObject = new JSONObject();
        final NoticeExample noticeExample = new NoticeExample();
        noticeExample.or().andNoticeIsNotNull();
        final String notice = this.noticeMapper.selectByExample(noticeExample).get(0).getNotice();
        jsonObject.put("text", (Object)notice);
        return jsonObject;
    }
    
    public JSONObject getAdPhoneNum() {
        final JSONObject jsonObject = new JSONObject();
        final PhonenumExample phonenumExample = new PhonenumExample();
        phonenumExample.or().andPhonenumIsNotNull();
        final String phonenum = this.phonenumMapper.selectByExample(phonenumExample).get(0).getPhonenum();
        jsonObject.put("adphonenum", (Object)phonenum);
        return jsonObject;
    }
}

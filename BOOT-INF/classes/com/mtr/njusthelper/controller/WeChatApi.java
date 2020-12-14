package com.mtr.njusthelper.controller;

import com.mtr.njusthelper.service.*;
import org.springframework.beans.factory.annotation.*;
import com.alibaba.fastjson.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({ "/api/wechat" })
public class WeChatApi
{
    public WeChatService weChatService;
    
    @Autowired
    public WeChatApi(final WeChatService weChatService) {
        this.weChatService = weChatService;
    }
    
    @RequestMapping({ "/login" })
    public Object login(@RequestBody final JSONObject reqjson) {
        final String code = reqjson.getString("code");
        final String result = this.weChatService.wxLogin(code);
        return result;
    }
    
    @RequestMapping({ "/add" })
    public Object add(@RequestBody final JSONObject reqjson) {
        final String code = reqjson.getString("code");
        final String nickname = reqjson.getString("nickname");
        final String avatarurl = reqjson.getString("avatarurl");
        final int gender = reqjson.getIntValue("gender");
        final String province = reqjson.getString("province");
        final String city = reqjson.getString("city");
        final String studentnum = reqjson.getString("studentnum");
        final JSONObject result = this.weChatService.add(code, studentnum, nickname, avatarurl, gender, province, city);
        return result;
    }
}

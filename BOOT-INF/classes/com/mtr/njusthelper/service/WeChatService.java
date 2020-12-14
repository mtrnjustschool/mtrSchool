package com.mtr.njusthelper.service;

import org.springframework.stereotype.*;
import com.mtr.njusthelper.dao.*;
import org.springframework.beans.factory.annotation.*;
import com.mtr.njusthelper.utils.*;
import com.alibaba.fastjson.*;
import com.mtr.njusthelper.entity.*;

@Service
public class WeChatService
{
    private WeaccountMapper weaccountMapper;
    
    @Autowired
    public WeChatService(final WeaccountMapper weaccountMapper) {
        this.weaccountMapper = weaccountMapper;
    }
    
    public String wxLogin(final String code) {
        final String wxLoginUrl = "https://api.weixin.qq.com/sns/jscode2session";
        final String appId = "wx49d828b86b336218";
        final String secret = "bfc3a2f653730b06e51bc8f12d63e3fe";
        final String param = "appid=" + appId + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        final String url = String.valueOf(wxLoginUrl) + "?" + param;
        final JSONObject jsonObject = HtmlGetter.getJson(url, (String)null, (Object)null);
        final String openid = jsonObject.getString("openid");
        return openid;
    }
    
    public JSONObject add(final String code, final String studentnum, final String nickname, final String avatarurl, final int gender, final String province, final String city) {
        final JSONObject result = new JSONObject();
        final String openid = this.wxLogin(code);
        final Weaccount weaccount = this.weaccountMapper.selectByPrimaryKey(openid);
        if (weaccount != null) {
            result.put("success", (Object)1);
        }
        else {
            final Weaccount thisaccount = new Weaccount();
            thisaccount.setOpenid(openid);
            thisaccount.setStudentnum(studentnum);
            thisaccount.setNickname(nickname);
            thisaccount.setAvatarurl(avatarurl);
            thisaccount.setGender(Integer.valueOf(gender));
            thisaccount.setProvince(province);
            thisaccount.setCity(city);
            final int insertres = this.weaccountMapper.insert(thisaccount);
            if (insertres == 1) {
                result.put("success", (Object)1);
            }
        }
        return result;
    }
}

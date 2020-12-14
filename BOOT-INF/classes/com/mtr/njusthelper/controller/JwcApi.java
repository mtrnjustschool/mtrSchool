package com.mtr.njusthelper.controller;

import com.mtr.njusthelper.service.*;
import org.springframework.beans.factory.annotation.*;
import com.alibaba.fastjson.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping({ "/api/njustjwc" })
public class JwcApi
{
    private JwcService jwcService;
    
    @Autowired
    public JwcApi(final JwcService jwcService) {
        this.jwcService = jwcService;
    }
    
    @RequestMapping({ "/login" })
    public Object Login(@RequestBody final JSONObject requestJson) {
        final String username = requestJson.get((Object)"username").toString();
        final String password = requestJson.get((Object)"password").toString();
        final JSONObject jsonObject = this.jwcService.getVerification(username, password);
        return jsonObject;
    }
    
    @RequestMapping({ "/testlogin" })
    public Object testLogin(@RequestBody final JSONObject requestJson) {
        final JSONObject jsonObject = new JSONObject();
        final String cookie = requestJson.get((Object)"cookie").toString();
        final String result = this.jwcService.testLogin(cookie);
        jsonObject.put("success", (Object)result);
        return jsonObject;
    }
    
    @RequestMapping({ "/getgrade" })
    public Object getGrade(@RequestBody final JSONObject requestJson) {
        final String cook = (String)requestJson.get((Object)"cookie");
        final JSONObject jsonObject = this.jwcService.getGrade(cook);
        return jsonObject;
    }
    
    @RequestMapping({ "/getcourse" })
    public Object getCourse(@RequestBody final JSONObject requestJson) {
        final String cook = (String)requestJson.get((Object)"cookie");
        final JSONObject jsonObject = this.jwcService.getCourse(cook);
        return jsonObject;
    }
    
    @RequestMapping({ "/getexam" })
    public Object getExam(@RequestBody final JSONObject requestJson) {
        final String cook = (String)requestJson.get((Object)"cookie");
        final JSONObject jsonObject = this.jwcService.getExam(cook);
        return jsonObject;
    }
    
    @RequestMapping({ "/getclassroom" })
    public Object getClassroom(@RequestBody final JSONObject requestJson) {
        final String cook = (String)requestJson.get((Object)"cookie");
        final String jxlbh = (String)requestJson.get((Object)"jxlbh");
        final String zc = (String)requestJson.get((Object)"zc");
        final String xq = (String)requestJson.get((Object)"xq");
        final List<String> jcList = (List<String>)requestJson.get((Object)"jc");
        final JSONObject jsonObject = this.jwcService.getClassroom(cook, jxlbh, zc, xq, (List)jcList);
        return jsonObject;
    }
    
    @RequestMapping({ "/getcengke" })
    public Object getCengke(@RequestBody final JSONObject requestJson) {
        final String cook = requestJson.getString("cookie");
        final String name = requestJson.getString("name");
        final String teacher = requestJson.getString("teacher");
        final JSONObject jsonObject = this.jwcService.getCengke(cook, name, teacher);
        return jsonObject;
    }
}

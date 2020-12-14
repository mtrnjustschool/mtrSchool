package com.mtr.njusthelper.controller;

import com.mtr.njusthelper.service.*;
import org.springframework.beans.factory.annotation.*;
import com.alibaba.fastjson.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({ "/api/moment" })
public class MomentApi
{
    private MomentService momentService;
    
    @Autowired
    public MomentApi(final MomentService momentService) {
        this.momentService = momentService;
    }
    
    @RequestMapping({ "/post" })
    public JSONObject postmoment(@RequestBody final JSONObject requestJson) {
        final String openid = requestJson.getString("openid");
        final String title = requestJson.getString("title");
        final String content = requestJson.getString("content");
        final int label = requestJson.getIntValue("label");
        final JSONObject result = this.momentService.post(openid, title, content, label);
        return result;
    }
    
    @RequestMapping({ "/upload" })
    public JSONObject uploadPicture(@RequestBody final JSONObject requestJson) throws Exception {
        final int postid = requestJson.getIntValue("postid");
        final String picurl = requestJson.getString("picurl");
        final JSONObject result = this.momentService.uploadPicture(postid, picurl);
        return result;
    }
    
    @RequestMapping({ "/uploadtoken" })
    public JSONObject uploadToken() {
        final String token = this.momentService.uploadtoken();
        final JSONObject result = new JSONObject();
        result.put("uptoken", (Object)token);
        result.put("imgUrl", (Object)"https://mtrschool.top");
        return result;
    }
    
    @RequestMapping({ "/getmoments" })
    public JSONObject getMoments(@RequestBody final JSONObject requestJson) {
        final int pageNum = requestJson.getIntValue("pageNum");
        final int pageSize = requestJson.getIntValue("pageSize");
        final String key = requestJson.getString("searchval");
        final int sort = requestJson.getIntValue("sort");
        final JSONObject result = this.momentService.getmoments(pageNum, pageSize, key, sort);
        return result;
    }
    
    @RequestMapping({ "/getmomentdetail" })
    public JSONObject getMomentDetail(final int postid) {
        final JSONObject result = this.momentService.getMomentDetail(postid);
        return result;
    }
    
    @RequestMapping({ "/postcomment" })
    public JSONObject postComment(@RequestBody final JSONObject requestJson) {
        final String openid = requestJson.getString("openid");
        final int postid = requestJson.getIntValue("postid");
        final String form_id = requestJson.getString("formId");
        final String content = requestJson.getString("content");
        final int pid = requestJson.getIntValue("pid");
        final JSONObject result = this.momentService.postComment(openid, postid, content, pid, form_id);
        return result;
    }
    
    @RequestMapping({ "/getmymoments" })
    public JSONObject getMyMoments(@RequestBody final JSONObject requestJson) {
        final int pageNum = requestJson.getIntValue("pageNum");
        final int pageSize = requestJson.getIntValue("pageSize");
        final String openid = requestJson.getString("openid");
        final JSONObject result = this.momentService.getmymoments(pageNum, pageSize, openid);
        return result;
    }
    
    @RequestMapping({ "/deletemoment" })
    public JSONObject deleteMoment(@RequestBody final JSONObject requestJson) {
        final int postid = requestJson.getIntValue("id");
        final JSONObject result = this.momentService.deleteMoment(postid);
        return result;
    }
    
    @RequestMapping({ "/getmycomments" })
    public JSONObject getMyComments(@RequestBody final JSONObject requestJson) {
        final String openid = requestJson.getString("openid");
        final JSONObject result = this.momentService.getmycomments(openid);
        return result;
    }
    
    @RequestMapping({ "/getmycommentsnum" })
    public JSONObject getMyCommentsNum(@RequestBody final JSONObject requestJson) {
        final String openid = requestJson.getString("openid");
        final JSONObject result = this.momentService.getmycommentsnum(openid);
        return result;
    }
    
    @RequestMapping({ "/getmymessages" })
    public JSONObject getMyMessages(@RequestBody final JSONObject requestJson) {
        final String openid = requestJson.getString("openid");
        final JSONObject result = this.momentService.getmymessages(openid);
        return result;
    }
}

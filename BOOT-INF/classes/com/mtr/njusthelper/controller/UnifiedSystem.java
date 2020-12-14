package com.mtr.njusthelper.controller;

import com.mtr.njusthelper.service.*;
import org.springframework.beans.factory.annotation.*;
import com.alibaba.fastjson.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({ "/api/unified" })
public class UnifiedSystem
{
    private UnifieldSystemService unifieldSystemService;
    
    @Autowired
    public UnifiedSystem(final UnifieldSystemService unifieldSystemService) {
        this.unifieldSystemService = unifieldSystemService;
    }
    
    @RequestMapping({ "/getaeskey" })
    public JSONObject getAesKey() {
        final JSONObject jsonObject = this.unifieldSystemService.getAesKey();
        return jsonObject;
    }
    
    @RequestMapping({ "/login" })
    public Object Login(@RequestBody final JSONObject requestJson) {
        final String username = requestJson.getString("username");
        final String password = requestJson.getString("password");
        final String lt = requestJson.getString("lt");
        final String dllt = requestJson.getString("dllt");
        final String execution = requestJson.getString("execution");
        final String _eventId = requestJson.getString("_eventId");
        final String rmShown = requestJson.getString("rmShown");
        final String cookies = requestJson.getString("cookies");
        final JSONObject jsonObject = this.unifieldSystemService.getVerification(username, password, lt, dllt, execution, _eventId, rmShown, cookies);
        return jsonObject;
    }
}

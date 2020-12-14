package com.mtr.njusthelper.controller;

import org.springframework.web.bind.annotation.*;
import com.mtr.njusthelper.service.*;
import org.springframework.beans.factory.annotation.*;
import com.alibaba.fastjson.*;

@RestController
@RequestMapping({ "/api/utils" })
public class GetUtilsApi
{
    @Value("${banner1}")
    private String banner1;
    private UtilsService utilsService;
    
    @Autowired
    public GetUtilsApi(final UtilsService utilsService) {
        this.utilsService = utilsService;
    }
    
    @RequestMapping({ "/getbanner1" })
    public Boolean getBanner1() {
        if (this.banner1.equals("true")) {
            return true;
        }
        return false;
    }
    
    @RequestMapping({ "/getnotice" })
    public Object getnotice() {
        final JSONObject jsonObject = this.utilsService.getnotice();
        return jsonObject;
    }
    
    @RequestMapping({ "/getadphonenum" })
    public Object getAdPhoneNum() {
        final JSONObject jsonObject = this.utilsService.getAdPhoneNum();
        return jsonObject;
    }
}

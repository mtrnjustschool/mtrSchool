package com.mtr.njusthelper.controller;

import com.mtr.njusthelper.service.*;
import org.springframework.beans.factory.annotation.*;
import com.alibaba.fastjson.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({ "/api/library" })
public class LibraryApi
{
    private LibraryService libraryService;
    
    @Autowired
    public LibraryApi(final LibraryService libraryService) {
        this.libraryService = libraryService;
    }
    
    @RequestMapping({ "/search" })
    public JSONObject search(@RequestBody final JSONObject requestJson) {
        final JSONObject jsonObject = this.libraryService.search(requestJson.getString("keyword"));
        return jsonObject;
    }
    
    @RequestMapping({ "/detail" })
    public JSONObject detail(@RequestBody final JSONObject requestJson) {
        final String marcRecNo = requestJson.getString("marcRecNo");
        final JSONObject jsonObject = this.libraryService.detail(marcRecNo);
        return jsonObject;
    }
    
    @RequestMapping({ "/borrowed" })
    public JSONObject iborrowed(@RequestBody final JSONObject requestJson) {
        final String username = requestJson.getString("libusername");
        final String password = requestJson.getString("libpassword");
        final JSONObject jsonObject = this.libraryService.iborrowed(username, password);
        return jsonObject;
    }
}

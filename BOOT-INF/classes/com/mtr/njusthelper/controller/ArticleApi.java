package com.mtr.njusthelper.controller;

import org.springframework.web.bind.annotation.*;
import com.mtr.njusthelper.service.*;
import org.springframework.beans.factory.annotation.*;
import com.alibaba.fastjson.*;
import java.util.*;
import com.mtr.njusthelper.entity.*;

@RestController
@RequestMapping({ "/api/article" })
public class ArticleApi
{
    private ArticleService articleService;
    
    @Autowired
    public ArticleApi(final ArticleService articleService) {
        this.articleService = articleService;
    }
    
    @RequestMapping({ "/getarticle" })
    public JSONObject getArticle() {
        final JSONObject jsonObject = new JSONObject();
        final List<Article> articles = (List<Article>)this.articleService.selectAll();
        jsonObject.put("articles", (Object)articles);
        return jsonObject;
    }
    
    @RequestMapping({ "/addarticleviews" })
    public void addArticleViews(final int id) {
        this.articleService.addViews(id);
    }
}

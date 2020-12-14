package com.mtr.njusthelper.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import com.mtr.njusthelper.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.servlet.*;
import java.util.*;
import com.mtr.njusthelper.entity.*;

@Controller
@RequestMapping({ "/admin" })
public class ArticleController
{
    private ArticleService articleService;
    
    @Autowired
    public ArticleController(final ArticleService articleService) {
        this.articleService = articleService;
    }
    
    @RequestMapping({ "/insert" })
    public ModelAndView insert(final String title, final String brief, final String author, final String url) {
        final String result = this.articleService.insert(title, brief, author, url);
        final ModelAndView modelAndView = this.listAll();
        modelAndView.addObject("result", (Object)result);
        return modelAndView;
    }
    
    @RequestMapping({ "/listall" })
    public ModelAndView listAll() {
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("allarticle");
        final List<Article> articles = this.articleService.selectAll();
        modelAndView.addObject("articles", (Object)articles);
        return modelAndView;
    }
    
    @RequestMapping({ "/delete" })
    public ModelAndView delete(final int id) {
        final String result = this.articleService.delete(id);
        final ModelAndView modelAndView = this.listAll();
        modelAndView.addObject("result", (Object)result);
        return modelAndView;
    }
    
    @RequestMapping({ "/update" })
    public ModelAndView update(final int cuId, final String cuTitle, final String cuBrief, final String cuAuthor, final String cuTime, final String cuUrl, final int cuViews) {
        final String result = this.articleService.update(cuId, cuTitle, cuBrief, cuAuthor, cuTime, cuUrl, cuViews);
        final ModelAndView modelAndView = this.listAll();
        modelAndView.addObject("result", (Object)result);
        return modelAndView;
    }
}

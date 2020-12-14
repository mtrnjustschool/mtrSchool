package com.mtr.njusthelper.service;

import org.springframework.stereotype.*;
import com.mtr.njusthelper.dao.*;
import org.springframework.beans.factory.annotation.*;
import java.text.*;
import com.mtr.njusthelper.entity.*;
import com.mtr.njusthelper.utils.*;
import java.util.*;

@Service
public class ArticleService
{
    private ArticleMapper articleMapper;
    
    @Autowired
    public ArticleService(final ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }
    
    public String insert(final String title, final String brief, final String author, final String url) {
        final Article article = new Article();
        article.setTitle(title);
        article.setBrief(brief);
        article.setAuthor(author);
        final Date now = new Date();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        article.setTime(sdf.format(now));
        article.setUrl(url);
        article.setViews(Integer.valueOf(0));
        article.setModifytime(now);
        final int insertRes = this.articleMapper.insert(article);
        if (insertRes == 1) {
            return "\u6dfb\u52a0\u6210\u529f\uff01";
        }
        return "\u6dfb\u52a0\u5931\u8d25\uff01";
    }
    
    public String delete(final int id) {
        final int deleteRes = this.articleMapper.deleteByPrimaryKey(id);
        if (deleteRes == 1) {
            return "\u5220\u9664\u6210\u529f\uff01";
        }
        return "\u5220\u9664\u5931\u8d25\uff01";
    }
    
    public String update(final int id, final String title, final String brief, final String author, final String time, final String url, final int views) {
        final Article article = this.articleMapper.selectByPrimaryKey(id);
        article.setTitle(title);
        article.setBrief(brief);
        article.setAuthor(author);
        article.setTime(time);
        final Date now = new Date();
        article.setUrl(url);
        article.setViews(Integer.valueOf(views));
        article.setModifytime(now);
        final int updateRes = this.articleMapper.updateByPrimaryKey(article);
        if (updateRes == 1) {
            return "\u4fee\u6539\u6210\u529f\uff01";
        }
        return "\u4fee\u6539\u5931\u8d25\uff01";
    }
    
    public void addViews(final int id) {
        final Article article = this.articleMapper.selectByPrimaryKey(id);
        final int views = article.getViews() + 1;
        article.setViews(Integer.valueOf(views));
        final int updateRes = this.articleMapper.updateByPrimaryKey(article);
    }
    
    public List<Article> selectByTitle(final String title) {
        final ArticleExample articleExample = new ArticleExample();
        articleExample.or().andTitleGreaterThan(title);
        final List<Article> articles = this.articleMapper.selectByExample(articleExample);
        return articles;
    }
    
    public List<Article> selectAll() {
        final ArticleExample articleExample = new ArticleExample();
        articleExample.or().andIdIsNotNull();
        final List<Article> articles = this.articleMapper.selectByExample(articleExample);
        final ArticleComparator articleComparator = new ArticleComparator();
        articles.sort((Comparator<? super Article>)articleComparator);
        return articles;
    }
}

package com.mtr.njusthelper.service;

import org.springframework.stereotype.*;
import com.mtr.njusthelper.dao.*;
import org.springframework.beans.factory.annotation.*;
import com.alibaba.fastjson.*;
import com.qiniu.util.*;
import com.github.pagehelper.*;
import java.util.*;
import com.mtr.njusthelper.entity.*;
import java.text.*;
import com.mtr.njusthelper.utils.*;
import com.qiniu.storage.*;
import com.qiniu.common.*;

@Service
public class MomentService
{
    private PostMapper postMapper;
    private PictureMapper pictureMapper;
    private WeaccountMapper weaccountMapper;
    private CommentMapper commentMapper;
    
    @Autowired
    public MomentService(final PostMapper postMapper, final PictureMapper pictureMapper, final WeaccountMapper weaccountMapper, final CommentMapper commentMapper) {
        this.postMapper = postMapper;
        this.pictureMapper = pictureMapper;
        this.weaccountMapper = weaccountMapper;
        this.commentMapper = commentMapper;
    }
    
    public JSONObject post(final String openid, final String title, final String content, final int label) {
        final JSONObject result = new JSONObject();
        final PostWithBLOBs post = new PostWithBLOBs();
        post.setOpenid(openid);
        post.setTitle(title);
        post.setContent(content);
        post.setLabel(label);
        post.setHot(0);
        post.setCommentnum(0);
        post.setLikenum(0);
        final Date now = new Date();
        post.setCreatetime(now);
        final int insertres = this.postMapper.insert(post);
        if (insertres == 1) {
            final int postid = post.getId();
            result.put("success", (Object)1);
            result.put("postid", (Object)postid);
        }
        else {
            result.put("success", (Object)0);
        }
        return result;
    }
    
    public String uploadtoken() {
        final String accessKey = "fPlJKipxytij8mGD3QKRxCqEZCCwS8PjxqfVbAzM";
        final String secretKey = "xZL_qzjaacbHpDl8dUdUuQH6KIEEDfYtgBEkaD9p";
        final String bucket = "njusthelper";
        final Auth auth = Auth.create(accessKey, secretKey);
        final String upToken = auth.uploadToken(bucket);
        System.out.println(upToken);
        return upToken;
    }
    
    public JSONObject uploadPicture(final int postid, final String picurl) {
        final JSONObject result = new JSONObject();
        final Picture picture = new Picture();
        picture.setPostid(postid);
        picture.setPicurl(picurl);
        final int insertres = this.pictureMapper.insert(picture);
        final PictureExample pictureExample = new PictureExample();
        pictureExample.or().andPostidEqualTo(Integer.valueOf(postid));
        final List<Picture> pictures = this.pictureMapper.selectByExample(pictureExample);
        final Picture firstpicture = pictures.get(0);
        final Post post = this.postMapper.selectByPrimaryKey(postid);
        post.setFirstpic(firstpicture.getPicurl());
        this.postMapper.updateByPrimaryKey(post);
        if (insertres == 1) {
            result.put("success", (Object)1);
        }
        else {
            result.put("success", (Object)0);
        }
        return result;
    }
    
    public JSONObject getmoments(final int pageNum, final int pageSize, final String key, final int sort) {
        final JSONObject result = new JSONObject();
        final PostExample postExample = new PostExample();
        if (key.equals("")) {
            postExample.or().andIdIsNotNull();
        }
        else {
            postExample.or().andTitleLike("%" + key + "%");
        }
        if (sort == 0) {
            postExample.setOrderByClause("createtime desc");
        }
        else {
            postExample.setOrderByClause("hot desc");
        }
        PageHelper.startPage(pageNum, pageSize);
        final List<Post> resultlist = this.postMapper.selectByExample(postExample);
        final PageInfo pageInfo = new PageInfo((List)resultlist);
        result.put("pageNum", (Object)pageInfo.getPageNum());
        result.put("nextPage", (Object)pageInfo.getNextPage());
        final List<Post> pageList = (List<Post>)pageInfo.getList();
        final List<JSONObject> resultJsonList = new ArrayList<JSONObject>();
        for (final Post post : pageList) {
            final JSONObject thisPost = new JSONObject();
            thisPost.put("id", (Object)post.getId());
            thisPost.put("openid", (Object)post.getOpenid());
            thisPost.put("label", (Object)post.getLabel());
            thisPost.put("content", (Object)post.getContent());
            thisPost.put("title", (Object)post.getTitle());
            thisPost.put("firstpic", (Object)post.getFirstpic());
            thisPost.put("hot", (Object)post.getHot());
            thisPost.put("likenum", (Object)post.getLikenum());
            thisPost.put("commentnum", (Object)post.getCommentnum());
            thisPost.put("createtime", (Object)this.processTime(post.getCreatetime()));
            resultJsonList.add(thisPost);
        }
        result.put("list", (Object)resultJsonList);
        return result;
    }
    
    public JSONObject getmymessages(final String openid) {
        final JSONObject result = new JSONObject();
        final CommentExample commentExample = new CommentExample();
        commentExample.or().andOpenidEqualTo(openid);
        final List<Comment> comments = this.commentMapper.selectByExample(commentExample);
        final List<Comment> messagesList = new ArrayList<Comment>();
        return result;
    }
    
    public JSONObject getmycomments(final String openid) {
        final JSONObject result = new JSONObject();
        final PostExample postExample = new PostExample();
        postExample.or().andOpenidEqualTo(openid);
        postExample.setOrderByClause("createtime desc");
        final List<Post> resultlist = this.postMapper.selectByExample(postExample);
        final List<Comment> commentList = new ArrayList<Comment>();
        for (final Post post : resultlist) {
            final CommentExample commentExample = new CommentExample();
            commentExample.or().andPostidEqualTo(post.getId()).andPidEqualTo(Integer.valueOf(0));
            final List<Comment> comments = this.commentMapper.selectByExample(commentExample);
            commentList.addAll(comments);
        }
        final CommentExample commentExample2 = new CommentExample();
        commentExample2.or().andOpenidEqualTo(openid);
        final List<Comment> comments2 = this.commentMapper.selectByExample(commentExample2);
        for (final Comment comment : comments2) {
            final CommentExample thisCommentExample = new CommentExample();
            thisCommentExample.or().andPidEqualTo(comment.getId());
            final List<Comment> thisCommentList = this.commentMapper.selectByExample(thisCommentExample);
            commentList.addAll(thisCommentList);
        }
        commentList.sort((Comparator<? super Comment>)new CommentComparator());
        final List<JSONObject> resultJsonList = new ArrayList<JSONObject>();
        for (final Comment comment2 : commentList) {
            final JSONObject thisComment = new JSONObject();
            final String commentOpenId = comment2.getOpenid();
            final Weaccount weaccount = this.weaccountMapper.selectByPrimaryKey(commentOpenId);
            thisComment.put("avatar", (Object)weaccount.getAvatarurl());
            thisComment.put("nickname", (Object)weaccount.getNickname());
            thisComment.put("postid", (Object)comment2.getPostid());
            final Post thisPost = this.postMapper.selectByPrimaryKey(comment2.getPostid());
            thisComment.put("posttitle", (Object)thisPost.getTitle());
            thisComment.put("content", (Object)comment2.getContent());
            thisComment.put("createtime", (Object)this.processTime(comment2.getCreatetime()));
            resultJsonList.add(thisComment);
        }
        result.put("list", (Object)resultJsonList);
        return result;
    }
    
    public JSONObject getmycommentsnum(final String openid) {
        final JSONObject result = new JSONObject();
        final PostExample postExample = new PostExample();
        postExample.or().andOpenidEqualTo(openid);
        final List<Post> resultlist = this.postMapper.selectByExample(postExample);
        final List<Comment> commentList = new ArrayList<Comment>();
        for (final Post post : resultlist) {
            final CommentExample commentExample = new CommentExample();
            commentExample.or().andPostidEqualTo(post.getId()).andPidEqualTo(Integer.valueOf(0));
            final List<Comment> comments = this.commentMapper.selectByExample(commentExample);
            commentList.addAll(comments);
        }
        final CommentExample commentExample2 = new CommentExample();
        commentExample2.or().andOpenidEqualTo(openid);
        final List<Comment> comments2 = this.commentMapper.selectByExample(commentExample2);
        for (final Comment comment : comments2) {
            final CommentExample thisCommentExample = new CommentExample();
            thisCommentExample.or().andPidEqualTo(comment.getId());
            final List<Comment> thisCommentList = this.commentMapper.selectByExample(thisCommentExample);
            commentList.addAll(thisCommentList);
        }
        final int commentsNum = commentList.size();
        result.put("commentsNum", (Object)commentsNum);
        return result;
    }
    
    public JSONObject getmymoments(final int pageNum, final int pageSize, final String openid) {
        final JSONObject result = new JSONObject();
        final PostExample postExample = new PostExample();
        postExample.or().andOpenidEqualTo(openid);
        postExample.setOrderByClause("createtime desc");
        PageHelper.startPage(pageNum, pageSize);
        final List<Post> resultlist = this.postMapper.selectByExample(postExample);
        final PageInfo pageInfo = new PageInfo((List)resultlist);
        result.put("pageNum", (Object)pageInfo.getPageNum());
        result.put("nextPage", (Object)pageInfo.getNextPage());
        final List<Post> pageList = (List<Post>)pageInfo.getList();
        final List<JSONObject> resultJsonList = new ArrayList<JSONObject>();
        for (final Post post : pageList) {
            final JSONObject thisPost = new JSONObject();
            thisPost.put("id", (Object)post.getId());
            thisPost.put("openid", (Object)post.getOpenid());
            thisPost.put("label", (Object)post.getLabel());
            thisPost.put("content", (Object)post.getContent());
            thisPost.put("title", (Object)post.getTitle());
            thisPost.put("firstpic", (Object)post.getFirstpic());
            thisPost.put("hot", (Object)post.getHot());
            thisPost.put("likenum", (Object)post.getLikenum());
            thisPost.put("commentnum", (Object)post.getCommentnum());
            thisPost.put("createtime", (Object)this.processTime(post.getCreatetime()));
            resultJsonList.add(thisPost);
        }
        result.put("list", (Object)resultJsonList);
        return result;
    }
    
    public JSONObject getMomentDetail(final int postid) {
        final JSONObject result = new JSONObject();
        final Post post = this.postMapper.selectByPrimaryKey(postid);
        final String openid = post.getOpenid();
        final Weaccount weaccount = this.weaccountMapper.selectByPrimaryKey(openid);
        final PictureExample pictureExample = new PictureExample();
        pictureExample.or().andPostidEqualTo(Integer.valueOf(postid));
        final List<Picture> pictures = this.pictureMapper.selectByExample(pictureExample);
        final List<String> picurls = new ArrayList<String>();
        for (final Picture picture : pictures) {
            picurls.add(picture.getPicurl());
        }
        final CommentExample commentExample = new CommentExample();
        commentExample.or().andPostidEqualTo(Integer.valueOf(postid));
        final List<Comment> comments = this.commentMapper.selectByExample(commentExample);
        final List<JSONObject> commentsList = new ArrayList<JSONObject>();
        for (final Comment comment : comments) {
            final JSONObject thisComment = new JSONObject();
            final String comopenid = comment.getOpenid();
            final Weaccount comwe = this.weaccountMapper.selectByPrimaryKey(comopenid);
            final JSONObject comWeJson = new JSONObject();
            comWeJson.put("nickname", (Object)comwe.getNickname());
            comWeJson.put("avatarurl", (Object)comwe.getAvatarurl());
            comWeJson.put("openid", (Object)comwe.getOpenid());
            thisComment.put("weaccount", (Object)comWeJson);
            thisComment.put("id", (Object)comment.getId());
            final int pid = comment.getPid();
            thisComment.put("pid", (Object)pid);
            if (comment.getPid() != 0) {
                final Comment responsed = this.commentMapper.selectByPrimaryKey(pid);
                final Weaccount responsedWe = this.weaccountMapper.selectByPrimaryKey(responsed.getOpenid());
                thisComment.put("responsed", (Object)responsedWe.getNickname());
            }
            else {
                thisComment.put("responsed", (Object)null);
            }
            thisComment.put("content", (Object)comment.getContent());
            thisComment.put("createtime", (Object)this.processTime(comment.getCreatetime()));
            commentsList.add(thisComment);
        }
        final JSONObject moment = new JSONObject();
        moment.put("id", (Object)post.getId());
        moment.put("title", (Object)post.getTitle());
        moment.put("content", (Object)post.getContent());
        moment.put("likenum", (Object)post.getLikenum());
        moment.put("commentnum", (Object)post.getCommentnum());
        moment.put("createtime", (Object)this.processTime(post.getCreatetime()));
        final JSONObject weac = new JSONObject();
        try {
            weac.put("nickname", (Object)weaccount.getNickname());
        }
        catch (Exception e) {
            weac.put("nickname", (Object)"\u56e0\u7279\u6b8a\u7b26\u53f7\u65e0\u6cd5\u663e\u793a");
        }
        weac.put("avatarurl", (Object)weaccount.getAvatarurl());
        weac.put("openid", (Object)weaccount.getOpenid());
        moment.put("weaccount", (Object)weac);
        moment.put("pictures", (Object)picurls);
        moment.put("comments", (Object)commentsList);
        result.put("moment", (Object)moment);
        return result;
    }
    
    public JSONObject postComment(final String openid, final int postid, final String content, final int pid, final String form_id) {
        final JSONObject result = new JSONObject();
        final Weaccount weaccount = this.weaccountMapper.selectByPrimaryKey(openid);
        final Comment comment = new Comment();
        comment.setOpenid(openid);
        comment.setPostid(postid);
        comment.setContent(content);
        comment.setPid(pid);
        final Date now = new Date();
        comment.setCreatetime(now);
        final int insertres = this.commentMapper.insert(comment);
        if (insertres == 1) {
            final PostExample postExample = new PostExample();
            postExample.or().andIdEqualTo(Integer.valueOf(postid));
            final Post post = this.postMapper.selectByPrimaryKey(postid);
            final String touser = post.getOpenid();
            final int commentnum = post.getCommentnum();
            post.setCommentnum(commentnum + 1);
            final int hot = post.getHot();
            post.setHot(hot + 1);
            this.postMapper.updateByPrimaryKey(post);
            final String template_id = "xkI4THQJpbEOmB5GC9RL2spi5oitZGJilG6tpM8l7Zc";
            final JSONObject data = new JSONObject();
            final JSONObject value1 = new JSONObject();
            value1.put("value", (Object)post.getTitle());
            data.put("keyword1", (Object)value1);
            final JSONObject value2 = new JSONObject();
            value2.put("value", (Object)weaccount.getNickname());
            data.put("keyword2", (Object)value2);
            final JSONObject value3 = new JSONObject();
            value3.put("value", (Object)content);
            data.put("keyword3", (Object)value3);
            final JSONObject value4 = new JSONObject();
            value4.put("value", (Object)this.processTime(now));
            data.put("keyword4", (Object)value4);
            final JSONObject sumResult = this.sendUniformMessage(touser, template_id, form_id, data);
            if (sumResult.getInteger("errcode") == 0) {
                result.put("success", (Object)1);
            }
            else {
                result.put("success", (Object)0);
            }
        }
        else {
            result.put("success", (Object)0);
        }
        return result;
    }
    
    public JSONObject deleteMoment(final int postid) {
        final JSONObject result = new JSONObject();
        final int deleteres = this.postMapper.deleteByPrimaryKey(postid);
        if (deleteres == 1) {
            final CommentExample commentExample = new CommentExample();
            commentExample.or().andPostidEqualTo(Integer.valueOf(postid));
            final List<Comment> comments = this.commentMapper.selectByExample(commentExample);
            for (final Comment comment : comments) {
                this.commentMapper.deleteByPrimaryKey(comment.getId());
            }
            final PictureExample pictureExample = new PictureExample();
            pictureExample.or().andPostidEqualTo(Integer.valueOf(postid));
            final List<Picture> pictures = this.pictureMapper.selectByExample(pictureExample);
            for (final Picture picture : pictures) {
                final String key = picture.getPicurl().substring(22);
                this.deleteQiniu(key);
                this.pictureMapper.deleteByPrimaryKey(picture.getId());
            }
            result.put("success", (Object)1);
        }
        else {
            result.put("success", (Object)0);
        }
        return result;
    }
    
    public String processTime(final Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
        final String time = sdf.format(date);
        return time;
    }
    
    public JSONObject getWeToken() {
        final String appId = "wx49d828b86b336218";
        final String secret = "bfc3a2f653730b06e51bc8f12d63e3fe";
        final String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + secret;
        final JSONObject token = HtmlGetter.getJson(getTokenUrl, null, null);
        return token;
    }
    
    public JSONObject sendUniformMessage(final String touser, final String template_id, final String form_id, final JSONObject data) {
        final String access_token = this.getWeToken().getString("access_token");
        final String sendMessageUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + access_token;
        final JSONObject postData = new JSONObject();
        postData.put("touser", (Object)touser);
        postData.put("template_id", (Object)template_id);
        postData.put("form_id", (Object)form_id);
        postData.put("data", (Object)data);
        postData.put("emphasis_keyword", (Object)"keyword1.DATA");
        final JSONObject result = HtmlGetter.getJson(sendMessageUrl, null, postData);
        return result;
    }
    
    public void deleteQiniu(final String key) {
        final Configuration cfg = new Configuration(Zone.zone0());
        final String accessKey = "fPlJKipxytij8mGD3QKRxCqEZCCwS8PjxqfVbAzM";
        final String secretKey = "xZL_qzjaacbHpDl8dUdUuQH6KIEEDfYtgBEkaD9p";
        final String bucket = "njusthelper";
        final Auth auth = Auth.create(accessKey, secretKey);
        final BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        }
        catch (QiniuException ex) {
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
}

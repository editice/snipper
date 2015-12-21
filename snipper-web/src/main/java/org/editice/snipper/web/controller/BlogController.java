package org.editice.snipper.web.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.editice.snipper.client.constants.GlobalIdentifier;
import org.editice.snipper.client.domain.BlogDO;
import org.editice.snipper.client.domain.PageBean;
import org.editice.snipper.client.util.Convert;
import org.editice.snipper.dal.dao.BlogDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 博客页面
 *
 * @author tinglang(editice@gmail.com) on 15/12/5.
 */
@Controller
@RequestMapping(value = "blogs")
public class BlogController {

    @Resource
    BlogDAO blogDAO;

    private static final int PAGE_SIZE = 10;

    //主页
    @RequestMapping(value = "blogPage")
    @ResponseBody
    public ModelAndView blogPage(HttpServletRequest request, HttpServletResponse response) {
        int idx = Convert.asInt(request.getAttribute("idx"), 1);

        PageBean pageBean = new PageBean(idx, PAGE_SIZE);
        int start = pageBean.getStart();
//        int end= Math.min(blogDOs.size(), start + PageBean.SIZE);

        List<BlogDO> blogDOs = blogDAO.queryBlogs(start, PAGE_SIZE);
        if (CollectionUtils.isEmpty(blogDOs)) {
            return new ModelAndView("blogPage", "res", null);
        }

        Map<String, Object> res = Maps.newHashMap();
        res.put("blogs", blogDOs);
        res.put("pageBean", pageBean);
        return new ModelAndView("blogPage", "res", res);
    }

    //博客对应的标签页
    @RequestMapping(value = "blogTag")
    @ResponseBody
    public ModelAndView blogTag(HttpServletRequest request, HttpServletResponse response) {
        int idx = Convert.asInt(request.getAttribute("idx"), 1);
        String tag = (String) request.getAttribute("blogTag");

        PageBean pageBean = new PageBean(idx, PAGE_SIZE);
        int start = pageBean.getStart();
//        int end= Math.min(blogDOs.size(), start + PageBean.SIZE);

        int cnt = blogDAO.countBlogsByKeyword(tag);

        Map<String, Object> res = Maps.newHashMap();
        pageBean.setTotal(cnt);
        res.put("pageBean", pageBean);
        res.put("cnt",cnt);
        res.put("tag", tag);
        if(cnt<=0){
            res.put("blogs", null);
            return new ModelAndView("blogTag", "res", res);
        }

        List<BlogDO> blogDOs = blogDAO.queryBlogsByKeyword(tag, start, PAGE_SIZE);
        res.put("blogs", blogDOs);
        return new ModelAndView("blogTag", "res", res);
    }

    //博客详情页
    @RequestMapping(value = "blogDetail")
    @ResponseBody
    public ModelAndView blogDetail(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> res = Maps.newHashMap();
        int id = Convert.asInt(request.getAttribute("blogId"),7);
        BlogDO blogDO = blogDAO.queryBlogsById(id);
        if (id <= 0 || blogDO==null) {
            return new ModelAndView("blogDetail", "res", res);
        }

        String[] keywords = blogDO.getKeywords().split(",");
        List<String> keywordList = Lists.newArrayList(keywords);
        res.put("keywords", keywordList);
        res.put("blog", blogDO);
        return new ModelAndView("blogDetail", "res", res);
    }

    //新增博客
    @RequestMapping(value = "blogInsert")
    @ResponseBody
    public ModelAndView blogInsert(HttpServletRequest request, HttpServletResponse response) {
        String title = (String) request.getAttribute("title");
        String content = (String) request.getAttribute("content");
        String keywords = (String) request.getAttribute("keywords");
        if (title == null || content == null) {
            return new ModelAndView("blogInsert", "res", null);
        }

        BlogDO blogDO = new BlogDO();
        blogDO.setTitle(title);
        blogDO.setContent(content);
        blogDO.setAuthor(GlobalIdentifier.author);
        blogDO.setCreate_time(new Date());
        blogDO.setKeywords(keywords);
        blogDO.setNote(content.substring(0, 40));
        int r = blogDAO.insertBlog(blogDO);
        String result = (r == 0) ? "SUCCESS" : "FAIL";

        Map<String, String> res = Maps.newHashMap();
        res.put("result", result);
        res.put("title",title);
        res.put("content",content);
        res.put("keywords",keywords);
        return new ModelAndView("blogInsert", "res", res);
    }

    //更新博客
    @RequestMapping(value = "blogUpdate")
    @ResponseBody
    public ModelAndView blogUpdate(HttpServletRequest request, HttpServletResponse response) {
        String title = (String) request.getAttribute("title");
        String content = (String) request.getAttribute("content");
        String keywords = (String) request.getAttribute("keywords");
        int blogId= Convert.asInt(request.getAttribute("blogId"));

        Map<String, String> res = Maps.newHashMap();
        BlogDO blogDO = blogDAO.queryBlogsById(blogId);
        if(blogDO==null){
            res.put("result", "FAIL");
            return new ModelAndView("blogUpdate", "res", res);
        }

        if(title ==null || content==null || keywords==null){
            res.put("result","query");
            res.put("title",blogDO.getTitle());
            res.put("content",blogDO.getContent());
            res.put("keywords",blogDO.getKeywords());
            res.put("blogId",blogDO.getId()+"");
            return new ModelAndView("blogUpdate", "res", res);

        }

        res.put("title",title);
        res.put("content",content);
        res.put("keywords",keywords);
        if (title.equals(blogDO.getTitle())
                && content.equals(blogDO.getContent())
                && keywords.contains(blogDO.getKeywords())) {
            res.put("result","SUCCESS");
            return new ModelAndView("blogUpdate", "res", res);
        }

        blogDO.setTitle(title);
        blogDO.setContent(content);
        blogDO.setKeywords(keywords);
        boolean update = blogDAO.updateBlog(blogId, blogDO);
        res.put("result", (update ?"SUCCESS":"FAIL"));
        return new ModelAndView("blogUpdate", "res", res);
    }


}

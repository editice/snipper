package org.editice.snipper.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 博客页面
 *
 * @author tinglang(editice@gmail.com) on 15/12/5.
 */
@Controller
@RequestMapping(value = "blogs")
public class BlogController {

    //主页
    @RequestMapping(value = "blogPage")
    @ResponseBody
    public ModelAndView blogPage(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("blogPage","index",null);
    }
}

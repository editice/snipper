package org.editice.snipper.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 关于页面
 *
 * @author tinglang(editice@gmail.com) on 15/12/5.
 */
@Controller
@RequestMapping(value = "about")
public class AboutController {

    //关于
    @RequestMapping(value = "page")
    @ResponseBody
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("about","index",null);
    }
}

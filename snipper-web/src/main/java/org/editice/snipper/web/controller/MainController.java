package org.editice.snipper.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 承接页面入口
 * @author tinglang(editice@gmail.com) on 15/11/26.
 */
@Controller
@RequestMapping(value = "main")
public class MainController {

    @RequestMapping(value = "showAll")
    @ResponseBody
    public String showAll(HttpServletRequest request, HttpServletResponse response) {
       return "hello world!";
    }
}

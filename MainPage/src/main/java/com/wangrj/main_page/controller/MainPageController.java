package com.wangrj.main_page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * by wangrongjun on 2018/5/29.
 */
@Controller
public class MainPageController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello123";
    }

    @GetMapping("/saveSession")
    @ResponseBody
    public String saveSession(HttpServletRequest request, String value) {
        request.getSession().setAttribute("testParam", value);
        return "succeed save value: " + value;
    }

    @GetMapping("/loadSession")
    @ResponseBody
    public String loadSession(HttpServletRequest request) {
        Object testParam = request.getSession().getAttribute("testParam");
        return "testParam: " + testParam;
    }

}

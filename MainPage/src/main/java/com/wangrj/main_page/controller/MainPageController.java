package com.wangrj.main_page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * by wangrongjun on 2018/5/29.
 */
@Controller
public class MainPageController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

}

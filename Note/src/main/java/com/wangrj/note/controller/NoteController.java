package com.wangrj.note.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * by wangrongjun on 2018/5/31.
 */
@Controller
public class NoteController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

}

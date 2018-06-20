package com.wangrj.note.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Controller
public class TestController {

    @GetMapping("/testParam")
    @ResponseBody
    public String test(@Min(value = 2) int age, @NotNull @Pattern(regexp = "man|woman") String sex, @RequestParam String abc) {
        return "hello, age=" + age;
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/testDate")
    @ResponseBody
    public String testDate(Date date) {
        return "hello, date=" + date;
    }

}

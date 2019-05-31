package com.atguigu.crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: Albert
 * @Date: 2018/9/18 21:34
 * @Description:
 */
@Controller
public class Test {

    @RequestMapping("/testjs")
    public String fun(){
        return "testjs";
    }
}

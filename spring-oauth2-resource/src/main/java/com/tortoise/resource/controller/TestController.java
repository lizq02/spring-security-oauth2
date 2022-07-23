package com.tortoise.resource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: lizhongqing
 * @create: 2022/07/19
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/getUser")
    public String getUser() {
        return "success";
    }
}
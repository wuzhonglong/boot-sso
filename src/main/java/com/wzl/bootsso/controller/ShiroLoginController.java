package com.wzl.bootsso.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author W.sir
 * @version 1.0
 * @description 登陆
 * @className ShiroLoginController
 * @date 2020/7/23 15:52
 **/
@RestController
@RequestMapping(value = "/shiro")
public class ShiroLoginController {
    @GetMapping("/login")
    public void login(){

    }
}

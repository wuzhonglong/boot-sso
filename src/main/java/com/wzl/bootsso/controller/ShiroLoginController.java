package com.wzl.bootsso.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public void login(@RequestParam String username, @RequestParam String password) {
        // 用户名查询数据库 是否存在
        // 存在 验证密码
        // 通过 生成token
    }

    @GetMapping("/401")
    public void unauthorized(){
        System.out.println("未授权");
    }
}

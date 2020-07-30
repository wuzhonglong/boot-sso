package com.wzl.bootsso.controller;

import com.wzl.bootsso.common.CommonResult;
import com.wzl.bootsso.entity.User;
import com.wzl.bootsso.service.UserService;
import com.wzl.bootsso.utils.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author W.sir
 * @version 1.0
 * @description 登陆
 * @className ShiroLoginController
 * @date 2020/7/23 15:52
 **/
@Slf4j
@RestController
@RequestMapping(value = "/shiro")
public class ShiroLoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public void login(@RequestParam String username, @RequestParam String password) {
        try {
            // 用户名查询数据库 是否存在
            List<User> userList = userService.getUserInfo(username);
            if (CollectionUtils.isEmpty(userList)) {
                log.info("用户不存在");
            }
            if (userList.get(0).getState() == 1) {
                log.info("登陆失败，该用户已锁定");
            }
            // 假设注册的时候 数据库村的密码是 md5加密过的密码
            String md5Password = Md5Util.getEncryptedPwd(password);
            if (StringUtils.equals(userList.get(0).getPassword(), md5Password)) {
                // 通过 生成token

            } else {
                log.info("密码错误");
            }
        } catch (Exception e) {
            log.info("未知错误");
        }
    }

    @GetMapping("/401")
    public void unauthorized() {
        System.out.println("未授权");
    }

    /**
     * @param
     * @return void
     * @description 模拟注册接口
     * @author W.sir
     * @date 2020-07-30 11:44
     **/
    @GetMapping("/registry")
    public CommonResult<?> registry() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        userService.saveUser(user);
        return CommonResult.success();
    }
}

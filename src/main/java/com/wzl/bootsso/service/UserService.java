package com.wzl.bootsso.service;


import com.wzl.bootsso.entity.User;

import java.util.List;

/**
 * @author W.sir
 * @version 1.0
 * @description 用户接口层
 * @className UserService
 * @date 2020/7/30 10:07
 **/
public interface UserService {

    /**
     * @param username
     * @return com.wzl.bootsso.entity.User
     * @description 验证用户信息（登陆验证）
     * @author W.sir
     * @date 2020-07-30 10:37
     **/
    List<User> getUserInfo(String username);

    /**
     * @param user
     * @return void
     * @description 保存用户
     * @author W.sir
     * @date 2020-07-30 11:41
     **/
    void saveUser(User user);

}

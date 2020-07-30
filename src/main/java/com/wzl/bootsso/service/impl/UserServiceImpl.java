package com.wzl.bootsso.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzl.bootsso.entity.User;
import com.wzl.bootsso.mapper.UserMapper;
import com.wzl.bootsso.service.UserService;
import com.wzl.bootsso.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


/**
 * @author W.sir
 * @version 1.0
 * @description 用户接口实现类
 * @className UserServiceImpl
 * @date 2020/7/30 10:08
 **/
@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUserInfo(String username) {
        // 低版本的mybatisplus 好像没有wrapper
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectList(wrapper);
    }

    @Override
    public void saveUser(User user) {
        try {
            user.setPassword(Md5Util.getEncryptedPwd(user.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        userMapper.insert(user);
    }
}

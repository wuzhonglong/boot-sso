package com.wzl.bootsso.utils;


import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author W.sir
 * @version 1.0
 * @description Jwt工具类
 * @className JwtUtil
 * @date 2020/7/28 11:12
 * Jwt 包括三部分 头部 载荷 签证
 **/
public class JwtUtil {
    // 过期时间
    public static final long EXPIRE_TIME = 30 * 60 * 1000;

    // 假设注册的时候md5加密 生成token的时候再进行一次加密
    public static String createToken(String userId) {
        // 指定签名算法 HS256 是属于HMAC的一种对称算法，双方共享一个密钥
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
        // 生成token的时间
        long createTime = System.currentTimeMillis();
        // jwt构造器
        JwtBuilder builder = Jwts.builder();
        // 设置头部
        builder.setHeaderParam("type", "jwt");
        // 设置载荷
        builder.claim("userId", userId);
        // TODO 设置签证  这个盐需要注意下  现在就自定义
        String signKey = "wuzhonglong";
        builder.signWith(algorithm, signKey);
        // 设置失效时间
        builder.setExpiration(new Date(createTime + EXPIRE_TIME));
        // 创建token （内部创建流程 利用算法algorithm 和 盐signKey 将头部的base64字符串+载荷的base64字符串 加密得到的字符串就是token）
        String token = builder.compact();
        return token;
    }
}

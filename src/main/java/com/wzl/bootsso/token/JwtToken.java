package com.wzl.bootsso.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author W.sir
 * @version 1.0
 * @description 自定义token
 * @className JwtToken
 * @date 2020/7/28 10:02
 **/
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    /**
     * @description Principal翻译过来就是身份 Credentials翻译证明
     * 由于这儿是返回的自定义的token 就拿一般的UsernamePasswordToken举例
     * 他的getPrincipal()其实获取的就是用户名   getCredentials()获取的密码
     * @author W.sir
     * @date 2020-07-28 10:11
     * @param
     * @return java.lang.Object
     **/
    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}

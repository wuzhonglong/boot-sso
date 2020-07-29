package com.wzl.bootsso.realms;

import com.wzl.bootsso.token.JwtToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author W.sir
 * @version 1.0
 * @description 用户登录鉴权和获取用户授权
 * @className JwtRealm
 * @date 2020/7/28 9:42
 **/
public class JwtRealm extends AuthorizingRealm {

    /**
     * @param token
     * @return boolean
     * @description 采用Jwt 必须重写此方法 不然shiro会报错
     * @author W.sir
     * @date 2020-07-28 09:47
     **/
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * @param principalCollection
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @description 权限认证
     * @author W.sir
     * @date 2020-07-28 10:41
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * @param auth
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @description 登陆鉴权
     * @author W.sir
     * @date 2020-07-28 10:41
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        // 我们已经重写了JwtToken的getCredentials方法 返回的就是字符串
        @SuppressWarnings("unchecked")
        String token = (String) auth.getCredentials();
        if (token == null) {
            throw new AuthenticationException("token 为空");
        }
        // 检查token是否有效

        return null;
    }

    public void checkTokenIsEffective(String token) {
        // 将token解密
    }

}

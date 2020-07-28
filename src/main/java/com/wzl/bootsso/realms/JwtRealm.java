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

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}

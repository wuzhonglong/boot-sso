package com.wzl.bootsso.filter;

import com.wzl.bootsso.common.CommonConstant;
import com.wzl.bootsso.token.JwtToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author W.sir
 * @version 1.0
 * @description 自定义过滤器需要继承shiro的web过滤
 * @className JwtFilter
 * @date 2020/7/29 15:01
 **/
public class JwtFilter extends BasicHttpAuthenticationFilter {
    /**
     * @param request
     * @param response
     * @param mappedValue
     * @return boolean
     * @description 重写登陆认证
     * @author W.sir
     * @date 2020-07-29 15:42
     **/
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            // 登陆认证能够通过的必然是返回的 true  那么就运行访问
            return executeLogin(request, response);
        } catch (Exception e) {
            throw new AuthenticationException("Token失效，请重新登录", e);
        }
    }

    /**
     * @param request
     * @param response
     * @return boolean
     * @description 重写执行登陆认证 只返回true 其它都抛异常
     * @author W.sir
     * @date 2020-07-29 15:43
     **/
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String token = httpServletRequest.getHeader(CommonConstant.ACCESS_TOKEN_NAME);
        // 假设一个极端情况 token有可能时放在参数中的
        if (StringUtils.isBlank(token)) {
            token = httpServletRequest.getParameter(CommonConstant.ACCESS_TOKEN_NAME);
        }
        // 如果还是null/"" 直接抛异常
        if (StringUtils.isBlank(token)) {
            throw new AuthenticationException("Token失效，请重新登录");
        }
        // 如果存在token 交给realm验证 由于验证
        JwtToken jwtToken = new JwtToken(token);
        getSubject(request, response).login(jwtToken);
        // 由于验证的时候 如果出错 会直接抛异常 就不管了 通过了就会执行下面的代码
        System.out.println("验证通过,刷新token缓存时间");
        return true;

    }
}

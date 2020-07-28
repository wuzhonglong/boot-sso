package com.wzl.bootsso.config;

import com.wzl.bootsso.realms.JwtRealm;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.servlet.Filter;
import java.util.*;

/**
 * @author W.sir
 * @version 1.0
 * @description shiro配置
 * @className ShiroFilter
 * @date 2020/7/23 14:59
 **/
@Configuration

public class ShiroConfig {
    /**
     * @param securityManager
     * @return org.apache.shiro.spring.web.ShiroFilterFactoryBean
     * @description 配置shiro的过滤工厂
     * @author W.sir
     * @date 2020-07-23 15:24
     **/
    @Bean(name = "shiroFilter") // bean的名字  默认的是方法的名字
    public ShiroFilterFactoryBean shiroFilterFactory(SecurityManager securityManager) {
        ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
        // 注入安全管理器
        factory.setSecurityManager(securityManager);
        // 设置过滤链
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // TODO 这个有疑问 是需要写登出接口  还是shiro自动实现
        filterChainDefinitionMap.put("/logout","logout");
        // 设置不被拦截的路径 这里只放行了 登陆接口
        // TODO 为什么要区分Ajax登陆
        filterChainDefinitionMap.put("/login","anon");
        filterChainDefinitionMap.put("/ajaxLogin","anon");
        // 这里还可以通过配置文件配置 不被拦截的路径 具体可以参考wlyypt-sms-properties
        // 添加过滤器 一个url可以配置多个过滤器 只有全部验证通过 才会视为通过
        // TODO 可以尝试设置两个 然后看看效果
        Map<String, Filter> filterMap = new HashMap<>();

        factory.setFilters(filterMap);
        factory.setFilterChainDefinitionMap(filterChainDefinitionMap);
        // 未登陆时需要跳转的页面
        factory.setLoginUrl("");
        // 未授权界面
        factory.setUnauthorizedUrl("");
        // 登陆成功跳转的页面  可以不在这儿实现 登陆代码里面也可以直接实现跳转功能
        factory.setSuccessUrl("");
        return factory;
    }

    /**
     * @description 自定义的认证器列表
     * 可以配置多个认证器 会按照相应的顺序和策略进行认证 这里就用一个
     * @author W.sir
     * @date 2020-07-28 09:39
     * @param
     * @return java.util.List<org.apache.shiro.realm.Realm>
     **/
    private List<Realm> realmList(){
        List<Realm> realmList = new ArrayList<>();
        realmList.add(new JwtRealm());
        return realmList;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 注册登陆验证 默认的是使用AtLeastOneSuccessfulStrategy策略
        // 只要有一个Realm验证成功即可,返回所有 Realm 身份验证成功的认证信息；
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setRealms(realmList());
        return null;
    }



}

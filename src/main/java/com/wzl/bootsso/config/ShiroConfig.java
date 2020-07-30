package com.wzl.bootsso.config;

import com.wzl.bootsso.filter.JwtFilter;
import com.wzl.bootsso.properties.RedisConfigProperties;
import com.wzl.bootsso.realms.JwtRealm;
import com.wzl.bootsso.utils.JwtUtil;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

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

    @Autowired
    private RedisConfigProperties redisConfigProperties;

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
        filterChainDefinitionMap.put("/logout", "logout");
        // 设置不被拦截的路径 这里只放行了 登陆接口
        // TODO 为什么要区分Ajax登陆
        filterChainDefinitionMap.put("/shiro/login", "anon");
        filterChainDefinitionMap.put("/shiro/ajaxLogin", "anon");
        // 这里还可以通过配置文件配置 不被拦截的路径 具体可以参考wlyypt-sms-properties
        // 添加过滤器 一个url可以配置多个过滤器 只有全部验证通过 才会视为通过
        // TODO 可以尝试设置两个 然后看看效果
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", new JwtFilter());
        factory.setFilters(filterMap);

        filterChainDefinitionMap.put("/**", "jwt");
        factory.setFilterChainDefinitionMap(filterChainDefinitionMap);
        // 检测到未登陆时需要跳转的页面
        factory.setLoginUrl("/shiro/login");
        // 未授权界面
        factory.setUnauthorizedUrl("/shiro/401");
        // 登陆成功跳转的页面  可以不在这儿实现 登陆代码里面也可以直接实现跳转功能
        //factory.setSuccessUrl("/shiro/index");
        return factory;
    }

    /**
     * @param
     * @return java.util.List<org.apache.shiro.realm.Realm>
     * @description 自定义的认证器列表
     * 可以配置多个认证器 会按照相应的顺序和策略进行认证 这里就用一个
     * @author W.sir
     * @date 2020-07-28 09:39
     **/
    private List<Realm> realmList() {
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
        // 注册权限验证
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        authorizer.setRealms(realmList());

        securityManager.setAuthenticator(authenticator);
        securityManager.setAuthorizer(authorizer);

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-
         * StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        // 自定义缓存实现  使用redis
        securityManager.setCacheManager(redisCacheManager());
        return securityManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        // redis中针对不同用户缓存(此处的id需要对应user实体中的id字段,用于唯一标识)
        redisCacheManager.setPrincipalIdFieldName("id");
        // 用户权限信息缓存时间
        redisCacheManager.setExpire((int) JwtUtil.EXPIRE_TIME);
        return redisCacheManager;
    }

    /**
     * DependsOn 该注解可以控制spring bean 加载顺序
     * 由于这里需要依赖redisConfigProperties Bean 所以需要redisConfigProperties优先加载
     *
     * @return
     */
    @Bean
    @DependsOn("redisConfigProperties")
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisConfigProperties.getHost());
        redisManager.setPort(redisConfigProperties.getPort());
        redisManager.setDatabase(redisConfigProperties.getDatabase());
        redisManager.setPassword(redisConfigProperties.getPassword());
        redisManager.setTimeout(redisConfigProperties.getTimeout());
        return redisManager;
    }

    /**
     * @param
     * @return org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
     * @description 开启shiro的自动代理注解支持（需要借助AOP）
     * (如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @author W.sir
     * @date 2020-07-29 14:37
     **/
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        autoProxyCreator.setProxyTargetClass(true);
        return autoProxyCreator;
    }

    /**
     * @param securityManager
     * @return org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     * @description 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
     * @author W.sir
     * @date 2020-07-29 14:42
     **/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor sourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        sourceAdvisor.setSecurityManager(securityManager);
        return sourceAdvisor;
    }

}

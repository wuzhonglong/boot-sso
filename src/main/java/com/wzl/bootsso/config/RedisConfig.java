package com.wzl.bootsso.config;

import com.wzl.bootsso.properties.RedisConfigProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author W.sir
 * @version 1.0
 * @description TODO
 * @className RedisConfig
 * @date 2020/7/29 11:55
 **/
@Configuration
@PropertySource(value = "classpath:redisconfig.properties",encoding = "UTF-8")
public class RedisConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public RedisConfigProperties redisConfigProperties() {
        return new RedisConfigProperties();
    }
}

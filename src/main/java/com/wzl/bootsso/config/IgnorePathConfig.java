package com.wzl.bootsso.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author W.sir
 * @version 1.0
 * @description 不需要鉴权的路径
 * @className IgnorePath
 * @date 2020/7/24 11:34
 **/
@Configuration
@PropertySource(value = "classpath:ignore.properties", encoding = "UTF-8")
public class IgnorePathConfig {
    @Bean(name = "path")
    @ConfigurationProperties(prefix = "ignore")
    public List<String> ignorePath() {
        List<String> path = new ArrayList<>();
        return path;
    }
}

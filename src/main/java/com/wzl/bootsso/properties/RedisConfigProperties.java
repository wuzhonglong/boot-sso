package com.wzl.bootsso.properties;

import lombok.Getter;
import lombok.Setter;


/**
 * @author W.sir
 * @version 1.0
 * @description
 * @className RedisConfigEntity
 * @date 2020/7/29 11:51
 **/
@Setter
@Getter
public class RedisConfigProperties {

    private String host;

    private int port;

    private String password;

    private int database;

    private int timeout;
}

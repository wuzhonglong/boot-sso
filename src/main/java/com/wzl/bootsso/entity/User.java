package com.wzl.bootsso.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author W.sir
 * @version 1.0
 * @description TODO
 * @className User
 * @date 2020/7/30 10:19
 **/
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String username;
    private String password;
    /**
     * 用户状态 1 锁定 0 正常
     **/
    private int state;
}

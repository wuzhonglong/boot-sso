package com.wzl.bootsso.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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


/**
 * 关于使用mybatis_plus 说明下
 **/


@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
// 如果表名和实体类名一致（忽略大小写） 那么可以不用 下面这个注解
@TableName("sso_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    // mybatis_plus 底层默认自增ID（所以不要自己设置主键值）@TableId(value = "id",type = IdType.AUTO)
    // 不过 默认生成的是时间戳 可以换成UUID @TableId(value = "id",type = IdType.ASSIGN_UUID)
    // 如果不想使用自动生成的  那么可以设置 @TableId(value = "id",type = IdType.INPUT) 这就需要自己设置id
    // 具体看下IdType源码中枚举说明  根据业务需求合理选择
    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    // 同样的道理 如果字段名和属性名一致（符合驼峰对应）可以不使用@TableField(value = "name")
    private String username;
    private String password;
    /**
     * 用户状态 1 锁定 0 正常
     **/
    private int state;
}

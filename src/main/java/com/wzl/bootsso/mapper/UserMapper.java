package com.wzl.bootsso.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzl.bootsso.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author W.sir
 * @version 1.0
 * @description TODO
 * @className UserMapper
 * @date 2020/7/30 10:33
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {
}

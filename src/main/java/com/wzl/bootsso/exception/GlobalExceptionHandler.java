package com.wzl.bootsso.exception;

import com.wzl.bootsso.common.CommonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author W.sir
 * @version 1.0
 * @description TODO
 * @className GlobalExceptionHandler
 * @date 2020/7/30 14:14
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public CommonResult<?> exceptionHandl(Exception e) {
        return CommonResult.fail().msg(e.getMessage());
    }
}

package com.wzl.bootsso.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author W.sir
 * @version 1.0
 * @description Base64工具类
 * @className Base64Util
 * @date 2020/7/28 16:57
 **/
public class Base64Util {

    /**
     * @param sourcesStr
     * @return java.lang.String
     * @description base64 编码
     * @author W.sir
     * @date 2020-07-28 17:20
     **/
    public static String encryptBase64(String sourcesStr) throws Exception {
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            return encoder.encode(sourcesStr.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new Exception("base64加密失败", e);
        }
    }

    /**
     * @param sourcesStr
     * @return java.lang.String
     * @description base64 解码
     * @author W.sir
     * @date 2020-07-28 17:20
     **/
    public static String dncryptBase64(String sourcesStr) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            return new String(decoder.decodeBuffer(sourcesStr), "UTF-8");
        } catch (IOException e) {
            throw new Exception("base64解密失败", e);
        }
    }
}

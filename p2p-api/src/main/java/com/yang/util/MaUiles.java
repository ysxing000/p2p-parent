package com.yang.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5加密工具类
 */
public class MaUiles {

    public static String md(String password){
        String s = DigestUtils.md5Hex(password);
        return s;
    }

}

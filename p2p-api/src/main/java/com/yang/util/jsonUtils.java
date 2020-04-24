package com.yang.util;


import com.alibaba.fastjson.JSON;
import com.yang.entity.Account;

import javax.xml.transform.Source;
import java.math.BigDecimal;

/**
 * json工具类
 */
public class jsonUtils {


    public static <E> String tojson(E obj){
        String s = JSON.toJSONString(obj);

        return s;
    }

    public static <E> E toString(String obj,Class<E> tClass){
        E e = JSON.parseObject(obj, tClass);
        return e;
    }
    public static void main(String[] args) {
        Account account=Account.empty(33L);
        account.setUsableAmount(new BigDecimal(2000));

        String tojson = jsonUtils.tojson(account);

        System.out.println(tojson);

        Account account1 = jsonUtils.toString(tojson, Account.class);
        System.out.println(account1);


    }




}

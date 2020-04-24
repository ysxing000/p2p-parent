package com.yang.vo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 订单生成器
 */
public class ordersheng {




    public static void main(String[] args) {
        Random random = new Random();
        SimpleDateFormat allTime = new SimpleDateFormat("YYYYMMddHHmmSSS");
        String subjectno = allTime.format(new Date()) + random.nextInt(10);

        System.out.println(  subjectno + random.nextInt(10));

    }






}

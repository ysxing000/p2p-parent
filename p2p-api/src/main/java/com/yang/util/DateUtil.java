package com.yang.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {


    /**
     * 给指定的日期向后推迟指定的月数
     * @param date
     * @param
     * @return
     */
    public static Date addmonth(Date date,int monthes){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,monthes );
        return calendar.getTime();

    }


    /**
     * 返回该日期最后一秒对象
     * @param date
     * @return
     */
    public static Date endOfDay(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        calendar.add(Calendar.SECOND, -1);
        return calendar.getTime();

    }


    public static void main(String[] args) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

        Date parse = simpleDateFormat.parse("2019-09-01");

        Date date = DateUtil.addmonth(parse,1);

        SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = s.format(date);

        System.out.println(format);

    }




}

package com.yang.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean exists(String keys){

        Boolean aBoolean = stringRedisTemplate.hasKey(keys);
        return aBoolean;

    }


    //存进redis
    public <T> void set(String key,T value){
        String json=toJson(value);
        stringRedisTemplate.opsForValue().set(key, json);
    }

    //获取
    public <T> T get(String key,Class<T> tClass){

        String s1 = stringRedisTemplate.opsForValue().get(key);
        if(s1==null){

            return null;
        }
     T result=toString(s1,tClass);
        return result;

    }


    public <T> void setex(String key,T value,int seconds){
        String json=toJson(value);
        stringRedisTemplate.opsForValue().set(key, json, seconds, TimeUnit.SECONDS);
    }

    private <T> T toString(String value, Class<T> tClass) {
        T result=null;


        if(tClass==String.class){
            result= (T) value;
        }else if(tClass==int.class|| tClass==Integer.class){
            result= (T) Integer.valueOf(value);

        }else if(tClass==Long.class|| tClass==long.class){
            result= (T) Integer.valueOf(value);

        }else {
            result=jsonUtils.toString(value, tClass);
        }

return result;

    }


    private <T> String toJson(T value) {
        String result=null;
        Class<?> aClass = value.getClass();
        if(aClass==String.class){
            result= (String) value;
        }else if(aClass==int.class|| aClass==Integer.class||aClass==Long.class|| aClass==long.class){
            result=String.valueOf(value);

        }else {
            result=jsonUtils.tojson(value);
        }

        return result;
    }


}

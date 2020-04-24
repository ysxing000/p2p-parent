package com.yang.mapper;

import com.yang.entity.LoginInfo;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface LoginMapper {


    //注册账号
   public int registerUser(LoginInfo loginInfo);

   public LoginInfo loginByUsername(String username);



}

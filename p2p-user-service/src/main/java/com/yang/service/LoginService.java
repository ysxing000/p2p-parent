package com.yang.service;

import com.yang.entity.LoginInfo;

import javax.servlet.http.HttpServletResponse;

public interface LoginService {

    //注册账号
    boolean registerUser(String userName,String password);


    public LoginInfo selectByUsername(String username);


    public boolean login(String username, String password, HttpServletResponse response);


}

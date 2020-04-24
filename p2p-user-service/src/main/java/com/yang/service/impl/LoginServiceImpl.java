package com.yang.service.impl;

import com.netflix.client.http.HttpResponse;
import com.yang.entity.Account;
import com.yang.entity.LoginInfo;
import com.yang.entity.UserInfo;
import com.yang.exception.GlobalException;
import com.yang.mapper.LoginMapper;
import com.yang.mapper.UserInfoMapper;
import com.yang.service.AccountServer;
import com.yang.service.LoginService;
import com.yang.util.MaUiles;
import com.yang.util.RedisUtils;
import com.yang.vo.CodeMsg;
import com.yang.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.provider.MD5;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
   private LoginMapper loginMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private AccountServer accountServer;

    @Autowired
    private RedisUtils redisUtils;

    @Transactional
    @Override
    public boolean registerUser(String userName, String password) {

        LoginInfo loginInfo=new LoginInfo();
        loginInfo.setUsername(userName);
        loginInfo.setPassword(MaUiles.md(password));//对密码进行加密
        loginMapper.registerUser(loginInfo);
        Long id = loginInfo.getId();//获取添加的对象的id

        UserInfo userInfo=UserInfo.empty(id);
        int i = userInfoMapper.insertUserinfo(userInfo);
     if(i>0){
            ResultVo<Account> account = accountServer.createAccount(id);
            return account.getCode()==CodeMsg.success.getCode();
        }

        return i>0;
    }

    @Override
    public LoginInfo selectByUsername(String username) {
        return null;
    }


    //登录
    @Override
    public boolean login(String username, String password, HttpServletResponse response) {

        LoginInfo logininfo = loginMapper.loginByUsername(username);
        if(logininfo==null){
            throw new GlobalException(CodeMsg.accountexists);
        }else if(!logininfo.getPassword().equals(MaUiles.md(password))){
            throw new GlobalException(CodeMsg.password_fall);
        }
        //redis 实现分布式session

        // 生成随机字符串 最为token
        String token = UUID.randomUUID().toString().replace("-", "");
        //在客户端存储（cookie）token
        Cookie cookie=new Cookie("USER-TOKEN", token);
        cookie.setMaxAge(60*30);
        cookie.setDomain("p2p.com");  
        cookie.setPath("/");

        response.addCookie(cookie);
        //根据token从redis 中查找用户系信息
        redisUtils.setex(token, logininfo, 60*30);
        return true;
    }


}

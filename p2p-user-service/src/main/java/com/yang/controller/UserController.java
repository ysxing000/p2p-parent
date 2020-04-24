package com.yang.controller;

import com.yang.entity.LoginInfo;
import com.yang.entity.UserInfo;
import com.yang.service.LoginService;
import com.yang.util.RedisUtils;
import com.yang.vo.CodeMsg;
import com.yang.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestControlle
public class UserController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping("regis")
    public Map<String,Object> registeUser(String username,String password){
        System.out.println(username);
        Map<String,Object> map=new HashMap<>();
        boolean b = loginService.registerUser(username, password);
         map.put("statue", 200);
         map.put("magges", "注册成功");
         map.put("rsques", b);
         map.put("error","失败");
         return map;
    }

    @RequestMapping("login")
    public ResultVo<Boolean> login(String username, String password, HttpServletResponse response){

        boolean login = loginService.login(username, password,response);

        return ResultVo.success(true);


    }

    //判断用户是否登录

    @RequestMapping("checkLogin")
    @ResponseBody
    public ResultVo<LoginInfo> checkLogin(@CookieValue(name = "USER-TOKEN")String token){
        if(token==null||"".equals(token)){

            return ResultVo.error(CodeMsg.user_sesson_expTRE);

        }
        LoginInfo loginInfo = redisUtils.get(token, LoginInfo.class);
        if(loginInfo!=null){
            return ResultVo.success(loginInfo);

        }else {

            return ResultVo.error(CodeMsg.user_sesson_expTRE);

        }
    }

   /* //通过id进行查找
    public ResultVo<UserInfo> selectUserInfo(){

        return ResultVo.error(CodeMsg.accountexists);

    }
*/









}

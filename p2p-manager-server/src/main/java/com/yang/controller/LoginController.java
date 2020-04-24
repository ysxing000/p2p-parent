package com.yang.controller;

import com.yang.entity.LoginInfo;
import com.yang.vo.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginController {

    @RequestMapping("login")
    @ResponseBody
    public ResultVo<Boolean> login(String username,String password){


        return ResultVo.success(true);
    }

    @RequestMapping("/index")
    public String index(Model model){
        LoginInfo loginInfo=LoginInfo.empty(5L);
        loginInfo.setUsername("admin");
        model.addAttribute("logininfo", loginInfo);
        return "main";
    }


}

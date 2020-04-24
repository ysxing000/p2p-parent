package com.yang.controller;


import com.yang.entity.UserInfo;
import com.yang.service.UserServier;
import com.yang.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonalController {

    @Autowired
    private UserServier userServier;

    @RequestMapping("user")
    @ResponseBody
    public ResultVo<UserInfo> select(@RequestParam("id")Long id){
        UserInfo userInfo = userServier.selectUserInfoById(id);
        return ResultVo.success(userInfo);

    }






}

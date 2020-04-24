package com.yang.service;

import com.yang.entity.Account;
import com.yang.vo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "account-server")
public interface AccountServer {

    @RequestMapping("/createAccount/{id}")
    public ResultVo<Account> createAccount(@PathVariable("id") Long id);

}






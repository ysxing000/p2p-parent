package com.yang.client;

import com.yang.vo.ResultVo;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "account-server")
public interface AccountClient {


    @RequestMapping("/tou")
    public ResultVo touAccount(@RequestParam("id") Long id, @RequestParam("money") BigDecimal money);



}

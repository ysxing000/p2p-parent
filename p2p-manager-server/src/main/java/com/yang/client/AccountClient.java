package com.yang.client;

import com.yang.entity.Account;
import com.yang.vo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.math.BigDecimal;

@FeignClient(name ="account-server" )
public interface AccountClient {

    //满标二审之后借款人获得放款

    @RequestMapping("/fangAccount")
    public int fangAccount(@RequestParam("bidRequestAmount") BigDecimal bidRequestAmount, @RequestParam("benxi") BigDecimal benxi, @RequestParam("id") Long id);

    @RequestMapping("/accountById")
    public Account selectAccountByid(@RequestParam("id")Long id);

    //放款后投资人账户冻结金额 待收利息 代收本金改变
    @RequestMapping("updateTouziRen")
    @ResponseBody
    public int updateTouziRen(@RequestParam("availableAmount") BigDecimal availableAmount, @RequestParam("multiply") BigDecimal multiply, @RequestParam("id") Long id );




}

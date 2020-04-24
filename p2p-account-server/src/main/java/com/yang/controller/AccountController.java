package com.yang.controller;

import com.yang.entity.Account;
import com.yang.service.AccountService;
import com.yang.vo.CodeMsg;
import com.yang.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;


    @RequestMapping("/createAccount/{id}")
    public ResultVo<Account> createAccount(@PathVariable("id") Long id){
        Account account=Account.empty(id);
      try {
          accountService.createAccount(account);
      }catch (Exception e){
          ResultVo.error(CodeMsg.account_create_fall);

      }
      return ResultVo.success(account);
    }


    @RequestMapping("/detail")
    public ResultVo<Account> selectAccount(@RequestParam("id")Long id){
        System.out.println(id);
        Account account = accountService.selectAccountById(id);
        if(account==null){
            return ResultVo.error(CodeMsg.account_not_exists);

        }
        return ResultVo.success(account);
    }




    @RequestMapping("/tou")
    @ResponseBody
    public ResultVo touAccount(@RequestParam("id")Long id, @RequestParam("money")BigDecimal money){

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+id);
        int i = accountService.updateAccount(id, money);
        if(i>0){
            return ResultVo.error(CodeMsg.account_not_exists);
        }
        return ResultVo.success(CodeMsg.success);
    }


    //满标二审之后借款人获得放款

    @RequestMapping("/fangAccount")
    @ResponseBody
    public int fangAccount(@RequestParam("bidRequestAmount") BigDecimal bidRequestAmount,@RequestParam("benxi") BigDecimal benxi,@RequestParam("id") Long id){
        int i = accountService.updateRequestAccount(bidRequestAmount, benxi, id);
       return i;
    }

    @RequestMapping("/accountById")
    public Account selectAccountByid(@RequestParam("id")Long id){
        Account account = accountService.selectAccountById(id);
        return account;

    }

    //放款后投资人账户冻结金额 待收利息 代收本金改变
    @RequestMapping("updateTouziRen")
    @ResponseBody
    public int updateTouziRen(@RequestParam("availableAmount") BigDecimal availableAmount, @RequestParam("multiply") BigDecimal multiply, @RequestParam("id") Long id ){

        int updatetouziren = accountService.updatetouziren(availableAmount, multiply, id);
        return updatetouziren;
    }
















}

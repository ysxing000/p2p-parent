package com.yang.service;

import com.yang.entity.Account;

import java.math.BigDecimal;

public interface AccountService {


    public void createAccount(Account account);

    public Account selectAccountById(Long id);

    public int updateAccount( Long id,BigDecimal money);

    //满标二审之后借款人获得放款
    public int updateRequestAccount(BigDecimal bidRequestAmount,BigDecimal benxi,Long id);

    //放款后投资人账户冻结金额 待收利息 代收本金改变
    public int updatetouziren(BigDecimal availableAmount,BigDecimal multiply,Long id);


}

package com.yang.service.impl;

import com.yang.dao.AccountRepostary;
import com.yang.entity.Account;
import com.yang.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepostary accoutRepostary;

    @Override
    public void createAccount(Account account) {
        Account save = accoutRepostary.save(account);

    }

    @Override
    public Account selectAccountById(Long id) {
        Account account = accoutRepostary.findById(id).get();
        System.out.println(account);

        return account;
    }

    @Override
    public int updateAccount(Long id,BigDecimal money) {
        int i = accoutRepostary.updateAccount(id,money);
        return i;
    }

    @Override
    public int updateRequestAccount(BigDecimal bidRequestAmount, BigDecimal benxi, Long id) {
        int i = accoutRepostary.updateRequestAccount(bidRequestAmount, benxi, id);

        return i;
    }

    //放款后投资人账户冻结金额 待收利息 代收本金改变
    @Override
    public int updatetouziren(BigDecimal availableAmount, BigDecimal multiply, Long id) {
        int updatetouziren = accoutRepostary.updatetouziren(availableAmount, multiply, id);

        return updatetouziren;
    }


}

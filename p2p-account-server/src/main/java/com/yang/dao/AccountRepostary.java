package com.yang.dao;

import com.yang.entity.Account;
import com.yang.entity.BidRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


public interface AccountRepostary extends JpaRepository<Account,Long> {

    @Query(value = "update account set usableAmount=usableAmount-?2,freezedAmount=freezedAmount+?2 where id=?1",nativeQuery = true)
    @Modifying
    @Transactional
    public int updateAccount(@Param("id") Long id,@Param("money") BigDecimal money);


    @Query(value = "update account set usableAmount=usableAmount+?1,unReturnAmount=unReturnAmount+?2,remainBorrowLimit=remainBorrowLimit-?1 where id=?3",nativeQuery = true)
    @Modifying
    @Transactional
    public int updateRequestAccount(BigDecimal bidRequestAmount,BigDecimal benxi,Long id);

//放款后投资人账户冻结金额 待收利息 代收本金改变
    @Modifying
    @Transactional
    @Query(value ="update account set freezedAmount=freezedAmount-?1,unReceivePrincipal=unReceivePrincipal+?1,unReceiveInterest=unReceiveInterest+?2 where id=?3",nativeQuery = true)
    public int updatetouziren(BigDecimal availableAmount,BigDecimal multiply,Long id);

    //执行还款
   /* @Transactional
    @Modifying
    @Query(value = "")
    public int removeAccountByjie(BigDecimal bidRequestAmount,BigDecimal benxi,Long id);*/










}

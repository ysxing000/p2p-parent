package com.yang.entity;

import com.yang.util.BidConst;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "account")
public class Account {

    @Id
    private Long id;

    private String tradePassword;//交易密码


    private BigDecimal usableAmount= BidConst.ZERO;//可用金额

    private BigDecimal freezedAmount=BigDecimal.ZERO;//冻结金额

    private BigDecimal borrowLimitAmount;//征信额度

    private int version;

    private BigDecimal unReceiveInterest=BigDecimal.ZERO;//待收利息

    private BigDecimal unReceivePrincipal=BigDecimal.ZERO;//待收本金

    private BigDecimal unReturnAmount=BigDecimal.ZERO;//待还本息

   private BigDecimal remainBorrowLimit=BigDecimal.ZERO;//可用征信额度


    //计算账户金额
    public BigDecimal getTotalAmount(){

        return usableAmount.add(freezedAmount).add(unReceiveInterest).add(unReceivePrincipal);

    }


    //对始化一个账号进行初
    public static Account empty(Long id){
        Account account=new Account();
        account.setId(id);
        //征信额度
        account.setBorrowLimitAmount(BidConst.DEFALUT_BORROWLLMITAMOUNT);
        account.setRemainBorrowLimit(BidConst.DEFALUT_BORROWLLMITAMOUNT);
        account.setVersion(0);
        return account;
    }













}

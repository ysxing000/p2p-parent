package com.yang.vo;

import com.yang.entity.Accountflow;
import com.yang.entity.Bid;
import com.yang.entity.BidRequest;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BidRequestAccount {

   private BigDecimal money;//金额
   private Long bidUserId;//用户的id
   private Long bidRequestId;//标的的id
   private String note;//流动说明
   private int actualRate;//年化率
    private String title;//借款标标题
   private BigDecimal balance;//动账可用
   private Long account_id;//账户表
  private BigDecimal freezed;//冻结资金





}

package com.yang.util;

import java.math.BigDecimal;

public interface BidConst {

   //金额常量
   BigDecimal ZERO=BigDecimal.ZERO;

   BigDecimal DEFALUT_BORROWLLMITAMOUNT=BigDecimal.valueOf(2000);
   // 系统默认的最小投标金额
   public static final BigDecimal SMALLEST_BID_AMOUNT = BigDecimal.valueOf(100);

   //还款方式
   public static final  int RETURN_TYPE_MONTH_INTERST_PRINCIPAL = 0;//等额本息
   public static final  int RETURN_TYPE_MONTH_PRINCIPAL = 1;//等额本金
   public static final  int RETURN_TYPE_MONTH_INTERST = 2;//按月到期

   //标的类型
   public static final int BIDREQUEST_TYPE_NORMAL = 0;//信用贷
   public static final int BIDREQUEST_TYPE_CAR = 1;//车易贷
   public static final int BIDREQUEST_TYPE_HOUSE = 2;//房易贷


   //精度
   public final static int scale_CACULATE=8;//计算精度
   public final static int scale_Store=4;//存储精度
   public final static int scale_DISPLAY=2;//显示精度



   public final static int BIDREQUEST_STATE_PUBLISH_PENDING=0;//待发布=待发标前审核
   public final static int BIDREQUEST_STATE_BIDDING=1;//招标中=发标前审核通过
   public final static int BIDREQUEST_STATE_UNDO=2;//已撤销（借款方主动发起的）
   public final static int BIDREQUEST_STATE_BIDDING_OVERDUE=3;//流标（招标期限内没有满标）
   public final static int BIDREQUEST_STATE_APPROVE_PENDING_1=4;//满标1审（一旦在招标期限内满标，则自动进入待满标一审）
   public final static int BIDREQUEST_STATE_APPROVE_PENDING_2=5;//满标2审（一旦满标1审通过，则自动进入待满标二审）
   public final static int BIDREQUEST_STATE_REJECTED=6;//一审或者二审不通过
   public final static int BIDREQUEST_STATE_PAYING_BACK=7;//通过二审（会放款）,还款中
   public final static int BIDREQUEST_STATE_COMPLETE_PAY_BACK=8;//已还清
   public final static int BIDREQUEST_STATE_PAY_BACK_OVERDUE=9;//逾期
   public final static int BIDREQUEST_STATE_PUBLISH_REFUSE=10;//初审拒绝(发标前审核不通过)

//审核状态
   public static final int STATE_APPLY = 0;//申请状态
   public static final int STATE_PASS = 1;//审核通过
   public static final int STATE_REJECT = 2;//审核拒绝

   public static final int STATE_PASS1 = 5;//审核一审通过
   public static final int STATE_REJECT1 = 6;//审核一审拒绝

   public static final int STATE_PASS2 = 7;//审核二审通过
   public static final int STATE_REJECT2 = 6;//审核二审拒绝



//招标状态 审核类型
   public static  final int PUBLISH_AUDIT = 0 ; //发标审核
   public static  final int FULL_AUDIT1 = 4 ; //满标一审
   public static  final int FULL_AUDIT2 = 5 ; //满标二审

//还款状态
   public static  final int PAYMENT_STATE_NORMAL =0 ;
   public static  final int PAYMENT_STATE_DONE =1 ;
   public static  final int PAYMENT_STATE_OVERDUE =2 ;
}
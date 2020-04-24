package com.yang.dao;

import com.yang.entity.Account;
import com.yang.entity.PaymentSchedule;
import com.yang.entity.PaymentScheduleDetail;
import com.yang.vo.BidRequestAuditQueryObject;

import java.util.List;

public interface PaymentScheduleMapper {



    //添加还款计划
    public int insertpaymentschedule(PaymentSchedule paymentSchedule);

    //添加回款计划
    public int intsertpaymentscheduledetail(PaymentScheduleDetail paymentscheduledetail);

    //分页查询还款计划表
    public List<PaymentSchedule> selectPaymentSchedulePageByUserid(Long id);






}

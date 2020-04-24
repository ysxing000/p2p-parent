package com.yang.service;

import com.yang.entity.*;
import com.yang.vo.BidRequestAccount;
import com.yang.vo.BidRequestAuditQueryObject;
import com.yang.vo.PageResult;

import java.util.List;

public interface BidService {

    public int saveBidRequest(BidRequest bidRequest);

    void doAuditForPublish(Long bidrequestid, Integer authstat, String remark, Long auditorId);


    PageResult selectBidRequestBystate(BidRequestAuditQueryObject queryObject);


    public BidRequest selectRequestById(Long bidrequestid);


    public List<Bid> selectBidByRequestId(Long id);

    //添加一天流动信息
    public int insertAccountFlow(BidRequestAccount bidRequestAccount);

    //投标是bidrequest投标时+1
    public int BidRequestbycon(BidRequestAccount bidRequestAccount);

    //增加一天当前借款人的投标
    public int insertBid(BidRequestAccount bidRequestAccount);


    //满标之后修改状态
    public int updateRequestState(Long id,Long state);


    //满标之后查找借款标
    public BidRequest findbyid(Long id);

    //投标记录表
    public List<Bid> findBid(Long id);

    //放款时插入一条动账信息
    public int insertaccountflow(Accountflow accountflow);

    //查询bid
    public Bid selectBidByid(Long id);

    //添加还款计划
    public int insertpaymentschedule(PaymentSchedule paymentSchedule);

    //添加回款计划
    public int intsertpaymentscheduledetail(PaymentScheduleDetail paymentscheduledetail);

    //分页查询还款计划
    public PageResult selectPaymentSchedulePageByUserid(BidRequestAuditQueryObject queryObject);


}

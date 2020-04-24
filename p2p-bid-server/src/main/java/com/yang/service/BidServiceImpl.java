package com.yang.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yang.dao.BidRequestAuditHistoryMapper;
import com.yang.dao.BidRequestMapper;
import com.yang.dao.PaymentScheduleMapper;
import com.yang.entity.*;
import com.yang.exception.GlobalException;
import com.yang.util.BidConst;
import com.yang.vo.BidRequestAccount;
import com.yang.vo.BidRequestAuditQueryObject;
import com.yang.vo.CodeMsg;
import com.yang.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BidServiceImpl implements BidService{

    @Autowired
    private BidRequestMapper bidRequestMapper;

    @Autowired
   private BidRequestAuditHistoryMapper bidRequestAuditHistoryMapper;

    @Autowired
    private PaymentScheduleMapper paymentScheduleMapper;


    @Override
    public int saveBidRequest(BidRequest bidRequest) {
        int i = bidRequestMapper.saveBidRequest(bidRequest);
        return i;
    }


    //分页查询
    @Override
    public PageResult selectBidRequestBystate(BidRequestAuditQueryObject queryObject) {

        PageHelper.startPage(queryObject.getCurrentPage(), queryObject.getPageSize());

        List<BidRequest> bidRequestList = bidRequestMapper.selectpage(queryObject);
        PageInfo<BidRequest> info=new PageInfo<>(bidRequestList);

        PageResult pageResult=new PageResult((int)info.getTotal(),queryObject.getPageSize(),queryObject.getCurrentPage(),info.getList());

        return pageResult;
    }

    //根据id查询对象
    @Override
    public BidRequest selectRequestById(Long bidrequestid) {
        BidRequest bidRequest = bidRequestMapper.selectRequestById(bidrequestid);
        return bidRequest;
    }

    /**
     * 根据借款标id查询
     * @param id
     * @return
     */
    @Override
    public List<Bid> selectBidByRequestId(Long id) {
        List<Bid> bids = bidRequestMapper.selectBidByRequestId(id);
        return bids;
    }

    @Override
    public int insertAccountFlow(BidRequestAccount bidRequestAccount) {
        int i = bidRequestMapper.insertAccountFlow(bidRequestAccount);
        return i;
    }

    @Override
    public int BidRequestbycon(BidRequestAccount bidRequestAccount) {
        int i = bidRequestMapper.BidRequestbycon(bidRequestAccount);
        return i;
    }

    @Override
    public int insertBid(BidRequestAccount bidRequestAccount) {
        int i = bidRequestMapper.insertBid(bidRequestAccount);
        return i;
    }

    @Override
    public int updateRequestState(Long id, Long state) {
        int i = bidRequestMapper.updateRequestState(id, state);
        return i;
    }

    @Override
    public BidRequest findbyid(Long id) {
        BidRequest findbyid = bidRequestMapper.findbyid(id);
        return findbyid;
    }

    @Override
    public List<Bid> findBid(Long id) {
        List<Bid> bids = bidRequestMapper.findBid(id);
        return bids;
    }

    //放款时插入一条动账信息
    @Override
    public int insertaccountflow(Accountflow accountflow) {
        int insertaccountflow = bidRequestMapper.insertaccountflow(accountflow);

        return insertaccountflow;
    }

    @Override
    public Bid selectBidByid(Long id) {
        Bid bid = bidRequestMapper.selectBidByid(id);
        return bid;
    }

    @Override
    public int insertpaymentschedule(PaymentSchedule paymentSchedule) {

        int insertpaymentschedule = paymentScheduleMapper.insertpaymentschedule(paymentSchedule);

        return insertpaymentschedule;
    }

    @Override
    public int intsertpaymentscheduledetail(PaymentScheduleDetail paymentscheduledetail) {
        int intsertpaymentscheduledetail = paymentScheduleMapper.intsertpaymentscheduledetail(paymentscheduledetail);
        return intsertpaymentscheduledetail;
    }

    @Override
    public PageResult selectPaymentSchedulePageByUserid(BidRequestAuditQueryObject queryObject) {
        PageHelper.startPage(queryObject.getCurrentPage(), queryObject.getPageSize());

        System.out.println(queryObject.getCreatorId());
        List<PaymentSchedule> paymentSchedules = paymentScheduleMapper.selectPaymentSchedulePageByUserid(queryObject.getCreatorId());
        PageInfo<PaymentSchedule> info=new PageInfo<>(paymentSchedules);


        PageResult pageResult=new PageResult((int)info.getTotal(),queryObject.getCurrentPage(),queryObject.getPageSize(),info.getList() );

        return pageResult;
    }


    //发表前审核 满标审核
    @Override
    public void doAuditForPublish(Long bidrequestid, Integer authstat, String remark, Long auditorId) {

       BidRequest bidRequest= bidRequestMapper.selectRequestById(bidrequestid);
       int a=bidRequest.getBidRequestState();
       if(authstat== BidConst.STATE_PASS){
           //审核通过
           a=BidConst.BIDREQUEST_STATE_BIDDING;
       }else if(authstat==BidConst.STATE_REJECT){
           a=BidConst.BIDREQUEST_STATE_PUBLISH_REFUSE;
       }

       if(authstat==BidConst.STATE_PASS1){
           a=BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2;
       }else if(authstat==BidConst.STATE_REJECT1){
            a=BidConst.BIDREQUEST_STATE_REJECTED;
       }

       if(authstat==BidConst.STATE_PASS2){
           a=BidConst.BIDREQUEST_STATE_PAYING_BACK;
       }else if(authstat==BidConst.STATE_REJECT2){
          a=BidConst.BIDREQUEST_STATE_REJECTED;
       }
       bidRequest.setBidRequestState(a);
        int upda = bidRequestMapper.updateBidRequest(bidRequest);
        if(upda<=0){
            throw new GlobalException(CodeMsg.BORROW_LOCK_ERROR.fillArgs(Long.toString(bidrequestid)));

        }
        BidRequestAuditHistory bidRequestAuditHistory=new BidRequestAuditHistory();//创建对象
        bidRequestAuditHistory.setAuditType(BidConst.PUBLISH_AUDIT);//set 对象值
        bidRequestAuditHistory.setBidRequestId(bidrequestid);
        bidRequestAuditHistory.setApplier(bidRequest.getCreateUser());//申请人
        bidRequestAuditHistory.setApplyTime(bidRequest.getApplyDate());//申请时间
        bidRequestAuditHistory.setAuditor(LoginInfo.empty(auditorId));//审核人
        bidRequestAuditHistory.setAuditTime(new Date());
         bidRequestAuditHistory.setState(authstat);
         bidRequestAuditHistory.setRemark(remark);//意见

        int i = bidRequestAuditHistoryMapper.saveBidRequestAuditHistory(bidRequestAuditHistory);

        if(i<=0){
            throw new GlobalException(CodeMsg.BORROW_AUDIT_ERROT);
        }
    }








}

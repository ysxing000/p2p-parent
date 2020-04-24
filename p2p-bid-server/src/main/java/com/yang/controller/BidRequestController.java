package com.yang.controller;

import com.yang.client.AccountClient;
import com.yang.entity.*;
import com.yang.service.BidService;

import com.yang.util.InterestUtils;
import com.yang.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
public class BidRequestController {

    @Autowired
    private BidService bidService;

   @Autowired
    private AccountClient accountClient;

    @RequestMapping("saveBid")
    @ResponseBody
    public ResultVo<Boolean> saveBid(BidRequest bidRequest){

        System.out.println(bidRequest);
        //总利息t
        BigDecimal t= InterestUtils.totalInterest(bidRequest.getBidRequestAmount(), bidRequest.getReturnType(), bidRequest.getCurrentRate(),bidRequest.getMonthes2Return());
        bidRequest.setTotalRewardAmount(t);
        //借款人
       // bidRequest.setCreateUser();
        //申请时间
        bidRequest.setApplyDate(new Date());

        return ResultVo.success(true);
    }


    @RequestMapping("saveBid2")
    @ResponseBody
    public ResultVo<Boolean> saveBid2(@RequestBody BidRequest bidRequest) {

        System.out.println(bidRequest);
        //总利息t
        BigDecimal t = InterestUtils.totalInterest(bidRequest.getBidRequestAmount(), bidRequest.getReturnType(), bidRequest.getCurrentRate(), bidRequest.getMonthes2Return());
        bidRequest.setTotalRewardAmount(t);

        //申请时间
        bidRequest.setApplyDate(new Date());
        int i = bidService.saveBidRequest(bidRequest);
        if (i > 0) {
            return ResultVo.success(true);
        } else {
            return ResultVo.error(CodeMsg.Insert_BidRequest);
        }
    }

    //分页查询借款标信息
    @RequestMapping("publish_list")
    @ResponseBody
    public PageResult selectPageBidrequestBystate( @RequestBody BidRequestAuditQueryObject queryObject){
        PageResult pageResult=bidService.selectBidRequestBystate(queryObject);
        return pageResult;

    }


    //发表前审核 标的id 审核结果状态 批注 审核人id
    @ResponseBody
    @RequestMapping("/publish")
    public ResultVo<Boolean> befoerpublish( Long bidrequestid,Integer authstat,String remark,Long auditorId){

       bidService.doAuditForPublish(bidrequestid,authstat,remark,auditorId);

       return ResultVo.success(true);
    }

    @RequestMapping("ById")
    @ResponseBody
    public BidRequest selectById(Long id){
        BidRequest bidRequest = bidService.selectRequestById(id);
        return bidRequest;
    }


    @RequestMapping("BidRequestId")
    @ResponseBody
    public List<Bid> selectBidByrequestId(Long id){
        List<Bid> bids = bidService.selectBidByRequestId(id);
        return bids;

    }



   //投标
    @RequestMapping("tender")
    @ResponseBody
    public ResultVo tender(@RequestBody BidRequestAccount bidRequestAccount){

        System.out.println(bidRequestAccount+"+++++++++++++++++++++++++++++");
        bidService.insertBid(bidRequestAccount);
        bidService.insertAccountFlow(bidRequestAccount);
        bidService.BidRequestbycon(bidRequestAccount);
        accountClient.touAccount(bidRequestAccount.getAccount_id(),bidRequestAccount.getMoney());
        return ResultVo.success(CodeMsg.success);
    }
    //满标修改状态
    @ResponseBody
    @RequestMapping("updateState")
    public int updateState(Long id,Long state){
        int i = bidService.updateRequestState(id, state);
        return i;
    }

    //满标之后查找借款标


    @RequestMapping("findId")
    @ResponseBody
    public BidRequest findbyid(@RequestBody Long id){

        BidRequest findbyid = bidService.findbyid(id);
        return findbyid;

    }


    @RequestMapping("findBid")
    @ResponseBody
    public List<Bid> findBid(@RequestParam("id") Long id){
        System.out.println(id+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@222222");
        List<Bid> bids = bidService.findBid(id);
        return bids;
    }




    //放款时插入一条动账信息
    @RequestMapping("insertFlow")
    @ResponseBody
    public int insertflow(@RequestBody Accountflow accountflow){
        int insertaccountflow = bidService.insertaccountflow(accountflow);
        if(insertaccountflow>0){
            return insertaccountflow;

        }
        return 0;
    }

    @RequestMapping("BidById")
    @ResponseBody
    public Bid selectBidByid(@RequestParam("id")Long id){
        System.out.println(id+"真狗");
        Bid bid = bidService.selectBidByid(id);
        return bid;

    }

    @RequestMapping("insertp")
    @ResponseBody
    public Long insertp( @RequestBody PaymentSchedule paymentSchedule){

        int insertpaymentschedule = bidService.insertpaymentschedule(paymentSchedule);
        Long id = paymentSchedule.getId();

        return id;


    }
    //回款计划
    @RequestMapping("insertdatil")
    @ResponseBody
    public int insertDatil(@RequestBody PaymentScheduleDetail paymentScheduleDetail){
        int intsertpaymentscheduledetail = bidService.intsertpaymentscheduledetail(paymentScheduleDetail);
        return intsertpaymentscheduledetail;
    }

    //分页查询还款计划
    @RequestMapping("PaymentSchedulePage")
    @ResponseBody
    public PageResult selectPaymentSchedulePage(@RequestBody BidRequestAuditQueryObject queryObject){
        System.out.println(queryObject.getCreatorId());

        PageResult pageResult = bidService.selectPaymentSchedulePageByUserid(queryObject);

        return pageResult;
    }

    //还款
























}

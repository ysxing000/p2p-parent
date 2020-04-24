package com.yang.controller;

import com.alibaba.fastjson.JSON;
import com.yang.client.AccountClient;
import com.yang.client.BidRequestAuditHistoryClient;
import com.yang.client.BidRequestClient;
import com.yang.entity.*;
import com.yang.util.BidConst;
import com.yang.util.DateUtil;
import com.yang.util.InterestUtils;
import com.yang.vo.BidRequestAuditQueryObject;
import com.yang.vo.PageResult;
import com.yang.vo.ResultVo;
import jdk.management.resource.internal.inst.SocketOutputStreamRMHooks;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * 用于审核借款标
 */
@Controller
public class BidRequestAuditorController {

    @Autowired
    private BidRequestClient bidRequestClient;

    @Autowired
    private BidRequestAuditHistoryClient bidRequestAuditHistoryClient;

    @Autowired
    private AccountClient accountClient;



    @RequestMapping("bidrequest_publishaudit_list")
    public String index(Model model, @ModelAttribute("qo") BidRequestAuditQueryObject queryObject){
        LoginInfo loginInfo=LoginInfo.empty(5L);
        loginInfo.setUsername("admin");
        model.addAttribute("logininfo", loginInfo);
        //feign调用
        PageResult pageResult = bidRequestClient.selectPageBidrequestBystate(queryObject);
        model.addAttribute("pageResult", pageResult);
        System.out.println(  pageResult.getCurrentPage());

        return "bidrequest/publish_audit";

    }


    @RequestMapping("/manbiaoyi")
    @ResponseBody
    public ResultVo<Boolean> shenhe1( Long id, Integer state,String remark,Long auditorId){
        ResultVo<Boolean> befoerpublish = bidRequestAuditHistoryClient.befoerpublish(id, state, remark, auditorId);
        return befoerpublish;
    }


    @RequestMapping("/bidrequest_publishaudit")
    @ResponseBody
    public ResultVo<Boolean> shenhe( Long id, Integer state,String remark,Long auditorId){
//审核
        ResultVo<Boolean> befoerpublish = bidRequestAuditHistoryClient.befoerpublish(id, state, remark, auditorId);

        System.out.println(id);
        BidRequest bidRequest = bidRequestClient.findbyid(id);
        System.out.println(bidRequest);
        BigDecimal bidRequestAmount = bidRequest.getBidRequestAmount();
        Long id1 = bidRequest.getCreateUser().getId();
        BigDecimal benxi = bidRequest.getTotalRewardAmount().add(bidRequest.getBidRequestAmount());

        System.out.println(bidRequestAmount+" "+benxi+"$$"+id1);

        int i = accountClient.fangAccount(bidRequestAmount, benxi, id1);
        Account account = accountClient.selectAccountByid(bidRequest.getCreateUser().getId());
        //插入流动数据
        Accountflow accountflow=new Accountflow();
        accountflow.setAccountActionType((long)bidRequest.getBidRequestType());
        accountflow.setAmount(bidRequest.getBidRequestAmount());
        accountflow.setNote("借款人入账金额："+bidRequest.getBidRequestAmount());
       accountflow.setBalance(account.getUsableAmount().add(bidRequest.getBidRequestAmount()));
       accountflow.setFreezed(account.getFreezedAmount());
       accountflow.setAccount_id(bidRequest.getCreateUser().getId());
        System.out.println(accountflow);
        bidRequestClient.insertflow(accountflow);

        int bidCount = bidRequest.getBidCount();

        //List<Bid> bid = bidRequest.getBids();
        //获得借款标的投资人集合
        System.out.println(bidRequest.getCreateUser().getId()+"&&&&&&&&&&");
        List<Bid> bid = bidRequestClient.findBid(bidRequest.getCreateUser().getId());
        System.out.println(bid+"^^^^^^^^^^^^^^^^^^^^^^^^^^6666");
        for(int j=0;j<bidCount;j++){
            Bid bid1 = bid.get(j);
        //投标金额
            BigDecimal availableAmount = bid1.getAvailableAmount();
            //利息
            BigDecimal multiply = bid1.getActualRate().multiply(availableAmount);
            //投资人账户id
            Long id2 = bid1.getBidUser_id();

            System.out.println(id2+"annnbrnnnngnntbjeng");
            int i1 = accountClient.updateTouziRen(availableAmount, multiply, id2);

            Account acc = accountClient.selectAccountByid(bid1.getBidUser_id());

            System.out.println(acc.getId()+"wokao ");
            Bid bid2 = bidRequestClient.selectBidByid(acc.getId());

            System.out.println("bid2+::::;"+bid2);
            Accountflow a=new Accountflow();
            a.setAccountActionType((long)bidRequest.getBidRequestType());
            a.setAmount(bid2.getAvailableAmount());
            a.setNote("投标人冻结金额减少："+bid2.getAvailableAmount());
            a.setBalance(acc.getUsableAmount());
            a.setFreezed(acc.getFreezedAmount());
            a.setAccount_id(acc.getId());
            bidRequestClient.insertflow(a);
        }

        int monthes2Return = bidRequest.getMonthes2Return();
        //生成还款计划
        Date currentDate=new Date();


        for(int f=0;f<monthes2Return;f++){
            int monthIndex=f+1;//表示第几期
            //封装index期 对应生成还款计划

            Date deadLine = DateUtil.addmonth(currentDate, monthIndex);
            //该月还款的总额
            BigDecimal totalAmount = InterestUtils.caculateMonthAmount(bidRequest.getBidRequestAmount(), bidRequest.getCurrentRate(), monthes2Return, bidRequest.getReturnType(), monthIndex);

            //该月还款的利息
            BigDecimal monthinter = InterestUtils.caculateMonthInterest(bidRequest.getBidRequestAmount(), bidRequest.getCurrentRate(), monthes2Return, bidRequest.getReturnType(), monthIndex);

            PaymentSchedule ps=new PaymentSchedule(bidRequest.getId(), bidRequest.getTitle(), bidRequest.getCreateUser(), deadLine, null,
                    totalAmount, totalAmount.subtract(monthinter), monthinter,  monthIndex, BidConst.PAYMENT_STATE_NORMAL,
                    bidRequest.getBidRequestType(), bidRequest.getReturnType());
                    //将该还款计划对象添加到数据库中
            Long pd = bidRequestClient.insertp(ps);
            List<Bid> bids = bidRequestClient.findBid(bidRequest.getCreateUser().getId());
            System.out.println(ps+"nimade");
            Long psId = pd;
            System.out.println(psId+"去你妹的");
            for(int k=0;k<bids.size();k++){
                Bid bidk = bids.get(k);
                //比例
                BigDecimal proportion=bidk.getAvailableAmount().divide(bidRequest.getBidRequestAmount(), BidConst.scale_CACULATE, RoundingMode.HALF_UP);
                //生成该出借人的回款
                PaymentScheduleDetail paymentScheduleDetail=new  PaymentScheduleDetail(bidk.getAvailableAmount(), bidk.getId(),totalAmount.multiply(proportion).setScale(BidConst.scale_CACULATE) ,totalAmount.subtract(monthinter).multiply(proportion).setScale(BidConst.scale_CACULATE),
                        monthinter.multiply(proportion).setScale(BidConst.scale_CACULATE), monthIndex, deadLine, bidRequest.getId(),null, bidRequest.getReturnType(), psId, bidRequest.getCreateUser(), bidk.getBidUser_id());

                int i1 = bidRequestClient.insertDatil(paymentScheduleDetail);
            }
        }
        return befoerpublish;
    }


    @RequestMapping("bidrequest_audit1_list")
    public String audit1(Model model, @ModelAttribute BidRequestAuditQueryObject queryObject){
        LoginInfo loginInfo=LoginInfo.empty(5L);
        loginInfo.setUsername("admin");
        model.addAttribute("logininfo", loginInfo);

        int [] d = {4};
        System.out.println(d[0]);

        queryObject.setState(1);
        queryObject.setBidRequestStates(d);
        //feign调用
        PageResult pageResult = bidRequestClient.selectPageBidrequestBystate(queryObject);

        System.out.println("feign滴哦用的结果是"+pageResult);
        model.addAttribute("pageResult", pageResult);
        System.out.println(  pageResult.getCurrentPage());

        return "bidrequest/bidrequest_audit1_list";

    }


//审核2
    @RequestMapping("bidrequest_audit2_list")
    public String audit2(Model model, @ModelAttribute BidRequestAuditQueryObject queryObject){
        LoginInfo loginInfo=LoginInfo.empty(5L);
        loginInfo.setUsername("admin");
        model.addAttribute("logininfo", loginInfo);

        int [] d = {5};
        System.out.println(d[0]);

        queryObject.setState(1);
        queryObject.setBidRequestStates(d);
        //feign调用
        PageResult pageResult = bidRequestClient.selectPageBidrequestBystate(queryObject);
        model.addAttribute("pageResult", pageResult);
        System.out.println(  pageResult.getCurrentPage());

        return "bidrequest/audit2_list";

    }























}

package com.yang.client;

import com.yang.entity.*;
import com.yang.vo.BidRequestAuditQueryObject;
import com.yang.vo.PageResult;
import com.yang.vo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "bidRequest-server")
public interface BidRequestClient {

    @RequestMapping("publish_list")
    public PageResult selectPageBidrequestBystate(BidRequestAuditQueryObject queryObject);

    @RequestMapping("findId")
    public BidRequest findbyid(Long id);

    @RequestMapping("findBid")
    public List<Bid> findBid(@RequestParam("id") Long id);

    //放款时插入一条动账信息
    @RequestMapping("insertFlow")
    public int insertflow(@RequestBody Accountflow accountflow);

    //chaxunbid
    @RequestMapping("BidById")
    public Bid selectBidByid(@RequestParam("id")Long id);
//插入还款计划
    @RequestMapping("insertp")
    public Long insertp(@RequestBody PaymentSchedule paymentSchedule);
//插入回款计划
    @RequestMapping("insertdatil")
    public int insertDatil(@RequestBody PaymentScheduleDetail paymentScheduleDetail);








}

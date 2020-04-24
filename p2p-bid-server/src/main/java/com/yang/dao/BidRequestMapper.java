package com.yang.dao;

import com.yang.entity.Accountflow;
import com.yang.entity.Bid;
import com.yang.entity.BidRequest;
import com.yang.vo.BidRequestAccount;
import com.yang.vo.BidRequestAuditQueryObject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Mapper
public interface BidRequestMapper {

    public int saveBidRequest(BidRequest bidRequest);

    public int updateBidRequest(BidRequest bidRequest);

    //根据条件查询借款标
    public List<BidRequest> selectpage(@Param("qo")BidRequestAuditQueryObject queryObject);

    void doAuditForPublish(Long bidrequestid, Integer authstat, String remark, Long auditorId);


    public BidRequest selectRequestById(Long bidrequestid);


    //借款标bidrequestiD 进行查询出投标记录
    public List<Bid> selectBidByRequestId(Long id);

    //添加一天流动信息
    public int insertAccountFlow(@RequestParam("bidRequestAccount") BidRequestAccount bidRequestAccount);

    //投标是bidrequest投标时+1
    public int BidRequestbycon(@RequestParam("bidRequestAccount")BidRequestAccount bidRequestAccount);

    //增加一天当前借款人的投标
    public int insertBid(@RequestParam("bidRequestAccount")BidRequestAccount bidRequestAccount);


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









}

package com.yang.entity;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yang.util.BidConst;
import lombok.Data;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Data
public class BidRequest {

    private Long id;

    private Integer version;

    private Integer returnType= BidConst.RETURN_TYPE_MONTH_INTERST_PRINCIPAL;

    private Integer bidRequestType=BidConst.BIDREQUEST_TYPE_NORMAL;

    private Integer bidRequestState=BidConst.BIDREQUEST_STATE_PUBLISH_PENDING;//借款标的状态


    private BigDecimal bidRequestAmount=BidConst.ZERO;//借款金额
    private BigDecimal currentRate=BidConst.ZERO;//借款利率
    private BigDecimal minBidAmount=BidConst.SMALLEST_BID_AMOUNT;//最小投标金额
    private int monthes2Return=1;//借款期限
    private int bidCount=0;//已有投标数
    private BigDecimal totalRewardAmount=BidConst.ZERO;//总报酬金额  总利息
    private BigDecimal currentSum=BidConst.ZERO;//当前已经借到多少钱
    private String title="";//标的名称
    private String description="";//借款描述
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date disableDate;//招标到期时间
    private int disableDays=0;//标的有效天数
    private LoginInfo createUser;//发标人  对应于createuser_id
    private List<Bid> bids=new ArrayList<>();//这个借款已经有的标
    private Date applyDate;//申请时间
    private Date publishDate;//发布时间, 当发标前审核通过时

    //获取到进度条
    public BigDecimal getPersent(){
        return this.currentSum.divide(this.bidRequestAmount, 2, RoundingMode.HALF_UP);

    }
    public String getBidRequestStateDisplay() {
        switch (this.bidRequestState) {
            case BidConst.BIDREQUEST_STATE_PUBLISH_PENDING:
                return "待发布";
            case BidConst.BIDREQUEST_STATE_BIDDING:
                return "招标中";
            case BidConst.BIDREQUEST_STATE_UNDO:
                return "已撤销";
            case BidConst.BIDREQUEST_STATE_BIDDING_OVERDUE:
                return "流标";
            case BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1:
                return "满标一审";
            case BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2:
                return "满标二审";
            case BidConst.BIDREQUEST_STATE_REJECTED:
                return "满标审核被拒";
            case BidConst.BIDREQUEST_STATE_PAYING_BACK:
                return "还款中";
            case BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK:
                return "完成";
            case BidConst.BIDREQUEST_STATE_PAY_BACK_OVERDUE:
                return "逾期";
            case BidConst.BIDREQUEST_STATE_PUBLISH_REFUSE:
                return "发标拒绝";
            default:
                return "";
        }
    }
    public String getReturnTypeDisplay() {
        switch (returnType) {
            case BidConst.RETURN_TYPE_MONTH_INTERST_PRINCIPAL:
                return "等额本息";

            case BidConst.RETURN_TYPE_MONTH_INTERST:
                return "按月到期";

            case BidConst.RETURN_TYPE_MONTH_PRINCIPAL:
                return "等额本金";

            default:
                return "";
        }
    }


    public String getJsonString() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", id);

        try
        {
            json.put("username", this.createUser.getUsername());
        }catch(NullPointerException e)
        {
            System.out.println("发生异常的原因为 :"+e.getMessage());
        }

        json.put("title", title);
        json.put("bidRequestAmount", bidRequestAmount);
        json.put("currentRate", currentRate);
        json.put("monthes2Return", monthes2Return);
        json.put("returnType", getReturnTypeDisplay());
        json.put("totalRewardAmount", totalRewardAmount);
        return JSONObject.toJSONString(json);
    }


    //获取剩余还未投满的金额 (+:add  -:subtract * :multiply  / :divide)
    public BigDecimal  getRemainAmount(){
        return this.bidRequestAmount.subtract(this.currentSum);
    }









}

package com.yang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;

import java.util.Date;
@Data
public class Bid {


    private Long id;

    private BigDecimal actualRate;

    private BigDecimal availableAmount;

    private Long bidrequest_id;

    private String bidRequestTitle;//借款标标题

    private LoginInfo bidUserId;//

    private Long bidUser_id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bidTime;//投标时间

    public Bid() {
    }

    public Bid(Long id, BigDecimal actualRate, BigDecimal availableAmount, Long bidrequest_id, String bidRequestTitle, LoginInfo bidUserId, Date bidTime) {
        this.id = id;
        this.actualRate = actualRate;
        this.availableAmount = availableAmount;
        this.bidrequest_id = bidrequest_id;
        this.bidRequestTitle = bidRequestTitle;
        this.bidUserId = bidUserId;
        this.bidTime = bidTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getActualRate() {
        return actualRate;
    }

    public void setActualRate(BigDecimal actualRate) {
        this.actualRate = actualRate;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public Long getBidrequest_id() {
        return bidrequest_id;
    }

    public void setBidrequest_id(Long bidrequest_id) {
        this.bidrequest_id = bidrequest_id;
    }

    public String getBidRequestTitle() {
        return bidRequestTitle;
    }

    public void setBidRequestTitle(String bidRequestTitle) {
        this.bidRequestTitle = bidRequestTitle;
    }

    public LoginInfo getBidUserId() {
        return bidUserId;
    }

    public void setBidUserId(LoginInfo bidUserId) {
        this.bidUserId = bidUserId;
    }

    public Date getBidTime() {
        return bidTime;
    }

    public void setBidTime(Date bidTime) {
        this.bidTime = bidTime;
    }




}

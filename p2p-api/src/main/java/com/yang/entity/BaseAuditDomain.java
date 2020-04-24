package com.yang.entity;

import com.yang.util.BidConst;
import lombok.Data;

import java.util.Date;

@Data
public abstract class BaseAuditDomain {



    private LoginInfo applier;//申请人
    private Date applyTime;//申请时间
    private LoginInfo auditor;//审核人
    private Date auditTime;//审核时间
    private int state;//状态
    private String remark;//审核备注

    public String getStateDisplay() {
        switch (state) {
            case BidConst.STATE_APPLY:
                return "申请状态";
            case BidConst.STATE_PASS:
                return "审核通过";
            case BidConst.STATE_REJECT:
                return "审核失败";
            default:
                return "异常";
        }
    }




}

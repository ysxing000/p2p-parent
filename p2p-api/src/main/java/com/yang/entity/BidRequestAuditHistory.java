package com.yang.entity;

import com.yang.util.BidConst;
import lombok.Data;

@Data
public class BidRequestAuditHistory extends BaseAuditDomain {


    private Long bidRequestId;//借款标id
    private int auditType= BidConst.PUBLISH_AUDIT;




}

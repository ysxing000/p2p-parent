package com.yang.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BidRequestAuditQueryObject extends  BaseAuditQueryObject{

    private int[] bidRequestStates;//要查询的多个值

    private String orderBy;//按照哪个列表排序

    private String orderType;//按照什么类型排序

    private Long creatorId;//借款人的id



}

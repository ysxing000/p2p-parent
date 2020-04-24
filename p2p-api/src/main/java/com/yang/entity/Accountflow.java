package com.yang.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Accountflow {

    private Long id;

    private Long accountActionType;//动账类型

    private BigDecimal amount;//动账金额

    private String note;//动账说明

    private BigDecimal balance;//动账后的可用金额

    private BigDecimal freezed;//动账后的冻结金额

    private Date actionTime;//动账时间

    private Long account_id;//所属账户










}

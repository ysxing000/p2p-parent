package com.yang.vo;

import com.yang.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class BaseAuditQueryObject extends QueryObject{



    private int state = -1;
    private Date beginDate;
    private Date endDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        if (endDate != null) {
            return DateUtil.endOfDay(endDate);
        }
        return null;
    }



}

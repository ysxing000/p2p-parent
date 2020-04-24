package com.yang.client;

import com.yang.vo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bidRequest-server")
public interface BidRequestAuditHistoryClient {

    @RequestMapping("/publish")
    public ResultVo<Boolean> befoerpublish(@RequestParam("bidrequestid") Long bidrequestid, @RequestParam("authstat")Integer authstat, @RequestParam("remark")String remark, @RequestParam("auditorId")Long auditorId);

}

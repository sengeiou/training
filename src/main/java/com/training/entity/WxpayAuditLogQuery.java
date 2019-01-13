package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * wxpay_audit_log 查询类
 * Created by huai23 on 2019-01-13 20:31:38.
 */ 
@Data
public class WxpayAuditLogQuery {

    private Long pkId;

    private String transactionId;

    private String type;

    private String auditStaffId;

    private String auditTime;

    private String feature;

    private String remark;

    private Date created;

    private Date modified;


}


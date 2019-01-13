package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * wxpay_log 查询类
 * Created by huai23 on 2019-01-13 20:31:57.
 */ 
@Data
public class WxpayLogQuery {

    private Long pkId;

    private String transactionId;

    private String openid;

    private String sign;

    private String outTradeNo;

    private String mchId;

    private String appid;

    private String deviceInfo;

    private String feeType;

    private String cashFee;

    private String totalFee;

    private String tradeType;

    private String attach;

    private String timeEnd;

    private String resultCode;

    private String returnCode;

    private String taxRate;

    private String type;

    private String orderId;

    private String storeId;

    private String memberId;

    private String staffId;

    private String cardNo;

    private String relId;

    private String content;

    private String feature;

    private String remark;

    private String auditStaffId;

    private String auditTime;

    private Date created;

    private Date modified;


}


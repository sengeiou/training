package com.training.domain;

import lombok.Data;

/**
 * training 实体类
 * Created by huai23 on 2018-05-26 17:09:14.
 */ 
@Data
public class WechatPayLog {

    private String transactionId;

    private String memberId;

    private String name;

    private String phone;

    private String payTime;

    private String payFee;

    private String taxRate;

    private String taxFee;

    private String money;

    private String storeId;

    private String storeName;

}


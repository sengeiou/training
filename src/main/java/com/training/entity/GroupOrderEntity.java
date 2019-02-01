package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * group_order 实体类
 * Created by huai23 on 2019-02-01 20:05:18.
 */ 
@Data
public class GroupOrderEntity {

    private Long orderId;

    private String storeId;

    private String memberId;

    private String phone;

    private Integer gender;

    private Integer count;

    private String totalFee;

    private String mainFlag;

    private String mainOrderId;

    private Integer status;

    private String feature;

    private String payType;

    private String payId;

    private String payTime;

    private String remark;

    private Date created;

    private Date modified;

    private String code;

    private String openId;

}


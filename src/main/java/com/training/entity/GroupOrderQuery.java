package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * group_order 查询类
 * Created by huai23 on 2019-02-01 20:05:18.
 */ 
@Data
public class GroupOrderQuery {

    private Long orderId;

    private String buyId;

    private String storeId;

    private String memberId;

    private String phone;

    private String name;

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

    private String startDate;

    private String endDate;

}


package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * group_order 实体类
 * Created by huai23 on 2019-01-30 22:53:43.
 */ 
@Data
public class GroupOrderEntity {

    private Long pkId;

    private String orderId;

    private String storeId;

    private String memberId;

    private String phone;

    private Integer gender;

    private Integer count;

    private String mainFlag;

    private String mainOrderId;

    private Integer status;

    private String feature;

    private String payType;

    private String payId;

    private String payDate;

    private String remark;

    private Date created;

    private Date modified;


}


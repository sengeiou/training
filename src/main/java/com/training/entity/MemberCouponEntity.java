package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * member_coupon 实体类
 * Created by huai23 on 2018-06-30 10:02:47.
 */ 
@Data
public class MemberCouponEntity {

    private Long couponId;

    private String memberId;

    private String storeId;

    private String type;

    private String title;

    private String startDate;

    private String endDate;

    private String content;

    private String feature;

    private String remark;

    private Integer status;

    private String useDate;

    private String useStaffId;

    private String creator;

    private Date created;

    private Date modified;


}


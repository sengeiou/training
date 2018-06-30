package com.training.domain;

import lombok.Data;

import java.util.Date;

/**
 * member_coupon 实体类
 * Created by huai23 on 2018-06-30 11:02:43.
 */ 
@Data
public class MemberCoupon {

    private Long couponId;

    private String memberId;

    private String memberName;

    private String storeId;

    private String type;

    private String title;

    private Integer discount;

    private Integer total;

    private Integer reduction;

    private String startDate;

    private String endDate;

    private String content;

    private String origin;

    private String feature;

    private String remark;

    private Integer status;

    private String useDate;

    private String useStaffId;

    private String creator;

    private Date created;

    private Date modified;

    private Member member;

}


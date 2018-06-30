package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * member_coupon 查询类
 * Created by huai23 on 2018-06-30 11:02:43.
 */ 
@Data
public class MemberCouponQuery {

    private Long couponId;

    private String memberId;

    private String storeId;

    private String type;

    private String title;

    private Integer discount;

    private Integer total;

    private Integer reduction;

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


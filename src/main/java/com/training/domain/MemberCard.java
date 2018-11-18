package com.training.domain;

import lombok.Data;

import java.util.Date;

/**
 * member_card 实体类
 * Created by huai23 on 2018-05-26 13:53:17.
 */ 
@Data
public class MemberCard {

    private String cardNo;

    private String cardId;

    private String cardName;

    private String memberId;

    private String memberName;

    private String coachId;

    private String coachName;

    private String saleStaffId;

    private String saleStaffName;

    private String storeId;

    private String storeName;

    private String type;

    private String cardType;

    private String money;

    private Integer count;

    private Integer total;

    private Integer days;

    private String startDate;

    private String endDate;

    private Integer delay;

    private String feature;

    private String remark;

    private String auditId;

    private String contractId;

    private Integer status;

    private String creater;

    private Date created;

    private Date modified;

    private String staffId;

    private Integer canDelay;

    private String delayFee;

    private Integer delayDays;

    private String showStatus = " ";


}


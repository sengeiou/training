package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * member_card 查询类
 * Created by huai23 on 2018-05-26 13:53:17.
 */ 
@Data
public class MemberCardQuery {

    private Long pkId;

    private String cardNo;

    private String cardId;

    private String memberId;

    private String coachId;

    private String storeId;

    private String type;

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


}


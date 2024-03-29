package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * kpi_staff_detail 实体类
 * Created by huai23 on 2018-11-18 12:13:37.
 */ 
@Data
public class KpiStaffDetailEntity {

    private Long pkId;

    private String cardNo;

    private String contractId;

    private String type;

    private String cardType;

    private String month;

    private String day;

    private String staffId;

    private String storeId;

    private String memberId;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;


}


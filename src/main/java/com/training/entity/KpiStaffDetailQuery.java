package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * kpi_staff_detail 查询类
 * Created by huai23 on 2018-11-18 10:53:42.
 */ 
@Data
public class KpiStaffDetailQuery {

    private Long pkId;

    private String cardNo;

    private String contractId;

    private String month;

    private String type;

    private String staffId;

    private String storeId;

    private String memberId;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;


}


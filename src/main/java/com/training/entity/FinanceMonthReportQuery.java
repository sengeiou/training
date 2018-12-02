package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * finance_month_report 查询类
 * Created by huai23 on 2018-12-02 20:58:01.
 */ 
@Data
public class FinanceMonthReportQuery {

    private Long pkId;

    private String storeId;

    private String storeName;

    private String year;

    private String month;

    private String reportDate;

    private String saleMoney;

    private String saleDaysCount;

    private String waitingDaysMoney;

    private String waitingDaysCount;

    private String usedDaysMoney;

    private String usedDaysCount;

    private String outDaysOney;

    private String inDaysMoney;

    private String backDaysMoney;

    private String param1;

    private String param2;

    private String param3;

    private String param4;

    private String param5;

    private String param6;

    private String param7;

    private String param8;

    private String reportData;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;


}


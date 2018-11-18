package com.training.domain;

import lombok.Data;

/**
 * 财务月卡报表 实体类
 * Created by huai23 on 2018-11-10 15:01:40.
 */ 
@Data
public class FinanceMonthCardReportData {

    private String storeId;

    private String storeName;

    private String month;

    private String saleMoney;

    private Integer saleDaysCount;

    private Integer waitingDaysMoney;

    private Integer waitingDaysCount;

    private Integer usedDaysMoney;

    private Integer usedDaysCount;

    private Integer outDaysMoney;

    private Integer inDaysMoney;

    private Integer backDaysMoney;


}


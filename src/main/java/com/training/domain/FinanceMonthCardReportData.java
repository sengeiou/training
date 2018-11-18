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

    private String saleDaysCount;

    private String waitingDaysMoney;

    private String waitingDaysCount;

    private String usedDaysMoney;

    private String usedDaysCount;

    private String outDaysMoney;

    private String inDaysMoney;

    private String backDaysMoney;


}


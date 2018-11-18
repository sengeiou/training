package com.training.domain;

import lombok.Data;

/**
 * 财务次卡报表 实体类
 * Created by huai23 on 2018-11-10 15:01:40.
 */ 
@Data
public class FinanceTimesCardReportData {

    private String storeId;

    private String storeName;

    private String month;

    private String saleMoney;

    private String saleLessonCount;

    private String waitingLessonMoney;

    private String waitingLessonCount;

    private String usedLessonMoney;

    private String usedLessonCount;

    private String deadLessonMoney;

    private String deadLessonCount;

    private String delayMoney;

    private String outLessonMoney;

    private String outLessonCount;

    private String inLessonMoney;

    private String inLessonCount;

    private String backLessonMoney;

    private String backLessonCount;

}


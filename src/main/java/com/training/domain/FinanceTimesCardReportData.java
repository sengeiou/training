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

    private Integer saleLessonCount;

    private Integer waitingLessonMoney;

    private Integer waitingLessonCount;

    private Integer usedLessonMoney;

    private Integer usedLessonCount;

    private Integer deadLessonMoney;

    private Integer deadLessonCount;

    private String delayMoney;

    private Integer outLessonMoney;

    private Integer outLessonCount;

    private Integer inLessonMoney;

    private Integer inLessonCount;

    private Integer backLessonMoney;

    private Integer backLessonCount;

}


package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * finance_once_report 实体类
 * Created by huai23 on 2018-12-02 20:58:14.
 */ 
@Data
public class FinanceOnceReportEntity {

    private Long pkId;

    private String storeId;

    private String storeName;

    private String year;

    private String month;

    private String reportDate;

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


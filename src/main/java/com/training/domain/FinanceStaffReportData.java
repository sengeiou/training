package com.training.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 员工薪资报表 实体类
 * Created by huai23 on 2018-07-13 23:24:52.
 */ 
@Data
public class FinanceStaffReportData {

    private String month;

    private String staffId;

    private String staffName;

    private String storeId;

    private String storeName;

    private String templateId;

    private String templateName;

    private String phone;

    private String job;

    private String star;

    private String kpiScore;

    private String saleMoney;

    private String xkMoney;

    private String timesCardLessonCount;

    private String monthCardSingleLessonCount;

    private String monthCardMultiLessonCount;

    private String tyCardMultiLessonCount;

    private String specialLessonCount;

    private String teamLessonCount;


}


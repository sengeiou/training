package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * finance_staff_report 查询类
 * Created by huai23 on 2018-12-02 22:02:12.
 */ 
@Data
public class FinanceStaffReportQuery {

    private Long pkId;

    private String staffId;

    private String staffName;

    private String storeId;

    private String storeName;

    private String templateId;

    private String templateName;

    private String type;

    private String year;

    private String month;

    private String reportDate;

    private String job;

    private String kpiScore;

    private Integer star;

    private String saleMoney;

    private String xkMoney;

    private String timesCardLessonCount;

    private String monthCardSingleLessonCount;

    private String monthCardMultiLessonCount;

    private String tyCardMultiLessonCount;

    private String specialLessonCount;

    private String teamLessonCount;

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


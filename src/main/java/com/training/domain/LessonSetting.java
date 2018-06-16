package com.training.domain;

import lombok.Data;

import java.util.Date;

/**
 * lesson_setting 实体类
 * Created by huai23 on 2018-06-16 08:59:33.
 */ 
@Data
public class LessonSetting {

    private Long pkId;

    private String lessonId;

    private String storeId;

    private String storeName;

    private String title;

    private String classroom;

    private String type;

    private String typeName;

    private String coachId;

    private String coachName;

    private String startDate;

    private String endDate;

    private Integer startHour;

    private Integer endHour;

    private Integer quotaMin;

    private Integer quotaMax;

    private String weekRepeat;

    private String feature;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;


}


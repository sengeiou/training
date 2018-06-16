package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * lesson_setting 查询类
 * Created by huai23 on 2018-06-16 08:59:33.
 */ 
@Data
public class LessonSettingQuery {

    private Long pkId;

    private String lessonId;

    private String storeId;

    private String title;

    private String classroom;

    private String type;

    private String coachId;

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


package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * lesson 查询类
 * Created by huai23 on 2018-05-26 17:02:18.
 */ 
@Data
public class LessonQuery {

    private Long pkId;

    private String lessonId;

    private String storeId;

    private String title;

    private String type;

    private String specialType;

    private String coachId;

    private String cardNo;

    private String lessonDate;

    private Integer startHour;

    private Integer endHour;

    private Integer quota;

    private String trainingData;

    private String feature;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;


}


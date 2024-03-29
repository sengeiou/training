package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * training 实体类
 * Created by huai23 on 2018-05-26 17:09:14.
 */ 
@Data
public class TrainingEntity {

    private Long pkId;

    private String trainingId;

    private String lessonId;

    private String title;

    private String storeId;

    private String type;

    private String memberId;

    private String coachId;

    private String staffId;

    private String cardNo;

    private String cardType;

    private String lessonDate;

    private Integer startHour;

    private Integer endHour;

    private String trainingData;

    private String signTime;

    private String feature;

    private String remark;

    private Integer status;

    private Integer showTag;

    private Date created;

    private Date modified;


}


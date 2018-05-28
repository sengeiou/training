package com.training.domain;

import lombok.Data;
import java.util.Date;

/**
 * training 实体类
 * Created by huai23 on 2018-05-26 17:09:14.
 */ 
@Data
public class Training {

    private Long pkId;

    private String trainingId;

    private String lessonId;

    private String title;

    private String storeId;

    private String type;

    private String memberId;

    private String coachId;

    private String cardNo;

    private String cardType;

    private String lessonDate;

    private Integer startHour;

    private Integer endHour;

    private String trainingData;

    private String feature;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;

    private Member member;

}


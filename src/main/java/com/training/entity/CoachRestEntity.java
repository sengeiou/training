package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * coach_rest 实体类
 * Created by huai23 on 2018-05-26 13:55:16.
 */ 
@Data
public class CoachRestEntity {

    private Long pkId;

    private String restId;

    private String coachId;

    private String title;

    private String type;

    private String restDate;

    private Integer startHour;

    private Integer endHour;

    private Integer weekIndex;

    private Integer monthIndex;

    private Integer status;

    private String remark;

    private Date created;

    private Date modified;


}


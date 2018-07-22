package com.training.domain;

import lombok.Data;

import java.util.Date;

/**
 * member_medal 实体类
 * Created by huai23 on 2018-05-26 13:54:40.
 */ 
@Data
public class StaffMedal {

    private Long pkId;

    private String staffId;

    private String medalId;

    private String medalName;

    private String content;

    private String awardDate;

    private String feature;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;


}


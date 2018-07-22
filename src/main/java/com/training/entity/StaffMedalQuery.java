package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * staff_medal 查询类
 * Created by huai23 on 2018-07-22 23:28:30.
 */ 
@Data
public class StaffMedalQuery {

    private Long pkId;

    private String staffId;

    private String medalId;

    private String content;

    private String awardDate;

    private String feature;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;


}


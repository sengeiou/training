package com.training.domain;

import lombok.Data;

import java.util.Date;

/**
 * card 实体类
 * Created by huai23 on 2018-06-06 18:46:25.
 */ 
@Data
public class Card {

    private Long pkId;

    private String cardId;

    private String cardName;

    private String type;

    private String typeName;

    private String price;

    private Integer total;

    private Integer days;

    private String startDate;

    private String endDate;

    private String desc;

    private String feature;

    private String remark;

    private Integer status;

    private String processId;

    private String creater;

    private Date created;

    private Date modified;


}


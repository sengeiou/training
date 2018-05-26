package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * card 实体类
 * Created by huai23 on 2018-05-26 13:53:45.
 */ 
@Data
public class CardEntity {

    private Long pkId;

    private String cardId;

    private String cardName;

    private String type;

    private String price;

    private Integer total;

    private Integer days;

    private String startDate;

    private String endDate;

    private String desc;

    private String feature;

    private String remark;

    private Integer status;

    private String creater;

    private Date created;

    private Date modified;


}


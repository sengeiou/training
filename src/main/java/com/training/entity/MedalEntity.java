package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * medal 实体类
 * Created by huai23 on 2018-07-24 22:48:27.
 */ 
@Data
public class MedalEntity {

    private Long pkId;

    private String medalId;

    private String name;

    private String type;

    private Integer level;

    private String content;

    private String image;

    private String feature;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;


}


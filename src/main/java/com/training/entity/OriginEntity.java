package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * origin 实体类
 * Created by huai23 on 2019-01-12 13:23:55.
 */ 
@Data
public class OriginEntity {

    private Long originId;

    private String code;

    private String name;

    private String type;

    private String image;

    private String feature;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;


}


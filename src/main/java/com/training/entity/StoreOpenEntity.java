package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * store_open 实体类
 * Created by huai23 on 2018-07-10 20:40:20.
 */ 
@Data
public class StoreOpenEntity {

    private Long pkId;

    private String storeId;

    private String storeName;

    private String year;

    private Integer m01;

    private Integer m02;

    private Integer m03;

    private Integer m04;

    private Integer m05;

    private Integer m06;

    private Integer m07;

    private Integer m08;

    private Integer m09;

    private Integer m10;

    private Integer m11;

    private Integer m12;

    private String remark;

    private Date created;

    private Date modified;


}


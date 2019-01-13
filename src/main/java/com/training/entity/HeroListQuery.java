package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * hero_list 查询类
 * Created by huai23 on 2019-01-13 20:32:10.
 */ 
@Data
public class HeroListQuery {

    private Long pkId;

    private String heroDate;

    private String type;

    private String staffId;

    private String staffName;

    private String memberId;

    private String memberName;

    private String storeId;

    private String storeName;

    private String image;

    private Integer sort;

    private String label;

    private String value;

    private String unit;

    private String feature;

    private String remark;

    private Date created;

    private Date modified;


}


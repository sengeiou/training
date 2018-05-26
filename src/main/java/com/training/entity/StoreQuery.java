package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * store 查询类
 * Created by huai23 on 2018-05-26 13:43:38.
 */ 
@Data
public class StoreQuery {

    private Long pkId;

    private String storeId;

    private String deptId;

    private String userId;

    private String name;

    private String address;

    private String feature;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;


}


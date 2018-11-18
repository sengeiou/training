package com.training.entity;

import lombok.Data;

import java.util.Date;

/**
 * store 查询类
 * Created by huai23 on 2018-05-26 13:43:38.
 */ 
@Data
public class StoreDataQuery {

    private String storeId;

    private String staffId;

    private String month;

    private String phone;

    private String templateId;

    private String job;

    private String startDate;

    private String endDate;

}


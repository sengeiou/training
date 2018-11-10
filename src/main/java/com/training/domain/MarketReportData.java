package com.training.domain;

import lombok.Data;

/**
 * 营销报表 实体类
 * Created by huai23 on 2018-11-10 15:01:40.
 */ 
@Data
public class MarketReportData {

    private String storeId;

    private String storeName;

    private String startDate;

    private String endDate;

    private String origin;

    private Integer newCount;

    private Integer arriveCount;

    private Integer orderCount;

    private String money;

}


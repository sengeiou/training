package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * group_buy 查询类
 * Created by huai23 on 2019-01-30 23:03:41.
 */ 
@Data
public class GroupBuyQuery {

    private Long pkId;

    private String buyId;

    private String title;

    private String image;

    private String shareTitle;

    private String shareDesc;

    private String shareImage;

    private String shareUrl;

    private Integer count;

    private Integer initCount;

    private Integer saleCount;

    private String autoComplete;

    private String price;

    private String groupPrice;

    private String startDate;

    private String endDate;

    private Integer limitation;

    private String content;

    private String mainTag;

    private Integer status;

    private Integer viewCount;

    private Integer buyCount;

    private String feature;

    private String remark;

    private Date created;

    private Date modified;

    private String storeList;

}


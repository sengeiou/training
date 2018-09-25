package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * feedback 查询类
 * Created by huai23 on 2018-05-26 13:54:54.
 */ 
@Data
public class FeedbackQuery {

    private Long pkId;

    private String feedbackId;

    private String memberId;

    private String type;

    private String title;

    private String content;

    private String image;

    private String remark;

    private Integer trackTag;

    private Integer status;

    private Date created;

    private Date modified;

    private String startDate;

    private String endDate;

    private String storeId;

    private String phone;

}


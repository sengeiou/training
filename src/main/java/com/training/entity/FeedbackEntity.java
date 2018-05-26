package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * feedback 实体类
 * Created by huai23 on 2018-05-26 13:54:54.
 */ 
@Data
public class FeedbackEntity {

    private Long pkId;

    private String feedbackId;

    private String memberId;

    private String title;

    private String content;

    private String image;

    private String remark;

    private Date created;

    private Date modified;


}


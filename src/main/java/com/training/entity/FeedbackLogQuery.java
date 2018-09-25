package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * feedback_log 查询类
 * Created by huai23 on 2018-09-26 00:21:14.
 */ 
@Data
public class FeedbackLogQuery {

    private Long pkId;

    private String logId;

    private String type;

    private String content;

    private String feedbackId;

    private String memberId;

    private String staffId;

    private String storeId;

    private String remark;

    private Date created;

    private Date modified;


}


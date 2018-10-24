package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * sms_log 实体类
 * Created by huai23 on 2018-10-24 08:29:13.
 */ 
@Data
public class SmsLogEntity {

    private Long pkId;

    private String logId;

    private String type;

    private String storeId;

    private String memberId;

    private String staffId;

    private String cardNo;

    private String trainingId;

    private String relId;

    private String phone;

    private String content;

    private Integer status;

    private String sendTime;

    private String remark;

    private Date created;

    private Date modified;


}


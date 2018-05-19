package com.training.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Email {

    private String emailId;

    private String title;

    private String content;

    private String fromEmail;

    private String contactId;

    private String toEmail;

    private Integer total;

    private Integer sendCount;

    private Integer errorCount;

    private String price;

    private String fee;

    private Integer status;

    private String orgId;

    private String userId;

    private Date sendTime;

    private Date finishTime;

    private String remark;

    private Date created;

    private Date modified;

}

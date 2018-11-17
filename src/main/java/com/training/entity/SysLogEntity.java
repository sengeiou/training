package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * sys_log 实体类
 * Created by huai23 on 2018-06-03 15:57:51.
 */ 
@Data
public class SysLogEntity {

    private Long pkId;

    private String logId;

    private String type;

    private Integer level;

    private String id1;

    private String id2;

    private String logText;

    private String content;

    private String remark;

    private Date created;

    private Date modified;

    private String storeId;

    private String storeName;

    private String memberId;

    private String name;

    private String date;

    private String staffId;

    private String staffName;

    private String staffName2;

    private String cardNo;

    private String cardType;

    private String money;

}


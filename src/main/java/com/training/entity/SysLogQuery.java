package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * sys_log 查询类
 * Created by huai23 on 2018-06-03 15:57:51.
 */ 
@Data
public class SysLogQuery {

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

    private String startDate;

    private String endDate;

}


package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * member_log 实体类
 * Created by huai23 on 2018-06-09 09:16:23.
 */ 
@Data
public class MemberLogEntity {

    private Long pkId;

    private String logId;

    private String type;

    private String content;

    private String staffId;

    private String storeId;

    private String remark;

    private Date created;

    private Date modified;


}


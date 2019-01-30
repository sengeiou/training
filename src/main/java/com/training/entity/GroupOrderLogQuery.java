package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * group_order_log 查询类
 * Created by huai23 on 2019-01-30 23:16:13.
 */ 
@Data
public class GroupOrderLogQuery {

    private Long pkId;

    private String logId;

    private String orderId;

    private String type;

    private String content;

    private String staffId;

    private String storeId;

    private String remark;

    private Date created;

    private Date modified;


}


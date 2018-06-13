package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * store_task 查询类
 * Created by huai23 on 2018-06-13 22:49:38.
 */ 
@Data
public class StoreTaskQuery {

    private Long pkId;

    private String taskId;

    private String storeId;

    private String staffId;

    private String type;

    private String name;

    private String content;

    private String pushDate;

    private String remark;

    private Integer status;

    private String creator;

    private Date created;

    private Date modified;


}


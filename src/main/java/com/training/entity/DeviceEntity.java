package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * device 实体类
 * Created by huai23 on 2019-01-26 23:09:05.
 */ 
@Data
public class DeviceEntity {

    private Long pkId;

    private String deviceSn;

    private String storeId;

    private String feature;

    private Integer status;

    private String remark;

    private Date created;

    private Date modified;


}


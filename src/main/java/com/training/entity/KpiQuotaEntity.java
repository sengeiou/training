package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * kpi_quota 实体类
 * Created by huai23 on 2018-07-09 22:42:44.
 */ 
@Data
public class KpiQuotaEntity {

    private Long pkId;

    private String quotaId;

    private String type;

    private String name;

    private String content;

    private String feature;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;


}


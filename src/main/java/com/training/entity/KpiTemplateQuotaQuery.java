package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * kpi_template_quota 查询类
 * Created by huai23 on 2018-07-09 22:42:58.
 */ 
@Data
public class KpiTemplateQuotaQuery {

    private Long pkId;

    private String templateId;

    private String quotaId;

    private String type;

    private String name;

    private Integer weight;

    private String standard;

    private String score;

    private String content;

    private String feature;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;


}


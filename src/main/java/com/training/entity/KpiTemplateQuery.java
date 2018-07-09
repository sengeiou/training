package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * kpi_template 查询类
 * Created by huai23 on 2018-07-09 22:42:32.
 */ 
@Data
public class KpiTemplateQuery {

    private Long pkId;

    private String templateId;

    private String type;

    private String title;

    private String remark;

    private Integer status;

    private String creator;

    private Date created;

    private Date modified;


}


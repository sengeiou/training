package com.training.entity;

import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * kpi_template 实体类
 * Created by huai23 on 2018-07-09 22:42:32.
 */ 
@Data
public class KpiTemplateEntity {

    private Long pkId;

    private String templateId;

    private String type;

    private String title;

    private String remark;

    private Integer status;

    private String creator;

    private Date created;

    private Date modified;

    private List<KpiTemplateQuotaEntity> quotaEntityList;

}


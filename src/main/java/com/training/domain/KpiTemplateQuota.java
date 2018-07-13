package com.training.domain;

import com.training.entity.KpiQuotaStandard;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * kpi_template_quota 实体类
 * Created by huai23 on 2018-07-09 22:42:58.
 */ 
@Data
public class KpiTemplateQuota {

    private String templateId;

    private String quotaId;

    private String type;

    private String name;

    private Integer weight;

    private String standard;

    private String finishRate;

    private String score;

    private String kpiScore;

    private String content;

    private String feature;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;

    private List<KpiQuotaStandard> standardList;

}


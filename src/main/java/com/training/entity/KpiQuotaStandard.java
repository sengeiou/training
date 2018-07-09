package com.training.entity;

import lombok.Data;

import java.util.Date;

/**
 * kpi_quota 实体类
 * Created by huai23 on 2018-07-09 22:42:44.
 */ 
@Data
public class KpiQuotaStandard {

    private Long pkId;

    private String templateId;

    private String quotaId;

    private String min;

    private String max;

    private String score;

}


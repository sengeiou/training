package com.training.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * kpi_staff_month 实体类
 * Created by huai23 on 2018-07-13 23:24:52.
 */ 
@Data
public class KpiStaffMonth {

    private String staffId;

    private String staffName;

    private String storeId;

    private String storeName;

    private String templateId;

    private String templateName;

    private String title;

    private String type;

    private String year;

    private String month;

    private String xks;

    private String jks;

    private String sjks;

    private String fsjks;

    private String yxhys;

    private String yyts;

    private String xkl;

    private String hyd;

    private String zjs;

    private String tnkh;

    private String zykh;

    private String tss;

    private String hydp;

    private String zye;

    private String qdzye;

    private String xsmb;

    private String xswcl;

    private String qdxks;

    private String qdjks;

    private String qdsjks;

    private String qdfsjks;

    private String qdyxhys;

    private String qdyyts;

    private String qdxkl;

    private String qdzjs;

    private String qdhydp;

    private String qdhyd;

    private String cjs;

    private String tcs;

    private String qdcjs;

    private String qdtcs;

    private String tczhl;

    private String kpiScore;

    private String kpiData;

    private String param1;

    private String param2;

    private String param3;

    private String param4;

    private String param5;

    private String param6;

    private String param7;

    private String param8;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;

    private List<KpiTemplateQuota> kpiTemplateQuotaList;

    private String star;

}


package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * measurement 实体类
 * Created by huai23 on 2019-01-22 21:54:18.
 */ 
@Data
public class MeasurementEntity {

    private Long pkId;

    private String measurementId;

    private String bodyId;

    private String memberId;

    private String storeId;

    private String deviceSn;

    private Integer gender;

    private Integer age;

    private Integer height;

    private String weight;

    private String phone;

    private String outline;

    private String measurement;

    private String composition;

    private String posture;

    private String girth;

    private String feature;

    private String measureDate;

    private String startTime;

    private String remark;

    private Date created;

    private Date modified;


}


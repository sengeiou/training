package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * member_body 查询类
 * Created by huai23 on 2018-05-26 13:54:03.
 */ 
@Data
public class MemberBodyQuery {

    private Long pkId;

    private String bodyId;

    private String memberId;

    private String coachId;

    private String height;

    private String weight;

    private String bmi;

    private String fat;

    private String armLeft;

    private String armRight;

    private String waist;

    private String hip;

    private String legLeft;

    private String legRight;

    private String imageFace;

    private String imageLeft;

    private String imageRight;

    private String imageReport;

    private String feature;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;

    private String measurementId;

}


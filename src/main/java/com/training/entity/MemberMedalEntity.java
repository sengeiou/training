package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * member_medal 实体类
 * Created by huai23 on 2018-07-24 22:31:46.
 */ 
@Data
public class MemberMedalEntity {

    private Long pkId;

    private String memberId;

    private String medalId;

    private String content;

    private String awardDate;

    private String feature;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;


}


package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * member_medal 查询类
 * Created by huai23 on 2018-05-26 13:54:40.
 */ 
@Data
public class MemberMedalQuery {

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


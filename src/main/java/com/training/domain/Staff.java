package com.training.domain;

import lombok.Data;

import java.util.Date;

/**
 * staff 实体类
 * Created by huai23 on 2018-05-26 13:55:30.
 */ 
@Data
public class Staff {

    private Long pkId;

    private String staffId;

    private String storeId;

    private String storName;

    private String roleId;

    private String roleName;

    private String username;

    private String password;

    private String custname;

    private String email;

    private String phone;

    private String job;

    private String image;

    private String openId;

    private String unionId;

    private String feature;

    private Integer status;

    private String relId;

    private Date created;

    private Date modified;

    private Integer memberCount;

    private Integer kpi;

    private String newPassword;

    private Role role;

    private String heroNumber;


}


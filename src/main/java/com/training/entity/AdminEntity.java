package com.training.entity;

import lombok.Data;

import java.util.Date;

/**
 * admin 实体类
 * Created by huai23 on 2017-12-19 11:16:51.
 */ 
@Data
public class AdminEntity {

    private Long pkId;

    private String adminId;

    private String account;

    private String password;

    private String name;

    private String email;

    private String phone;

    private String roleId;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;


}


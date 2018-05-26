package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * staff 查询类
 * Created by huai23 on 2018-05-26 13:55:30.
 */ 
@Data
public class StaffQuery {

    private Long pkId;

    private String staffId;

    private String storeId;

    private String roleId;

    private String username;

    private String password;

    private String custname;

    private String email;

    private String phone;

    private String job;

    private String openId;

    private String unionId;

    private String feature;

    private Integer status;

    private String relId;

    private Date created;

    private Date modified;


}


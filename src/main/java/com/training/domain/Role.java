package com.training.domain;

import lombok.Data;

import java.util.Date;

/**
 * role 实体类
 * Created by huai23 on 2018-06-27 15:28:01.
 */ 
@Data
public class Role {

    private Long pkId;

    private String roleId;

    private String roleName;

    private String nodeData;

    private String storeData;

    private String feature;

    private Integer status;

    private Date created;

    private Date modified;


}


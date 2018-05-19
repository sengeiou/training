package com.training.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable{

    private static final long serialVersionUID = -8036219797322639507L;

    private String userId;

    private String orgId;

    private String orgName;

    private String username;

    private String email;

    private String custname;

    private String pwd;

    private String roleId;

    private Integer status;

    private String code;

    private String verifyCode;

}
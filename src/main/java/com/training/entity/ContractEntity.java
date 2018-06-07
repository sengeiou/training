package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * contract 实体类
 * Created by huai23 on 2018-06-06 21:52:04.
 */ 
@Data
public class ContractEntity {

    private Long pkId;

    private String contractId;

    private String contractName;

    private String memberName;

    private String gender;

    private String phone;

    private String type;

    private String money;

    private String total;

    private String payType;

    private String startDate;

    private String endDate;

    private String salesman;

    private String coach;

    private String feature;

    private String remark;

    private String signDate;

    private String image;

    private Integer status;

    private Date created;

    private Date modified;


}


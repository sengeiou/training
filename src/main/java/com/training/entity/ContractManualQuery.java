package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * contract_manual 查询类
 * Created by huai23 on 2018-06-28 02:06:09.
 */ 
@Data
public class ContractManualQuery {

    private Long pkId;

    private String contractId;

    private String cardNo;

    private String memberName;

    private String phone;

    private String cardType;

    private String storeName;

    private String salesman;

    private String salesmanPhone;

    private String coach;

    private String coachPhone;

    private String total;

    private String money;

    private String type;

    private String payType;

    private String price;

    private String startDate;

    private String endDate;

    private String realFee;

    private String count;

    private Integer status;

    private String pauseDate;

    private String deadDate;

    private String remark;

    private String feature;

    private Date created;

    private Date modified;

    private Integer dealFlag;

}


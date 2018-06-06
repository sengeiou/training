package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * member 查询类
 * Created by huai23 on 2018-06-06 20:15:26.
 */ 
@Data
public class MemberQuery {

    private Long pkId;

    private String memberId;

    private String storeId;

    private String type;

    private String name;

    private String email;

    private String phone;

    private String nickname;

    private String image;

    private Integer age;

    private Integer gender;

    private Integer height;

    private String idCard;

    private String address;

    private String cardNo;

    private Integer trainingHours;

    private String openId;

    private String unionId;

    private String feature;

    private String origin;

    private String salesman;

    private String remark;

    private Integer status;

    private String creater;

    private Date created;

    private Date modified;


}


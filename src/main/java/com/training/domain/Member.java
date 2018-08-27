package com.training.domain;

import com.training.common.MemberStatusEnum;
import com.training.entity.MemberLogEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * member 实体类
 * Created by huai23 on 2018-05-26 13:33:17.
 */ 
@Data
public class Member {

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

    private String cardId;

    private Integer trainingHours;

    private String openId;

    private String unionId;

    private String feature;

    private String origin;

    private String remark;

    private Integer status;

    private String creater;

    private Date created;

    private Date modified;

    private String code;

    private String cardType;

    private String staffId;

    private String coachStaffId;

    private String coachName;

    private MemberLogEntity memberLog;

    private Integer hasTY = 0;

    private Integer starLevel;

    private List<MemberMedal> memberMedalList;

    private String showTag;

    public String getShowStatus(){
        if(null!=MemberStatusEnum.getEnumByKey(this.status)){
            return MemberStatusEnum.getEnumByKey(this.status).getDesc();
        }
        return "";
    }

}


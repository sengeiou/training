package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * member_pause 实体类
 * Created by huai23 on 2018-06-27 20:52:15.
 */ 
@Data
public class MemberPauseEntity {

    private Long pkId;

    private String memberId;

    private String name;

    private String phone;

    private String pauseDate;

    private String pauseStaffId;

    private String pauseStaffName;

    private String restoreDate;

    private String restoreStaffId;

    private String restoreStaffName;

    private Integer status;

    private String showStatus;

    private String creater;

    private Date created;

    private Date modified;

    private String storeId;

    private String storeName;

}


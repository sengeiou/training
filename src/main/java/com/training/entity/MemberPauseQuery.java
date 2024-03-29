package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * member_pause 查询类
 * Created by huai23 on 2018-06-27 20:52:15.
 */ 
@Data
public class MemberPauseQuery {

    private Long pkId;

    private String memberId;

    private String cardNo;

    private String pauseDate;

    private String pauseStaffId;

    private String restoreDate;

    private String restoreStaffId;

    private Integer status;

    private String creater;

    private Date created;

    private Date modified;

    private String startDate;

    private String endDate;

    private String storeId;

    private String staffId;

    private String name;

    private String phone;

}


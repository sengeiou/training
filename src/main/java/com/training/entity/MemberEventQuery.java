package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * member_event 查询类
 * Created by huai23 on 2018-07-31 07:36:32.
 */ 
@Data
public class MemberEventQuery {

    private Long pkId;

    private String eventId;

    private String memberId;

    private String type;

    private String title;

    private String content;

    private String eventDate;

    private String remark;

    private Date created;

    private Date modified;


}


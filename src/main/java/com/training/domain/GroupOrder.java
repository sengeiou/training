package com.training.domain;

import com.training.entity.GroupOrderLogEntity;
import lombok.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * group_order 实体类
 * Created by huai23 on 2019-02-01 20:05:18.
 */ 
@Data
public class GroupOrder {

    private Long orderId;

    private String storeId;

    private String storeName;

    private String title;

    private String memberId;

    private String memberName;

    private String phone;

    private Integer gender;

    private Integer count;

    private String totalFee;

    private String mainFlag;

    private String mainOrderId;

    private Integer status;

    private String feature;

    private String payType;

    private String payId;

    private String payTime;

    private String remark;

    private Date created;

    private Date modified;

    private String showStatus;

    private List<GroupOrderLogEntity> logs = new ArrayList();

}


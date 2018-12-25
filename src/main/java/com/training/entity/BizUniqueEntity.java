package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * biz_unique 实体类
 * Created by huai23 on 2018-12-25 23:24:18.
 */ 
@Data
public class BizUniqueEntity {

    private Long pkId;

    private String bizId;

    private Date created;

    private Date modified;


}


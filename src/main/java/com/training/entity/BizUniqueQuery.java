package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * biz_unique 查询类
 * Created by huai23 on 2018-12-25 23:24:18.
 */ 
@Data
public class BizUniqueQuery {

    private Long pkId;

    private String bizId;

    private Date created;

    private Date modified;


}


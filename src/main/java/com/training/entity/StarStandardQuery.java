package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * star_standard 查询类
 * Created by huai23 on 2018-07-22 20:49:43.
 */ 
@Data
public class StarStandardQuery {

    private Long pkId;

    private String starId;

    private String storeId;

    private String name;

    private Integer monthNum;

    private String score;

    private Integer memberNum;

    private Integer medalNum;

    private String scoreDown;

    private Integer memberNumDown;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;


}


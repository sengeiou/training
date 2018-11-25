package com.training.entity;

import lombok.Data;
import java.util.Date;

/**
 * rotation_chart 查询类
 * Created by huai23 on 2018-11-25 10:40:37.
 */ 
@Data
public class RotationChartQuery {

    private Long pkId;

    private String chartId;

    private String title;

    private String image;

    private String url;

    private String content;

    private Integer sort;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;


}


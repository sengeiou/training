package com.training.domain;

import lombok.Data;

import java.util.List;

/**
 * member 时间表
 * Created by huai23 on 2018-05-26 13:33:17.
 */
@Data
public class Schedule {

    private Coach coach;

    private String type;

    private String day;

    private List<Lesson> lessonList;

}

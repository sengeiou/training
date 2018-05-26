package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * coach_rest 数据库操作类
 * Created by huai23 on 2018-05-26 13:55:16.
 */ 
@Mapper
public interface CoachRestRepository {

    @Insert("<script> INSERT INTO coach_rest ( " +
                " <if test=\"coachRest.restId != null\"> rest_id, </if>" +
                " <if test=\"coachRest.coachId != null\"> coach_id, </if>" +
                " <if test=\"coachRest.title != null\"> title, </if>" +
                " <if test=\"coachRest.type != null\"> type, </if>" +
                " <if test=\"coachRest.restDate != null\"> rest_date, </if>" +
                " <if test=\"coachRest.startHour != null\"> start_hour, </if>" +
                " <if test=\"coachRest.endHour != null\"> end_hour, </if>" +
                " <if test=\"coachRest.weekIndex != null\"> week_index, </if>" +
                " <if test=\"coachRest.monthIndex != null\"> month_index, </if>" +
                " <if test=\"coachRest.status != null\"> status, </if>" +
                " <if test=\"coachRest.remark != null\"> remark, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"coachRest.restId != null\"> #{coachRest.restId}, </if>" +
                " <if test=\"coachRest.coachId != null\"> #{coachRest.coachId}, </if>" +
                " <if test=\"coachRest.title != null\"> #{coachRest.title}, </if>" +
                " <if test=\"coachRest.type != null\"> #{coachRest.type}, </if>" +
                " <if test=\"coachRest.restDate != null\"> #{coachRest.restDate}, </if>" +
                " <if test=\"coachRest.startHour != null\"> #{coachRest.startHour}, </if>" +
                " <if test=\"coachRest.endHour != null\"> #{coachRest.endHour}, </if>" +
                " <if test=\"coachRest.weekIndex != null\"> #{coachRest.weekIndex}, </if>" +
                " <if test=\"coachRest.monthIndex != null\"> #{coachRest.monthIndex}, </if>" +
                " <if test=\"coachRest.status != null\"> #{coachRest.status}, </if>" +
                " <if test=\"coachRest.remark != null\"> #{coachRest.remark}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("coachRest") CoachRestEntity coachRest);

    @Select("<script> SELECT pk_id,rest_id,coach_id,title,type,rest_date,start_hour,end_hour,week_index,month_index,status,remark,created,modified " +
            " FROM coach_rest " +
            " WHERE 1 = 1 " +
            " <if test=\"query.restId != null\"> AND rest_id = #{query.restId} </if>" +
            " <if test=\"query.coachId != null\"> AND coach_id = #{query.coachId} </if>" +
            " <if test=\"query.title != null\"> AND title = #{query.title} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.restDate != null\"> AND rest_date = #{query.restDate} </if>" +
            " <if test=\"query.startHour != null\"> AND start_hour = #{query.startHour} </if>" +
            " <if test=\"query.endHour != null\"> AND end_hour = #{query.endHour} </if>" +
            " <if test=\"query.weekIndex != null\"> AND week_index = #{query.weekIndex} </if>" +
            " <if test=\"query.monthIndex != null\"> AND month_index = #{query.monthIndex} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<CoachRestEntity> find(@Param("query") CoachRestQuery coachRest , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM coach_rest " +
            " WHERE 1 = 1 " +
            " <if test=\"query.restId != null\"> AND rest_id = #{query.restId} </if>" +
            " <if test=\"query.coachId != null\"> AND coach_id = #{query.coachId} </if>" +
            " <if test=\"query.title != null\"> AND title = #{query.title} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.restDate != null\"> AND rest_date = #{query.restDate} </if>" +
            " <if test=\"query.startHour != null\"> AND start_hour = #{query.startHour} </if>" +
            " <if test=\"query.endHour != null\"> AND end_hour = #{query.endHour} </if>" +
            " <if test=\"query.weekIndex != null\"> AND week_index = #{query.weekIndex} </if>" +
            " <if test=\"query.monthIndex != null\"> AND month_index = #{query.monthIndex} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            "</script>")
    Long count(@Param("query") CoachRestQuery coachRest);

    @Select("<script> SELECT pk_id,rest_id,coach_id,title,type,rest_date,start_hour,end_hour,week_index,month_index,status,remark,created,modified " +
            " FROM coach_rest " +
            " WHERE rest_id = #{id} " +
            "</script>")
    CoachRestEntity getById(@Param("id") String id);

    @Update("<script> UPDATE coach_rest SET " +
                " <if test=\"coachRest.restId != null\"> rest_id = #{coachRest.restId} , </if>" +
                " <if test=\"coachRest.coachId != null\"> coach_id = #{coachRest.coachId} , </if>" +
                " <if test=\"coachRest.title != null\"> title = #{coachRest.title} , </if>" +
                " <if test=\"coachRest.type != null\"> type = #{coachRest.type} , </if>" +
                " <if test=\"coachRest.restDate != null\"> rest_date = #{coachRest.restDate} , </if>" +
                " <if test=\"coachRest.startHour != null\"> start_hour = #{coachRest.startHour} , </if>" +
                " <if test=\"coachRest.endHour != null\"> end_hour = #{coachRest.endHour} , </if>" +
                " <if test=\"coachRest.weekIndex != null\"> week_index = #{coachRest.weekIndex} , </if>" +
                " <if test=\"coachRest.monthIndex != null\"> month_index = #{coachRest.monthIndex} , </if>" +
                " <if test=\"coachRest.status != null\"> status = #{coachRest.status} , </if>" +
                " <if test=\"coachRest.remark != null\"> remark = #{coachRest.remark} , </if>" +
                " modified = now() " +
            " WHERE rest_id = #{coachRest.restId} " +
            "</script>")
    int update(@Param("coachRest") CoachRestEntity coachRest);

    @Update("<script> DELETE  FROM coach_rest " +
            " WHERE rest_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


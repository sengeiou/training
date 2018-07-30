package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * member_event 数据库操作类
 * Created by huai23 on 2018-07-31 07:36:32.
 */ 
@Mapper
public interface MemberEventRepository {

    @Insert("<script> INSERT INTO member_event ( " +
                " <if test=\"memberEvent.eventId != null\"> event_id, </if>" +
                " <if test=\"memberEvent.memberId != null\"> member_id, </if>" +
                " <if test=\"memberEvent.type != null\"> type, </if>" +
                " <if test=\"memberEvent.title != null\"> title, </if>" +
                " <if test=\"memberEvent.content != null\"> content, </if>" +
                " <if test=\"memberEvent.eventDate != null\"> event_date, </if>" +
                " <if test=\"memberEvent.remark != null\"> REMARK, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"memberEvent.eventId != null\"> #{memberEvent.eventId}, </if>" +
                " <if test=\"memberEvent.memberId != null\"> #{memberEvent.memberId}, </if>" +
                " <if test=\"memberEvent.type != null\"> #{memberEvent.type}, </if>" +
                " <if test=\"memberEvent.title != null\"> #{memberEvent.title}, </if>" +
                " <if test=\"memberEvent.content != null\"> #{memberEvent.content}, </if>" +
                " <if test=\"memberEvent.eventDate != null\"> #{memberEvent.eventDate}, </if>" +
                " <if test=\"memberEvent.remark != null\"> #{memberEvent.remark}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("memberEvent") MemberEventEntity memberEvent);

    @Select("<script> SELECT pk_id,event_id,member_id,type,title,content,event_date,REMARK,created,modified " +
            " FROM member_event " +
            " WHERE 1 = 1 " +
            " <if test=\"query.eventId != null\"> AND event_id = #{query.eventId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.title != null\"> AND title = #{query.title} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.eventDate != null\"> AND event_date = #{query.eventDate} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<MemberEventEntity> find(@Param("query") MemberEventQuery memberEvent , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM member_event " +
            " WHERE 1 = 1 " +
            " <if test=\"query.eventId != null\"> AND event_id = #{query.eventId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.title != null\"> AND title = #{query.title} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.eventDate != null\"> AND event_date = #{query.eventDate} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            "</script>")
    Long count(@Param("query") MemberEventQuery memberEvent);

    @Select("<script> SELECT pk_id,event_id,member_id,type,title,content,event_date,REMARK,created,modified " +
            " FROM member_event " +
            " WHERE event_id = #{id} " +
            "</script>")
    MemberEventEntity getById(@Param("id") String id);

    @Update("<script> UPDATE member_event SET " +
                " <if test=\"memberEvent.eventId != null\"> event_id = #{memberEvent.eventId} , </if>" +
                " <if test=\"memberEvent.memberId != null\"> member_id = #{memberEvent.memberId} , </if>" +
                " <if test=\"memberEvent.type != null\"> type = #{memberEvent.type} , </if>" +
                " <if test=\"memberEvent.title != null\"> title = #{memberEvent.title} , </if>" +
                " <if test=\"memberEvent.content != null\"> content = #{memberEvent.content} , </if>" +
                " <if test=\"memberEvent.eventDate != null\"> event_date = #{memberEvent.eventDate} , </if>" +
                " <if test=\"memberEvent.remark != null\"> REMARK = #{memberEvent.remark} , </if>" +
                " modified = now() " +
            " WHERE event_id = #{memberEvent.eventId} " +
            "</script>")
    int update(@Param("memberEvent") MemberEventEntity memberEvent);

    @Update("<script> DELETE  FROM member_event " +
            " WHERE event_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


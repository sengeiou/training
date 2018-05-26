package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * member_medal 数据库操作类
 * Created by huai23 on 2018-05-26 13:54:40.
 */ 
@Mapper
public interface MemberMedalRepository {

    @Insert("<script> INSERT INTO member_medal ( " +
                " <if test=\"memberMedal.memberId != null\"> member_id, </if>" +
                " <if test=\"memberMedal.medalId != null\"> medal_id, </if>" +
                " <if test=\"memberMedal.content != null\"> content, </if>" +
                " <if test=\"memberMedal.awardDate != null\"> award_date, </if>" +
                " <if test=\"memberMedal.feature != null\"> feature, </if>" +
                " <if test=\"memberMedal.remark != null\"> remark, </if>" +
                " <if test=\"memberMedal.status != null\"> status, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"memberMedal.memberId != null\"> #{memberMedal.memberId}, </if>" +
                " <if test=\"memberMedal.medalId != null\"> #{memberMedal.medalId}, </if>" +
                " <if test=\"memberMedal.content != null\"> #{memberMedal.content}, </if>" +
                " <if test=\"memberMedal.awardDate != null\"> #{memberMedal.awardDate}, </if>" +
                " <if test=\"memberMedal.feature != null\"> #{memberMedal.feature}, </if>" +
                " <if test=\"memberMedal.remark != null\"> #{memberMedal.remark}, </if>" +
                " <if test=\"memberMedal.status != null\"> #{memberMedal.status}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("memberMedal") MemberMedalEntity memberMedal);

    @Select("<script> SELECT pk_id,member_id,medal_id,content,award_date,feature,remark,status,created,modified " +
            " FROM member_medal " +
            " WHERE 1 = 1 " +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.medalId != null\"> AND medal_id = #{query.medalId} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.awardDate != null\"> AND award_date = #{query.awardDate} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<MemberMedalEntity> find(@Param("query") MemberMedalQuery memberMedal , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM member_medal " +
            " WHERE 1 = 1 " +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.medalId != null\"> AND medal_id = #{query.medalId} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.awardDate != null\"> AND award_date = #{query.awardDate} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            "</script>")
    Long count(@Param("query") MemberMedalQuery memberMedal);

    @Select("<script> SELECT pk_id,member_id,medal_id,content,award_date,feature,remark,status,created,modified " +
            " FROM member_medal " +
            " WHERE member_id = #{id} " +
            "</script>")
    MemberMedalEntity getById(@Param("id") String id);

    @Update("<script> UPDATE member_medal SET " +
                " <if test=\"memberMedal.memberId != null\"> member_id = #{memberMedal.memberId} , </if>" +
                " <if test=\"memberMedal.medalId != null\"> medal_id = #{memberMedal.medalId} , </if>" +
                " <if test=\"memberMedal.content != null\"> content = #{memberMedal.content} , </if>" +
                " <if test=\"memberMedal.awardDate != null\"> award_date = #{memberMedal.awardDate} , </if>" +
                " <if test=\"memberMedal.feature != null\"> feature = #{memberMedal.feature} , </if>" +
                " <if test=\"memberMedal.remark != null\"> remark = #{memberMedal.remark} , </if>" +
                " <if test=\"memberMedal.status != null\"> status = #{memberMedal.status} , </if>" +
                " modified = now() " +
            " WHERE member_id = #{memberMedal.memberId} " +
            "</script>")
    int update(@Param("memberMedal") MemberMedalEntity memberMedal);

    @Update("<script> DELETE  FROM member_medal " +
            " WHERE member_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


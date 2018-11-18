package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * member_pause 数据库操作类
 * Created by huai23 on 2018-06-27 20:52:15.
 */ 
@Mapper
public interface MemberPauseRepository {

    @Insert("<script> INSERT INTO member_pause ( " +
                " <if test=\"memberPause.memberId != null\"> member_id, </if>" +
                " <if test=\"memberPause.cardNo != null\"> card_no, </if>" +
                " <if test=\"memberPause.pauseDate != null\"> pause_date, </if>" +
                " <if test=\"memberPause.pauseStaffId != null\"> pause_staff_id, </if>" +
                " <if test=\"memberPause.restoreDate != null\"> restore_date, </if>" +
                " <if test=\"memberPause.restoreStaffId != null\"> restore_staff_id, </if>" +
                " <if test=\"memberPause.status != null\"> status, </if>" +
                " <if test=\"memberPause.creater != null\"> creater, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"memberPause.memberId != null\"> #{memberPause.memberId}, </if>" +
                " <if test=\"memberPause.cardNo != null\"> #{memberPause.cardNo},  </if>" +
                " <if test=\"memberPause.pauseDate != null\"> #{memberPause.pauseDate}, </if>" +
                " <if test=\"memberPause.pauseStaffId != null\"> #{memberPause.pauseStaffId}, </if>" +
                " <if test=\"memberPause.restoreDate != null\"> #{memberPause.restoreDate}, </if>" +
                " <if test=\"memberPause.restoreStaffId != null\"> #{memberPause.restoreStaffId}, </if>" +
                " <if test=\"memberPause.status != null\"> #{memberPause.status}, </if>" +
                " <if test=\"memberPause.creater != null\"> #{memberPause.creater}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("memberPause") MemberPauseEntity memberPause);

    @Select("<script> SELECT pk_id,member_id,card_no,pause_date,pause_staff_id,restore_date,restore_staff_id,status,creater,created,modified " +
            " FROM member_pause " +
            " WHERE 1 = 1 " +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.cardNo != null\"> AND card_no = #{query.cardNo} </if>" +
            " <if test=\"query.pauseDate != null\"> AND pause_date = #{query.pauseDate} </if>" +
            " <if test=\"query.pauseStaffId != null\"> AND pause_staff_id = #{query.pauseStaffId} </if>" +
            " <if test=\"query.restoreDate != null\"> AND restore_date = #{query.restoreDate} </if>" +
            " <if test=\"query.restoreStaffId != null\"> AND restore_staff_id = #{query.restoreStaffId} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.creater != null\"> AND creater = #{query.creater} </if>" +
            " <if test=\"query.startDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d')  &gt;= #{query.startDate}  </if>" +
            " <if test=\"query.endDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d')  &lt;= #{query.endDate} </if>" +
            " order by pk_id desc LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<MemberPauseEntity> find(@Param("query") MemberPauseQuery memberPause , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM member_pause " +
            " WHERE 1 = 1 " +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.cardNo != null\"> AND card_no = #{query.cardNo} </if>" +
            " <if test=\"query.pauseDate != null\"> AND pause_date = #{query.pauseDate} </if>" +
            " <if test=\"query.pauseStaffId != null\"> AND pause_staff_id = #{query.pauseStaffId} </if>" +
            " <if test=\"query.restoreDate != null\"> AND restore_date = #{query.restoreDate} </if>" +
            " <if test=\"query.restoreStaffId != null\"> AND restore_staff_id = #{query.restoreStaffId} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.creater != null\"> AND creater = #{query.creater} </if>" +
            " <if test=\"query.startDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d')  &gt;= #{query.startDate}  </if>" +
            " <if test=\"query.endDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d')  &lt;= #{query.endDate} </if>" +
            "</script>")
    Long count(@Param("query") MemberPauseQuery memberPause);

    @Select("<script> SELECT pk_id,member_id,card_no,pause_date,pause_staff_id,restore_date,restore_staff_id,status,creater,created,modified " +
            " FROM member_pause " +
            " WHERE member_id = #{id} " +
            "</script>")
    MemberPauseEntity getById(@Param("id") String id);

    @Update("<script> UPDATE member_pause SET " +
                " <if test=\"memberPause.restoreDate != null\"> restore_date = #{memberPause.restoreDate} , </if>" +
                " <if test=\"memberPause.restoreStaffId != null\"> restore_staff_id = #{memberPause.restoreStaffId} , </if>" +
                " <if test=\"memberPause.status != null\"> status = #{memberPause.status} , </if>" +
                " modified = now() " +
            " WHERE member_id = #{memberPause.memberId} " +
            "</script>")
    int update(@Param("memberPause") MemberPauseEntity memberPause);

    @Update("<script> DELETE  FROM member_pause " +
            " WHERE member_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


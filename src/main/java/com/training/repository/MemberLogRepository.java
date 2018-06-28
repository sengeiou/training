package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * member_log 数据库操作类
 * Created by huai23 on 2018-06-28 23:16:57.
 */ 
@Mapper
public interface MemberLogRepository {

    @Insert("<script> INSERT INTO member_log ( " +
                " <if test=\"memberLog.logId != null\"> LOG_ID, </if>" +
                " <if test=\"memberLog.type != null\"> TYPE, </if>" +
                " <if test=\"memberLog.content != null\"> content, </if>" +
                " <if test=\"memberLog.memberId != null\"> member_id, </if>" +
                " <if test=\"memberLog.staffId != null\"> staff_id, </if>" +
                " <if test=\"memberLog.storeId != null\"> store_id, </if>" +
                " <if test=\"memberLog.remark != null\"> REMARK, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"memberLog.logId != null\"> #{memberLog.logId}, </if>" +
                " <if test=\"memberLog.type != null\"> #{memberLog.type}, </if>" +
                " <if test=\"memberLog.content != null\"> #{memberLog.content}, </if>" +
                " <if test=\"memberLog.memberId != null\"> #{memberLog.memberId}, </if>" +
                " <if test=\"memberLog.staffId != null\"> #{memberLog.staffId}, </if>" +
                " <if test=\"memberLog.storeId != null\"> #{memberLog.storeId}, </if>" +
                " <if test=\"memberLog.remark != null\"> #{memberLog.remark}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("memberLog") MemberLogEntity memberLog);

    @Select("<script> SELECT pk_id,LOG_ID,TYPE,content,member_id,staff_id,store_id,REMARK,created,modified " +
            " FROM member_log " +
            " WHERE 1 = 1 " +
            " <if test=\"query.logId != null\"> AND LOG_ID = #{query.logId} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            " order by pk_id desc LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<MemberLogEntity> find(@Param("query") MemberLogQuery memberLog , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM member_log " +
            " WHERE 1 = 1 " +
            " <if test=\"query.logId != null\"> AND LOG_ID = #{query.logId} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            "</script>")
    Long count(@Param("query") MemberLogQuery memberLog);

    @Select("<script> SELECT pk_id,LOG_ID,TYPE,content,member_id,staff_id,store_id,REMARK,created,modified " +
            " FROM member_log " +
            " WHERE LOG_ID = #{id} " +
            "</script>")
    MemberLogEntity getById(@Param("id") String id);

    @Update("<script> UPDATE member_log SET " +
                " <if test=\"memberLog.logId != null\"> LOG_ID = #{memberLog.logId} , </if>" +
                " <if test=\"memberLog.type != null\"> TYPE = #{memberLog.type} , </if>" +
                " <if test=\"memberLog.content != null\"> content = #{memberLog.content} , </if>" +
                " <if test=\"memberLog.memberId != null\"> member_id = #{memberLog.memberId} , </if>" +
                " <if test=\"memberLog.staffId != null\"> staff_id = #{memberLog.staffId} , </if>" +
                " <if test=\"memberLog.storeId != null\"> store_id = #{memberLog.storeId} , </if>" +
                " <if test=\"memberLog.remark != null\"> REMARK = #{memberLog.remark} , </if>" +
                " modified = now() " +
            " WHERE LOG_ID = #{memberLog.logId} " +
            "</script>")
    int update(@Param("memberLog") MemberLogEntity memberLog);

    @Update("<script> DELETE  FROM member_log " +
            " WHERE LOG_ID = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


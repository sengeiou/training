package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * sys_log 数据库操作类
 * Created by huai23 on 2018-06-03 15:57:51.
 */ 
@Mapper
public interface SysLogRepository {

    @Insert("<script> INSERT INTO sys_log ( " +
                " <if test=\"sysLog.logId != null\"> LOG_ID, </if>" +
                " <if test=\"sysLog.type != null\"> TYPE, </if>" +
                " <if test=\"sysLog.level != null\"> LEVEL, </if>" +
                " <if test=\"sysLog.logText != null\"> log_text, </if>" +
                " <if test=\"sysLog.content != null\"> content, </if>" +
                " <if test=\"sysLog.remark != null\"> REMARK, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"sysLog.logId != null\"> #{sysLog.logId}, </if>" +
                " <if test=\"sysLog.type != null\"> #{sysLog.type}, </if>" +
                " <if test=\"sysLog.level != null\"> #{sysLog.level}, </if>" +
                " <if test=\"sysLog.logText != null\"> #{sysLog.logText}, </if>" +
                " <if test=\"sysLog.content != null\"> #{sysLog.content}, </if>" +
                " <if test=\"sysLog.remark != null\"> #{sysLog.remark}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("sysLog") SysLogEntity sysLog);

    @Select("<script> SELECT pk_id,LOG_ID,TYPE,LEVEL,log_text,content,REMARK,created,modified " +
            " FROM sys_log " +
            " WHERE 1 = 1 " +
            " <if test=\"query.logId != null\"> AND LOG_ID = #{query.logId} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.level != null\"> AND LEVEL = #{query.level} </if>" +
            " <if test=\"query.logText != null\"> AND log_text = #{query.logText} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<SysLogEntity> find(@Param("query") SysLogQuery sysLog , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM sys_log " +
            " WHERE 1 = 1 " +
            " <if test=\"query.logId != null\"> AND LOG_ID = #{query.logId} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.level != null\"> AND LEVEL = #{query.level} </if>" +
            " <if test=\"query.logText != null\"> AND log_text = #{query.logText} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            "</script>")
    Long count(@Param("query") SysLogQuery sysLog);

    @Select("<script> SELECT pk_id,LOG_ID,TYPE,LEVEL,log_text,content,REMARK,created,modified " +
            " FROM sys_log " +
            " WHERE LOG_ID = #{id} " +
            "</script>")
    SysLogEntity getById(@Param("id") String id);

    @Update("<script> UPDATE sys_log SET " +
                " <if test=\"sysLog.logId != null\"> LOG_ID = #{sysLog.logId} , </if>" +
                " <if test=\"sysLog.type != null\"> TYPE = #{sysLog.type} , </if>" +
                " <if test=\"sysLog.level != null\"> LEVEL = #{sysLog.level} , </if>" +
                " <if test=\"sysLog.logText != null\"> log_text = #{sysLog.logText} , </if>" +
                " <if test=\"sysLog.content != null\"> content = #{sysLog.content} , </if>" +
                " <if test=\"sysLog.remark != null\"> REMARK = #{sysLog.remark} , </if>" +
                " modified = now() " +
            " WHERE LOG_ID = #{sysLog.logId} " +
            "</script>")
    int update(@Param("sysLog") SysLogEntity sysLog);

    @Update("<script> DELETE  FROM sys_log " +
            " WHERE LOG_ID = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


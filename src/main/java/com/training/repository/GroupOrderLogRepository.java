package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * group_order_log 数据库操作类
 * Created by huai23 on 2019-01-30 23:16:13.
 */ 
@Mapper
public interface GroupOrderLogRepository {

    @Insert("<script> INSERT INTO group_order_log ( " +
                " <if test=\"groupOrderLog.logId != null\"> LOG_ID, </if>" +
                " <if test=\"groupOrderLog.orderId != null\"> order_id, </if>" +
                " <if test=\"groupOrderLog.type != null\"> TYPE, </if>" +
                " <if test=\"groupOrderLog.content != null\"> content, </if>" +
                " <if test=\"groupOrderLog.staffId != null\"> staff_id, </if>" +
                " <if test=\"groupOrderLog.storeId != null\"> store_id, </if>" +
                " <if test=\"groupOrderLog.remark != null\"> REMARK, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"groupOrderLog.logId != null\"> #{groupOrderLog.logId}, </if>" +
                " <if test=\"groupOrderLog.orderId != null\"> #{groupOrderLog.orderId}, </if>" +
                " <if test=\"groupOrderLog.type != null\"> #{groupOrderLog.type}, </if>" +
                " <if test=\"groupOrderLog.content != null\"> #{groupOrderLog.content}, </if>" +
                " <if test=\"groupOrderLog.staffId != null\"> #{groupOrderLog.staffId}, </if>" +
                " <if test=\"groupOrderLog.storeId != null\"> #{groupOrderLog.storeId}, </if>" +
                " <if test=\"groupOrderLog.remark != null\"> #{groupOrderLog.remark}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("groupOrderLog") GroupOrderLogEntity groupOrderLog);

    @Select("<script> SELECT pk_id,LOG_ID,order_id,TYPE,content,staff_id,store_id,REMARK,created,modified " +
            " FROM group_order_log " +
            " WHERE 1 = 1 " +
            " <if test=\"query.logId != null\"> AND LOG_ID = #{query.logId} </if>" +
            " <if test=\"query.orderId != null\"> AND order_id = #{query.orderId} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<GroupOrderLogEntity> find(@Param("query") GroupOrderLogQuery groupOrderLog , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM group_order_log " +
            " WHERE 1 = 1 " +
            " <if test=\"query.logId != null\"> AND LOG_ID = #{query.logId} </if>" +
            " <if test=\"query.orderId != null\"> AND order_id = #{query.orderId} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            "</script>")
    Long count(@Param("query") GroupOrderLogQuery groupOrderLog);

    @Select("<script> SELECT pk_id,LOG_ID,order_id,TYPE,content,staff_id,store_id,REMARK,created,modified " +
            " FROM group_order_log " +
            " WHERE LOG_ID = #{id} " +
            "</script>")
    GroupOrderLogEntity getById(@Param("id") String id);

    @Update("<script> UPDATE group_order_log SET " +
                " <if test=\"groupOrderLog.logId != null\"> LOG_ID = #{groupOrderLog.logId} , </if>" +
                " <if test=\"groupOrderLog.orderId != null\"> order_id = #{groupOrderLog.orderId} , </if>" +
                " <if test=\"groupOrderLog.type != null\"> TYPE = #{groupOrderLog.type} , </if>" +
                " <if test=\"groupOrderLog.content != null\"> content = #{groupOrderLog.content} , </if>" +
                " <if test=\"groupOrderLog.staffId != null\"> staff_id = #{groupOrderLog.staffId} , </if>" +
                " <if test=\"groupOrderLog.storeId != null\"> store_id = #{groupOrderLog.storeId} , </if>" +
                " <if test=\"groupOrderLog.remark != null\"> REMARK = #{groupOrderLog.remark} , </if>" +
                " modified = now() " +
            " WHERE LOG_ID = #{groupOrderLog.logId} " +
            "</script>")
    int update(@Param("groupOrderLog") GroupOrderLogEntity groupOrderLog);

    @Update("<script> DELETE  FROM group_order_log " +
            " WHERE LOG_ID = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


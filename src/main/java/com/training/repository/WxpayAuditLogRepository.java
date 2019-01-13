package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * wxpay_audit_log 数据库操作类
 * Created by huai23 on 2019-01-13 20:31:38.
 */ 
@Mapper
public interface WxpayAuditLogRepository {

    @Insert("<script> INSERT INTO wxpay_audit_log ( " +
                " <if test=\"wxpayAuditLog.transactionId != null\"> transaction_id, </if>" +
                " <if test=\"wxpayAuditLog.type != null\"> TYPE, </if>" +
                " <if test=\"wxpayAuditLog.auditStaffId != null\"> audit_staff_id, </if>" +
                " <if test=\"wxpayAuditLog.auditTime != null\"> audit_time, </if>" +
                " <if test=\"wxpayAuditLog.feature != null\"> feature, </if>" +
                " <if test=\"wxpayAuditLog.remark != null\"> remark, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"wxpayAuditLog.transactionId != null\"> #{wxpayAuditLog.transactionId}, </if>" +
                " <if test=\"wxpayAuditLog.type != null\"> #{wxpayAuditLog.type}, </if>" +
                " <if test=\"wxpayAuditLog.auditStaffId != null\"> #{wxpayAuditLog.auditStaffId}, </if>" +
                " <if test=\"wxpayAuditLog.auditTime != null\"> #{wxpayAuditLog.auditTime}, </if>" +
                " <if test=\"wxpayAuditLog.feature != null\"> #{wxpayAuditLog.feature}, </if>" +
                " <if test=\"wxpayAuditLog.remark != null\"> #{wxpayAuditLog.remark}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("wxpayAuditLog") WxpayAuditLogEntity wxpayAuditLog);

    @Select("<script> SELECT pk_id,transaction_id,TYPE,audit_staff_id,audit_time,feature,remark,created,modified " +
            " FROM wxpay_audit_log " +
            " WHERE 1 = 1 " +
            " <if test=\"query.transactionId != null\"> AND transaction_id = #{query.transactionId} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.auditStaffId != null\"> AND audit_staff_id = #{query.auditStaffId} </if>" +
            " <if test=\"query.auditTime != null\"> AND audit_time = #{query.auditTime} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<WxpayAuditLogEntity> find(@Param("query") WxpayAuditLogQuery wxpayAuditLog , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM wxpay_audit_log " +
            " WHERE 1 = 1 " +
            " <if test=\"query.transactionId != null\"> AND transaction_id = #{query.transactionId} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.auditStaffId != null\"> AND audit_staff_id = #{query.auditStaffId} </if>" +
            " <if test=\"query.auditTime != null\"> AND audit_time = #{query.auditTime} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            "</script>")
    Long count(@Param("query") WxpayAuditLogQuery wxpayAuditLog);

    @Select("<script> SELECT pk_id,transaction_id,TYPE,audit_staff_id,audit_time,feature,remark,created,modified " +
            " FROM wxpay_audit_log " +
            " WHERE transaction_id = #{id} " +
            "</script>")
    WxpayAuditLogEntity getById(@Param("id") String id);

    @Update("<script> UPDATE wxpay_audit_log SET " +
                " <if test=\"wxpayAuditLog.transactionId != null\"> transaction_id = #{wxpayAuditLog.transactionId} , </if>" +
                " <if test=\"wxpayAuditLog.type != null\"> TYPE = #{wxpayAuditLog.type} , </if>" +
                " <if test=\"wxpayAuditLog.auditStaffId != null\"> audit_staff_id = #{wxpayAuditLog.auditStaffId} , </if>" +
                " <if test=\"wxpayAuditLog.auditTime != null\"> audit_time = #{wxpayAuditLog.auditTime} , </if>" +
                " <if test=\"wxpayAuditLog.feature != null\"> feature = #{wxpayAuditLog.feature} , </if>" +
                " <if test=\"wxpayAuditLog.remark != null\"> remark = #{wxpayAuditLog.remark} , </if>" +
                " modified = now() " +
            " WHERE transaction_id = #{wxpayAuditLog.transactionId} " +
            "</script>")
    int update(@Param("wxpayAuditLog") WxpayAuditLogEntity wxpayAuditLog);

    @Update("<script> DELETE  FROM wxpay_audit_log " +
            " WHERE transaction_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


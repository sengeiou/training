package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * kpi_quota 数据库操作类
 * Created by huai23 on 2018-07-09 22:42:44.
 */ 
@Mapper
public interface KpiQuotaRepository {

    @Insert("<script> INSERT INTO kpi_quota ( " +
                " <if test=\"kpiQuota.quotaId != null\"> quota_id, </if>" +
                " <if test=\"kpiQuota.type != null\"> type, </if>" +
                " <if test=\"kpiQuota.name != null\"> name, </if>" +
                " <if test=\"kpiQuota.content != null\"> content, </if>" +
                " <if test=\"kpiQuota.feature != null\"> feature, </if>" +
                " <if test=\"kpiQuota.remark != null\"> remark, </if>" +
                " <if test=\"kpiQuota.status != null\"> status, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"kpiQuota.quotaId != null\"> #{kpiQuota.quotaId}, </if>" +
                " <if test=\"kpiQuota.type != null\"> #{kpiQuota.type}, </if>" +
                " <if test=\"kpiQuota.name != null\"> #{kpiQuota.name}, </if>" +
                " <if test=\"kpiQuota.content != null\"> #{kpiQuota.content}, </if>" +
                " <if test=\"kpiQuota.feature != null\"> #{kpiQuota.feature}, </if>" +
                " <if test=\"kpiQuota.remark != null\"> #{kpiQuota.remark}, </if>" +
                " <if test=\"kpiQuota.status != null\"> #{kpiQuota.status}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("kpiQuota") KpiQuotaEntity kpiQuota);

    @Select("<script> SELECT pk_id,quota_id,type,name,content,feature,remark,status,created,modified " +
            " FROM kpi_quota " +
            " WHERE 1 = 1 " +
            " <if test=\"query.quotaId != null\"> AND quota_id = #{query.quotaId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.name != null\"> AND name = #{query.name} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<KpiQuotaEntity> find(@Param("query") KpiQuotaQuery kpiQuota , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM kpi_quota " +
            " WHERE 1 = 1 " +
            " <if test=\"query.quotaId != null\"> AND quota_id = #{query.quotaId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.name != null\"> AND name = #{query.name} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            "</script>")
    Long count(@Param("query") KpiQuotaQuery kpiQuota);

    @Select("<script> SELECT pk_id,quota_id,type,name,content,feature,remark,status,created,modified " +
            " FROM kpi_quota " +
            " WHERE quota_id = #{id} " +
            "</script>")
    KpiQuotaEntity getById(@Param("id") String id);

    @Update("<script> UPDATE kpi_quota SET " +
                " <if test=\"kpiQuota.quotaId != null\"> quota_id = #{kpiQuota.quotaId} , </if>" +
                " <if test=\"kpiQuota.type != null\"> type = #{kpiQuota.type} , </if>" +
                " <if test=\"kpiQuota.name != null\"> name = #{kpiQuota.name} , </if>" +
                " <if test=\"kpiQuota.content != null\"> content = #{kpiQuota.content} , </if>" +
                " <if test=\"kpiQuota.feature != null\"> feature = #{kpiQuota.feature} , </if>" +
                " <if test=\"kpiQuota.remark != null\"> remark = #{kpiQuota.remark} , </if>" +
                " <if test=\"kpiQuota.status != null\"> status = #{kpiQuota.status} , </if>" +
                " modified = now() " +
            " WHERE quota_id = #{kpiQuota.quotaId} " +
            "</script>")
    int update(@Param("kpiQuota") KpiQuotaEntity kpiQuota);

    @Update("<script> DELETE  FROM kpi_quota " +
            " WHERE quota_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


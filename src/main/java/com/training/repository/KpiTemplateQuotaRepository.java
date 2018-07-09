package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * kpi_template_quota 数据库操作类
 * Created by huai23 on 2018-07-09 22:42:58.
 */ 
@Mapper
public interface KpiTemplateQuotaRepository {

    @Insert("<script> INSERT INTO kpi_template_quota ( " +
                " <if test=\"kpiTemplateQuota.templateId != null\"> template_id, </if>" +
                " <if test=\"kpiTemplateQuota.quotaId != null\"> quota_id, </if>" +
                " <if test=\"kpiTemplateQuota.type != null\"> type, </if>" +
                " <if test=\"kpiTemplateQuota.name != null\"> name, </if>" +
                " <if test=\"kpiTemplateQuota.weight != null\"> weight, </if>" +
                " <if test=\"kpiTemplateQuota.standard != null\"> standard, </if>" +
                " <if test=\"kpiTemplateQuota.score != null\"> score, </if>" +
                " <if test=\"kpiTemplateQuota.content != null\"> content, </if>" +
                " <if test=\"kpiTemplateQuota.feature != null\"> feature, </if>" +
                " <if test=\"kpiTemplateQuota.remark != null\"> remark, </if>" +
                " <if test=\"kpiTemplateQuota.status != null\"> status, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"kpiTemplateQuota.templateId != null\"> #{kpiTemplateQuota.templateId}, </if>" +
                " <if test=\"kpiTemplateQuota.quotaId != null\"> #{kpiTemplateQuota.quotaId}, </if>" +
                " <if test=\"kpiTemplateQuota.type != null\"> #{kpiTemplateQuota.type}, </if>" +
                " <if test=\"kpiTemplateQuota.name != null\"> #{kpiTemplateQuota.name}, </if>" +
                " <if test=\"kpiTemplateQuota.weight != null\"> #{kpiTemplateQuota.weight}, </if>" +
                " <if test=\"kpiTemplateQuota.standard != null\"> #{kpiTemplateQuota.standard}, </if>" +
                " <if test=\"kpiTemplateQuota.score != null\"> #{kpiTemplateQuota.score}, </if>" +
                " <if test=\"kpiTemplateQuota.content != null\"> #{kpiTemplateQuota.content}, </if>" +
                " <if test=\"kpiTemplateQuota.feature != null\"> #{kpiTemplateQuota.feature}, </if>" +
                " <if test=\"kpiTemplateQuota.remark != null\"> #{kpiTemplateQuota.remark}, </if>" +
                " <if test=\"kpiTemplateQuota.status != null\"> #{kpiTemplateQuota.status}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("kpiTemplateQuota") KpiTemplateQuotaEntity kpiTemplateQuota);

    @Select("<script> SELECT pk_id,template_id,quota_id,type,name,weight,standard,score,content,feature,remark,status,created,modified " +
            " FROM kpi_template_quota " +
            " WHERE 1 = 1 " +
            " <if test=\"query.templateId != null\"> AND template_id = #{query.templateId} </if>" +
            " <if test=\"query.quotaId != null\"> AND quota_id = #{query.quotaId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.name != null\"> AND name = #{query.name} </if>" +
            " <if test=\"query.weight != null\"> AND weight = #{query.weight} </if>" +
            " <if test=\"query.standard != null\"> AND standard = #{query.standard} </if>" +
            " <if test=\"query.score != null\"> AND score = #{query.score} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<KpiTemplateQuotaEntity> find(@Param("query") KpiTemplateQuotaQuery kpiTemplateQuota , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM kpi_template_quota " +
            " WHERE 1 = 1 " +
            " <if test=\"query.templateId != null\"> AND template_id = #{query.templateId} </if>" +
            " <if test=\"query.quotaId != null\"> AND quota_id = #{query.quotaId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.name != null\"> AND name = #{query.name} </if>" +
            " <if test=\"query.weight != null\"> AND weight = #{query.weight} </if>" +
            " <if test=\"query.standard != null\"> AND standard = #{query.standard} </if>" +
            " <if test=\"query.score != null\"> AND score = #{query.score} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            "</script>")
    Long count(@Param("query") KpiTemplateQuotaQuery kpiTemplateQuota);

    @Select("<script> SELECT pk_id,template_id,quota_id,type,name,weight,standard,score,content,feature,remark,status,created,modified " +
            " FROM kpi_template_quota " +
            " WHERE template_id = #{templateId} and quota_id = #{quotaId}  " +
            "</script>")
    KpiTemplateQuotaEntity getById(@Param("templateId") String templateId,@Param("quotaId") String quotaId);

    @Update("<script> UPDATE kpi_template_quota SET " +
                " <if test=\"kpiTemplateQuota.type != null\"> type = #{kpiTemplateQuota.type} , </if>" +
                " <if test=\"kpiTemplateQuota.name != null\"> name = #{kpiTemplateQuota.name} , </if>" +
                " <if test=\"kpiTemplateQuota.weight != null\"> weight = #{kpiTemplateQuota.weight} , </if>" +
                " <if test=\"kpiTemplateQuota.standard != null\"> standard = #{kpiTemplateQuota.standard} , </if>" +
                " <if test=\"kpiTemplateQuota.score != null\"> score = #{kpiTemplateQuota.score} , </if>" +
                " <if test=\"kpiTemplateQuota.content != null\"> content = #{kpiTemplateQuota.content} , </if>" +
                " <if test=\"kpiTemplateQuota.feature != null\"> feature = #{kpiTemplateQuota.feature} , </if>" +
                " <if test=\"kpiTemplateQuota.remark != null\"> remark = #{kpiTemplateQuota.remark} , </if>" +
                " <if test=\"kpiTemplateQuota.status != null\"> status = #{kpiTemplateQuota.status} , </if>" +
                " modified = now() " +
            " WHERE template_id = #{kpiTemplateQuota.templateId} and quota_id = #{kpiTemplateQuota.quotaId}  " +
            "</script>")
    int update(@Param("kpiTemplateQuota") KpiTemplateQuotaEntity kpiTemplateQuota);

    @Update("<script> DELETE  FROM kpi_template_quota " +
            " WHERE template_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


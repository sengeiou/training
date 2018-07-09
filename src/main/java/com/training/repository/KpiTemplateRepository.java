package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * kpi_template 数据库操作类
 * Created by huai23 on 2018-07-09 22:42:32.
 */ 
@Mapper
public interface KpiTemplateRepository {

    @Insert("<script> INSERT INTO kpi_template ( " +
                " <if test=\"kpiTemplate.templateId != null\"> template_id, </if>" +
                " <if test=\"kpiTemplate.type != null\"> type, </if>" +
                " <if test=\"kpiTemplate.title != null\"> title, </if>" +
                " <if test=\"kpiTemplate.remark != null\"> remark, </if>" +
                " <if test=\"kpiTemplate.status != null\"> status, </if>" +
                " <if test=\"kpiTemplate.creator != null\"> creator, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"kpiTemplate.templateId != null\"> #{kpiTemplate.templateId}, </if>" +
                " <if test=\"kpiTemplate.type != null\"> #{kpiTemplate.type}, </if>" +
                " <if test=\"kpiTemplate.title != null\"> #{kpiTemplate.title}, </if>" +
                " <if test=\"kpiTemplate.remark != null\"> #{kpiTemplate.remark}, </if>" +
                " <if test=\"kpiTemplate.status != null\"> #{kpiTemplate.status}, </if>" +
                " <if test=\"kpiTemplate.creator != null\"> #{kpiTemplate.creator}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("kpiTemplate") KpiTemplateEntity kpiTemplate);

    @Select("<script> SELECT pk_id,template_id,type,title,remark,status,creator,created,modified " +
            " FROM kpi_template " +
            " WHERE 1 = 1 " +
            " <if test=\"query.templateId != null\"> AND template_id = #{query.templateId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.title != null\"> AND title = #{query.title} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.creator != null\"> AND creator = #{query.creator} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<KpiTemplateEntity> find(@Param("query") KpiTemplateQuery kpiTemplate , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM kpi_template " +
            " WHERE 1 = 1 " +
            " <if test=\"query.templateId != null\"> AND template_id = #{query.templateId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.title != null\"> AND title = #{query.title} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.creator != null\"> AND creator = #{query.creator} </if>" +
            "</script>")
    Long count(@Param("query") KpiTemplateQuery kpiTemplate);

    @Select("<script> SELECT pk_id,template_id,type,title,remark,status,creator,created,modified " +
            " FROM kpi_template " +
            " WHERE template_id = #{id} " +
            "</script>")
    KpiTemplateEntity getById(@Param("id") String id);

    @Update("<script> UPDATE kpi_template SET " +
                " <if test=\"kpiTemplate.templateId != null\"> template_id = #{kpiTemplate.templateId} , </if>" +
                " <if test=\"kpiTemplate.type != null\"> type = #{kpiTemplate.type} , </if>" +
                " <if test=\"kpiTemplate.title != null\"> title = #{kpiTemplate.title} , </if>" +
                " <if test=\"kpiTemplate.remark != null\"> remark = #{kpiTemplate.remark} , </if>" +
                " <if test=\"kpiTemplate.status != null\"> status = #{kpiTemplate.status} , </if>" +
                " <if test=\"kpiTemplate.creator != null\"> creator = #{kpiTemplate.creator} , </if>" +
                " modified = now() " +
            " WHERE template_id = #{kpiTemplate.templateId} " +
            "</script>")
    int update(@Param("kpiTemplate") KpiTemplateEntity kpiTemplate);

    @Update("<script> DELETE  FROM kpi_template " +
            " WHERE template_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


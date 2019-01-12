package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * origin 数据库操作类
 * Created by huai23 on 2019-01-12 13:23:55.
 */ 
@Mapper
public interface OriginRepository {

    @Insert("<script> INSERT INTO origin ( " +
                " <if test=\"origin.originId != null\"> origin_id, </if>" +
                " <if test=\"origin.code != null\"> code, </if>" +
                " <if test=\"origin.name != null\"> name, </if>" +
                " <if test=\"origin.type != null\"> type, </if>" +
                " <if test=\"origin.image != null\"> image, </if>" +
                " <if test=\"origin.feature != null\"> feature, </if>" +
                " <if test=\"origin.remark != null\"> remark, </if>" +
                " <if test=\"origin.status != null\"> status, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"origin.originId != null\"> #{origin.originId}, </if>" +
                " <if test=\"origin.code != null\"> #{origin.code}, </if>" +
                " <if test=\"origin.name != null\"> #{origin.name}, </if>" +
                " <if test=\"origin.type != null\"> #{origin.type}, </if>" +
                " <if test=\"origin.image != null\"> #{origin.image}, </if>" +
                " <if test=\"origin.feature != null\"> #{origin.feature}, </if>" +
                " <if test=\"origin.remark != null\"> #{origin.remark}, </if>" +
                " <if test=\"origin.status != null\"> #{origin.status}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("origin") OriginEntity origin);

    @Select("<script> SELECT origin_id,code,name,type,image,feature,remark,status,created,modified " +
            " FROM origin " +
            " WHERE 1 = 1 " +
            " <if test=\"query.originId != null\"> AND origin_id = #{query.originId} </if>" +
            " <if test=\"query.code != null\"> AND code = #{query.code} </if>" +
            " <if test=\"query.name != null\"> AND name = #{query.name} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.image != null\"> AND image = #{query.image} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<OriginEntity> find(@Param("query") OriginQuery origin , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM origin " +
            " WHERE 1 = 1 " +
            " <if test=\"query.originId != null\"> AND origin_id = #{query.originId} </if>" +
            " <if test=\"query.code != null\"> AND code = #{query.code} </if>" +
            " <if test=\"query.name != null\"> AND name = #{query.name} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.image != null\"> AND image = #{query.image} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            "</script>")
    Long count(@Param("query") OriginQuery origin);

    @Select("<script> SELECT origin_id,code,name,type,image,feature,remark,status,created,modified " +
            " FROM origin " +
            " WHERE origin_id = #{id} " +
            "</script>")
    OriginEntity getById(@Param("id") String id);

    @Update("<script> UPDATE origin SET " +
                " <if test=\"origin.originId != null\"> origin_id = #{origin.originId} , </if>" +
                " <if test=\"origin.code != null\"> code = #{origin.code} , </if>" +
                " <if test=\"origin.name != null\"> name = #{origin.name} , </if>" +
                " <if test=\"origin.type != null\"> type = #{origin.type} , </if>" +
                " <if test=\"origin.image != null\"> image = #{origin.image} , </if>" +
                " <if test=\"origin.feature != null\"> feature = #{origin.feature} , </if>" +
                " <if test=\"origin.remark != null\"> remark = #{origin.remark} , </if>" +
                " <if test=\"origin.status != null\"> status = #{origin.status} , </if>" +
                " modified = now() " +
            " WHERE origin_id = #{origin.originId} " +
            "</script>")
    int update(@Param("origin") OriginEntity origin);

    @Update("<script> DELETE  FROM origin " +
            " WHERE origin_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


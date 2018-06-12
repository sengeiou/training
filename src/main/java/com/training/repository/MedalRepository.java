package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * medal 数据库操作类
 * Created by huai23 on 2018-05-26 13:54:27.
 */ 
@Mapper
public interface MedalRepository {

    @Insert("<script> INSERT INTO medal ( " +
                " <if test=\"medal.medalId != null\"> medal_id, </if>" +
                " <if test=\"medal.name != null\"> name, </if>" +
                " <if test=\"medal.type != null\"> type, </if>" +
                " <if test=\"medal.content != null\"> content, </if>" +
                " <if test=\"medal.desc != null\"> `desc`, </if>" +
                " <if test=\"medal.image != null\"> image, </if>" +
                " <if test=\"medal.feature != null\"> feature, </if>" +
                " <if test=\"medal.remark != null\"> remark, </if>" +
                " <if test=\"medal.status != null\"> status, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"medal.medalId != null\"> #{medal.medalId}, </if>" +
                " <if test=\"medal.name != null\"> #{medal.name}, </if>" +
                " <if test=\"medal.type != null\"> #{medal.type}, </if>" +
                " <if test=\"medal.content != null\"> #{medal.content}, </if>" +
                " <if test=\"medal.desc != null\"> #{medal.desc}, </if>" +
                " <if test=\"medal.image != null\"> #{medal.image}, </if>" +
                " <if test=\"medal.feature != null\"> #{medal.feature}, </if>" +
                " <if test=\"medal.remark != null\"> #{medal.remark}, </if>" +
                " <if test=\"medal.status != null\"> #{medal.status}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("medal") MedalEntity medal);

    @Select("<script> SELECT pk_id,medal_id,name,type,content,`desc`,image,feature,remark,status,created,modified " +
            " FROM medal " +
            " WHERE 1 = 1 " +
            " <if test=\"query.medalId != null\"> AND medal_id = #{query.medalId} </if>" +
            " <if test=\"query.name != null\"> AND name = #{query.name} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.desc != null\"> AND `desc` = #{query.desc} </if>" +
            " <if test=\"query.image != null\"> AND image = #{query.image} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<MedalEntity> find(@Param("query") MedalQuery medal , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM medal " +
            " WHERE 1 = 1 " +
            " <if test=\"query.medalId != null\"> AND medal_id = #{query.medalId} </if>" +
            " <if test=\"query.name != null\"> AND name = #{query.name} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.desc != null\"> AND `desc` = #{query.desc} </if>" +
            " <if test=\"query.image != null\"> AND image = #{query.image} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            "</script>")
    Long count(@Param("query") MedalQuery medal);

    @Select("<script> SELECT pk_id,medal_id,name,type,content,`desc`,image,feature,remark,status,created,modified " +
            " FROM medal " +
            " WHERE medal_id = #{id} " +
            "</script>")
    MedalEntity getById(@Param("id") String id);

    @Update("<script> UPDATE medal SET " +
                " <if test=\"medal.medalId != null\"> medal_id = #{medal.medalId} , </if>" +
                " <if test=\"medal.name != null\"> name = #{medal.name} , </if>" +
                " <if test=\"medal.type != null\"> type = #{medal.type} , </if>" +
                " <if test=\"medal.content != null\"> content = #{medal.content} , </if>" +
                " <if test=\"medal.desc != null\"> `desc` = #{medal.desc} , </if>" +
                " <if test=\"medal.image != null\"> image = #{medal.image} , </if>" +
                " <if test=\"medal.feature != null\"> feature = #{medal.feature} , </if>" +
                " <if test=\"medal.remark != null\"> remark = #{medal.remark} , </if>" +
                " <if test=\"medal.status != null\"> status = #{medal.status} , </if>" +
                " modified = now() " +
            " WHERE medal_id = #{medal.medalId} " +
            "</script>")
    int update(@Param("medal") MedalEntity medal);

    @Update("<script> DELETE  FROM medal " +
            " WHERE medal_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


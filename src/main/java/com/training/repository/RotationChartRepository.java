package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * rotation_chart 数据库操作类
 * Created by huai23 on 2018-11-25 10:40:37.
 */ 
@Mapper
public interface RotationChartRepository {

    @Insert("<script> INSERT INTO rotation_chart ( " +
                " <if test=\"rotationChart.chartId != null\"> chart_id, </if>" +
                " <if test=\"rotationChart.title != null\"> title, </if>" +
                " <if test=\"rotationChart.image != null\"> image, </if>" +
                " <if test=\"rotationChart.url != null\"> url, </if>" +
                " <if test=\"rotationChart.content != null\"> content, </if>" +
                " <if test=\"rotationChart.sort != null\"> sort, </if>" +
                " <if test=\"rotationChart.remark != null\"> remark, </if>" +
                " <if test=\"rotationChart.status != null\"> status, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"rotationChart.chartId != null\"> #{rotationChart.chartId}, </if>" +
                " <if test=\"rotationChart.title != null\"> #{rotationChart.title}, </if>" +
                " <if test=\"rotationChart.image != null\"> #{rotationChart.image}, </if>" +
                " <if test=\"rotationChart.url != null\"> #{rotationChart.url}, </if>" +
                " <if test=\"rotationChart.content != null\"> #{rotationChart.content}, </if>" +
                " <if test=\"rotationChart.sort != null\"> #{rotationChart.sort}, </if>" +
                " <if test=\"rotationChart.remark != null\"> #{rotationChart.remark}, </if>" +
                " <if test=\"rotationChart.status != null\"> #{rotationChart.status}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("rotationChart") RotationChartEntity rotationChart);

    @Select("<script> SELECT pk_id,chart_id,title,image,url,content,sort,remark,status,created,modified " +
            " FROM rotation_chart " +
            " WHERE 1 = 1 " +
            " <if test=\"query.chartId != null\"> AND chart_id = #{query.chartId} </if>" +
            " <if test=\"query.title != null\"> AND title = #{query.title} </if>" +
            " <if test=\"query.image != null\"> AND image = #{query.image} </if>" +
            " <if test=\"query.url != null\"> AND url = #{query.url} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.sort != null\"> AND index = #{query.sort} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " order by index LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<RotationChartEntity> find(@Param("query") RotationChartQuery rotationChart , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM rotation_chart " +
            " WHERE 1 = 1 " +
            " <if test=\"query.chartId != null\"> AND chart_id = #{query.chartId} </if>" +
            " <if test=\"query.title != null\"> AND title = #{query.title} </if>" +
            " <if test=\"query.image != null\"> AND image = #{query.image} </if>" +
            " <if test=\"query.url != null\"> AND url = #{query.url} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.sort != null\"> AND index = #{query.sort} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            "</script>")
    Long count(@Param("query") RotationChartQuery rotationChart);

    @Select("<script> SELECT pk_id,chart_id,title,image,url,content,sort,remark,status,created,modified " +
            " FROM rotation_chart " +
            " WHERE chart_id = #{id} " +
            "</script>")
    RotationChartEntity getById(@Param("id") String id);

    @Update("<script> UPDATE rotation_chart SET " +
                " <if test=\"rotationChart.chartId != null\"> chart_id = #{rotationChart.chartId} , </if>" +
                " <if test=\"rotationChart.title != null\"> title = #{rotationChart.title} , </if>" +
                " <if test=\"rotationChart.image != null\"> image = #{rotationChart.image} , </if>" +
                " <if test=\"rotationChart.url != null\"> url = #{rotationChart.url} , </if>" +
                " <if test=\"rotationChart.content != null\"> content = #{rotationChart.content} , </if>" +
                " <if test=\"rotationChart.sort != null\"> index = #{rotationChart.sort} , </if>" +
                " <if test=\"rotationChart.remark != null\"> remark = #{rotationChart.remark} , </if>" +
                " <if test=\"rotationChart.status != null\"> status = #{rotationChart.status} , </if>" +
                " modified = now() " +
            " WHERE chart_id = #{rotationChart.chartId} " +
            "</script>")
    int update(@Param("rotationChart") RotationChartEntity rotationChart);

    @Update("<script> DELETE  FROM rotation_chart " +
            " WHERE chart_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


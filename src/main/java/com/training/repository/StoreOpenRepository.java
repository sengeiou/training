package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * store_open 数据库操作类
 * Created by huai23 on 2018-07-10 20:40:20.
 */ 
@Mapper
public interface StoreOpenRepository {

    @Insert("<script> INSERT INTO store_open ( " +
                " <if test=\"storeOpen.storeId != null\"> store_id, </if>" +
                " <if test=\"storeOpen.year != null\"> year, </if>" +
                " <if test=\"storeOpen.m01 != null\"> m01, </if>" +
                " <if test=\"storeOpen.m02 != null\"> m02, </if>" +
                " <if test=\"storeOpen.m03 != null\"> m03, </if>" +
                " <if test=\"storeOpen.m04 != null\"> m04, </if>" +
                " <if test=\"storeOpen.m05 != null\"> m05, </if>" +
                " <if test=\"storeOpen.m06 != null\"> m06, </if>" +
                " <if test=\"storeOpen.m07 != null\"> m07, </if>" +
                " <if test=\"storeOpen.m08 != null\"> m08, </if>" +
                " <if test=\"storeOpen.m09 != null\"> m09, </if>" +
                " <if test=\"storeOpen.m10 != null\"> m10, </if>" +
                " <if test=\"storeOpen.m11 != null\"> m11, </if>" +
                " <if test=\"storeOpen.m12 != null\"> m12, </if>" +
                " <if test=\"storeOpen.remark != null\"> remark, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"storeOpen.storeId != null\"> #{storeOpen.storeId}, </if>" +
                " <if test=\"storeOpen.year != null\"> #{storeOpen.year}, </if>" +
                " <if test=\"storeOpen.m01 != null\"> #{storeOpen.m01}, </if>" +
                " <if test=\"storeOpen.m02 != null\"> #{storeOpen.m02}, </if>" +
                " <if test=\"storeOpen.m03 != null\"> #{storeOpen.m03}, </if>" +
                " <if test=\"storeOpen.m04 != null\"> #{storeOpen.m04}, </if>" +
                " <if test=\"storeOpen.m05 != null\"> #{storeOpen.m05}, </if>" +
                " <if test=\"storeOpen.m06 != null\"> #{storeOpen.m06}, </if>" +
                " <if test=\"storeOpen.m07 != null\"> #{storeOpen.m07}, </if>" +
                " <if test=\"storeOpen.m08 != null\"> #{storeOpen.m08}, </if>" +
                " <if test=\"storeOpen.m09 != null\"> #{storeOpen.m09}, </if>" +
                " <if test=\"storeOpen.m10 != null\"> #{storeOpen.m10}, </if>" +
                " <if test=\"storeOpen.m11 != null\"> #{storeOpen.m11}, </if>" +
                " <if test=\"storeOpen.m12 != null\"> #{storeOpen.m12}, </if>" +
                " <if test=\"storeOpen.remark != null\"> #{storeOpen.remark}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("storeOpen") StoreOpenEntity storeOpen);

    @Select("<script> SELECT pk_id,store_id,year,m01,m02,m03,m04,m05,m06,m07,m08,m09,m10,m11,m12,remark,created,modified " +
            " FROM store_open " +
            " WHERE 1 = 1 " +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.storeName != null\"> AND store_id in ( select store_id from store where name like CONCAT('%',#{query.storeName},'%')  )</if>" +
            " <if test=\"query.year != null\"> AND year = #{query.year} </if>" +
            " <if test=\"query.m01 != null\"> AND m01 = #{query.m01} </if>" +
            " <if test=\"query.m02 != null\"> AND m02 = #{query.m02} </if>" +
            " <if test=\"query.m03 != null\"> AND m03 = #{query.m03} </if>" +
            " <if test=\"query.m04 != null\"> AND m04 = #{query.m04} </if>" +
            " <if test=\"query.m05 != null\"> AND m05 = #{query.m05} </if>" +
            " <if test=\"query.m06 != null\"> AND m06 = #{query.m06} </if>" +
            " <if test=\"query.m07 != null\"> AND m07 = #{query.m07} </if>" +
            " <if test=\"query.m08 != null\"> AND m08 = #{query.m08} </if>" +
            " <if test=\"query.m09 != null\"> AND m09 = #{query.m09} </if>" +
            " <if test=\"query.m10 != null\"> AND m10 = #{query.m10} </if>" +
            " <if test=\"query.m11 != null\"> AND m11 = #{query.m11} </if>" +
            " <if test=\"query.m12 != null\"> AND m12 = #{query.m12} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<StoreOpenEntity> find(@Param("query") StoreOpenQuery storeOpen , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM store_open " +
            " WHERE 1 = 1 " +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.storeName != null\"> AND store_id in ( select store_id from store where name like CONCAT('%',#{query.storeName},'%')  )</if>" +
            " <if test=\"query.year != null\"> AND year = #{query.year} </if>" +
            " <if test=\"query.m01 != null\"> AND m01 = #{query.m01} </if>" +
            " <if test=\"query.m02 != null\"> AND m02 = #{query.m02} </if>" +
            " <if test=\"query.m03 != null\"> AND m03 = #{query.m03} </if>" +
            " <if test=\"query.m04 != null\"> AND m04 = #{query.m04} </if>" +
            " <if test=\"query.m05 != null\"> AND m05 = #{query.m05} </if>" +
            " <if test=\"query.m06 != null\"> AND m06 = #{query.m06} </if>" +
            " <if test=\"query.m07 != null\"> AND m07 = #{query.m07} </if>" +
            " <if test=\"query.m08 != null\"> AND m08 = #{query.m08} </if>" +
            " <if test=\"query.m09 != null\"> AND m09 = #{query.m09} </if>" +
            " <if test=\"query.m10 != null\"> AND m10 = #{query.m10} </if>" +
            " <if test=\"query.m11 != null\"> AND m11 = #{query.m11} </if>" +
            " <if test=\"query.m12 != null\"> AND m12 = #{query.m12} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            "</script>")
    Long count(@Param("query") StoreOpenQuery storeOpen);

    @Select("<script> SELECT pk_id,store_id,year,m01,m02,m03,m04,m05,m06,m07,m08,m09,m10,m11,m12,remark,created,modified " +
            " FROM store_open " +
            " WHERE store_id = #{id} and year = #{year} " +
            "</script>")
    StoreOpenEntity getById(@Param("id") String id,@Param("year") String year);

    @Update("<script> UPDATE store_open SET " +

                " <if test=\"storeOpen.m01 != null\"> m01 = #{storeOpen.m01} , </if>" +
                " <if test=\"storeOpen.m02 != null\"> m02 = #{storeOpen.m02} , </if>" +
                " <if test=\"storeOpen.m03 != null\"> m03 = #{storeOpen.m03} , </if>" +
                " <if test=\"storeOpen.m04 != null\"> m04 = #{storeOpen.m04} , </if>" +
                " <if test=\"storeOpen.m05 != null\"> m05 = #{storeOpen.m05} , </if>" +
                " <if test=\"storeOpen.m06 != null\"> m06 = #{storeOpen.m06} , </if>" +
                " <if test=\"storeOpen.m07 != null\"> m07 = #{storeOpen.m07} , </if>" +
                " <if test=\"storeOpen.m08 != null\"> m08 = #{storeOpen.m08} , </if>" +
                " <if test=\"storeOpen.m09 != null\"> m09 = #{storeOpen.m09} , </if>" +
                " <if test=\"storeOpen.m10 != null\"> m10 = #{storeOpen.m10} , </if>" +
                " <if test=\"storeOpen.m11 != null\"> m11 = #{storeOpen.m11} , </if>" +
                " <if test=\"storeOpen.m12 != null\"> m12 = #{storeOpen.m12} , </if>" +
                " <if test=\"storeOpen.remark != null\"> remark = #{storeOpen.remark} , </if>" +
                " modified = now() " +
            " WHERE store_id = #{storeOpen.storeId} and year = #{storeOpen.year} " +
            "</script>")
    int update(@Param("storeOpen") StoreOpenEntity storeOpen);

    @Update("<script> DELETE  FROM store_open " +
            " WHERE store_id = #{id}  and year = #{year}  " +
            "</script>")
    int delete(@Param("id") String id ,@Param("year") String year);


}


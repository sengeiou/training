package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * store 数据库操作类
 * Created by huai23 on 2018-05-26 13:43:38.
 */ 
@Mapper
public interface StoreRepository {

    @Insert("<script> INSERT INTO store ( " +
                " <if test=\"store.storeId != null\"> store_id, </if>" +
                " <if test=\"store.deptId != null\"> dept_id, </if>" +
                " <if test=\"store.userId != null\"> user_id, </if>" +
                " <if test=\"store.name != null\"> name, </if>" +
                " <if test=\"store.address != null\"> address, </if>" +
                " <if test=\"store.feature != null\"> feature, </if>" +
                " <if test=\"store.remark != null\"> remark, </if>" +
                " <if test=\"store.status != null\"> status, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"store.storeId != null\"> #{store.storeId}, </if>" +
                " <if test=\"store.deptId != null\"> #{store.deptId}, </if>" +
                " <if test=\"store.userId != null\"> #{store.userId}, </if>" +
                " <if test=\"store.name != null\"> #{store.name}, </if>" +
                " <if test=\"store.address != null\"> #{store.address}, </if>" +
                " <if test=\"store.feature != null\"> #{store.feature}, </if>" +
                " <if test=\"store.remark != null\"> #{store.remark}, </if>" +
                " <if test=\"store.status != null\"> #{store.status}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("store") StoreEntity store);

    @Select("<script> SELECT pk_id,store_id,dept_id,user_id,name,address,feature,remark,status,created,modified " +
            " FROM store " +
            " WHERE 1 = 1 " +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.deptId != null\"> AND dept_id = #{query.deptId} </if>" +
            " <if test=\"query.userId != null\"> AND user_id = #{query.userId} </if>" +
            " <if test=\"query.name != null\"> AND name like CONCAT('%',#{query.name},'%')  </if>" +
            " <if test=\"query.address != null\"> AND address like CONCAT('%',#{query.address},'%')  </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<StoreEntity> find(@Param("query") StoreQuery store , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM store " +
            " WHERE 1 = 1 " +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.deptId != null\"> AND dept_id = #{query.deptId} </if>" +
            " <if test=\"query.userId != null\"> AND user_id = #{query.userId} </if>" +
            " <if test=\"query.name != null\"> AND name like CONCAT('%',#{query.name},'%') </if>" +
            " <if test=\"query.address != null\"> AND address like CONCAT('%',#{query.address},'%')  </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            "</script>")
    Long count(@Param("query") StoreQuery store);

    @Select("<script> SELECT pk_id,store_id,dept_id,user_id,name,address,feature,remark,status,created,modified " +
            " FROM store " +
            " WHERE store_id = #{id} " +
            "</script>")
    StoreEntity getById(@Param("id") String id);

    @Update("<script> UPDATE store SET " +
                " <if test=\"store.storeId != null\"> store_id = #{store.storeId} , </if>" +
                " <if test=\"store.deptId != null\"> dept_id = #{store.deptId} , </if>" +
                " <if test=\"store.userId != null\"> user_id = #{store.userId} , </if>" +
                " <if test=\"store.name != null\"> name = #{store.name} , </if>" +
                " <if test=\"store.address != null\"> address = #{store.address} , </if>" +
                " <if test=\"store.feature != null\"> feature = #{store.feature} , </if>" +
                " <if test=\"store.remark != null\"> remark = #{store.remark} , </if>" +
                " <if test=\"store.status != null\"> status = #{store.status} , </if>" +
                " modified = now() " +
            " WHERE store_id = #{store.storeId} " +
            "</script>")
    int update(@Param("store") StoreEntity store);

    @Update("<script> DELETE  FROM store " +
            " WHERE store_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * role 数据库操作类
 * Created by huai23 on 2018-06-27 15:28:01.
 */ 
@Mapper
public interface RoleRepository {

    @Insert("<script> INSERT INTO role ( " +
                " <if test=\"role.roleId != null\"> role_id, </if>" +
                " <if test=\"role.roleName != null\"> role_name, </if>" +
                " <if test=\"role.nodeData != null\"> node_data, </if>" +
                " <if test=\"role.storeData != null\"> store_data, </if>" +
                " <if test=\"role.feature != null\"> feature, </if>" +
                " <if test=\"role.status != null\"> status, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"role.roleId != null\"> #{role.roleId}, </if>" +
                " <if test=\"role.roleName != null\"> #{role.roleName}, </if>" +
                " <if test=\"role.nodeData != null\"> #{role.nodeData}, </if>" +
                " <if test=\"role.storeData != null\"> #{role.storeData}, </if>" +
                " <if test=\"role.feature != null\"> #{role.feature}, </if>" +
                " <if test=\"role.status != null\"> #{role.status}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("role") RoleEntity role);

    @Select("<script> SELECT pk_id,role_id,role_name,node_data,store_data,feature,status,created,modified " +
            " FROM role " +
            " WHERE 1 = 1 " +
            " <if test=\"query.roleId != null\"> AND role_id = #{query.roleId} </if>" +
            " <if test=\"query.roleName != null\"> AND role_name = #{query.roleName} </if>" +
            " <if test=\"query.nodeData != null\"> AND node_data = #{query.nodeData} </if>" +
            " <if test=\"query.storeData != null\"> AND store_data = #{query.storeData} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<RoleEntity> find(@Param("query") RoleQuery role , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM role " +
            " WHERE 1 = 1 " +
            " <if test=\"query.roleId != null\"> AND role_id = #{query.roleId} </if>" +
            " <if test=\"query.roleName != null\"> AND role_name = #{query.roleName} </if>" +
            " <if test=\"query.nodeData != null\"> AND node_data = #{query.nodeData} </if>" +
            " <if test=\"query.storeData != null\"> AND store_data = #{query.storeData} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            "</script>")
    Long count(@Param("query") RoleQuery role);

    @Select("<script> SELECT pk_id,role_id,role_name,node_data,store_data,feature,status,created,modified " +
            " FROM role " +
            " WHERE role_id = #{id} " +
            "</script>")
    RoleEntity getById(@Param("id") String id);

    @Update("<script> UPDATE role SET " +
                " <if test=\"role.roleId != null\"> role_id = #{role.roleId} , </if>" +
                " <if test=\"role.roleName != null\"> role_name = #{role.roleName} , </if>" +
                " <if test=\"role.nodeData != null\"> node_data = #{role.nodeData} , </if>" +
                " <if test=\"role.storeData != null\"> store_data = #{role.storeData} , </if>" +
                " <if test=\"role.feature != null\"> feature = #{role.feature} , </if>" +
                " <if test=\"role.status != null\"> status = #{role.status} , </if>" +
                " modified = now() " +
            " WHERE role_id = #{role.roleId} " +
            "</script>")
    int update(@Param("role") RoleEntity role);

    @Update("<script> DELETE  FROM role " +
            " WHERE role_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


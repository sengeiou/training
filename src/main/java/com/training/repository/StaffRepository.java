package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * staff 数据库操作类
 * Created by huai23 on 2018-05-26 13:55:30.
 */ 
@Mapper
public interface StaffRepository {

    @Insert("<script> INSERT INTO staff ( " +
                " <if test=\"staff.staffId != null\"> staff_id, </if>" +
                " <if test=\"staff.storeId != null\"> store_id, </if>" +
                " <if test=\"staff.roleId != null\"> role_id, </if>" +
                " <if test=\"staff.username != null\"> username, </if>" +
                " <if test=\"staff.password != null\"> password, </if>" +
                " <if test=\"staff.custname != null\"> custname, </if>" +
                " <if test=\"staff.email != null\"> email, </if>" +
                " <if test=\"staff.phone != null\"> phone, </if>" +
                " <if test=\"staff.job != null\"> job, </if>" +
                " <if test=\"staff.openId != null\"> open_id, </if>" +
                " <if test=\"staff.unionId != null\"> union_id, </if>" +
                " <if test=\"staff.feature != null\"> feature, </if>" +
                " <if test=\"staff.status != null\"> status, </if>" +
                " <if test=\"staff.relId != null\"> rel_id, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"staff.staffId != null\"> #{staff.staffId}, </if>" +
                " <if test=\"staff.storeId != null\"> #{staff.storeId}, </if>" +
                " <if test=\"staff.roleId != null\"> #{staff.roleId}, </if>" +
                " <if test=\"staff.username != null\"> #{staff.username}, </if>" +
                " <if test=\"staff.password != null\"> #{staff.password}, </if>" +
                " <if test=\"staff.custname != null\"> #{staff.custname}, </if>" +
                " <if test=\"staff.email != null\"> #{staff.email}, </if>" +
                " <if test=\"staff.phone != null\"> #{staff.phone}, </if>" +
                " <if test=\"staff.job != null\"> #{staff.job}, </if>" +
                " <if test=\"staff.openId != null\"> #{staff.openId}, </if>" +
                " <if test=\"staff.unionId != null\"> #{staff.unionId}, </if>" +
                " <if test=\"staff.feature != null\"> #{staff.feature}, </if>" +
                " <if test=\"staff.status != null\"> #{staff.status}, </if>" +
                " <if test=\"staff.relId != null\"> #{staff.relId}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("staff") StaffEntity staff);

    @Select("<script> SELECT pk_id,staff_id,store_id,role_id,username,password,custname,email,phone,job,open_id,union_id,feature,status,rel_id,created,modified " +
            " FROM staff " +
            " WHERE 1 = 1 " +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id in ( #{query.storeId} ) </if>" +
            " <if test=\"query.roleId != null\"> AND role_id = #{query.roleId} </if>" +
            " <if test=\"query.username != null\"> AND username = #{query.username} </if>" +
            " <if test=\"query.password != null\"> AND password = #{query.password} </if>" +
            " <if test=\"query.custname != null\"> AND custname like CONCAT('%',#{query.custname},'%')   </if>" +
            " <if test=\"query.email != null\"> AND email like CONCAT('%',#{query.email},'%')   </if>" +
            " <if test=\"query.phone != null\"> AND phone like CONCAT('%',#{query.phone},'%')    </if>" +
            " <if test=\"query.job != null\"> AND job = #{query.job} </if>" +
            " <if test=\"query.openId != null\"> AND open_id = #{query.openId} </if>" +
            " <if test=\"query.unionId != null\"> AND union_id = #{query.unionId} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.relId != null\"> AND rel_id = #{query.relId} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<StaffEntity> find(@Param("query") StaffQuery staff , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM staff " +
            " WHERE 1 = 1 " +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id in ( #{query.storeId} ) </if>" +
            " <if test=\"query.roleId != null\"> AND role_id = #{query.roleId} </if>" +
            " <if test=\"query.username != null\"> AND username = #{query.username} </if>" +
            " <if test=\"query.password != null\"> AND password = #{query.password} </if>" +
            " <if test=\"query.custname != null\"> AND custname like CONCAT('%',#{query.custname},'%')   </if>" +
            " <if test=\"query.email != null\"> AND email like CONCAT('%',#{query.email},'%')   </if>" +
            " <if test=\"query.phone != null\"> AND phone like CONCAT('%',#{query.phone},'%')    </if>" +
            " <if test=\"query.job != null\"> AND job = #{query.job} </if>" +
            " <if test=\"query.openId != null\"> AND open_id = #{query.openId} </if>" +
            " <if test=\"query.unionId != null\"> AND union_id = #{query.unionId} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.relId != null\"> AND rel_id = #{query.relId} </if>" +
            "</script>")
    Long count(@Param("query") StaffQuery staff);

    @Select("<script> SELECT pk_id,staff_id,store_id,role_id,username,password,custname,email,phone,job,open_id,union_id,feature,status,rel_id,created,modified " +
            " FROM staff " +
            " WHERE staff_id = #{id} " +
            "</script>")
    StaffEntity getById(@Param("id") String id);

    @Update("<script> UPDATE staff SET " +
                " <if test=\"staff.staffId != null\"> staff_id = #{staff.staffId} , </if>" +
                " <if test=\"staff.storeId != null\"> store_id = #{staff.storeId} , </if>" +
                " <if test=\"staff.roleId != null\"> role_id = #{staff.roleId} , </if>" +
                " <if test=\"staff.username != null\"> username = #{staff.username} , </if>" +
                " <if test=\"staff.password != null\"> password = #{staff.password} , </if>" +
                " <if test=\"staff.custname != null\"> custname = #{staff.custname} , </if>" +
                " <if test=\"staff.email != null\"> email = #{staff.email} , </if>" +
                " <if test=\"staff.phone != null\"> phone = #{staff.phone} , </if>" +
                " <if test=\"staff.job != null\"> job = #{staff.job} , </if>" +
                " <if test=\"staff.openId != null\"> open_id = #{staff.openId} , </if>" +
                " <if test=\"staff.unionId != null\"> union_id = #{staff.unionId} , </if>" +
                " <if test=\"staff.feature != null\"> feature = #{staff.feature} , </if>" +
                " <if test=\"staff.status != null\"> status = #{staff.status} , </if>" +
                " <if test=\"staff.relId != null\"> rel_id = #{staff.relId} , </if>" +
                " modified = now() " +
            " WHERE staff_id = #{staff.staffId} " +
            "</script>")
    int update(@Param("staff") StaffEntity staff);

    @Update("<script> DELETE  FROM staff " +
            " WHERE staff_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);

    @Select("<script> SELECT pk_id,staff_id,store_id,role_id,username,password,custname,email,phone,job,open_id,union_id,feature,status,rel_id,created,modified " +
            " FROM staff " +
            " WHERE username = #{username} limit 0,1 " +
            "</script>")
    StaffEntity getByUsername(@Param("username") String username);

    @Select("<script> SELECT pk_id,staff_id,store_id,role_id,username,password,custname,email,phone,job,open_id,union_id,feature,status,rel_id,created,modified " +
            " FROM staff " +
            " WHERE phone = #{phone} limit 0,1 " +
            "</script>")
    StaffEntity getByPhone(@Param("phone") String phone);

    @Update("<script> UPDATE staff SET open_id = #{staff.openId} , modified = now() WHERE staff_id = #{staff.staffId}  </script>")
    int bind(@Param("staff") StaffEntity staff);

}


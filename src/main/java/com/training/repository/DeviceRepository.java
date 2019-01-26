package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * device 数据库操作类
 * Created by huai23 on 2019-01-26 23:09:05.
 */ 
@Mapper
public interface DeviceRepository {

    @Insert("<script> INSERT INTO device ( " +
                " <if test=\"device.deviceSn != null\"> device_sn, </if>" +
                " <if test=\"device.storeId != null\"> store_id, </if>" +
                " <if test=\"device.feature != null\"> feature, </if>" +
                " <if test=\"device.status != null\"> status, </if>" +
                " <if test=\"device.remark != null\"> REMARK, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"device.deviceSn != null\"> #{device.deviceSn}, </if>" +
                " <if test=\"device.storeId != null\"> #{device.storeId}, </if>" +
                " <if test=\"device.feature != null\"> #{device.feature}, </if>" +
                " <if test=\"device.status != null\"> #{device.status}, </if>" +
                " <if test=\"device.remark != null\"> #{device.remark}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("device") DeviceEntity device);

    @Select("<script> SELECT pk_id,device_sn,store_id,feature,status,REMARK,created,modified " +
            " FROM device " +
            " WHERE 1 = 1 " +
            " <if test=\"query.deviceSn != null\"> AND device_sn = #{query.deviceSn} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<DeviceEntity> find(@Param("query") DeviceQuery device , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM device " +
            " WHERE 1 = 1 " +
            " <if test=\"query.deviceSn != null\"> AND device_sn = #{query.deviceSn} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            "</script>")
    Long count(@Param("query") DeviceQuery device);

    @Select("<script> SELECT pk_id,device_sn,store_id,feature,status,REMARK,created,modified " +
            " FROM device " +
            " WHERE device_sn = #{id} " +
            "</script>")
    DeviceEntity getById(@Param("id") String id);

    @Update("<script> UPDATE device SET " +
                " <if test=\"device.deviceSn != null\"> device_sn = #{device.deviceSn} , </if>" +
                " <if test=\"device.storeId != null\"> store_id = #{device.storeId} , </if>" +
                " <if test=\"device.feature != null\"> feature = #{device.feature} , </if>" +
                " <if test=\"device.status != null\"> status = #{device.status} , </if>" +
                " <if test=\"device.remark != null\"> REMARK = #{device.remark} , </if>" +
                " modified = now() " +
            " WHERE device_sn = #{device.deviceSn} " +
            "</script>")
    int update(@Param("device") DeviceEntity device);

    @Update("<script> DELETE  FROM device " +
            " WHERE device_sn = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


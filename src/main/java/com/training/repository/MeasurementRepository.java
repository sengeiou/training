package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * measurement 数据库操作类
 * Created by huai23 on 2019-01-22 21:54:18.
 */ 
@Mapper
public interface MeasurementRepository {

    @Insert("<script> INSERT INTO measurement ( " +
                " <if test=\"measurement.measurementId != null\"> measurement_id, </if>" +
                " <if test=\"measurement.bodyId != null\"> body_id, </if>" +
                " <if test=\"measurement.memberId != null\"> member_id, </if>" +
                " <if test=\"measurement.storeId != null\"> store_id, </if>" +
                " <if test=\"measurement.deviceSn != null\"> device_sn, </if>" +
                " <if test=\"measurement.gender != null\"> gender, </if>" +
                " <if test=\"measurement.age != null\"> age, </if>" +
                " <if test=\"measurement.height != null\"> height, </if>" +
                " <if test=\"measurement.weight != null\"> weight, </if>" +
                " <if test=\"measurement.phone != null\"> phone, </if>" +
                " <if test=\"measurement.outline != null\"> outline, </if>" +
                " <if test=\"measurement.measurement != null\"> measurement, </if>" +
                " <if test=\"measurement.composition != null\"> composition, </if>" +
                " <if test=\"measurement.posture != null\"> posture, </if>" +
                " <if test=\"measurement.girth != null\"> girth, </if>" +
                " <if test=\"measurement.feature != null\"> feature, </if>" +
                " <if test=\"measurement.measureDate != null\"> measure_date, </if>" +
                " <if test=\"measurement.startTime != null\"> start_time, </if>" +
                " <if test=\"measurement.remark != null\"> REMARK, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"measurement.measurementId != null\"> #{measurement.measurementId}, </if>" +
                " <if test=\"measurement.bodyId != null\"> #{measurement.bodyId}, </if>" +
                " <if test=\"measurement.memberId != null\"> #{measurement.memberId}, </if>" +
                " <if test=\"measurement.storeId != null\"> #{measurement.storeId}, </if>" +
                " <if test=\"measurement.deviceSn != null\"> #{measurement.deviceSn}, </if>" +
                " <if test=\"measurement.gender != null\"> #{measurement.gender}, </if>" +
                " <if test=\"measurement.age != null\"> #{measurement.age}, </if>" +
                " <if test=\"measurement.height != null\"> #{measurement.height}, </if>" +
                " <if test=\"measurement.weight != null\"> #{measurement.weight}, </if>" +
                " <if test=\"measurement.phone != null\"> #{measurement.phone}, </if>" +
                " <if test=\"measurement.outline != null\"> #{measurement.outline}, </if>" +
                " <if test=\"measurement.measurement != null\"> #{measurement.measurement}, </if>" +
                " <if test=\"measurement.composition != null\"> #{measurement.composition}, </if>" +
                " <if test=\"measurement.posture != null\"> #{measurement.posture}, </if>" +
                " <if test=\"measurement.girth != null\"> #{measurement.girth}, </if>" +
                " <if test=\"measurement.feature != null\"> #{measurement.feature}, </if>" +
                " <if test=\"measurement.measureDate != null\"> #{measurement.measureDate}, </if>" +
                " <if test=\"measurement.startTime != null\"> #{measurement.startTime}, </if>" +
                " <if test=\"measurement.remark != null\"> #{measurement.remark}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("measurement") MeasurementEntity measurement);

    @Select("<script> SELECT pk_id,measurement_id,body_id,member_id,store_id,device_sn,gender,age,height,weight,phone,outline,measurement,composition,posture,girth,feature,measure_date,start_time,REMARK,created,modified " +
            " FROM measurement " +
            " WHERE 1 = 1 " +
            " <if test=\"query.measurementId != null\"> AND measurement_id = #{query.measurementId} </if>" +
            " <if test=\"query.bodyId != null\"> AND body_id = #{query.bodyId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.deviceSn != null\"> AND device_sn = #{query.deviceSn} </if>" +
            " <if test=\"query.gender != null\"> AND gender = #{query.gender} </if>" +
            " <if test=\"query.age != null\"> AND age = #{query.age} </if>" +
            " <if test=\"query.height != null\"> AND height = #{query.height} </if>" +
            " <if test=\"query.weight != null\"> AND weight = #{query.weight} </if>" +
            " <if test=\"query.phone != null\"> AND phone = #{query.phone} </if>" +
            " <if test=\"query.outline != null\"> AND outline = #{query.outline} </if>" +
            " <if test=\"query.measurement != null\"> AND measurement = #{query.measurement} </if>" +
            " <if test=\"query.composition != null\"> AND composition = #{query.composition} </if>" +
            " <if test=\"query.posture != null\"> AND posture = #{query.posture} </if>" +
            " <if test=\"query.girth != null\"> AND girth = #{query.girth} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.measureDate != null\"> AND measure_date = #{query.measureDate} </if>" +
            " <if test=\"query.startTime != null\"> AND start_time = #{query.startTime} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<MeasurementEntity> find(@Param("query") MeasurementQuery measurement , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM measurement " +
            " WHERE 1 = 1 " +
            " <if test=\"query.measurementId != null\"> AND measurement_id = #{query.measurementId} </if>" +
            " <if test=\"query.bodyId != null\"> AND body_id = #{query.bodyId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.deviceSn != null\"> AND device_sn = #{query.deviceSn} </if>" +
            " <if test=\"query.gender != null\"> AND gender = #{query.gender} </if>" +
            " <if test=\"query.age != null\"> AND age = #{query.age} </if>" +
            " <if test=\"query.height != null\"> AND height = #{query.height} </if>" +
            " <if test=\"query.weight != null\"> AND weight = #{query.weight} </if>" +
            " <if test=\"query.phone != null\"> AND phone = #{query.phone} </if>" +
            " <if test=\"query.outline != null\"> AND outline = #{query.outline} </if>" +
            " <if test=\"query.measurement != null\"> AND measurement = #{query.measurement} </if>" +
            " <if test=\"query.composition != null\"> AND composition = #{query.composition} </if>" +
            " <if test=\"query.posture != null\"> AND posture = #{query.posture} </if>" +
            " <if test=\"query.girth != null\"> AND girth = #{query.girth} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.measureDate != null\"> AND measure_date = #{query.measureDate} </if>" +
            " <if test=\"query.startTime != null\"> AND start_time = #{query.startTime} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            "</script>")
    Long count(@Param("query") MeasurementQuery measurement);

    @Select("<script> SELECT pk_id,measurement_id,body_id,member_id,store_id,device_sn,gender,age,height,weight,phone,outline,measurement,composition,posture,girth,feature,measure_date,start_time,REMARK,created,modified " +
            " FROM measurement " +
            " WHERE measurement_id = #{id} " +
            "</script>")
    MeasurementEntity getById(@Param("id") String id);

    @Update("<script> UPDATE measurement SET " +
                " <if test=\"measurement.measurementId != null\"> measurement_id = #{measurement.measurementId} , </if>" +
                " <if test=\"measurement.bodyId != null\"> body_id = #{measurement.bodyId} , </if>" +
                " <if test=\"measurement.memberId != null\"> member_id = #{measurement.memberId} , </if>" +
                " <if test=\"measurement.storeId != null\"> store_id = #{measurement.storeId} , </if>" +
                " <if test=\"measurement.deviceSn != null\"> device_sn = #{measurement.deviceSn} , </if>" +
                " <if test=\"measurement.gender != null\"> gender = #{measurement.gender} , </if>" +
                " <if test=\"measurement.age != null\"> age = #{measurement.age} , </if>" +
                " <if test=\"measurement.height != null\"> height = #{measurement.height} , </if>" +
                " <if test=\"measurement.weight != null\"> weight = #{measurement.weight} , </if>" +
                " <if test=\"measurement.phone != null\"> phone = #{measurement.phone} , </if>" +
                " <if test=\"measurement.outline != null\"> outline = #{measurement.outline} , </if>" +
                " <if test=\"measurement.measurement != null\"> measurement = #{measurement.measurement} , </if>" +
                " <if test=\"measurement.composition != null\"> composition = #{measurement.composition} , </if>" +
                " <if test=\"measurement.posture != null\"> posture = #{measurement.posture} , </if>" +
                " <if test=\"measurement.girth != null\"> girth = #{measurement.girth} , </if>" +
                " <if test=\"measurement.feature != null\"> feature = #{measurement.feature} , </if>" +
                " <if test=\"measurement.measureDate != null\"> measure_date = #{measurement.measureDate} , </if>" +
                " <if test=\"measurement.startTime != null\"> start_time = #{measurement.startTime} , </if>" +
                " <if test=\"measurement.remark != null\"> REMARK = #{measurement.remark} , </if>" +
                " modified = now() " +
            " WHERE measurement_id = #{measurement.measurementId} " +
            "</script>")
    int update(@Param("measurement") MeasurementEntity measurement);

    @Update("<script> DELETE  FROM measurement " +
            " WHERE measurement_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


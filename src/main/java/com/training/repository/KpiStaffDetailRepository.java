package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * kpi_staff_detail 数据库操作类
 * Created by huai23 on 2018-11-18 10:53:42.
 */ 
@Mapper
public interface KpiStaffDetailRepository {

    @Insert("<script> INSERT INTO kpi_staff_detail ( " +
                " <if test=\"kpiStaffDetail.cardNo != null\"> card_no, </if>" +
                " <if test=\"kpiStaffDetail.contractId != null\"> contract_id, </if>" +
                " <if test=\"kpiStaffDetail.month != null\"> month, </if>" +
                " <if test=\"kpiStaffDetail.type != null\"> type, </if>" +
                " <if test=\"kpiStaffDetail.staffId != null\"> staff_id, </if>" +
                " <if test=\"kpiStaffDetail.storeId != null\"> store_id, </if>" +
                " <if test=\"kpiStaffDetail.memberId != null\"> member_id, </if>" +
                " <if test=\"kpiStaffDetail.remark != null\"> remark, </if>" +
                " <if test=\"kpiStaffDetail.status != null\"> status, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"kpiStaffDetail.cardNo != null\"> #{kpiStaffDetail.cardNo}, </if>" +
                " <if test=\"kpiStaffDetail.contractId != null\"> #{kpiStaffDetail.contractId}, </if>" +
                " <if test=\"kpiStaffDetail.month != null\"> #{kpiStaffDetail.month}, </if>" +
                " <if test=\"kpiStaffDetail.type != null\"> #{kpiStaffDetail.type}, </if>" +
                " <if test=\"kpiStaffDetail.staffId != null\"> #{kpiStaffDetail.staffId}, </if>" +
                " <if test=\"kpiStaffDetail.storeId != null\"> #{kpiStaffDetail.storeId}, </if>" +
                " <if test=\"kpiStaffDetail.memberId != null\"> #{kpiStaffDetail.memberId}, </if>" +
                " <if test=\"kpiStaffDetail.remark != null\"> #{kpiStaffDetail.remark}, </if>" +
                " <if test=\"kpiStaffDetail.status != null\"> #{kpiStaffDetail.status}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("kpiStaffDetail") KpiStaffDetailEntity kpiStaffDetail);

    @Select("<script> SELECT pk_id,card_no,contract_id,month,type,staff_id,store_id,member_id,remark,status,created,modified " +
            " FROM kpi_staff_detail " +
            " WHERE 1 = 1 " +
            " <if test=\"query.cardNo != null\"> AND card_no = #{query.cardNo} </if>" +
            " <if test=\"query.contractId != null\"> AND contract_id = #{query.contractId} </if>" +
            " <if test=\"query.month != null\"> AND month = #{query.month} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<KpiStaffDetailEntity> find(@Param("query") KpiStaffDetailQuery kpiStaffDetail , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM kpi_staff_detail " +
            " WHERE 1 = 1 " +
            " <if test=\"query.cardNo != null\"> AND card_no = #{query.cardNo} </if>" +
            " <if test=\"query.contractId != null\"> AND contract_id = #{query.contractId} </if>" +
            " <if test=\"query.month != null\"> AND month = #{query.month} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            "</script>")
    Long count(@Param("query") KpiStaffDetailQuery kpiStaffDetail);

    @Select("<script> SELECT pk_id,card_no,contract_id,month,type,staff_id,store_id,member_id,remark,status,created,modified " +
            " FROM kpi_staff_detail " +
            " WHERE card_no = #{id} " +
            "</script>")
    KpiStaffDetailEntity getById(@Param("id") String id);

    @Update("<script> UPDATE kpi_staff_detail SET " +
                " <if test=\"kpiStaffDetail.cardNo != null\"> card_no = #{kpiStaffDetail.cardNo} , </if>" +
                " <if test=\"kpiStaffDetail.contractId != null\"> contract_id = #{kpiStaffDetail.contractId} , </if>" +
                " <if test=\"kpiStaffDetail.month != null\"> month = #{kpiStaffDetail.month} , </if>" +
                " <if test=\"kpiStaffDetail.type != null\"> type = #{kpiStaffDetail.type} , </if>" +
                " <if test=\"kpiStaffDetail.staffId != null\"> staff_id = #{kpiStaffDetail.staffId} , </if>" +
                " <if test=\"kpiStaffDetail.storeId != null\"> store_id = #{kpiStaffDetail.storeId} , </if>" +
                " <if test=\"kpiStaffDetail.memberId != null\"> member_id = #{kpiStaffDetail.memberId} , </if>" +
                " <if test=\"kpiStaffDetail.remark != null\"> remark = #{kpiStaffDetail.remark} , </if>" +
                " <if test=\"kpiStaffDetail.status != null\"> status = #{kpiStaffDetail.status} , </if>" +
                " modified = now() " +
            " WHERE card_no = #{kpiStaffDetail.cardNo} " +
            "</script>")
    int update(@Param("kpiStaffDetail") KpiStaffDetailEntity kpiStaffDetail);

    @Update("<script> DELETE  FROM kpi_staff_detail " +
            " WHERE card_no = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


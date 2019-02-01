package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * group_order 数据库操作类
 * Created by huai23 on 2019-02-01 20:05:18.
 */ 
@Mapper
public interface GroupOrderRepository {

    @Insert("<script> INSERT INTO group_order ( " +
                " <if test=\"groupOrder.orderId != null\"> order_id, </if>" +
                " <if test=\"groupOrder.storeId != null\"> store_id, </if>" +
                " <if test=\"groupOrder.memberId != null\"> member_id, </if>" +
                " <if test=\"groupOrder.phone != null\"> phone, </if>" +
                " <if test=\"groupOrder.gender != null\"> gender, </if>" +
                " <if test=\"groupOrder.count != null\"> count, </if>" +
                " <if test=\"groupOrder.totalFee != null\"> total_fee, </if>" +
                " <if test=\"groupOrder.mainFlag != null\"> main_flag, </if>" +
                " <if test=\"groupOrder.mainOrderId != null\"> main_order_id, </if>" +
                " <if test=\"groupOrder.status != null\"> status, </if>" +
                " <if test=\"groupOrder.feature != null\"> feature, </if>" +
                " <if test=\"groupOrder.payType != null\"> pay_type, </if>" +
                " <if test=\"groupOrder.payId != null\"> pay_id, </if>" +
                " <if test=\"groupOrder.payTime != null\"> pay_time, </if>" +
                " <if test=\"groupOrder.remark != null\"> remark, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"groupOrder.orderId != null\"> #{groupOrder.orderId}, </if>" +
                " <if test=\"groupOrder.storeId != null\"> #{groupOrder.storeId}, </if>" +
                " <if test=\"groupOrder.memberId != null\"> #{groupOrder.memberId}, </if>" +
                " <if test=\"groupOrder.phone != null\"> #{groupOrder.phone}, </if>" +
                " <if test=\"groupOrder.gender != null\"> #{groupOrder.gender}, </if>" +
                " <if test=\"groupOrder.count != null\"> #{groupOrder.count}, </if>" +
                " <if test=\"groupOrder.totalFee != null\"> #{groupOrder.totalFee}, </if>" +
                " <if test=\"groupOrder.mainFlag != null\"> #{groupOrder.mainFlag}, </if>" +
                " <if test=\"groupOrder.mainOrderId != null\"> #{groupOrder.mainOrderId}, </if>" +
                " <if test=\"groupOrder.status != null\"> #{groupOrder.status}, </if>" +
                " <if test=\"groupOrder.feature != null\"> #{groupOrder.feature}, </if>" +
                " <if test=\"groupOrder.payType != null\"> #{groupOrder.payType}, </if>" +
                " <if test=\"groupOrder.payId != null\"> #{groupOrder.payId}, </if>" +
                " <if test=\"groupOrder.payTime != null\"> #{groupOrder.payTime}, </if>" +
                " <if test=\"groupOrder.remark != null\"> #{groupOrder.remark}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("groupOrder") GroupOrderEntity groupOrder);

    @Select("<script> SELECT order_id,store_id,member_id,phone,gender,count,total_fee,main_flag,main_order_id,status,feature,pay_type,pay_id,pay_time,remark,created,modified " +
            " FROM group_order " +
            " WHERE 1 = 1 " +
            " <if test=\"query.orderId != null\"> AND order_id = #{query.orderId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.phone != null\"> AND phone = #{query.phone} </if>" +
            " <if test=\"query.gender != null\"> AND gender = #{query.gender} </if>" +
            " <if test=\"query.count != null\"> AND count = #{query.count} </if>" +
            " <if test=\"query.totalFee != null\"> AND total_fee = #{query.totalFee} </if>" +
            " <if test=\"query.mainFlag != null\"> AND main_flag = #{query.mainFlag} </if>" +
            " <if test=\"query.mainOrderId != null\"> AND main_order_id = #{query.mainOrderId} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.payType != null\"> AND pay_type = #{query.payType} </if>" +
            " <if test=\"query.payId != null\"> AND pay_id = #{query.payId} </if>" +
            " <if test=\"query.payTime != null\"> AND pay_time = #{query.payTime} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<GroupOrderEntity> find(@Param("query") GroupOrderQuery groupOrder , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM group_order " +
            " WHERE 1 = 1 " +
            " <if test=\"query.orderId != null\"> AND order_id = #{query.orderId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.phone != null\"> AND phone = #{query.phone} </if>" +
            " <if test=\"query.gender != null\"> AND gender = #{query.gender} </if>" +
            " <if test=\"query.count != null\"> AND count = #{query.count} </if>" +
            " <if test=\"query.totalFee != null\"> AND total_fee = #{query.totalFee} </if>" +
            " <if test=\"query.mainFlag != null\"> AND main_flag = #{query.mainFlag} </if>" +
            " <if test=\"query.mainOrderId != null\"> AND main_order_id = #{query.mainOrderId} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.payType != null\"> AND pay_type = #{query.payType} </if>" +
            " <if test=\"query.payId != null\"> AND pay_id = #{query.payId} </if>" +
            " <if test=\"query.payTime != null\"> AND pay_time = #{query.payTime} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            "</script>")
    Long count(@Param("query") GroupOrderQuery groupOrder);

    @Select("<script> SELECT order_id,store_id,member_id,phone,gender,count,total_fee,main_flag,main_order_id,status,feature,pay_type,pay_id,pay_time,remark,created,modified " +
            " FROM group_order " +
            " WHERE order_id = #{id} " +
            "</script>")
    GroupOrderEntity getById(@Param("id") String id);

    @Update("<script> UPDATE group_order SET " +
                " <if test=\"groupOrder.orderId != null\"> order_id = #{groupOrder.orderId} , </if>" +
                " <if test=\"groupOrder.storeId != null\"> store_id = #{groupOrder.storeId} , </if>" +
                " <if test=\"groupOrder.memberId != null\"> member_id = #{groupOrder.memberId} , </if>" +
                " <if test=\"groupOrder.phone != null\"> phone = #{groupOrder.phone} , </if>" +
                " <if test=\"groupOrder.gender != null\"> gender = #{groupOrder.gender} , </if>" +
                " <if test=\"groupOrder.count != null\"> count = #{groupOrder.count} , </if>" +
                " <if test=\"groupOrder.totalFee != null\"> total_fee = #{groupOrder.totalFee} , </if>" +
                " <if test=\"groupOrder.mainFlag != null\"> main_flag = #{groupOrder.mainFlag} , </if>" +
                " <if test=\"groupOrder.mainOrderId != null\"> main_order_id = #{groupOrder.mainOrderId} , </if>" +
                " <if test=\"groupOrder.status != null\"> status = #{groupOrder.status} , </if>" +
                " <if test=\"groupOrder.feature != null\"> feature = #{groupOrder.feature} , </if>" +
                " <if test=\"groupOrder.payType != null\"> pay_type = #{groupOrder.payType} , </if>" +
                " <if test=\"groupOrder.payId != null\"> pay_id = #{groupOrder.payId} , </if>" +
                " <if test=\"groupOrder.payTime != null\"> pay_time = #{groupOrder.payTime} , </if>" +
                " <if test=\"groupOrder.remark != null\"> remark = #{groupOrder.remark} , </if>" +
                " modified = now() " +
            " WHERE order_id = #{groupOrder.orderId} " +
            "</script>")
    int update(@Param("groupOrder") GroupOrderEntity groupOrder);

    @Update("<script> DELETE  FROM group_order " +
            " WHERE order_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


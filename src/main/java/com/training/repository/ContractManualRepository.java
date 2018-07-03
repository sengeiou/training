package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * contract_manual 数据库操作类
 * Created by huai23 on 2018-06-28 02:06:09.
 */ 
@Mapper
public interface ContractManualRepository {

    @Insert("<script> INSERT INTO contract_manual ( " +
                " <if test=\"contractManual.contractId != null\"> contract_id, </if>" +
                " <if test=\"contractManual.cardNo != null\"> card_no, </if>" +
                " <if test=\"contractManual.memberName != null\"> member_name, </if>" +
                " <if test=\"contractManual.phone != null\"> phone, </if>" +
                " <if test=\"contractManual.cardType != null\"> card_type, </if>" +
                " <if test=\"contractManual.storeName != null\"> store_name, </if>" +
                " <if test=\"contractManual.salesman != null\"> salesman, </if>" +
                " <if test=\"contractManual.salesmanPhone != null\"> salesman_phone, </if>" +
                " <if test=\"contractManual.coach != null\"> coach, </if>" +
                " <if test=\"contractManual.coachPhone != null\"> coach_phone, </if>" +
                " <if test=\"contractManual.total != null\"> total, </if>" +
                " <if test=\"contractManual.money != null\"> money, </if>" +
                " <if test=\"contractManual.type != null\"> type, </if>" +
                " <if test=\"contractManual.payType != null\"> pay_type, </if>" +
                " <if test=\"contractManual.price != null\"> price, </if>" +
                " <if test=\"contractManual.startDate != null\"> start_date, </if>" +
                " <if test=\"contractManual.endDate != null\"> end_date, </if>" +
                " <if test=\"contractManual.realFee != null\"> real_fee, </if>" +
                " <if test=\"contractManual.count != null\"> count, </if>" +
                " <if test=\"contractManual.status != null\"> status, </if>" +
                " <if test=\"contractManual.pauseDate != null\"> pause_date, </if>" +
                " <if test=\"contractManual.deadDate != null\"> dead_date, </if>" +
                " <if test=\"contractManual.remark != null\"> remark, </if>" +
                " <if test=\"contractManual.feature != null\"> feature, </if>" +
                " <if test=\"contractManual.dealFlag != null\"> deal_flag, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"contractManual.contractId != null\"> #{contractManual.contractId}, </if>" +
                " <if test=\"contractManual.cardNo != null\"> #{contractManual.cardNo}, </if>" +
                " <if test=\"contractManual.memberName != null\"> #{contractManual.memberName}, </if>" +
                " <if test=\"contractManual.phone != null\"> #{contractManual.phone}, </if>" +
                " <if test=\"contractManual.cardType != null\"> #{contractManual.cardType}, </if>" +
                " <if test=\"contractManual.storeName != null\"> #{contractManual.storeName}, </if>" +
                " <if test=\"contractManual.salesman != null\"> #{contractManual.salesman}, </if>" +
                " <if test=\"contractManual.salesmanPhone != null\"> #{contractManual.salesmanPhone}, </if>" +
                " <if test=\"contractManual.coach != null\"> #{contractManual.coach}, </if>" +
                " <if test=\"contractManual.coachPhone != null\"> #{contractManual.coachPhone}, </if>" +
                " <if test=\"contractManual.total != null\"> #{contractManual.total}, </if>" +
                " <if test=\"contractManual.money != null\"> #{contractManual.money}, </if>" +
                " <if test=\"contractManual.type != null\"> #{contractManual.type}, </if>" +
                " <if test=\"contractManual.payType != null\"> #{contractManual.payType}, </if>" +
                " <if test=\"contractManual.price != null\"> #{contractManual.price}, </if>" +
                " <if test=\"contractManual.startDate != null\"> #{contractManual.startDate}, </if>" +
                " <if test=\"contractManual.endDate != null\"> #{contractManual.endDate}, </if>" +
                " <if test=\"contractManual.realFee != null\"> #{contractManual.realFee}, </if>" +
                " <if test=\"contractManual.count != null\"> #{contractManual.count}, </if>" +
                " <if test=\"contractManual.status != null\"> #{contractManual.status}, </if>" +
                " <if test=\"contractManual.pauseDate != null\"> #{contractManual.pauseDate}, </if>" +
                " <if test=\"contractManual.deadDate != null\"> #{contractManual.deadDate}, </if>" +
                " <if test=\"contractManual.remark != null\"> #{contractManual.remark}, </if>" +
                " <if test=\"contractManual.feature != null\"> #{contractManual.feature}, </if>" +
                " <if test=\"contractManual.dealFlag != null\"> #{contractManual.dealFlag}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("contractManual") ContractManualEntity contractManual);

    @Select("<script> SELECT pk_id,contract_id,card_no,member_name,phone,card_type,store_name,salesman,salesman_phone,coach,coach_phone,total,money,type,pay_type,price,start_date,end_date,real_fee,count,status,pause_date,dead_date,remark,feature,deal_flag,created,modified " +
            " FROM contract_manual " +
            " WHERE 1 = 1 " +
            " <if test=\"query.contractId != null\"> AND contract_id = #{query.contractId} </if>" +
            " <if test=\"query.cardNo != null\"> AND card_no = #{query.cardNo} </if>" +
            " <if test=\"query.memberName != null\"> AND member_name = #{query.memberName} </if>" +
            " <if test=\"query.phone != null\"> AND phone = #{query.phone} </if>" +
            " <if test=\"query.cardType != null\"> AND card_type = #{query.cardType} </if>" +
            " <if test=\"query.storeName != null\"> AND store_name = #{query.storeName} </if>" +
            " <if test=\"query.salesman != null\"> AND salesman = #{query.salesman} </if>" +
            " <if test=\"query.salesmanPhone != null\"> AND salesman_phone = #{query.salesmanPhone} </if>" +
            " <if test=\"query.coach != null\"> AND coach = #{query.coach} </if>" +
            " <if test=\"query.coachPhone != null\"> AND coach_phone = #{query.coachPhone} </if>" +
            " <if test=\"query.total != null\"> AND total = #{query.total} </if>" +
            " <if test=\"query.money != null\"> AND money = #{query.money} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.payType != null\"> AND pay_type = #{query.payType} </if>" +
            " <if test=\"query.price != null\"> AND price = #{query.price} </if>" +
            " <if test=\"query.startDate != null\"> AND start_date = #{query.startDate} </if>" +
            " <if test=\"query.endDate != null\"> AND end_date = #{query.endDate} </if>" +
            " <if test=\"query.realFee != null\"> AND real_fee = #{query.realFee} </if>" +
            " <if test=\"query.count != null\"> AND count = #{query.count} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.pauseDate != null\"> AND pause_date = #{query.pauseDate} </if>" +
            " <if test=\"query.deadDate != null\"> AND dead_date = #{query.deadDate} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.dealFlag != null\"> AND deal_flag = #{query.dealFlag} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<ContractManualEntity> find(@Param("query") ContractManualQuery contractManual , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM contract_manual " +
            " WHERE 1 = 1 " +
            " <if test=\"query.contractId != null\"> AND contract_id = #{query.contractId} </if>" +
            " <if test=\"query.cardNo != null\"> AND card_no = #{query.cardNo} </if>" +
            " <if test=\"query.memberName != null\"> AND member_name = #{query.memberName} </if>" +
            " <if test=\"query.phone != null\"> AND phone = #{query.phone} </if>" +
            " <if test=\"query.cardType != null\"> AND card_type = #{query.cardType} </if>" +
            " <if test=\"query.storeName != null\"> AND store_name = #{query.storeName} </if>" +
            " <if test=\"query.salesman != null\"> AND salesman = #{query.salesman} </if>" +
            " <if test=\"query.salesmanPhone != null\"> AND salesman_phone = #{query.salesmanPhone} </if>" +
            " <if test=\"query.coach != null\"> AND coach = #{query.coach} </if>" +
            " <if test=\"query.coachPhone != null\"> AND coach_phone = #{query.coachPhone} </if>" +
            " <if test=\"query.total != null\"> AND total = #{query.total} </if>" +
            " <if test=\"query.money != null\"> AND money = #{query.money} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.payType != null\"> AND pay_type = #{query.payType} </if>" +
            " <if test=\"query.price != null\"> AND price = #{query.price} </if>" +
            " <if test=\"query.startDate != null\"> AND start_date = #{query.startDate} </if>" +
            " <if test=\"query.endDate != null\"> AND end_date = #{query.endDate} </if>" +
            " <if test=\"query.realFee != null\"> AND real_fee = #{query.realFee} </if>" +
            " <if test=\"query.count != null\"> AND count = #{query.count} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.pauseDate != null\"> AND pause_date = #{query.pauseDate} </if>" +
            " <if test=\"query.deadDate != null\"> AND dead_date = #{query.deadDate} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.dealFlag != null\"> AND deal_flag = #{query.dealFlag} </if>" +
            "</script>")
    Long count(@Param("query") ContractManualQuery contractManual);

    @Select("<script> SELECT pk_id,contract_id,card_no,member_name,phone,card_type,store_name,salesman,salesman_phone,coach,coach_phone,total,money,type,pay_type,price,start_date,end_date,real_fee,count,status,pause_date,dead_date,remark,feature,deal_flag,created,modified " +
            " FROM contract_manual " +
            " WHERE contract_id = #{id} " +
            "</script>")
    ContractManualEntity getById(@Param("id") String id);

    @Update("<script> UPDATE contract_manual SET " +
                " <if test=\"contractManual.contractId != null\"> contract_id = #{contractManual.contractId} , </if>" +
                " <if test=\"contractManual.cardNo != null\"> card_no = #{contractManual.cardNo} , </if>" +
                " <if test=\"contractManual.memberName != null\"> member_name = #{contractManual.memberName} , </if>" +
                " <if test=\"contractManual.phone != null\"> phone = #{contractManual.phone} , </if>" +
                " <if test=\"contractManual.cardType != null\"> card_type = #{contractManual.cardType} , </if>" +
                " <if test=\"contractManual.storeName != null\"> store_name = #{contractManual.storeName} , </if>" +
                " <if test=\"contractManual.salesman != null\"> salesman = #{contractManual.salesman} , </if>" +
                " <if test=\"contractManual.salesmanPhone != null\"> salesman_phone = #{contractManual.salesmanPhone} , </if>" +
                " <if test=\"contractManual.coach != null\"> coach = #{contractManual.coach} , </if>" +
                " <if test=\"contractManual.coachPhone != null\"> coach_phone = #{contractManual.coachPhone} , </if>" +
                " <if test=\"contractManual.total != null\"> total = #{contractManual.total} , </if>" +
                " <if test=\"contractManual.money != null\"> money = #{contractManual.money} , </if>" +
                " <if test=\"contractManual.type != null\"> type = #{contractManual.type} , </if>" +
                " <if test=\"contractManual.payType != null\"> pay_type = #{contractManual.payType} , </if>" +
                " <if test=\"contractManual.price != null\"> price = #{contractManual.price} , </if>" +
                " <if test=\"contractManual.startDate != null\"> start_date = #{contractManual.startDate} , </if>" +
                " <if test=\"contractManual.endDate != null\"> end_date = #{contractManual.endDate} , </if>" +
                " <if test=\"contractManual.realFee != null\"> real_fee = #{contractManual.realFee} , </if>" +
                " <if test=\"contractManual.count != null\"> count = #{contractManual.count} , </if>" +
                " <if test=\"contractManual.status != null\"> status = #{contractManual.status} , </if>" +
                " <if test=\"contractManual.pauseDate != null\"> pause_date = #{contractManual.pauseDate} , </if>" +
                " <if test=\"contractManual.deadDate != null\"> dead_date = #{contractManual.deadDate} , </if>" +
                " <if test=\"contractManual.remark != null\"> remark = #{contractManual.remark} , </if>" +
                " <if test=\"contractManual.feature != null\"> feature = #{contractManual.feature} , </if>" +
                " <if test=\"contractManual.dealFlag != null\"> deal_flag = #{contractManual.dealFlag} , </if>" +
                " modified = now() " +
            " WHERE contract_id = #{contractManual.contractId} " +
            "</script>")
    int update(@Param("contractManual") ContractManualEntity contractManual);

    @Update("<script> DELETE  FROM contract_manual " +
            " WHERE contract_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


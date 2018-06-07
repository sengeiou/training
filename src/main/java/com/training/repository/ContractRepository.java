package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * contract 数据库操作类
 * Created by huai23 on 2018-06-07 23:34:46.
 */ 
@Mapper
public interface ContractRepository {

    @Insert("<script> INSERT INTO contract ( " +
                " <if test=\"contract.processInstanceId != null\"> process_instance_id, </if>" +
                " <if test=\"contract.contractId != null\"> contract_id, </if>" +
                " <if test=\"contract.contractName != null\"> contract_name, </if>" +
                " <if test=\"contract.memberName != null\"> member_name, </if>" +
                " <if test=\"contract.gender != null\"> gender, </if>" +
                " <if test=\"contract.phone != null\"> phone, </if>" +
                " <if test=\"contract.cardType != null\"> card_type, </if>" +
                " <if test=\"contract.type != null\"> type, </if>" +
                " <if test=\"contract.money != null\"> money, </if>" +
                " <if test=\"contract.total != null\"> total, </if>" +
                " <if test=\"contract.payType != null\"> pay_type, </if>" +
                " <if test=\"contract.startDate != null\"> start_date, </if>" +
                " <if test=\"contract.endDate != null\"> end_date, </if>" +
                " <if test=\"contract.salesman != null\"> salesman, </if>" +
                " <if test=\"contract.coach != null\"> coach, </if>" +
                " <if test=\"contract.feature != null\"> feature, </if>" +
                " <if test=\"contract.remark != null\"> remark, </if>" +
                " <if test=\"contract.signDate != null\"> sign_date, </if>" +
                " <if test=\"contract.image != null\"> image, </if>" +
                " <if test=\"contract.status != null\"> status, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"contract.processInstanceId != null\"> #{contract.processInstanceId}, </if>" +
                " <if test=\"contract.contractId != null\"> #{contract.contractId}, </if>" +
                " <if test=\"contract.contractName != null\"> #{contract.contractName}, </if>" +
                " <if test=\"contract.memberName != null\"> #{contract.memberName}, </if>" +
                " <if test=\"contract.gender != null\"> #{contract.gender}, </if>" +
                " <if test=\"contract.phone != null\"> #{contract.phone}, </if>" +
                " <if test=\"contract.cardType != null\"> #{contract.cardType}, </if>" +
                " <if test=\"contract.type != null\"> #{contract.type}, </if>" +
                " <if test=\"contract.money != null\"> #{contract.money}, </if>" +
                " <if test=\"contract.total != null\"> #{contract.total}, </if>" +
                " <if test=\"contract.payType != null\"> #{contract.payType}, </if>" +
                " <if test=\"contract.startDate != null\"> #{contract.startDate}, </if>" +
                " <if test=\"contract.endDate != null\"> #{contract.endDate}, </if>" +
                " <if test=\"contract.salesman != null\"> #{contract.salesman}, </if>" +
                " <if test=\"contract.coach != null\"> #{contract.coach}, </if>" +
                " <if test=\"contract.feature != null\"> #{contract.feature}, </if>" +
                " <if test=\"contract.remark != null\"> #{contract.remark}, </if>" +
                " <if test=\"contract.signDate != null\"> #{contract.signDate}, </if>" +
                " <if test=\"contract.image != null\"> #{contract.image}, </if>" +
                " <if test=\"contract.status != null\"> #{contract.status}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("contract") ContractEntity contract);

    @Select("<script> SELECT pk_id,process_instance_id,contract_id,contract_name,member_name,gender,phone,card_type,type,money,total,pay_type,start_date,end_date,salesman,coach,feature,remark,sign_date,image,status,created,modified " +
            " FROM contract " +
            " WHERE 1 = 1 " +
            " <if test=\"query.processInstanceId != null\"> AND process_instance_id = #{query.processInstanceId} </if>" +
            " <if test=\"query.contractId != null\"> AND contract_id = #{query.contractId} </if>" +
            " <if test=\"query.contractName != null\"> AND contract_name = #{query.contractName} </if>" +
            " <if test=\"query.memberName != null\"> AND member_name = #{query.memberName} </if>" +
            " <if test=\"query.gender != null\"> AND gender = #{query.gender} </if>" +
            " <if test=\"query.phone != null\"> AND phone = #{query.phone} </if>" +
            " <if test=\"query.cardType != null\"> AND card_type = #{query.cardType} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.money != null\"> AND money = #{query.money} </if>" +
            " <if test=\"query.total != null\"> AND total = #{query.total} </if>" +
            " <if test=\"query.payType != null\"> AND pay_type = #{query.payType} </if>" +
            " <if test=\"query.startDate != null\"> AND start_date = #{query.startDate} </if>" +
            " <if test=\"query.endDate != null\"> AND end_date = #{query.endDate} </if>" +
            " <if test=\"query.salesman != null\"> AND salesman = #{query.salesman} </if>" +
            " <if test=\"query.coach != null\"> AND coach = #{query.coach} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.signDate != null\"> AND sign_date = #{query.signDate} </if>" +
            " <if test=\"query.image != null\"> AND image = #{query.image} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<ContractEntity> find(@Param("query") ContractQuery contract , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM contract " +
            " WHERE 1 = 1 " +
            " <if test=\"query.processInstanceId != null\"> AND process_instance_id = #{query.processInstanceId} </if>" +
            " <if test=\"query.contractId != null\"> AND contract_id = #{query.contractId} </if>" +
            " <if test=\"query.contractName != null\"> AND contract_name = #{query.contractName} </if>" +
            " <if test=\"query.memberName != null\"> AND member_name = #{query.memberName} </if>" +
            " <if test=\"query.gender != null\"> AND gender = #{query.gender} </if>" +
            " <if test=\"query.phone != null\"> AND phone = #{query.phone} </if>" +
            " <if test=\"query.cardType != null\"> AND card_type = #{query.cardType} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.money != null\"> AND money = #{query.money} </if>" +
            " <if test=\"query.total != null\"> AND total = #{query.total} </if>" +
            " <if test=\"query.payType != null\"> AND pay_type = #{query.payType} </if>" +
            " <if test=\"query.startDate != null\"> AND start_date = #{query.startDate} </if>" +
            " <if test=\"query.endDate != null\"> AND end_date = #{query.endDate} </if>" +
            " <if test=\"query.salesman != null\"> AND salesman = #{query.salesman} </if>" +
            " <if test=\"query.coach != null\"> AND coach = #{query.coach} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.signDate != null\"> AND sign_date = #{query.signDate} </if>" +
            " <if test=\"query.image != null\"> AND image = #{query.image} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            "</script>")
    Long count(@Param("query") ContractQuery contract);

    @Select("<script> SELECT pk_id,process_instance_id,contract_id,contract_name,member_name,gender,phone,card_type,type,money,total,pay_type,start_date,end_date,salesman,coach,feature,remark,sign_date,image,status,created,modified " +
            " FROM contract " +
            " WHERE process_instance_id = #{id} " +
            "</script>")
    ContractEntity getById(@Param("id") String id);

    @Update("<script> UPDATE contract SET " +
                " <if test=\"contract.processInstanceId != null\"> process_instance_id = #{contract.processInstanceId} , </if>" +
                " <if test=\"contract.contractId != null\"> contract_id = #{contract.contractId} , </if>" +
                " <if test=\"contract.contractName != null\"> contract_name = #{contract.contractName} , </if>" +
                " <if test=\"contract.memberName != null\"> member_name = #{contract.memberName} , </if>" +
                " <if test=\"contract.gender != null\"> gender = #{contract.gender} , </if>" +
                " <if test=\"contract.phone != null\"> phone = #{contract.phone} , </if>" +
                " <if test=\"contract.cardType != null\"> card_type = #{contract.cardType} , </if>" +
                " <if test=\"contract.type != null\"> type = #{contract.type} , </if>" +
                " <if test=\"contract.money != null\"> money = #{contract.money} , </if>" +
                " <if test=\"contract.total != null\"> total = #{contract.total} , </if>" +
                " <if test=\"contract.payType != null\"> pay_type = #{contract.payType} , </if>" +
                " <if test=\"contract.startDate != null\"> start_date = #{contract.startDate} , </if>" +
                " <if test=\"contract.endDate != null\"> end_date = #{contract.endDate} , </if>" +
                " <if test=\"contract.salesman != null\"> salesman = #{contract.salesman} , </if>" +
                " <if test=\"contract.coach != null\"> coach = #{contract.coach} , </if>" +
                " <if test=\"contract.feature != null\"> feature = #{contract.feature} , </if>" +
                " <if test=\"contract.remark != null\"> remark = #{contract.remark} , </if>" +
                " <if test=\"contract.signDate != null\"> sign_date = #{contract.signDate} , </if>" +
                " <if test=\"contract.image != null\"> image = #{contract.image} , </if>" +
                " <if test=\"contract.status != null\"> status = #{contract.status} , </if>" +
                " modified = now() " +
            " WHERE process_instance_id = #{contract.processInstanceId} " +
            "</script>")
    int update(@Param("contract") ContractEntity contract);

    @Update("<script> DELETE  FROM contract " +
            " WHERE process_instance_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


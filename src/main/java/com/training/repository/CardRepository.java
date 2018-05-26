package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * card 数据库操作类
 * Created by huai23 on 2018-05-26 13:53:45.
 */ 
@Mapper
public interface CardRepository {

    @Insert("<script> INSERT INTO card ( " +
                " <if test=\"card.cardId != null\"> card_id, </if>" +
                " <if test=\"card.cardName != null\"> card_name, </if>" +
                " <if test=\"card.type != null\"> type, </if>" +
                " <if test=\"card.price != null\"> price, </if>" +
                " <if test=\"card.total != null\"> total, </if>" +
                " <if test=\"card.days != null\"> days, </if>" +
                " <if test=\"card.startDate != null\"> start_date, </if>" +
                " <if test=\"card.endDate != null\"> end_date, </if>" +
                " <if test=\"card.desc != null\"> desc, </if>" +
                " <if test=\"card.feature != null\"> feature, </if>" +
                " <if test=\"card.remark != null\"> remark, </if>" +
                " <if test=\"card.status != null\"> status, </if>" +
                " <if test=\"card.creater != null\"> creater, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"card.cardId != null\"> #{card.cardId}, </if>" +
                " <if test=\"card.cardName != null\"> #{card.cardName}, </if>" +
                " <if test=\"card.type != null\"> #{card.type}, </if>" +
                " <if test=\"card.price != null\"> #{card.price}, </if>" +
                " <if test=\"card.total != null\"> #{card.total}, </if>" +
                " <if test=\"card.days != null\"> #{card.days}, </if>" +
                " <if test=\"card.startDate != null\"> #{card.startDate}, </if>" +
                " <if test=\"card.endDate != null\"> #{card.endDate}, </if>" +
                " <if test=\"card.desc != null\"> #{card.desc}, </if>" +
                " <if test=\"card.feature != null\"> #{card.feature}, </if>" +
                " <if test=\"card.remark != null\"> #{card.remark}, </if>" +
                " <if test=\"card.status != null\"> #{card.status}, </if>" +
                " <if test=\"card.creater != null\"> #{card.creater}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("card") CardEntity card);

    @Select("<script> SELECT pk_id,card_id,card_name,type,price,total,days,start_date,end_date,desc,feature,remark,status,creater,created,modified " +
            " FROM card " +
            " WHERE 1 = 1 " +
            " <if test=\"query.cardId != null\"> AND card_id = #{query.cardId} </if>" +
            " <if test=\"query.cardName != null\"> AND card_name = #{query.cardName} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.price != null\"> AND price = #{query.price} </if>" +
            " <if test=\"query.total != null\"> AND total = #{query.total} </if>" +
            " <if test=\"query.days != null\"> AND days = #{query.days} </if>" +
            " <if test=\"query.startDate != null\"> AND start_date = #{query.startDate} </if>" +
            " <if test=\"query.endDate != null\"> AND end_date = #{query.endDate} </if>" +
            " <if test=\"query.desc != null\"> AND desc = #{query.desc} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.creater != null\"> AND creater = #{query.creater} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<CardEntity> find(@Param("query") CardQuery card , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM card " +
            " WHERE 1 = 1 " +
            " <if test=\"query.cardId != null\"> AND card_id = #{query.cardId} </if>" +
            " <if test=\"query.cardName != null\"> AND card_name = #{query.cardName} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.price != null\"> AND price = #{query.price} </if>" +
            " <if test=\"query.total != null\"> AND total = #{query.total} </if>" +
            " <if test=\"query.days != null\"> AND days = #{query.days} </if>" +
            " <if test=\"query.startDate != null\"> AND start_date = #{query.startDate} </if>" +
            " <if test=\"query.endDate != null\"> AND end_date = #{query.endDate} </if>" +
            " <if test=\"query.desc != null\"> AND desc = #{query.desc} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.creater != null\"> AND creater = #{query.creater} </if>" +
            "</script>")
    Long count(@Param("query") CardQuery card);

    @Select("<script> SELECT pk_id,card_id,card_name,type,price,total,days,start_date,end_date,desc,feature,remark,status,creater,created,modified " +
            " FROM card " +
            " WHERE card_id = #{id} " +
            "</script>")
    CardEntity getById(@Param("id") String id);

    @Update("<script> UPDATE card SET " +
                " <if test=\"card.cardId != null\"> card_id = #{card.cardId} , </if>" +
                " <if test=\"card.cardName != null\"> card_name = #{card.cardName} , </if>" +
                " <if test=\"card.type != null\"> type = #{card.type} , </if>" +
                " <if test=\"card.price != null\"> price = #{card.price} , </if>" +
                " <if test=\"card.total != null\"> total = #{card.total} , </if>" +
                " <if test=\"card.days != null\"> days = #{card.days} , </if>" +
                " <if test=\"card.startDate != null\"> start_date = #{card.startDate} , </if>" +
                " <if test=\"card.endDate != null\"> end_date = #{card.endDate} , </if>" +
                " <if test=\"card.desc != null\"> desc = #{card.desc} , </if>" +
                " <if test=\"card.feature != null\"> feature = #{card.feature} , </if>" +
                " <if test=\"card.remark != null\"> remark = #{card.remark} , </if>" +
                " <if test=\"card.status != null\"> status = #{card.status} , </if>" +
                " <if test=\"card.creater != null\"> creater = #{card.creater} , </if>" +
                " modified = now() " +
            " WHERE card_id = #{card.cardId} " +
            "</script>")
    int update(@Param("card") CardEntity card);

    @Update("<script> DELETE  FROM card " +
            " WHERE card_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


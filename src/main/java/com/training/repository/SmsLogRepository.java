package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * sms_log 数据库操作类
 * Created by huai23 on 2018-10-24 08:29:13.
 */ 
@Mapper
public interface SmsLogRepository {

    @Insert("<script> INSERT INTO sms_log ( " +
                " <if test=\"smsLog.logId != null\"> LOG_ID, </if>" +
                " <if test=\"smsLog.type != null\"> TYPE, </if>" +
                " <if test=\"smsLog.storeId != null\"> store_id, </if>" +
                " <if test=\"smsLog.memberId != null\"> member_id, </if>" +
                " <if test=\"smsLog.staffId != null\"> staff_id, </if>" +
                " <if test=\"smsLog.cardNo != null\"> card_no, </if>" +
                " <if test=\"smsLog.trainingId != null\"> training_id, </if>" +
                " <if test=\"smsLog.relId != null\"> rel_id, </if>" +
                " <if test=\"smsLog.phone != null\"> phone, </if>" +
                " <if test=\"smsLog.content != null\"> content, </if>" +
                " <if test=\"smsLog.status != null\"> status, </if>" +
                " <if test=\"smsLog.sendTime != null\"> send_time, </if>" +
                " <if test=\"smsLog.remark != null\"> REMARK, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"smsLog.logId != null\"> #{smsLog.logId}, </if>" +
                " <if test=\"smsLog.type != null\"> #{smsLog.type}, </if>" +
                " <if test=\"smsLog.storeId != null\"> #{smsLog.storeId}, </if>" +
                " <if test=\"smsLog.memberId != null\"> #{smsLog.memberId}, </if>" +
                " <if test=\"smsLog.staffId != null\"> #{smsLog.staffId}, </if>" +
                " <if test=\"smsLog.cardNo != null\"> #{smsLog.cardNo}, </if>" +
                " <if test=\"smsLog.trainingId != null\"> #{smsLog.trainingId}, </if>" +
                " <if test=\"smsLog.relId != null\"> #{smsLog.relId}, </if>" +
                " <if test=\"smsLog.phone != null\"> #{smsLog.phone}, </if>" +
                " <if test=\"smsLog.content != null\"> #{smsLog.content}, </if>" +
                " <if test=\"smsLog.status != null\"> #{smsLog.status}, </if>" +
                " <if test=\"smsLog.sendTime != null\"> #{smsLog.sendTime}, </if>" +
                " <if test=\"smsLog.remark != null\"> #{smsLog.remark}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("smsLog") SmsLogEntity smsLog);

    @Select("<script> SELECT pk_id,LOG_ID,TYPE,store_id,member_id,staff_id,card_no,training_id,rel_id,phone,content,status,send_time,REMARK,created,modified " +
            " FROM sms_log " +
            " WHERE 1 = 1 " +
            " <if test=\"query.logId != null\"> AND LOG_ID = #{query.logId} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.cardNo != null\"> AND card_no = #{query.cardNo} </if>" +
            " <if test=\"query.trainingId != null\"> AND training_id = #{query.trainingId} </if>" +
            " <if test=\"query.relId != null\"> AND rel_id = #{query.relId} </if>" +
            " <if test=\"query.phone != null\"> AND phone = #{query.phone} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.sendTime != null\"> AND send_time = #{query.sendTime} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<SmsLogEntity> find(@Param("query") SmsLogQuery smsLog , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM sms_log " +
            " WHERE 1 = 1 " +
            " <if test=\"query.logId != null\"> AND LOG_ID = #{query.logId} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.cardNo != null\"> AND card_no = #{query.cardNo} </if>" +
            " <if test=\"query.trainingId != null\"> AND training_id = #{query.trainingId} </if>" +
            " <if test=\"query.relId != null\"> AND rel_id = #{query.relId} </if>" +
            " <if test=\"query.phone != null\"> AND phone = #{query.phone} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.sendTime != null\"> AND send_time = #{query.sendTime} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            "</script>")
    Long count(@Param("query") SmsLogQuery smsLog);

    @Select("<script> SELECT pk_id,LOG_ID,TYPE,store_id,member_id,staff_id,card_no,training_id,rel_id,phone,content,status,send_time,REMARK,created,modified " +
            " FROM sms_log " +
            " WHERE LOG_ID = #{id} " +
            "</script>")
    SmsLogEntity getById(@Param("id") String id);

    @Update("<script> UPDATE sms_log SET " +
                " <if test=\"smsLog.logId != null\"> LOG_ID = #{smsLog.logId} , </if>" +
                " <if test=\"smsLog.type != null\"> TYPE = #{smsLog.type} , </if>" +
                " <if test=\"smsLog.storeId != null\"> store_id = #{smsLog.storeId} , </if>" +
                " <if test=\"smsLog.memberId != null\"> member_id = #{smsLog.memberId} , </if>" +
                " <if test=\"smsLog.staffId != null\"> staff_id = #{smsLog.staffId} , </if>" +
                " <if test=\"smsLog.cardNo != null\"> card_no = #{smsLog.cardNo} , </if>" +
                " <if test=\"smsLog.trainingId != null\"> training_id = #{smsLog.trainingId} , </if>" +
                " <if test=\"smsLog.relId != null\"> rel_id = #{smsLog.relId} , </if>" +
                " <if test=\"smsLog.phone != null\"> phone = #{smsLog.phone} , </if>" +
                " <if test=\"smsLog.content != null\"> content = #{smsLog.content} , </if>" +
                " <if test=\"smsLog.status != null\"> status = #{smsLog.status} , </if>" +
                " <if test=\"smsLog.sendTime != null\"> send_time = #{smsLog.sendTime} , </if>" +
                " <if test=\"smsLog.remark != null\"> REMARK = #{smsLog.remark} , </if>" +
                " modified = now() " +
            " WHERE LOG_ID = #{smsLog.logId} " +
            "</script>")
    int update(@Param("smsLog") SmsLogEntity smsLog);

    @Update("<script> DELETE  FROM sms_log " +
            " WHERE LOG_ID = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


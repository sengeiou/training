package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * feedback_log 数据库操作类
 * Created by huai23 on 2018-09-26 00:21:14.
 */ 
@Mapper
public interface FeedbackLogRepository {

    @Insert("<script> INSERT INTO feedback_log ( " +
                " <if test=\"feedbackLog.logId != null\"> LOG_ID, </if>" +
                " <if test=\"feedbackLog.type != null\"> TYPE, </if>" +
                " <if test=\"feedbackLog.content != null\"> content, </if>" +
                " <if test=\"feedbackLog.feedbackId != null\"> feedback_id, </if>" +
                " <if test=\"feedbackLog.memberId != null\"> member_id, </if>" +
                " <if test=\"feedbackLog.staffId != null\"> staff_id, </if>" +
                " <if test=\"feedbackLog.storeId != null\"> store_id, </if>" +
                " <if test=\"feedbackLog.remark != null\"> REMARK, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"feedbackLog.logId != null\"> #{feedbackLog.logId}, </if>" +
                " <if test=\"feedbackLog.type != null\"> #{feedbackLog.type}, </if>" +
                " <if test=\"feedbackLog.content != null\"> #{feedbackLog.content}, </if>" +
                " <if test=\"feedbackLog.feedbackId != null\"> #{feedbackLog.feedbackId}, </if>" +
                " <if test=\"feedbackLog.memberId != null\"> #{feedbackLog.memberId}, </if>" +
                " <if test=\"feedbackLog.staffId != null\"> #{feedbackLog.staffId}, </if>" +
                " <if test=\"feedbackLog.storeId != null\"> #{feedbackLog.storeId}, </if>" +
                " <if test=\"feedbackLog.remark != null\"> #{feedbackLog.remark}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("feedbackLog") FeedbackLogEntity feedbackLog);

    @Select("<script> SELECT pk_id,LOG_ID,TYPE,content,feedback_id,member_id,staff_id,store_id,REMARK,created,modified " +
            " FROM feedback_log " +
            " WHERE 1 = 1 " +
            " <if test=\"query.logId != null\"> AND LOG_ID = #{query.logId} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.feedbackId != null\"> AND feedback_id = #{query.feedbackId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            " order by pk_id desc LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<FeedbackLogEntity> find(@Param("query") FeedbackLogQuery feedbackLog , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM feedback_log " +
            " WHERE 1 = 1 " +
            " <if test=\"query.logId != null\"> AND LOG_ID = #{query.logId} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.feedbackId != null\"> AND feedback_id = #{query.feedbackId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            "</script>")
    Long count(@Param("query") FeedbackLogQuery feedbackLog);

    @Select("<script> SELECT pk_id,LOG_ID,TYPE,content,feedback_id,member_id,staff_id,store_id,REMARK,created,modified " +
            " FROM feedback_log " +
            " WHERE LOG_ID = #{id} " +
            "</script>")
    FeedbackLogEntity getById(@Param("id") String id);

    @Update("<script> UPDATE feedback_log SET " +
                " <if test=\"feedbackLog.logId != null\"> LOG_ID = #{feedbackLog.logId} , </if>" +
                " <if test=\"feedbackLog.type != null\"> TYPE = #{feedbackLog.type} , </if>" +
                " <if test=\"feedbackLog.content != null\"> content = #{feedbackLog.content} , </if>" +
                " <if test=\"feedbackLog.feedbackId != null\"> feedback_id = #{feedbackLog.feedbackId} , </if>" +
                " <if test=\"feedbackLog.memberId != null\"> member_id = #{feedbackLog.memberId} , </if>" +
                " <if test=\"feedbackLog.staffId != null\"> staff_id = #{feedbackLog.staffId} , </if>" +
                " <if test=\"feedbackLog.storeId != null\"> store_id = #{feedbackLog.storeId} , </if>" +
                " <if test=\"feedbackLog.remark != null\"> REMARK = #{feedbackLog.remark} , </if>" +
                " modified = now() " +
            " WHERE LOG_ID = #{feedbackLog.logId} " +
            "</script>")
    int update(@Param("feedbackLog") FeedbackLogEntity feedbackLog);

    @Update("<script> DELETE  FROM feedback_log " +
            " WHERE LOG_ID = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


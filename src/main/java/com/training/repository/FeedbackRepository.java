package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * feedback 数据库操作类
 * Created by huai23 on 2018-05-26 13:54:54.
 */ 
@Mapper
public interface FeedbackRepository {

    @Insert("<script> INSERT INTO feedback ( " +
                " <if test=\"feedback.feedbackId != null\"> feedback_id, </if>" +
                " <if test=\"feedback.memberId != null\"> member_id, </if>" +
                " <if test=\"feedback.type != null\"> type, </if>" +
                " <if test=\"feedback.title != null\"> title, </if>" +
                " <if test=\"feedback.content != null\"> content, </if>" +
                " <if test=\"feedback.image != null\"> image, </if>" +
                " <if test=\"feedback.remark != null\"> remark, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"feedback.feedbackId != null\"> #{feedback.feedbackId}, </if>" +
                " <if test=\"feedback.memberId != null\"> #{feedback.memberId}, </if>" +
                " <if test=\"feedback.type != null\"> #{feedback.type}, </if>" +
                " <if test=\"feedback.title != null\"> #{feedback.title}, </if>" +
                " <if test=\"feedback.content != null\"> #{feedback.content}, </if>" +
                " <if test=\"feedback.image != null\"> #{feedback.image}, </if>" +
                " <if test=\"feedback.remark != null\"> #{feedback.remark}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("feedback") FeedbackEntity feedback);

    @Select("<script> SELECT pk_id,feedback_id,member_id,type,title,content,image,remark,track_tag,status,created,modified " +
            " FROM feedback " +
            " WHERE 1 = 1 " +
            " <if test=\"query.feedbackId != null\"> AND feedback_id = #{query.feedbackId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.title != null\"> AND title like CONCAT('%',#{query.title},'%')  </if>" +
            " <if test=\"query.image != null\"> AND image = #{query.image} </if>" +
            " <if test=\"query.startDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d')  &gt;= #{query.startDate}  </if>" +
            " <if test=\"query.endDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d')  &lt;= #{query.endDate} </if>" +
            " <if test=\"query.phone != null\"> AND member_id in ( select member_id from member where type = 'M' AND phone like CONCAT('%',#{query.phone},'%')  ) </if>" +
            " <if test=\"query.storeId != null\"> AND member_id in ( select member_id  from member where type = 'M' AND coach_staff_id in ( select staff_id from staff where store_id = #{query.storeId}  )  ) </if>" +
            " order by pk_id desc LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<FeedbackEntity> find(@Param("query") FeedbackQuery feedback , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM feedback " +
            " WHERE 1 = 1 " +
            " <if test=\"query.feedbackId != null\"> AND feedback_id = #{query.feedbackId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.title != null\"> AND title like CONCAT('%',#{query.title},'%')  </if>" +
            " <if test=\"query.image != null\"> AND image = #{query.image} </if>" +
            " <if test=\"query.startDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d')  &gt;= #{query.startDate}  </if>" +
            " <if test=\"query.endDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d')  &lt;= #{query.endDate}  </if>" +
            " <if test=\"query.phone != null\"> AND member_id in ( select member_id from member where type = 'M' AND phone like CONCAT('%',#{query.phone},'%')  ) </if>" +
            " <if test=\"query.storeId != null\"> AND member_id in ( select member_id  from member where type = 'M' AND coach_staff_id in ( select staff_id from staff where store_id = #{query.storeId}  )  ) </if>" +
            "</script>")
    Long count(@Param("query") FeedbackQuery feedback);

    @Select("<script> SELECT pk_id,feedback_id,member_id,type,title,content,image,remark,track_tag,status,created,modified " +
            " FROM feedback " +
            " WHERE feedback_id = #{id} " +
            "</script>")
    FeedbackEntity getById(@Param("id") String id);

    @Update("<script> UPDATE feedback SET " +
//                " <if test=\"feedback.feedbackId != null\"> feedback_id = #{feedback.feedbackId} , </if>" +
//                " <if test=\"feedback.memberId != null\"> member_id = #{feedback.memberId} , </if>" +
//                " <if test=\"feedback.title != null\"> title = #{feedback.title} , </if>" +
//                " <if test=\"feedback.content != null\"> content = #{feedback.content} , </if>" +
//                " <if test=\"feedback.image != null\"> image = #{feedback.image} , </if>" +
//                " <if test=\"feedback.remark != null\"> remark = #{feedback.remark} , </if>" +
                " <if test=\"feedback.trackTag != null\"> track_tag = #{feedback.trackTag} , </if>" +
                " <if test=\"feedback.status != null\"> status = #{feedback.status} , </if>" +
                " modified = now() " +
            " WHERE feedback_id = #{feedback.feedbackId} " +
            "</script>")
    int update(@Param("feedback") FeedbackEntity feedback);

    @Update("<script> DELETE  FROM feedback " +
            " WHERE feedback_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * training 数据库操作类
 * Created by huai23 on 2018-05-26 17:09:14.
 */ 
@Mapper
public interface TrainingRepository {

    @Insert("<script> INSERT INTO training ( " +
                " <if test=\"training.trainingId != null\"> training_id, </if>" +
                " <if test=\"training.lessonId != null\"> lesson_id, </if>" +
                " <if test=\"training.title != null\"> title, </if>" +
                " <if test=\"training.storeId != null\"> store_id, </if>" +
                " <if test=\"training.type != null\"> type, </if>" +
                " <if test=\"training.memberId != null\"> member_id, </if>" +
                " <if test=\"training.coachId != null\"> coach_id, </if>" +
                " <if test=\"training.cardNo != null\"> card_no, </if>" +
                " <if test=\"training.cardType != null\"> card_type, </if>" +
                " <if test=\"training.lessonDate != null\"> lesson_date, </if>" +
                " <if test=\"training.startHour != null\"> start_hour, </if>" +
                " <if test=\"training.endHour != null\"> end_hour, </if>" +
                " <if test=\"training.trainingData != null\"> training_data, </if>" +
                " <if test=\"training.feature != null\"> feature, </if>" +
                " <if test=\"training.remark != null\"> remark, </if>" +
                " <if test=\"training.status != null\"> status, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"training.trainingId != null\"> #{training.trainingId}, </if>" +
                " <if test=\"training.lessonId != null\"> #{training.lessonId}, </if>" +
                " <if test=\"training.title != null\"> #{training.title}, </if>" +
                " <if test=\"training.storeId != null\"> #{training.storeId}, </if>" +
                " <if test=\"training.type != null\"> #{training.type}, </if>" +
                " <if test=\"training.memberId != null\"> #{training.memberId}, </if>" +
                " <if test=\"training.coachId != null\"> #{training.coachId}, </if>" +
                " <if test=\"training.cardNo != null\"> #{training.cardNo}, </if>" +
                " <if test=\"training.cardType != null\"> #{training.cardType}, </if>" +
                " <if test=\"training.lessonDate != null\"> #{training.lessonDate}, </if>" +
                " <if test=\"training.startHour != null\"> #{training.startHour}, </if>" +
                " <if test=\"training.endHour != null\"> #{training.endHour}, </if>" +
                " <if test=\"training.trainingData != null\"> #{training.trainingData}, </if>" +
                " <if test=\"training.feature != null\"> #{training.feature}, </if>" +
                " <if test=\"training.remark != null\"> #{training.remark}, </if>" +
                " <if test=\"training.status != null\"> #{training.status}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("training") TrainingEntity training);

    @Select("<script> SELECT pk_id,training_id,lesson_id,title,store_id,type,member_id,coach_id,card_no,card_type,lesson_date,start_hour,end_hour,training_data,sign_time,feature,remark,status,created,modified " +
            " FROM training " +
            " WHERE 1 = 1 " +
            " <if test=\"query.trainingId != null\"> AND training_id = #{query.trainingId} </if>" +
            " <if test=\"query.lessonId != null\"> AND lesson_id = #{query.lessonId} </if>" +
            " <if test=\"query.title != null\"> AND title = #{query.title} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.coachId != null\"> AND coach_id = #{query.coachId} </if>" +
            " <if test=\"query.cardNo != null\"> AND card_no = #{query.cardNo} </if>" +
            " <if test=\"query.cardType != null\"> AND card_type = #{query.cardType} </if>" +
            " <if test=\"query.lessonDate != null\"> AND lesson_date = #{query.lessonDate} </if>" +
            " <if test=\"query.startHour != null\"> AND start_hour = #{query.startHour} </if>" +
            " <if test=\"query.endHour != null\"> AND end_hour = #{query.endHour} </if>" +
            " <if test=\"query.trainingData != null\"> AND training_data = #{query.trainingData} </if>" +
            " <if test=\"query.startDate != null\"> AND lesson_date &gt;= #{query.startDate} </if>" +
            " <if test=\"query.endDate != null\"> AND lesson_date &lt;= #{query.endDate} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.isSign != null and query.isSign == '0'.toString() \"> AND sign_time = '' </if>" +
            " <if test=\"query.isSign != null and query.isSign == '1'.toString() \"> AND sign_time &gt; '' </if>" +
            " order by lesson_date DESC LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<TrainingEntity> find(@Param("query") TrainingQuery training , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM training " +
            " WHERE 1 = 1 " +
            " <if test=\"query.trainingId != null\"> AND training_id = #{query.trainingId} </if>" +
            " <if test=\"query.lessonId != null\"> AND lesson_id = #{query.lessonId} </if>" +
            " <if test=\"query.title != null\"> AND title = #{query.title} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.coachId != null\"> AND coach_id = #{query.coachId} </if>" +
            " <if test=\"query.cardNo != null\"> AND card_no = #{query.cardNo} </if>" +
            " <if test=\"query.cardType != null\"> AND card_type = #{query.cardType} </if>" +
            " <if test=\"query.lessonDate != null\"> AND lesson_date = #{query.lessonDate} </if>" +
            " <if test=\"query.startDate != null\"> AND lesson_date &gt;= #{query.startDate} </if>" +
            " <if test=\"query.endDate != null\"> AND lesson_date &lt;= #{query.endDate} </if>" +
            " <if test=\"query.startHour != null\"> AND start_hour = #{query.startHour} </if>" +
            " <if test=\"query.endHour != null\"> AND end_hour = #{query.endHour} </if>" +
            " <if test=\"query.trainingData != null\"> AND training_data = #{query.trainingData} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.isSign != null and query.isSign == '0'.toString() \"> AND sign_time = '' </if>" +
            " <if test=\"query.isSign != null and query.isSign == '1'.toString() \"> AND sign_time &gt; '' </if>" +
            "</script>")
    Long count(@Param("query") TrainingQuery training);

    @Select("<script> SELECT pk_id,training_id,lesson_id,title,store_id,type,member_id,coach_id,card_no,card_type,lesson_date,start_hour,end_hour,training_data,sign_time,feature,remark,status,created,modified " +
            " FROM training " +
            " WHERE training_id = #{id} " +
            "</script>")
    TrainingEntity getById(@Param("id") String id);

    @Update("<script> UPDATE training SET " +
                " <if test=\"training.trainingId != null\"> training_id = #{training.trainingId} , </if>" +
                " <if test=\"training.lessonId != null\"> lesson_id = #{training.lessonId} , </if>" +
                " <if test=\"training.title != null\"> title = #{training.title} , </if>" +
                " <if test=\"training.storeId != null\"> store_id = #{training.storeId} , </if>" +
                " <if test=\"training.type != null\"> type = #{training.type} , </if>" +
                " <if test=\"training.memberId != null\"> member_id = #{training.memberId} , </if>" +
                " <if test=\"training.coachId != null\"> coach_id = #{training.coachId} , </if>" +
                " <if test=\"training.cardNo != null\"> card_no = #{training.cardNo} , </if>" +
                " <if test=\"training.cardType != null\"> card_type = #{training.cardType} , </if>" +
                " <if test=\"training.lessonDate != null\"> lesson_date = #{training.lessonDate} , </if>" +
                " <if test=\"training.startHour != null\"> start_hour = #{training.startHour} , </if>" +
                " <if test=\"training.endHour != null\"> end_hour = #{training.endHour} , </if>" +
                " <if test=\"training.trainingData != null\"> training_data = #{training.trainingData} , </if>" +
                " <if test=\"training.feature != null\"> feature = #{training.feature} , </if>" +
                " <if test=\"training.remark != null\"> remark = #{training.remark} , </if>" +
                " <if test=\"training.status != null\"> status = #{training.status} , </if>" +
                " modified = now() " +
            " WHERE training_id = #{training.trainingId} " +
            "</script>")
    int update(@Param("training") TrainingEntity training);

    @Update("<script> DELETE  FROM training " +
            " WHERE training_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);

    @Update("<script> UPDATE training SET sign_time = #{training.signTime} , modified = now() " +
            " WHERE training_id = #{training.trainingId} " +
            "</script>")
    int signIn(@Param("training") TrainingEntity training);

}


package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * lesson 数据库操作类
 * Created by huai23 on 2018-05-26 17:02:18.
 */ 
@Mapper
public interface LessonRepository {

    @Insert("<script> INSERT INTO lesson ( " +
                " <if test=\"lesson.lessonId != null\"> lesson_id, </if>" +
                " <if test=\"lesson.storeId != null\"> store_id, </if>" +
                " <if test=\"lesson.title != null\"> title, </if>" +
                " <if test=\"lesson.type != null\"> type, </if>" +
                " <if test=\"lesson.coachId != null\"> coach_id, </if>" +
                " <if test=\"lesson.cardNo != null\"> card_no, </if>" +
                " <if test=\"lesson.lessonDate != null\"> lesson_date, </if>" +
                " <if test=\"lesson.startHour != null\"> start_hour, </if>" +
                " <if test=\"lesson.endHour != null\"> end_hour, </if>" +
                " <if test=\"lesson.quota != null\"> quota, </if>" +
                " <if test=\"lesson.trainingData != null\"> training_data, </if>" +
                " <if test=\"lesson.feature != null\"> feature, </if>" +
                " <if test=\"lesson.remark != null\"> remark, </if>" +
                " <if test=\"lesson.status != null\"> status, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"lesson.lessonId != null\"> #{lesson.lessonId}, </if>" +
                " <if test=\"lesson.storeId != null\"> #{lesson.storeId}, </if>" +
                " <if test=\"lesson.title != null\"> #{lesson.title}, </if>" +
                " <if test=\"lesson.type != null\"> #{lesson.type}, </if>" +
                " <if test=\"lesson.coachId != null\"> #{lesson.coachId}, </if>" +
                " <if test=\"lesson.cardNo != null\"> #{lesson.cardNo}, </if>" +
                " <if test=\"lesson.lessonDate != null\"> #{lesson.lessonDate}, </if>" +
                " <if test=\"lesson.startHour != null\"> #{lesson.startHour}, </if>" +
                " <if test=\"lesson.endHour != null\"> #{lesson.endHour}, </if>" +
                " <if test=\"lesson.quota != null\"> #{lesson.quota}, </if>" +
                " <if test=\"lesson.trainingData != null\"> #{lesson.trainingData}, </if>" +
                " <if test=\"lesson.feature != null\"> #{lesson.feature}, </if>" +
                " <if test=\"lesson.remark != null\"> #{lesson.remark}, </if>" +
                " <if test=\"lesson.status != null\"> #{lesson.status}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("lesson") LessonEntity lesson);

    @Select("<script> SELECT pk_id,lesson_id,store_id,title,type,coach_id,card_no,lesson_date,start_hour,end_hour,quota,training_data,feature,remark,status,created,modified " +
            " FROM lesson " +
            " WHERE 1 = 1 " +
            " <if test=\"query.lessonId != null\"> AND lesson_id = #{query.lessonId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.title != null\"> AND title = #{query.title} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.coachId != null\"> AND coach_id = #{query.coachId} </if>" +
            " <if test=\"query.cardNo != null\"> AND card_no = #{query.cardNo} </if>" +
            " <if test=\"query.lessonDate != null\"> AND lesson_date = #{query.lessonDate} </if>" +
            " <if test=\"query.startHour != null\"> AND start_hour = #{query.startHour} </if>" +
            " <if test=\"query.endHour != null\"> AND end_hour = #{query.endHour} </if>" +
            " <if test=\"query.quota != null\"> AND quota = #{query.quota} </if>" +
            " <if test=\"query.trainingData != null\"> AND training_data = #{query.trainingData} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<LessonEntity> find(@Param("query") LessonQuery lesson , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM lesson " +
            " WHERE 1 = 1 " +
            " <if test=\"query.lessonId != null\"> AND lesson_id = #{query.lessonId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.title != null\"> AND title = #{query.title} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.coachId != null\"> AND coach_id = #{query.coachId} </if>" +
            " <if test=\"query.cardNo != null\"> AND card_no = #{query.cardNo} </if>" +
            " <if test=\"query.lessonDate != null\"> AND lesson_date = #{query.lessonDate} </if>" +
            " <if test=\"query.startHour != null\"> AND start_hour = #{query.startHour} </if>" +
            " <if test=\"query.endHour != null\"> AND end_hour = #{query.endHour} </if>" +
            " <if test=\"query.quota != null\"> AND quota = #{query.quota} </if>" +
            " <if test=\"query.trainingData != null\"> AND training_data = #{query.trainingData} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            "</script>")
    Long count(@Param("query") LessonQuery lesson);

    @Select("<script> SELECT pk_id,lesson_id,store_id,title,type,coach_id,card_no,lesson_date,start_hour,end_hour,quota,training_data,feature,remark,status,created,modified " +
            " FROM lesson " +
            " WHERE lesson_id = #{id} " +
            "</script>")
    LessonEntity getById(@Param("id") String id);

    @Update("<script> UPDATE lesson SET " +
                " <if test=\"lesson.lessonId != null\"> lesson_id = #{lesson.lessonId} , </if>" +
                " <if test=\"lesson.storeId != null\"> store_id = #{lesson.storeId} , </if>" +
                " <if test=\"lesson.title != null\"> title = #{lesson.title} , </if>" +
                " <if test=\"lesson.type != null\"> type = #{lesson.type} , </if>" +
                " <if test=\"lesson.coachId != null\"> coach_id = #{lesson.coachId} , </if>" +
                " <if test=\"lesson.cardNo != null\"> card_no = #{lesson.cardNo} , </if>" +
                " <if test=\"lesson.lessonDate != null\"> lesson_date = #{lesson.lessonDate} , </if>" +
                " <if test=\"lesson.startHour != null\"> start_hour = #{lesson.startHour} , </if>" +
                " <if test=\"lesson.endHour != null\"> end_hour = #{lesson.endHour} , </if>" +
                " <if test=\"lesson.quota != null\"> quota = #{lesson.quota} , </if>" +
                " <if test=\"lesson.trainingData != null\"> training_data = #{lesson.trainingData} , </if>" +
                " <if test=\"lesson.feature != null\"> feature = #{lesson.feature} , </if>" +
                " <if test=\"lesson.remark != null\"> remark = #{lesson.remark} , </if>" +
                " <if test=\"lesson.status != null\"> status = #{lesson.status} , </if>" +
                " modified = now() " +
            " WHERE lesson_id = #{lesson.lessonId} " +
            "</script>")
    int update(@Param("lesson") LessonEntity lesson);

    @Update("<script> DELETE  FROM lesson " +
            " WHERE lesson_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


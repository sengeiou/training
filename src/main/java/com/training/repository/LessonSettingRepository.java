package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * lesson_setting 数据库操作类
 * Created by huai23 on 2018-06-16 08:59:33.
 */ 
@Mapper
public interface LessonSettingRepository {

    @Insert("<script> INSERT INTO lesson_setting ( " +
                " <if test=\"lessonSetting.lessonId != null\"> lesson_id, </if>" +
                " <if test=\"lessonSetting.storeId != null\"> store_id, </if>" +
                " <if test=\"lessonSetting.title != null\"> title, </if>" +
                " <if test=\"lessonSetting.classroom != null\"> classroom, </if>" +
                " <if test=\"lessonSetting.type != null\"> type, </if>" +
                " <if test=\"lessonSetting.coachId != null\"> coach_id, </if>" +
                " <if test=\"lessonSetting.startDate != null\"> start_date, </if>" +
                " <if test=\"lessonSetting.endDate != null\"> end_date, </if>" +
                " <if test=\"lessonSetting.startHour != null\"> start_hour, </if>" +
                " <if test=\"lessonSetting.endHour != null\"> end_hour, </if>" +
                " <if test=\"lessonSetting.quotaMin != null\"> quota_min, </if>" +
                " <if test=\"lessonSetting.quotaMax != null\"> quota_max, </if>" +
                " <if test=\"lessonSetting.weekRepeat != null\"> week_repeat, </if>" +
                " <if test=\"lessonSetting.feature != null\"> feature, </if>" +
                " <if test=\"lessonSetting.image != null\"> image, </if>" +
                " <if test=\"lessonSetting.remark != null\"> remark, </if>" +
                " <if test=\"lessonSetting.status != null\"> status, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"lessonSetting.lessonId != null\"> #{lessonSetting.lessonId}, </if>" +
                " <if test=\"lessonSetting.storeId != null\"> #{lessonSetting.storeId}, </if>" +
                " <if test=\"lessonSetting.title != null\"> #{lessonSetting.title}, </if>" +
                " <if test=\"lessonSetting.classroom != null\"> #{lessonSetting.classroom}, </if>" +
                " <if test=\"lessonSetting.type != null\"> #{lessonSetting.type}, </if>" +
                " <if test=\"lessonSetting.coachId != null\"> #{lessonSetting.coachId}, </if>" +
                " <if test=\"lessonSetting.startDate != null\"> #{lessonSetting.startDate}, </if>" +
                " <if test=\"lessonSetting.endDate != null\"> #{lessonSetting.endDate}, </if>" +
                " <if test=\"lessonSetting.startHour != null\"> #{lessonSetting.startHour}, </if>" +
                " <if test=\"lessonSetting.endHour != null\"> #{lessonSetting.endHour}, </if>" +
                " <if test=\"lessonSetting.quotaMin != null\"> #{lessonSetting.quotaMin}, </if>" +
                " <if test=\"lessonSetting.quotaMax != null\"> #{lessonSetting.quotaMax}, </if>" +
                " <if test=\"lessonSetting.weekRepeat != null\"> #{lessonSetting.weekRepeat}, </if>" +
                " <if test=\"lessonSetting.feature != null\"> #{lessonSetting.feature}, </if>" +
                " <if test=\"lessonSetting.image != null\"> #{lessonSetting.image}, </if>" +
                " <if test=\"lessonSetting.remark != null\"> #{lessonSetting.remark}, </if>" +
                " <if test=\"lessonSetting.status != null\"> #{lessonSetting.status}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("lessonSetting") LessonSettingEntity lessonSetting);

    @Select("<script> SELECT pk_id,lesson_id,store_id,title,classroom,type,coach_id,start_date,end_date,start_hour,end_hour,quota_min,quota_max,week_repeat,feature,image,remark,status,created,modified " +
            " FROM lesson_setting " +
            " WHERE 1 = 1 " +
            " <if test=\"query.lessonId != null\"> AND lesson_id = #{query.lessonId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.title != null\"> AND title = #{query.title} </if>" +
            " <if test=\"query.classroom != null\"> AND classroom = #{query.classroom} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.coachId != null\"> AND coach_id = #{query.coachId} </if>" +
            " <if test=\"query.startDate != null\"> AND start_date &lt;= #{query.startDate} </if>" +
            " <if test=\"query.endDate != null\"> AND end_date &gt; #{query.endDate} </if>" +
            " <if test=\"query.startHour != null\"> AND start_hour = #{query.startHour} </if>" +
            " <if test=\"query.endHour != null\"> AND end_hour = #{query.endHour} </if>" +
            " <if test=\"query.quotaMin != null\"> AND quota_min = #{query.quotaMin} </if>" +
            " <if test=\"query.quotaMax != null\"> AND quota_max = #{query.quotaMax} </if>" +
            " <if test=\"query.weekRepeat != null\"> AND week_repeat = #{query.weekRepeat} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<LessonSettingEntity> find(@Param("query") LessonSettingQuery lessonSetting , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM lesson_setting " +
            " WHERE 1 = 1 " +
            " <if test=\"query.lessonId != null\"> AND lesson_id = #{query.lessonId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.title != null\"> AND title = #{query.title} </if>" +
            " <if test=\"query.classroom != null\"> AND classroom = #{query.classroom} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.coachId != null\"> AND coach_id = #{query.coachId} </if>" +
            " <if test=\"query.startDate != null\"> AND start_date &lt;= #{query.startDate} </if>" +
            " <if test=\"query.endDate != null\"> AND end_date &gt; #{query.endDate} </if>" +
            " <if test=\"query.startHour != null\"> AND start_hour = #{query.startHour} </if>" +
            " <if test=\"query.endHour != null\"> AND end_hour = #{query.endHour} </if>" +
            " <if test=\"query.quotaMin != null\"> AND quota_min = #{query.quotaMin} </if>" +
            " <if test=\"query.quotaMax != null\"> AND quota_max = #{query.quotaMax} </if>" +
            " <if test=\"query.weekRepeat != null\"> AND week_repeat = #{query.weekRepeat} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            "</script>")
    Long count(@Param("query") LessonSettingQuery lessonSetting);

    @Select("<script> SELECT pk_id,lesson_id,store_id,title,classroom,type,coach_id,start_date,end_date,start_hour,end_hour,quota_min,quota_max,week_repeat,feature,image,remark,status,created,modified " +
            " FROM lesson_setting " +
            " WHERE lesson_id = #{id} " +
            "</script>")
    LessonSettingEntity getById(@Param("id") String id);

    @Update("<script> UPDATE lesson_setting SET " +
                " <if test=\"lessonSetting.lessonId != null\"> lesson_id = #{lessonSetting.lessonId} , </if>" +
                " <if test=\"lessonSetting.storeId != null\"> store_id = #{lessonSetting.storeId} , </if>" +
                " <if test=\"lessonSetting.title != null\"> title = #{lessonSetting.title} , </if>" +
                " <if test=\"lessonSetting.classroom != null\"> classroom = #{lessonSetting.classroom} , </if>" +
                " <if test=\"lessonSetting.type != null\"> type = #{lessonSetting.type} , </if>" +
                " <if test=\"lessonSetting.coachId != null\"> coach_id = #{lessonSetting.coachId} , </if>" +
                " <if test=\"lessonSetting.startDate != null\"> start_date = #{lessonSetting.startDate} , </if>" +
                " <if test=\"lessonSetting.endDate != null\"> end_date = #{lessonSetting.endDate} , </if>" +
                " <if test=\"lessonSetting.startHour != null\"> start_hour = #{lessonSetting.startHour} , </if>" +
                " <if test=\"lessonSetting.endHour != null\"> end_hour = #{lessonSetting.endHour} , </if>" +
                " <if test=\"lessonSetting.quotaMin != null\"> quota_min = #{lessonSetting.quotaMin} , </if>" +
                " <if test=\"lessonSetting.quotaMax != null\"> quota_max = #{lessonSetting.quotaMax} , </if>" +
                " <if test=\"lessonSetting.weekRepeat != null\"> week_repeat = #{lessonSetting.weekRepeat} , </if>" +
                " <if test=\"lessonSetting.feature != null\"> feature = #{lessonSetting.feature} , </if>" +
                " <if test=\"lessonSetting.remark != null\"> remark = #{lessonSetting.remark} , </if>" +
                " <if test=\"lessonSetting.status != null\"> status = #{lessonSetting.status} , </if>" +
                " modified = now() " +
            " WHERE lesson_id = #{lessonSetting.lessonId} " +
            "</script>")
    int update(@Param("lessonSetting") LessonSettingEntity lessonSetting);

    @Update("<script> DELETE  FROM lesson_setting " +
            " WHERE lesson_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


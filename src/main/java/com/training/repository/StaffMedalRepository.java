package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * staff_medal 数据库操作类
 * Created by huai23 on 2018-07-22 23:28:30.
 */ 
@Mapper
public interface StaffMedalRepository {

    @Insert("<script> INSERT INTO staff_medal ( " +
                " <if test=\"staffMedal.staffId != null\"> staff_id, </if>" +
                " <if test=\"staffMedal.medalId != null\"> medal_id, </if>" +
                " <if test=\"staffMedal.content != null\"> content, </if>" +
                " <if test=\"staffMedal.awardDate != null\"> award_date, </if>" +
                " <if test=\"staffMedal.feature != null\"> feature, </if>" +
                " <if test=\"staffMedal.remark != null\"> remark, </if>" +
                " <if test=\"staffMedal.status != null\"> status, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"staffMedal.staffId != null\"> #{staffMedal.staffId}, </if>" +
                " <if test=\"staffMedal.medalId != null\"> #{staffMedal.medalId}, </if>" +
                " <if test=\"staffMedal.content != null\"> #{staffMedal.content}, </if>" +
                " <if test=\"staffMedal.awardDate != null\"> #{staffMedal.awardDate}, </if>" +
                " <if test=\"staffMedal.feature != null\"> #{staffMedal.feature}, </if>" +
                " <if test=\"staffMedal.remark != null\"> #{staffMedal.remark}, </if>" +
                " <if test=\"staffMedal.status != null\"> #{staffMedal.status}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("staffMedal") StaffMedalEntity staffMedal);

    @Select("<script> SELECT pk_id,staff_id,medal_id,content,award_date,feature,remark,status,created,modified " +
            " FROM staff_medal " +
            " WHERE 1 = 1 " +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.medalId != null\"> AND medal_id = #{query.medalId} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.awardDate != null\"> AND award_date = #{query.awardDate} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<StaffMedalEntity> find(@Param("query") StaffMedalQuery staffMedal , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM staff_medal " +
            " WHERE 1 = 1 " +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.medalId != null\"> AND medal_id = #{query.medalId} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.awardDate != null\"> AND award_date = #{query.awardDate} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            "</script>")
    Long count(@Param("query") StaffMedalQuery staffMedal);

    @Select("<script> SELECT pk_id,staff_id,medal_id,content,award_date,feature,remark,status,created,modified " +
            " FROM staff_medal " +
            " WHERE staff_id = #{staffId} and medal_id = #{medalId} " +
            "</script>")
    StaffMedalEntity getById(@Param("staffId") String staffId,@Param("medalId") String medalId);

    @Select("<script> SELECT pk_id,staff_id,medal_id,content,award_date,feature,remark,status,created,modified " +
            " FROM staff_medal " +
            " WHERE staff_id = #{staffId} " +
            "</script>")
    List<StaffMedalEntity> getByStaffId(@Param("staffId") String staffId);

    @Update("<script> UPDATE staff_medal SET " +
                " <if test=\"staffMedal.staffId != null\"> staff_id = #{staffMedal.staffId} , </if>" +
                " <if test=\"staffMedal.medalId != null\"> medal_id = #{staffMedal.medalId} , </if>" +
                " <if test=\"staffMedal.content != null\"> content = #{staffMedal.content} , </if>" +
                " <if test=\"staffMedal.awardDate != null\"> award_date = #{staffMedal.awardDate} , </if>" +
                " <if test=\"staffMedal.feature != null\"> feature = #{staffMedal.feature} , </if>" +
                " <if test=\"staffMedal.remark != null\"> remark = #{staffMedal.remark} , </if>" +
                " <if test=\"staffMedal.status != null\"> status = #{staffMedal.status} , </if>" +
                " modified = now() " +
            " WHERE staff_id = #{staffMedal.staffId} " +
            "</script>")
    int update(@Param("staffMedal") StaffMedalEntity staffMedal);

//    @Update("<script> DELETE  FROM staff_medal " +
//            " WHERE staff_id = #{staffId} and medal_id = #{medalId} " +
//            "</script>")
//    int delete(@Param("staffId") String staffId,@Param("medalId") String medalId);

    @Update("<script> UPDATE staff_medal SET  status = -1 ，modified = now()  " +
            " WHERE staff_id = #{staffId} and medal_id = #{medalId} " +
            "</script>")
    int delete(@Param("staffId") String staffId,@Param("medalId") String medalId);


}


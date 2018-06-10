package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * member_body 数据库操作类
 * Created by huai23 on 2018-05-26 13:54:03.
 */ 
@Mapper
public interface MemberBodyRepository {

    @Insert("<script> INSERT INTO member_body ( " +
                " <if test=\"memberBody.bodyId != null\"> body_id, </if>" +
                " <if test=\"memberBody.memberId != null\"> member_id, </if>" +
                " <if test=\"memberBody.coachId != null\"> coach_id, </if>" +
                " <if test=\"memberBody.height != null\"> height, </if>" +
                " <if test=\"memberBody.weight != null\"> weight, </if>" +
                " <if test=\"memberBody.bmi != null\"> bmi, </if>" +
                " <if test=\"memberBody.fat != null\"> fat, </if>" +
                " <if test=\"memberBody.armLeft != null\"> arm_left, </if>" +
                " <if test=\"memberBody.armRight != null\"> arm_right, </if>" +
                " <if test=\"memberBody.waist != null\"> waist, </if>" +
                " <if test=\"memberBody.hip != null\"> hip, </if>" +
                " <if test=\"memberBody.legLeft != null\"> leg_left, </if>" +
                " <if test=\"memberBody.legRight != null\"> leg_right, </if>" +
                " <if test=\"memberBody.imageFace != null\"> image_face, </if>" +
                " <if test=\"memberBody.imageLeft != null\"> image_left, </if>" +
                " <if test=\"memberBody.imageRight != null\"> image_right, </if>" +
                " <if test=\"memberBody.imageReport != null\"> image_report, </if>" +
                " <if test=\"memberBody.feature != null\"> feature, </if>" +
                " <if test=\"memberBody.remark != null\"> remark, </if>" +
                " <if test=\"memberBody.status != null\"> status, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"memberBody.bodyId != null\"> #{memberBody.bodyId}, </if>" +
                " <if test=\"memberBody.memberId != null\"> #{memberBody.memberId}, </if>" +
                " <if test=\"memberBody.coachId != null\"> #{memberBody.coachId}, </if>" +
                " <if test=\"memberBody.height != null\"> #{memberBody.height}, </if>" +
                " <if test=\"memberBody.weight != null\"> #{memberBody.weight}, </if>" +
                " <if test=\"memberBody.bmi != null\"> #{memberBody.bmi}, </if>" +
                " <if test=\"memberBody.fat != null\"> #{memberBody.fat}, </if>" +
                " <if test=\"memberBody.armLeft != null\"> #{memberBody.armLeft}, </if>" +
                " <if test=\"memberBody.armRight != null\"> #{memberBody.armRight}, </if>" +
                " <if test=\"memberBody.waist != null\"> #{memberBody.waist}, </if>" +
                " <if test=\"memberBody.hip != null\"> #{memberBody.hip}, </if>" +
                " <if test=\"memberBody.legLeft != null\"> #{memberBody.legLeft}, </if>" +
                " <if test=\"memberBody.legRight != null\"> #{memberBody.legRight}, </if>" +
                " <if test=\"memberBody.imageFace != null\"> #{memberBody.imageFace}, </if>" +
                " <if test=\"memberBody.imageLeft != null\"> #{memberBody.imageLeft}, </if>" +
                " <if test=\"memberBody.imageRight != null\"> #{memberBody.imageRight}, </if>" +
                " <if test=\"memberBody.imageReport != null\"> #{memberBody.imageReport}, </if>" +
                " <if test=\"memberBody.feature != null\"> #{memberBody.feature}, </if>" +
                " <if test=\"memberBody.remark != null\"> #{memberBody.remark}, </if>" +
                " <if test=\"memberBody.status != null\"> #{memberBody.status}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("memberBody") MemberBodyEntity memberBody);

    @Select("<script> SELECT pk_id,body_id,member_id,coach_id,height,weight,bmi,fat,arm_left,arm_right,waist,hip,leg_left,leg_right,image_face,image_left,image_right,image_report,feature,remark,status,created,modified " +
            " FROM member_body " +
            " WHERE 1 = 1 " +
            " <if test=\"query.bodyId != null\"> AND body_id = #{query.bodyId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.coachId != null\"> AND coach_id = #{query.coachId} </if>" +
            " <if test=\"query.height != null\"> AND height = #{query.height} </if>" +
            " <if test=\"query.weight != null\"> AND weight = #{query.weight} </if>" +
            " <if test=\"query.bmi != null\"> AND bmi = #{query.bmi} </if>" +
            " <if test=\"query.fat != null\"> AND fat = #{query.fat} </if>" +
            " <if test=\"query.armLeft != null\"> AND arm_left = #{query.armLeft} </if>" +
            " <if test=\"query.armRight != null\"> AND arm_right = #{query.armRight} </if>" +
            " <if test=\"query.waist != null\"> AND waist = #{query.waist} </if>" +
            " <if test=\"query.hip != null\"> AND hip = #{query.hip} </if>" +
            " <if test=\"query.legLeft != null\"> AND leg_left = #{query.legLeft} </if>" +
            " <if test=\"query.legRight != null\"> AND leg_right = #{query.legRight} </if>" +
            " <if test=\"query.imageFace != null\"> AND image_face = #{query.imageFace} </if>" +
            " <if test=\"query.imageLeft != null\"> AND image_left = #{query.imageLeft} </if>" +
            " <if test=\"query.imageRight != null\"> AND image_right = #{query.imageRight} </if>" +
            " <if test=\"query.imageReport != null\"> AND image_report = #{query.imageReport} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " order by pk_id desc LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<MemberBodyEntity> find(@Param("query") MemberBodyQuery memberBody , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM member_body " +
            " WHERE 1 = 1 " +
            " <if test=\"query.bodyId != null\"> AND body_id = #{query.bodyId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.coachId != null\"> AND coach_id = #{query.coachId} </if>" +
            " <if test=\"query.height != null\"> AND height = #{query.height} </if>" +
            " <if test=\"query.weight != null\"> AND weight = #{query.weight} </if>" +
            " <if test=\"query.bmi != null\"> AND bmi = #{query.bmi} </if>" +
            " <if test=\"query.fat != null\"> AND fat = #{query.fat} </if>" +
            " <if test=\"query.armLeft != null\"> AND arm_left = #{query.armLeft} </if>" +
            " <if test=\"query.armRight != null\"> AND arm_right = #{query.armRight} </if>" +
            " <if test=\"query.waist != null\"> AND waist = #{query.waist} </if>" +
            " <if test=\"query.hip != null\"> AND hip = #{query.hip} </if>" +
            " <if test=\"query.legLeft != null\"> AND leg_left = #{query.legLeft} </if>" +
            " <if test=\"query.legRight != null\"> AND leg_right = #{query.legRight} </if>" +
            " <if test=\"query.imageFace != null\"> AND image_face = #{query.imageFace} </if>" +
            " <if test=\"query.imageLeft != null\"> AND image_left = #{query.imageLeft} </if>" +
            " <if test=\"query.imageRight != null\"> AND image_right = #{query.imageRight} </if>" +
            " <if test=\"query.imageReport != null\"> AND image_report = #{query.imageReport} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            "</script>")
    Long count(@Param("query") MemberBodyQuery memberBody);

    @Select("<script> SELECT pk_id,body_id,member_id,coach_id,height,weight,bmi,fat,arm_left,arm_right,waist,hip,leg_left,leg_right,image_face,image_left,image_right,image_report,feature,remark,status,created,modified " +
            " FROM member_body " +
            " WHERE body_id = #{id} " +
            "</script>")
    MemberBodyEntity getById(@Param("id") String id);

    @Update("<script> UPDATE member_body SET " +
                " <if test=\"memberBody.bodyId != null\"> body_id = #{memberBody.bodyId} , </if>" +
                " <if test=\"memberBody.memberId != null\"> member_id = #{memberBody.memberId} , </if>" +
                " <if test=\"memberBody.coachId != null\"> coach_id = #{memberBody.coachId} , </if>" +
                " <if test=\"memberBody.height != null\"> height = #{memberBody.height} , </if>" +
                " <if test=\"memberBody.weight != null\"> weight = #{memberBody.weight} , </if>" +
                " <if test=\"memberBody.bmi != null\"> bmi = #{memberBody.bmi} , </if>" +
                " <if test=\"memberBody.fat != null\"> fat = #{memberBody.fat} , </if>" +
                " <if test=\"memberBody.armLeft != null\"> arm_left = #{memberBody.armLeft} , </if>" +
                " <if test=\"memberBody.armRight != null\"> arm_right = #{memberBody.armRight} , </if>" +
                " <if test=\"memberBody.waist != null\"> waist = #{memberBody.waist} , </if>" +
                " <if test=\"memberBody.hip != null\"> hip = #{memberBody.hip} , </if>" +
                " <if test=\"memberBody.legLeft != null\"> leg_left = #{memberBody.legLeft} , </if>" +
                " <if test=\"memberBody.legRight != null\"> leg_right = #{memberBody.legRight} , </if>" +
                " <if test=\"memberBody.imageFace != null\"> image_face = #{memberBody.imageFace} , </if>" +
                " <if test=\"memberBody.imageLeft != null\"> image_left = #{memberBody.imageLeft} , </if>" +
                " <if test=\"memberBody.imageRight != null\"> image_right = #{memberBody.imageRight} , </if>" +
                " <if test=\"memberBody.imageReport != null\"> image_report = #{memberBody.imageReport} , </if>" +
                " <if test=\"memberBody.feature != null\"> feature = #{memberBody.feature} , </if>" +
                " <if test=\"memberBody.remark != null\"> remark = #{memberBody.remark} , </if>" +
                " <if test=\"memberBody.status != null\"> status = #{memberBody.status} , </if>" +
                " modified = now() " +
            " WHERE body_id = #{memberBody.bodyId} " +
            "</script>")
    int update(@Param("memberBody") MemberBodyEntity memberBody);

    @Update("<script> DELETE  FROM member_body " +
            " WHERE body_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * star_standard 数据库操作类
 * Created by huai23 on 2018-07-22 20:49:43.
 */ 
@Mapper
public interface StarStandardRepository {

    @Insert("<script> INSERT INTO star_standard ( " +
                " <if test=\"starStandard.starId != null\"> star_id, </if>" +
                " <if test=\"starStandard.storeId != null\"> store_id, </if>" +
                " <if test=\"starStandard.name != null\"> name, </if>" +
                " <if test=\"starStandard.monthNum != null\"> month_num, </if>" +
                " <if test=\"starStandard.score != null\"> score, </if>" +
                " <if test=\"starStandard.memberNum != null\"> member_num, </if>" +
                " <if test=\"starStandard.medalNum != null\"> medal_num, </if>" +
                " <if test=\"starStandard.scoreDown != null\"> score_down, </if>" +
                " <if test=\"starStandard.memberNumDown != null\"> member_num_down, </if>" +
                " <if test=\"starStandard.remark != null\"> remark, </if>" +
                " <if test=\"starStandard.status != null\"> status, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"starStandard.starId != null\"> #{starStandard.starId}, </if>" +
                " <if test=\"starStandard.storeId != null\"> #{starStandard.storeId}, </if>" +
                " <if test=\"starStandard.name != null\"> #{starStandard.name}, </if>" +
                " <if test=\"starStandard.monthNum != null\"> #{starStandard.monthNum}, </if>" +
                " <if test=\"starStandard.score != null\"> #{starStandard.score}, </if>" +
                " <if test=\"starStandard.memberNum != null\"> #{starStandard.memberNum}, </if>" +
                " <if test=\"starStandard.medalNum != null\"> #{starStandard.medalNum}, </if>" +
                " <if test=\"starStandard.scoreDown != null\"> #{starStandard.scoreDown}, </if>" +
                " <if test=\"starStandard.memberNumDown != null\"> #{starStandard.memberNumDown}, </if>" +
                " <if test=\"starStandard.remark != null\"> #{starStandard.remark}, </if>" +
                " <if test=\"starStandard.status != null\"> #{starStandard.status}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("starStandard") StarStandardEntity starStandard);

    @Select("<script> SELECT pk_id,star_id,store_id,name,month_num,score,member_num,medal_num,score_down,member_num_down,remark,status,created,modified " +
            " FROM star_standard " +
            " WHERE 1 = 1 " +
            " <if test=\"query.starId != null\"> AND star_id = #{query.starId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.name != null\"> AND name = #{query.name} </if>" +
            " <if test=\"query.monthNum != null\"> AND month_num = #{query.monthNum} </if>" +
            " <if test=\"query.score != null\"> AND score = #{query.score} </if>" +
            " <if test=\"query.memberNum != null\"> AND member_num = #{query.memberNum} </if>" +
            " <if test=\"query.medalNum != null\"> AND medal_num = #{query.medalNum} </if>" +
            " <if test=\"query.scoreDown != null\"> AND score_down = #{query.scoreDown} </if>" +
            " <if test=\"query.memberNumDown != null\"> AND member_num_down = #{query.memberNumDown} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<StarStandardEntity> find(@Param("query") StarStandardQuery starStandard , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM star_standard " +
            " WHERE 1 = 1 " +
            " <if test=\"query.starId != null\"> AND star_id = #{query.starId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.name != null\"> AND name = #{query.name} </if>" +
            " <if test=\"query.monthNum != null\"> AND month_num = #{query.monthNum} </if>" +
            " <if test=\"query.score != null\"> AND score = #{query.score} </if>" +
            " <if test=\"query.memberNum != null\"> AND member_num = #{query.memberNum} </if>" +
            " <if test=\"query.medalNum != null\"> AND medal_num = #{query.medalNum} </if>" +
            " <if test=\"query.scoreDown != null\"> AND score_down = #{query.scoreDown} </if>" +
            " <if test=\"query.memberNumDown != null\"> AND member_num_down = #{query.memberNumDown} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            "</script>")
    Long count(@Param("query") StarStandardQuery starStandard);

    @Select("<script> SELECT pk_id,star_id,store_id,name,month_num,score,member_num,medal_num,score_down,member_num_down,remark,status,created,modified " +
            " FROM star_standard " +
            " WHERE star_id = #{id} " +
            "</script>")
    StarStandardEntity getById(@Param("id") String id);

    @Update("<script> UPDATE star_standard SET " +
                " <if test=\"starStandard.starId != null\"> star_id = #{starStandard.starId} , </if>" +
                " <if test=\"starStandard.storeId != null\"> store_id = #{starStandard.storeId} , </if>" +
                " <if test=\"starStandard.name != null\"> name = #{starStandard.name} , </if>" +
                " <if test=\"starStandard.monthNum != null\"> month_num = #{starStandard.monthNum} , </if>" +
                " <if test=\"starStandard.score != null\"> score = #{starStandard.score} , </if>" +
                " <if test=\"starStandard.memberNum != null\"> member_num = #{starStandard.memberNum} , </if>" +
                " <if test=\"starStandard.medalNum != null\"> medal_num = #{starStandard.medalNum} , </if>" +
                " <if test=\"starStandard.scoreDown != null\"> score_down = #{starStandard.scoreDown} , </if>" +
                " <if test=\"starStandard.memberNumDown != null\"> member_num_down = #{starStandard.memberNumDown} , </if>" +
                " <if test=\"starStandard.remark != null\"> remark = #{starStandard.remark} , </if>" +
                " <if test=\"starStandard.status != null\"> status = #{starStandard.status} , </if>" +
                " modified = now() " +
            " WHERE star_id = #{starStandard.starId} " +
            "</script>")
    int update(@Param("starStandard") StarStandardEntity starStandard);

    @Update("<script> DELETE  FROM star_standard " +
            " WHERE star_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


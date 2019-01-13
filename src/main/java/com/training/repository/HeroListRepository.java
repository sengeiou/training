package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * hero_list 数据库操作类
 * Created by huai23 on 2019-01-13 20:32:10.
 */ 
@Mapper
public interface HeroListRepository {

    @Insert("<script> INSERT INTO hero_list ( " +
                " <if test=\"heroList.heroDate != null\"> hero_date, </if>" +
                " <if test=\"heroList.type != null\"> TYPE, </if>" +
                " <if test=\"heroList.staffId != null\"> staff_id, </if>" +
                " <if test=\"heroList.staffName != null\"> staff_name, </if>" +
                " <if test=\"heroList.memberId != null\"> member_id, </if>" +
                " <if test=\"heroList.memberName != null\"> member_name, </if>" +
                " <if test=\"heroList.storeId != null\"> store_id, </if>" +
                " <if test=\"heroList.storeName != null\"> store_name, </if>" +
                " <if test=\"heroList.image != null\"> image, </if>" +
                " <if test=\"heroList.sort != null\"> sort, </if>" +
                " <if test=\"heroList.label != null\"> label, </if>" +
                " <if test=\"heroList.value != null\"> value, </if>" +
                " <if test=\"heroList.unit != null\"> unit, </if>" +
                " <if test=\"heroList.feature != null\"> feature, </if>" +
                " <if test=\"heroList.remark != null\"> remark, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"heroList.heroDate != null\"> #{heroList.heroDate}, </if>" +
                " <if test=\"heroList.type != null\"> #{heroList.type}, </if>" +
                " <if test=\"heroList.staffId != null\"> #{heroList.staffId}, </if>" +
                " <if test=\"heroList.staffName != null\"> #{heroList.staffName}, </if>" +
                " <if test=\"heroList.memberId != null\"> #{heroList.memberId}, </if>" +
                " <if test=\"heroList.memberName != null\"> #{heroList.memberName}, </if>" +
                " <if test=\"heroList.storeId != null\"> #{heroList.storeId}, </if>" +
                " <if test=\"heroList.storeName != null\"> #{heroList.storeName}, </if>" +
                " <if test=\"heroList.image != null\"> #{heroList.image}, </if>" +
                " <if test=\"heroList.sort != null\"> #{heroList.sort}, </if>" +
                " <if test=\"heroList.label != null\"> #{heroList.label}, </if>" +
                " <if test=\"heroList.value != null\"> #{heroList.value}, </if>" +
                " <if test=\"heroList.unit != null\"> #{heroList.unit}, </if>" +
                " <if test=\"heroList.feature != null\"> #{heroList.feature}, </if>" +
                " <if test=\"heroList.remark != null\"> #{heroList.remark}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("heroList") HeroListEntity heroList);

    @Select("<script> SELECT pk_id,hero_date,TYPE,staff_id,staff_name,member_id,member_name,store_id,store_name,image,sort,label,value,unit,feature,remark,created,modified " +
            " FROM hero_list " +
            " WHERE 1 = 1 " +
            " <if test=\"query.heroDate != null\"> AND hero_date = #{query.heroDate} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.staffName != null\"> AND staff_name = #{query.staffName} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.memberName != null\"> AND member_name = #{query.memberName} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.storeName != null\"> AND store_name = #{query.storeName} </if>" +
            " <if test=\"query.image != null\"> AND image = #{query.image} </if>" +
            " <if test=\"query.sort != null\"> AND sort = #{query.sort} </if>" +
            " <if test=\"query.label != null\"> AND label = #{query.label} </if>" +
            " <if test=\"query.value != null\"> AND value = #{query.value} </if>" +
            " <if test=\"query.unit != null\"> AND unit = #{query.unit} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<HeroListEntity> find(@Param("query") HeroListQuery heroList , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM hero_list " +
            " WHERE 1 = 1 " +
            " <if test=\"query.heroDate != null\"> AND hero_date = #{query.heroDate} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.staffName != null\"> AND staff_name = #{query.staffName} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.memberName != null\"> AND member_name = #{query.memberName} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.storeName != null\"> AND store_name = #{query.storeName} </if>" +
            " <if test=\"query.image != null\"> AND image = #{query.image} </if>" +
            " <if test=\"query.sort != null\"> AND sort = #{query.sort} </if>" +
            " <if test=\"query.label != null\"> AND label = #{query.label} </if>" +
            " <if test=\"query.value != null\"> AND value = #{query.value} </if>" +
            " <if test=\"query.unit != null\"> AND unit = #{query.unit} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            "</script>")
    Long count(@Param("query") HeroListQuery heroList);

    @Select("<script> SELECT pk_id,hero_date,TYPE,staff_id,staff_name,member_id,member_name,store_id,store_name,image,sort,label,value,unit,feature,remark,created,modified " +
            " FROM hero_list " +
            " WHERE hero_date = #{id} " +
            "</script>")
    HeroListEntity getById(@Param("id") String id);

    @Update("<script> UPDATE hero_list SET " +
                " <if test=\"heroList.heroDate != null\"> hero_date = #{heroList.heroDate} , </if>" +
                " <if test=\"heroList.type != null\"> TYPE = #{heroList.type} , </if>" +
                " <if test=\"heroList.staffId != null\"> staff_id = #{heroList.staffId} , </if>" +
                " <if test=\"heroList.staffName != null\"> staff_name = #{heroList.staffName} , </if>" +
                " <if test=\"heroList.memberId != null\"> member_id = #{heroList.memberId} , </if>" +
                " <if test=\"heroList.memberName != null\"> member_name = #{heroList.memberName} , </if>" +
                " <if test=\"heroList.storeId != null\"> store_id = #{heroList.storeId} , </if>" +
                " <if test=\"heroList.storeName != null\"> store_name = #{heroList.storeName} , </if>" +
                " <if test=\"heroList.image != null\"> image = #{heroList.image} , </if>" +
                " <if test=\"heroList.sort != null\"> sort = #{heroList.sort} , </if>" +
                " <if test=\"heroList.label != null\"> label = #{heroList.label} , </if>" +
                " <if test=\"heroList.value != null\"> value = #{heroList.value} , </if>" +
                " <if test=\"heroList.unit != null\"> unit = #{heroList.unit} , </if>" +
                " <if test=\"heroList.feature != null\"> feature = #{heroList.feature} , </if>" +
                " <if test=\"heroList.remark != null\"> remark = #{heroList.remark} , </if>" +
                " modified = now() " +
            " WHERE hero_date = #{heroList.heroDate} " +
            "</script>")
    int update(@Param("heroList") HeroListEntity heroList);

    @Update("<script> DELETE  FROM hero_list " +
            " WHERE hero_date = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


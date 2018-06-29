package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * store_task 数据库操作类
 * Created by huai23 on 2018-06-13 22:49:38.
 */ 
@Mapper
public interface StoreTaskRepository {

    @Insert("<script> INSERT INTO store_task ( " +
                " <if test=\"storeTask.taskId != null\"> task_id, </if>" +
                " <if test=\"storeTask.storeId != null\"> store_id, </if>" +
                " <if test=\"storeTask.staffId != null\"> staff_id, </if>" +
                " <if test=\"storeTask.type != null\"> type, </if>" +
                " <if test=\"storeTask.name != null\"> name, </if>" +
                " <if test=\"storeTask.content != null\"> content, </if>" +
                " <if test=\"storeTask.pushDate != null\"> push_date, </if>" +
                " <if test=\"storeTask.remark != null\"> remark, </if>" +
                " <if test=\"storeTask.status != null\"> status, </if>" +
                " <if test=\"storeTask.creator != null\"> creator, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"storeTask.taskId != null\"> #{storeTask.taskId}, </if>" +
                " <if test=\"storeTask.storeId != null\"> #{storeTask.storeId}, </if>" +
                " <if test=\"storeTask.staffId != null\"> #{storeTask.staffId}, </if>" +
                " <if test=\"storeTask.type != null\"> #{storeTask.type}, </if>" +
                " <if test=\"storeTask.name != null\"> #{storeTask.name}, </if>" +
                " <if test=\"storeTask.content != null\"> #{storeTask.content}, </if>" +
                " <if test=\"storeTask.pushDate != null\"> #{storeTask.pushDate}, </if>" +
                " <if test=\"storeTask.remark != null\"> #{storeTask.remark}, </if>" +
                " <if test=\"storeTask.status != null\"> #{storeTask.status}, </if>" +
                " <if test=\"storeTask.creator != null\"> #{storeTask.creator}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("storeTask") StoreTaskEntity storeTask);

    @Select("<script> SELECT pk_id,task_id,store_id,staff_id,type,name,content,push_date,remark,status,creator,created,modified " +
            " FROM store_task " +
            " WHERE 1 = 1 " +
            " <if test=\"query.taskId != null\"> AND task_id = #{query.taskId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.name != null\"> AND name = #{query.name} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.pushDate != null\"> AND push_date = #{query.pushDate} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.creator != null\"> AND creator = #{query.creator} </if>" +
            " order by pk_id desc LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<StoreTaskEntity> find(@Param("query") StoreTaskQuery storeTask , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM store_task " +
            " WHERE 1 = 1 " +
            " <if test=\"query.taskId != null\"> AND task_id = #{query.taskId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.name != null\"> AND name = #{query.name} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.pushDate != null\"> AND push_date = #{query.pushDate} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.creator != null\"> AND creator = #{query.creator} </if>" +
            "</script>")
    Long count(@Param("query") StoreTaskQuery storeTask);

    @Select("<script> SELECT pk_id,task_id,store_id,staff_id,type,name,content,push_date,remark,status,creator,created,modified " +
            " FROM store_task " +
            " WHERE task_id = #{id} " +
            "</script>")
    StoreTaskEntity getById(@Param("id") String id);

    @Update("<script> UPDATE store_task SET " +
                " <if test=\"storeTask.taskId != null\"> task_id = #{storeTask.taskId} , </if>" +
                " <if test=\"storeTask.storeId != null\"> store_id = #{storeTask.storeId} , </if>" +
                " <if test=\"storeTask.staffId != null\"> staff_id = #{storeTask.staffId} , </if>" +
                " <if test=\"storeTask.type != null\"> type = #{storeTask.type} , </if>" +
                " <if test=\"storeTask.name != null\"> name = #{storeTask.name} , </if>" +
                " <if test=\"storeTask.content != null\"> content = #{storeTask.content} , </if>" +
                " <if test=\"storeTask.pushDate != null\"> push_date = #{storeTask.pushDate} , </if>" +
                " <if test=\"storeTask.remark != null\"> remark = #{storeTask.remark} , </if>" +
                " <if test=\"storeTask.status != null\"> status = #{storeTask.status} , </if>" +
                " <if test=\"storeTask.creator != null\"> creator = #{storeTask.creator} , </if>" +
                " modified = now() " +
            " WHERE task_id = #{storeTask.taskId} " +
            "</script>")
    int update(@Param("storeTask") StoreTaskEntity storeTask);

    @Update("<script> DELETE  FROM store_task " +
            " WHERE task_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


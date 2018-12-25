package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * biz_unique 数据库操作类
 * Created by huai23 on 2018-12-25 23:24:18.
 */ 
@Mapper
public interface BizUniqueRepository {

    @Insert("<script> INSERT INTO biz_unique ( biz_id , created ,  modified  ) VALUES ( #{bizUnique.bizId},  now() ,  now()  ) " +
            "</script>")
    int add(@Param("bizUnique") BizUniqueEntity bizUnique);

    @Select("<script> SELECT pk_id,biz_id,created,modified " +
            " FROM biz_unique " +
            " WHERE 1 = 1 " +
            " <if test=\"query.bizId != null\"> AND biz_id = #{query.bizId} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<BizUniqueEntity> find(@Param("query") BizUniqueQuery bizUnique , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM biz_unique " +
            " WHERE 1 = 1 " +
            " <if test=\"query.bizId != null\"> AND biz_id = #{query.bizId} </if>" +
            "</script>")
    Long count(@Param("query") BizUniqueQuery bizUnique);

    @Select("<script> SELECT pk_id,biz_id,created,modified " +
            " FROM biz_unique " +
            " WHERE biz_id = #{id} " +
            "</script>")
    BizUniqueEntity getById(@Param("id") String id);

    @Update("<script> UPDATE biz_unique SET " +
                " <if test=\"bizUnique.bizId != null\"> biz_id = #{bizUnique.bizId} , </if>" +
                " modified = now() " +
            " WHERE biz_id = #{bizUnique.bizId} " +
            "</script>")
    int update(@Param("bizUnique") BizUniqueEntity bizUnique);

    @Update("<script> DELETE  FROM biz_unique " +
            " WHERE biz_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


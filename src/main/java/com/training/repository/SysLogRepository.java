package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * sys_log 数据库操作类
 * Created by huai23 on 2018-06-03 15:57:51.
 */ 
@Mapper
public interface SysLogRepository {

    @Insert("<script> INSERT INTO sys_log ( " +
                " <if test=\"sysLog.logId != null\"> LOG_ID, </if>" +
                " <if test=\"sysLog.type != null\"> TYPE, </if>" +
                " <if test=\"sysLog.level != null\"> LEVEL, </if>" +
                " <if test=\"sysLog.id1 != null\"> id1, </if>" +
                " <if test=\"sysLog.id2 != null\"> id2, </if>" +
                " <if test=\"sysLog.logText != null\"> log_text, </if>" +
                " <if test=\"sysLog.content != null\"> content, </if>" +
                " <if test=\"sysLog.remark != null\"> REMARK, </if>" +
                " <if test=\"sysLog.storeId != null\"> store_id, </if>" +
                " <if test=\"sysLog.memberId != null\"> member_id, </if>" +
                " <if test=\"sysLog.staffId != null\"> staff_id, </if>" +
                " <if test=\"sysLog.cardNo != null\"> card_no, </if>" +
                " <if test=\"sysLog.operStaffId != null\"> oper_staff_id, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"sysLog.logId != null\"> #{sysLog.logId}, </if>" +
                " <if test=\"sysLog.type != null\"> #{sysLog.type}, </if>" +
                " <if test=\"sysLog.level != null\"> #{sysLog.level}, </if>" +
                " <if test=\"sysLog.id1 != null\"> #{sysLog.id1}, </if>" +
                " <if test=\"sysLog.id2 != null\"> #{sysLog.id2}, </if>" +
                " <if test=\"sysLog.logText != null\"> #{sysLog.logText}, </if>" +
                " <if test=\"sysLog.content != null\"> #{sysLog.content}, </if>" +
                " <if test=\"sysLog.remark != null\"> #{sysLog.remark}, </if>" +
                " <if test=\"sysLog.storeId != null\"> #{sysLog.storeId}, </if>" +
                " <if test=\"sysLog.memberId != null\"> #{sysLog.memberId}, </if>" +
                " <if test=\"sysLog.staffId != null\"> #{sysLog.staffId}, </if>" +
                " <if test=\"sysLog.cardNo != null\"> #{sysLog.cardNo}, </if>" +
                " <if test=\"sysLog.operStaffId != null\"> #{sysLog.operStaffId}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("sysLog") SysLogEntity sysLog);

    @Select("<script> SELECT pk_id,LOG_ID,TYPE,LEVEL,id1,id2,log_text,content,REMARK,store_id,member_id,staff_id,oper_staff_id,created,modified " +
            " FROM sys_log " +
            " WHERE 1 = 1 " +
            " <if test=\"query.logId != null\"> AND LOG_ID = #{query.logId} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.level != null\"> AND LEVEL = #{query.level} </if>" +
            " <if test=\"query.id1 != null\"> AND id1 = #{query.id1} </if>" +
            " <if test=\"query.id2 != null\"> AND id2 = #{query.id2} </if>" +
            " <if test=\"query.logText != null\"> AND log_text = #{query.logText} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            " <if test=\"query.startDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d')  &gt;= #{query.startDate}  </if>" +
            " <if test=\"query.endDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d')  &lt;= #{query.endDate} </if>" +
            " <if test=\"query.name != null\"> AND id1 in ( select member_id from member where type = 'M' AND name like CONCAT('%',#{query.name},'%')  ) </if>" +
            " <if test=\"query.phone != null\"> AND id1 in ( select member_id from member where type = 'M' AND phone like CONCAT('%',#{query.phone},'%')  ) </if>" +
            " <if test=\"query.storeId != null\"> AND id2 in ( select staff_id from staff where store_id = #{query.storeId}  ) </if>" +
            " <if test=\"query.operStaffId != null\"> AND oper_staff_id = #{query.operStaffId} </if>" +
            " order by pk_id desc LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<SysLogEntity> find(@Param("query") SysLogQuery sysLog , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM sys_log " +
            " WHERE 1 = 1 " +
            " <if test=\"query.logId != null\"> AND LOG_ID = #{query.logId} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.level != null\"> AND LEVEL = #{query.level} </if>" +
            " <if test=\"query.id1 != null\"> AND id1 = #{query.id1} </if>" +
            " <if test=\"query.id2 != null\"> AND id2 = #{query.id2} </if>" +
            " <if test=\"query.logText != null\"> AND log_text = #{query.logText} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            " <if test=\"query.startDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d')  &gt;= #{query.startDate}  </if>" +
            " <if test=\"query.endDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d')  &lt;= #{query.endDate} </if>" +
            " <if test=\"query.name != null\"> AND id1 in ( select member_id from member where type = 'M' AND name like CONCAT('%',#{query.name},'%')  ) </if>" +
            " <if test=\"query.phone != null\"> AND id1 in ( select member_id from member where type = 'M' AND phone like CONCAT('%',#{query.phone},'%')  ) </if>" +
            " <if test=\"query.storeId != null\"> AND id2 in ( select staff_id from staff where store_id = #{query.storeId}  ) </if>" +
            " <if test=\"query.operStaffId != null\"> AND oper_staff_id = #{query.operStaffId} </if>" +
            "</script>")
    Long count(@Param("query") SysLogQuery sysLog);

    @Select("<script> SELECT pk_id,LOG_ID,TYPE,LEVEL,id1,id2,log_text,content,REMARK,store_id,member_id,staff_id,oper_staff_id,created,modified " +
            " FROM sys_log " +
            " WHERE LOG_ID = #{id} " +
            "</script>")
    SysLogEntity getById(@Param("id") String id);

    @Update("<script> UPDATE sys_log SET " +
                " <if test=\"sysLog.logId != null\"> LOG_ID = #{sysLog.logId} , </if>" +
                " <if test=\"sysLog.type != null\"> TYPE = #{sysLog.type} , </if>" +
                " <if test=\"sysLog.level != null\"> LEVEL = #{sysLog.level} , </if>" +
                " <if test=\"sysLog.logText != null\"> log_text = #{sysLog.logText} , </if>" +
                " <if test=\"sysLog.content != null\"> content = #{sysLog.content} , </if>" +
                " <if test=\"sysLog.remark != null\"> REMARK = #{sysLog.remark} , </if>" +
                " modified = now() " +
            " WHERE LOG_ID = #{sysLog.logId} " +
            "</script>")
    int update(@Param("sysLog") SysLogEntity sysLog);

    @Update("<script> DELETE  FROM sys_log " +
            " WHERE LOG_ID = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


    @Select("<script> SELECT pk_id,LOG_ID,TYPE,LEVEL,id1,id2,log_text,content,REMARK,store_id,member_id,staff_id,oper_staff_id,created,modified " +
            " FROM sys_log " +
            " WHERE 1 = 1 " +
            " <if test=\"query.logId != null\"> AND LOG_ID = #{query.logId} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.level != null\"> AND LEVEL = #{query.level} </if>" +
            " <if test=\"query.id1 != null\"> AND id1 = #{query.id1} </if>" +
            " <if test=\"query.id2 != null\"> AND id2 = #{query.id2} </if>" +
            " <if test=\"query.logText != null\"> AND log_text = #{query.logText} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            " <if test=\"query.startDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d')  &gt;= #{query.startDate}  </if>" +
            " <if test=\"query.endDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d')  &lt;= #{query.endDate} </if>" +
            " <if test=\"query.name != null\"> AND id1 in ( select b.card_no from member a, member_card b  where a.type = 'M' AND a.name like CONCAT('%',#{query.name},'%') and a.member_id = b.member_id  ) </if>" +
            " <if test=\"query.phone != null\"> AND id1 in ( select b.card_no from member a, member_card b  where a.type = 'M' AND a.phone like CONCAT('%',#{query.phone},'%') and a.member_id = b.member_id  ) </if>" +
            " <if test=\"query.storeId != null\"> AND id1 in ( select b.card_no from member a, member_card b  where a.type = 'M' AND a.store_id = #{query.storeId} and a.member_id = b.member_id   ) </if>" +
            " <if test=\"query.operStaffId != null\"> AND oper_staff_id = #{query.operStaffId} </if>" +
            " order by pk_id desc LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<SysLogEntity> findDelayLog(@Param("query") SysLogQuery sysLog , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM sys_log " +
            " WHERE 1 = 1 " +
            " <if test=\"query.logId != null\"> AND LOG_ID = #{query.logId} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.level != null\"> AND LEVEL = #{query.level} </if>" +
            " <if test=\"query.id1 != null\"> AND id1 = #{query.id1} </if>" +
            " <if test=\"query.id2 != null\"> AND id2 = #{query.id2} </if>" +
            " <if test=\"query.logText != null\"> AND log_text = #{query.logText} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            " <if test=\"query.startDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d')  &gt;= #{query.startDate}  </if>" +
            " <if test=\"query.endDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d')  &lt;= #{query.endDate} </if>" +
            " <if test=\"query.name != null\"> AND id1 in ( select b.card_no from member a, member_card b  where a.type = 'M' AND a.name like CONCAT('%',#{query.name},'%') and a.member_id = b.member_id  ) </if>" +
            " <if test=\"query.phone != null\"> AND id1 in ( select b.card_no from member a, member_card b  where a.type = 'M' AND a.phone like CONCAT('%',#{query.phone},'%') and a.member_id = b.member_id  ) </if>" +
            " <if test=\"query.storeId != null\"> AND id1 in ( select b.card_no from member a, member_card b  where a.type = 'M' AND a.store_id = #{query.storeId} and a.member_id = b.member_id   ) </if>" +
            " <if test=\"query.operStaffId != null\"> AND oper_staff_id = #{query.operStaffId} </if>" +
            "</script>")
    Long countDelayLog(@Param("query") SysLogQuery sysLog);

    @Select("<script> SELECT pk_id,LOG_ID,TYPE,LEVEL,id1,id2,log_text,content,REMARK,store_id,member_id,staff_id,oper_staff_id,created,modified " +
            " FROM sys_log " +
            " WHERE 1 = 1 " +
            " <if test=\"query.logId != null\"> AND LOG_ID = #{query.logId} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.level != null\"> AND LEVEL = #{query.level} </if>" +
            " <if test=\"query.transactionId != null\"> AND id2 like CONCAT('%',#{query.transactionId},'%')  </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            " <if test=\"query.startDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d')  &gt;= #{query.startDate}  </if>" +
            " <if test=\"query.endDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d')  &lt;= #{query.endDate} </if>" +
            " <if test=\"query.name != null\"> AND id1 in ( select b.card_no from member a, member_card b  where a.type = 'M' AND a.name like CONCAT('%',#{query.name},'%') and a.member_id = b.member_id  ) </if>" +
            " <if test=\"query.phone != null\"> AND id1 in ( select b.card_no from member a, member_card b  where a.type = 'M' AND a.phone like CONCAT('%',#{query.phone},'%') and a.member_id = b.member_id  ) </if>" +
            " <if test=\"query.storeId != null\"> AND id1 in ( select b.card_no from member a, member_card b  where a.type = 'M' AND a.store_id = #{query.storeId} and a.member_id = b.member_id   ) </if>" +
            " order by pk_id desc LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<SysLogEntity> findPayLog(@Param("query") SysLogQuery query, PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM sys_log " +
            " WHERE 1 = 1 " +
            " <if test=\"query.logId != null\"> AND LOG_ID = #{query.logId} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.level != null\"> AND LEVEL = #{query.level} </if>" +
            " <if test=\"query.transactionId != null\"> AND id2 like CONCAT('%',#{query.transactionId},'%')  </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.remark != null\"> AND REMARK = #{query.remark} </if>" +
            " <if test=\"query.startDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d')  &gt;= #{query.startDate}  </if>" +
            " <if test=\"query.endDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d')  &lt;= #{query.endDate} </if>" +
            " <if test=\"query.name != null\"> AND id1 in ( select b.card_no from member a, member_card b  where a.type = 'M' AND a.name like CONCAT('%',#{query.name},'%') and a.member_id = b.member_id  ) </if>" +
            " <if test=\"query.phone != null\"> AND id1 in ( select b.card_no from member a, member_card b  where a.type = 'M' AND a.phone like CONCAT('%',#{query.phone},'%') and a.member_id = b.member_id  ) </if>" +
            " <if test=\"query.storeId != null\"> AND id1 in ( select b.card_no from member a, member_card b  where a.type = 'M' AND a.store_id = #{query.storeId} and a.member_id = b.member_id   ) </if>" +
            "</script>")
    Long countPayLog(@Param("query") SysLogQuery query);

}


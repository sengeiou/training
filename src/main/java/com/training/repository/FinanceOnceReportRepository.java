package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * finance_once_report 数据库操作类
 * Created by huai23 on 2018-12-02 20:58:14.
 */ 
@Mapper
public interface FinanceOnceReportRepository {

    @Insert("<script> INSERT INTO finance_once_report ( " +
                " <if test=\"financeOnceReport.storeId != null\"> store_id, </if>" +
                " <if test=\"financeOnceReport.storeName != null\"> store_name, </if>" +
                " <if test=\"financeOnceReport.year != null\"> year, </if>" +
                " <if test=\"financeOnceReport.month != null\"> month, </if>" +
                " <if test=\"financeOnceReport.reportDate != null\"> report_date, </if>" +
                " <if test=\"financeOnceReport.saleMoney != null\"> sale_money, </if>" +
                " <if test=\"financeOnceReport.saleLessonCount != null\"> sale_lesson_count, </if>" +
                " <if test=\"financeOnceReport.waitingLessonMoney != null\"> waiting_lesson_money, </if>" +
                " <if test=\"financeOnceReport.waitingLessonCount != null\"> waiting_lesson_count, </if>" +
                " <if test=\"financeOnceReport.usedLessonMoney != null\"> used_lesson_money, </if>" +
                " <if test=\"financeOnceReport.usedLessonCount != null\"> used_lesson_count, </if>" +
                " <if test=\"financeOnceReport.deadLessonMoney != null\"> dead_lesson_money, </if>" +
                " <if test=\"financeOnceReport.deadLessonCount != null\"> dead_lesson_count, </if>" +
                " <if test=\"financeOnceReport.delayMoney != null\"> delay_money, </if>" +
                " <if test=\"financeOnceReport.outLessonMoney != null\"> out_lesson_money, </if>" +
                " <if test=\"financeOnceReport.outLessonCount != null\"> out_lesson_count, </if>" +
                " <if test=\"financeOnceReport.inLessonMoney != null\"> in_lesson_money, </if>" +
                " <if test=\"financeOnceReport.inLessonCount != null\"> in_lesson_count, </if>" +
                " <if test=\"financeOnceReport.backLessonMoney != null\"> back_lesson_money, </if>" +
                " <if test=\"financeOnceReport.backLessonCount != null\"> back_lesson_count, </if>" +
                " <if test=\"financeOnceReport.param1 != null\"> param1, </if>" +
                " <if test=\"financeOnceReport.param2 != null\"> param2, </if>" +
                " <if test=\"financeOnceReport.param3 != null\"> param3, </if>" +
                " <if test=\"financeOnceReport.param4 != null\"> param4, </if>" +
                " <if test=\"financeOnceReport.param5 != null\"> param5, </if>" +
                " <if test=\"financeOnceReport.param6 != null\"> param6, </if>" +
                " <if test=\"financeOnceReport.param7 != null\"> param7, </if>" +
                " <if test=\"financeOnceReport.param8 != null\"> param8, </if>" +
                " <if test=\"financeOnceReport.reportData != null\"> report_data, </if>" +
                " <if test=\"financeOnceReport.remark != null\"> remark, </if>" +
                " <if test=\"financeOnceReport.status != null\"> status, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"financeOnceReport.storeId != null\"> #{financeOnceReport.storeId}, </if>" +
                " <if test=\"financeOnceReport.storeName != null\"> #{financeOnceReport.storeName}, </if>" +
                " <if test=\"financeOnceReport.year != null\"> #{financeOnceReport.year}, </if>" +
                " <if test=\"financeOnceReport.month != null\"> #{financeOnceReport.month}, </if>" +
                " <if test=\"financeOnceReport.reportDate != null\"> #{financeOnceReport.reportDate}, </if>" +
                " <if test=\"financeOnceReport.saleMoney != null\"> #{financeOnceReport.saleMoney}, </if>" +
                " <if test=\"financeOnceReport.saleLessonCount != null\"> #{financeOnceReport.saleLessonCount}, </if>" +
                " <if test=\"financeOnceReport.waitingLessonMoney != null\"> #{financeOnceReport.waitingLessonMoney}, </if>" +
                " <if test=\"financeOnceReport.waitingLessonCount != null\"> #{financeOnceReport.waitingLessonCount}, </if>" +
                " <if test=\"financeOnceReport.usedLessonMoney != null\"> #{financeOnceReport.usedLessonMoney}, </if>" +
                " <if test=\"financeOnceReport.usedLessonCount != null\"> #{financeOnceReport.usedLessonCount}, </if>" +
                " <if test=\"financeOnceReport.deadLessonMoney != null\"> #{financeOnceReport.deadLessonMoney}, </if>" +
                " <if test=\"financeOnceReport.deadLessonCount != null\"> #{financeOnceReport.deadLessonCount}, </if>" +
                " <if test=\"financeOnceReport.delayMoney != null\"> #{financeOnceReport.delayMoney}, </if>" +
                " <if test=\"financeOnceReport.outLessonMoney != null\"> #{financeOnceReport.outLessonMoney}, </if>" +
                " <if test=\"financeOnceReport.outLessonCount != null\"> #{financeOnceReport.outLessonCount}, </if>" +
                " <if test=\"financeOnceReport.inLessonMoney != null\"> #{financeOnceReport.inLessonMoney}, </if>" +
                " <if test=\"financeOnceReport.inLessonCount != null\"> #{financeOnceReport.inLessonCount}, </if>" +
                " <if test=\"financeOnceReport.backLessonMoney != null\"> #{financeOnceReport.backLessonMoney}, </if>" +
                " <if test=\"financeOnceReport.backLessonCount != null\"> #{financeOnceReport.backLessonCount}, </if>" +
                " <if test=\"financeOnceReport.param1 != null\"> #{financeOnceReport.param1}, </if>" +
                " <if test=\"financeOnceReport.param2 != null\"> #{financeOnceReport.param2}, </if>" +
                " <if test=\"financeOnceReport.param3 != null\"> #{financeOnceReport.param3}, </if>" +
                " <if test=\"financeOnceReport.param4 != null\"> #{financeOnceReport.param4}, </if>" +
                " <if test=\"financeOnceReport.param5 != null\"> #{financeOnceReport.param5}, </if>" +
                " <if test=\"financeOnceReport.param6 != null\"> #{financeOnceReport.param6}, </if>" +
                " <if test=\"financeOnceReport.param7 != null\"> #{financeOnceReport.param7}, </if>" +
                " <if test=\"financeOnceReport.param8 != null\"> #{financeOnceReport.param8}, </if>" +
                " <if test=\"financeOnceReport.reportData != null\"> #{financeOnceReport.reportData}, </if>" +
                " <if test=\"financeOnceReport.remark != null\"> #{financeOnceReport.remark}, </if>" +
                " <if test=\"financeOnceReport.status != null\"> #{financeOnceReport.status}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("financeOnceReport") FinanceOnceReportEntity financeOnceReport);

    @Select("<script> SELECT pk_id,store_id,store_name,year,month,report_date,sale_money,sale_lesson_count,waiting_lesson_money,waiting_lesson_count,used_lesson_money,used_lesson_count,dead_lesson_money,dead_lesson_count,delay_money,out_lesson_money,out_lesson_count,in_lesson_money,in_lesson_count,back_lesson_money,back_lesson_count,param1,param2,param3,param4,param5,param6,param7,param8,report_data,remark,status,created,modified " +
            " FROM finance_once_report " +
            " WHERE 1 = 1 " +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.storeName != null\"> AND store_name = #{query.storeName} </if>" +
            " <if test=\"query.year != null\"> AND year = #{query.year} </if>" +
            " <if test=\"query.month != null\"> AND month = #{query.month} </if>" +
            " <if test=\"query.reportDate != null\"> AND report_date = #{query.reportDate} </if>" +
            " <if test=\"query.saleMoney != null\"> AND sale_money = #{query.saleMoney} </if>" +
            " <if test=\"query.saleLessonCount != null\"> AND sale_lesson_count = #{query.saleLessonCount} </if>" +
            " <if test=\"query.waitingLessonMoney != null\"> AND waiting_lesson_money = #{query.waitingLessonMoney} </if>" +
            " <if test=\"query.waitingLessonCount != null\"> AND waiting_lesson_count = #{query.waitingLessonCount} </if>" +
            " <if test=\"query.usedLessonMoney != null\"> AND used_lesson_money = #{query.usedLessonMoney} </if>" +
            " <if test=\"query.usedLessonCount != null\"> AND used_lesson_count = #{query.usedLessonCount} </if>" +
            " <if test=\"query.deadLessonMoney != null\"> AND dead_lesson_money = #{query.deadLessonMoney} </if>" +
            " <if test=\"query.deadLessonCount != null\"> AND dead_lesson_count = #{query.deadLessonCount} </if>" +
            " <if test=\"query.delayMoney != null\"> AND delay_money = #{query.delayMoney} </if>" +
            " <if test=\"query.outLessonMoney != null\"> AND out_lesson_money = #{query.outLessonMoney} </if>" +
            " <if test=\"query.outLessonCount != null\"> AND out_lesson_count = #{query.outLessonCount} </if>" +
            " <if test=\"query.inLessonMoney != null\"> AND in_lesson_money = #{query.inLessonMoney} </if>" +
            " <if test=\"query.inLessonCount != null\"> AND in_lesson_count = #{query.inLessonCount} </if>" +
            " <if test=\"query.backLessonMoney != null\"> AND back_lesson_money = #{query.backLessonMoney} </if>" +
            " <if test=\"query.backLessonCount != null\"> AND back_lesson_count = #{query.backLessonCount} </if>" +
            " <if test=\"query.param1 != null\"> AND param1 = #{query.param1} </if>" +
            " <if test=\"query.param2 != null\"> AND param2 = #{query.param2} </if>" +
            " <if test=\"query.param3 != null\"> AND param3 = #{query.param3} </if>" +
            " <if test=\"query.param4 != null\"> AND param4 = #{query.param4} </if>" +
            " <if test=\"query.param5 != null\"> AND param5 = #{query.param5} </if>" +
            " <if test=\"query.param6 != null\"> AND param6 = #{query.param6} </if>" +
            " <if test=\"query.param7 != null\"> AND param7 = #{query.param7} </if>" +
            " <if test=\"query.param8 != null\"> AND param8 = #{query.param8} </if>" +
            " <if test=\"query.reportData != null\"> AND report_data = #{query.reportData} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<FinanceOnceReportEntity> find(@Param("query") FinanceOnceReportQuery financeOnceReport , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM finance_once_report " +
            " WHERE 1 = 1 " +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.storeName != null\"> AND store_name = #{query.storeName} </if>" +
            " <if test=\"query.year != null\"> AND year = #{query.year} </if>" +
            " <if test=\"query.month != null\"> AND month = #{query.month} </if>" +
            " <if test=\"query.reportDate != null\"> AND report_date = #{query.reportDate} </if>" +
            " <if test=\"query.saleMoney != null\"> AND sale_money = #{query.saleMoney} </if>" +
            " <if test=\"query.saleLessonCount != null\"> AND sale_lesson_count = #{query.saleLessonCount} </if>" +
            " <if test=\"query.waitingLessonMoney != null\"> AND waiting_lesson_money = #{query.waitingLessonMoney} </if>" +
            " <if test=\"query.waitingLessonCount != null\"> AND waiting_lesson_count = #{query.waitingLessonCount} </if>" +
            " <if test=\"query.usedLessonMoney != null\"> AND used_lesson_money = #{query.usedLessonMoney} </if>" +
            " <if test=\"query.usedLessonCount != null\"> AND used_lesson_count = #{query.usedLessonCount} </if>" +
            " <if test=\"query.deadLessonMoney != null\"> AND dead_lesson_money = #{query.deadLessonMoney} </if>" +
            " <if test=\"query.deadLessonCount != null\"> AND dead_lesson_count = #{query.deadLessonCount} </if>" +
            " <if test=\"query.delayMoney != null\"> AND delay_money = #{query.delayMoney} </if>" +
            " <if test=\"query.outLessonMoney != null\"> AND out_lesson_money = #{query.outLessonMoney} </if>" +
            " <if test=\"query.outLessonCount != null\"> AND out_lesson_count = #{query.outLessonCount} </if>" +
            " <if test=\"query.inLessonMoney != null\"> AND in_lesson_money = #{query.inLessonMoney} </if>" +
            " <if test=\"query.inLessonCount != null\"> AND in_lesson_count = #{query.inLessonCount} </if>" +
            " <if test=\"query.backLessonMoney != null\"> AND back_lesson_money = #{query.backLessonMoney} </if>" +
            " <if test=\"query.backLessonCount != null\"> AND back_lesson_count = #{query.backLessonCount} </if>" +
            " <if test=\"query.param1 != null\"> AND param1 = #{query.param1} </if>" +
            " <if test=\"query.param2 != null\"> AND param2 = #{query.param2} </if>" +
            " <if test=\"query.param3 != null\"> AND param3 = #{query.param3} </if>" +
            " <if test=\"query.param4 != null\"> AND param4 = #{query.param4} </if>" +
            " <if test=\"query.param5 != null\"> AND param5 = #{query.param5} </if>" +
            " <if test=\"query.param6 != null\"> AND param6 = #{query.param6} </if>" +
            " <if test=\"query.param7 != null\"> AND param7 = #{query.param7} </if>" +
            " <if test=\"query.param8 != null\"> AND param8 = #{query.param8} </if>" +
            " <if test=\"query.reportData != null\"> AND report_data = #{query.reportData} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            "</script>")
    Long count(@Param("query") FinanceOnceReportQuery financeOnceReport);

    @Select("<script> SELECT pk_id,store_id,store_name,year,month,report_date,sale_money,sale_lesson_count,waiting_lesson_money,waiting_lesson_count,used_lesson_money,used_lesson_count,dead_lesson_money,dead_lesson_count,delay_money,out_lesson_money,out_lesson_count,in_lesson_money,in_lesson_count,back_lesson_money,back_lesson_count,param1,param2,param3,param4,param5,param6,param7,param8,report_data,remark,status,created,modified " +
            " FROM finance_once_report " +
            " WHERE store_id = #{id} " +
            "</script>")
    FinanceOnceReportEntity getById(@Param("id") String id);

    @Update("<script> UPDATE finance_once_report SET " +
                " <if test=\"financeOnceReport.storeId != null\"> store_id = #{financeOnceReport.storeId} , </if>" +
                " <if test=\"financeOnceReport.storeName != null\"> store_name = #{financeOnceReport.storeName} , </if>" +
                " <if test=\"financeOnceReport.year != null\"> year = #{financeOnceReport.year} , </if>" +
                " <if test=\"financeOnceReport.month != null\"> month = #{financeOnceReport.month} , </if>" +
                " <if test=\"financeOnceReport.reportDate != null\"> report_date = #{financeOnceReport.reportDate} , </if>" +
                " <if test=\"financeOnceReport.saleMoney != null\"> sale_money = #{financeOnceReport.saleMoney} , </if>" +
                " <if test=\"financeOnceReport.saleLessonCount != null\"> sale_lesson_count = #{financeOnceReport.saleLessonCount} , </if>" +
                " <if test=\"financeOnceReport.waitingLessonMoney != null\"> waiting_lesson_money = #{financeOnceReport.waitingLessonMoney} , </if>" +
                " <if test=\"financeOnceReport.waitingLessonCount != null\"> waiting_lesson_count = #{financeOnceReport.waitingLessonCount} , </if>" +
                " <if test=\"financeOnceReport.usedLessonMoney != null\"> used_lesson_money = #{financeOnceReport.usedLessonMoney} , </if>" +
                " <if test=\"financeOnceReport.usedLessonCount != null\"> used_lesson_count = #{financeOnceReport.usedLessonCount} , </if>" +
                " <if test=\"financeOnceReport.deadLessonMoney != null\"> dead_lesson_money = #{financeOnceReport.deadLessonMoney} , </if>" +
                " <if test=\"financeOnceReport.deadLessonCount != null\"> dead_lesson_count = #{financeOnceReport.deadLessonCount} , </if>" +
                " <if test=\"financeOnceReport.delayMoney != null\"> delay_money = #{financeOnceReport.delayMoney} , </if>" +
                " <if test=\"financeOnceReport.outLessonMoney != null\"> out_lesson_money = #{financeOnceReport.outLessonMoney} , </if>" +
                " <if test=\"financeOnceReport.outLessonCount != null\"> out_lesson_count = #{financeOnceReport.outLessonCount} , </if>" +
                " <if test=\"financeOnceReport.inLessonMoney != null\"> in_lesson_money = #{financeOnceReport.inLessonMoney} , </if>" +
                " <if test=\"financeOnceReport.inLessonCount != null\"> in_lesson_count = #{financeOnceReport.inLessonCount} , </if>" +
                " <if test=\"financeOnceReport.backLessonMoney != null\"> back_lesson_money = #{financeOnceReport.backLessonMoney} , </if>" +
                " <if test=\"financeOnceReport.backLessonCount != null\"> back_lesson_count = #{financeOnceReport.backLessonCount} , </if>" +
                " <if test=\"financeOnceReport.param1 != null\"> param1 = #{financeOnceReport.param1} , </if>" +
                " <if test=\"financeOnceReport.param2 != null\"> param2 = #{financeOnceReport.param2} , </if>" +
                " <if test=\"financeOnceReport.param3 != null\"> param3 = #{financeOnceReport.param3} , </if>" +
                " <if test=\"financeOnceReport.param4 != null\"> param4 = #{financeOnceReport.param4} , </if>" +
                " <if test=\"financeOnceReport.param5 != null\"> param5 = #{financeOnceReport.param5} , </if>" +
                " <if test=\"financeOnceReport.param6 != null\"> param6 = #{financeOnceReport.param6} , </if>" +
                " <if test=\"financeOnceReport.param7 != null\"> param7 = #{financeOnceReport.param7} , </if>" +
                " <if test=\"financeOnceReport.param8 != null\"> param8 = #{financeOnceReport.param8} , </if>" +
                " <if test=\"financeOnceReport.reportData != null\"> report_data = #{financeOnceReport.reportData} , </if>" +
                " <if test=\"financeOnceReport.remark != null\"> remark = #{financeOnceReport.remark} , </if>" +
                " <if test=\"financeOnceReport.status != null\"> status = #{financeOnceReport.status} , </if>" +
                " modified = now() " +
            " WHERE store_id = #{financeOnceReport.storeId} " +
            "</script>")
    int update(@Param("financeOnceReport") FinanceOnceReportEntity financeOnceReport);

    @Update("<script> DELETE  FROM finance_once_report " +
            " WHERE store_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


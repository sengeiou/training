package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * finance_month_report 数据库操作类
 * Created by huai23 on 2018-12-02 20:58:01.
 */ 
@Mapper
public interface FinanceMonthReportRepository {

    @Insert("<script> INSERT INTO finance_month_report ( " +
                " <if test=\"financeMonthReport.storeId != null\"> store_id, </if>" +
                " <if test=\"financeMonthReport.storeName != null\"> store_name, </if>" +
                " <if test=\"financeMonthReport.year != null\"> year, </if>" +
                " <if test=\"financeMonthReport.month != null\"> month, </if>" +
                " <if test=\"financeMonthReport.reportDate != null\"> report_date, </if>" +
                " <if test=\"financeMonthReport.saleMoney != null\"> sale_money, </if>" +
                " <if test=\"financeMonthReport.saleDaysCount != null\"> sale_days_count, </if>" +
                " <if test=\"financeMonthReport.waitingDaysMoney != null\"> waiting_days_money, </if>" +
                " <if test=\"financeMonthReport.waitingDaysCount != null\"> waiting_days_count, </if>" +
                " <if test=\"financeMonthReport.usedDaysMoney != null\"> used_days_money, </if>" +
                " <if test=\"financeMonthReport.usedDaysCount != null\"> used_days_count, </if>" +
                " <if test=\"financeMonthReport.outDaysMoney != null\"> out_days_money, </if>" +
                " <if test=\"financeMonthReport.inDaysMoney != null\"> in_days_money, </if>" +
                " <if test=\"financeMonthReport.backDaysMoney != null\"> back_days_money, </if>" +
                " <if test=\"financeMonthReport.param1 != null\"> param1, </if>" +
                " <if test=\"financeMonthReport.param2 != null\"> param2, </if>" +
                " <if test=\"financeMonthReport.param3 != null\"> param3, </if>" +
                " <if test=\"financeMonthReport.param4 != null\"> param4, </if>" +
                " <if test=\"financeMonthReport.param5 != null\"> param5, </if>" +
                " <if test=\"financeMonthReport.param6 != null\"> param6, </if>" +
                " <if test=\"financeMonthReport.param7 != null\"> param7, </if>" +
                " <if test=\"financeMonthReport.param8 != null\"> param8, </if>" +
                " <if test=\"financeMonthReport.reportData != null\"> report_data, </if>" +
                " <if test=\"financeMonthReport.remark != null\"> remark, </if>" +
                " <if test=\"financeMonthReport.status != null\"> status, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"financeMonthReport.storeId != null\"> #{financeMonthReport.storeId}, </if>" +
                " <if test=\"financeMonthReport.storeName != null\"> #{financeMonthReport.storeName}, </if>" +
                " <if test=\"financeMonthReport.year != null\"> #{financeMonthReport.year}, </if>" +
                " <if test=\"financeMonthReport.month != null\"> #{financeMonthReport.month}, </if>" +
                " <if test=\"financeMonthReport.reportDate != null\"> #{financeMonthReport.reportDate}, </if>" +
                " <if test=\"financeMonthReport.saleMoney != null\"> #{financeMonthReport.saleMoney}, </if>" +
                " <if test=\"financeMonthReport.saleDaysCount != null\"> #{financeMonthReport.saleDaysCount}, </if>" +
                " <if test=\"financeMonthReport.waitingDaysMoney != null\"> #{financeMonthReport.waitingDaysMoney}, </if>" +
                " <if test=\"financeMonthReport.waitingDaysCount != null\"> #{financeMonthReport.waitingDaysCount}, </if>" +
                " <if test=\"financeMonthReport.usedDaysMoney != null\"> #{financeMonthReport.usedDaysMoney}, </if>" +
                " <if test=\"financeMonthReport.usedDaysCount != null\"> #{financeMonthReport.usedDaysCount}, </if>" +
                " <if test=\"financeMonthReport.outDaysMoney != null\"> #{financeMonthReport.outDaysMoney}, </if>" +
                " <if test=\"financeMonthReport.inDaysMoney != null\"> #{financeMonthReport.inDaysMoney}, </if>" +
                " <if test=\"financeMonthReport.backDaysMoney != null\"> #{financeMonthReport.backDaysMoney}, </if>" +
                " <if test=\"financeMonthReport.param1 != null\"> #{financeMonthReport.param1}, </if>" +
                " <if test=\"financeMonthReport.param2 != null\"> #{financeMonthReport.param2}, </if>" +
                " <if test=\"financeMonthReport.param3 != null\"> #{financeMonthReport.param3}, </if>" +
                " <if test=\"financeMonthReport.param4 != null\"> #{financeMonthReport.param4}, </if>" +
                " <if test=\"financeMonthReport.param5 != null\"> #{financeMonthReport.param5}, </if>" +
                " <if test=\"financeMonthReport.param6 != null\"> #{financeMonthReport.param6}, </if>" +
                " <if test=\"financeMonthReport.param7 != null\"> #{financeMonthReport.param7}, </if>" +
                " <if test=\"financeMonthReport.param8 != null\"> #{financeMonthReport.param8}, </if>" +
                " <if test=\"financeMonthReport.reportData != null\"> #{financeMonthReport.reportData}, </if>" +
                " <if test=\"financeMonthReport.remark != null\"> #{financeMonthReport.remark}, </if>" +
                " <if test=\"financeMonthReport.status != null\"> #{financeMonthReport.status}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("financeMonthReport") FinanceMonthReportEntity financeMonthReport);

    @Select("<script> SELECT pk_id,store_id,store_name,year,month,report_date,sale_money,sale_days_count,waiting_days_money,waiting_days_count,used_days_money,used_days_count,out_days_money,in_days_money,back_days_money,param1,param2,param3,param4,param5,param6,param7,param8,report_data,remark,status,created,modified " +
            " FROM finance_month_report " +
            " WHERE 1 = 1 " +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.storeName != null\"> AND store_name = #{query.storeName} </if>" +
            " <if test=\"query.year != null\"> AND year = #{query.year} </if>" +
            " <if test=\"query.month != null\"> AND month = #{query.month} </if>" +
            " <if test=\"query.reportDate != null\"> AND report_date = #{query.reportDate} </if>" +
            " <if test=\"query.saleMoney != null\"> AND sale_money = #{query.saleMoney} </if>" +
            " <if test=\"query.saleDaysCount != null\"> AND sale_days_count = #{query.saleDaysCount} </if>" +
            " <if test=\"query.waitingDaysMoney != null\"> AND waiting_days_money = #{query.waitingDaysMoney} </if>" +
            " <if test=\"query.waitingDaysCount != null\"> AND waiting_days_count = #{query.waitingDaysCount} </if>" +
            " <if test=\"query.usedDaysMoney != null\"> AND used_days_money = #{query.usedDaysMoney} </if>" +
            " <if test=\"query.usedDaysCount != null\"> AND used_days_count = #{query.usedDaysCount} </if>" +
            " <if test=\"query.outDaysMoney != null\"> AND out_days_money = #{query.outDaysMoney} </if>" +
            " <if test=\"query.inDaysMoney != null\"> AND in_days_money = #{query.inDaysMoney} </if>" +
            " <if test=\"query.backDaysMoney != null\"> AND back_days_money = #{query.backDaysMoney} </if>" +
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
    List<FinanceMonthReportEntity> find(@Param("query") FinanceMonthReportQuery financeMonthReport , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM finance_month_report " +
            " WHERE 1 = 1 " +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.storeName != null\"> AND store_name = #{query.storeName} </if>" +
            " <if test=\"query.year != null\"> AND year = #{query.year} </if>" +
            " <if test=\"query.month != null\"> AND month = #{query.month} </if>" +
            " <if test=\"query.reportDate != null\"> AND report_date = #{query.reportDate} </if>" +
            " <if test=\"query.saleMoney != null\"> AND sale_money = #{query.saleMoney} </if>" +
            " <if test=\"query.saleDaysCount != null\"> AND sale_days_count = #{query.saleDaysCount} </if>" +
            " <if test=\"query.waitingDaysMoney != null\"> AND waiting_days_money = #{query.waitingDaysMoney} </if>" +
            " <if test=\"query.waitingDaysCount != null\"> AND waiting_days_count = #{query.waitingDaysCount} </if>" +
            " <if test=\"query.usedDaysMoney != null\"> AND used_days_money = #{query.usedDaysMoney} </if>" +
            " <if test=\"query.usedDaysCount != null\"> AND used_days_count = #{query.usedDaysCount} </if>" +
            " <if test=\"query.outDaysMoney != null\"> AND out_days_money = #{query.outDaysMoney} </if>" +
            " <if test=\"query.inDaysMoney != null\"> AND in_days_money = #{query.inDaysMoney} </if>" +
            " <if test=\"query.backDaysMoney != null\"> AND back_days_money = #{query.backDaysMoney} </if>" +
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
    Long count(@Param("query") FinanceMonthReportQuery financeMonthReport);

    @Select("<script> SELECT pk_id,store_id,store_name,year,month,report_date,sale_money,sale_days_count,waiting_days_money,waiting_days_count,used_days_money,used_days_count,out_days_money,in_days_money,back_days_money,param1,param2,param3,param4,param5,param6,param7,param8,report_data,remark,status,created,modified " +
            " FROM finance_month_report " +
            " WHERE store_id = #{id} " +
            "</script>")
    FinanceMonthReportEntity getById(@Param("id") String id);

    @Update("<script> UPDATE finance_month_report SET " +
                " <if test=\"financeMonthReport.storeId != null\"> store_id = #{financeMonthReport.storeId} , </if>" +
                " <if test=\"financeMonthReport.storeName != null\"> store_name = #{financeMonthReport.storeName} , </if>" +
                " <if test=\"financeMonthReport.year != null\"> year = #{financeMonthReport.year} , </if>" +
                " <if test=\"financeMonthReport.month != null\"> month = #{financeMonthReport.month} , </if>" +
                " <if test=\"financeMonthReport.reportDate != null\"> report_date = #{financeMonthReport.reportDate} , </if>" +
                " <if test=\"financeMonthReport.saleMoney != null\"> sale_money = #{financeMonthReport.saleMoney} , </if>" +
                " <if test=\"financeMonthReport.saleDaysCount != null\"> sale_days_count = #{financeMonthReport.saleDaysCount} , </if>" +
                " <if test=\"financeMonthReport.waitingDaysMoney != null\"> waiting_days_money = #{financeMonthReport.waitingDaysMoney} , </if>" +
                " <if test=\"financeMonthReport.waitingDaysCount != null\"> waiting_days_count = #{financeMonthReport.waitingDaysCount} , </if>" +
                " <if test=\"financeMonthReport.usedDaysMoney != null\"> used_days_money = #{financeMonthReport.usedDaysMoney} , </if>" +
                " <if test=\"financeMonthReport.usedDaysCount != null\"> used_days_count = #{financeMonthReport.usedDaysCount} , </if>" +
                " <if test=\"financeMonthReport.outDaysMoney != null\"> out_days_money = #{financeMonthReport.outDaysMoney} , </if>" +
                " <if test=\"financeMonthReport.inDaysMoney != null\"> in_days_money = #{financeMonthReport.inDaysMoney} , </if>" +
                " <if test=\"financeMonthReport.backDaysMoney != null\"> back_days_money = #{financeMonthReport.backDaysMoney} , </if>" +
                " <if test=\"financeMonthReport.param1 != null\"> param1 = #{financeMonthReport.param1} , </if>" +
                " <if test=\"financeMonthReport.param2 != null\"> param2 = #{financeMonthReport.param2} , </if>" +
                " <if test=\"financeMonthReport.param3 != null\"> param3 = #{financeMonthReport.param3} , </if>" +
                " <if test=\"financeMonthReport.param4 != null\"> param4 = #{financeMonthReport.param4} , </if>" +
                " <if test=\"financeMonthReport.param5 != null\"> param5 = #{financeMonthReport.param5} , </if>" +
                " <if test=\"financeMonthReport.param6 != null\"> param6 = #{financeMonthReport.param6} , </if>" +
                " <if test=\"financeMonthReport.param7 != null\"> param7 = #{financeMonthReport.param7} , </if>" +
                " <if test=\"financeMonthReport.param8 != null\"> param8 = #{financeMonthReport.param8} , </if>" +
                " <if test=\"financeMonthReport.reportData != null\"> report_data = #{financeMonthReport.reportData} , </if>" +
                " <if test=\"financeMonthReport.remark != null\"> remark = #{financeMonthReport.remark} , </if>" +
                " <if test=\"financeMonthReport.status != null\"> status = #{financeMonthReport.status} , </if>" +
                " modified = now() " +
            " WHERE store_id = #{financeMonthReport.storeId} " +
            "</script>")
    int update(@Param("financeMonthReport") FinanceMonthReportEntity financeMonthReport);

    @Update("<script> DELETE  FROM finance_month_report " +
            " WHERE store_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


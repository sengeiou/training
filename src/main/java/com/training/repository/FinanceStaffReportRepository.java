package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * finance_staff_report 数据库操作类
 * Created by huai23 on 2018-12-02 22:02:12.
 */ 
@Mapper
public interface FinanceStaffReportRepository {

    @Insert("<script> INSERT INTO finance_staff_report ( " +
                " <if test=\"financeStaffReport.staffId != null\"> staff_id, </if>" +
                " <if test=\"financeStaffReport.staffName != null\"> staff_name, </if>" +
                " <if test=\"financeStaffReport.storeId != null\"> store_id, </if>" +
                " <if test=\"financeStaffReport.storeName != null\"> store_name, </if>" +
                " <if test=\"financeStaffReport.templateId != null\"> template_id, </if>" +
                " <if test=\"financeStaffReport.templateName != null\"> template_name, </if>" +
                " <if test=\"financeStaffReport.type != null\"> type, </if>" +
                " <if test=\"financeStaffReport.year != null\"> year, </if>" +
                " <if test=\"financeStaffReport.month != null\"> month, </if>" +
                " <if test=\"financeStaffReport.reportDate != null\"> report_date, </if>" +
                " <if test=\"financeStaffReport.job != null\"> job, </if>" +
                " <if test=\"financeStaffReport.kpiScore != null\"> kpi_score, </if>" +
                " <if test=\"financeStaffReport.star != null\"> star, </if>" +
                " <if test=\"financeStaffReport.saleMoney != null\"> sale_money, </if>" +
                " <if test=\"financeStaffReport.xkMoney != null\"> xk_money, </if>" +
                " <if test=\"financeStaffReport.timesCardLessonCount != null\"> times_card_lesson_count, </if>" +
                " <if test=\"financeStaffReport.monthCardSingleLessonCount != null\"> month_card_single_lesson_count, </if>" +
                " <if test=\"financeStaffReport.monthCardMultiLessonCount != null\"> month_card_multi_lesson_count, </if>" +
                " <if test=\"financeStaffReport.tyCardMultiLessonCount != null\"> ty_card_multi_lesson_count, </if>" +
                " <if test=\"financeStaffReport.specialLessonCount != null\"> special_lesson_count, </if>" +
                " <if test=\"financeStaffReport.teamLessonCount != null\"> team_lesson_count, </if>" +
                " <if test=\"financeStaffReport.param1 != null\"> param1, </if>" +
                " <if test=\"financeStaffReport.param2 != null\"> param2, </if>" +
                " <if test=\"financeStaffReport.param3 != null\"> param3, </if>" +
                " <if test=\"financeStaffReport.param4 != null\"> param4, </if>" +
                " <if test=\"financeStaffReport.param5 != null\"> param5, </if>" +
                " <if test=\"financeStaffReport.param6 != null\"> param6, </if>" +
                " <if test=\"financeStaffReport.param7 != null\"> param7, </if>" +
                " <if test=\"financeStaffReport.param8 != null\"> param8, </if>" +
                " <if test=\"financeStaffReport.reportData != null\"> report_data, </if>" +
                " <if test=\"financeStaffReport.remark != null\"> remark, </if>" +
                " <if test=\"financeStaffReport.status != null\"> status, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"financeStaffReport.staffId != null\"> #{financeStaffReport.staffId}, </if>" +
                " <if test=\"financeStaffReport.staffName != null\"> #{financeStaffReport.staffName}, </if>" +
                " <if test=\"financeStaffReport.storeId != null\"> #{financeStaffReport.storeId}, </if>" +
                " <if test=\"financeStaffReport.storeName != null\"> #{financeStaffReport.storeName}, </if>" +
                " <if test=\"financeStaffReport.templateId != null\"> #{financeStaffReport.templateId}, </if>" +
                " <if test=\"financeStaffReport.templateName != null\"> #{financeStaffReport.templateName}, </if>" +
                " <if test=\"financeStaffReport.type != null\"> #{financeStaffReport.type}, </if>" +
                " <if test=\"financeStaffReport.year != null\"> #{financeStaffReport.year}, </if>" +
                " <if test=\"financeStaffReport.month != null\"> #{financeStaffReport.month}, </if>" +
                " <if test=\"financeStaffReport.reportDate != null\"> #{financeStaffReport.reportDate}, </if>" +
                " <if test=\"financeStaffReport.job != null\"> #{financeStaffReport.job}, </if>" +
                " <if test=\"financeStaffReport.kpiScore != null\"> #{financeStaffReport.kpiScore}, </if>" +
                " <if test=\"financeStaffReport.star != null\"> #{financeStaffReport.star}, </if>" +
                " <if test=\"financeStaffReport.saleMoney != null\"> #{financeStaffReport.saleMoney}, </if>" +
                " <if test=\"financeStaffReport.xkMoney != null\"> #{financeStaffReport.xkMoney}, </if>" +
                " <if test=\"financeStaffReport.timesCardLessonCount != null\"> #{financeStaffReport.timesCardLessonCount}, </if>" +
                " <if test=\"financeStaffReport.monthCardSingleLessonCount != null\"> #{financeStaffReport.monthCardSingleLessonCount}, </if>" +
                " <if test=\"financeStaffReport.monthCardMultiLessonCount != null\"> #{financeStaffReport.monthCardMultiLessonCount}, </if>" +
                " <if test=\"financeStaffReport.tyCardMultiLessonCount != null\"> #{financeStaffReport.tyCardMultiLessonCount}, </if>" +
                " <if test=\"financeStaffReport.specialLessonCount != null\"> #{financeStaffReport.specialLessonCount}, </if>" +
                " <if test=\"financeStaffReport.teamLessonCount != null\"> #{financeStaffReport.teamLessonCount}, </if>" +
                " <if test=\"financeStaffReport.param1 != null\"> #{financeStaffReport.param1}, </if>" +
                " <if test=\"financeStaffReport.param2 != null\"> #{financeStaffReport.param2}, </if>" +
                " <if test=\"financeStaffReport.param3 != null\"> #{financeStaffReport.param3}, </if>" +
                " <if test=\"financeStaffReport.param4 != null\"> #{financeStaffReport.param4}, </if>" +
                " <if test=\"financeStaffReport.param5 != null\"> #{financeStaffReport.param5}, </if>" +
                " <if test=\"financeStaffReport.param6 != null\"> #{financeStaffReport.param6}, </if>" +
                " <if test=\"financeStaffReport.param7 != null\"> #{financeStaffReport.param7}, </if>" +
                " <if test=\"financeStaffReport.param8 != null\"> #{financeStaffReport.param8}, </if>" +
                " <if test=\"financeStaffReport.reportData != null\"> #{financeStaffReport.reportData}, </if>" +
                " <if test=\"financeStaffReport.remark != null\"> #{financeStaffReport.remark}, </if>" +
                " <if test=\"financeStaffReport.status != null\"> #{financeStaffReport.status}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("financeStaffReport") FinanceStaffReportEntity financeStaffReport);

    @Select("<script> SELECT pk_id,staff_id,staff_name,store_id,store_name,template_id,template_name,type,year,month,report_date,job,kpi_score,star,sale_money,xk_money,times_card_lesson_count,month_card_single_lesson_count,month_card_multi_lesson_count,ty_card_multi_lesson_count,special_lesson_count,team_lesson_count,param1,param2,param3,param4,param5,param6,param7,param8,report_data,remark,status,created,modified " +
            " FROM finance_staff_report " +
            " WHERE 1 = 1 " +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.staffName != null\"> AND staff_id in ( select staff_id from staff where custname like CONCAT('%',#{query.staffName},'%') ) </if>" +
            " <if test=\"query.phone != null\"> AND staff_id in ( select staff_id from staff where phone like CONCAT('%',#{query.phone},'%') ) </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.storeName != null\"> AND store_name = #{query.storeName} </if>" +
            " <if test=\"query.templateId != null\"> AND template_id = #{query.templateId} </if>" +
            " <if test=\"query.templateName != null\"> AND template_name = #{query.templateName} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.year != null\"> AND year = #{query.year} </if>" +
            " <if test=\"query.month != null\"> AND month = #{query.month} </if>" +
            " <if test=\"query.reportDate != null\"> AND report_date = #{query.reportDate} </if>" +
            " <if test=\"query.job != null\"> AND job = #{query.job} </if>" +
            " <if test=\"query.kpiScore != null\"> AND kpi_score = #{query.kpiScore} </if>" +
            " <if test=\"query.star != null\"> AND star = #{query.star} </if>" +
            " <if test=\"query.saleMoney != null\"> AND sale_money = #{query.saleMoney} </if>" +
            " <if test=\"query.xkMoney != null\"> AND xk_money = #{query.xkMoney} </if>" +
            " <if test=\"query.timesCardLessonCount != null\"> AND times_card_lesson_count = #{query.timesCardLessonCount} </if>" +
            " <if test=\"query.monthCardSingleLessonCount != null\"> AND month_card_single_lesson_count = #{query.monthCardSingleLessonCount} </if>" +
            " <if test=\"query.monthCardMultiLessonCount != null\"> AND month_card_multi_lesson_count = #{query.monthCardMultiLessonCount} </if>" +
            " <if test=\"query.tyCardMultiLessonCount != null\"> AND ty_card_multi_lesson_count = #{query.tyCardMultiLessonCount} </if>" +
            " <if test=\"query.specialLessonCount != null\"> AND special_lesson_count = #{query.specialLessonCount} </if>" +
            " <if test=\"query.teamLessonCount != null\"> AND team_lesson_count = #{query.teamLessonCount} </if>" +
            " <if test=\"query.param1 != null\"> AND param1 = #{query.param1} </if>" +
            " <if test=\"query.param2 != null\"> AND param2 = #{query.param2} </if>" +
            " <if test=\"query.param3 != null\"> AND param3 = #{query.param3} </if>" +
            " <if test=\"query.param4 != null\"> AND param4 = #{query.param4} </if>" +
            " <if test=\"query.param5 != null\"> AND param5 = #{query.param5} </if>" +
            " <if test=\"query.param6 != null\"> AND param6 = #{query.param6} </if>" +
            " <if test=\"query.param7 != null\"> AND param7 = #{query.param7} </if>" +
            " <if test=\"query.param8 != null\"> AND param8 = #{query.param8} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<FinanceStaffReportEntity> find(@Param("query") FinanceStaffReportQuery financeStaffReport , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM finance_staff_report " +
            " WHERE 1 = 1 " +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.staffName != null\"> AND staff_id in ( select staff_id from staff where custname like CONCAT('%',#{query.staffName},'%') ) </if>" +
            " <if test=\"query.phone != null\"> AND staff_id in ( select staff_id from staff where phone like CONCAT('%',#{query.phone},'%') ) </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.storeName != null\"> AND store_name = #{query.storeName} </if>" +
            " <if test=\"query.templateId != null\"> AND template_id = #{query.templateId} </if>" +
            " <if test=\"query.templateName != null\"> AND template_name = #{query.templateName} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.year != null\"> AND year = #{query.year} </if>" +
            " <if test=\"query.month != null\"> AND month = #{query.month} </if>" +
            " <if test=\"query.reportDate != null\"> AND report_date = #{query.reportDate} </if>" +
            " <if test=\"query.job != null\"> AND job = #{query.job} </if>" +
            " <if test=\"query.kpiScore != null\"> AND kpi_score = #{query.kpiScore} </if>" +
            " <if test=\"query.star != null\"> AND star = #{query.star} </if>" +
            " <if test=\"query.saleMoney != null\"> AND sale_money = #{query.saleMoney} </if>" +
            " <if test=\"query.xkMoney != null\"> AND xk_money = #{query.xkMoney} </if>" +
            " <if test=\"query.timesCardLessonCount != null\"> AND times_card_lesson_count = #{query.timesCardLessonCount} </if>" +
            " <if test=\"query.monthCardSingleLessonCount != null\"> AND month_card_single_lesson_count = #{query.monthCardSingleLessonCount} </if>" +
            " <if test=\"query.monthCardMultiLessonCount != null\"> AND month_card_multi_lesson_count = #{query.monthCardMultiLessonCount} </if>" +
            " <if test=\"query.tyCardMultiLessonCount != null\"> AND ty_card_multi_lesson_count = #{query.tyCardMultiLessonCount} </if>" +
            " <if test=\"query.specialLessonCount != null\"> AND special_lesson_count = #{query.specialLessonCount} </if>" +
            " <if test=\"query.teamLessonCount != null\"> AND team_lesson_count = #{query.teamLessonCount} </if>" +
            " <if test=\"query.param1 != null\"> AND param1 = #{query.param1} </if>" +
            " <if test=\"query.param2 != null\"> AND param2 = #{query.param2} </if>" +
            " <if test=\"query.param3 != null\"> AND param3 = #{query.param3} </if>" +
            " <if test=\"query.param4 != null\"> AND param4 = #{query.param4} </if>" +
            " <if test=\"query.param5 != null\"> AND param5 = #{query.param5} </if>" +
            " <if test=\"query.param6 != null\"> AND param6 = #{query.param6} </if>" +
            " <if test=\"query.param7 != null\"> AND param7 = #{query.param7} </if>" +
            " <if test=\"query.param8 != null\"> AND param8 = #{query.param8} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            "</script>")
    Long count(@Param("query") FinanceStaffReportQuery financeStaffReport);

    @Select("<script> SELECT pk_id,staff_id,staff_name,store_id,store_name,template_id,template_name,type,year,month,report_date,job,kpi_score,star,sale_money,xk_money,times_card_lesson_count,month_card_single_lesson_count,month_card_multi_lesson_count,ty_card_multi_lesson_count,special_lesson_count,team_lesson_count,param1,param2,param3,param4,param5,param6,param7,param8,report_data,remark,status,created,modified " +
            " FROM finance_staff_report " +
            " WHERE staff_id = #{id} " +
            "</script>")
    FinanceStaffReportEntity getById(@Param("id") String id);

    @Update("<script> UPDATE finance_staff_report SET " +
                " <if test=\"financeStaffReport.staffId != null\"> staff_id = #{financeStaffReport.staffId} , </if>" +
                " <if test=\"financeStaffReport.staffName != null\"> staff_name = #{financeStaffReport.staffName} , </if>" +
                " <if test=\"financeStaffReport.storeId != null\"> store_id = #{financeStaffReport.storeId} , </if>" +
                " <if test=\"financeStaffReport.storeName != null\"> store_name = #{financeStaffReport.storeName} , </if>" +
                " <if test=\"financeStaffReport.templateId != null\"> template_id = #{financeStaffReport.templateId} , </if>" +
                " <if test=\"financeStaffReport.templateName != null\"> template_name = #{financeStaffReport.templateName} , </if>" +
                " <if test=\"financeStaffReport.type != null\"> type = #{financeStaffReport.type} , </if>" +
                " <if test=\"financeStaffReport.year != null\"> year = #{financeStaffReport.year} , </if>" +
                " <if test=\"financeStaffReport.month != null\"> month = #{financeStaffReport.month} , </if>" +
                " <if test=\"financeStaffReport.reportDate != null\"> report_date = #{financeStaffReport.reportDate} , </if>" +
                " <if test=\"financeStaffReport.job != null\"> job = #{financeStaffReport.job} , </if>" +
                " <if test=\"financeStaffReport.kpiScore != null\"> kpi_score = #{financeStaffReport.kpiScore} , </if>" +
                " <if test=\"financeStaffReport.star != null\"> star = #{financeStaffReport.star} , </if>" +
                " <if test=\"financeStaffReport.saleMoney != null\"> sale_money = #{financeStaffReport.saleMoney} , </if>" +
                " <if test=\"financeStaffReport.xkMoney != null\"> xk_money = #{financeStaffReport.xkMoney} , </if>" +
                " <if test=\"financeStaffReport.timesCardLessonCount != null\"> times_card_lesson_count = #{financeStaffReport.timesCardLessonCount} , </if>" +
                " <if test=\"financeStaffReport.monthCardSingleLessonCount != null\"> month_card_single_lesson_count = #{financeStaffReport.monthCardSingleLessonCount} , </if>" +
                " <if test=\"financeStaffReport.monthCardMultiLessonCount != null\"> month_card_multi_lesson_count = #{financeStaffReport.monthCardMultiLessonCount} , </if>" +
                " <if test=\"financeStaffReport.tyCardMultiLessonCount != null\"> ty_card_multi_lesson_count = #{financeStaffReport.tyCardMultiLessonCount} , </if>" +
                " <if test=\"financeStaffReport.specialLessonCount != null\"> special_lesson_count = #{financeStaffReport.specialLessonCount} , </if>" +
                " <if test=\"financeStaffReport.teamLessonCount != null\"> team_lesson_count = #{financeStaffReport.teamLessonCount} , </if>" +
                " <if test=\"financeStaffReport.param1 != null\"> param1 = #{financeStaffReport.param1} , </if>" +
                " <if test=\"financeStaffReport.param2 != null\"> param2 = #{financeStaffReport.param2} , </if>" +
                " <if test=\"financeStaffReport.param3 != null\"> param3 = #{financeStaffReport.param3} , </if>" +
                " <if test=\"financeStaffReport.param4 != null\"> param4 = #{financeStaffReport.param4} , </if>" +
                " <if test=\"financeStaffReport.param5 != null\"> param5 = #{financeStaffReport.param5} , </if>" +
                " <if test=\"financeStaffReport.param6 != null\"> param6 = #{financeStaffReport.param6} , </if>" +
                " <if test=\"financeStaffReport.param7 != null\"> param7 = #{financeStaffReport.param7} , </if>" +
                " <if test=\"financeStaffReport.param8 != null\"> param8 = #{financeStaffReport.param8} , </if>" +
                " <if test=\"financeStaffReport.reportData != null\"> report_data = #{financeStaffReport.reportData} , </if>" +
                " <if test=\"financeStaffReport.remark != null\"> remark = #{financeStaffReport.remark} , </if>" +
                " <if test=\"financeStaffReport.status != null\"> status = #{financeStaffReport.status} , </if>" +
                " modified = now() " +
            " WHERE staff_id = #{financeStaffReport.staffId} " +
            "</script>")
    int update(@Param("financeStaffReport") FinanceStaffReportEntity financeStaffReport);

    @Update("<script> DELETE  FROM finance_staff_report " +
            " WHERE staff_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}


package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * kpi_staff_month 数据库操作类
 * Created by huai23 on 2018-07-13 23:24:52.
 */ 
@Mapper
public interface KpiStaffMonthRepository {

    @Insert("<script> INSERT INTO kpi_staff_month ( " +
                " <if test=\"kpiStaffMonth.staffId != null\"> staff_id, </if>" +
                " <if test=\"kpiStaffMonth.staffName != null\"> staff_name, </if>" +
                " <if test=\"kpiStaffMonth.storeId != null\"> store_id, </if>" +
                " <if test=\"kpiStaffMonth.templateId != null\"> template_id, </if>" +
                " <if test=\"kpiStaffMonth.type != null\"> type, </if>" +
                " <if test=\"kpiStaffMonth.year != null\"> year, </if>" +
                " <if test=\"kpiStaffMonth.month != null\"> month, </if>" +
                " <if test=\"kpiStaffMonth.xks != null\"> xks, </if>" +
                " <if test=\"kpiStaffMonth.jks != null\"> jks, </if>" +
                " <if test=\"kpiStaffMonth.sjks != null\"> sjks, </if>" +
                " <if test=\"kpiStaffMonth.fsjks != null\"> fsjks, </if>" +
                " <if test=\"kpiStaffMonth.yxhys != null\"> yxhys, </if>" +
                " <if test=\"kpiStaffMonth.yyts != null\"> yyts, </if>" +
                " <if test=\"kpiStaffMonth.xkl != null\"> xkl, </if>" +
                " <if test=\"kpiStaffMonth.hyd != null\"> hyd, </if>" +
                " <if test=\"kpiStaffMonth.zjs != null\"> zjs, </if>" +
                " <if test=\"kpiStaffMonth.tnkh != null\"> tnkh, </if>" +
                " <if test=\"kpiStaffMonth.zykh != null\"> zykh, </if>" +
                " <if test=\"kpiStaffMonth.tss != null\"> tss, </if>" +
                " <if test=\"kpiStaffMonth.hydp != null\"> hydp, </if>" +
                " <if test=\"kpiStaffMonth.zye != null\"> zye, </if>" +
                " <if test=\"kpiStaffMonth.xsmb != null\"> xsmb, </if>" +
                " <if test=\"kpiStaffMonth.xswcl != null\"> xswcl, </if>" +
                " <if test=\"kpiStaffMonth.cjs != null\"> cjs, </if>" +
                " <if test=\"kpiStaffMonth.tcs != null\"> tcs, </if>" +
                " <if test=\"kpiStaffMonth.tczhl != null\"> tczhl, </if>" +
                " <if test=\"kpiStaffMonth.kpiScore != null\"> kpi_score, </if>" +
                " <if test=\"kpiStaffMonth.kpiData != null\"> kpi_data, </if>" +
                " <if test=\"kpiStaffMonth.param1 != null\"> param1, </if>" +
                " <if test=\"kpiStaffMonth.param2 != null\"> param2, </if>" +
                " <if test=\"kpiStaffMonth.param3 != null\"> param3, </if>" +
                " <if test=\"kpiStaffMonth.param4 != null\"> param4, </if>" +
                " <if test=\"kpiStaffMonth.param5 != null\"> param5, </if>" +
                " <if test=\"kpiStaffMonth.param6 != null\"> param6, </if>" +
                " <if test=\"kpiStaffMonth.param7 != null\"> param7, </if>" +
                " <if test=\"kpiStaffMonth.param8 != null\"> param8, </if>" +
                " <if test=\"kpiStaffMonth.remark != null\"> remark, </if>" +
                " <if test=\"kpiStaffMonth.status != null\"> status, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"kpiStaffMonth.staffId != null\"> #{kpiStaffMonth.staffId}, </if>" +
                " <if test=\"kpiStaffMonth.staffName != null\"> #{kpiStaffMonth.staffName}, </if>" +
                " <if test=\"kpiStaffMonth.storeId != null\"> #{kpiStaffMonth.storeId}, </if>" +
                " <if test=\"kpiStaffMonth.templateId != null\"> #{kpiStaffMonth.templateId}, </if>" +
                " <if test=\"kpiStaffMonth.type != null\"> #{kpiStaffMonth.type}, </if>" +
                " <if test=\"kpiStaffMonth.year != null\"> #{kpiStaffMonth.year}, </if>" +
                " <if test=\"kpiStaffMonth.month != null\"> #{kpiStaffMonth.month}, </if>" +
                " <if test=\"kpiStaffMonth.xks != null\"> #{kpiStaffMonth.xks}, </if>" +
                " <if test=\"kpiStaffMonth.jks != null\"> #{kpiStaffMonth.jks}, </if>" +
                " <if test=\"kpiStaffMonth.sjks != null\"> #{kpiStaffMonth.sjks}, </if>" +
                " <if test=\"kpiStaffMonth.fsjks != null\"> #{kpiStaffMonth.fsjks}, </if>" +
                " <if test=\"kpiStaffMonth.yxhys != null\"> #{kpiStaffMonth.yxhys}, </if>" +
                " <if test=\"kpiStaffMonth.yyts != null\"> #{kpiStaffMonth.yyts}, </if>" +
                " <if test=\"kpiStaffMonth.xkl != null\"> #{kpiStaffMonth.xkl}, </if>" +
                " <if test=\"kpiStaffMonth.hyd != null\"> #{kpiStaffMonth.hyd}, </if>" +
                " <if test=\"kpiStaffMonth.zjs != null\"> #{kpiStaffMonth.zjs}, </if>" +
                " <if test=\"kpiStaffMonth.tnkh != null\"> #{kpiStaffMonth.tnkh}, </if>" +
                " <if test=\"kpiStaffMonth.zykh != null\"> #{kpiStaffMonth.zykh}, </if>" +
                " <if test=\"kpiStaffMonth.tss != null\"> #{kpiStaffMonth.tss}, </if>" +
                " <if test=\"kpiStaffMonth.hydp != null\"> #{kpiStaffMonth.hydp}, </if>" +
                " <if test=\"kpiStaffMonth.zye != null\"> #{kpiStaffMonth.zye}, </if>" +
                " <if test=\"kpiStaffMonth.xsmb != null\"> #{kpiStaffMonth.xsmb}, </if>" +
                " <if test=\"kpiStaffMonth.xswcl != null\"> #{kpiStaffMonth.xswcl}, </if>" +
                " <if test=\"kpiStaffMonth.cjs != null\"> #{kpiStaffMonth.cjs}, </if>" +
                " <if test=\"kpiStaffMonth.tcs != null\"> #{kpiStaffMonth.tcs}, </if>" +
                " <if test=\"kpiStaffMonth.tczhl != null\"> #{kpiStaffMonth.tczhl}, </if>" +
                " <if test=\"kpiStaffMonth.kpiScore != null\"> #{kpiStaffMonth.kpiScore}, </if>" +
                " <if test=\"kpiStaffMonth.kpiData != null\"> #{kpiStaffMonth.kpiData}, </if>" +
                " <if test=\"kpiStaffMonth.param1 != null\"> #{kpiStaffMonth.param1}, </if>" +
                " <if test=\"kpiStaffMonth.param2 != null\"> #{kpiStaffMonth.param2}, </if>" +
                " <if test=\"kpiStaffMonth.param3 != null\"> #{kpiStaffMonth.param3}, </if>" +
                " <if test=\"kpiStaffMonth.param4 != null\"> #{kpiStaffMonth.param4}, </if>" +
                " <if test=\"kpiStaffMonth.param5 != null\"> #{kpiStaffMonth.param5}, </if>" +
                " <if test=\"kpiStaffMonth.param6 != null\"> #{kpiStaffMonth.param6}, </if>" +
                " <if test=\"kpiStaffMonth.param7 != null\"> #{kpiStaffMonth.param7}, </if>" +
                " <if test=\"kpiStaffMonth.param8 != null\"> #{kpiStaffMonth.param8}, </if>" +
                " <if test=\"kpiStaffMonth.remark != null\"> #{kpiStaffMonth.remark}, </if>" +
                " <if test=\"kpiStaffMonth.status != null\"> #{kpiStaffMonth.status}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("kpiStaffMonth") KpiStaffMonthEntity kpiStaffMonth);

    @Select("<script> SELECT pk_id,staff_id,staff_name,store_id,template_id,type,year,month,xks,jks,sjks,fsjks,yxhys,yyts,xkl,hyd,zjs,tnkh,zykh,tss,hydp,zye,xsmb,xswcl,cjs,tcs,tczhl,kpi_score,kpi_data,param1,param2,param3,param4,param5,param6,param7,param8,remark,status,created,modified " +
            " FROM kpi_staff_month " +
            " WHERE 1 = 1 " +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.staffName != null\"> AND staff_name = #{query.staffName} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.templateId != null\"> AND template_id = #{query.templateId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.year != null\"> AND year = #{query.year} </if>" +
            " <if test=\"query.month != null\"> AND month = #{query.month} </if>" +
            " <if test=\"query.xks != null\"> AND xks = #{query.xks} </if>" +
            " <if test=\"query.jks != null\"> AND jks = #{query.jks} </if>" +
            " <if test=\"query.sjks != null\"> AND sjks = #{query.sjks} </if>" +
            " <if test=\"query.fsjks != null\"> AND fsjks = #{query.fsjks} </if>" +
            " <if test=\"query.yxhys != null\"> AND yxhys = #{query.yxhys} </if>" +
            " <if test=\"query.yyts != null\"> AND yyts = #{query.yyts} </if>" +
            " <if test=\"query.xkl != null\"> AND xkl = #{query.xkl} </if>" +
            " <if test=\"query.hyd != null\"> AND hyd = #{query.hyd} </if>" +
            " <if test=\"query.zjs != null\"> AND zjs = #{query.zjs} </if>" +
            " <if test=\"query.tnkh != null\"> AND tnkh = #{query.tnkh} </if>" +
            " <if test=\"query.zykh != null\"> AND zykh = #{query.zykh} </if>" +
            " <if test=\"query.tss != null\"> AND tss = #{query.tss} </if>" +
            " <if test=\"query.hydp != null\"> AND hydp = #{query.hydp} </if>" +
            " <if test=\"query.zye != null\"> AND zye = #{query.zye} </if>" +
            " <if test=\"query.xsmb != null\"> AND xsmb = #{query.xsmb} </if>" +
            " <if test=\"query.xswcl != null\"> AND xswcl = #{query.xswcl} </if>" +
            " <if test=\"query.cjs != null\"> AND cjs = #{query.cjs} </if>" +
            " <if test=\"query.tcs != null\"> AND tcs = #{query.tcs} </if>" +
            " <if test=\"query.tczhl != null\"> AND tczhl = #{query.tczhl} </if>" +
            " <if test=\"query.kpiScore != null\"> AND kpi_score = #{query.kpiScore} </if>" +
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
    List<KpiStaffMonthEntity> find(@Param("query") KpiStaffMonthQuery kpiStaffMonth , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM kpi_staff_month " +
            " WHERE 1 = 1 " +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.staffName != null\"> AND staff_name = #{query.staffName} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.templateId != null\"> AND template_id = #{query.templateId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.year != null\"> AND year = #{query.year} </if>" +
            " <if test=\"query.month != null\"> AND month = #{query.month} </if>" +
            " <if test=\"query.xks != null\"> AND xks = #{query.xks} </if>" +
            " <if test=\"query.jks != null\"> AND jks = #{query.jks} </if>" +
            " <if test=\"query.sjks != null\"> AND sjks = #{query.sjks} </if>" +
            " <if test=\"query.fsjks != null\"> AND fsjks = #{query.fsjks} </if>" +
            " <if test=\"query.yxhys != null\"> AND yxhys = #{query.yxhys} </if>" +
            " <if test=\"query.yyts != null\"> AND yyts = #{query.yyts} </if>" +
            " <if test=\"query.xkl != null\"> AND xkl = #{query.xkl} </if>" +
            " <if test=\"query.hyd != null\"> AND hyd = #{query.hyd} </if>" +
            " <if test=\"query.zjs != null\"> AND zjs = #{query.zjs} </if>" +
            " <if test=\"query.tnkh != null\"> AND tnkh = #{query.tnkh} </if>" +
            " <if test=\"query.zykh != null\"> AND zykh = #{query.zykh} </if>" +
            " <if test=\"query.tss != null\"> AND tss = #{query.tss} </if>" +
            " <if test=\"query.hydp != null\"> AND hydp = #{query.hydp} </if>" +
            " <if test=\"query.zye != null\"> AND zye = #{query.zye} </if>" +
            " <if test=\"query.xsmb != null\"> AND xsmb = #{query.xsmb} </if>" +
            " <if test=\"query.xswcl != null\"> AND xswcl = #{query.xswcl} </if>" +
            " <if test=\"query.cjs != null\"> AND cjs = #{query.cjs} </if>" +
            " <if test=\"query.tcs != null\"> AND tcs = #{query.tcs} </if>" +
            " <if test=\"query.tczhl != null\"> AND tczhl = #{query.tczhl} </if>" +
            " <if test=\"query.kpiScore != null\"> AND kpi_score = #{query.kpiScore} </if>" +
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
    Long count(@Param("query") KpiStaffMonthQuery kpiStaffMonth);

    @Select("<script> SELECT pk_id,staff_id,staff_name,store_id,template_id,type,year,month,xks,jks,sjks,fsjks,yxhys,yyts,xkl,hyd,zjs,tnkh,zykh,tss,hydp,zye,xsmb,xswcl,cjs,tcs,tczhl,kpi_score,kpi_data,param1,param2,param3,param4,param5,param6,param7,param8,remark,status,created,modified " +
            " FROM kpi_staff_month " +
            " WHERE staff_id = #{id} and month = #{kpiStaffMonth.month} " +
            "</script>")
    KpiStaffMonthEntity getByIdAndMonth(@Param("id") String id,@Param("month") String month);

    @Update("<script> UPDATE kpi_staff_month SET " +
                " <if test=\"kpiStaffMonth.staffName != null\"> staff_name = #{kpiStaffMonth.staffName} , </if>" +
                " <if test=\"kpiStaffMonth.storeId != null\"> store_id = #{kpiStaffMonth.storeId} , </if>" +
                " <if test=\"kpiStaffMonth.templateId != null\"> template_id = #{kpiStaffMonth.templateId} , </if>" +
                " <if test=\"kpiStaffMonth.type != null\"> type = #{kpiStaffMonth.type} , </if>" +
                " <if test=\"kpiStaffMonth.xks != null\"> xks = #{kpiStaffMonth.xks} , </if>" +
                " <if test=\"kpiStaffMonth.jks != null\"> jks = #{kpiStaffMonth.jks} , </if>" +
                " <if test=\"kpiStaffMonth.sjks != null\"> sjks = #{kpiStaffMonth.sjks} , </if>" +
                " <if test=\"kpiStaffMonth.fsjks != null\"> fsjks = #{kpiStaffMonth.fsjks} , </if>" +
                " <if test=\"kpiStaffMonth.yxhys != null\"> yxhys = #{kpiStaffMonth.yxhys} , </if>" +
                " <if test=\"kpiStaffMonth.yyts != null\"> yyts = #{kpiStaffMonth.yyts} , </if>" +
                " <if test=\"kpiStaffMonth.xkl != null\"> xkl = #{kpiStaffMonth.xkl} , </if>" +
                " <if test=\"kpiStaffMonth.hyd != null\"> hyd = #{kpiStaffMonth.hyd} , </if>" +
                " <if test=\"kpiStaffMonth.zjs != null\"> zjs = #{kpiStaffMonth.zjs} , </if>" +
                " <if test=\"kpiStaffMonth.tnkh != null\"> tnkh = #{kpiStaffMonth.tnkh} , </if>" +
                " <if test=\"kpiStaffMonth.zykh != null\"> zykh = #{kpiStaffMonth.zykh} , </if>" +
                " <if test=\"kpiStaffMonth.tss != null\"> tss = #{kpiStaffMonth.tss} , </if>" +
                " <if test=\"kpiStaffMonth.hydp != null\"> hydp = #{kpiStaffMonth.hydp} , </if>" +
                " <if test=\"kpiStaffMonth.zye != null\"> zye = #{kpiStaffMonth.zye} , </if>" +
                " <if test=\"kpiStaffMonth.xsmb != null\"> xsmb = #{kpiStaffMonth.xsmb} , </if>" +
                " <if test=\"kpiStaffMonth.xswcl != null\"> xswcl = #{kpiStaffMonth.xswcl} , </if>" +
                " <if test=\"kpiStaffMonth.cjs != null\"> cjs = #{kpiStaffMonth.cjs} , </if>" +
                " <if test=\"kpiStaffMonth.tcs != null\"> tcs = #{kpiStaffMonth.tcs} , </if>" +
                " <if test=\"kpiStaffMonth.tczhl != null\"> tczhl = #{kpiStaffMonth.tczhl} , </if>" +
                " <if test=\"kpiStaffMonth.kpiScore != null\"> kpi_score = #{kpiStaffMonth.kpiScore} , </if>" +
                " <if test=\"kpiStaffMonth.kpiData != null\"> kpi_data = #{kpiStaffMonth.kpiData} , </if>" +
                " <if test=\"kpiStaffMonth.param1 != null\"> param1 = #{kpiStaffMonth.param1} , </if>" +
                " <if test=\"kpiStaffMonth.param2 != null\"> param2 = #{kpiStaffMonth.param2} , </if>" +
                " <if test=\"kpiStaffMonth.param3 != null\"> param3 = #{kpiStaffMonth.param3} , </if>" +
                " <if test=\"kpiStaffMonth.param4 != null\"> param4 = #{kpiStaffMonth.param4} , </if>" +
                " <if test=\"kpiStaffMonth.param5 != null\"> param5 = #{kpiStaffMonth.param5} , </if>" +
                " <if test=\"kpiStaffMonth.param6 != null\"> param6 = #{kpiStaffMonth.param6} , </if>" +
                " <if test=\"kpiStaffMonth.param7 != null\"> param7 = #{kpiStaffMonth.param7} , </if>" +
                " <if test=\"kpiStaffMonth.param8 != null\"> param8 = #{kpiStaffMonth.param8} , </if>" +
                " <if test=\"kpiStaffMonth.remark != null\"> remark = #{kpiStaffMonth.remark} , </if>" +
                " <if test=\"kpiStaffMonth.status != null\"> status = #{kpiStaffMonth.status} , </if>" +
                " modified = now() " +
            " WHERE staff_id = #{kpiStaffMonth.staffId} and month = #{kpiStaffMonth.month} " +
            "</script>")
    int update(@Param("kpiStaffMonth") KpiStaffMonthEntity kpiStaffMonth);

    @Update("<script> DELETE  FROM kpi_staff_month " +
            " WHERE staff_id = #{id} and month = '123456' " +
            "</script>")
    int delete(@Param("id") String id);


}


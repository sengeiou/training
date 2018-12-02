package com.training.admin.service;

import com.alibaba.fastjson.JSON;
import com.training.dao.*;
import com.training.entity.*;
import com.training.service.*;
import com.training.util.ut;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 报表计算核心业务操作类
 * Created by huai23 on 2018-05-26 13:55:30.
 */ 
@Service
public class ReportService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberCardDao memberCardDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private TrainingDao trainingDao;

    @Autowired
    private KpiStaffMonthService kpiStaffMonthService;

    @Autowired
    private KpiStaffMonthDao kpiStaffMonthDao;

    @Autowired
    private StoreOpenDao storeOpenDao;

    @Autowired
    private KpiTemplateService kpiTemplateService;

    @Autowired
    private ManualService manualService;

    @Autowired
    private FinanceOnceReportService financeOnceReportService;

    @Autowired
    private FinanceMonthReportService financeMonthReportService;

    @Autowired
    private FinanceStaffReportService financeStaffReportService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String calculateStoreFinanceOnceReport(String storeId,String today) {
        StoreEntity storeEntity = storeDao.getById(storeId);
        if(storeEntity==null){
            return "";
        }
        String year = today.substring(0,4);
        String month = today.substring(0,7);
        FinanceOnceReportEntity financeOnceReportEntity = new FinanceOnceReportEntity();
        financeOnceReportEntity.setStoreId(storeId);
        financeOnceReportEntity.setStoreName(storeEntity.getName());
        financeOnceReportEntity.setYear(year);
        financeOnceReportEntity.setMonth(month);
        financeOnceReportEntity.setReportDate(today);
        financeOnceReportEntity.setStatus(1);
        // 1. 计算销售额
        calculateSaleMoney(financeOnceReportEntity,month);
        // 2. 计算待耗课金额


        // 3. 耗课收入


        // 4. 死课收入


        // 5. 延期收入


        // 6. 转出金额


        // 7. 转入金额


        // 8. 退课金额


        try {
            jdbcTemplate.update(" update finance_once_report set status = 0 where store_id = ? and month = ?",new Object[]{storeId,ut.currentFullMonth()});
            financeOnceReportService.add(financeOnceReportEntity);
        }catch (Exception e){

        }
        return "";
    }

    private void calculateSaleMoney(FinanceOnceReportEntity financeOnceReportEntity, String month) {
        String startDate = month+"-01";
        String endDate = month+"-31";
        String sql = "select * from contract where card_type in ('PT') and store_id = ? and sign_date >= ? and sign_date <= ? ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{financeOnceReportEntity.getStoreId(),startDate,endDate});
        double money = 0;
        int count = 0;
        for (int i = 0; i < data.size(); i++) {
            Map contract = (Map)data.get(i);
            money = money + Double.parseDouble(contract.get("money").toString());
            count = count + Integer.parseInt(contract.get("total").toString());
        }
        logger.info(" calculateSaleMoney  money  = {} , count = {}  ",money,count);
        financeOnceReportEntity.setSaleMoney(ut.getDoubleString(money));
        financeOnceReportEntity.setSaleLessonCount(""+count);
    }


    public String calculateStoreFinanceMonthReport(String storeId,String today) {
        StoreEntity storeEntity = storeDao.getById(storeId);
        if(storeEntity==null){
            return "";
        }
        String year = today.substring(0,4);
        String month = today.substring(0,7);
        FinanceMonthReportEntity financeMonthReportEntity = new FinanceMonthReportEntity();
        financeMonthReportEntity.setStoreId(storeId);
        financeMonthReportEntity.setStoreName(storeEntity.getName());
        financeMonthReportEntity.setYear(year);
        financeMonthReportEntity.setMonth(month);
        financeMonthReportEntity.setReportDate(today);
        financeMonthReportEntity.setStatus(1);
        // 1. 计算销售额


        // 2. 计算待耗课金额


        // 3. 耗课收入


        // 4. 转出金额


        // 5. 转入金额


        // 6. 退课金额


        try {
            jdbcTemplate.update(" update finance_month_report set status = 0 where store_id = ? and month = ?",new Object[]{storeId,ut.currentFullMonth()});
            financeMonthReportService.add(financeMonthReportEntity);
        }catch (Exception e){

        }
        return "";
    }


    public String calculateStaffFinanceReport(String staffId,String today) {
        StaffEntity staffEntity = staffDao.getById(staffId);
        if(staffEntity==null){
            return "";
        }
        StoreEntity storeEntity = storeDao.getById(staffEntity.getStoreId());
        String year = today.substring(0,4);
        String month = today.substring(0,7);
        FinanceStaffReportEntity financeStaffReportEntity = new FinanceStaffReportEntity();
        financeStaffReportEntity.setStaffId(staffId);
        financeStaffReportEntity.setStaffName(staffEntity.getCustname());
        financeStaffReportEntity.setStoreId(staffEntity.getStoreId());
        financeStaffReportEntity.setStoreName(storeEntity.getName());
        financeStaffReportEntity.setTemplateId(staffEntity.getTemplateId());
        financeStaffReportEntity.setStar(staffEntity.getStar());
        financeStaffReportEntity.setYear(year);
        financeStaffReportEntity.setMonth(month);
        financeStaffReportEntity.setReportDate(today);
        financeStaffReportEntity.setStatus(1);
        // 1. 计算销售额


        // 2. 计算续课额


        // 3. 次卡私教课


        // 4. 月卡1v1私教课


        // 5. 月卡1v2私教课


        // 6. 体验课

        // 7. 特色课

        // 8. 团体课


        try {
            jdbcTemplate.update(" update finance_staff_report set status = 0 where staff_id = ? and month = ?",new Object[]{staffId,ut.currentFullMonth()});
            financeStaffReportService.add(financeStaffReportEntity);
        }catch (Exception e){

        }
        return "";
    }

}


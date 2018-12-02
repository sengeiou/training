package com.training.admin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.response.SmartworkBpmsProcessinstanceListResponse;
import com.training.common.CardTypeEnum;
import com.training.common.PageRequest;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表计算核心业务操作类
 * Created by huai23 on 2018-05-26 13:55:30.
 */ 
@Service
public class ReportStaffService {

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
    private ContractDao contractDao;

    @Autowired
    private SysLogDao sysLogDao;

    @Autowired
    private KpiStaffDetailDao kpiStaffDetailDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;


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
            jdbcTemplate.update(" update finance_staff_report set status = 0 where staff_id = ? and month = ?",new Object[]{staffId,month});
            financeStaffReportService.add(financeStaffReportEntity);
        }catch (Exception e){

        }
        return "";
    }

}


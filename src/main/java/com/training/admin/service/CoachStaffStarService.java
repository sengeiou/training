package com.training.admin.service;

import com.training.dao.*;
import com.training.entity.*;
import com.training.service.*;
import com.training.util.ut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * staff 核心业务操作类
 * Created by huai23 on 2018-05-26 13:55:30.
 */ 
@Service
public class CoachStaffStarService {

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
    private JdbcTemplate jdbcTemplate;

    public String calculateStaffStar(String staffId, String month) {
        StaffEntity staffEntity = staffDao.getById(staffId);
        if(!staffEntity.getJob().equals("教练")){
            return "非教练员工没有星级";
        }
        logger.info(" calculateStaffStar   staffName = {} , month = {} , star = {}   ",staffEntity.getCustname(),month,staffEntity.getStar());
        KpiStaffMonthEntity kpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(staffId,month.replace("-",""));
        int n = 0;
        if(staffEntity.getStar()<2){
            n = calculateStar1(staffEntity,month);
        }else if(staffEntity.getStar()==2){
            n = calculateStar2(staffEntity,month);
        }if(staffEntity.getStar()==3){
            n = calculateStar3(staffEntity,month);
        }if(staffEntity.getStar()==4){
            n = calculateStar4(staffEntity,month);
        }
        logger.info(" calculateStaffStar   staffName = {} , month = {} , star = {} , n =  ",staffEntity.getCustname(),month,staffEntity.getStar(),n);
        return "";
    }

    private int calculateStar1(StaffEntity staffEntity, String month) {
        String month1 = ut.getKpiMonth(month,-1);

        return 0;
    }

    private int calculateStar2(StaffEntity staffEntity, String month) {

        return 0;
    }

    private int calculateStar3(StaffEntity staffEntity, String month) {

        return 0;
    }

    private int calculateStar4(StaffEntity staffEntity, String month) {

        return 0;
    }

}


package com.training.admin.service;

import com.alibaba.fastjson.JSON;
import com.training.common.PageRequest;
import com.training.dao.*;
import com.training.domain.KpiStaffMonth;
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

/**
 * staff 核心业务操作类
 * Created by huai23 on 2018-05-26 13:55:30.
 */ 
@Service
public class CalculateKpiService {

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
    private CardDao cardDao;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private CardService cardService;

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
    private JdbcTemplate jdbcTemplate;


    public void calculateStaffKpi(String staffId,String month) {
        KpiStaffMonthEntity kpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(staffId,month);
        StaffEntity staffEntity = staffDao.getById(staffId);
        String templateId = staffEntity.getTemplateId();
        KpiTemplateEntity kpiTemplateEntity = new KpiTemplateEntity();
        List<KpiTemplateQuotaEntity> kpiTemplateQuotaEntityList = new ArrayList<>();
        if(StringUtils.isNotEmpty(templateId)){
            kpiTemplateEntity = kpiTemplateService.getById(templateId);
            if(kpiTemplateEntity!=null){
                kpiTemplateQuotaEntityList = kpiTemplateEntity.getQuotaEntityList();
            }
        }

        int xks = getXks(staffId,month);
        int jks = getJks(staffId,month);
        int lessonCount = queryLessonCount(staffId,month);
        int validMemberCount = queryValidMemberCount(staffId,month);
        int yyts = queryOpenDays(staffEntity.getStoreId(),month);

        kpiStaffMonthEntity.setXks(""+xks);
        kpiStaffMonthEntity.setJks(""+jks);
        kpiStaffMonthEntity.setSjks(""+lessonCount);
        kpiStaffMonthEntity.setYxhys(""+validMemberCount);
        kpiStaffMonthEntity.setYyts(""+yyts);


        kpiStaffMonthEntity.setKpiScore("1");
        kpiStaffMonthEntity.setKpiData(JSON.toJSONString(kpiTemplateEntity));
        int n = kpiStaffMonthDao.update(kpiStaffMonthEntity);

    }

    private int getXks(String staffId, String month) {
        return 0;
    }

    private int getJks(String staffId, String month) {
        return 0;
    }

    private int queryLessonCount(String staffId, String month) {
        return 0;
    }

    private int queryValidMemberCount(String staffId, String month) {
        return 0;
    }

    private int queryOpenDays(String storeId,String month) {
        String y = month.substring(0,4);
        String m = month.substring(5,6);
        StoreOpenEntity storeOpenEntity = storeOpenDao.getById(storeId, y);
        if(storeOpenEntity==null){
            int days = ut.daysOfMonth(y+"-"+m+"-01");
            return days;
        }
        if(m.equals("01")) return storeOpenEntity.getM01();
        if(m.equals("02")) return storeOpenEntity.getM02();
        if(m.equals("03")) return storeOpenEntity.getM03();
        if(m.equals("04")) return storeOpenEntity.getM04();
        if(m.equals("05")) return storeOpenEntity.getM05();
        if(m.equals("06")) return storeOpenEntity.getM06();
        if(m.equals("07")) return storeOpenEntity.getM07();
        if(m.equals("08")) return storeOpenEntity.getM08();
        if(m.equals("09")) return storeOpenEntity.getM09();
        if(m.equals("10")) return storeOpenEntity.getM10();
        if(m.equals("11")) return storeOpenEntity.getM11();
        if(m.equals("12")) return storeOpenEntity.getM12();
        return 0;
    }

}


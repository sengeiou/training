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
        financeStaffReportEntity.setJob(staffEntity.getJob());
        financeStaffReportEntity.setReportDate(today);
        financeStaffReportEntity.setStatus(1);

        // 1. 计算销售额和续课额
        calculateSaleMoney(financeStaffReportEntity,month);
        // 2. 次卡私教课
        calculateOnceLessonCount(financeStaffReportEntity,month);
        // 3. 月卡1v1私教课
        calculateMonthCardSingleLessonCount(financeStaffReportEntity,month);
        // 4. 月卡1v2私教课
        calculateMonthCardMultiLessonCount(financeStaffReportEntity,month);
        // 5. 体验课
        calculateTyCardMultiLessonCount(financeStaffReportEntity,month);
        // 6. 特色课
        calculateSpecialLessonCount(financeStaffReportEntity,month);
        // 7. 团体课
        calculateTeamLessonCount(financeStaffReportEntity,month);
        try {
            jdbcTemplate.update(" update finance_staff_report set status = 0 where staff_id = ? and month = ?",new Object[]{staffId,month});
            financeStaffReportService.add(financeStaffReportEntity);
        }catch (Exception e){

        }
        return "";
    }

    /**
     * 7. 团体课
     * @param financeStaffReportEntity
     * @param month
     */
    private void calculateTeamLessonCount(FinanceStaffReportEntity financeStaffReportEntity, String month) {
        String startDate = month+"-01";
        String endDate = month+"-31";
        String sql = "select lesson_id, count(1) total  from training " +
                " where card_type in ('TT','TM') and staff_id = ? and lesson_date >= ? and lesson_date <= ? and show_tag = 1 " +
                " group by lesson_id  ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{financeStaffReportEntity.getStaffId(),startDate,endDate});
        logger.info(" calculateTeamLessonCount  count  = {} ",data.size());
        financeStaffReportEntity.setTeamLessonCount(""+data.size());
    }

    /**
     * 6. 计算特色课
     * @param financeStaffReportEntity
     * @param month
     */
    private void calculateSpecialLessonCount(FinanceStaffReportEntity financeStaffReportEntity, String month) {
        String startDate = month+"-01";
        String endDate = month+"-31";
        String sql = "select * from training where card_type in ('ST1','ST2','ST3','ST4') and staff_id = ? and lesson_date >= ? and lesson_date <= ? and show_tag = 1 ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{financeStaffReportEntity.getStaffId(),startDate,endDate});
        logger.info(" calculateSpecialLessonCount  count  = {} ",data.size());
        financeStaffReportEntity.setSpecialLessonCount(""+data.size());
    }

    /**
     * 5. 计算体验课
     * @param financeStaffReportEntity
     * @param month
     */
    private void calculateTyCardMultiLessonCount(FinanceStaffReportEntity financeStaffReportEntity, String month) {
        String startDate = month+"-01";
        String endDate = month+"-31";
        String sql = "select * from training where card_type in ('TY') and staff_id = ? and lesson_date >= ? and lesson_date <= ? and show_tag = 1 ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{financeStaffReportEntity.getStaffId(),startDate,endDate});
        logger.info(" calculateTyCardMultiLessonCount  count  = {} ",data.size());
        financeStaffReportEntity.setTyCardMultiLessonCount(""+data.size());
    }

    /**
     * 4. 月卡1v2私教课
     * @param financeStaffReportEntity
     * @param month
     */
    private void calculateMonthCardMultiLessonCount(FinanceStaffReportEntity financeStaffReportEntity, String month) {
        String startDate = month+"-01";
        String endDate = month+"-31";
        String sql = " select lesson_id, count(1) total from training " +
                " where card_type in ('PM') and staff_id = ? and lesson_date >= ? and lesson_date <= ? and show_tag = 1 " +
                " group by lesson_id having total > 1 ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{financeStaffReportEntity.getStaffId(),startDate,endDate});
        logger.info(" calculateMonthCardMultiLessonCount  count  = {} ",data.size());
        financeStaffReportEntity.setMonthCardMultiLessonCount(""+data.size());
    }

    /**
     * 3. 月卡1v1私教课
     * @param financeStaffReportEntity
     * @param month
     */
    private void calculateMonthCardSingleLessonCount(FinanceStaffReportEntity financeStaffReportEntity, String month) {
        String startDate = month+"-01";
        String endDate = month+"-31";
        String sql = " select lesson_id, count(1) total from training " +
                " where card_type in ('PM') and staff_id = ? and lesson_date >= ? and lesson_date <= ? and show_tag = 1 " +
                " group by lesson_id having total = 1 ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{financeStaffReportEntity.getStaffId(),startDate,endDate});
        logger.info(" calculateMonthCardSingleLessonCount  count  = {} ",data.size());
        financeStaffReportEntity.setMonthCardSingleLessonCount(""+data.size());
    }

    /**
     * 2. 计算次卡私教课
     * @param financeStaffReportEntity
     * @param month
     */
    private void calculateOnceLessonCount(FinanceStaffReportEntity financeStaffReportEntity, String month) {
        String startDate = month+"-01";
        String endDate = month+"-31";
        String sql = "select * from training where card_type in ('PT') and staff_id = ? and lesson_date >= ? and lesson_date <= ? and show_tag = 1 ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{financeStaffReportEntity.getStaffId(),startDate,endDate});
        logger.info(" calculateOnceLessonCount  count  = {} ",data.size());
        financeStaffReportEntity.setTimesCardLessonCount(""+data.size());
    }

    /**
     * 1. 计算销售额和续课额
     * @param financeStaffReportEntity
     * @param month
     */
    private void calculateSaleMoney(FinanceStaffReportEntity financeStaffReportEntity, String month) {
        String startDate = month+"-01";
        String endDate = month+"-31";
        String sql = "select * from contract where card_type in ('PT','PM','TT','TM','ST1','ST2','ST3','ST4') and sale_staff_id = ? and sign_date >= ? and sign_date <= ? ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{financeStaffReportEntity.getStaffId(),startDate,endDate});
        logger.info(" calculateSaleMoney data = {}  ",data.size());
        double money_total = 0;
        double money_xk = 0;
        for (int i = 0; i < data.size(); i++) {
            Map contract = (Map)data.get(i);
            String type = contract.get("type").toString();
            double money = Double.parseDouble(contract.get("money").toString());
            if(type.indexOf("续课")>=0){
                money_xk = money_xk + money;
            }else{
                money_total = money_total + money;
            }
        }
        logger.info(" calculateSaleMoney  name = {} , month = {} , money_total  = {} , money_xk = {}  ",financeStaffReportEntity.getStaffName(),month,money_total,money_xk);
        financeStaffReportEntity.setSaleMoney(ut.getDoubleString(money_total));
        financeStaffReportEntity.setXkMoney(ut.getDoubleString(money_xk));
    }



}


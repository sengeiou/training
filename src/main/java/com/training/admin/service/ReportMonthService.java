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
public class ReportMonthService {

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

    /**
     * 5. 计算退课金额
     * @param financeMonthReportEntity
     * @param month
     */
    private void calculateBackLessonMoney(FinanceMonthReportEntity financeMonthReportEntity, String month) {
        String startDate = month+"-01";
        String endDate = month+"-31";
        double money = 0;
        int count = 0;
        ContractQuery query = new ContractQuery();
        query.setCardType("TK");
        query.setStartDate(startDate);
        query.setEndDate(endDate);
        PageRequest page = new PageRequest();
        page.setPageSize(1000);
        List<ContractEntity> contractEntityList = contractDao.find(query,page);
//        logger.info(" *************  calculateBackLessonMoney   contractEntityList.size() = {} ", contractEntityList.size());
        for (ContractEntity contractEntity : contractEntityList){
            MemberEntity memberEntity = memberDao.getByPhone(contractEntity.getPhone());
            if(memberEntity==null){
                continue;
            }
            if(!memberEntity.getStoreId().equals(financeMonthReportEntity.getStoreId())){
                continue;
            }

            SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo processInstanceTopVo = JSON.parseObject(contractEntity.getForm(),SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo.class);
            Map<String,String> contractMap = new HashMap();
            List<SmartworkBpmsProcessinstanceListResponse.FormComponentValueVo> forms = processInstanceTopVo.getFormComponentValues();
            for (SmartworkBpmsProcessinstanceListResponse.FormComponentValueVo form : forms){
                System.out.println(form.getName()+" : "+form.getValue());
                contractMap.put(form.getName(),form.getValue()==null?"":form.getValue());
            }
            String cardNo = contractMap.get("课卡号");
            if(StringUtils.isEmpty(cardNo)){
                continue;
            }
            MemberCardEntity memberCardEntity = memberCardDao.getById(cardNo);
            if(memberCardEntity==null){
                continue;
            }

            if(memberCardEntity.getType().equals(CardTypeEnum.PM.getKey()) || memberCardEntity.getType().equals(CardTypeEnum.TM.getKey()) ) {
                money = money + Double.parseDouble(contractEntity.getMoney());
            }

        }
//        logger.info(" calculateBackLessonMoney  money  = {}",money);
        financeMonthReportEntity.setBackDaysMoney(ut.getDoubleString(money));
    }

    /**
     *  4. 计算转入金额和转出金额
     * @param financeMonthReportEntity
     * @param month
     */
    private void calculateInAndOutLessonMoney(FinanceMonthReportEntity financeMonthReportEntity, String month) {
        String startDate = month+"-01";
        String endDate = month+"-31";
        double in_money = 0;
        int in_count = 0;
        double out_money = 0;
        int out_count = 0;
        ContractQuery query = new ContractQuery();
        query.setCardType("ZK");
        query.setStartDate(startDate);
        query.setEndDate(endDate);
        PageRequest page = new PageRequest();
        page.setPageSize(1000);
        List<ContractEntity> contractEntityList = contractDao.find(query,page);
//        logger.info(" *************  calculateOutLessonMoney   contractEntityList.size() = {} ", contractEntityList.size());
        for (ContractEntity contractEntity : contractEntityList){
            SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo processInstanceTopVo = JSON.parseObject(contractEntity.getForm(),SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo.class);
            Map<String,String> contractMap = new HashMap();
            List<SmartworkBpmsProcessinstanceListResponse.FormComponentValueVo> forms = processInstanceTopVo.getFormComponentValues();
            for (SmartworkBpmsProcessinstanceListResponse.FormComponentValueVo form : forms){
                System.out.println(form.getName()+" : "+form.getValue());
                contractMap.put(form.getName(),form.getValue()==null?"":form.getValue());
            }
            String cardNo = contractMap.get("课卡号");
            if(StringUtils.isEmpty(cardNo)){
                continue;
            }
            MemberCardEntity memberCardEntity = memberCardDao.getById(cardNo);
            if(memberCardEntity==null){
                continue;
            }
            if(memberCardEntity.getType().equals(CardTypeEnum.PM.getKey()) || memberCardEntity.getType().equals(CardTypeEnum.TM.getKey()) ){

                String phone = contractEntity.getPhone();
                String newPhone = contractMap.get("转入会员电话");
                MemberEntity memberFrom = memberDao.getByPhone(phone);
                MemberEntity memberTo = memberDao.getByPhone(newPhone);
                if(memberFrom==null||memberTo==null){
                    continue;
                }
                if(memberFrom.getStoreId().equals(memberTo.getStoreId())){
                    continue;
                }
                if(memberFrom.getStoreId().equals(financeMonthReportEntity.getStoreId())){
                    out_money = out_money + Double.parseDouble(contractEntity.getMoney());
                }
                if(memberTo.getStoreId().equals(financeMonthReportEntity.getStoreId())){
                    in_money = in_money + Double.parseDouble(contractEntity.getMoney());
                }
            }
        }
//        logger.info(" calculateDelayLessonMoney  out_money  = {} out_count = {} ",out_money);
//        logger.info(" calculateDelayLessonMoney  in_money  = {} in_count = {} ",in_money);
        financeMonthReportEntity.setOutDaysMoney(ut.getDoubleString(out_money));
        financeMonthReportEntity.setInDaysMoney(ut.getDoubleString(in_money));
    }

    /**
     * 3. 计算耗课收入
     * @param financeMonthReportEntity
     * @param month
     */
    private void calculateUsedLessonMoney(FinanceMonthReportEntity financeMonthReportEntity, String month) {
        String startDate = month+"-01";
        String endDate = month+"-31";
        String today = ut.currentDate();
        if(ut.passDayByDate(today,endDate)<0){
            today = endDate;
        }
        // 1 先查询所有会员
        String sql = "select member_id from member where store_id = ? and created <= ? ";
        String card_sql = "select * from member_card where member_id = ? and type in ('PM','TM') and end_date >= ? ";
        List members = jdbcTemplate.queryForList(sql,new Object[]{financeMonthReportEntity.getStoreId(),endDate+" 23:59:59"});
        // 2 再查询所有会员卡
        double money = 0;
        int count = 0;
        for (int i = 0; i < members.size(); i++) {
            Map member = (Map)members.get(i);
            String memberId = member.get("member_id").toString();
            List cards = jdbcTemplate.queryForList(card_sql,new Object[]{memberId,endDate});
            for (int j = 0; j < cards.size(); j++) {
                Map card = (Map)cards.get(j);
                String start = card.get("start_date").toString();
                String end = card.get("end_date").toString();
                int days = ut.passDayByDate(start,end)+1;

                int pauseDays = getPauseDaysByMonth(memberId,startDate,endDate);


                days = days - pauseDays;

                double price = Double.parseDouble(card.get("money").toString())/days;
                int this_count = ut.passDayByDate(startDate,today)+1;
                money = money + this_count*price;
                count = count + this_count;
            }
        }
//        logger.info(" calculateUsedLessonMoney  money  = {} , count = {}  ",money,count);
        financeMonthReportEntity.setUsedDaysMoney(ut.getDoubleString(money));
        financeMonthReportEntity.setUsedDaysCount(""+count);
    }

    public int getPauseDaysByMonth(String memberId, String startDate, String endDate) {
        String start = startDate;
        String end = endDate;
        int pauseDays = 0;
        String sql = " select * from member_pause where member_id = ? and status = 0 and restore_date > ?";
        List data = jdbcTemplate.queryForList(sql,new Object[]{ memberId, start});
        for (int i = 0; i < data.size(); i++) {
            Map pause = (Map)data.get(i);
            String pause_date = pause.get("pause_date").toString();
            if(pause_date.indexOf(":")>0){
                pause_date = pause_date.substring(0,10);
            }
            String restore_date = pause.get("restore_date").toString();
            if(ut.passDayByDate(startDate,pause_date)>0){
                startDate = pause_date;
            }
            if(ut.passDayByDate(endDate,restore_date)<0){
                endDate = restore_date;
            }
            int days = ut.passDayByDate(startDate,endDate);
            pauseDays = pauseDays + days;
        }

        startDate = start;
        endDate = end;

        sql = " select * from member_pause where member_id = ? and status = 1 and pause_date < ?";
        data = jdbcTemplate.queryForList(sql,new Object[]{ memberId, end});
        for (int i = 0; i < data.size(); i++) {
            Map pause = (Map)data.get(i);
            String pause_date = pause.get("pause_date").toString();
            if(pause_date.indexOf(":")>0){
                pause_date = pause_date.substring(0,10);
            }
            String restore_date = pause.get("restore_date").toString();
            if(ut.passDayByDate(startDate,pause_date)>0){
                startDate = pause_date;
            }
            if(ut.passDayByDate(endDate,restore_date)<0){
                endDate = restore_date;
            }
            int days = ut.passDayByDate(startDate,endDate);
            pauseDays = pauseDays + days;
        }
        return pauseDays;
    }

    /**
     * 2. 计算待耗课金额
     * @param financeMonthReportEntity
     * @param month
     */
    private void calculateWaitingLessonMoney(FinanceMonthReportEntity financeMonthReportEntity, String month) {
        String startDate = month+"-01";
        String endDate = month+"-31";
        String today = ut.currentDate();
        if(ut.passDayByDate(today,endDate)<0){
            today = endDate;
        }
        // 1 先查询所有会员
        String sql = "select member_id from member where store_id = ? and created <= ? ";
        String card_sql = "select * from member_card where member_id = ? and type in ('PM','TM') and end_date >= ? ";
        List members = jdbcTemplate.queryForList(sql,new Object[]{financeMonthReportEntity.getStoreId(),endDate+" 23:59:59"});
        // 2 再查询所有会员卡
        double money = 0;
        int count = 0;
        for (int i = 0; i < members.size(); i++) {
            Map member = (Map)members.get(i);
            String memberId = member.get("member_id").toString();
            List cards = jdbcTemplate.queryForList(card_sql,new Object[]{memberId,endDate});
            for (int j = 0; j < cards.size(); j++) {
                Map card = (Map)cards.get(j);
                String start = card.get("start_date").toString();
                String end = card.get("end_date").toString();
                int days = ut.passDayByDate(start,end)+1;
                double price = Double.parseDouble(card.get("money").toString())/days;
                int this_count = ut.passDayByDate(today,end)+1;
                money = money + this_count*price;
                count = count + this_count;
            }
        }
//        logger.info(" calculateWaitingLessonMoney  money  = {} , count = {}  ",money,count);
        financeMonthReportEntity.setWaitingDaysMoney(ut.getDoubleString(money));
        financeMonthReportEntity.setWaitingDaysCount(""+count);
    }

    /**
     * 1. 计算销售额
     * @param financeMonthReportEntity
     * @param month
     */
    private void calculateSaleMoney(FinanceMonthReportEntity financeMonthReportEntity, String month) {
        String startDate = month+"-01";
        String endDate = month+"-31";
        String sql = "select * from contract where card_type in ('PM','TM') and store_id = ? and sign_date >= ? and sign_date <= ? ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{financeMonthReportEntity.getStoreId(),startDate,endDate});
        double money = 0;
        int count = 0;
        for (int i = 0; i < data.size(); i++) {
            Map contract = (Map)data.get(i);
            money = money + Double.parseDouble(contract.get("money").toString());
            String startDay = contract.get("start_date").toString();
            String endDay = contract.get("end_date").toString();
            count = count + ut.passDayByDate(startDay,endDay)+1;
        }
//        logger.info(" calculateSaleMoney  money  = {} , count = {}  ",money,count);
        financeMonthReportEntity.setSaleMoney(ut.getDoubleString(money));
        financeMonthReportEntity.setSaleDaysCount(""+count);
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
        calculateSaleMoney(financeMonthReportEntity,month);
        // 2. 计算待耗课金额
        calculateWaitingLessonMoney(financeMonthReportEntity,month);
        // 3. 耗课收入
        calculateUsedLessonMoney(financeMonthReportEntity,month);
        // 4. 转入转出金额
        calculateInAndOutLessonMoney(financeMonthReportEntity,month);
        // 5. 退课金额
        calculateBackLessonMoney(financeMonthReportEntity,month);
        try {
            jdbcTemplate.update(" update finance_month_report set status = 0 where store_id = ? and month = ?",new Object[]{storeId,month});
            financeMonthReportService.add(financeMonthReportEntity);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }



}


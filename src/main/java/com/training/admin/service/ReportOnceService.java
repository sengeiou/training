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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表计算核心业务操作类
 * Created by huai23 on 2018-05-26 13:55:30.
 */ 
@Service
public class ReportOnceService {

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
        calculateWaitingLessonMoney(financeOnceReportEntity,month);
        // 3. 耗课收入
        calculateUsedLessonMoney(financeOnceReportEntity,month);
        // 4. 死课收入
        calculateDeadLessonMoney(financeOnceReportEntity,month);
        // 5. 延期收入
        calculateDelayLessonMoney(financeOnceReportEntity,month);
        // 6. 转出金额
        calculateInAndOutLessonMoney(financeOnceReportEntity,month);
        // 7. 退课金额
        calculateBackLessonMoney(financeOnceReportEntity,month);
        try {
            jdbcTemplate.update(" update finance_once_report set status = 0 where store_id = ? and month = ?",new Object[]{storeId,month});
            financeOnceReportService.add(financeOnceReportEntity);
        }catch (Exception e){

        }
        return "";
    }

    /**
     * 7. 计算退课金额
     * @param financeOnceReportEntity
     * @param month
     */
    private void calculateBackLessonMoney(FinanceOnceReportEntity financeOnceReportEntity, String month) {
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
        logger.info(" *************  calculateBackLessonMoney   contractEntityList.size() = {} ", contractEntityList.size());
        for (ContractEntity contractEntity : contractEntityList){
            MemberEntity memberEntity = memberDao.getByPhone(contractEntity.getPhone());
            if(memberEntity==null){
                continue;
            }
            if(!memberEntity.getStoreId().equals(financeOnceReportEntity.getStoreId())){
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

            if(memberCardEntity.getType().equals(CardTypeEnum.PT.getKey()) || memberCardEntity.getType().equals(CardTypeEnum.TT.getKey())
                    || memberCardEntity.getType().equals(CardTypeEnum.ST1.getKey()) || memberCardEntity.getType().equals(CardTypeEnum.ST2.getKey())
                    || memberCardEntity.getType().equals(CardTypeEnum.ST3.getKey()) ) {
                money = money + Double.parseDouble(contractEntity.getMoney());
                count = count + Integer.parseInt(contractEntity.getGender());
            }

        }
        logger.info(" calculateBackLessonMoney  money  = {} , count = {}  ",money,count);
        financeOnceReportEntity.setBackLessonMoney(ut.getDoubleString(money));
        financeOnceReportEntity.setBackLessonCount(""+count);
    }

    /**
     * 6. 计算转入金额和转出金额
     * @param financeOnceReportEntity
     * @param month
     */
    private void calculateInAndOutLessonMoney(FinanceOnceReportEntity financeOnceReportEntity, String month) {
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
        logger.info(" *************  calculateOutLessonMoney   contractEntityList.size() = {} ", contractEntityList.size());
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
            if(memberCardEntity.getType().equals(CardTypeEnum.PT.getKey()) || memberCardEntity.getType().equals(CardTypeEnum.TT.getKey())
                    || memberCardEntity.getType().equals(CardTypeEnum.ST1.getKey()) || memberCardEntity.getType().equals(CardTypeEnum.ST2.getKey())
                    || memberCardEntity.getType().equals(CardTypeEnum.ST3.getKey()) ){

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
                if(memberFrom.getStoreId().equals(financeOnceReportEntity.getStoreId())){
                    out_money = out_money + Double.parseDouble(contractEntity.getMoney());
                    out_count = out_count + Integer.parseInt(contractEntity.getTotal());
                }
                if(memberTo.getStoreId().equals(financeOnceReportEntity.getStoreId())){
                    in_money = in_money + Double.parseDouble(contractEntity.getMoney());
                    in_count = in_count + Integer.parseInt(contractEntity.getTotal());
                }

            }
        }
        logger.info(" calculateDelayLessonMoney  out_money  = {} out_count = {} ",out_money,out_count);
        logger.info(" calculateDelayLessonMoney  in_money  = {} in_count = {} ",in_money,in_count);
        financeOnceReportEntity.setOutLessonMoney(ut.getDoubleString(out_money));
        financeOnceReportEntity.setOutLessonCount(""+out_count);
        financeOnceReportEntity.setInLessonMoney(ut.getDoubleString(in_money));
        financeOnceReportEntity.setInLessonCount(""+in_count);
    }

    /**
     * 5. 计算延期收入
     * @param financeOnceReportEntity
     * @param month
     */
    private void calculateDelayLessonMoney(FinanceOnceReportEntity financeOnceReportEntity, String month) {
        String startDate = month+"-01";
        String endDate = month+"-31";
        double money = 0;
        SysLogQuery query = new SysLogQuery();
        PageRequest page = new PageRequest();
        query.setType("YQ");
        query.setId2("pay");
        query.setRemark("success");
        query.setStartDate(startDate);
        query.setEndDate(endDate);
        page.setPageSize(1000);
        List<SysLogEntity> sysLogList = sysLogDao.findDelayLog(query,page);
        for (SysLogEntity sysLogEntity:sysLogList) {
            String cardNo = sysLogEntity.getId1();
            MemberCardEntity memberCardEntity = memberCardDao.getById(cardNo);
            if(memberCardEntity==null){
                continue;
            }
            if(memberCardEntity.getType().equals(CardTypeEnum.PT.getKey()) || memberCardEntity.getType().equals(CardTypeEnum.TT.getKey())
                || memberCardEntity.getType().equals(CardTypeEnum.ST1.getKey()) || memberCardEntity.getType().equals(CardTypeEnum.ST2.getKey())
                || memberCardEntity.getType().equals(CardTypeEnum.ST3.getKey()) ){

                MemberEntity memberEntity = memberDao.getById(memberCardEntity.getMemberId());
                if(memberEntity.getStoreId().equals(financeOnceReportEntity.getStoreId())){
                    JSONObject data = JSON.parseObject(sysLogEntity.getLogText());
                    String total_fee = data.getString("total_fee");
                    money = money + Double.parseDouble(total_fee)/100;
                }
            }
        }
        logger.info(" calculateDelayLessonMoney  money  = {} ",money);
        financeOnceReportEntity.setDelayMoney(ut.getDoubleString(money));
    }

    /**
     * 4. 计算死课收入
     * @param financeOnceReportEntity
     * @param month
     */
    private void calculateDeadLessonMoney(FinanceOnceReportEntity financeOnceReportEntity, String month) {
        String startDate = month+"-01";
        String endDate = month+"-31";
        double money = 0;
        int count = 0;

        KpiStaffDetailQuery query = new KpiStaffDetailQuery();
        query.setStoreId(financeOnceReportEntity.getStoreId());
        query.setType("SK");
        query.setMonth(month);
//        query.setStartDate(startDate);
//        query.setEndDate(endDate);
        PageRequest page = new PageRequest();
        page.setPageSize(10000);
        List<KpiStaffDetailEntity> kpiStaffDetailList = kpiStaffDetailDao.find(query,page);
        for (KpiStaffDetailEntity kpiStaffDetailEntity : kpiStaffDetailList){
            String cardNo = kpiStaffDetailEntity.getCardNo();
            MemberCardEntity memberCardEntity = memberCardDao.getById(cardNo);
            if(memberCardEntity==null){
                continue;
            }
            if(memberCardEntity.getType().equals(CardTypeEnum.PT.getKey()) || memberCardEntity.getType().equals(CardTypeEnum.TT.getKey())
                    || memberCardEntity.getType().equals(CardTypeEnum.ST1.getKey()) || memberCardEntity.getType().equals(CardTypeEnum.ST2.getKey())
                    || memberCardEntity.getType().equals(CardTypeEnum.ST3.getKey()) ){

                if(memberCardEntity.getTotal()>0){
                    money = money + Double.parseDouble(memberCardEntity.getMoney())*memberCardEntity.getCount()/memberCardEntity.getTotal();
                }
                count = count + memberCardEntity.getCount();

            }
        }
        logger.info(" calculateDeadLessonMoney  money  = {} , count = {}  ",money,count);
        financeOnceReportEntity.setDeadLessonMoney(ut.getDoubleString(money));
        financeOnceReportEntity.setDeadLessonCount(""+count);
    }

    /**
     * 3. 计算耗课收入
     * @param financeOnceReportEntity
     * @param month
     */
    private void calculateUsedLessonMoney(FinanceOnceReportEntity financeOnceReportEntity, String month) {
        String startDate = month+"-01";
        String endDate = month+"-31";
        String sql = "select * from training where store_id = ? and lesson_date >= ? and lesson_date <= ? and card_type in ('PT','TT','ST1','ST2','ST3','ST4') and status >= 0 ";
        List trainings = jdbcTemplate.queryForList(sql,new Object[]{financeOnceReportEntity.getStoreId(),startDate,endDate});
        double money = 0;
        int count = 0;
        for (int i = 0; i < trainings.size(); i++) {
            Map training = (Map)trainings.get(i);
            String cardNo = training.get("card_no").toString();
            MemberCardEntity memberCardEntity = memberCardDao.getById(cardNo);
            if(memberCardEntity==null){
                continue;
            }

            if(memberCardEntity.getType().equals(CardTypeEnum.PT.getKey()) || memberCardEntity.getType().equals(CardTypeEnum.TT.getKey())
                    || memberCardEntity.getType().equals(CardTypeEnum.ST1.getKey()) || memberCardEntity.getType().equals(CardTypeEnum.ST2.getKey())
                    || memberCardEntity.getType().equals(CardTypeEnum.ST3.getKey()) ){
                if(memberCardEntity.getTotal()>0){
                    money = money + Double.parseDouble(memberCardEntity.getMoney())/memberCardEntity.getTotal();
                }
                count = count + 1;

            }
        }
        logger.info(" calculateUsedLessonMoney  money  = {} , count = {}  ",money,count);
        financeOnceReportEntity.setUsedLessonMoney(ut.getDoubleString(money));
        financeOnceReportEntity.setUsedLessonCount(""+count);
    }

    /**
     * 2. 计算待耗课金额
     * @param financeOnceReportEntity
     * @param month
     */
    private void calculateWaitingLessonMoney(FinanceOnceReportEntity financeOnceReportEntity, String month) {
        String startDate = month+"-01";
        String endDate = month+"-31";
        // 1 先查询所有会员
        String sql = "select member_id from member where store_id = ? and created <= ? ";
        String card_sql = "select * from member_card where member_id = ? and type in ('PT','TT','ST1','ST2','ST3','ST4') and count > 0 and end_date >= ? ";
        List members = jdbcTemplate.queryForList(sql,new Object[]{financeOnceReportEntity.getStoreId(),endDate+" 23:59:59"});
        // 2 再查询所有会员卡
        double money = 0;
        int count = 0;
        for (int i = 0; i < members.size(); i++) {
            Map member = (Map)members.get(i);
            String memberId = member.get("member_id").toString();
            List cards = jdbcTemplate.queryForList(card_sql,new Object[]{memberId,endDate});
            for (int j = 0; j < cards.size(); j++) {
                Map card = (Map)cards.get(j);
                money = money + Double.parseDouble(card.get("money").toString());
                count = count + Integer.parseInt(card.get("count").toString());
            }
        }
        logger.info(" calculateWaitingLessonMoney  money  = {} , count = {}  ",money,count);
        financeOnceReportEntity.setWaitingLessonMoney(ut.getDoubleString(money));
        financeOnceReportEntity.setWaitingLessonCount(""+count);
    }

    /**
     * 1. 计算销售额
     * @param financeOnceReportEntity
     * @param month
     */
    private void calculateSaleMoney(FinanceOnceReportEntity financeOnceReportEntity, String month) {
        String startDate = month+"-01";
        String endDate = month+"-31";
        String sql = "select * from contract where card_type in ('PT','TT','ST1','ST2','ST3','ST4') and store_id = ? and sign_date >= ? and sign_date <= ? ";
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

}


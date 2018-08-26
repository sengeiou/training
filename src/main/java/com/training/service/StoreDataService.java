package com.training.service;

import com.training.admin.service.CalculateKpiService;
import com.training.admin.service.ManualService;
import com.training.admin.service.MemberTrainingTaskService;
import com.training.common.Page;
import com.training.common.PageRequest;
import com.training.dao.*;
import com.training.domain.KpiStaffMonth;
import com.training.domain.Staff;
import com.training.domain.StoreData;
import com.training.domain.User;
import com.training.entity.*;
import com.training.util.RequestContextHelper;
import com.training.util.ResponseUtil;
import com.training.util.ut;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * store 核心业务操作类
 * Created by huai23 on 2018-05-26 13:43:38.
 */ 
@Service
public class StoreDataService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private StoreService storeService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CardService cardService;

    @Autowired
    private MemberCardService memberCardService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractManualService contractManualService;

    @Autowired
    private ContractManualDao contractManualDao;

    @Autowired
    private ManualService manualService;

    @Autowired
    private CalculateKpiService calculateKpiService;

    @Autowired
    MemberTrainingTaskService memberTrainingTaskService;

    @Autowired
    private KpiStaffMonthService kpiStaffMonthService;

    @Autowired
    private KpiStaffMonthDao kpiStaffMonthDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<StoreData> querySaleMoney(StoreDataQuery query) {
        logger.info(" StoreDataService   querySaleMoney  query = {} ",query);

        String month = query.getMonth();
        if(month.indexOf("-")>0){
            month = month.replace("-","");
        }

        String y = month.substring(0,4);
        String m = month.substring(4,6);
        String startDate = y+"-"+m+"-01";
        String endDate = y+"-"+m+"-31";

        logger.info(" StoreDataService   querySaleMoney  startDate = {} ",startDate);
        logger.info(" StoreDataService   querySaleMoney  endDate = {} ",endDate);

        Set<String> staffNameSet = new HashSet<>();
        List<Map<String,Object>> staffs =  jdbcTemplate.queryForList(" SELECT staff_id,custname from staff where store_id = ? ",new Object[]{query.getStoreId()});
        for (int i = 0; i < staffs.size(); i++){
            Map staff = staffs.get(i);
            String custname = staff.get("custname").toString();
            staffNameSet.add(custname);
        }

        logger.info(" StoreDataService   querySaleMoney  staffs = {} ",staffs.size());

        int sjkxq_count = 0;
        int sjkxq_lesson=0;
        double sjkxq_money = 0;

        int sjkxk_count = 0;
        int sjkxk_lesson=0;
        double sjkxk_money = 0;

        int sjkzjs_count = 0;
        int sjkzjs_lesson=0;
        double sjkzjs_money = 0;

        int ttk_count = 0;
        int ttk_lesson=0;
        double ttk_money = 0;

        int tsk_count = 0;
        int tsk_lesson=0;
        double tsk_money = 0;

        String sql = " SELECT * from contract where sign_date >= ? and sign_date <= ? ";
        List<Map<String,Object>> contracts =  jdbcTemplate.queryForList(sql,new Object[]{startDate,endDate});
        logger.info(" StoreDataService   querySaleMoney  contracts = {} ",contracts.size());

        int count = 0;

        for (int i = 0; i < contracts.size(); i++){
            Map contract = contracts.get(i);
            String coach = contract.get("coach").toString();
            if(coach.indexOf("(")>0){
                int index = coach.indexOf("(");
                coach = coach.substring(0,index);
            }
            if(coach.indexOf("（")>0){
                int index = coach.indexOf("（");
                coach = coach.substring(0,index);
            }
            if(!staffNameSet.contains(coach)){
                continue;
            }
            count++;
            String card_type = contract.get("card_type").toString();
            int total = 0;
            double money = 0;
            try{
                total = Integer.parseInt(contract.get("total").toString());
            }catch (Exception e){

            }
            try{
                money = Double.parseDouble(contract.get("money").toString());
            }catch (Exception e){

            }


            if("PT".equals(card_type)||"PM".equals(card_type)){
                String type = contract.get("type").toString();

                if("新会员".equals(type)){
                    sjkxq_count++;
                    sjkxq_lesson = sjkxq_lesson + total;
                    sjkxq_money = sjkxq_money + money;
                }

                if("续课".equals(type)){
                    sjkxk_count++;
                    sjkxk_lesson = sjkxk_lesson + total;
                    sjkxk_money = sjkxk_money + money;
                }

                if("转介绍".equals(type)){
                    sjkzjs_count++;
                    sjkzjs_lesson = sjkzjs_lesson + total;
                    sjkzjs_money = sjkzjs_money + money;
                }

            }else if("TT".equals(card_type)||"TM".equals(card_type)){
                ttk_count++;
                ttk_lesson = ttk_lesson + total;
                ttk_money = ttk_money + money;

            }else if("ST1".equals(card_type)||"ST2".equals(card_type)||"ST3".equals(card_type)){
                tsk_count++;
                tsk_lesson = tsk_lesson + total;
                tsk_money = tsk_money + money;

            }
        }
        logger.info(" StoreDataService   querySaleMoney  count = {} ",count);

        List<StoreData> storeDataList= new ArrayList();
        StoreData sjkxq = new StoreData();
        sjkxq.setLabel("私教课新签");
        sjkxq.setCount(""+sjkxq_count);
        sjkxq.setLesson(""+sjkxq_lesson);
        sjkxq.setMoney(ut.getDoubleString(sjkxq_money));
        storeDataList.add(sjkxq);

        StoreData sjkxk = new StoreData();
        sjkxk.setLabel("私教课续课");
        sjkxk.setCount(""+sjkxk_count);
        sjkxk.setLesson(""+sjkxk_lesson);
        sjkxk.setMoney(ut.getDoubleString(sjkxk_money));
        storeDataList.add(sjkxk);

        StoreData sjkzjs = new StoreData();
        sjkzjs.setLabel("私教课转介绍");
        sjkzjs.setCount(""+sjkzjs_count);
        sjkzjs.setLesson(""+sjkzjs_lesson);
        sjkzjs.setMoney(ut.getDoubleString(sjkzjs_money));
        storeDataList.add(sjkzjs);

        StoreData ttk = new StoreData();
        ttk.setLabel("团体课");
        ttk.setCount(""+ttk_count);
        ttk.setLesson(""+ttk_lesson);
        ttk.setMoney(ut.getDoubleString(ttk_money));
        storeDataList.add(ttk);

        StoreData tsk = new StoreData();
        tsk.setLabel("特色课");
        tsk.setCount(""+tsk_count);
        tsk.setLesson(""+tsk_lesson);
        tsk.setMoney(ut.getDoubleString(tsk_money));
        storeDataList.add(tsk);

        return storeDataList;
    }

    public List<StoreData> queryIncome(StoreDataQuery query) {
        String month = query.getMonth();
        if(month.indexOf("-")>0){
            month = month.replace("-","");
        }

        String y = month.substring(0,4);
        String m = month.substring(4,6);
        String startDate = y+"-"+m+"-01";
        String endDate = y+"-"+m+"-31";

        logger.info(" StoreDataService   queryIncome  startDate = {} ",startDate);
        logger.info(" StoreDataService   queryIncome  endDate = {} ",endDate);

        Set<String> staffIdSet = new HashSet<>();
        Set<String> staffNameSet = new HashSet<>();

        List<Map<String,Object>> staffs =  jdbcTemplate.queryForList(" SELECT staff_id,custname from staff where store_id = ? ",new Object[]{query.getStoreId()});
        logger.info(" StoreDataService   queryIncome  staffs = {} ",staffs.size());

        for (int i = 0; i < staffs.size(); i++){
            Map staff = staffs.get(i);
            String staff_id = staff.get("staff_id").toString();
            staffIdSet.add(staff_id);
            String custname = staff.get("custname").toString();
            staffNameSet.add(custname);
        }
        String sql = " SELECT * from member where created >= ? and created <= ? ";
        List<Map<String,Object>> members =  jdbcTemplate.queryForList(sql,new Object[]{startDate,endDate});
        logger.info(" StoreDataService   queryIncome  members = {} ",members.size());

        int count = 0;
        int count_hk = 0;
        int count_sk = 0;
        double money_hk = 0;
        double money_sk = 0;

        for (int i = 0; i < members.size(); i++) {
            Map member = members.get(i);
            String coach_staff_id = member.get("coach_staff_id").toString();
            if (!staffIdSet.contains(coach_staff_id)) {
                continue;
            }
            count++;
//            String origin = "";
//            if(null!=member.get("origin")){
//                origin = member.get("origin").toString();
//            }
//            if(origin.indexOf("EXCEL")>=0||origin.indexOf("合同")>=0||origin.indexOf("自动生成")>=0){
//                continue;
//            }

            String sql_training = "select * from training where member_id = ? and card_type in ('PT') and  lesson_date >= ? and lesson_date <= ?  ";
            List trainings = jdbcTemplate.queryForList(sql_training,new Object[]{member.get("member_id").toString(),startDate,endDate});

            logger.info(" StoreDataService   queryIncome  trainings = {} ",trainings.size());

            for (int j = 0; j < trainings.size(); j++) {
                Map training = (Map)trainings.get(j);
                String card_no = training.get("card_no").toString();
                List cards = jdbcTemplate.queryForList("select * from member_card where card_no = ? ",new Object[]{card_no});
                if(cards.size()>0){
                    Map card = (Map)cards.get(0);
                    int total = 0;
                    double money = 0;
                    try{
                        total = Integer.parseInt(card.get("total").toString());
                    }catch (Exception e){

                    }
                    try{
                        money = Double.parseDouble(card.get("money").toString());
                    }catch (Exception e){

                    }
                    if(total>0){
                        money_hk = money_hk+money/total;
                    }
                }
                count_hk++;
            }

            String sql_card = "select * from member_card where member_id = ? and type in ('PT') and count > 0 and end_date >= ? and end_date < ?  ";

            if(ut.passDay(ut.currentDate(),endDate)>0){
                endDate = ut.currentDate();
            }

            List cards = jdbcTemplate.queryForList(sql_card,new Object[]{member.get("member_id").toString(),startDate,endDate});

            logger.info(" StoreDataService   queryIncome  cards = {} ",cards.size());
            for (int j = 0; j < cards.size(); j++) {
                Map card = (Map)cards.get(j);
                int total = 0;
                int left_count = 0;
                double money = 0;
                try{
                    total = Integer.parseInt(card.get("total").toString());
                    left_count = Integer.parseInt(card.get("count").toString());
                }catch (Exception e){

                }
                try{
                    money = Double.parseDouble(card.get("money").toString());
                }catch (Exception e){

                }
                if(total>0){
                    money_sk = money_sk+money*left_count/total;
                }

                count_sk = count_sk + left_count;
            }
        }
        logger.info(" StoreDataService   queryIncome  count = {} ",count);

        List<StoreData> storeDataList= new ArrayList();

        StoreData hksr = new StoreData();
        hksr.setLabel("耗课收入");
        hksr.setValue(ut.getDoubleString(money_hk));
        hksr.setLesson(""+count_hk);
        storeDataList.add(hksr);

        StoreData sksr = new StoreData();
        sksr.setLabel("死课收入");
        sksr.setValue(ut.getDoubleString(money_sk));
        sksr.setLesson(""+count_sk);
        storeDataList.add(sksr);

        StoreData yqsr = new StoreData();
        yqsr.setLabel("延期收入");
        yqsr.setValue("0");
        yqsr.setLesson("0");
        storeDataList.add(yqsr);

        return storeDataList;
    }

    public List<StoreData> queryChangeRate(StoreDataQuery query) {
        String month = query.getMonth();
        if(month.indexOf("-")>0){
            month = month.replace("-","");
        }

        String y = month.substring(0,4);
        String m = month.substring(4,6);
        String startDate = y+"-"+m+"-01";
        String endDate = y+"-"+m+"-31";

        logger.info(" StoreDataService   queryChangeRate  startDate = {} ",startDate);
        logger.info(" StoreDataService   queryChangeRate  endDate = {} ",endDate);

        Set<String> staffIdSet = new HashSet<>();
        Set<String> staffNameSet = new HashSet<>();


        List<Map<String,Object>> staffs =  jdbcTemplate.queryForList(" SELECT staff_id,custname from staff where store_id = ? ",new Object[]{query.getStoreId()});
        for (int i = 0; i < staffs.size(); i++){
            Map staff = staffs.get(i);
            String staff_id = staff.get("staff_id").toString();
            staffIdSet.add(staff_id);
            String custname = staff.get("custname").toString();
            staffNameSet.add(custname);
        }
        String sql = " SELECT * from member where created >= ? and created <= ? ";
        List<Map<String,Object>> members =  jdbcTemplate.queryForList(sql,new Object[]{startDate,endDate});
        logger.info(" StoreDataService   queryChangeRate  members = {} ",members.size());

        int count = 0;
        int count_dd = 0;

        for (int i = 0; i < members.size(); i++) {
            Map member = members.get(i);
            String coach_staff_id = member.get("coach_staff_id").toString();
            if (!staffIdSet.contains(coach_staff_id)) {
                continue;
            }
            count++;
            String origin = "";
            if(null!=member.get("origin")){
                origin = member.get("origin").toString();
            }
            if(origin.indexOf("EXCEL")>=0||origin.indexOf("合同")>=0||origin.indexOf("自动生成")>=0){
                continue;
            }

            String sql_card = "select * from member_card where member_id = ? and type = 'TY' and created >= ? and created <= ?  ";
            List cards = jdbcTemplate.queryForList(sql_card,new Object[]{member.get("member_id").toString(),startDate,endDate});
            if(cards.size()>0){

            }
            count_dd++;
        }


        int cjs = 0;
        String sql_cj = " SELECT * from contract where sign_date >= ? and sign_date <= ? ";
        List<Map<String,Object>> contracts =  jdbcTemplate.queryForList(sql_cj,new Object[]{startDate,endDate});
        logger.info(" StoreDataService   queryChangeRate  contracts = {} ",contracts.size());
        for (int i = 0; i < contracts.size(); i++) {
            Map contract = contracts.get(i);
            String coach = contract.get("coach").toString();
            if (coach.indexOf("(") > 0) {
                int index = coach.indexOf("(");
                coach = coach.substring(0, index);
            }
            if (coach.indexOf("（") > 0) {
                int index = coach.indexOf("（");
                coach = coach.substring(0, index);
            }
            if (!staffNameSet.contains(coach)) {
                continue;
            }
            String type = contract.get("type").toString();
            if("新会员".equals(type)){
                cjs++;
            }
        }
        List<StoreData> storeDataList= new ArrayList();

        StoreData xzyxhys = new StoreData();
        xzyxhys.setLabel("新增意向会员数");
        xzyxhys.setValue(""+count);
        storeDataList.add(xzyxhys);

        StoreData ddrs = new StoreData();
        ddrs.setLabel("到店人数");
        ddrs.setValue(""+count_dd);
        storeDataList.add(ddrs);

        int rate_dd = 0;
        if(count>0){
            rate_dd = count_dd*100/count;
        }

        StoreData sksr = new StoreData();
        sksr.setLabel("到店率");
        sksr.setValue(rate_dd+"%");
        storeDataList.add(sksr);

        StoreData cjrs = new StoreData();
        cjrs.setLabel("成交人数");
        cjrs.setValue(""+cjs);
        storeDataList.add(cjrs);

        int rate_cj = 0;
        if(count_dd>0){
            rate_cj = cjs*100/count_dd;
        }

        StoreData cjl = new StoreData();
        cjl.setLabel("成交率");
        cjl.setValue(rate_cj+"%");
        storeDataList.add(cjl);

        return storeDataList;
    }

    public List<StoreData> queryMemberData(StoreDataQuery query) {
        List<StoreData> storeDataList= new ArrayList();
        String month = query.getMonth();
        if(month.indexOf("-")>0){
            month = month.replace("-","");
        }

        String y = month.substring(0,4);
        String m = month.substring(4,6);
        String startDate = y+"-"+m+"-01";
        String endDate = y+"-"+m+"-31";

        logger.info(" StoreDataService   queryMemberData  startDate = {} ",startDate);
        logger.info(" StoreDataService   queryMemberData  endDate = {} ",endDate);

        Set<String> staffIdSet = new HashSet<>();
        Set<String> staffNameSet = new HashSet<>();

        int jks = 0;
        int xks = 0;
        String qdhydp = "0";
        List<Map<String,Object>> staffs =  jdbcTemplate.queryForList(" SELECT staff_id,custname,job from staff where store_id = ? ",new Object[]{query.getStoreId()});
        for (int i = 0; i < staffs.size(); i++){
            Map staff = staffs.get(i);
            String staff_id = staff.get("staff_id").toString();
            staffIdSet.add(staff_id);
            String custname = staff.get("custname").toString();
            staffNameSet.add(custname);

            if(staff.get("job")!=null && "店长".equals(staff.get("job").toString())){
                KpiStaffMonthEntity kpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(staff_id,month);

                logger.info(" StoreDataService   queryMemberData  kpiStaffMonthEntity = {} ",kpiStaffMonthEntity);


                if(StringUtils.isNotEmpty(kpiStaffMonthEntity.getJks())){
                    jks = Integer.parseInt(kpiStaffMonthEntity.getJks());
                }
                if(StringUtils.isNotEmpty(kpiStaffMonthEntity.getXks())){
                    xks = Integer.parseInt(kpiStaffMonthEntity.getXks());
                }
                qdhydp = kpiStaffMonthEntity.getQdhyd();
            }

        }
        String sql = " SELECT * from member where created >= ? and created <= ? ";
        List<Map<String,Object>> members =  jdbcTemplate.queryForList(sql,new Object[]{startDate,endDate});
        logger.info(" StoreDataService   queryMemberData  members = {} ",members.size());

        int count = 0;
        int count_tk = 0;
        int count_yx = 0;


        for (int i = 0; i < members.size(); i++) {
            Map member = members.get(i);
            String coach_staff_id = member.get("coach_staff_id").toString();
            if (!staffIdSet.contains(coach_staff_id)) {
                continue;
            }
            count++;
            if("-1".equals(member.get("status").toString())){
                count_tk++;
            }
            String sql_card = "select * from member_card where member_id = ? and type <> 'TY' and end_date >= ?  ";
            List cards = jdbcTemplate.queryForList(sql_card,new Object[]{member.get("member_id").toString(),ut.currentDate()});
            if(cards.size()>0){
                count_yx++;
                logger.info(" StoreDataService   member  name = {} ",member.get("name"));
            }
        }

        logger.info(" StoreDataService   queryMemberData  count = {} ",count);
        logger.info(" StoreDataService   queryMemberData  count_yx = {} ",count_yx);
        logger.info(" StoreDataService   queryMemberData  count_tk = {} ",count_tk);

        StoreData yxhy = new StoreData();
        yxhy.setLabel("有效会员");
        yxhy.setValue(""+count_yx);
        storeDataList.add(yxhy);

        StoreData tkrs = new StoreData();
        tkrs.setLabel("停课会员");
        tkrs.setValue(""+count_tk);
        storeDataList.add(tkrs);

        StoreData hyd = new StoreData();
        hyd.setLabel("会员活跃度");
        hyd.setValue(""+qdhydp);
        storeDataList.add(hyd);

        StoreData jkrs = new StoreData();
        jkrs.setLabel("结课人数");
        jkrs.setValue(""+jks);
        storeDataList.add(jkrs);

        StoreData xkrs = new StoreData();
        xkrs.setLabel("续课人数");
        xkrs.setValue(""+xks);
        storeDataList.add(xkrs);

        int xkl1 = 100;
        if(jks>0){
            xkl1 = xks*100/jks;
        }
        StoreData xkl = new StoreData();
        xkl.setLabel("续课率");
        xkl.setValue(xkl1+"%");
        storeDataList.add(xkl);

        return storeDataList;
    }

    public List<StoreData> queryManagerKpi(StoreDataQuery query) {
        List<StoreData> storeDataList= new ArrayList();
        List<Map<String,Object>> staffs =  jdbcTemplate.queryForList(" SELECT staff_id,custname from staff where store_id = ? and job = '店长' ",new Object[]{query.getStoreId()});
        for (int i = 0; i < staffs.size(); i++){
            Map staff = staffs.get(i);
            String custname = staff.get("custname").toString();
            KpiStaffMonthEntity kpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(staff.get("staff_id").toString(),query.getMonth().replace("-",""));
            StoreData manager = new StoreData();
            manager.setStaffName(custname);
            manager.setValue(kpiStaffMonthEntity.getKpiScore());
            storeDataList.add(manager);
        }
        return storeDataList;
    }

    public List<StoreData> queryCoachKpi(StoreDataQuery query) {
        List<StoreData> storeDataList= new ArrayList();
        List<Map<String,Object>> staffs =  jdbcTemplate.queryForList(" SELECT staff_id,custname from staff where store_id = ? and job = '教练' ",new Object[]{query.getStoreId()});
        for (int i = 0; i < staffs.size(); i++){
            Map staff = staffs.get(i);
            String custname = staff.get("custname").toString();
            KpiStaffMonthEntity kpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(staff.get("staff_id").toString(),query.getMonth().replace("-",""));
            StoreData manager = new StoreData();
            manager.setStaffName(custname);
            if(kpiStaffMonthEntity!=null){
                manager.setValue(kpiStaffMonthEntity.getKpiScore());
            }else {
                manager.setValue("0");
            }
            storeDataList.add(manager);
        }
        return storeDataList;
    }
}


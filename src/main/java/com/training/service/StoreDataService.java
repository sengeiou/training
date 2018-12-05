package com.training.service;

import com.training.admin.service.CalculateKpiService;
import com.training.admin.service.ManualService;
import com.training.admin.service.MemberTrainingTaskService;
import com.training.common.OriginEnum;
import com.training.common.Page;
import com.training.common.PageRequest;
import com.training.dao.*;
import com.training.domain.*;
import com.training.entity.*;
import com.training.util.ut;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private FinanceMonthReportDao financeMonthReportDao;

    @Autowired
    private FinanceOnceReportDao financeOnceReportDao;

    @Autowired
    private FinanceStaffReportDao financeStaffReportDao;

    @Autowired
    private MemberCardDao memberCardDao;

    @Autowired
    private MemberDao memberDao;

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

        String sql = " SELECT * from contract where store_id = ? and sign_date >= ? and sign_date <= ? ";
        List<Map<String,Object>> contracts =  jdbcTemplate.queryForList(sql,new Object[]{query.getStoreId(),startDate,endDate});
        logger.info(" StoreDataService   querySaleMoney  contracts = {} ",contracts.size());

        int count = 0;

        for (int i = 0; i < contracts.size(); i++){
            Map contract = contracts.get(i);
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

        int count_hk = 0;
        int count_sk = 0;
        double money_hk = 0;
        double money_sk = 0;

        String sql_training = "select * from training where store_id = ? and card_type in ('PT') and status = 0 and  lesson_date >= ? and lesson_date <= ?  ";
        List trainings = jdbcTemplate.queryForList(sql_training,new Object[]{query.getStoreId(),startDate,endDate});
        logger.info(" StoreDataService   queryIncome  trainings = {} ",trainings.size());
        for (int i = 0; i < trainings.size(); i++) {
            Map training = (Map) trainings.get(i);
            String cardNo = training.get("card_no").toString();
            MemberCardEntity memberCardEntity = memberCardDao.getById(cardNo);
            int total = memberCardEntity.getTotal();
            double money = Double.parseDouble(memberCardEntity.getMoney());
            if(total>0){
                double price = money/total;
                money_hk = money_hk + price;
            }
            count_hk++;
        }

        String sql_dead = "select * from kpi_staff_detail where store_id = ? and type = 'SK' and card_type in ('PT') and  month = ?  ";
        List deadCards = jdbcTemplate.queryForList(sql_dead,new Object[]{query.getStoreId(),query.getMonth()});
        for (int i = 0; i < deadCards.size(); i++) {
            Map card = (Map) deadCards.get(i);
            String cardNo = card.get("card_no").toString();
            MemberCardEntity memberCardEntity = memberCardDao.getById(cardNo);
            int count = memberCardEntity.getCount();
            int total = memberCardEntity.getTotal();
            double money = Double.parseDouble(memberCardEntity.getMoney());
            if(total>0){
                double price = money/total;
                money_sk = money_sk + count*price;
            }
            count_sk = count_sk + count;
        }

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
        yqsr.setValue("-");
        yqsr.setLesson("-");
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

        String sql = " SELECT * from member where store_id = ? and created >= ? and created <= ?  ";
        List<Map<String,Object>> members =  jdbcTemplate.queryForList(sql,new Object[]{query.getStoreId(),startDate+" 00:00:00",endDate+" 23:59:59"});
        logger.info(" StoreDataService   queryChangeRate  members = {} ",members.size());

        Set<String> staffIdSet = new HashSet<>();
        List<Map<String,Object>> staffs =  jdbcTemplate.queryForList(" SELECT staff_id,custname,job from staff where store_id = ? ",new Object[]{query.getStoreId()});
        for (int i = 0; i < staffs.size(); i++){
            Map staff = staffs.get(i);
            String staff_id = staff.get("staff_id").toString();
            staffIdSet.add(staff_id);
        }

        int count = 0;
        for (int i = 0; i < members.size(); i++) {
            Map member = members.get(i);
//            String coach_staff_id = member.get("coach_staff_id").toString();
//            if (!staffIdSet.contains(coach_staff_id)) {
//                continue;
//            }
//            String origin = "";
//            if(null!=member.get("origin")){
//                origin = member.get("origin").toString();
//            }
//            if(origin.indexOf("EXCEL")>=0){
//                continue;
//            }
            count++;
        }

        int count_lj = 0;
        String sql_lj = " SELECT * from member where store_id = ? and status = 0 and created <= ?  ";
        List<Map<String,Object>> lj_members =  jdbcTemplate.queryForList(sql_lj,new Object[]{query.getStoreId(),endDate+" 23:59:59"});
        logger.info(" StoreDataService   queryChangeRate  lj_members = {} ",lj_members.size());
        count_lj = lj_members.size();

        int count_dd = 0;
        String sql_card = "select * from member_card where type = 'TY' and created >= ? and created <= ?  ";
        List cards = jdbcTemplate.queryForList(sql_card,new Object[]{startDate+" 00:00:00",endDate+" 23:59:59"});
        for (int i = 0; i <cards.size() ; i++) {
            Map card = (Map) cards.get(i);
            String memberId = card.get("member_id").toString();
            MemberEntity memberEntity = memberDao.getById(memberId);
            if(staffIdSet.contains(memberEntity.getCoachStaffId())){
                count_dd++;
            }
        }

        int cjs = 0;
        String sql_cj = " SELECT * from contract where store_id = ? and sign_date >= ? and sign_date <= ? ";
        List<Map<String,Object>> contracts =  jdbcTemplate.queryForList(sql_cj,new Object[]{query.getStoreId(),startDate,endDate});
        logger.info(" StoreDataService   queryChangeRate  contracts = {} ",contracts.size());
        for (int i = 0; i < contracts.size(); i++) {
            Map contract = contracts.get(i);
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

        StoreData ljyxhys = new StoreData();
        ljyxhys.setLabel("累计意向会员数");
        ljyxhys.setValue(""+count_lj);
        storeDataList.add(ljyxhys);

        StoreData ddrs = new StoreData();
        ddrs.setLabel("到店人数");
        ddrs.setValue(""+count_dd);
        storeDataList.add(ddrs);

        int rate_dd = 100;
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

        int rate_cj = 100;
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
        String tableName = "member_his_"+m;

        logger.info(" StoreDataService   queryMemberData  startDate = {} ",startDate);
        logger.info(" StoreDataService   queryMemberData  endDate = {} ",endDate);

        Set<String> staffIdSet = new HashSet<>();
        Set<String> staffNameSet = new HashSet<>();

        int count_yx = 0;
        int count_tk = 0;

        List data = jdbcTemplate.queryForList("select max(backup_date) backup_date from "+tableName);

        if(data.size()>0){
            Map item = (Map)data.get(0);
            String backup_date = item.get("backup_date").toString();
            List yx_members = jdbcTemplate.queryForList("select * from "+tableName+" where store_id = ? and backup_date = ? and status = 1  ",new Object[]{query.getStoreId(),backup_date});
            List tk_members = jdbcTemplate.queryForList("select * from "+tableName+" where store_id = ? and backup_date = ? and status = 9  ",new Object[]{query.getStoreId(),backup_date});
            count_yx = yx_members.size();
            count_tk = tk_members.size();
        }

        int jks = 0;
        int xks = 0;

        String sql_jk = "select * from kpi_staff_detail where store_id = ? and type = 'JK' and  month = ?  ";
        List jkCards = jdbcTemplate.queryForList(sql_jk,new Object[]{query.getStoreId(),query.getMonth()});
        jks = jkCards.size();

        String sql_xk = "select * from kpi_staff_detail where store_id = ? and type = 'XK' and  month = ?  ";
        List xkCards = jdbcTemplate.queryForList(sql_xk,new Object[]{query.getStoreId(),query.getMonth()});
        xks = xkCards.size();

        String qdhyd = "-";

        String sql = " SELECT * from member where created <= ? ";
        List<Map<String,Object>> members =  jdbcTemplate.queryForList(sql,new Object[]{endDate+" 23:59:59"});
        logger.info(" StoreDataService   queryMemberData  members = {} ",members.size());

        logger.info(" StoreDataService   queryMemberData  count_yx = {} ",count_yx);
        logger.info(" StoreDataService   queryMemberData  count_tk = {} ",count_tk);

        StoreData yxhy = new StoreData();
        yxhy.setLabel("期末有效会员");
        yxhy.setValue(""+count_yx);
        storeDataList.add(yxhy);

        StoreData tkrs = new StoreData();
        tkrs.setLabel("期末停课会员");
        tkrs.setValue(""+count_tk);
        storeDataList.add(tkrs);

        StoreData hyd = new StoreData();
        hyd.setLabel("本月会员活跃度");
        hyd.setValue(""+qdhyd);
        storeDataList.add(hyd);

        StoreData jkrs = new StoreData();
        jkrs.setLabel("本月结课人数");
        jkrs.setValue(""+jks);
        storeDataList.add(jkrs);

        StoreData xkrs = new StoreData();
        xkrs.setLabel("本月续课人数");
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

    public List<StoreData> staffSaleMoneyList(StoreDataQuery query) {
        String staffId = query.getStaffId();
        StaffEntity staffEntity = staffDao.getById(staffId);
        List<StoreData> storeDataList= new ArrayList();
        if(StringUtils.isNotEmpty(query.getMonth())){
            String month = query.getMonth();
            if(month.length()==6){
                month = month.substring(0,4)+"-"+month.substring(4,6);
            }
            StoreData current = this.queryOneMonthstaffSaleMoney(staffEntity,month);
            storeDataList.add(current);
            return storeDataList;
        }
        for (int i = 0; i < 3 ; i++) {
            String month = ut.currentFullMonth(0-i);
            StoreData current = this.queryOneMonthstaffSaleMoney(staffEntity,month);
            logger.info("  queryOneMonthstaffSaleMoney  current = {}",current);
            storeDataList.add(current);
        }
        return storeDataList;
    }

    StoreData queryOneMonthstaffSaleMoney(StaffEntity staffEntity,String month){
        logger.info("  queryOneMonthstaffSaleMoney   staff = {} and month = {}",staffEntity.getCustname(),month);
        StoreData storeData = new StoreData();
        String sql = " select * from contract where type in ('新会员','续课','转介绍') and sign_date >= '"+month+"-01"+"' and sign_date <=  '"+month+"-31"+"' and salesman like '%"+staffEntity.getCustname()+"%'";
        List data = jdbcTemplate.queryForList(sql);
        int count = 0;
        double money = 0;
        for (int i = 0; i < data.size(); i++) {
            Map item = (Map)data.get(i);
            String salesman = item.get("salesman").toString();
            if (salesman.indexOf("(") > 0) {
                int index = salesman.indexOf("(");
                salesman = salesman.substring(0, index);
            }
            if (salesman.indexOf("（") > 0) {
                int index = salesman.indexOf("（");
                salesman = salesman.substring(0, index);
            }
            if(salesman.equals(staffEntity.getCustname())){
                count++;
                money = money + Double.parseDouble(item.get("money").toString());
            }
        }
        storeData.setMonth(month);
        storeData.setCount(""+count);
        storeData.setMoney(ut.getDoubleString(money));
        return storeData;
    }

    private Map<String,MarketReportData> createMarketReportMap(StoreEntity storeEntity) {
        Map<String,MarketReportData> dataMap = new HashedMap();
        for(OriginEnum originEnum:OriginEnum.values()){
            MarketReportData marketReportData = new MarketReportData();
            marketReportData.setStoreId(storeEntity.getStoreId());
            marketReportData.setStoreName(storeEntity.getName());
            marketReportData.setOrigin(originEnum.getDesc());
            marketReportData.setNewCount(0);
            marketReportData.setArriveCount(0);
            marketReportData.setOrderCount(0);
            marketReportData.setMoney("0");
            dataMap.put(originEnum.getDesc(),marketReportData);
        }
        return dataMap;
    }

    public List<MarketReportData> queryMarketingReport(StoreDataQuery query) {
        StoreEntity storeEntity = storeDao.getById(query.getStoreId());
        List<MarketReportData> dataList= new ArrayList();
        if(storeEntity==null){
            return dataList;
        }
        Map<String,MarketReportData> dataMap = createMarketReportMap(storeEntity);
        String startDate = ut.firstDayOfMonth();
        String endDate = ut.lastDayOfMonth();
        if(StringUtils.isNotEmpty(query.getStartDate())){
            startDate = query.getStartDate();
        }
        if(StringUtils.isNotEmpty(query.getEndDate())){
            endDate = query.getEndDate();
        }
        //统计新增意向人数
        queryNewMember(storeEntity,startDate,endDate,dataMap);
        //统计到店人数
        queryComingMember(storeEntity,startDate,endDate,dataMap);
        //统计成交人数和金额
        queryContractMember(storeEntity,startDate,endDate,dataMap);
        for ( MarketReportData marketReportData : dataMap.values()){
//            logger.info(" marketReportData = {} ",marketReportData);
            dataList.add(marketReportData);
        }
        logger.info(" dataList = {} ",dataList.size());
        return dataList;
    }

    /**
     *  统计新增意向人数
     */
    private void queryNewMember(StoreEntity storeEntity, String startDate, String endDate, Map<String, MarketReportData> dataMap) {
        String sql = " select member_id,phone,store_id,coach_staff_id, origin from member where store_id = ? and created >= ? and created <= ? and origin <> '' ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{storeEntity.getStoreId(),startDate+" 00:00:00",endDate+" 23:59:59"});
        for (int i = 0; i < data.size(); i++) {
            Map item = (Map) data.get(i);
            String origin = item.get("origin").toString().trim();
            if(StringUtils.isEmpty(origin)){
                origin = OriginEnum.OTHER.getDesc();
            }
            MarketReportData marketReportData = null;
            if(dataMap.containsKey(origin)){
                marketReportData = dataMap.get(origin);
            }else{
                logger.info(" *************   origin = {} ",origin);
                marketReportData = new MarketReportData();
                marketReportData.setStoreId(storeEntity.getStoreId());
                marketReportData.setStoreName(storeEntity.getName());
                marketReportData.setOrigin(origin);
                marketReportData.setNewCount(0);
                marketReportData.setArriveCount(0);
                marketReportData.setOrderCount(0);
                marketReportData.setMoney("0");
                dataMap.put(origin,marketReportData);
            }
            marketReportData.setNewCount(marketReportData.getNewCount()+1);
        }
    }

    /**
     *  统计到店人数,即发体验卡的人数
     */
    private void queryComingMember(StoreEntity storeEntity, String startDate, String endDate, Map<String, MarketReportData> dataMap) {
        String sql = " select a.member_id,a.`name`,a.phone,a.store_id,a.coach_staff_id, a.origin,b.card_no,b.type,b.created from member a , member_card b " +
                " where a.store_id = ? and a.origin <> ''  and a.member_id = b.member_id and b.type = 'TY' " +
                " and b.created >= ? and b.created <= ? ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{storeEntity.getStoreId(),startDate+" 00:00:00",endDate+" 23:59:59"});
        for (int i = 0; i < data.size(); i++) {
            Map item = (Map) data.get(i);
            String origin = item.get("origin").toString().trim();
            if(StringUtils.isEmpty(origin)){
                origin = OriginEnum.OTHER.getDesc();
            }
            MarketReportData marketReportData = null;
            if(!dataMap.containsKey(origin)){
                continue;
            }
            marketReportData = dataMap.get(origin);
            marketReportData.setArriveCount(marketReportData.getArriveCount()+1);
        }
    }

    /**
     *  统计成交人数和金额
     *  新会员和转介绍都算成交
     */
    private void queryContractMember(StoreEntity storeEntity, String startDate, String endDate, Map<String, MarketReportData> dataMap) {
        String sql = " select a.member_id,a.`name`,a.phone,a.store_id,a.coach_staff_id, a.origin,b.sign_date,b.money,b.type,b.created from member a , contract b " +
                "where a.store_id = ? and a.origin <> '' and a.member_id = b.member_id and b.type in ('新会员','转介绍') " +
                "and b.sign_date >= ? and b.sign_date <= ? ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{storeEntity.getStoreId(),startDate,endDate});
        for (int i = 0; i < data.size(); i++) {
            Map item = (Map) data.get(i);
            String origin = item.get("origin").toString().trim();
            if(StringUtils.isEmpty(origin)){
                origin = OriginEnum.OTHER.getDesc();
            }
            MarketReportData marketReportData = null;
            if(!dataMap.containsKey(origin)){
                continue;
            }
            marketReportData = dataMap.get(origin);
            double money = Double.parseDouble(item.get("money").toString());
            double total = Double.parseDouble(marketReportData.getMoney());
            marketReportData.setOrderCount(marketReportData.getOrderCount()+1);
            marketReportData.setMoney(ut.getDoubleString(total+money));
        }
    }

    private List getContract(String memberId, String phone, String startDate, String endDate) {
        List<ContractEntity> result = new ArrayList<>();
        String sql = "select * from contract where phone = ? and type in ('新会员','转介绍') and sign_date >= ? and sign_date <= ?  ";
        List contracts = jdbcTemplate.queryForList(sql,new Object[]{phone,startDate,endDate});
        for (int i = 0; i < contracts.size(); i++) {
            Map item = (Map) contracts.get(i);
            ContractEntity contractEntity = new ContractEntity();
            contractEntity.setMoney(item.get("money").toString());
            result.add(contractEntity);
        }
        return result;
    }

    private boolean hasTYCard(String memberId, String startDate, String endDate) {
        String sql = "select * from member_card where member_id = ? and type = 'TY'  and created >= ? and created <= ? ";
        List cards = jdbcTemplate.queryForList(sql,new Object[]{memberId,startDate+" 00:00:00",endDate+" 23:59:59"});
        return cards.size()>0;
    }


    public Page<FinanceTimesCardReportData> queryFinanceTimesCardReport(StoreDataQuery query, PageRequest pageRequest) {
        List<FinanceTimesCardReportData> dataList= new ArrayList();
        FinanceOnceReportQuery financeOnceReportQuery = new FinanceOnceReportQuery();
        financeOnceReportQuery.setStoreId(query.getStoreId());
        if(StringUtils.isEmpty(query.getMonth())){
            financeOnceReportQuery.setMonth(ut.currentFullMonth());
        }else {
            financeOnceReportQuery.setMonth(query.getMonth().substring(0,4)+"-"+query.getMonth().substring(4,6));
        }
        financeOnceReportQuery.setStatus(1);

        logger.info(" queryFinanceTimesCardReport financeOnceReportQuery = {} ",financeOnceReportQuery);
        List<FinanceOnceReportEntity> financeOnceReportList = financeOnceReportDao.find(financeOnceReportQuery,pageRequest);
        for (FinanceOnceReportEntity financeOnceReportEntity:financeOnceReportList){
            FinanceTimesCardReportData financeTimesCardReportData = new FinanceTimesCardReportData();
            BeanUtils.copyProperties(financeOnceReportEntity,financeTimesCardReportData);
            dataList.add(financeTimesCardReportData);
        }
        Long count = financeOnceReportDao.count(financeOnceReportQuery);
        Page<FinanceTimesCardReportData> returnPage = new Page<>();
        returnPage.setContent(dataList);
        returnPage.setPage(pageRequest.getPage());
        returnPage.setSize(pageRequest.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    public Page<FinanceMonthCardReportData> queryFinanceMonthCardReport(StoreDataQuery query,PageRequest pageRequest) {
        List<FinanceMonthCardReportData> dataList= new ArrayList();
        FinanceMonthReportQuery financeMonthReportQuery = new FinanceMonthReportQuery();
        financeMonthReportQuery.setStoreId(query.getStoreId());
        if(StringUtils.isEmpty(query.getMonth())){
            financeMonthReportQuery.setMonth(ut.currentFullMonth());
        }else {
            financeMonthReportQuery.setMonth(query.getMonth().substring(0,4)+"-"+query.getMonth().substring(4,6));
        }
        financeMonthReportQuery.setStatus(1);

        logger.info(" queryFinanceMonthCardReport financeMonthReportQuery = {} ",financeMonthReportQuery);
        List<FinanceMonthReportEntity> financeMonthReportEntities = financeMonthReportDao.find(financeMonthReportQuery,pageRequest);
        for (FinanceMonthReportEntity financeMonthReportEntity:financeMonthReportEntities){
            FinanceMonthCardReportData financeMonthCardReportData = new FinanceMonthCardReportData();
            BeanUtils.copyProperties(financeMonthReportEntity,financeMonthCardReportData);
            dataList.add(financeMonthCardReportData);
        }
        Long count = financeMonthReportDao.count(financeMonthReportQuery);
        Page<FinanceMonthCardReportData> returnPage = new Page<>();
        returnPage.setContent(dataList);
        returnPage.setPage(pageRequest.getPage());
        returnPage.setSize(pageRequest.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    public Page<FinanceStaffReportData> queryFinanceStaffReport(StoreDataQuery query,PageRequest pageRequest) {
        List<FinanceStaffReportData> dataList= new ArrayList();
        FinanceStaffReportQuery financeStaffReportQuery = new FinanceStaffReportQuery();
        financeStaffReportQuery.setStaffId(query.getStaffId());
        financeStaffReportQuery.setTemplateId(query.getTemplateId());
        financeStaffReportQuery.setStaffName(query.getName());
        financeStaffReportQuery.setPhone(query.getPhone());
        financeStaffReportQuery.setStoreId(query.getStoreId());
        financeStaffReportQuery.setJob(query.getJob());
        if(StringUtils.isEmpty(query.getMonth())){
            financeStaffReportQuery.setMonth(ut.currentFullMonth());
        }else {
            financeStaffReportQuery.setMonth(query.getMonth().substring(0,4)+"-"+query.getMonth().substring(4,6));
        }
        financeStaffReportQuery.setStatus(1);

        logger.info(" queryFinanceStaffReport financeStaffReportQuery = {} ",financeStaffReportQuery);
        List<FinanceStaffReportEntity> financeStaffReportEntityList = financeStaffReportDao.find(financeStaffReportQuery,pageRequest);
        for (FinanceStaffReportEntity financeStaffReportEntity:financeStaffReportEntityList){
            FinanceStaffReportData financeStaffReportData = new FinanceStaffReportData();
            BeanUtils.copyProperties(financeStaffReportEntity,financeStaffReportData);
            dataList.add(financeStaffReportData);
        }
        Long count = financeStaffReportDao.count(financeStaffReportQuery);
        Page<FinanceStaffReportData> returnPage = new Page<>();
        returnPage.setContent(dataList);
        returnPage.setPage(pageRequest.getPage());
        returnPage.setSize(pageRequest.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    private FinanceTimesCardReportData randomFinanceTimesCardReportData() {
        FinanceTimesCardReportData financeTimesCardReportData = new FinanceTimesCardReportData();
        financeTimesCardReportData.setStoreId(""+RandomUtils.nextInt(new Random(10)));
        financeTimesCardReportData.setStoreName("测试门店");
        financeTimesCardReportData.setMonth("2018-10");
        financeTimesCardReportData.setSaleMoney("SaleMoney");
        financeTimesCardReportData.setSaleLessonCount("saleLessonCount");
        financeTimesCardReportData.setWaitingLessonMoney("WaitingLessonMoney");
        financeTimesCardReportData.setWaitingLessonCount("WaitingLessonCount");
        financeTimesCardReportData.setUsedLessonMoney("UsedLessonMoney");
        financeTimesCardReportData.setUsedLessonCount("UsedLessonCount");
        financeTimesCardReportData.setDeadLessonMoney("DeadLessonMoney");
        financeTimesCardReportData.setDeadLessonCount("DeadLessonCoun");
        financeTimesCardReportData.setDelayMoney("DelayMoney");
        financeTimesCardReportData.setOutLessonMoney("OutLessonMoney");
        financeTimesCardReportData.setOutLessonCount("OutLessonCount");
        financeTimesCardReportData.setInLessonMoney("InLessonMoney");
        financeTimesCardReportData.setInLessonCount("InLessonCount");
        financeTimesCardReportData.setBackLessonMoney("BackLessonMoney");
        financeTimesCardReportData.setBackLessonCount("BackLessonCount");
        return financeTimesCardReportData;
    }

    private FinanceMonthCardReportData randomFinanceMonthCardReportData() {
        FinanceMonthCardReportData financeMonthCardReportData = new FinanceMonthCardReportData();
        financeMonthCardReportData.setStoreId(""+RandomUtils.nextInt(new Random(10)));
        financeMonthCardReportData.setStoreName("测试门店");
        financeMonthCardReportData.setMonth("2018-10");
        financeMonthCardReportData.setSaleMoney("SaleMoney");
        financeMonthCardReportData.setSaleDaysCount("SaleDaysCount");
        financeMonthCardReportData.setWaitingDaysMoney("WaitingDaysMoney");
        financeMonthCardReportData.setWaitingDaysCount("WaitingDaysCount");
        financeMonthCardReportData.setUsedDaysMoney("UsedDaysMoney");
        financeMonthCardReportData.setUsedDaysCount("UsedDaysCount");
        financeMonthCardReportData.setOutDaysMoney("OutDaysMoney");
        financeMonthCardReportData.setInDaysMoney("InDaysMoney");
        financeMonthCardReportData.setBackDaysMoney("BackDaysMoney");
        return financeMonthCardReportData;
    }

    private FinanceStaffReportData randomFinanceStaffReportData() {
        FinanceStaffReportData financeStaffReportData = new FinanceStaffReportData();
        financeStaffReportData.setStaffId(""+RandomUtils.nextInt(new Random(100)));
        financeStaffReportData.setStaffName("测试员工");
        financeStaffReportData.setStoreId(""+RandomUtils.nextInt(new Random(10)));
        financeStaffReportData.setStoreName("测试门店");
        financeStaffReportData.setMonth("2018-10");
        financeStaffReportData.setPhone("Phone");
        financeStaffReportData.setStar("Star");
        financeStaffReportData.setJob("Job");
        financeStaffReportData.setTemplateName("TemplateName");
        financeStaffReportData.setKpiScore("100");
        financeStaffReportData.setSaleMoney("");
        financeStaffReportData.setXkMoney("");
        financeStaffReportData.setTimesCardLessonCount("TimesCardLessonCount");
        financeStaffReportData.setMonthCardSingleLessonCount("MonthCardSingleLessonCount");
        financeStaffReportData.setMonthCardMultiLessonCount("MonthCardMultiLessonCount");
        financeStaffReportData.setTyCardMultiLessonCount("TyCardMultiLessonCount");
        financeStaffReportData.setSpecialLessonCount("SpecialLessonCount");
        financeStaffReportData.setTeamLessonCount("TeamLessonCount");
        return financeStaffReportData;
    }


}


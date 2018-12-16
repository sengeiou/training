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

import java.util.*;

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

    public void calculateStaffKpi(String staffId,String month) {
        KpiStaffMonthEntity kpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(staffId,month);
        if(kpiStaffMonthEntity==null){
            int n = manualService.createSingleStaffMonth(staffId,month);
            if(n==0){
                return;
            }
            kpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(staffId,month);
        }

        StaffEntity staffEntity = staffDao.getById(staffId);

        if(StringUtils.isEmpty(staffEntity.getJob())){
            return;
        }
        if(!staffEntity.getJob().equals("店长") && !staffEntity.getJob().equals("教练")){
            return;
        }

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
//        int yyts = queryOpenDays(staffEntity.getStoreId(),month);

        int xkl = 100;
        int exXks = 0;
        int exJks = 0;
        if(StringUtils.isNotEmpty(kpiStaffMonthEntity.getParam3())){
            exXks = (int)Double.parseDouble(kpiStaffMonthEntity.getParam3());
        }

        if(StringUtils.isNotEmpty(kpiStaffMonthEntity.getParam4())){
            exJks = (int)Double.parseDouble(kpiStaffMonthEntity.getParam4());
        }

        if((jks+exJks)>0){
            xkl = 100*(xks+exXks)/(jks+exJks);
        }

        int cjs = getCjs(staffId,month);
        int tcs = getTcs(staffId,month);
        int zye = getZye(staffId,month);

        double hyd = 0;
        if(validMemberCount>0){
            String y = month.substring(0,4);
            String m = month.substring(4,6);
            String startDate = y+"-"+m+"-01";
            int days = ut.daysOfMonth(startDate);
            int restDays = 0;
            if(StringUtils.isNotEmpty(kpiStaffMonthEntity.getParam2())){
                restDays = Integer.parseInt(kpiStaffMonthEntity.getParam2());
            }
            int average = 0;
            if(days>0){
                average = validMemberCount/days;
            }
            hyd = (double)lessonCount*100/(validMemberCount-average*restDays);
        }
//        logger.info(" hyd  = {}  ",hyd);


        kpiStaffMonthEntity.setXks(""+xks);
        kpiStaffMonthEntity.setJks(""+jks);
        kpiStaffMonthEntity.setXkl(""+xkl);
        kpiStaffMonthEntity.setSjks(""+lessonCount);
        kpiStaffMonthEntity.setYxhys(""+validMemberCount);
        kpiStaffMonthEntity.setYyts("");
        kpiStaffMonthEntity.setHyd(""+ut.getDoubleString(hyd));
        kpiStaffMonthEntity.setCjs(""+cjs);
        kpiStaffMonthEntity.setTcs(""+tcs);
        kpiStaffMonthEntity.setZye(""+zye);

        if(StringUtils.isNotEmpty(templateId)){
            KpiStaffMonth kpiStaffMonth = kpiStaffMonthService.calculateKpiStaffMonth(kpiStaffMonthEntity);
            kpiStaffMonthEntity.setKpiScore(kpiStaffMonth.getKpiScore());
        }
        else{
            kpiStaffMonthEntity.setKpiScore("0");
        }
        kpiStaffMonthEntity.setKpiData(JSON.toJSONString(kpiTemplateEntity));
        int n = kpiStaffMonthDao.update(kpiStaffMonthEntity);
    }

    public void calculateStoreKpi(String storeId,String month) {
        String y = month.substring(0,4);
        String m = month.substring(4,6);
        String startDate = y+"-"+m+"-01";
        int days = ut.daysOfMonth(startDate);

        KpiStaffMonthEntity kpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(storeId,month);
        if(kpiStaffMonthEntity==null){
            int n = manualService.createSingleStaffMonth(storeId,month);
            if(n==0){
                return;
            }
            kpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(storeId,month);
        }

        double qdxks = 0;
        double qdjks = 0;
        int qdlessonCount = 0;
        int qdvalidMemberCount = 0;

        int qdxkl = 100;
        double qdzjs = 0;
        int qdhydp = 0;
        double qdhyd = 0;

        int qdzye = 0;
        int qdcjs = 0;
        int qdtcs = 0;
        List<String> staffIdList = queryStoreStaffList(storeId,month);
        int staffCount = staffIdList.size();
        logger.info(" staffCount  = {}  ",staffCount);
        for (String staffId : staffIdList){
            KpiStaffMonthEntity subKpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(staffId,month);
            if(subKpiStaffMonthEntity==null){
                continue;
            }
            if(StringUtils.isNotEmpty(subKpiStaffMonthEntity.getHydp())){
                int hydp = Integer.parseInt(subKpiStaffMonthEntity.getHydp());
                qdhydp = qdhydp + hydp;
            }
            if(StringUtils.isNotEmpty(subKpiStaffMonthEntity.getXks())){
                qdxks = qdxks + Integer.parseInt(subKpiStaffMonthEntity.getXks());
            }

            if(StringUtils.isNotEmpty(subKpiStaffMonthEntity.getParam3())){
                qdxks = qdxks + (int)Double.parseDouble(subKpiStaffMonthEntity.getParam3());
            }

            if(StringUtils.isNotEmpty(subKpiStaffMonthEntity.getJks())){
                qdjks = qdjks + Integer.parseInt(subKpiStaffMonthEntity.getJks());
            }

            if(StringUtils.isNotEmpty(subKpiStaffMonthEntity.getParam4())){
                qdxks = qdxks + (int)Double.parseDouble(subKpiStaffMonthEntity.getParam4());
            }

            if(StringUtils.isNotEmpty(subKpiStaffMonthEntity.getSjks())){
                qdlessonCount = qdlessonCount + Integer.parseInt(subKpiStaffMonthEntity.getSjks());
            }
            if(StringUtils.isNotEmpty(subKpiStaffMonthEntity.getYxhys())){
                int validMemberCount = Integer.parseInt(subKpiStaffMonthEntity.getYxhys());
                int restDays = 0;
                if(StringUtils.isNotEmpty(subKpiStaffMonthEntity.getParam2())){
                    restDays = Integer.parseInt(subKpiStaffMonthEntity.getParam2());
                }
                int average = 0;
                if(days>0){
                    average = validMemberCount/days;
                }
                validMemberCount = validMemberCount - average*restDays;
                qdvalidMemberCount = qdvalidMemberCount + validMemberCount;
            }
            if(StringUtils.isNotEmpty(subKpiStaffMonthEntity.getZjs())){
                qdzjs = qdzjs + Integer.parseInt(subKpiStaffMonthEntity.getZjs());
            }
            if(StringUtils.isNotEmpty(subKpiStaffMonthEntity.getZye())){
                qdzye = qdzye + Integer.parseInt(subKpiStaffMonthEntity.getZye());
            }
            if(StringUtils.isNotEmpty(subKpiStaffMonthEntity.getCjs())){
                qdcjs = qdcjs + Integer.parseInt(subKpiStaffMonthEntity.getCjs());
            }
            if(StringUtils.isNotEmpty(subKpiStaffMonthEntity.getTcs())){
                qdtcs = qdtcs + Integer.parseInt(subKpiStaffMonthEntity.getTcs());
            }
        }

        if(staffCount>0){
            qdhydp = qdhydp/staffCount;
            qdzjs = qdzjs/staffCount;
            qdxks = qdxks/staffCount;
            qdjks = qdjks/staffCount;
        }

        if(qdvalidMemberCount>0){
            qdhyd = (double)qdlessonCount*100/qdvalidMemberCount;
        }

        int xswcl = 100;
        if(StringUtils.isNotEmpty(kpiStaffMonthEntity.getXsmb())){
            int xsmb = Integer.parseInt(kpiStaffMonthEntity.getXsmb());
            if(xsmb>0){
                xswcl = qdzye*100/xsmb;
            }
        }
        int tczhl = 0;
        if(qdtcs>0){
            tczhl = qdcjs*100/qdtcs;
        }
        kpiStaffMonthEntity.setQdzye(""+qdzye);

        kpiStaffMonthEntity.setQdxks(""+qdxks);
        kpiStaffMonthEntity.setQdjks(""+qdjks);
        kpiStaffMonthEntity.setQdyxhys(""+qdvalidMemberCount);
        kpiStaffMonthEntity.setQdsjks(""+qdlessonCount);

        kpiStaffMonthEntity.setXswcl(""+xswcl);
        kpiStaffMonthEntity.setQdxkl(""+qdxkl);
        kpiStaffMonthEntity.setQdhyd(""+ut.getDoubleString(qdhyd));
        kpiStaffMonthEntity.setQdhydp(""+qdhydp);
        kpiStaffMonthEntity.setQdzjs(ut.getDoubleString(qdzjs));
        kpiStaffMonthEntity.setQdcjs(""+qdcjs);
        kpiStaffMonthEntity.setQdtcs(""+qdtcs);
        kpiStaffMonthEntity.setTczhl(""+tczhl);

        KpiStaffMonth kpiStaffMonth = kpiStaffMonthService.calculateKpiStaffMonth(kpiStaffMonthEntity);
        kpiStaffMonthEntity.setKpiScore(kpiStaffMonth.getKpiScore());

        kpiStaffMonthEntity.setKpiData("");
        int n = kpiStaffMonthDao.update(kpiStaffMonthEntity);

    }

    private int getZye(String staffId, String month) {
        StaffEntity staffEntity = staffDao.getById(staffId);
        if(staffEntity==null){
            return 0;
        }
        String y = month.substring(0,4);
        String m = month.substring(4,6);
        String startDate = y+"-"+m+"-01";
        String endDate = y+"-"+m+"-31";
        String sql = "select * from contract where salesman like concat('%',?,'%') and sign_date >= ? and sign_date <= ? ";
        int yye = 0;
        List data = jdbcTemplate.queryForList(sql,new Object[]{staffEntity.getCustname(),startDate,endDate});
        for (int i = 0; i < data.size(); i++) {
            Map item = (Map)data.get(i);
            String money = item.get("money").toString();
            if(StringUtils.isNotEmpty(money)){
                yye = yye + (int)Double.parseDouble(money);
            }
        }
        logger.info(" getZye = {} , staffId = {} , name = {} , month = {} , data.size = {} ",yye,staffId,staffEntity.getCustname(),month,data.size());
        return yye;
    }

    private int getTcs(String subStaffId , String month) {
        String y = month.substring(0,4);
        String m = month.substring(4,6);
        String startDate = y+"-"+m+"-01";
        String endDate = y+"-"+m+"-31";
        int count = 0;
        String sql = "select * from member where coach_staff_id = ?  ";
        String sql_card = "select * from member_card where member_id = ? and type = 'TY' and created >= ? and created <= ?  ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{subStaffId});
        for (int i = 0; i < data.size(); i++) {
            Map member = (Map)data.get(i);
            String memberId = member.get("member_id").toString();
            String origin = "";
            if(null!=member.get("origin")){
                origin = member.get("origin").toString();
            }
            if(origin.indexOf("EXCEL")>=0||origin.indexOf("合同")>=0||origin.indexOf("自动生成")>=0){
                continue;
            }
            List ty_cards = jdbcTemplate.queryForList(sql_card,new Object[]{member.get("member_id").toString(),startDate+" 00:00:00",endDate+" 23:59:59"});
            if(ty_cards.size()>0){
                count++;
            }
        }
        return count;
    }

    private int getCjs(String subStaffId , String month) {
        StaffEntity staffEntity = staffDao.getById(subStaffId);
        if(staffEntity==null){
            return 0;
        }
        String y = month.substring(0,4);
        String m = month.substring(4,6);
        String startDate = y+"-"+m+"-01";
        String endDate = y+"-"+m+"-31";
        String sql = "select * from contract where card_type in ('PT','PM') and salesman like concat('%',?,'%') and sign_date >= ? and sign_date <= ? ";
        int cjs = 0;
        List data = jdbcTemplate.queryForList(sql,new Object[]{staffEntity.getCustname(),startDate,endDate});
        for (int i = 0; i < data.size(); i++) {
            Map item = (Map)data.get(i);
            String type = item.get("type").toString();
            if("新会员".equals(type)){
                cjs++;
            }
            if("转介绍".equals(type)){
                cjs++;
            }
        }
        logger.info(" getCjs = {} , staffId = {} , name = {} , month = {} , data.size = {} ",cjs,subStaffId,staffEntity.getCustname(),month,data.size());
        return cjs;
    }

    private List queryStoreStaffList(String storeId,String month) {
        List<String> staffIdList = new ArrayList<>();
        String sql = "select * from staff where store_id = ? and job in ('教练','店长') ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{storeId});
        for (int i = 0; i < data.size(); i++) {
            Map staff = (Map)data.get(i);
            String staff_id = staff.get("staff_id").toString();
//            int count = queryValidMemberCount(staff_id,month);
//            if(count>0){
                staffIdList.add(staff_id);
//            }
        }
        return staffIdList;
    }

    private int getXks(String staffId, String month) {
        StaffEntity staffEntity = staffDao.getById(staffId);
        if(staffEntity==null){
            return 0;
        }
        String y = month.substring(0,4);
        String m = month.substring(4,6);
        String startDate = y+"-"+m+"-01";
        String endDate = y+"-"+m+"-31";
        String sql = "select * from contract where card_type in ('PT','PM') and coach like concat('%',?,'%') and sign_date >= ? and sign_date <= ? ";
        int xks = 0;
        List data = jdbcTemplate.queryForList(sql,new Object[]{staffEntity.getCustname(),startDate,endDate});
//        logger.info(" getXks  name = {} , startDate = {} , endDate = {} , data.size = {} ",staffEntity.getCustname(),startDate,endDate,data.size());
        for (int i = 0; i < data.size(); i++) {
            Map item = (Map)data.get(i);
            String type = item.get("type").toString();
            if("续课".equals(type)){
//                logger.info(" getXks"+xks+"  item = {} ",item);
                xks++;
            }
        }
//        logger.info(" getXks = {} , staffId = {} , name = {} , month = {} , data.size = {} ",xks,staffId,staffEntity.getCustname(),month,data.size());
        return xks;
    }

    /**
     *  余额为0 结课+1 , 有效期过期30天以上算结课
     */
    private int getJks(String staffId, String month) {
        int jks = 0;
        String y = month.substring(0,4);
        String m = month.substring(4,6);
        String startDate = y+"-"+m+"-01";
        String endDate = y+"-"+m+"-31";
        String sql = "select * from member where coach_staff_id = ? and created <= ? ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{staffId,endDate+" 23:59:59"});
        for (int i = 0; i < data.size(); i++) {
            Map member = (Map)data.get(i);
            String memberId = member.get("member_id").toString();
            String name = member.get("name").toString();
            List cards = jdbcTemplate.queryForList("select * from member_card where member_id = ? and count = 0 and type in ('PT','PM') and end_date >= ? and created <= ?  " ,new Object[]{memberId,startDate,endDate+" 23:59:59"});
            for (int j = 0; j < cards.size(); j++) {
                Map memberCard = (Map)cards.get(j);
                String cardNo = memberCard.get("card_no").toString();
                List trainings = jdbcTemplate.queryForList("select * from training where card_no = ? and lesson_date >= ? and lesson_date <= ? " ,new Object[]{cardNo,startDate,endDate});
                if(trainings.size()>0){
                    trainings = jdbcTemplate.queryForList("select * from training where card_no = ? and lesson_date > ? " ,new Object[]{cardNo,endDate});
                    if(trainings.size()==0){
//                        logger.info(" memberId = {} , cardNo = {} ",memberId,cardNo);
                        jks++;
                    }
                }
            }
            String sd = ut.currentDate(startDate,-30);
            if(ut.passDayByDate(endDate,ut.currentDate())<0){
                endDate = ut.currentDate();
            }
            String ed = ut.currentDate(endDate,-30);
            List cards_end = jdbcTemplate.queryForList("select * from member_card where member_id = ? and count > 0 and type <> 'TY' and end_date >= ? and end_date <= ? and created <= ?  " ,new Object[]{memberId,sd,ed,endDate+" 23:59:59"});
//            logger.info(" memberId = {} , sd = {} , ed = {} ,  cards_end.size() = {} ",memberId,sd,ed,cards_end.size());
            if(cards_end.size()>0){
//                logger.info(" cards_end.size() > 0   memberId = {} ",memberId);
                jks++;
            }
        }
//        logger.info(" jks = {} ",jks);
        return jks;
    }


    /**
     * KPI计算仅统计已经签到的私教课，包括特色课
     * @param staffId
     * @param month
     * @return
     */
    private int queryLessonCount(String staffId, String month) {
        String y = month.substring(0,4);
        String m = month.substring(4,6);
        String startDate = y+"-"+m+"-01";
        String endDate = y+"-"+m+"-31";

        if(ut.passDayByDate(ut.currentDate(),endDate)>0){
            endDate = ut.currentDate();
        }

        int count = 0;
        int count_sign = 0;
        int count_ty = 0;
        int count_ty_sign = 0;
        String sql = " select training_id,lesson_id,type,sign_time,card_type from training where staff_id = ? and lesson_date >= ? and lesson_date <= ? and show_tag = 1  ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{staffId,startDate,endDate});

//        Set<String> lessonIds = new HashSet<>();

        for (int i = 0; i < data.size(); i++) {
            Map training = (Map)data.get(i);
            String type = training.get("type").toString();
            String card_type = training.get("card_type").toString();
            String sign_time = training.get("sign_time").toString();
            if("P".equals(type)){
                if("PT".equals(card_type)||"PM".equals(card_type)){
                    count++;
                    if(StringUtils.isNotEmpty(sign_time)){
                        count_sign++;
                    }
                }else if("TY".equals(card_type)){
                    count_ty++;
                    if(StringUtils.isNotEmpty(sign_time)){
                        count_ty_sign++;
                    }
                }
            }else if(type.startsWith("S")){
                count++;
                if(StringUtils.isNotEmpty(sign_time)){
                    count_sign++;
                }
            }
        }
//        logger.info(" queryLessonCount  count = {} , count_sign = {} ,  count_ty = {} , count_ty_sign = {} ",count,count_sign,count_ty,count_ty_sign);
        return count_sign;
    }

    /**
     *  有效会员数 ： 非停 非结
     */
    public int queryValidMemberCount(String staffId, String month) {
        logger.info(" queryValidMemberCount  staffId = {} , month = {} ",staffId,month);
        String y = month.substring(0,4);
        String m = month.substring(4,6);
        String startDate = y+"-"+m+"-01";
        String endDate = y+"-"+m+"-31";
        if(ut.passDayByDate(ut.currentDate(),endDate)>0){
            endDate = ut.currentDate();
        }
        String tableName = "member_his_"+m;
        int count = 0;
        String sql = " select * from "+tableName+" where coach_staff_id = ? and status in (1) and created <= ? ";
//        logger.info(" sql = {} ",sql);
        List data = jdbcTemplate.queryForList(sql,new Object[]{staffId,endDate+" 23:59:59"});
        for (int i = 0; i < data.size(); i++) {
            Map member = (Map)data.get(i);

            count++;
        }
//        logger.info(" queryValidMemberCount tableName = {} ,  data.size() = {} , count = {} ",tableName,data.size(),count);
        return count;
    }


    /**
     *  有效会员数 ： 非停 非结
     */
    public int queryValidMemberCountByDay(String staffId, String day) {
//        logger.info(" queryValidMemberCountByDay  staffId = {} , day = {} ",staffId,day);
        String y = day.substring(0,4);
        String m = day.substring(5,7);
        String tableName = "member_his_"+m;
        int count = 0;
        String sql = " select * from "+tableName+" where coach_staff_id = ? and status in (1) and backup_date = ? ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{staffId,day});
        for (int i = 0; i < data.size(); i++) {
            Map member = (Map)data.get(i);

            count++;
        }
//        logger.info(" queryValidMemberCountByDay tableName = {} ,  data.size() = {} , count = {} ",tableName,data.size(),count);
        return count;
    }

    /**
     *  有效会员数 ： 非停 非结
     */
    public int queryTotalMemberCountByDay(String staffId, String day) {
//        logger.info(" queryTotalMemberCountByDay  staffId = {} , day = {} ",staffId,day);
        String y = day.substring(0,4);
        String m = day.substring(5,7);
        String tableName = "member_his_"+m;
        int count = 0;
        String sql = " select * from "+tableName+" where coach_staff_id = ? and backup_date = ? and status = 1 ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{staffId,day});
        for (int i = 0; i < data.size(); i++) {
            Map member = (Map)data.get(i);

            count++;
        }
//        logger.info(" queryTotalMemberCountByDay tableName = {} ,  data.size() = {} , count = {} ",tableName,data.size(),count);
        return count;
    }


    private int queryOpenDays(String storeId,String month) {
        String y = month.substring(0,4);
        String m = month.substring(4,6);
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


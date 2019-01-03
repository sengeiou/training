package com.training.admin.service;

import com.alibaba.fastjson.JSON;
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
import java.util.Map;

/**
 * staff 核心业务操作类
 * Created by huai23 on 2018-05-26 13:55:30.
 */
@Service
public class CoachStaffKpiService {

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
        StaffEntity staffEntity = staffDao.getById(staffId);
        if(StringUtils.isEmpty(staffEntity.getJob())){
            return;
        }
        if(!staffEntity.getJob().equals("店长") && !staffEntity.getJob().equals("教练")){
            return;
        }
        KpiStaffMonthEntity kpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(staffId,month);
        if(kpiStaffMonthEntity==null){
            int n = manualService.createSingleStaffMonth(staffId,month);
            if(n==0){
                return;
            }
            kpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(staffId,month);
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

        int xks = getXks(staffEntity,month);
        int jks = getJks(staffEntity,month);
        int lessonCount = queryLessonCount(staffEntity,month);
        int validMemberCount = queryValidMemberCount(staffEntity,month);


        double exXks = 0;
        double exJks = 0;
        if(StringUtils.isNotEmpty(kpiStaffMonthEntity.getParam3())){
            exXks = Double.parseDouble(kpiStaffMonthEntity.getParam3());
        }
        if(StringUtils.isNotEmpty(kpiStaffMonthEntity.getParam4())){
            exJks = Double.parseDouble(kpiStaffMonthEntity.getParam4());
        }

        int xkl = getXkl(xks+exXks,jks+exJks,staffEntity,month);

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

        int cjs = getCjs(staffEntity,month);
        int tcs = getTcs(staffEntity,month);
        int zye = getZye(staffEntity,month);

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

        if(month.equals("201811")){
            if(StringUtils.isNotEmpty(templateId)){
                KpiStaffMonth kpiStaffMonth = kpiStaffMonthService.calculateKpiStaffMonth(kpiStaffMonthEntity);
                kpiStaffMonthEntity.setKpiScore(kpiStaffMonth.getKpiScore());
            }
            else{
                kpiStaffMonthEntity.setKpiScore("0");
            }
            kpiStaffMonthEntity.setKpiData(JSON.toJSONString(kpiTemplateEntity));
        }else{
            kpiStaffMonthEntity.setKpiScore(null);
        }
        int n = kpiStaffMonthDao.update(kpiStaffMonthEntity);
    }

    private int getXkl(double xks, double jks, StaffEntity staffEntity, String month) {
        String month1 = ut.getKpiMonth(month,-1);
        String month2= ut.getKpiMonth(month,-2);
        KpiStaffMonthEntity kpiStaffMonthEntity1 = kpiStaffMonthDao.getByIdAndMonth(staffEntity.getStaffId(),month1);
        KpiStaffMonthEntity kpiStaffMonthEntity2 = kpiStaffMonthDao.getByIdAndMonth(staffEntity.getStaffId(),month2);

        if(kpiStaffMonthEntity1!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity1.getXks())){
            xks = xks + Integer.parseInt(kpiStaffMonthEntity1.getXks());
            if(kpiStaffMonthEntity1.getParam3()!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity1.getParam3())){
                xks = xks + Double.parseDouble(kpiStaffMonthEntity1.getParam3());
            }
        }
        if(kpiStaffMonthEntity2!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity2.getXks())){
            xks = xks + Integer.parseInt(kpiStaffMonthEntity2.getXks());
            if(kpiStaffMonthEntity2.getParam3()!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity2.getParam3())){
                xks = xks + Double.parseDouble(kpiStaffMonthEntity2.getParam3());
            }
        }

        if(kpiStaffMonthEntity1!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity1.getJks())){
            jks = jks + Integer.parseInt(kpiStaffMonthEntity1.getJks());
            if(kpiStaffMonthEntity1.getParam4()!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity1.getParam4())){
                jks = jks + Double.parseDouble(kpiStaffMonthEntity1.getParam4());
            }
        }
        if(kpiStaffMonthEntity2!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity2.getJks())){
            jks = jks + Integer.parseInt(kpiStaffMonthEntity2.getJks());
            if(kpiStaffMonthEntity2.getParam4()!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity2.getParam4())){
                jks = jks + Double.parseDouble(kpiStaffMonthEntity2.getParam4());
            }
        }
        double xkl = 100;
        if(jks>0){
            xkl = 100*xks/jks;
        }
        return (int)xkl;
    }

    private int getZye(StaffEntity staffEntity, String month) {
        if(staffEntity==null){
            return 0;
        }
        String y = month.substring(0,4);
        String m = month.substring(4,6);
        String startDate = y+"-"+m+"-01";
        String endDate = y+"-"+m+"-31";
        String sql = "select * from contract where sale_staff_id = ? and type in ('新会员','续课','转介绍') and sign_date >= ? and sign_date <= ? ";
        int yye = 0;
        List data = jdbcTemplate.queryForList(sql,new Object[]{staffEntity.getStaffId(),startDate,endDate});
        for (int i = 0; i < data.size(); i++) {
            Map item = (Map)data.get(i);
            String money = item.get("money").toString();
            if(StringUtils.isNotEmpty(money)){
                yye = yye + (int)Double.parseDouble(money);
            }
        }
//        logger.info(" getZye = {} , staffId = {} , name = {} , month = {} , data.size = {} ",yye,staffEntity.getStaffId(),staffEntity.getCustname(),month,data.size());
        return yye;
    }

    private int getTcs(StaffEntity staffEntity, String month) {
        String y = month.substring(0,4);
        String m = month.substring(4,6);
        String startDate = y+"-"+m+"-01";
        String endDate = y+"-"+m+"-31";
        int count = 0;
        String sql = "select * from member where coach_staff_id = ?  ";
        String sql_card = "select * from member_card where member_id = ? and type = 'TY' and created >= ? and created <= ?  ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{staffEntity.getStaffId()});
        for (int i = 0; i < data.size(); i++) {
            Map member = (Map)data.get(i);
            String memberId = member.get("member_id").toString();
            String origin = "";
            if(null!=member.get("origin")){
                origin = member.get("origin").toString();
            }
            if(origin.indexOf("EXCEL")>=0||origin.indexOf("合同")>=0){
                continue;
            }
            List ty_cards = jdbcTemplate.queryForList(sql_card,new Object[]{memberId,startDate+" 00:00:00",endDate+" 23:59:59"});
            if(ty_cards.size()>0){
                count++;
            }
        }
        return count;
    }

    private int getCjs(StaffEntity staffEntity,  String month) {
        if(staffEntity==null){
            return 0;
        }
        String y = month.substring(0,4);
        String m = month.substring(4,6);
        String startDate = y+"-"+m+"-01";
        String endDate = y+"-"+m+"-31";
        String sql = "select * from contract where card_type in ('PT','PM') and sale_staff_id = ? and sign_date >= ? and sign_date <= ? ";
        int cjs = 0;
        List data = jdbcTemplate.queryForList(sql,new Object[]{staffEntity.getStaffId(),startDate,endDate});
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
//        logger.info(" getCjs = {} , staffId = {} , name = {} , month = {} , data.size = {} ",cjs,staffEntity.getStaffId(),staffEntity.getCustname(),month,data.size());
        return cjs;
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

        double qdzjs = 0;
        int qdhydp = 0;
        double qdhyd = 0;

        int qdzye = 0;
        int qdcjs = 0;
        int qdtcs = 0;
        List<String> staffIdList = queryStoreStaffList(storeId,y+"-"+m);
        int staffCount = staffIdList.size();
//        logger.info(" staffCount  = {}  ",staffCount);
        for (String staffId : staffIdList){
            KpiStaffMonthEntity subKpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(staffId,month);
            if(subKpiStaffMonthEntity==null){
                continue;
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
                qdjks = qdjks + (int)Double.parseDouble(subKpiStaffMonthEntity.getParam4());
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

        int qdxkl = 0;

        kpiStaffMonthEntity.setQdzye(""+qdzye);

        kpiStaffMonthEntity.setQdxks(ut.getDoubleString(qdxks));
        kpiStaffMonthEntity.setQdjks(ut.getDoubleString(qdjks));
        kpiStaffMonthEntity.setQdyxhys(""+qdvalidMemberCount);
        kpiStaffMonthEntity.setQdsjks(""+qdlessonCount);

        kpiStaffMonthEntity.setXswcl(""+xswcl);
        kpiStaffMonthEntity.setQdxkl(""+qdxkl);
        kpiStaffMonthEntity.setQdhyd(""+ut.getDoubleString(qdhyd));
        kpiStaffMonthEntity.setQdhydp(""+qdhydp);
        kpiStaffMonthEntity.setZjs(ut.getDoubleString(qdzjs));
        kpiStaffMonthEntity.setQdzjs(ut.getDoubleString(qdzjs));
        kpiStaffMonthEntity.setQdcjs(""+qdcjs);
        kpiStaffMonthEntity.setQdtcs(""+qdtcs);
        kpiStaffMonthEntity.setTczhl(""+tczhl);

        KpiStaffMonth kpiStaffMonth = kpiStaffMonthService.calculateKpiStaffMonth(kpiStaffMonthEntity);
        kpiStaffMonthEntity.setKpiScore(kpiStaffMonth.getKpiScore());

        kpiStaffMonthEntity.setKpiData("");
        int n = kpiStaffMonthDao.update(kpiStaffMonthEntity);

        qdxkl = kpiStaffMonthService.calculateQdxkl(storeId,month);
        qdzjs = kpiStaffMonthService.calculateQdzjs(storeId,month);

        List<StaffEntity> managers = staffDao.getManagerByStoreId(storeId);
        for (StaffEntity staffEntity : managers){
            KpiStaffMonthEntity item = kpiStaffMonthDao.getByIdAndMonth(staffEntity.getStaffId(),month);
            if(item==null){
                continue;
            }
            int xswcl_manager = 100;
            if(StringUtils.isNotEmpty(item.getXsmb())){
                int xsmb = Integer.parseInt(item.getXsmb());
                if(xsmb>0){
                    xswcl_manager = qdzye*100/xsmb;
                }
            }
            KpiStaffMonthEntity update = new KpiStaffMonthEntity();
            update.setStaffId(staffEntity.getStaffId());
            update.setMonth(month);
            update.setQdzye(""+qdzye);
            update.setXswcl(""+xswcl_manager);
            update.setQdxks(ut.getDoubleString(qdxks));
            update.setQdjks(ut.getDoubleString(qdjks));
            update.setQdyxhys(""+qdvalidMemberCount);
            update.setQdsjks(""+qdlessonCount);
            update.setQdxkl(""+qdxkl);
            update.setQdhyd(""+ut.getDoubleString(qdhyd));
            update.setQdhydp(""+qdhydp);
            update.setQdzjs(ut.getDoubleString(qdzjs));
            update.setQdcjs(""+qdcjs);
            update.setQdtcs(""+qdtcs);
            update.setTczhl(""+tczhl);
            kpiStaffMonthDao.update(update);
            item = kpiStaffMonthDao.getByIdAndMonth(staffEntity.getStaffId(),month);
            KpiStaffMonth kpiStaffMonthUpdate = kpiStaffMonthService.calculateKpiStaffMonth(item);
            update.setKpiScore(kpiStaffMonthUpdate.getKpiScore());
            kpiStaffMonthDao.update(update);
        }
    }

    private List queryStoreStaffList(String storeId,String month) {
        String today = ut.currentDate();
        List<String> staffIdList = new ArrayList<>();
        if(today.indexOf(month)>=0){
            String sql = "select staff_id from staff where store_id = ? and job in ('教练','店长') and status >= 0 and template_id <> '' ";
            List staffList = jdbcTemplate.queryForList(sql,new Object[]{storeId});
            for (int i = 0; i < staffList.size(); i++) {
                Map staff = (Map)staffList.get(i);
                String staff_id = staff.get("staff_id").toString();
                staffIdList.add(staff_id);
            }
        }else{
            List data = jdbcTemplate.queryForList(" select max(backup_date) backup_date from staff_his where backup_date like '%"+month+"%'  ");
            if(data.size()>0){
                Map item = (Map)data.get(0);
                String backup_date = item.get("backup_date").toString();
                String sql = "select staff_id from staff_his where store_id = ? and job in ('教练','店长') and status >= 0 and backup_date = ?  and template_id <> '' ";
                List staffList = jdbcTemplate.queryForList(sql,new Object[]{storeId,backup_date});
                for (int i = 0; i < staffList.size(); i++) {
                    Map staff = (Map)staffList.get(i);
                    String staff_id = staff.get("staff_id").toString();
                    staffIdList.add(staff_id);
                }
            }
        }
        return staffIdList;
    }

    private int getXks(StaffEntity staffEntity, String month) {
        if(staffEntity==null){
            return 0;
        }
        String y = month.substring(0,4);
        String m = month.substring(4,6);
        month = y+"-"+m;
        String sql = "select * from kpi_staff_detail where staff_id = ? and month = ? and type = 'XK' ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{staffEntity.getStaffId(),month});
//        logger.info(" getXks name = {} , month = {} , data.size = {} ",staffEntity.getCustname(),month,data.size());
        return data.size();
    }

    private int getJks(StaffEntity staffEntity, String month) {
        if(staffEntity==null){
            return 0;
        }
        String y = month.substring(0,4);
        String m = month.substring(4,6);
        month = y+"-"+m;
        String sql = "select * from kpi_staff_detail where staff_id = ? and month = ? and type in ('JK') ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{staffEntity.getStaffId(),month});
//        logger.info(" getJks name = {} , month = {} , data.size = {} ",staffEntity.getCustname(),month,data.size());
        return data.size();
    }

    /**
     * KPI计算仅统计私教次卡已经签到数量
     */
    private int queryLessonCount(StaffEntity staffEntity, String month) {
        String y = month.substring(0,4);
        String m = month.substring(4,6);
        String startDate = y+"-"+m+"-01";
        String endDate = y+"-"+m+"-31";
        if(ut.passDayByDate(ut.currentDate(),endDate)>0){
            endDate = ut.currentDate();
        }
        String sql = " select training_id,lesson_id,type,sign_time,card_type from training where staff_id = ? and lesson_date >= ? and lesson_date <= ? and show_tag = 1 and card_type in ('PT') ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{staffEntity.getStaffId(),startDate,endDate});
//        logger.info(" queryLessonCount  count = {}  ",data.size());
        return data.size();
    }

    /**
     *  有效会员数 ： 非停 非结
     */
    public int queryValidMemberCount(StaffEntity staffEntity, String month) {
//        logger.info(" queryValidMemberCount  staffEntity = {} , month = {} ",staffEntity,month);
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
        List data = jdbcTemplate.queryForList(sql,new Object[]{staffEntity.getStaffId(),endDate+" 23:59:59"});
        for (int i = 0; i < data.size(); i++) {
            Map member = (Map)data.get(i);

            count++;
        }
//        logger.info(" queryValidMemberCount tableName = {} ,  data.size() = {} , count = {} ",tableName,data.size(),count);
        return count;
    }

}


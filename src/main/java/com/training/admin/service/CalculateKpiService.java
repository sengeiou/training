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
import java.util.Map;

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
        int yyts = queryOpenDays(staffEntity.getStoreId(),month);

        int xkl = 100;
        if(jks>0){
            xkl = xks/jks;
        }

        int hyd = 0;
        if(validMemberCount>0&&yyts>0){
            hyd = lessonCount*100/(validMemberCount*yyts);
        }
//        logger.info(" hyd  = {}  ",hyd);
        if("店长".equals(staffEntity.getJob())){
            xks = 0;
            jks = 0;
            lessonCount = 0;
            validMemberCount = 0;
            hyd = 0;

            int qdxkl = 100;
            double qdzjs = 0;
            int qdhydp = 0;
            int qdhyd = 0;

            int zye = 0;
            int cjs = 0;
            int tcs = 0;
            List<String> staffIdList = queryStoreStaffList(staffEntity.getStoreId(),month);
            int staffCount = staffIdList.size();
            for (String subStaffId : staffIdList){
                KpiStaffMonthEntity subKpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(subStaffId,month);
                if(subKpiStaffMonthEntity==null){
                    continue;
                }
                if(StringUtils.isNotEmpty(subKpiStaffMonthEntity.getHydp())){
                    int hydp = Integer.parseInt(subKpiStaffMonthEntity.getHydp());
                    qdhydp = qdhydp + hydp;
                }
                if(StringUtils.isNotEmpty(subKpiStaffMonthEntity.getXks())){
                    xks = xks + Integer.parseInt(subKpiStaffMonthEntity.getXks());
                }
                if(StringUtils.isNotEmpty(subKpiStaffMonthEntity.getJks())){
                    jks = jks + Integer.parseInt(subKpiStaffMonthEntity.getJks());
                }
                if(StringUtils.isNotEmpty(subKpiStaffMonthEntity.getSjks())){
                    lessonCount = lessonCount + Integer.parseInt(subKpiStaffMonthEntity.getSjks());
                }
                if(StringUtils.isNotEmpty(subKpiStaffMonthEntity.getYxhys())){
                    validMemberCount = validMemberCount + Integer.parseInt(subKpiStaffMonthEntity.getYxhys());
                }

                if(StringUtils.isNotEmpty(subKpiStaffMonthEntity.getZjs())){
                    qdzjs = qdzjs + Integer.parseInt(subKpiStaffMonthEntity.getZjs());
                }

                zye = zye + getZye(subStaffId,month);
                cjs = cjs + getCjs(subStaffId,month);
                tcs = tcs + getTcs(subStaffId,month);
            }

            //计算店长的营业额，成交数和体测数
            zye = zye + getZye(staffId,month);
            cjs = cjs + getCjs(staffId,month);
            tcs = tcs + getTcs(staffId,month);

            if(staffCount>0){
                qdhydp = qdhydp/staffCount;
                qdzjs = qdzjs/staffCount;
            }

            xkl = 100;
            if(jks>0){
                xkl = xks/jks;
                qdxkl = xks/jks;
            }
            if(validMemberCount>0&&yyts>0){
                hyd = lessonCount*100/(validMemberCount*yyts);
                qdhyd = lessonCount*100/(validMemberCount*yyts);
            }

            int xswcl = 100;
            if(StringUtils.isNotEmpty(kpiStaffMonthEntity.getXsmb())){
                int xsmb = Integer.parseInt(kpiStaffMonthEntity.getXsmb());
                if(xsmb>0){
                    xswcl = zye*100/xsmb;
                }
            }
            int tczhl = 0;
            if(tcs>0){
                tczhl = cjs*100/tcs;
            }
            kpiStaffMonthEntity.setZye(""+zye);
            kpiStaffMonthEntity.setXswcl(""+xswcl);
            kpiStaffMonthEntity.setQdxkl(""+qdxkl);
            kpiStaffMonthEntity.setQdhyd(""+qdhyd);
            kpiStaffMonthEntity.setQdhydp(""+qdhydp);
            kpiStaffMonthEntity.setQdzjs(ut.getDoubleString(qdzjs));
            kpiStaffMonthEntity.setCjs(""+cjs);
            kpiStaffMonthEntity.setTcs(""+tcs);
            kpiStaffMonthEntity.setTczhl(""+tczhl);
        }

        kpiStaffMonthEntity.setXks(""+xks);
        kpiStaffMonthEntity.setJks(""+jks);
        kpiStaffMonthEntity.setXkl(""+xkl);
        kpiStaffMonthEntity.setSjks(""+lessonCount);
        kpiStaffMonthEntity.setYxhys(""+validMemberCount);
        kpiStaffMonthEntity.setYyts(""+yyts);
        kpiStaffMonthEntity.setHyd(""+hyd);

        if(StringUtils.isNotEmpty(templateId)){
            KpiStaffMonth kpiStaffMonth = kpiStaffMonthService.convertKpiStaffMonth(kpiStaffMonthEntity);
            kpiStaffMonthEntity.setKpiScore(kpiStaffMonth.getKpiScore());
        }
        else{
            kpiStaffMonthEntity.setKpiScore("0");
        }
        kpiStaffMonthEntity.setKpiData(JSON.toJSONString(kpiTemplateEntity));
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
        String sql = "select * from contract where coach like concat('%',?,'%') and sign_date >= ? and sign_date <= ? ";
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
        String sql = "select * from member where coach_staff_id = ? and status in (0) ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{subStaffId});
        for (int i = 0; i < data.size(); i++) {
            Map member = (Map)data.get(i);
            String memberId = member.get("member_id").toString();
            List cards = jdbcTemplate.queryForList(" select * from member_card where member_id = ? and type <> 'TY'  " ,new Object[]{memberId});
            if(cards.size()>0){
                continue;
            }
            List ty_cards = jdbcTemplate.queryForList(" select * from member_card where member_id = ? and type in ('TY') and created >= ?  and created <= ? " ,new Object[]{memberId,startDate,endDate});
//            logger.info(" cards  = {} , startDate = {} , endDate = {}  ",cards.size(),startDate,endDate);
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
        String sql = "select * from contract where coach like concat('%',?,'%') and sign_date >= ? and sign_date <= ? ";
        int cjs = 0;
        List data = jdbcTemplate.queryForList(sql,new Object[]{staffEntity.getCustname(),startDate,endDate});
        for (int i = 0; i < data.size(); i++) {
            Map item = (Map)data.get(i);
            String type = item.get("type").toString();
            if("新会员".equals(type)){
                cjs++;
            }
        }
        logger.info(" getCjs = {} , staffId = {} , name = {} , month = {} , data.size = {} ",cjs,subStaffId,staffEntity.getCustname(),month,data.size());
        return cjs;
    }

    private List queryStoreStaffList(String storeId,String month) {
        List<String> staffIdList = new ArrayList<>();
        String sql = "select * from staff where store_id = ? and job = '教练' ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{storeId});
        for (int i = 0; i < data.size(); i++) {
            Map staff = (Map)data.get(i);
            String staff_id = staff.get("staff_id").toString();
            int count = queryValidMemberCount(staff_id,month);
            if(count>0){
                staffIdList.add(staff_id);
            }
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
        String sql = "select * from contract where coach like concat('%',?,'%') and sign_date >= ? and sign_date <= ? ";
        int xks = 0;
        List data = jdbcTemplate.queryForList(sql,new Object[]{staffEntity.getCustname(),startDate,endDate});
        logger.info(" getXks  name = {} , startDate = {} , endDate = {} , data.size = {} ",staffEntity.getCustname(),startDate,endDate,data.size());
        for (int i = 0; i < data.size(); i++) {
            Map item = (Map)data.get(i);
            String type = item.get("type").toString();
            if("续课".equals(type)){
                xks++;
            }
        }
        logger.info(" getXks = {} , staffId = {} , name = {} , month = {} , data.size = {} ",xks,staffId,staffEntity.getCustname(),month,data.size());
        return xks;
    }

    private int getJks(String staffId, String month) {


        return 0;
    }

    private int queryLessonCount(String staffId, String month) {
        StaffEntity staffEntity = staffDao.getById(staffId);
        if(staffEntity==null){
            return 0;
        }

        if(StringUtils.isEmpty(staffEntity.getOpenId())){
            return 0;
        }

        MemberEntity coach = memberDao.getByOpenId(staffEntity.getOpenId());
        if(coach==null){
            return 0;
        }

        String y = month.substring(0,4);
        String m = month.substring(4,6);
        String startDate = y+"-"+m+"-01";
        String endDate = y+"-"+m+"-31";

        String sql = "select training_id,lesson_id from training where coach_id = ? and type in ('P') and lesson_date >= ? and lesson_date <= ? ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{coach.getMemberId(),startDate,endDate});

        return data.size();
    }

    public int queryValidMemberCount(String staffId, String month) {
        String y = month.substring(0,4);
        String m = month.substring(4,6);
        String startDate = y+"-"+m+"-01";
        String endDate = y+"-"+m+"-31";
        int count = 0;
        String sql = "select * from member where coach_staff_id = ? and status in (0) ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{staffId});
        for (int i = 0; i < data.size(); i++) {
            Map member = (Map)data.get(i);
            String memberId = member.get("member_id").toString();
            List cards = jdbcTemplate.queryForList("select * from member_card where member_id = ? and type in ('PT','PM') and end_date >= ? " ,new Object[]{memberId,startDate});
//            logger.info(" cards  = {} , startDate = {} , endDate = {}  ",cards.size(),startDate,endDate);
            if(cards.size()>0){
                count++;
            }
        }
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


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
        logger.info(" hyd  = {}  ",hyd);

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


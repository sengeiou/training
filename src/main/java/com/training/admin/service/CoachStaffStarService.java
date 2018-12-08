package com.training.admin.service;

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

import java.util.List;
import java.util.Map;

/**
 * staff 核心业务操作类
 * Created by huai23 on 2018-05-26 13:55:30.
 */ 
@Service
public class CoachStaffStarService {

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

    public String calculateStaffStar(String staffId, String month) {
        StaffEntity staffEntity = staffDao.getById(staffId);
        if(!staffEntity.getJob().equals("教练")){
            return "非教练员工没有星级";
        }
        staffEntity.setRemark("");
        logger.info(" calculateStaffStar   staffName = {} , month = {} , star = {}   ",staffEntity.getCustname(),month,staffEntity.getStar());
        String month1 = ut.getKpiMonth(month,-1);
        KpiStaffMonthEntity kpiStaffMonthEntity1 = kpiStaffMonthDao.getByIdAndMonth(staffEntity.getStaffId(),month1);
        int star = 0;
        if(kpiStaffMonthEntity1!=null && StringUtils.isNotEmpty(kpiStaffMonthEntity1.getParam1())){
            star = Integer.parseInt(kpiStaffMonthEntity1.getParam1());
        }
        int n = 0;
        if(star<2){
            n = calculateStar1(staffEntity,month);
        }else if(star==2){
            n = calculateStar2(staffEntity,month);
        }if(star==3){
            n = calculateStar3(staffEntity,month);
        }if(star==4){
            n = calculateStar4(staffEntity,month);
        }
        int newStar = star + n;
        logger.info(" calculateStaffStar   staffName = {} , month = {} , star = {} , new Star = {} , n = {} ",staffEntity.getCustname(),month,star,newStar,n);
        int i = jdbcTemplate.update(" update kpi_staff_month set param1 = ? , remark = ? where staff_id = ?  and month = ? ",new Object[]{newStar,staffEntity.getRemark(),staffId,month});
        int j = jdbcTemplate.update(" update staff set star = ? where staff_id = ?  ",new Object[]{newStar,staffId});
        return "";
    }

    private int calculateStar1(StaffEntity staffEntity, String month) {
        String msg = "";
        String month1 = ut.getKpiMonth(month,-1);
        KpiStaffMonthEntity kpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(staffEntity.getStaffId(),month);
        KpiStaffMonthEntity kpiStaffMonthEntity1 = kpiStaffMonthDao.getByIdAndMonth(staffEntity.getStaffId(),month1);
        if(kpiStaffMonthEntity==null || kpiStaffMonthEntity1==null || StringUtils.isEmpty(kpiStaffMonthEntity.getKpiScore()) || StringUtils.isEmpty(kpiStaffMonthEntity1.getKpiScore())){
            return 0;
        }
        double score = Double.parseDouble(kpiStaffMonthEntity.getKpiScore());
        double score_1 = Double.parseDouble(kpiStaffMonthEntity1.getKpiScore());
        if( score>= 90 &&score_1>= 90){
            msg = "上个月（"+ut.getKpiMonth(month,-1)+"）star=1星，上个月（"+ut.getKpiMonth(month,-1)+"）kpi("+score_1+")>=90,本月（"+month+"）kpi("+ut.getDoubleString(score)+")>=90，星级+1";
            logger.info(msg);
            staffEntity.setRemark(msg);
            return 1;
        }
        msg = "上个月（"+ut.getKpiMonth(month,-1)+"）star=1星，上个月（"+ut.getKpiMonth(month,-1)+"）kpi("+score_1+"),本月（"+month+"）kpi("+ut.getDoubleString(score)+")，没有达到全部>=90，星级不变";
        logger.info(msg);
        staffEntity.setRemark(msg);
        return 0;
    }

    private int calculateStar2(StaffEntity staffEntity, String month) {
        String msg = "";
        KpiStaffMonthEntity kpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(staffEntity.getStaffId(),month);
        KpiStaffMonthEntity kpiStaffMonthEntity_1 = kpiStaffMonthDao.getByIdAndMonth(staffEntity.getStaffId(),ut.getKpiMonth(month,-1));
        KpiStaffMonthEntity kpiStaffMonthEntity_2 = kpiStaffMonthDao.getByIdAndMonth(staffEntity.getStaffId(),ut.getKpiMonth(month,-2));
        double score = Double.parseDouble(kpiStaffMonthEntity.getKpiScore());
        double score_1 = Double.parseDouble(kpiStaffMonthEntity_1.getKpiScore());
        int star_2 = 1;
        if(kpiStaffMonthEntity_2!=null && StringUtils.isNotEmpty(kpiStaffMonthEntity_2.getParam1())){
            star_2 = Integer.parseInt(kpiStaffMonthEntity_2.getParam1());
        }
        if(star_2==1){
            if(score>=80){
                msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star=1星，上个月（"+ut.getKpiMonth(month,-1)+"）star=2星，本月（"+month+"）kpi("+ut.getDoubleString(score)+")>=80，星级保持不变";
                logger.info(msg);
                staffEntity.setRemark(msg);
                return 0;
            }
            if(score<80){
                msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star=1星，上个月（"+ut.getKpiMonth(month,-1)+"）star=2星，本月（"+month+"）kpi("+ut.getDoubleString(score)+")<80，星级-1";
                logger.info(msg);
                staffEntity.setRemark(msg);
                return 0;
            }
        }
        if(star_2==2){
            if(score_1>=90){
                if(score>=90){
                    int count = getValidMemberCountByMonth(staffEntity,month);
                    if(count>=15){
                        msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，上个月（"+ut.getKpiMonth(month,-1)+"）kpi("+ut.getDoubleString(score_1)+")>=90，本月（"+month+"）kpi("+ut.getDoubleString(score)+")>=90，月末有效会员数("+count+")>=15,星级+1";
                        logger.info(msg);
                        staffEntity.setRemark(msg);
                        return 1;
                    }
                    if(count<15){
                        msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，本月（"+month+"）kpi("+ut.getDoubleString(score)+")>=90，月末有效会员数("+count+")<15,星级不变";
                        logger.info(msg);
                        staffEntity.setRemark(msg);
                        return 0;
                    }
                }
                if(score>=80 && score<90){
                    msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，上个月（"+ut.getKpiMonth(month,-1)+"）kpi("+ut.getDoubleString(score_1)+")>=90，本月（"+month+"）kpi("+ut.getDoubleString(score)+")>=80 && <90 ，星级不变";
                    logger.info(msg);
                    staffEntity.setRemark(msg);
                    return 0;
                }
                if(score<80){
                    msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，上个月（"+ut.getKpiMonth(month,-1)+"）kpi("+ut.getDoubleString(score_1)+")>=90，本月（"+month+"）kpi("+ut.getDoubleString(score)+")<80 ，星级-1";
                    logger.info(msg);
                    staffEntity.setRemark(msg);
                    return -1;
                }
            }
            if(score_1<90){
                if(score>=80){
                    msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，上个月（"+ut.getKpiMonth(month,-1)+"）kpi("+ut.getDoubleString(score_1)+")<90，本月（"+month+"）kpi("+ut.getDoubleString(score)+")>=80 ，星级不变";
                    logger.info(msg);
                    staffEntity.setRemark(msg);
                    return 0;
                }
                if(score<80){
                    msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，上个月（"+ut.getKpiMonth(month,-1)+"）kpi("+ut.getDoubleString(score_1)+")<90，本月（"+month+"）kpi("+ut.getDoubleString(score)+")<80 ，星级-1";
                    logger.info(msg);
                    staffEntity.setRemark(msg);
                    return -1;
                }
            }
        }
        if(star_2==3){
            if(score>=80){
                msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，本月（"+month+"）kpi("+ut.getDoubleString(score)+")>=80 ，星级不变";
                logger.info(msg);
                staffEntity.setRemark(msg);
                return 0;
            }
            if(score<80){
                msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，本月（"+month+"）kpi("+ut.getDoubleString(score)+")<80 ，星级-1";
                logger.info(msg);
                staffEntity.setRemark(msg);
                return -1;
            }
        }
        return 0;
    }

    private int calculateStar3(StaffEntity staffEntity, String month) {
        String msg = "";
        KpiStaffMonthEntity kpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(staffEntity.getStaffId(),month);
        KpiStaffMonthEntity kpiStaffMonthEntity_1 = kpiStaffMonthDao.getByIdAndMonth(staffEntity.getStaffId(),ut.getKpiMonth(month,-1));
        KpiStaffMonthEntity kpiStaffMonthEntity_2 = kpiStaffMonthDao.getByIdAndMonth(staffEntity.getStaffId(),ut.getKpiMonth(month,-2));
        double score = Double.parseDouble(kpiStaffMonthEntity.getKpiScore());
        double score_1 = Double.parseDouble(kpiStaffMonthEntity_1.getKpiScore());
        double score_2 = Double.parseDouble(kpiStaffMonthEntity_2.getKpiScore());
        int star_2 = 2;
        if(kpiStaffMonthEntity_2!=null && StringUtils.isNotEmpty(kpiStaffMonthEntity_2.getParam1())){
            star_2 = Integer.parseInt(kpiStaffMonthEntity_2.getParam1());
        }
        if(star_2==2){
            if(score>=80){
                int count = getValidMemberCountByMonth(staffEntity,month);
                if(count>=12){
                    msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，上个月（"+ut.getKpiMonth(month,-1)+"）star=3星，本月（"+month+"）kpi("+ut.getDoubleString(score)+")>=80，，月末有效会员数("+count+")>=12,星级保持不变";
                    logger.info(msg);
                    staffEntity.setRemark(msg);
                    return 0;
                }
                if(count<12){
                    msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，上个月（"+ut.getKpiMonth(month,-1)+"）star=3星，本月（"+month+"）kpi("+ut.getDoubleString(score)+")>=80，，月末有效会员数("+count+")<12,星级-1";
                    logger.info(msg);
                    staffEntity.setRemark(msg);
                    return -1;
                }
            }
            if(score<80){
                msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，上个月（"+ut.getKpiMonth(month,-1)+"）star=3星，本月（"+month+"）kpi("+ut.getDoubleString(score)+")<80，星级-1";
                logger.info(msg);
                staffEntity.setRemark(msg);
                return -1;
            }
        }
        if(star_2==3){
            if(score_1>=90){
                if(score>=90){
                    int count = getValidMemberCountByMonth(staffEntity,month);
                    if(count>=18){
                        msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，上个月（"+ut.getKpiMonth(month,-1)+"）kpi("+ut.getDoubleString(score_1)+")>=90，本月（"+month+"）kpi("+ut.getDoubleString(score)+")>=90，月末有效会员数("+count+")>=18,星级+1";
                        logger.info(msg);
                        staffEntity.setRemark(msg);
                        return 1;
                    }
                    if(count<18&&count>=12){
                        msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，本月（"+month+"）kpi("+ut.getDoubleString(score)+")>=90，月末有效会员数("+count+")<18 & >=12 ,星级不变";
                        logger.info(msg);
                        staffEntity.setRemark(msg);
                        return 0;
                    }
                    if(count<12){
                        msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，本月（"+month+"）kpi("+ut.getDoubleString(score)+")>=90，月末有效会员数("+count+")<12,星级不变";
                        logger.info(msg);
                        staffEntity.setRemark(msg);
                        return -1;
                    }
                }
                if(score>=80 && score<90){
                    int count = getValidMemberCountByMonth(staffEntity,month);
                    if(count>=12){
                        msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，上个月（"+ut.getKpiMonth(month,-1)+"）star=3星，本月（"+month+"）kpi("+ut.getDoubleString(score)+")>=80 & <90，月末有效会员数("+count+")>=12,星级保持不变";
                        logger.info(msg);
                        staffEntity.setRemark(msg);
                        return 0;
                    }
                    if(count<12){
                        msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，上个月（"+ut.getKpiMonth(month,-1)+"）star=3星，本月（"+month+"）kpi("+ut.getDoubleString(score)+")>=80 & <90，月末有效会员数("+count+")<12,星级-1";
                        logger.info(msg);
                        staffEntity.setRemark(msg);
                        return -1;
                    }
                }
                if(score<80){
                    msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，上个月（"+ut.getKpiMonth(month,-1)+"）kpi("+ut.getDoubleString(score_1)+")>=90，本月（"+month+"）kpi("+ut.getDoubleString(score)+")<80 ，星级-1";
                    logger.info(msg);
                    staffEntity.setRemark(msg);
                    return -1;
                }
            }
            if(score_1<90){
                if(score>=80){
                    int count = getValidMemberCountByMonth(staffEntity,month);
                    if(count>=12){
                        msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，上个月（"+ut.getKpiMonth(month,-1)+"）star=3星，本月（"+month+"）kpi("+ut.getDoubleString(score)+")>=80，，月末有效会员数("+count+")>=12,星级保持不变";
                        logger.info(msg);
                        staffEntity.setRemark(msg);
                        return 0;
                    }
                    if(count<12){
                        msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，上个月（"+ut.getKpiMonth(month,-1)+"）star=3星，本月（"+month+"）kpi("+ut.getDoubleString(score)+")>=80，，月末有效会员数("+count+")<12,星级-1";
                        logger.info(msg);
                        staffEntity.setRemark(msg);
                        return -1;
                    }
                }
                if(score<80){
                    msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，上个月（"+ut.getKpiMonth(month,-1)+"）star=3星，本月（"+month+"）kpi("+ut.getDoubleString(score)+")<80，星级-1";
                    logger.info(msg);
                    staffEntity.setRemark(msg);
                    return -1;
                }
            }
        }
        if(star_2==4){
            if(score>=80){
                int count = getValidMemberCountByMonth(staffEntity,month);
                if(count>=12){
                    msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，上个月（"+ut.getKpiMonth(month,-1)+"）star=4星，本月（"+month+"）kpi("+ut.getDoubleString(score)+")>=80，，月末有效会员数("+count+")>=12,星级保持不变";
                    logger.info(msg);
                    staffEntity.setRemark(msg);
                    return 0;
                }
                if(count<12){
                    msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，上个月（"+ut.getKpiMonth(month,-1)+"）star=4星，本月（"+month+"）kpi("+ut.getDoubleString(score)+")>=80，，月末有效会员数("+count+")<12,星级-1";
                    logger.info(msg);
                    staffEntity.setRemark(msg);
                    return 0;
                }
            }
            if(score<80){
                msg = "上上个月（"+ut.getKpiMonth(month,-2)+"）star="+star_2+"星，上个月（"+ut.getKpiMonth(month,-1)+"）star=4星，本月（"+month+"）kpi("+ut.getDoubleString(score)+")<80，星级-1";
                logger.info(msg);
                staffEntity.setRemark(msg);
                return 0;
            }
        }
        return 0;
    }

    private int calculateStar4(StaffEntity staffEntity, String month) {
        String msg = "";
        KpiStaffMonthEntity kpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(staffEntity.getStaffId(),month);
        double score = Double.parseDouble(kpiStaffMonthEntity.getKpiScore());
        if(score<85){
            msg = "本月（"+month+"）kpi("+ut.getDoubleString(score)+")<85，星级-1";
            logger.info(msg);
            staffEntity.setRemark(msg);
            return -1;
        }
        int count = getValidMemberCountByMonth(staffEntity,month);
        if(count<15){
            msg = "本月（"+month+"）kpi("+ut.getDoubleString(score)+")>85，月末有效会员数("+count+")<15,星级-1";
            logger.info(msg);
            staffEntity.setRemark(msg);
            return -1;
        }
        msg = "本月（"+month+"）kpi("+ut.getDoubleString(score)+")>85，月末有效会员数("+count+")>=15,星级不变";
        logger.info(msg);
        staffEntity.setRemark(msg);
        return 0;
    }

    /**
     * 查询月末有效会员数
     * @param staffEntity
     * @param month
     * @return
     */
    private int getValidMemberCountByMonth(StaffEntity staffEntity, String month){
        String y = month.substring(0,4);
        String m = month.substring(4,6);
        String tableName = "member_his_"+m;
        List data = jdbcTemplate.queryForList("select max(backup_date) backup_date from "+tableName);
        if(data.size()>0){
            Map item = (Map)data.get(0);
            String backup_date = item.get("backup_date").toString();
            List yx_members = jdbcTemplate.queryForList("select * from "+tableName+" where coach_staff_id = ? and backup_date = ? and status = 1  ",new Object[]{staffEntity.getStaffId(),backup_date});
            return yx_members.size();
        }
        return 0;
    }

}


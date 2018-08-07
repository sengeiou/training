package com.training.admin.service;

import com.alibaba.fastjson.JSON;
import com.training.common.CardTypeEnum;
import com.training.common.PageRequest;
import com.training.dao.*;
import com.training.domain.KpiStaffMonth;
import com.training.domain.Member;
import com.training.domain.MemberCard;
import com.training.entity.*;
import com.training.service.CardService;
import com.training.service.KpiStaffMonthService;
import com.training.service.MemberService;
import com.training.service.SysLogService;
import com.training.util.ExcelUtil;
import com.training.util.IDUtils;
import com.training.util.ResponseUtil;
import com.training.util.ut;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class ManualService {

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
    private KpiStaffMonthService kpiStaffMonthService;

    @Autowired
    private KpiStaffMonthDao kpiStaffMonthDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createStaffMonth(String month) {
        StaffQuery query = new StaffQuery();
        PageRequest page = new PageRequest();
        page.setPageSize(1000);
        List<StaffEntity> staffList = staffDao.find(query,page);
        for (StaffEntity staffEntity : staffList){
            KpiStaffMonthEntity kpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(staffEntity.getStaffId(),month);
            if(kpiStaffMonthEntity==null){
                kpiStaffMonthEntity = new KpiStaffMonthEntity();
                kpiStaffMonthEntity.setStaffId(staffEntity.getStaffId());
                kpiStaffMonthEntity.setStaffName(staffEntity.getCustname());
                kpiStaffMonthEntity.setStoreId(staffEntity.getStoreId());
                kpiStaffMonthEntity.setYear(month.substring(0,4));
                kpiStaffMonthEntity.setMonth(month);
                kpiStaffMonthEntity.setKpiScore("0");
                kpiStaffMonthEntity.setType("");
                if("教练".equals(staffEntity.getJob())){
                    kpiStaffMonthEntity.setType("JL");
                }
                if("店长".equals(staffEntity.getJob())){
                    kpiStaffMonthEntity.setType("DZ");
                }
                kpiStaffMonthDao.add(kpiStaffMonthEntity);
            }
        }
    }

    public void trainingExcel(String startDate, String endDate) {
        List data = jdbcTemplate.queryForList("select * from training where lesson_date >= ? and lesson_date <= ? and status = 0 " +
                " order by coach_id , lesson_date ",new Object[]{startDate,endDate});
        List<List<String>> excelData = new ArrayList<>();
        List<String> titleRow = new ArrayList();
        titleRow.add("门店");
        titleRow.add("场地");
        titleRow.add("课程日期");
        titleRow.add("课程开始时间");
        titleRow.add("课程结束时间");
        titleRow.add("课程名");
        titleRow.add("教练");
        titleRow.add("会员");
        titleRow.add("预约人数");
        titleRow.add("会员卡名称");
        titleRow.add("耗课数量");
        titleRow.add("实收金额");
        titleRow.add("合同金额");
        titleRow.add("购买课程节数");
        titleRow.add("预约状态");
        titleRow.add("签到时间");
        System.out.println(data.size());
        excelData.add(titleRow);
        for(Object training : data){
            try {
                List<String> row = new ArrayList();
                training = (Map)training;
                String memberId = ((Map) training).get("member_id").toString();
                String coachId = ((Map) training).get("coach_id").toString();
                String lesson_date = ((Map) training).get("lesson_date").toString();
                String start_hour = ((Map) training).get("start_hour").toString();
                String end_hour = ((Map) training).get("end_hour").toString();
                String card_no = ((Map) training).get("card_no").toString();
                String title = ((Map) training).get("title").toString();
                String type = ((Map) training).get("type").toString();
                String card_type = ((Map) training).get("card_type").toString();
                String sign_time = ((Map) training).get("sign_time").toString();

                MemberEntity member = memberDao.getById(memberId);
                if(member==null){
                    logger.error(" member==null ,  "+ JSON.toJSONString(training));
                    continue;
                }
                MemberEntity coach = memberDao.getById(coachId);

                if(coach==null){
                    logger.error(" coach==null ,  "+ JSON.toJSONString(training));
                    continue;
                }

                StaffEntity staffEntity = staffDao.getByOpenId(coach.getOpenId());

                if(staffEntity==null){
                    logger.error(" staffEntity==null ,  "+ JSON.toJSONString(training));
                    continue;
                }

                StoreEntity storeEntity = storeDao.getById(staffEntity.getStoreId());
                MemberCardEntity memberCardEntity = memberCardDao.getById(card_no);

                if(memberCardEntity==null){
                    logger.error(" memberCardEntity==null ,  "+ JSON.toJSONString(training));
                    continue;
                }

                memberCardEntity.getMoney();
                double price =  Double.parseDouble(memberCardEntity.getMoney())/memberCardEntity.getTotal();
                row.add(storeEntity.getName());
                row.add("");
                row.add(lesson_date);
                row.add(start_hour);
                row.add(end_hour);
                row.add(title);
                row.add(staffEntity.getCustname());
                row.add(member.getName());
                row.add("1");
                row.add(CardTypeEnum.getEnumByKey(card_type).getDesc());
                row.add("1");
                row.add(ut.getDoubleString(price));
                row.add(memberCardEntity.getMoney());
                row.add(""+memberCardEntity.getTotal());
                row.add("已完成");
                row.add(sign_time);
                excelData.add(row);
            }catch (Exception e){
                logger.error(" training = "+ JSON.toJSONString(training),e);
            }
        }
        ExcelUtil.writeExcel(excelData,"C://training.xls");
    }

}


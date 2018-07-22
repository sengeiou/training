package com.training.admin.service;

import com.training.common.CardTypeEnum;
import com.training.common.PageRequest;
import com.training.dao.*;
import com.training.domain.KpiStaffMonth;
import com.training.domain.MemberCard;
import com.training.entity.*;
import com.training.service.CardService;
import com.training.service.KpiStaffMonthService;
import com.training.service.MemberService;
import com.training.service.SysLogService;
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

import java.util.List;

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

}


package com.training.admin.service;

import com.training.common.PageRequest;
import com.training.dao.*;
import com.training.entity.KpiStaffMonthEntity;
import com.training.entity.StaffEntity;
import com.training.entity.StaffQuery;
import com.training.service.CardService;
import com.training.service.KpiStaffMonthService;
import com.training.service.MemberService;
import com.training.service.SysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private KpiStaffMonthService kpiStaffMonthService;

    @Autowired
    private KpiStaffMonthDao kpiStaffMonthDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void calculateStaffKpi(String staffId,String month) {





    }

}


package com.training.admin.service;

import com.training.common.CardTypeEnum;
import com.training.common.PageRequest;
import com.training.dao.*;
import com.training.domain.MemberCard;
import com.training.entity.MemberCardEntity;
import com.training.entity.MemberCardQuery;
import com.training.entity.MemberEntity;
import com.training.entity.StaffEntity;
import com.training.service.CardService;
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
public class BackupService {

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
    private JdbcTemplate jdbcTemplate;

    public void backupMember() {
        String backupDate = ut.currentDate();
        String tableName = "member_his_"+ut.currentMonth();
        System.out.println(" backupMember  tableName = "+tableName);
        String sql = " INSERT into member_his_201809  select null, '"+backupDate+"',a.member_id,IFNULL(b.store_id,''),a.phone,a.coach_staff_id,a.training_hours,a.open_id,a.status ,a.created,a.modified from member a LEFT JOIN staff b on a.coach_staff_id = b.staff_id ";
        try{
            int n = jdbcTemplate.update(sql);
            System.out.println(" backupMember  n = "+n);
        }catch (Exception e){
            System.out.println(" backupMember  ERROR : "+e.getMessage());
//            logger.error(" backupMember  ERROR : {}" , e.getMessage(),e);
        }
    }

    public void backupStaff() {
        String backupDate = ut.currentDate();
        String sql = "  INSERT into staff_his  select null,staff_id,store_id,role_id,username,`password`,custname,email,phone,job,image,open_id,union_id,template_id,feature,`status`,rel_id,created,modified, '"+backupDate+"' from staff  ";
        try{
            int n = jdbcTemplate.update(sql);
            System.out.println(" backupStaff  n = "+n);
        }catch (Exception e){
            System.out.println(" backupStaff  ERROR : "+e.getMessage());
//            logger.error(" backupStaff  ERROR : {}" , e.getMessage(),e);
        }
    }

}


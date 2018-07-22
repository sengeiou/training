package com.training.service;

import com.training.dao.StoreDao;
import com.training.dao.TrainingDao;
import com.training.domain.Staff;
import com.training.entity.StoreEntity;
import com.training.util.ut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * training 核心业务操作类
 * Created by huai23 on 2018-05-26 17:09:14.
 */ 
@Service
public class HeroListService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TrainingDao trainingDao;

    @Autowired
    private MemberService memberService;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private StaffService staffService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Staff> lessonList() {
        List<Staff> staffList = new ArrayList<>();
        List data = jdbcTemplate.queryForList(" select * from kpi_staff_month where month = ? order by sjks desc limit 0,10 ",new Object[]{ut.currentFullMonth().replace("-","")});
        for (int i = 0; i < data.size(); i++) {
            Map item = (Map)data.get(i);
            Staff staff = staffService.getById(item.get("staff_id").toString());
            StoreEntity storeEntity = storeDao.getById(staff.getStoreId());
            staff.setStoreName(storeEntity.getName());
            staff.setHeroNumber(item.get("sjks").toString());
            staffList.add(staff);
        }
        return staffList;
    }

    public List<Staff> moneyList() {
        List<Staff> staffList = new ArrayList<>();
        List data = jdbcTemplate.queryForList(" select * from kpi_staff_month where month = ? order by xks desc limit 0,10 ",new Object[]{ut.currentFullMonth().replace("-","")});
        for (int i = 0; i < data.size(); i++) {
            Map item = (Map)data.get(i);
            Staff staff = staffService.getById(item.get("staff_id").toString());
            StoreEntity storeEntity = storeDao.getById(staff.getStoreId());
            staff.setStoreName(storeEntity.getName());
            staff.setHeroNumber(item.get("xks").toString());
            staffList.add(staff);
        }
        return staffList;
    }

    public List<Staff> activeRateList() {
        List<Staff> staffList = new ArrayList<>();
        List data = jdbcTemplate.queryForList(" select * from kpi_staff_month where month = ? order by hyd desc limit 0,10 ",new Object[]{ut.currentFullMonth().replace("-","")});
        for (int i = 0; i < data.size(); i++) {
            Map item = (Map)data.get(i);
            Staff staff = staffService.getById(item.get("staff_id").toString());
            StoreEntity storeEntity = storeDao.getById(staff.getStoreId());
            staff.setStoreName(storeEntity.getName());
            staff.setHeroNumber(item.get("hyd").toString());
            staffList.add(staff);
        }
        return staffList;
    }

    public List<Staff> introduceList() {
        List<Staff> staffList = new ArrayList<>();
        List data = jdbcTemplate.queryForList(" select * from kpi_staff_month where month = ? order by zjs desc limit 0,10 ",new Object[]{ut.currentFullMonth().replace("-","")});
        for (int i = 0; i < data.size(); i++) {
            Map item = (Map)data.get(i);
            Staff staff = staffService.getById(item.get("staff_id").toString());
            StoreEntity storeEntity = storeDao.getById(staff.getStoreId());
            staff.setStoreName(storeEntity.getName());
            staff.setHeroNumber(item.get("zjs").toString());
            staffList.add(staff);
        }
        return staffList;
    }

}


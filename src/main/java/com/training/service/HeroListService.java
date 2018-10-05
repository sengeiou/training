package com.training.service;

import com.training.common.PageRequest;
import com.training.dao.StaffDao;
import com.training.dao.StoreDao;
import com.training.dao.TrainingDao;
import com.training.domain.Staff;
import com.training.entity.StaffEntity;
import com.training.entity.StaffQuery;
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
    private StaffDao staffDao;

    @Autowired
    private StaffService staffService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Staff> lessonList() {
        List<Staff> staffList = new ArrayList<>();
        String sql = "select staff_id ,  count(1) sjks from training where lesson_date >= ? and lesson_date <= ?  group by staff_id order by sjks desc limit 0,10";
        List data = jdbcTemplate.queryForList(sql,new Object[]{ut.firstDayOfMonth(),ut.lastDayOfMonth()});
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
        String sql = "select salesman ,  sum(money) total from contract where sign_date >= ? and type = '续课' group by salesman order by total desc limit 0,10 ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{ut.firstDayOfMonth()});
        for (int i = 0; i < data.size(); i++) {
            Map item = (Map)data.get(i);
            String staffName = item.get("salesman").toString();
            staffName = staffName.replaceAll("（","(").replaceAll("）",")");
            if(staffName.indexOf("(")>0){
                staffName = staffName.substring(0,staffName.indexOf("("));
            }
            StaffQuery query = new StaffQuery();
            query.setCustname(staffName);
            PageRequest page = new PageRequest();
            page.setPageSize(1000);
            List<StaffEntity> staffs = staffDao.find(query,new PageRequest());
            StaffEntity staffEntity = null;
            for (StaffEntity staff : staffs){
                if(staff.getCustname().trim().equals(staffName)){
                    staffEntity = staff;
                    break;
                }
            }
            Staff staff = new Staff();
            staff.setCustname(staffName);
            if(staffEntity==null){
                staff.setStoreName(" ");
            }else{
                StoreEntity storeEntity = storeDao.getById(staff.getStoreId());
                staff.setStoreName(storeEntity.getName());
            }
            staff.setHeroNumber(item.get("total").toString());
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


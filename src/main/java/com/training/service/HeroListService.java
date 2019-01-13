package com.training.service;

import com.training.common.HeroListTypeEnum;
import com.training.common.PageRequest;
import com.training.dao.HeroListDao;
import com.training.dao.StaffDao;
import com.training.dao.StoreDao;
import com.training.dao.TrainingDao;
import com.training.domain.Staff;
import com.training.entity.*;
import com.training.util.ut;
import org.apache.commons.collections.CollectionUtils;
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
    private HeroListDao heroListDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Staff> lessonList() {
        List<Staff> staffList = new ArrayList<>();
        String sql = "select staff_id ,  count(1) sjks from training where lesson_date >= ? and lesson_date <= ? and show_tag = 1 group by staff_id order by sjks desc limit 0,30";
        List data = jdbcTemplate.queryForList(sql,new Object[]{ut.firstDayOfMonth(),ut.currentDate(-1)});
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
        String sql = "select salesman ,  sum(money) total from contract where sign_date >= ?  and sign_date <= ? and type = '续课' group by salesman order by total desc limit 0,30 ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{ut.firstDayOfMonth(),ut.currentDate(-1)});
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
                staff.setImage(staffEntity.getImage());
                StoreEntity storeEntity = storeDao.getById(staffEntity.getStoreId());
                staff.setStoreName(storeEntity.getName());
            }
            staff.setHeroNumber(item.get("total").toString());
            staffList.add(staff);
        }
        return staffList;
    }

    public List<Staff> activeRateList() {
        List<Staff> staffList = new ArrayList<>();
        List data = jdbcTemplate.queryForList(" select * from kpi_staff_month where month = ? order by hyd+0 desc limit 0,20 ",new Object[]{ut.currentFullMonth().replace("-","")});
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
        List data = jdbcTemplate.queryForList(" select * from kpi_staff_month where month = ? order by zjs+0 desc limit 0,10 ",new Object[]{ut.currentFullMonth().replace("-","")});
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

    public String backupHeroList(String day) {
        List<Staff> lesson = this.lessonList();
        if(CollectionUtils.isNotEmpty(lesson)){
            jdbcTemplate.update(" delete from hero_list where hero_date = ? and type = ? ",new Object[]{day,HeroListTypeEnum.LC.getKey()});
            int sort = 1;
            for (Staff staff : lesson){
                HeroListEntity heroListEntity = new HeroListEntity();
                heroListEntity.setType(HeroListTypeEnum.LC.getKey());
                heroListEntity.setHeroDate(day);
                heroListEntity.setSort(sort);
                heroListEntity.setStaffId(staff.getStaffId());
                heroListEntity.setStaffName(staff.getCustname());
                heroListEntity.setImage(staff.getImage());
                heroListEntity.setStoreId(staff.getStoreId());
                heroListEntity.setStoreName(staff.getStoreName());
                heroListEntity.setValue(staff.getHeroNumber());
                heroListDao.add(heroListEntity);
                sort++;
            }
        }

        List<Staff> money = this.lessonList();
        if(CollectionUtils.isNotEmpty(money)){
            jdbcTemplate.update(" delete from hero_list where hero_date = ? and type = ? ",new Object[]{day,HeroListTypeEnum.XK.getKey()});
            int sort = 1;
            for (Staff staff : money){
                HeroListEntity heroListEntity = new HeroListEntity();
                heroListEntity.setType(HeroListTypeEnum.XK.getKey());
                heroListEntity.setHeroDate(day);
                heroListEntity.setSort(sort);
                heroListEntity.setStaffId(staff.getStaffId());
                heroListEntity.setStaffName(staff.getCustname());
                heroListEntity.setImage(staff.getImage());
                heroListEntity.setStoreId(staff.getStoreId());
                heroListEntity.setStoreName(staff.getStoreName());
                heroListEntity.setValue(staff.getHeroNumber());
                heroListDao.add(heroListEntity);
                sort++;
            }
        }

        List<Staff> hyd = this.activeRateList();
        if(CollectionUtils.isNotEmpty(money)){
            jdbcTemplate.update(" delete from hero_list where hero_date = ? and type = ? ",new Object[]{day,HeroListTypeEnum.HYD.getKey()});
            int sort = 1;
            for (Staff staff : hyd){
                HeroListEntity heroListEntity = new HeroListEntity();
                heroListEntity.setType(HeroListTypeEnum.HYD.getKey());
                heroListEntity.setHeroDate(day);
                heroListEntity.setSort(sort);
                heroListEntity.setStaffId(staff.getStaffId());
                heroListEntity.setStaffName(staff.getCustname());
                heroListEntity.setImage(staff.getImage());
                heroListEntity.setStoreId(staff.getStoreId());
                heroListEntity.setStoreName(staff.getStoreName());
                heroListEntity.setValue(staff.getHeroNumber());
                heroListDao.add(heroListEntity);
                sort++;
            }
        }
        return "更新"+day+"英雄榜成功";
    }


    public List<HeroListEntity> queryLesson(HeroListQuery query) {
        String month = query.getMonth();
        if(month==null){
            month = ut.currentFullMonth();
        }
        String day = heroListDao.getLastDay(month);
        HeroListQuery heroListQuery = new HeroListQuery();
        heroListQuery.setHeroDate(day);
        heroListQuery.setType(HeroListTypeEnum.LC.getKey());
        PageRequest page = new PageRequest(1000);
        List<HeroListEntity> heroListList = heroListDao.find(heroListQuery,page);
        return heroListList;
    }

    public List<HeroListEntity> queryMoney(HeroListQuery query) {
        String month = query.getMonth();
        if(month==null){
            month = ut.currentFullMonth();
        }
        String day = heroListDao.getLastDay(month);
        HeroListQuery heroListQuery = new HeroListQuery();
        heroListQuery.setHeroDate(day);
        heroListQuery.setType(HeroListTypeEnum.XK.getKey());
        PageRequest page = new PageRequest(1000);
        List<HeroListEntity> heroListList = heroListDao.find(heroListQuery,page);
        return heroListList;
    }

    public List<HeroListEntity> queryActiveRate(HeroListQuery query) {
        String month = query.getMonth();
        if(month==null){
            month = ut.currentFullMonth();
        }
        String day = heroListDao.getLastDay(month);
        HeroListQuery heroListQuery = new HeroListQuery();
        heroListQuery.setHeroDate(day);
        heroListQuery.setType(HeroListTypeEnum.HYD.getKey());
        PageRequest page = new PageRequest(1000);
        List<HeroListEntity> heroListList = heroListDao.find(heroListQuery,page);
        return heroListList;
    }


}


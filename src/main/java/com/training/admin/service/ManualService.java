package com.training.admin.service;

import com.training.common.PageRequest;
import com.training.dao.*;
import com.training.entity.*;
import com.training.service.*;
import com.training.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.*;

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
    private StaffService staffService;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private SysLogService sysLogService;

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
                createSingleStaffMonth(staffEntity.getStaffId(),month);
            }
        }
    }

    public int createSingleStaffMonth(String staffId,String month) {
        int n = 0;
        StaffEntity staffEntity = staffDao.getById(staffId);
        if(staffEntity==null){
            StoreEntity storeEntity = storeDao.getById(staffId);
            if(storeEntity==null){
                return n;
            }
            staffEntity = new StaffEntity();
            staffEntity.setStaffId(staffId);
            staffEntity.setCustname("全店");
            staffEntity.setStoreId(storeEntity.getStoreId());
            staffEntity.setJob("全店");
        }
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
            kpiStaffMonthEntity.setType("QD");
            if("教练".equals(staffEntity.getJob())){
                kpiStaffMonthEntity.setType("JL");
            }
            if("店长".equals(staffEntity.getJob())){
                kpiStaffMonthEntity.setType("DZ");
            }
            n = kpiStaffMonthDao.add(kpiStaffMonthEntity);
        }
        return n;
    }

    public int createStoreOpen(String storeId,String year) {
        StoreEntity storeEntity = storeDao.getById(storeId);
        if(storeEntity==null){
            return 0;
        }
        List data = jdbcTemplate.queryForList("select * from store_open where store_id = ? and  year = ?",new Object[]{storeId,year});
        if(data.size()>0){
            return 0;
        }
        String sql = " insert into store_open (store_id, year,created,modified) values (?,?,now(),now()) ";
        int n = jdbcTemplate.update(sql,new Object[]{storeId,year});
        return n;
    }

    public void updateCoachStaff() {
        logger.info(" updateCoachStaff  ");
        List<Map<String,Object>> deptList =  jdbcTemplate.queryForList("select * from store  ");
        for (int i = 0; i < deptList.size(); i++) {
            try {
                Map dept = deptList.get(i);
                String deptId = dept.get("dept_id").toString();
                System.out.println("dept_id: " + dept.get("dept_id").toString()+" , name: " + dept.get("name").toString());
                List<Map> staffList = DingtalkUtil.getStaffs(dept.get("dept_id").toString());
                for (int j = 0; j < staffList.size(); j++) {
                    try{
                        Map item = staffList.get(j);
                        System.out.println("userid: " + item.get("userid").toString());
                        System.out.println("name: " + item.get("name").toString());

                        String hiredDate = "";
                        if(item.containsKey("hiredDate")){
                            hiredDate = ut.getDateFromTimeStamp(Long.parseLong(item.get("hiredDate").toString()));
                        }
                        System.out.println(" hiredDate = "+hiredDate);

                        String userid = item.get("userid").toString();
                        StaffEntity staffDB = staffService.getByPhone(item.get("mobile").toString());
                        if(staffDB!=null){
                            logger.info(item.get("name").toString()+"已存在，无需重复添加");
                            StaffEntity staffUpdate = new StaffEntity();
                            staffUpdate.setStaffId(staffDB.getStaffId());
                            staffUpdate.setCustname(item.get("name").toString());
                            staffUpdate.setStoreId(deptId);
                            staffUpdate.setHiredDate(hiredDate);
                            if(item.containsKey("position")){
                                staffUpdate.setJob(item.get("position").toString());
                            }
                            staffService.update(staffUpdate);
                            continue;
                        }
                        String position = "";
                        if(item.containsKey("position")){
                            position = item.get("position").toString();
                        }
                        String mobile = "";
                        if(item.containsKey("mobile")){
                            mobile = item.get("mobile").toString();
                        }
                        String email = "";
                        if(item.containsKey("email")){
                            email = item.get("email").toString();
                        }
                        String extattr = "";
                        if(item.containsKey("extattr")){
                            extattr = item.get("extattr").toString();
                        }
                        StaffEntity staffEntity = new StaffEntity();
                        staffEntity.setStaffId(IDUtils.getId());
                        staffEntity.setRelId(item.get("userid").toString());
                        staffEntity.setStoreId(deptId);
                        staffEntity.setUsername(item.get("mobile").toString());
                        staffEntity.setCustname(item.get("name").toString());
                        staffEntity.setPhone(mobile);
                        staffEntity.setEmail(email);
                        staffEntity.setFeature(extattr);
                        staffEntity.setJob(position);
                        staffEntity.setHiredDate(hiredDate);
                        staffService.add(staffEntity);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){

            }
        }
    }

}


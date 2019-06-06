package com.training.dao;

import com.training.repository.*;
import com.training.entity.*;
import com.training.common.PageRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * staff 数据库操作类
 * Created by huai23 on 2018-05-26 13:55:30.
 */ 
@Service
public class StaffDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StaffRepository staffRepository;

    /**
     * 新增实体
     * @param staff
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    public int add(StaffEntity staff){
        int n = staffRepository.add(staff);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    public List<StaffEntity> find(StaffQuery query , PageRequest page){
        List<StaffEntity> staffList = staffRepository.find(query,page);
        return staffList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    public Long count(StaffQuery query){
        Long n = staffRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    public StaffEntity getById(String id){
        StaffEntity staffDB = staffRepository.getById(id);
        return staffDB;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:55:30.
     */
    public List<StaffEntity> getManagerByStoreId(String id){
        List<StaffEntity> staffDBs = staffRepository.getManagerByStoreId(id);
        return staffDBs;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:55:30.
     */
    public List<StaffEntity> getManagerByStoreIdAndDay(String id,String day){
        List<StaffEntity> staffDBs = staffRepository.getManagerByStoreIdAndDay(id,day);
        return staffDBs;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:55:30.
     */
    public StaffEntity getByOpenId(String id){
        if(StringUtils.isEmpty(id)){
            return null;
        }
        StaffEntity staffDB = staffRepository.getByOpenId(id);
        return staffDB;
    }

    /**
     * 根据实体更新
     * @param staff
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    public int update(StaffEntity staff){
        int n = staffRepository.update(staff);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    public int delete(String id){
        int n = staffRepository.delete(id);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param username
     * Created by huai23 on 2018-05-26 13:55:30.
     */
    public StaffEntity getByUsername(String username){
        StaffEntity staffDB = staffRepository.getByUsername(username);
        return staffDB;
    }

    /**
     * 根据ID查询实体
     * @param phone
     * Created by huai23 on 2018-05-26 13:55:30.
     */
    public StaffEntity getByPhone(String phone){
        StaffEntity staffDB = staffRepository.getByPhone(phone);
        return staffDB;
    }

    /**
     * 新增实体
     * @param staff
     * Created by huai23 on 2018-05-26 13:33:17.
     */
    public int bind(StaffEntity staff){
        int n = staffRepository.bind(staff);
        return n;
    }

    public StaffEntity getByCustname(String staffName) {
        StaffQuery query = new StaffQuery();
        query.setCustname(staffName);
        PageRequest page = new PageRequest();
        page.setPageSize(1000);
        List<StaffEntity> staffs = this.find(query,new PageRequest());
        StaffEntity staffEntity = null;
        for (StaffEntity staff : staffs){
            if(staff.getCustname().trim().equals(staffName)){
                staffEntity = staff;
                break;
            }
        }
        return staffEntity;
    }

    public int logoffByStaff(String openId) {
        int n = staffRepository.logoffByStaff(openId);
        return n;
    }

    public int leave(String staffId) {
        int n = staffRepository.leave(staffId);
        return n;
    }

    public int entry(String staffId) {
        int n = staffRepository.entry(staffId);
        return n;
    }

}


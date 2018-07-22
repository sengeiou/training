package com.training.dao;

import com.training.repository.*;
import com.training.entity.*;
import com.training.common.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * staff_medal 数据库操作类
 * Created by huai23 on 2018-07-22 23:28:30.
 */ 
@Service
public class StaffMedalDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StaffMedalRepository staffMedalRepository;

    /**
     * 新增实体
     * @param staffMedal
     * Created by huai23 on 2018-07-22 23:28:30.
     */ 
    public int add(StaffMedalEntity staffMedal){
        int n = staffMedalRepository.add(staffMedal);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-07-22 23:28:30.
     */ 
    public List<StaffMedalEntity> find(StaffMedalQuery query , PageRequest page){
        List<StaffMedalEntity> staffMedalList = staffMedalRepository.find(query,page);
        return staffMedalList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-22 23:28:30.
     */ 
    public Long count(StaffMedalQuery query){
        Long n = staffMedalRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-22 23:28:30.
     */ 
    public StaffMedalEntity getById(String id,String medalId){
        StaffMedalEntity staffMedalDB = staffMedalRepository.getById(id,medalId);
        return staffMedalDB;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-22 23:28:30.
     */
    public List<StaffMedalEntity> getByStaffId(String id){
        List<StaffMedalEntity> staffMedals = staffMedalRepository.getByStaffId(id);
        return staffMedals;
    }

    /**
     * 根据实体更新
     * @param staffMedal
     * Created by huai23 on 2018-07-22 23:28:30.
     */ 
    public int update(StaffMedalEntity staffMedal){
        int n = staffMedalRepository.update(staffMedal);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-22 23:28:30.
     */ 
    public int delete(String id,String medalId){
        int n = staffMedalRepository.delete(id,medalId);
        return n;
    }


}


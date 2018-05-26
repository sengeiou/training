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


}


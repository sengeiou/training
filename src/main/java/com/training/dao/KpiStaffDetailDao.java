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
 * kpi_staff_detail 数据库操作类
 * Created by huai23 on 2018-11-18 10:53:42.
 */ 
@Service
public class KpiStaffDetailDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KpiStaffDetailRepository kpiStaffDetailRepository;

    /**
     * 新增实体
     * @param kpiStaffDetail
     * Created by huai23 on 2018-11-18 10:53:42.
     */ 
    public int add(KpiStaffDetailEntity kpiStaffDetail){
        int n = kpiStaffDetailRepository.add(kpiStaffDetail);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-11-18 10:53:42.
     */ 
    public List<KpiStaffDetailEntity> find(KpiStaffDetailQuery query , PageRequest page){
        List<KpiStaffDetailEntity> kpiStaffDetailList = kpiStaffDetailRepository.find(query,page);
        return kpiStaffDetailList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-11-18 10:53:42.
     */ 
    public Long count(KpiStaffDetailQuery query){
        Long n = kpiStaffDetailRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-11-18 10:53:42.
     */ 
    public KpiStaffDetailEntity getById(String id){
        KpiStaffDetailEntity kpiStaffDetailDB = kpiStaffDetailRepository.getById(id);
        return kpiStaffDetailDB;
    }

    /**
     * 根据实体更新
     * @param kpiStaffDetail
     * Created by huai23 on 2018-11-18 10:53:42.
     */ 
    public int update(KpiStaffDetailEntity kpiStaffDetail){
        int n = kpiStaffDetailRepository.update(kpiStaffDetail);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-11-18 10:53:42.
     */ 
    public int delete(String id){
        int n = kpiStaffDetailRepository.delete(id);
        return n;
    }


}


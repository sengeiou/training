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
 * kpi_staff_month 数据库操作类
 * Created by huai23 on 2018-07-13 23:24:52.
 */ 
@Service
public class KpiStaffMonthDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KpiStaffMonthRepository kpiStaffMonthRepository;

    /**
     * 新增实体
     * @param kpiStaffMonth
     * Created by huai23 on 2018-07-13 23:24:52.
     */ 
    public int add(KpiStaffMonthEntity kpiStaffMonth){
        int n = kpiStaffMonthRepository.add(kpiStaffMonth);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-07-13 23:24:52.
     */ 
    public List<KpiStaffMonthEntity> find(KpiStaffMonthQuery query , PageRequest page){
        List<KpiStaffMonthEntity> kpiStaffMonthList = kpiStaffMonthRepository.find(query,page);
        return kpiStaffMonthList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-13 23:24:52.
     */ 
    public Long count(KpiStaffMonthQuery query){
        Long n = kpiStaffMonthRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-13 23:24:52.
     */ 
    public KpiStaffMonthEntity getById(String id){
        KpiStaffMonthEntity kpiStaffMonthDB = kpiStaffMonthRepository.getById(id);
        return kpiStaffMonthDB;
    }

    /**
     * 根据实体更新
     * @param kpiStaffMonth
     * Created by huai23 on 2018-07-13 23:24:52.
     */ 
    public int update(KpiStaffMonthEntity kpiStaffMonth){
        int n = kpiStaffMonthRepository.update(kpiStaffMonth);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-13 23:24:52.
     */ 
    public int delete(String id){
        int n = kpiStaffMonthRepository.delete(id);
        return n;
    }


}


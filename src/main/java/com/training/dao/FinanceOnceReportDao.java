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
 * finance_once_report 数据库操作类
 * Created by huai23 on 2018-12-02 20:58:14.
 */ 
@Service
public class FinanceOnceReportDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FinanceOnceReportRepository financeOnceReportRepository;

    /**
     * 新增实体
     * @param financeOnceReport
     * Created by huai23 on 2018-12-02 20:58:14.
     */ 
    public int add(FinanceOnceReportEntity financeOnceReport){
        int n = financeOnceReportRepository.add(financeOnceReport);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-12-02 20:58:14.
     */ 
    public List<FinanceOnceReportEntity> find(FinanceOnceReportQuery query , PageRequest page){
        List<FinanceOnceReportEntity> financeOnceReportList = financeOnceReportRepository.find(query,page);
        return financeOnceReportList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-12-02 20:58:14.
     */ 
    public Long count(FinanceOnceReportQuery query){
        Long n = financeOnceReportRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-12-02 20:58:14.
     */ 
    public FinanceOnceReportEntity getById(String id){
        FinanceOnceReportEntity financeOnceReportDB = financeOnceReportRepository.getById(id);
        return financeOnceReportDB;
    }

    /**
     * 根据实体更新
     * @param financeOnceReport
     * Created by huai23 on 2018-12-02 20:58:14.
     */ 
    public int update(FinanceOnceReportEntity financeOnceReport){
        int n = financeOnceReportRepository.update(financeOnceReport);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-12-02 20:58:14.
     */ 
    public int delete(String id){
        int n = financeOnceReportRepository.delete(id);
        return n;
    }


}


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
 * finance_month_report 数据库操作类
 * Created by huai23 on 2018-12-02 20:58:01.
 */ 
@Service
public class FinanceMonthReportDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FinanceMonthReportRepository financeMonthReportRepository;

    /**
     * 新增实体
     * @param financeMonthReport
     * Created by huai23 on 2018-12-02 20:58:01.
     */ 
    public int add(FinanceMonthReportEntity financeMonthReport){
        int n = financeMonthReportRepository.add(financeMonthReport);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-12-02 20:58:01.
     */ 
    public List<FinanceMonthReportEntity> find(FinanceMonthReportQuery query , PageRequest page){
        List<FinanceMonthReportEntity> financeMonthReportList = financeMonthReportRepository.find(query,page);
        return financeMonthReportList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-12-02 20:58:01.
     */ 
    public Long count(FinanceMonthReportQuery query){
        Long n = financeMonthReportRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-12-02 20:58:01.
     */ 
    public FinanceMonthReportEntity getById(String id){
        FinanceMonthReportEntity financeMonthReportDB = financeMonthReportRepository.getById(id);
        return financeMonthReportDB;
    }

    /**
     * 根据实体更新
     * @param financeMonthReport
     * Created by huai23 on 2018-12-02 20:58:01.
     */ 
    public int update(FinanceMonthReportEntity financeMonthReport){
        int n = financeMonthReportRepository.update(financeMonthReport);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-12-02 20:58:01.
     */ 
    public int delete(String id){
        int n = financeMonthReportRepository.delete(id);
        return n;
    }


}


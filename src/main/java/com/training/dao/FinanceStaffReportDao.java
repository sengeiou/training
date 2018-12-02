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
 * finance_staff_report 数据库操作类
 * Created by huai23 on 2018-12-02 22:02:12.
 */ 
@Service
public class FinanceStaffReportDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FinanceStaffReportRepository financeStaffReportRepository;

    /**
     * 新增实体
     * @param financeStaffReport
     * Created by huai23 on 2018-12-02 22:02:12.
     */ 
    public int add(FinanceStaffReportEntity financeStaffReport){
        int n = financeStaffReportRepository.add(financeStaffReport);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-12-02 22:02:12.
     */ 
    public List<FinanceStaffReportEntity> find(FinanceStaffReportQuery query , PageRequest page){
        List<FinanceStaffReportEntity> financeStaffReportList = financeStaffReportRepository.find(query,page);
        return financeStaffReportList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-12-02 22:02:12.
     */ 
    public Long count(FinanceStaffReportQuery query){
        Long n = financeStaffReportRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-12-02 22:02:12.
     */ 
    public FinanceStaffReportEntity getById(String id){
        FinanceStaffReportEntity financeStaffReportDB = financeStaffReportRepository.getById(id);
        return financeStaffReportDB;
    }

    /**
     * 根据实体更新
     * @param financeStaffReport
     * Created by huai23 on 2018-12-02 22:02:12.
     */ 
    public int update(FinanceStaffReportEntity financeStaffReport){
        int n = financeStaffReportRepository.update(financeStaffReport);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-12-02 22:02:12.
     */ 
    public int delete(String id){
        int n = financeStaffReportRepository.delete(id);
        return n;
    }


}


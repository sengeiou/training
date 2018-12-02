package com.training.service;

import com.training.dao.*;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.training.util.ResponseUtil;
import com.training.util.RequestContextHelper;

import java.util.List;

/**
 * finance_month_report 核心业务操作类
 * Created by huai23 on 2018-12-02 20:58:01.
 */ 
@Service
public class FinanceMonthReportService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FinanceMonthReportDao financeMonthReportDao;

    /**
     * 新增实体
     * @param financeMonthReport
     * Created by huai23 on 2018-12-02 20:58:01.
     */ 
    public ResponseEntity<String> add(FinanceMonthReportEntity financeMonthReport){
        User user = RequestContextHelper.getUser();
        int n = financeMonthReportDao.add(financeMonthReport);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-12-02 20:58:01.
     */ 
    public Page<FinanceMonthReportEntity> find(FinanceMonthReportQuery query , PageRequest page){
        List<FinanceMonthReportEntity> financeMonthReportList = financeMonthReportDao.find(query,page);
        Long count = financeMonthReportDao.count(query);
        Page<FinanceMonthReportEntity> returnPage = new Page<>();
        returnPage.setContent(financeMonthReportList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-12-02 20:58:01.
     */ 
    public Long count(FinanceMonthReportQuery query){
        Long count = financeMonthReportDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-12-02 20:58:01.
     */ 
    public FinanceMonthReportEntity getById(String id){
        FinanceMonthReportEntity financeMonthReportDB = financeMonthReportDao.getById(id);
        return financeMonthReportDB;
    }

    /**
     * 根据实体更新
     * @param financeMonthReport
     * Created by huai23 on 2018-12-02 20:58:01.
     */ 
    public  ResponseEntity<String> update(FinanceMonthReportEntity financeMonthReport){
        int n = financeMonthReportDao.update(financeMonthReport);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-12-02 20:58:01.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = financeMonthReportDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


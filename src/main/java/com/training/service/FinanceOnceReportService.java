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
 * finance_once_report 核心业务操作类
 * Created by huai23 on 2018-12-02 20:58:14.
 */ 
@Service
public class FinanceOnceReportService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FinanceOnceReportDao financeOnceReportDao;

    /**
     * 新增实体
     * @param financeOnceReport
     * Created by huai23 on 2018-12-02 20:58:14.
     */ 
    public ResponseEntity<String> add(FinanceOnceReportEntity financeOnceReport){
        int n = financeOnceReportDao.add(financeOnceReport);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-12-02 20:58:14.
     */ 
    public Page<FinanceOnceReportEntity> find(FinanceOnceReportQuery query , PageRequest page){
        List<FinanceOnceReportEntity> financeOnceReportList = financeOnceReportDao.find(query,page);
        Long count = financeOnceReportDao.count(query);
        Page<FinanceOnceReportEntity> returnPage = new Page<>();
        returnPage.setContent(financeOnceReportList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-12-02 20:58:14.
     */ 
    public Long count(FinanceOnceReportQuery query){
        Long count = financeOnceReportDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-12-02 20:58:14.
     */ 
    public FinanceOnceReportEntity getById(String id){
        FinanceOnceReportEntity financeOnceReportDB = financeOnceReportDao.getById(id);
        return financeOnceReportDB;
    }

    /**
     * 根据实体更新
     * @param financeOnceReport
     * Created by huai23 on 2018-12-02 20:58:14.
     */ 
    public  ResponseEntity<String> update(FinanceOnceReportEntity financeOnceReport){
        int n = financeOnceReportDao.update(financeOnceReport);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-12-02 20:58:14.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = financeOnceReportDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


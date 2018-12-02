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
 * finance_staff_report 核心业务操作类
 * Created by huai23 on 2018-12-02 22:02:12.
 */ 
@Service
public class FinanceStaffReportService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FinanceStaffReportDao financeStaffReportDao;

    /**
     * 新增实体
     * @param financeStaffReport
     * Created by huai23 on 2018-12-02 22:02:12.
     */ 
    public ResponseEntity<String> add(FinanceStaffReportEntity financeStaffReport){
        User user = RequestContextHelper.getUser();
        int n = financeStaffReportDao.add(financeStaffReport);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-12-02 22:02:12.
     */ 
    public Page<FinanceStaffReportEntity> find(FinanceStaffReportQuery query , PageRequest page){
        List<FinanceStaffReportEntity> financeStaffReportList = financeStaffReportDao.find(query,page);
        Long count = financeStaffReportDao.count(query);
        Page<FinanceStaffReportEntity> returnPage = new Page<>();
        returnPage.setContent(financeStaffReportList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-12-02 22:02:12.
     */ 
    public Long count(FinanceStaffReportQuery query){
        Long count = financeStaffReportDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-12-02 22:02:12.
     */ 
    public FinanceStaffReportEntity getById(String id){
        FinanceStaffReportEntity financeStaffReportDB = financeStaffReportDao.getById(id);
        return financeStaffReportDB;
    }

    /**
     * 根据实体更新
     * @param financeStaffReport
     * Created by huai23 on 2018-12-02 22:02:12.
     */ 
    public  ResponseEntity<String> update(FinanceStaffReportEntity financeStaffReport){
        int n = financeStaffReportDao.update(financeStaffReport);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-12-02 22:02:12.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = financeStaffReportDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


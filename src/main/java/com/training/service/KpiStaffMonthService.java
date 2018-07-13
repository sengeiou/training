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
 * kpi_staff_month 核心业务操作类
 * Created by huai23 on 2018-07-13 23:24:52.
 */ 
@Service
public class KpiStaffMonthService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KpiStaffMonthDao kpiStaffMonthDao;

    /**
     * 新增实体
     * @param kpiStaffMonth
     * Created by huai23 on 2018-07-13 23:24:52.
     */ 
    public ResponseEntity<String> add(KpiStaffMonthEntity kpiStaffMonth){
        User user = RequestContextHelper.getUser();
        int n = kpiStaffMonthDao.add(kpiStaffMonth);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-07-13 23:24:52.
     */ 
    public Page<KpiStaffMonthEntity> find(KpiStaffMonthQuery query , PageRequest page){
        List<KpiStaffMonthEntity> kpiStaffMonthList = kpiStaffMonthDao.find(query,page);
        Long count = kpiStaffMonthDao.count(query);
        Page<KpiStaffMonthEntity> returnPage = new Page<>();
        returnPage.setContent(kpiStaffMonthList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-13 23:24:53.
     */ 
    public Long count(KpiStaffMonthQuery query){
        Long count = kpiStaffMonthDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-13 23:24:53.
     */ 
    public KpiStaffMonthEntity getById(String id){
        KpiStaffMonthEntity kpiStaffMonthDB = kpiStaffMonthDao.getById(id);
        return kpiStaffMonthDB;
    }

    /**
     * 根据实体更新
     * @param kpiStaffMonth
     * Created by huai23 on 2018-07-13 23:24:53.
     */ 
    public  ResponseEntity<String> update(KpiStaffMonthEntity kpiStaffMonth){
        int n = kpiStaffMonthDao.update(kpiStaffMonth);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-13 23:24:53.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = kpiStaffMonthDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


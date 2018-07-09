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
 * kpi_quota 核心业务操作类
 * Created by huai23 on 2018-07-09 22:42:44.
 */ 
@Service
public class KpiQuotaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KpiQuotaDao kpiQuotaDao;

    /**
     * 新增实体
     * @param kpiQuota
     * Created by huai23 on 2018-07-09 22:42:44.
     */ 
    public ResponseEntity<String> add(KpiQuotaEntity kpiQuota){
        User user = RequestContextHelper.getUser();
        int n = kpiQuotaDao.add(kpiQuota);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-07-09 22:42:44.
     */ 
    public Page<KpiQuotaEntity> find(KpiQuotaQuery query , PageRequest page){
        List<KpiQuotaEntity> kpiQuotaList = kpiQuotaDao.find(query,page);
        Long count = kpiQuotaDao.count(query);
        Page<KpiQuotaEntity> returnPage = new Page<>();
        returnPage.setContent(kpiQuotaList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-09 22:42:44.
     */ 
    public Long count(KpiQuotaQuery query){
        Long count = kpiQuotaDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-09 22:42:44.
     */ 
    public KpiQuotaEntity getById(String id){
        KpiQuotaEntity kpiQuotaDB = kpiQuotaDao.getById(id);
        return kpiQuotaDB;
    }

    /**
     * 根据实体更新
     * @param kpiQuota
     * Created by huai23 on 2018-07-09 22:42:44.
     */ 
    public  ResponseEntity<String> update(KpiQuotaEntity kpiQuota){
        int n = kpiQuotaDao.update(kpiQuota);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-09 22:42:44.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = kpiQuotaDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


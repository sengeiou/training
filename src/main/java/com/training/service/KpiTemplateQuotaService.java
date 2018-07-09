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
 * kpi_template_quota 核心业务操作类
 * Created by huai23 on 2018-07-09 22:42:58.
 */ 
@Service
public class KpiTemplateQuotaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KpiTemplateQuotaDao kpiTemplateQuotaDao;

    /**
     * 新增实体
     * @param kpiTemplateQuota
     * Created by huai23 on 2018-07-09 22:42:58.
     */ 
    public ResponseEntity<String> add(KpiTemplateQuotaEntity kpiTemplateQuota){
        User user = RequestContextHelper.getUser();
        int n = kpiTemplateQuotaDao.add(kpiTemplateQuota);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-07-09 22:42:58.
     */ 
    public Page<KpiTemplateQuotaEntity> find(KpiTemplateQuotaQuery query , PageRequest page){
        List<KpiTemplateQuotaEntity> kpiTemplateQuotaList = kpiTemplateQuotaDao.find(query,page);
        Long count = kpiTemplateQuotaDao.count(query);
        Page<KpiTemplateQuotaEntity> returnPage = new Page<>();
        returnPage.setContent(kpiTemplateQuotaList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-09 22:42:58.
     */ 
    public Long count(KpiTemplateQuotaQuery query){
        Long count = kpiTemplateQuotaDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-09 22:42:58.
     */ 
    public KpiTemplateQuotaEntity getById(String id){
        KpiTemplateQuotaEntity kpiTemplateQuotaDB = kpiTemplateQuotaDao.getById(id);
        return kpiTemplateQuotaDB;
    }

    /**
     * 根据实体更新
     * @param kpiTemplateQuota
     * Created by huai23 on 2018-07-09 22:42:58.
     */ 
    public  ResponseEntity<String> update(KpiTemplateQuotaEntity kpiTemplateQuota){
        int n = kpiTemplateQuotaDao.update(kpiTemplateQuota);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-09 22:42:58.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = kpiTemplateQuotaDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


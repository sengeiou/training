package com.training.service;

import com.alibaba.fastjson.JSON;
import com.training.dao.*;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import com.training.util.IDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.training.util.ResponseUtil;
import com.training.util.RequestContextHelper;

import java.util.List;

/**
 * kpi_template 核心业务操作类
 * Created by huai23 on 2018-07-09 22:42:32.
 */ 
@Service
public class KpiTemplateService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KpiTemplateDao kpiTemplateDao;

    @Autowired
    private KpiTemplateQuotaDao kpiTemplateQuotaDao;

    /**
     * 新增实体
     * @param kpiTemplate
     * Created by huai23 on 2018-07-09 22:42:32.
     */ 
    public ResponseEntity<String> add(KpiTemplateEntity kpiTemplate){
        logger.info(" =================   KpiTemplateService add   kpiTemplate = {}",kpiTemplate);
        String id = IDUtils.getId();
        kpiTemplate.setTemplateId(id);
        int n = kpiTemplateDao.add(kpiTemplate);

        List<KpiTemplateQuotaEntity> quotaEntityList = kpiTemplate.getQuotaEntityList();
        for (KpiTemplateQuotaEntity kpiTemplateQuotaEntity :quotaEntityList){
            kpiTemplateQuotaEntity.setTemplateId(id);
            kpiTemplateQuotaDao.add(kpiTemplateQuotaEntity);
        }

        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-07-09 22:42:32.
     */ 
    public Page<KpiTemplateEntity> find(KpiTemplateQuery query , PageRequest page){
        List<KpiTemplateEntity> kpiTemplateList = kpiTemplateDao.find(query,page);

        for (KpiTemplateEntity kpiTemplateEntity : kpiTemplateList){
            KpiTemplateQuotaQuery kpiTemplateQuotaQuery = new KpiTemplateQuotaQuery();
            kpiTemplateQuotaQuery.setTemplateId(kpiTemplateEntity.getTemplateId());
            PageRequest pageRequest = new PageRequest();
            pageRequest.setPageSize(1000);
            List<KpiTemplateQuotaEntity> kpiTemplateQuotaEntityList = kpiTemplateQuotaDao.find(kpiTemplateQuotaQuery,pageRequest);
            kpiTemplateEntity.setQuotaEntityList(kpiTemplateQuotaEntityList);
        }

        Long count = kpiTemplateDao.count(query);
        Page<KpiTemplateEntity> returnPage = new Page<>();
        returnPage.setContent(kpiTemplateList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-09 22:42:32.
     */ 
    public Long count(KpiTemplateQuery query){
        Long count = kpiTemplateDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-09 22:42:32.
     */ 
    public KpiTemplateEntity getById(String id){
        KpiTemplateEntity kpiTemplateDB = kpiTemplateDao.getById(id);
        KpiTemplateQuotaQuery kpiTemplateQuotaQuery = new KpiTemplateQuotaQuery();
        kpiTemplateQuotaQuery.setTemplateId(kpiTemplateDB.getTemplateId());
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(1000);
        List<KpiTemplateQuotaEntity> kpiTemplateQuotaEntityList = kpiTemplateQuotaDao.find(kpiTemplateQuotaQuery,pageRequest);
        kpiTemplateDB.setQuotaEntityList(kpiTemplateQuotaEntityList);
        return kpiTemplateDB;
    }

    /**
     * 根据实体更新
     * @param kpiTemplate
     * Created by huai23 on 2018-07-09 22:42:32.
     */ 
    public  ResponseEntity<String> update(KpiTemplateEntity kpiTemplate){
        int n = kpiTemplateDao.update(kpiTemplate);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-09 22:42:32.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = kpiTemplateDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


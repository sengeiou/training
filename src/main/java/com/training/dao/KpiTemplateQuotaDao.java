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
 * kpi_template_quota 数据库操作类
 * Created by huai23 on 2018-07-09 22:42:58.
 */ 
@Service
public class KpiTemplateQuotaDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KpiTemplateQuotaRepository kpiTemplateQuotaRepository;

    /**
     * 新增实体
     * @param kpiTemplateQuota
     * Created by huai23 on 2018-07-09 22:42:58.
     */ 
    public int add(KpiTemplateQuotaEntity kpiTemplateQuota){
        int n = kpiTemplateQuotaRepository.add(kpiTemplateQuota);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-07-09 22:42:58.
     */ 
    public List<KpiTemplateQuotaEntity> find(KpiTemplateQuotaQuery query , PageRequest page){
        List<KpiTemplateQuotaEntity> kpiTemplateQuotaList = kpiTemplateQuotaRepository.find(query,page);
        return kpiTemplateQuotaList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-09 22:42:58.
     */ 
    public Long count(KpiTemplateQuotaQuery query){
        Long n = kpiTemplateQuotaRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-09 22:42:58.
     */ 
    public KpiTemplateQuotaEntity getById(String id){
        KpiTemplateQuotaEntity kpiTemplateQuotaDB = kpiTemplateQuotaRepository.getById(id);
        return kpiTemplateQuotaDB;
    }

    /**
     * 根据实体更新
     * @param kpiTemplateQuota
     * Created by huai23 on 2018-07-09 22:42:58.
     */ 
    public int update(KpiTemplateQuotaEntity kpiTemplateQuota){
        int n = kpiTemplateQuotaRepository.update(kpiTemplateQuota);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-09 22:42:58.
     */ 
    public int delete(String id){
        int n = kpiTemplateQuotaRepository.delete(id);
        return n;
    }


}


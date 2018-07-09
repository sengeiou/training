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
 * kpi_quota 数据库操作类
 * Created by huai23 on 2018-07-09 22:42:44.
 */ 
@Service
public class KpiQuotaDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KpiQuotaRepository kpiQuotaRepository;

    /**
     * 新增实体
     * @param kpiQuota
     * Created by huai23 on 2018-07-09 22:42:44.
     */ 
    public int add(KpiQuotaEntity kpiQuota){
        int n = kpiQuotaRepository.add(kpiQuota);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-07-09 22:42:44.
     */ 
    public List<KpiQuotaEntity> find(KpiQuotaQuery query , PageRequest page){
        List<KpiQuotaEntity> kpiQuotaList = kpiQuotaRepository.find(query,page);
        return kpiQuotaList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-09 22:42:44.
     */ 
    public Long count(KpiQuotaQuery query){
        Long n = kpiQuotaRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-09 22:42:44.
     */ 
    public KpiQuotaEntity getById(String id){
        KpiQuotaEntity kpiQuotaDB = kpiQuotaRepository.getById(id);
        return kpiQuotaDB;
    }

    /**
     * 根据实体更新
     * @param kpiQuota
     * Created by huai23 on 2018-07-09 22:42:44.
     */ 
    public int update(KpiQuotaEntity kpiQuota){
        int n = kpiQuotaRepository.update(kpiQuota);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-09 22:42:44.
     */ 
    public int delete(String id){
        int n = kpiQuotaRepository.delete(id);
        return n;
    }


}


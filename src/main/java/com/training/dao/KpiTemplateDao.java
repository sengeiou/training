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
 * kpi_template 数据库操作类
 * Created by huai23 on 2018-07-09 22:42:32.
 */ 
@Service
public class KpiTemplateDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KpiTemplateRepository kpiTemplateRepository;

    /**
     * 新增实体
     * @param kpiTemplate
     * Created by huai23 on 2018-07-09 22:42:32.
     */ 
    public int add(KpiTemplateEntity kpiTemplate){
        int n = kpiTemplateRepository.add(kpiTemplate);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-07-09 22:42:32.
     */ 
    public List<KpiTemplateEntity> find(KpiTemplateQuery query , PageRequest page){
        List<KpiTemplateEntity> kpiTemplateList = kpiTemplateRepository.find(query,page);
        return kpiTemplateList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-09 22:42:32.
     */ 
    public Long count(KpiTemplateQuery query){
        Long n = kpiTemplateRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-09 22:42:32.
     */ 
    public KpiTemplateEntity getById(String id){
        KpiTemplateEntity kpiTemplateDB = kpiTemplateRepository.getById(id);
        return kpiTemplateDB;
    }

    /**
     * 根据实体更新
     * @param kpiTemplate
     * Created by huai23 on 2018-07-09 22:42:32.
     */ 
    public int update(KpiTemplateEntity kpiTemplate){
        int n = kpiTemplateRepository.update(kpiTemplate);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-09 22:42:32.
     */ 
    public int delete(String id){
        int n = kpiTemplateRepository.delete(id);
        return n;
    }


}


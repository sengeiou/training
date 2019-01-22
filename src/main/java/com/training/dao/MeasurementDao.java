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
 * measurement 数据库操作类
 * Created by huai23 on 2019-01-22 18:13:44.
 */ 
@Service
public class MeasurementDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MeasurementRepository measurementRepository;

    /**
     * 新增实体
     * @param measurement
     * Created by huai23 on 2019-01-22 18:13:44.
     */ 
    public int add(MeasurementEntity measurement){
        int n = measurementRepository.add(measurement);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2019-01-22 18:13:44.
     */ 
    public List<MeasurementEntity> find(MeasurementQuery query , PageRequest page){
        List<MeasurementEntity> measurementList = measurementRepository.find(query,page);
        return measurementList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-22 18:13:44.
     */ 
    public Long count(MeasurementQuery query){
        Long n = measurementRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-22 18:13:44.
     */ 
    public MeasurementEntity getById(String id){
        MeasurementEntity measurementDB = measurementRepository.getById(id);
        return measurementDB;
    }

    /**
     * 根据实体更新
     * @param measurement
     * Created by huai23 on 2019-01-22 18:13:44.
     */ 
    public int update(MeasurementEntity measurement){
        int n = measurementRepository.update(measurement);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-22 18:13:44.
     */ 
    public int delete(String id){
        int n = measurementRepository.delete(id);
        return n;
    }


}


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
 * rotation_chart 数据库操作类
 * Created by huai23 on 2018-11-25 10:40:37.
 */ 
@Service
public class RotationChartDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RotationChartRepository rotationChartRepository;

    /**
     * 新增实体
     * @param rotationChart
     * Created by huai23 on 2018-11-25 10:40:37.
     */ 
    public int add(RotationChartEntity rotationChart){
        int n = rotationChartRepository.add(rotationChart);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-11-25 10:40:37.
     */ 
    public List<RotationChartEntity> find(RotationChartQuery query , PageRequest page){
        List<RotationChartEntity> rotationChartList = rotationChartRepository.find(query,page);
        return rotationChartList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-11-25 10:40:37.
     */ 
    public Long count(RotationChartQuery query){
        Long n = rotationChartRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-11-25 10:40:37.
     */ 
    public RotationChartEntity getById(String id){
        RotationChartEntity rotationChartDB = rotationChartRepository.getById(id);
        return rotationChartDB;
    }

    /**
     * 根据实体更新
     * @param rotationChart
     * Created by huai23 on 2018-11-25 10:40:37.
     */ 
    public int update(RotationChartEntity rotationChart){
        int n = rotationChartRepository.update(rotationChart);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-11-25 10:40:37.
     */ 
    public int delete(String id){
        int n = rotationChartRepository.delete(id);
        return n;
    }


}


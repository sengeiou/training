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
 * coach_rest 数据库操作类
 * Created by huai23 on 2018-05-26 13:55:16.
 */ 
@Service
public class CoachRestDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CoachRestRepository coachRestRepository;

    /**
     * 新增实体
     * @param coachRest
     * Created by huai23 on 2018-05-26 13:55:16.
     */ 
    public int add(CoachRestEntity coachRest){
        int n = coachRestRepository.add(coachRest);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:55:16.
     */ 
    public List<CoachRestEntity> find(CoachRestQuery query , PageRequest page){
        List<CoachRestEntity> coachRestList = coachRestRepository.find(query,page);
        return coachRestList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:55:16.
     */ 
    public Long count(CoachRestQuery query){
        Long n = coachRestRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:55:16.
     */ 
    public CoachRestEntity getById(String id){
        CoachRestEntity coachRestDB = coachRestRepository.getById(id);
        return coachRestDB;
    }

    /**
     * 根据实体更新
     * @param coachRest
     * Created by huai23 on 2018-05-26 13:55:16.
     */ 
    public int update(CoachRestEntity coachRest){
        int n = coachRestRepository.update(coachRest);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:55:16.
     */ 
    public int delete(String id){
        int n = coachRestRepository.delete(id);
        return n;
    }


}


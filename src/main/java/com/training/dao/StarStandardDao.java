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
 * star_standard 数据库操作类
 * Created by huai23 on 2018-07-22 20:49:43.
 */ 
@Service
public class StarStandardDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StarStandardRepository starStandardRepository;

    /**
     * 新增实体
     * @param starStandard
     * Created by huai23 on 2018-07-22 20:49:43.
     */ 
    public int add(StarStandardEntity starStandard){
        int n = starStandardRepository.add(starStandard);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-07-22 20:49:43.
     */ 
    public List<StarStandardEntity> find(StarStandardQuery query , PageRequest page){
        List<StarStandardEntity> starStandardList = starStandardRepository.find(query,page);
        return starStandardList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-22 20:49:43.
     */ 
    public Long count(StarStandardQuery query){
        Long n = starStandardRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-22 20:49:43.
     */ 
    public StarStandardEntity getById(String id){
        StarStandardEntity starStandardDB = starStandardRepository.getById(id);
        return starStandardDB;
    }

    /**
     * 根据实体更新
     * @param starStandard
     * Created by huai23 on 2018-07-22 20:49:43.
     */ 
    public int update(StarStandardEntity starStandard){
        int n = starStandardRepository.update(starStandard);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-22 20:49:43.
     */ 
    public int delete(String id){
        int n = starStandardRepository.delete(id);
        return n;
    }


}


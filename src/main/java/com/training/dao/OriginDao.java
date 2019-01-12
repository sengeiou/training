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
 * origin 数据库操作类
 * Created by huai23 on 2019-01-12 13:23:55.
 */ 
@Service
public class OriginDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OriginRepository originRepository;

    /**
     * 新增实体
     * @param origin
     * Created by huai23 on 2019-01-12 13:23:55.
     */ 
    public int add(OriginEntity origin){
        int n = originRepository.add(origin);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2019-01-12 13:23:55.
     */ 
    public List<OriginEntity> find(OriginQuery query , PageRequest page){
        List<OriginEntity> originList = originRepository.find(query,page);
        return originList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-12 13:23:55.
     */ 
    public Long count(OriginQuery query){
        Long n = originRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-12 13:23:55.
     */ 
    public OriginEntity getById(String id){
        OriginEntity originDB = originRepository.getById(id);
        return originDB;
    }

    /**
     * 根据实体更新
     * @param origin
     * Created by huai23 on 2019-01-12 13:23:55.
     */ 
    public int update(OriginEntity origin){
        int n = originRepository.update(origin);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-12 13:23:55.
     */ 
    public int delete(String id){
        int n = originRepository.delete(id);
        return n;
    }


}


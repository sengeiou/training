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
 * store_open 数据库操作类
 * Created by huai23 on 2018-07-10 20:40:20.
 */ 
@Service
public class StoreOpenDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StoreOpenRepository storeOpenRepository;

    /**
     * 新增实体
     * @param storeOpen
     * Created by huai23 on 2018-07-10 20:40:20.
     */ 
    public int add(StoreOpenEntity storeOpen){
        int n = storeOpenRepository.add(storeOpen);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-07-10 20:40:20.
     */ 
    public List<StoreOpenEntity> find(StoreOpenQuery query , PageRequest page){
        List<StoreOpenEntity> storeOpenList = storeOpenRepository.find(query,page);
        return storeOpenList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-10 20:40:20.
     */ 
    public Long count(StoreOpenQuery query){
        Long n = storeOpenRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-10 20:40:20.
     */ 
    public StoreOpenEntity getById(String id,String year){
        StoreOpenEntity storeOpenDB = storeOpenRepository.getById(id,year);
        return storeOpenDB;
    }

    /**
     * 根据实体更新
     * @param storeOpen
     * Created by huai23 on 2018-07-10 20:40:20.
     */ 
    public int update(StoreOpenEntity storeOpen){
        int n = storeOpenRepository.update(storeOpen);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-10 20:40:20.
     */ 
    public int delete(String id,String year){
        int n = storeOpenRepository.delete(id,year);
        return n;
    }


}


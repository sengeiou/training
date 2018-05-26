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
 * store 数据库操作类
 * Created by huai23 on 2018-05-26 13:43:38.
 */ 
@Service
public class StoreDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StoreRepository storeRepository;

    /**
     * 新增实体
     * @param store
     * Created by huai23 on 2018-05-26 13:43:38.
     */ 
    public int add(StoreEntity store){
        int n = storeRepository.add(store);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:43:38.
     */ 
    public List<StoreEntity> find(StoreQuery query , PageRequest page){
        List<StoreEntity> storeList = storeRepository.find(query,page);
        return storeList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:43:38.
     */ 
    public Long count(StoreQuery query){
        Long n = storeRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:43:38.
     */ 
    public StoreEntity getById(String id){
        StoreEntity storeDB = storeRepository.getById(id);
        return storeDB;
    }

    /**
     * 根据实体更新
     * @param store
     * Created by huai23 on 2018-05-26 13:43:38.
     */ 
    public int update(StoreEntity store){
        int n = storeRepository.update(store);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:43:38.
     */ 
    public int delete(String id){
        int n = storeRepository.delete(id);
        return n;
    }


}


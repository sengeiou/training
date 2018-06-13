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
 * store_task 数据库操作类
 * Created by huai23 on 2018-06-13 22:49:38.
 */ 
@Service
public class StoreTaskDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StoreTaskRepository storeTaskRepository;

    /**
     * 新增实体
     * @param storeTask
     * Created by huai23 on 2018-06-13 22:49:38.
     */ 
    public int add(StoreTaskEntity storeTask){
        int n = storeTaskRepository.add(storeTask);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-13 22:49:38.
     */ 
    public List<StoreTaskEntity> find(StoreTaskQuery query , PageRequest page){
        List<StoreTaskEntity> storeTaskList = storeTaskRepository.find(query,page);
        return storeTaskList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-13 22:49:38.
     */ 
    public Long count(StoreTaskQuery query){
        Long n = storeTaskRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-13 22:49:38.
     */ 
    public StoreTaskEntity getById(String id){
        StoreTaskEntity storeTaskDB = storeTaskRepository.getById(id);
        return storeTaskDB;
    }

    /**
     * 根据实体更新
     * @param storeTask
     * Created by huai23 on 2018-06-13 22:49:38.
     */ 
    public int update(StoreTaskEntity storeTask){
        int n = storeTaskRepository.update(storeTask);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-13 22:49:38.
     */ 
    public int delete(String id){
        int n = storeTaskRepository.delete(id);
        return n;
    }


}


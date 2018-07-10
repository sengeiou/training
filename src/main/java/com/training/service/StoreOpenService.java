package com.training.service;

import com.training.dao.*;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.training.util.ResponseUtil;
import com.training.util.RequestContextHelper;

import java.util.List;

/**
 * store_open 核心业务操作类
 * Created by huai23 on 2018-07-10 20:40:20.
 */ 
@Service
public class StoreOpenService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StoreOpenDao storeOpenDao;

    @Autowired
    private StoreDao storeDao;

    /**
     * 新增实体
     * @param storeOpen
     * Created by huai23 on 2018-07-10 20:40:20.
     */ 
    public ResponseEntity<String> add(StoreOpenEntity storeOpen){
        User user = RequestContextHelper.getUser();
        int n = storeOpenDao.add(storeOpen);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-07-10 20:40:20.
     */ 
    public Page<StoreOpenEntity> find(StoreOpenQuery query , PageRequest page){
        List<StoreOpenEntity> storeOpenList = storeOpenDao.find(query,page);
        for (StoreOpenEntity storeOpenEntity:storeOpenList){
            StoreEntity storeEntity = storeDao.getById(storeOpenEntity.getStoreId());
            storeOpenEntity.setStoreName(storeEntity.getName());
        }
        Long count = storeOpenDao.count(query);
        Page<StoreOpenEntity> returnPage = new Page<>();
        returnPage.setContent(storeOpenList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-10 20:40:20.
     */ 
    public Long count(StoreOpenQuery query){
        Long count = storeOpenDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-10 20:40:20.
     */ 
    public StoreOpenEntity getById(String id,String year){
        StoreOpenEntity storeOpenDB = storeOpenDao.getById(id,year);
        StoreEntity storeEntity = storeDao.getById(storeOpenDB.getStoreId());
        storeOpenDB.setStoreName(storeEntity.getName());
        return storeOpenDB;
    }

    /**
     * 根据实体更新
     * @param storeOpen
     * Created by huai23 on 2018-07-10 20:40:20.
     */ 
    public  ResponseEntity<String> update(StoreOpenEntity storeOpen){
        this.delete(storeOpen.getStoreId(),storeOpen.getYear());
        int n = storeOpenDao.add(storeOpen);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-10 20:40:20.
     */ 
    public ResponseEntity<String> delete(String id,String year){
        int n = storeOpenDao.delete(id,year);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


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
 * biz_unique 数据库操作类
 * Created by huai23 on 2018-12-25 23:24:18.
 */ 
@Service
public class BizUniqueDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BizUniqueRepository bizUniqueRepository;

    /**
     * 新增实体
     * @param bizId
     * Created by huai23 on 2018-12-25 23:24:18.
     */
    public int add(String bizId){
        BizUniqueEntity bizUnique = new BizUniqueEntity();
        bizUnique.setBizId(bizId);
        int n = bizUniqueRepository.add(bizUnique);
        return n;
    }

    /**
     * 新增实体
     * @param bizUnique
     * Created by huai23 on 2018-12-25 23:24:18.
     */ 
    public int add(BizUniqueEntity bizUnique){
        int n = bizUniqueRepository.add(bizUnique);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-12-25 23:24:18.
     */ 
    public List<BizUniqueEntity> find(BizUniqueQuery query , PageRequest page){
        List<BizUniqueEntity> bizUniqueList = bizUniqueRepository.find(query,page);
        return bizUniqueList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-12-25 23:24:18.
     */ 
    public Long count(BizUniqueQuery query){
        Long n = bizUniqueRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-12-25 23:24:18.
     */ 
    public BizUniqueEntity getById(String id){
        BizUniqueEntity bizUniqueDB = bizUniqueRepository.getById(id);
        return bizUniqueDB;
    }

    /**
     * 根据实体更新
     * @param bizUnique
     * Created by huai23 on 2018-12-25 23:24:18.
     */ 
    public int update(BizUniqueEntity bizUnique){
        int n = bizUniqueRepository.update(bizUnique);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-12-25 23:24:18.
     */ 
    public int delete(String id){
        int n = bizUniqueRepository.delete(id);
        return n;
    }


}


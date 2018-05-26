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
 * medal 数据库操作类
 * Created by huai23 on 2018-05-26 13:54:27.
 */ 
@Service
public class MedalDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MedalRepository medalRepository;

    /**
     * 新增实体
     * @param medal
     * Created by huai23 on 2018-05-26 13:54:27.
     */ 
    public int add(MedalEntity medal){
        int n = medalRepository.add(medal);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:54:27.
     */ 
    public List<MedalEntity> find(MedalQuery query , PageRequest page){
        List<MedalEntity> medalList = medalRepository.find(query,page);
        return medalList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:54:27.
     */ 
    public Long count(MedalQuery query){
        Long n = medalRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:54:27.
     */ 
    public MedalEntity getById(String id){
        MedalEntity medalDB = medalRepository.getById(id);
        return medalDB;
    }

    /**
     * 根据实体更新
     * @param medal
     * Created by huai23 on 2018-05-26 13:54:27.
     */ 
    public int update(MedalEntity medal){
        int n = medalRepository.update(medal);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:54:27.
     */ 
    public int delete(String id){
        int n = medalRepository.delete(id);
        return n;
    }


}


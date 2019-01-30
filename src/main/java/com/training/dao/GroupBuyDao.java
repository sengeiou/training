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
 * group_buy 数据库操作类
 * Created by huai23 on 2019-01-30 22:53:15.
 */ 
@Service
public class GroupBuyDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupBuyRepository groupBuyRepository;

    /**
     * 新增实体
     * @param groupBuy
     * Created by huai23 on 2019-01-30 22:53:15.
     */ 
    public int add(GroupBuyEntity groupBuy){
        int n = groupBuyRepository.add(groupBuy);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2019-01-30 22:53:15.
     */ 
    public List<GroupBuyEntity> find(GroupBuyQuery query , PageRequest page){
        List<GroupBuyEntity> groupBuyList = groupBuyRepository.find(query,page);
        return groupBuyList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-30 22:53:15.
     */ 
    public Long count(GroupBuyQuery query){
        Long n = groupBuyRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-30 22:53:15.
     */ 
    public GroupBuyEntity getById(String id){
        GroupBuyEntity groupBuyDB = groupBuyRepository.getById(id);
        return groupBuyDB;
    }

    /**
     * 根据实体更新
     * @param groupBuy
     * Created by huai23 on 2019-01-30 22:53:15.
     */ 
    public int update(GroupBuyEntity groupBuy){
        int n = groupBuyRepository.update(groupBuy);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-30 22:53:15.
     */ 
    public int delete(String id){
        int n = groupBuyRepository.delete(id);
        return n;
    }


}


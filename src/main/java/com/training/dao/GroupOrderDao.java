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
 * group_order 数据库操作类
 * Created by huai23 on 2019-01-30 22:53:43.
 */ 
@Service
public class GroupOrderDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupOrderRepository groupOrderRepository;

    /**
     * 新增实体
     * @param groupOrder
     * Created by huai23 on 2019-01-30 22:53:43.
     */ 
    public int add(GroupOrderEntity groupOrder){
        int n = groupOrderRepository.add(groupOrder);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2019-01-30 22:53:43.
     */ 
    public List<GroupOrderEntity> find(GroupOrderQuery query , PageRequest page){
        List<GroupOrderEntity> groupOrderList = groupOrderRepository.find(query,page);
        return groupOrderList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-30 22:53:43.
     */ 
    public Long count(GroupOrderQuery query){
        Long n = groupOrderRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-30 22:53:43.
     */ 
    public GroupOrderEntity getById(String id){
        GroupOrderEntity groupOrderDB = groupOrderRepository.getById(id);
        return groupOrderDB;
    }

    /**
     * 根据实体更新
     * @param groupOrder
     * Created by huai23 on 2019-01-30 22:53:43.
     */ 
    public int update(GroupOrderEntity groupOrder){
        int n = groupOrderRepository.update(groupOrder);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-30 22:53:43.
     */ 
    public int delete(String id){
        int n = groupOrderRepository.delete(id);
        return n;
    }


}


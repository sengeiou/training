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
 * group_order_log 数据库操作类
 * Created by huai23 on 2019-01-30 23:16:13.
 */ 
@Service
public class GroupOrderLogDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupOrderLogRepository groupOrderLogRepository;

    /**
     * 新增实体
     * @param groupOrderLog
     * Created by huai23 on 2019-01-30 23:16:13.
     */ 
    public int add(GroupOrderLogEntity groupOrderLog){
        int n = groupOrderLogRepository.add(groupOrderLog);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2019-01-30 23:16:13.
     */ 
    public List<GroupOrderLogEntity> find(GroupOrderLogQuery query , PageRequest page){
        List<GroupOrderLogEntity> groupOrderLogList = groupOrderLogRepository.find(query,page);
        return groupOrderLogList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-30 23:16:13.
     */ 
    public Long count(GroupOrderLogQuery query){
        Long n = groupOrderLogRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-30 23:16:13.
     */ 
    public GroupOrderLogEntity getById(String id){
        GroupOrderLogEntity groupOrderLogDB = groupOrderLogRepository.getById(id);
        return groupOrderLogDB;
    }

    /**
     * 根据实体更新
     * @param groupOrderLog
     * Created by huai23 on 2019-01-30 23:16:13.
     */ 
    public int update(GroupOrderLogEntity groupOrderLog){
        int n = groupOrderLogRepository.update(groupOrderLog);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-30 23:16:13.
     */ 
    public int delete(String id){
        int n = groupOrderLogRepository.delete(id);
        return n;
    }


}


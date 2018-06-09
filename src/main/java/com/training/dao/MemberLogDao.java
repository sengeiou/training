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
 * member_log 数据库操作类
 * Created by huai23 on 2018-06-09 09:16:23.
 */ 
@Service
public class MemberLogDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberLogRepository memberLogRepository;

    /**
     * 新增实体
     * @param memberLog
     * Created by huai23 on 2018-06-09 09:16:23.
     */ 
    public int add(MemberLogEntity memberLog){
        int n = memberLogRepository.add(memberLog);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-09 09:16:23.
     */ 
    public List<MemberLogEntity> find(MemberLogQuery query , PageRequest page){
        List<MemberLogEntity> memberLogList = memberLogRepository.find(query,page);
        return memberLogList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-09 09:16:23.
     */ 
    public Long count(MemberLogQuery query){
        Long n = memberLogRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-09 09:16:23.
     */ 
    public MemberLogEntity getById(String id){
        MemberLogEntity memberLogDB = memberLogRepository.getById(id);
        return memberLogDB;
    }

    /**
     * 根据实体更新
     * @param memberLog
     * Created by huai23 on 2018-06-09 09:16:23.
     */ 
    public int update(MemberLogEntity memberLog){
        int n = memberLogRepository.update(memberLog);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-09 09:16:23.
     */ 
    public int delete(String id){
        int n = memberLogRepository.delete(id);
        return n;
    }


}


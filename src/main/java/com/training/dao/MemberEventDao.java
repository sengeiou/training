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
 * member_event 数据库操作类
 * Created by huai23 on 2018-07-31 07:36:32.
 */ 
@Service
public class MemberEventDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberEventRepository memberEventRepository;

    /**
     * 新增实体
     * @param memberEvent
     * Created by huai23 on 2018-07-31 07:36:32.
     */ 
    public int add(MemberEventEntity memberEvent){
        int n = memberEventRepository.add(memberEvent);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-07-31 07:36:32.
     */ 
    public List<MemberEventEntity> find(MemberEventQuery query , PageRequest page){
        List<MemberEventEntity> memberEventList = memberEventRepository.find(query,page);
        return memberEventList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-31 07:36:32.
     */ 
    public Long count(MemberEventQuery query){
        Long n = memberEventRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-31 07:36:32.
     */ 
    public MemberEventEntity getById(String id){
        MemberEventEntity memberEventDB = memberEventRepository.getById(id);
        return memberEventDB;
    }

    /**
     * 根据实体更新
     * @param memberEvent
     * Created by huai23 on 2018-07-31 07:36:32.
     */ 
    public int update(MemberEventEntity memberEvent){
        int n = memberEventRepository.update(memberEvent);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-31 07:36:32.
     */ 
    public int delete(String id){
        int n = memberEventRepository.delete(id);
        return n;
    }


}


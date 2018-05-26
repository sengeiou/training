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
 * member_medal 数据库操作类
 * Created by huai23 on 2018-05-26 13:54:40.
 */ 
@Service
public class MemberMedalDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberMedalRepository memberMedalRepository;

    /**
     * 新增实体
     * @param memberMedal
     * Created by huai23 on 2018-05-26 13:54:40.
     */ 
    public int add(MemberMedalEntity memberMedal){
        int n = memberMedalRepository.add(memberMedal);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:54:40.
     */ 
    public List<MemberMedalEntity> find(MemberMedalQuery query , PageRequest page){
        List<MemberMedalEntity> memberMedalList = memberMedalRepository.find(query,page);
        return memberMedalList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:54:40.
     */ 
    public Long count(MemberMedalQuery query){
        Long n = memberMedalRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:54:40.
     */ 
    public MemberMedalEntity getById(String id){
        MemberMedalEntity memberMedalDB = memberMedalRepository.getById(id);
        return memberMedalDB;
    }

    /**
     * 根据实体更新
     * @param memberMedal
     * Created by huai23 on 2018-05-26 13:54:40.
     */ 
    public int update(MemberMedalEntity memberMedal){
        int n = memberMedalRepository.update(memberMedal);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:54:40.
     */ 
    public int delete(String id){
        int n = memberMedalRepository.delete(id);
        return n;
    }


}


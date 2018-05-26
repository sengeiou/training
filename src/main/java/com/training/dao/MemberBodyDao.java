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
 * member_body 数据库操作类
 * Created by huai23 on 2018-05-26 13:54:03.
 */ 
@Service
public class MemberBodyDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberBodyRepository memberBodyRepository;

    /**
     * 新增实体
     * @param memberBody
     * Created by huai23 on 2018-05-26 13:54:03.
     */ 
    public int add(MemberBodyEntity memberBody){
        int n = memberBodyRepository.add(memberBody);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:54:03.
     */ 
    public List<MemberBodyEntity> find(MemberBodyQuery query , PageRequest page){
        List<MemberBodyEntity> memberBodyList = memberBodyRepository.find(query,page);
        return memberBodyList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:54:03.
     */ 
    public Long count(MemberBodyQuery query){
        Long n = memberBodyRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:54:03.
     */ 
    public MemberBodyEntity getById(String id){
        MemberBodyEntity memberBodyDB = memberBodyRepository.getById(id);
        return memberBodyDB;
    }

    /**
     * 根据实体更新
     * @param memberBody
     * Created by huai23 on 2018-05-26 13:54:03.
     */ 
    public int update(MemberBodyEntity memberBody){
        int n = memberBodyRepository.update(memberBody);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:54:03.
     */ 
    public int delete(String id){
        int n = memberBodyRepository.delete(id);
        return n;
    }


}


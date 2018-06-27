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
 * role 数据库操作类
 * Created by huai23 on 2018-06-27 15:28:01.
 */ 
@Service
public class RoleDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 新增实体
     * @param role
     * Created by huai23 on 2018-06-27 15:28:01.
     */ 
    public int add(RoleEntity role){
        int n = roleRepository.add(role);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-27 15:28:01.
     */ 
    public List<RoleEntity> find(RoleQuery query , PageRequest page){
        List<RoleEntity> roleList = roleRepository.find(query,page);
        return roleList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-27 15:28:01.
     */ 
    public Long count(RoleQuery query){
        Long n = roleRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-27 15:28:01.
     */ 
    public RoleEntity getById(String id){
        RoleEntity roleDB = roleRepository.getById(id);
        return roleDB;
    }

    /**
     * 根据实体更新
     * @param role
     * Created by huai23 on 2018-06-27 15:28:01.
     */ 
    public int update(RoleEntity role){
        int n = roleRepository.update(role);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-27 15:28:01.
     */ 
    public int delete(String id){
        int n = roleRepository.delete(id);
        return n;
    }


}


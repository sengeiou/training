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
 * contract 数据库操作类
 * Created by huai23 on 2018-06-06 21:52:04.
 */ 
@Service
public class ContractDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContractRepository contractRepository;

    /**
     * 新增实体
     * @param contract
     * Created by huai23 on 2018-06-06 21:52:04.
     */ 
    public int add(ContractEntity contract){
        int n = contractRepository.add(contract);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-06 21:52:04.
     */ 
    public List<ContractEntity> find(ContractQuery query , PageRequest page){
        List<ContractEntity> contractList = contractRepository.find(query,page);
        return contractList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-06 21:52:04.
     */ 
    public Long count(ContractQuery query){
        Long n = contractRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-06 21:52:04.
     */ 
    public ContractEntity getById(String id){
        ContractEntity contractDB = contractRepository.getById(id);
        return contractDB;
    }

    /**
     * 根据实体更新
     * @param contract
     * Created by huai23 on 2018-06-06 21:52:04.
     */ 
    public int update(ContractEntity contract){
        int n = contractRepository.update(contract);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-06 21:52:04.
     */ 
    public int delete(String id){
        int n = contractRepository.delete(id);
        return n;
    }


}


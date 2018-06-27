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
 * contract_manual 数据库操作类
 * Created by huai23 on 2018-06-28 01:30:49.
 */ 
@Service
public class ContractManualDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContractManualRepository contractManualRepository;

    /**
     * 新增实体
     * @param contractManual
     * Created by huai23 on 2018-06-28 01:30:49.
     */ 
    public int add(ContractManualEntity contractManual){
        int n = contractManualRepository.add(contractManual);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-28 01:30:49.
     */ 
    public List<ContractManualEntity> find(ContractManualQuery query , PageRequest page){
        List<ContractManualEntity> contractManualList = contractManualRepository.find(query,page);
        return contractManualList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-28 01:30:49.
     */ 
    public Long count(ContractManualQuery query){
        Long n = contractManualRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-28 01:30:49.
     */ 
    public ContractManualEntity getById(String id){
        ContractManualEntity contractManualDB = contractManualRepository.getById(id);
        return contractManualDB;
    }

    /**
     * 根据实体更新
     * @param contractManual
     * Created by huai23 on 2018-06-28 01:30:49.
     */ 
    public int update(ContractManualEntity contractManual){
        int n = contractManualRepository.update(contractManual);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-28 01:30:49.
     */ 
    public int delete(String id){
        int n = contractManualRepository.delete(id);
        return n;
    }


}


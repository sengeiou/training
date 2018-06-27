package com.training.service;

import com.training.dao.*;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.training.util.ResponseUtil;
import com.training.util.RequestContextHelper;

import java.util.List;

/**
 * contract_manual 核心业务操作类
 * Created by huai23 on 2018-06-28 01:30:49.
 */ 
@Service
public class ContractManualService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContractManualDao contractManualDao;

    /**
     * 新增实体
     * @param contractManual
     * Created by huai23 on 2018-06-28 01:30:49.
     */ 
    public ResponseEntity<String> add(ContractManualEntity contractManual){
        User user = RequestContextHelper.getUser();
        int n = contractManualDao.add(contractManual);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-28 01:30:49.
     */ 
    public Page<ContractManualEntity> find(ContractManualQuery query , PageRequest page){
        List<ContractManualEntity> contractManualList = contractManualDao.find(query,page);
        Long count = contractManualDao.count(query);
        Page<ContractManualEntity> returnPage = new Page<>();
        returnPage.setContent(contractManualList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-28 01:30:49.
     */ 
    public Long count(ContractManualQuery query){
        Long count = contractManualDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-28 01:30:49.
     */ 
    public ContractManualEntity getById(String id){
        ContractManualEntity contractManualDB = contractManualDao.getById(id);
        return contractManualDB;
    }

    /**
     * 根据实体更新
     * @param contractManual
     * Created by huai23 on 2018-06-28 01:30:49.
     */ 
    public  ResponseEntity<String> update(ContractManualEntity contractManual){
        int n = contractManualDao.update(contractManual);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-28 01:30:49.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = contractManualDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


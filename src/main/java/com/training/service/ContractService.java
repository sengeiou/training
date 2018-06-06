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
 * contract 核心业务操作类
 * Created by huai23 on 2018-06-06 21:52:04.
 */ 
@Service
public class ContractService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContractDao contractDao;

    /**
     * 新增实体
     * @param contract
     * Created by huai23 on 2018-06-06 21:52:04.
     */ 
    public ResponseEntity<String> add(ContractEntity contract){
        User user = RequestContextHelper.getUser();
        int n = contractDao.add(contract);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-06 21:52:04.
     */ 
    public Page<ContractEntity> find(ContractQuery query , PageRequest page){
        List<ContractEntity> contractList = contractDao.find(query,page);
        Long count = contractDao.count(query);
        Page<ContractEntity> returnPage = new Page<>();
        returnPage.setContent(contractList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-06 21:52:04.
     */ 
    public Long count(ContractQuery query){
        Long count = contractDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-06 21:52:04.
     */ 
    public ContractEntity getById(String id){
        ContractEntity contractDB = contractDao.getById(id);
        return contractDB;
    }

    /**
     * 根据实体更新
     * @param contract
     * Created by huai23 on 2018-06-06 21:52:04.
     */ 
    public  ResponseEntity<String> update(ContractEntity contract){
        int n = contractDao.update(contract);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-06 21:52:04.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = contractDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


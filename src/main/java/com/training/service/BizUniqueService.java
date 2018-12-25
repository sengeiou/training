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
 * biz_unique 核心业务操作类
 * Created by huai23 on 2018-12-25 23:24:18.
 */ 
@Service
public class BizUniqueService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BizUniqueDao bizUniqueDao;

    /**
     * 新增实体
     * @param bizUnique
     * Created by huai23 on 2018-12-25 23:24:18.
     */ 
    public ResponseEntity<String> add(BizUniqueEntity bizUnique){
        int n = bizUniqueDao.add(bizUnique);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-12-25 23:24:18.
     */ 
    public Page<BizUniqueEntity> find(BizUniqueQuery query , PageRequest page){
        List<BizUniqueEntity> bizUniqueList = bizUniqueDao.find(query,page);
        Long count = bizUniqueDao.count(query);
        Page<BizUniqueEntity> returnPage = new Page<>();
        returnPage.setContent(bizUniqueList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-12-25 23:24:18.
     */ 
    public Long count(BizUniqueQuery query){
        Long count = bizUniqueDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-12-25 23:24:18.
     */ 
    public BizUniqueEntity getById(String id){
        BizUniqueEntity bizUniqueDB = bizUniqueDao.getById(id);
        return bizUniqueDB;
    }

    /**
     * 根据实体更新
     * @param bizUnique
     * Created by huai23 on 2018-12-25 23:24:18.
     */ 
    public  ResponseEntity<String> update(BizUniqueEntity bizUnique){
        int n = bizUniqueDao.update(bizUnique);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-12-25 23:24:18.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = bizUniqueDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


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
 * origin 核心业务操作类
 * Created by huai23 on 2019-01-12 13:23:55.
 */ 
@Service
public class OriginService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OriginDao originDao;

    /**
     * 新增实体
     * @param origin
     * Created by huai23 on 2019-01-12 13:23:55.
     */ 
    public ResponseEntity<String> add(OriginEntity origin){
        User user = RequestContextHelper.getUser();
        int n = originDao.add(origin);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2019-01-12 13:23:55.
     */ 
    public Page<OriginEntity> find(OriginQuery query , PageRequest page){
        List<OriginEntity> originList = originDao.find(query,page);
        Long count = originDao.count(query);
        Page<OriginEntity> returnPage = new Page<>();
        returnPage.setContent(originList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询全部
     * Created by huai23 on 2019-01-12 13:23:55.
     */
    public List<OriginEntity> findAll(){
        OriginQuery query = new OriginQuery();
        query.setStatus(0);
        PageRequest page = new PageRequest();
        page.setPageSize(10000);
        List<OriginEntity> originList = originDao.find(query,page);
        return originList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-12 13:23:55.
     */ 
    public Long count(OriginQuery query){
        Long count = originDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-12 13:23:55.
     */ 
    public OriginEntity getById(String id){
        OriginEntity originDB = originDao.getById(id);
        return originDB;
    }

    /**
     * 根据实体更新
     * @param origin
     * Created by huai23 on 2019-01-12 13:23:55.
     */ 
    public  ResponseEntity<String> update(OriginEntity origin){
        int n = originDao.update(origin);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-12 13:23:55.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = originDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


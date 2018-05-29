package com.training.service;

import com.training.dao.*;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import com.training.util.IDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.training.util.ResponseUtil;
import com.training.util.RequestContextHelper;

import java.util.List;

/**
 * coach_rest 核心业务操作类
 * Created by huai23 on 2018-05-26 13:55:16.
 */ 
@Service
public class CoachRestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CoachRestDao coachRestDao;

    /**
     * 新增实体
     * @param coachRest
     * Created by huai23 on 2018-05-26 13:55:16.
     */ 
    public ResponseEntity<String> add(CoachRestEntity coachRest){
        User user = RequestContextHelper.getUser();
        coachRest.setRestId(IDUtils.getId());
        int n = coachRestDao.add(coachRest);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:55:16.
     */ 
    public Page<CoachRestEntity> find(CoachRestQuery query , PageRequest page){
        List<CoachRestEntity> coachRestList = coachRestDao.find(query,page);
        Long count = coachRestDao.count(query);
        Page<CoachRestEntity> returnPage = new Page<>();
        returnPage.setContent(coachRestList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:55:16.
     */ 
    public Long count(CoachRestQuery query){
        Long count = coachRestDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:55:16.
     */ 
    public CoachRestEntity getById(String id){
        CoachRestEntity coachRestDB = coachRestDao.getById(id);
        return coachRestDB;
    }

    /**
     * 根据实体更新
     * @param coachRest
     * Created by huai23 on 2018-05-26 13:55:16.
     */ 
    public  ResponseEntity<String> update(CoachRestEntity coachRest){
        int n = coachRestDao.update(coachRest);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:55:16.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = coachRestDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


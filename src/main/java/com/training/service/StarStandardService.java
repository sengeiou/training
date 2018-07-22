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
 * star_standard 核心业务操作类
 * Created by huai23 on 2018-07-22 20:49:43.
 */ 
@Service
public class StarStandardService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StarStandardDao starStandardDao;

    /**
     * 新增实体
     * @param starStandard
     * Created by huai23 on 2018-07-22 20:49:43.
     */ 
    public ResponseEntity<String> add(StarStandardEntity starStandard){
        User user = RequestContextHelper.getUser();
        int n = starStandardDao.add(starStandard);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-07-22 20:49:43.
     */ 
    public Page<StarStandardEntity> find(StarStandardQuery query , PageRequest page){
        List<StarStandardEntity> starStandardList = starStandardDao.find(query,page);
        Long count = starStandardDao.count(query);
        Page<StarStandardEntity> returnPage = new Page<>();
        returnPage.setContent(starStandardList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-22 20:49:43.
     */ 
    public Long count(StarStandardQuery query){
        Long count = starStandardDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-22 20:49:43.
     */ 
    public StarStandardEntity getById(String id){
        StarStandardEntity starStandardDB = starStandardDao.getById(id);
        return starStandardDB;
    }

    /**
     * 根据实体更新
     * @param starStandard
     * Created by huai23 on 2018-07-22 20:49:43.
     */ 
    public  ResponseEntity<String> update(StarStandardEntity starStandard){
        int n = starStandardDao.update(starStandard);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-22 20:49:43.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = starStandardDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


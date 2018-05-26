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
 * medal 核心业务操作类
 * Created by huai23 on 2018-05-26 13:54:27.
 */ 
@Service
public class MedalService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MedalDao medalDao;

    /**
     * 新增实体
     * @param medal
     * Created by huai23 on 2018-05-26 13:54:27.
     */ 
    public ResponseEntity<String> add(MedalEntity medal){
        User user = RequestContextHelper.getUser();
        int n = medalDao.add(medal);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:54:27.
     */ 
    public Page<MedalEntity> find(MedalQuery query , PageRequest page){
        List<MedalEntity> medalList = medalDao.find(query,page);
        Long count = medalDao.count(query);
        Page<MedalEntity> returnPage = new Page<>();
        returnPage.setContent(medalList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:54:27.
     */ 
    public Long count(MedalQuery query){
        Long count = medalDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:54:27.
     */ 
    public MedalEntity getById(String id){
        MedalEntity medalDB = medalDao.getById(id);
        return medalDB;
    }

    /**
     * 根据实体更新
     * @param medal
     * Created by huai23 on 2018-05-26 13:54:27.
     */ 
    public  ResponseEntity<String> update(MedalEntity medal){
        int n = medalDao.update(medal);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:54:27.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = medalDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


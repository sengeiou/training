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
 * rotation_chart 核心业务操作类
 * Created by huai23 on 2018-11-25 10:40:37.
 */ 
@Service
public class RotationChartService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RotationChartDao rotationChartDao;

    /**
     * 新增实体
     * @param rotationChart
     * Created by huai23 on 2018-11-25 10:40:37.
     */ 
    public ResponseEntity<String> add(RotationChartEntity rotationChart){
        User user = RequestContextHelper.getUser();
        int n = rotationChartDao.add(rotationChart);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-11-25 10:40:37.
     */ 
    public Page<RotationChartEntity> find(RotationChartQuery query , PageRequest page){
        List<RotationChartEntity> rotationChartList = rotationChartDao.find(query,page);
        Long count = rotationChartDao.count(query);
        Page<RotationChartEntity> returnPage = new Page<>();
        returnPage.setContent(rotationChartList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-11-25 10:40:37.
     */ 
    public Long count(RotationChartQuery query){
        Long count = rotationChartDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-11-25 10:40:37.
     */ 
    public RotationChartEntity getById(String id){
        RotationChartEntity rotationChartDB = rotationChartDao.getById(id);
        return rotationChartDB;
    }

    /**
     * 根据实体更新
     * @param rotationChart
     * Created by huai23 on 2018-11-25 10:40:37.
     */ 
    public  ResponseEntity<String> update(RotationChartEntity rotationChart){
        int n = rotationChartDao.update(rotationChart);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-11-25 10:40:37.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = rotationChartDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


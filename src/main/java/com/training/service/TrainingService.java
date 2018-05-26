package com.training.service;

import com.training.dao.*;
import com.training.domain.Training;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.training.util.ResponseUtil;
import com.training.util.RequestContextHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * training 核心业务操作类
 * Created by huai23 on 2018-05-26 17:09:14.
 */ 
@Service
public class TrainingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TrainingDao trainingDao;

    /**
     * 新增实体
     * @param training
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    public ResponseEntity<String> add(TrainingEntity training){
        User user = RequestContextHelper.getUser();
        int n = trainingDao.add(training);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    public Page<TrainingEntity> find(TrainingQuery query , PageRequest page){
        List<TrainingEntity> trainingList = trainingDao.find(query,page);
        Long count = trainingDao.count(query);
        Page<TrainingEntity> returnPage = new Page<>();
        returnPage.setContent(trainingList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    public Long count(TrainingQuery query){
        Long count = trainingDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    public TrainingEntity getById(String id){
        TrainingEntity trainingDB = trainingDao.getById(id);
        return trainingDB;
    }

    /**
     * 根据实体更新
     * @param training
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    public  ResponseEntity<String> update(TrainingEntity training){
        int n = trainingDao.update(training);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = trainingDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }

    public ResponseEntity<String> list(TrainingQuery query) {
        if(StringUtils.isEmpty(query.getStartDate())||StringUtils.isEmpty(query.getEndDate())){
            return ResponseUtil.success("请输入起始日期和结束日期");
        }
        List<Training> trainingList = new ArrayList<>();
        Training training = new Training();
        training.setLessonDate(query.getStartDate());
        trainingList.add(training);
        return ResponseUtil.success(trainingList);
    }

}


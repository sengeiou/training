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
 * feedback 数据库操作类
 * Created by huai23 on 2018-05-26 13:54:54.
 */ 
@Service
public class FeedbackDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FeedbackRepository feedbackRepository;

    /**
     * 新增实体
     * @param feedback
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    public int add(FeedbackEntity feedback){
        int n = feedbackRepository.add(feedback);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    public List<FeedbackEntity> find(FeedbackQuery query , PageRequest page){
        List<FeedbackEntity> feedbackList = feedbackRepository.find(query,page);
        return feedbackList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    public Long count(FeedbackQuery query){
        Long n = feedbackRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    public FeedbackEntity getById(String id){
        FeedbackEntity feedbackDB = feedbackRepository.getById(id);
        return feedbackDB;
    }

    /**
     * 根据实体更新
     * @param feedback
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    public int update(FeedbackEntity feedback){
        int n = feedbackRepository.update(feedback);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    public int delete(String id){
        int n = feedbackRepository.delete(id);
        return n;
    }


}


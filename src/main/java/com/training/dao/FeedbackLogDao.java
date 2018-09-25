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
 * feedback_log 数据库操作类
 * Created by huai23 on 2018-09-26 00:21:14.
 */ 
@Service
public class FeedbackLogDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FeedbackLogRepository feedbackLogRepository;

    /**
     * 新增实体
     * @param feedbackLog
     * Created by huai23 on 2018-09-26 00:21:14.
     */ 
    public int add(FeedbackLogEntity feedbackLog){
        int n = feedbackLogRepository.add(feedbackLog);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-09-26 00:21:14.
     */ 
    public List<FeedbackLogEntity> find(FeedbackLogQuery query , PageRequest page){
        List<FeedbackLogEntity> feedbackLogList = feedbackLogRepository.find(query,page);
        return feedbackLogList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-09-26 00:21:14.
     */ 
    public Long count(FeedbackLogQuery query){
        Long n = feedbackLogRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-09-26 00:21:14.
     */ 
    public FeedbackLogEntity getById(String id){
        FeedbackLogEntity feedbackLogDB = feedbackLogRepository.getById(id);
        return feedbackLogDB;
    }

    /**
     * 根据实体更新
     * @param feedbackLog
     * Created by huai23 on 2018-09-26 00:21:14.
     */ 
    public int update(FeedbackLogEntity feedbackLog){
        int n = feedbackLogRepository.update(feedbackLog);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-09-26 00:21:14.
     */ 
    public int delete(String id){
        int n = feedbackLogRepository.delete(id);
        return n;
    }


}


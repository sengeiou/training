package com.training.dao;

import com.training.repository.*;
import com.training.entity.*;
import com.training.common.PageRequest;
import com.training.util.ut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * training 数据库操作类
 * Created by huai23 on 2018-05-26 17:09:14.
 */ 
@Service
public class TrainingDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TrainingRepository trainingRepository;

    /**
     * 新增实体
     * @param training
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    public int add(TrainingEntity training){
        System.out.println(" ===========     "+training.getLessonId());
        int n = trainingRepository.add(training);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    public List<TrainingEntity> find(TrainingQuery query , PageRequest page){
        List<TrainingEntity> trainingList = trainingRepository.find(query,page);
        return trainingList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    public Long count(TrainingQuery query){
        Long n = trainingRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    public TrainingEntity getById(String id){
        TrainingEntity trainingDB = trainingRepository.getById(id);
        return trainingDB;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 17:09:14.
     */
    public List<TrainingEntity> getByLessonId(String id){
        List<TrainingEntity> trainingEntities = trainingRepository.getByLessonId(id);
        return trainingEntities;
    }

    /**
     * 根据实体更新
     * @param training
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    public int update(TrainingEntity training){
        logger.info("  updateTraining  memberId = {} , lessonDate = {} ,trainingData = {}",training.getMemberId(),training.getLessonDate(),training.getTrainingData());
        int n = trainingRepository.update(training);
        return n;
    }

    /**
     * 根据实体更新
     * @param training
     * Created by huai23 on 2018-05-26 17:09:14.
     */
    public int saveTrainingData(TrainingEntity training){
        logger.info("  updateTraining  memberId = {} , lessonDate = {} ,trainingData = {}",training.getMemberId(),training.getLessonDate(),training.getTrainingData());
        int n = trainingRepository.saveTrainingData(training);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    public int delete(String id){
        int n = trainingRepository.delete(id);
        return n;
    }


    public int signIn(TrainingEntity trainingEntity) {
        if(trainingEntity==null){
            return 0;
        }
        trainingEntity.setSignTime(ut.currentTime());
        int n = trainingRepository.signIn(trainingEntity);
        return n;
    }

}


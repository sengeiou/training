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
 * lesson 数据库操作类
 * Created by huai23 on 2018-05-26 17:02:18.
 */ 
@Service
public class LessonDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LessonRepository lessonRepository;

    /**
     * 新增实体
     * @param lesson
     * Created by huai23 on 2018-05-26 17:02:18.
     */ 
    public int add(LessonEntity lesson){
        int n = lessonRepository.add(lesson);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 17:02:18.
     */ 
    public List<LessonEntity> find(LessonQuery query , PageRequest page){
        List<LessonEntity> lessonList = lessonRepository.find(query,page);
        return lessonList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 17:02:18.
     */ 
    public Long count(LessonQuery query){
        Long n = lessonRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 17:02:18.
     */ 
    public LessonEntity getById(String id){
        LessonEntity lessonDB = lessonRepository.getById(id);
        return lessonDB;
    }

    /**
     * 根据实体更新
     * @param lesson
     * Created by huai23 on 2018-05-26 17:02:18.
     */ 
    public int update(LessonEntity lesson){
        int n = lessonRepository.update(lesson);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 17:02:18.
     */ 
    public int delete(String id){
        int n = lessonRepository.delete(id);
        return n;
    }


}


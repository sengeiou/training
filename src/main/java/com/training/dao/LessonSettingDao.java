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
 * lesson_setting 数据库操作类
 * Created by huai23 on 2018-06-16 08:59:33.
 */ 
@Service
public class LessonSettingDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LessonSettingRepository lessonSettingRepository;

    /**
     * 新增实体
     * @param lessonSetting
     * Created by huai23 on 2018-06-16 08:59:33.
     */ 
    public int add(LessonSettingEntity lessonSetting){
        int n = lessonSettingRepository.add(lessonSetting);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-16 08:59:33.
     */ 
    public List<LessonSettingEntity> find(LessonSettingQuery query , PageRequest page){
        List<LessonSettingEntity> lessonSettingList = lessonSettingRepository.find(query,page);
        return lessonSettingList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-16 08:59:33.
     */ 
    public Long count(LessonSettingQuery query){
        Long n = lessonSettingRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-16 08:59:33.
     */ 
    public LessonSettingEntity getById(String id){
        LessonSettingEntity lessonSettingDB = lessonSettingRepository.getById(id);
        return lessonSettingDB;
    }

    /**
     * 根据实体更新
     * @param lessonSetting
     * Created by huai23 on 2018-06-16 08:59:33.
     */ 
    public int update(LessonSettingEntity lessonSetting){
        int n = lessonSettingRepository.update(lessonSetting);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-16 08:59:33.
     */ 
    public int delete(String id){
        int n = lessonSettingRepository.delete(id);
        return n;
    }

}


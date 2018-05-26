package com.training.service;

import com.alibaba.fastjson.JSONObject;
import com.training.dao.*;
import com.training.domain.Lesson;
import com.training.domain.Member;
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

import java.util.ArrayList;
import java.util.List;

/**
 * lesson 核心业务操作类
 * Created by huai23 on 2018-05-26 17:02:19.
 */ 
@Service
public class LessonService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LessonDao lessonDao;

    /**
     * 新增实体
     * @param lesson
     * Created by huai23 on 2018-05-26 17:02:19.
     */ 
    public ResponseEntity<String> add(LessonEntity lesson){
        User user = RequestContextHelper.getUser();
        int n = lessonDao.add(lesson);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 17:02:19.
     */ 
    public Page<LessonEntity> find(LessonQuery query , PageRequest page){
        List<LessonEntity> lessonList = lessonDao.find(query,page);
        Long count = lessonDao.count(query);
        Page<LessonEntity> returnPage = new Page<>();
        returnPage.setContent(lessonList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 17:02:19.
     */ 
    public Long count(LessonQuery query){
        Long count = lessonDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 17:02:19.
     */ 
    public LessonEntity getById(String id){
        LessonEntity lessonDB = lessonDao.getById(id);
        return lessonDB;
    }

    /**
     * 根据实体更新
     * @param lesson
     * Created by huai23 on 2018-05-26 17:02:19.
     */ 
    public  ResponseEntity<String> update(LessonEntity lesson){
        int n = lessonDao.update(lesson);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 17:02:19.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = lessonDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }

    public ResponseEntity<String> schedule(LessonQuery query) {
        Member memberRequest = RequestContextHelper.getMember();
        logger.info(" schedule  memberRequest = {}",memberRequest);
        List<Lesson> lessonList = new ArrayList();
        for (int i = 9; i < 22; i++) {
            Lesson lesson = new Lesson();
            lesson.setLessonId(System.currentTimeMillis()+"");
            lesson.setStartHour(i);
            lesson.setEndHour(i+1);
            lesson.setCoachId("1");
            lesson.setLessonDate(query.getLessonDate());
            if(i%5==0){
                lesson.setQuota(0);
            }else{
                lesson.setQuota(1);
            }
            if(i%3==0){
                lesson.setStatus(-1);
            }else{
                lesson.setStatus(0);
            }
            lessonList.add(lesson);
        }
        JSONObject jo = new JSONObject();
        jo.put("lessonList", lessonList);
        return ResponseUtil.success("查询课程时间表成功",lessonList);
    }

    public ResponseEntity<String> order(Lesson lesson) {
        Member memberRequest = RequestContextHelper.getMember();
        logger.info(" order  memberRequest = {}",memberRequest);
        if(true){
            return ResponseUtil.success("约课失败!该时段课程已约满!");
        }
        return ResponseUtil.success("约课成功");
    }


}


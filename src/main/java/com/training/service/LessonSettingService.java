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
 * lesson_setting 核心业务操作类
 * Created by huai23 on 2018-06-16 08:59:33.
 */ 
@Service
public class LessonSettingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LessonSettingDao lessonSettingDao;

    /**
     * 新增实体
     * @param lessonSetting
     * Created by huai23 on 2018-06-16 08:59:33.
     */ 
    public ResponseEntity<String> add(LessonSettingEntity lessonSetting){
        User user = RequestContextHelper.getUser();
        int n = lessonSettingDao.add(lessonSetting);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-16 08:59:33.
     */ 
    public Page<LessonSettingEntity> find(LessonSettingQuery query , PageRequest page){
        List<LessonSettingEntity> lessonSettingList = lessonSettingDao.find(query,page);
        Long count = lessonSettingDao.count(query);
        Page<LessonSettingEntity> returnPage = new Page<>();
        returnPage.setContent(lessonSettingList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-16 08:59:33.
     */ 
    public Long count(LessonSettingQuery query){
        Long count = lessonSettingDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-16 08:59:33.
     */ 
    public LessonSettingEntity getById(String id){
        LessonSettingEntity lessonSettingDB = lessonSettingDao.getById(id);
        return lessonSettingDB;
    }

    /**
     * 根据实体更新
     * @param lessonSetting
     * Created by huai23 on 2018-06-16 08:59:33.
     */ 
    public  ResponseEntity<String> update(LessonSettingEntity lessonSetting){
        int n = lessonSettingDao.update(lessonSetting);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-16 08:59:33.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = lessonSettingDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


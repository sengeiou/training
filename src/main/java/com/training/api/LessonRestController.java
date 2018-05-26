package com.training.api;

import com.training.domain.Lesson;
import com.training.service.*;
import com.training.entity.*;
import com.training.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.training.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.*;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;

import java.util.List;

/**
 * lesson API控制器
 * Created by huai23 on 2018-05-26 17:02:19.
 */ 
@RestController
@RequestMapping("/api/lesson")
public class LessonRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LessonService lessonService;

    /**
     * 新增实体
     * @param lesson
     * Created by huai23 on 2018-05-26 17:02:19.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody LessonEntity lesson,HttpServletRequest request, HttpServletResponse response){
        logger.info(" lessonRestController  add  lesson = {}",lesson);
        return lessonService.add(lesson);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 17:02:19.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute LessonQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<LessonEntity> page = lessonService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 17:02:19.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute LessonQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = lessonService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 17:02:19.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        LessonEntity lessonDB = lessonService.getById(id);
        if(lessonDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(lessonDB);
    }

    /**
     * 根据实体更新
     * @param lesson
     * Created by huai23 on 2018-05-26 17:02:19.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody LessonEntity lesson,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  lesson = {}",lesson);
        return lessonService.update(lesson);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 17:02:19.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return lessonService.delete(id);
    }

    /**
     * 查询时间表
     * @param query
     * Created by huai23 on 2018-05-26 17:02:19.
     */
    @RequestMapping (value = "schedule", method = RequestMethod.POST)
    public ResponseEntity<String> schedule(@RequestBody LessonQuery query,HttpServletRequest request, HttpServletResponse response){
        logger.info("  schedule  query = {}",query);
        return lessonService.schedule(query);
    }

    /**
     * 约课
     * @param lesson
     * Created by huai23 on 2018-05-26 17:02:19.
     */
    @RequestMapping (value = "order", method = RequestMethod.POST)
    public ResponseEntity<String> order(@RequestBody Lesson lesson, HttpServletRequest request, HttpServletResponse response){
        logger.info("  order  lesson = {}",lesson);
        return lessonService.order(lesson);
    }

}


package com.training.api;

import com.training.domain.Lesson;
import com.training.domain.Training;
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

    /**
     * 约课
     * @param lesson
     * Created by huai23 on 2018-05-26 17:02:19.
     */
    @RequestMapping (value = "cancel", method = RequestMethod.POST)
    public ResponseEntity<String> cancel(@RequestBody Lesson lesson, HttpServletRequest request, HttpServletResponse response){
        logger.info("  cancel  lesson = {}",lesson);
        try {
            return lessonService.cancel(lesson);
        } catch (Exception e) {
            logger.error("  orderCancel  lesson = {} ",lesson,e);
            return ResponseUtil.exception("取消约课异常!"+e.getMessage());
        }
    }

    /**
     * 分页查询
     * @param query
     * Created by huai23 on 2018-05-26 17:09:14.
     */
    @RequestMapping (value = "scheduleCoach", method = RequestMethod.GET)
    public ResponseEntity<String> scheduleCoach(@ModelAttribute LessonQuery query , HttpServletRequest request, HttpServletResponse response){
        logger.info("  scheduleCoach  LessonQuery = {}",query);
        return lessonService.scheduleCoach(query);
    }

}


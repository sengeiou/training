package com.training.api;

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
 * lesson_setting API控制器
 * Created by huai23 on 2018-06-16 08:59:33.
 */ 
@RestController
@RequestMapping("/api/lessonSetting")
public class LessonSettingRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LessonSettingService lessonSettingService;

    /**
     * 新增实体
     * @param lessonSetting
     * Created by huai23 on 2018-06-16 08:59:33.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody LessonSettingEntity lessonSetting,HttpServletRequest request, HttpServletResponse response){
        logger.info(" lesson_settingRestController  add  lessonSetting = {}",lessonSetting);
        return lessonSettingService.add(lessonSetting);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-06-16 08:59:33.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute LessonSettingQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<LessonSettingEntity> page = lessonSettingService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-16 08:59:33.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute LessonSettingQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = lessonSettingService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-16 08:59:33.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        LessonSettingEntity lessonSettingDB = lessonSettingService.getById(id);
        if(lessonSettingDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(lessonSettingDB);
    }

    /**
     * 根据实体更新
     * @param lessonSetting
     * Created by huai23 on 2018-06-16 08:59:33.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody LessonSettingEntity lessonSetting,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  lessonSetting = {}",lessonSetting);
        return lessonSettingService.update(lessonSetting);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-16 08:59:33.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return lessonSettingService.delete(id);
    }


}


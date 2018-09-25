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
 * feedback_log API控制器
 * Created by huai23 on 2018-09-26 00:21:14.
 */ 
@RestController
@RequestMapping("/api/feedbackLog")
public class FeedbackLogRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FeedbackLogService feedbackLogService;

    /**
     * 新增实体
     * @param feedbackLog
     * Created by huai23 on 2018-09-26 00:21:14.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody FeedbackLogEntity feedbackLog,HttpServletRequest request, HttpServletResponse response){
        logger.info(" feedback_logRestController  add  feedbackLog = {}",feedbackLog);
        return feedbackLogService.add(feedbackLog);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-09-26 00:21:14.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute FeedbackLogQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<FeedbackLogEntity> page = feedbackLogService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-09-26 00:21:14.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute FeedbackLogQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = feedbackLogService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-09-26 00:21:14.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        FeedbackLogEntity feedbackLogDB = feedbackLogService.getById(id);
        if(feedbackLogDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(feedbackLogDB);
    }

    /**
     * 根据实体更新
     * @param feedbackLog
     * Created by huai23 on 2018-09-26 00:21:14.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody FeedbackLogEntity feedbackLog,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  feedbackLog = {}",feedbackLog);
        return feedbackLogService.update(feedbackLog);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-09-26 00:21:14.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return feedbackLogService.delete(id);
    }


}


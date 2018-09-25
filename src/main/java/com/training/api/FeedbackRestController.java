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
 * feedback API控制器
 * Created by huai23 on 2018-05-26 13:54:54.
 */ 
@RestController
@RequestMapping("/api/feedback")
public class FeedbackRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 新增实体
     * @param feedback
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody FeedbackEntity feedback,HttpServletRequest request, HttpServletResponse response){
        logger.info(" feedbackRestController  add  feedback = {}",feedback);
        return feedbackService.add(feedback);
    }

    /**
     * 新增实体
     * @param feedback
     * Created by huai23 on 2018-05-26 13:54:54.
     */
    @RequestMapping (value = "changeCoach", method = RequestMethod.POST)
    public ResponseEntity<String> changeCoach(@RequestBody FeedbackEntity feedback,HttpServletRequest request, HttpServletResponse response){
        logger.info(" feedbackRestController  changeCoach  feedback = {}",feedback);
        return feedbackService.changeCoach(feedback);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute FeedbackQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<FeedbackEntity> page = feedbackService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute FeedbackQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = feedbackService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        FeedbackEntity feedbackDB = feedbackService.getById(id);
        if(feedbackDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(feedbackDB);
    }

    /**
     * 根据实体更新
     * @param feedback
     * Created by huai23 on 2018-05-26 13:54:54.
     */
//    @RequestMapping (value = "update", method = RequestMethod.POST)
//    public ResponseEntity<String> update(@RequestBody FeedbackEntity feedback,HttpServletRequest request, HttpServletResponse response){
//        logger.info("  update  feedback = {}",feedback);
//        return feedbackService.update(feedback);
//    }

    /**
     * 根据实体更新
     * @param feedback
     * Created by huai23 on 2018-05-26 13:54:54.
     */
    @RequestMapping (value = "updateTrackTag", method = RequestMethod.POST)
    public ResponseEntity<String> updateTrackTag(@RequestBody FeedbackEntity feedback,HttpServletRequest request, HttpServletResponse response){
        logger.info("  updateTrackTag  feedback = {}",feedback);
        FeedbackEntity update = new FeedbackEntity();
        update.setFeedbackId(feedback.getFeedbackId());
        update.setTrackTag(feedback.getTrackTag());
        return feedbackService.update(update);
    }

    /**
     * 根据实体更新
     * @param feedback
     * Created by huai23 on 2018-05-26 13:54:54.
     */
    @RequestMapping (value = "updateStatus", method = RequestMethod.POST)
    public ResponseEntity<String> updateStatus(@RequestBody FeedbackEntity feedback,HttpServletRequest request, HttpServletResponse response){
        logger.info("  updateStatus  updateStatus = {}",feedback);
        FeedbackEntity update = new FeedbackEntity();
        update.setFeedbackId(feedback.getFeedbackId());
        update.setStatus(feedback.getStatus());
        return feedbackService.update(feedback);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return feedbackService.delete(id);
    }


}


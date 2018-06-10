package com.training.api;

import com.training.domain.Member;
import com.training.domain.Training;
import com.training.service.*;
import com.training.entity.*;
import com.training.common.*;
import com.training.util.RequestContextHelper;
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
 * training API控制器
 * Created by huai23 on 2018-05-26 17:09:14.
 */ 
@RestController
@RequestMapping("/api/training")
public class TrainingRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TrainingService trainingService;

    /**
     * 新增实体
     * @param training
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody TrainingEntity training,HttpServletRequest request, HttpServletResponse response){
        logger.info(" trainingRestController  add  training = {}",training);
        return trainingService.add(training);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute TrainingQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        query.setStatus(0);
        Page<TrainingEntity> page = trainingService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute TrainingQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = trainingService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        Training training = trainingService.getById(id);
        if(training==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(training);
    }

    /**
     * 根据实体更新
     * @param training
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody TrainingEntity training,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  training = {}",training);
        return trainingService.update(training);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return trainingService.delete(id);
    }

    /**
     * 查询训练课程
     * @param query
     * Created by huai23 on 2018-05-26 17:09:14.
     */
    @RequestMapping (value = "list", method = RequestMethod.POST)
    public ResponseEntity<String> list(@RequestBody TrainingQuery query ,HttpServletRequest request, HttpServletResponse response){
        logger.info(" list  query = {}",query);
        Member memberRequest = RequestContextHelper.getMember();
        logger.info(" list  memberRequest = {}",memberRequest);
        return trainingService.list(query);
    }


    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 17:09:14.
     */
    @RequestMapping (value = "findByStaff", method = RequestMethod.GET)
    public ResponseEntity<String> findByStaff(@ModelAttribute TrainingQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<Training> page = trainingService.findByStaff(query,pageRequest);
        return ResponseUtil.success(page);
    }

}


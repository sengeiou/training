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
 * group_order_log API控制器
 * Created by huai23 on 2019-01-30 23:16:13.
 */ 
@RestController
@RequestMapping("/api/groupOrderLog")
public class GroupOrderLogRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupOrderLogService groupOrderLogService;

    /**
     * 新增实体
     * @param groupOrderLog
     * Created by huai23 on 2019-01-30 23:16:13.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody GroupOrderLogEntity groupOrderLog,HttpServletRequest request, HttpServletResponse response){
        logger.info(" group_order_logRestController  add  groupOrderLog = {}",groupOrderLog);
        return groupOrderLogService.add(groupOrderLog);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2019-01-30 23:16:13.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute GroupOrderLogQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<GroupOrderLogEntity> page = groupOrderLogService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-30 23:16:13.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute GroupOrderLogQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = groupOrderLogService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-30 23:16:13.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        GroupOrderLogEntity groupOrderLogDB = groupOrderLogService.getById(id);
        if(groupOrderLogDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(groupOrderLogDB);
    }

    /**
     * 根据实体更新
     * @param groupOrderLog
     * Created by huai23 on 2019-01-30 23:16:13.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody GroupOrderLogEntity groupOrderLog,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  groupOrderLog = {}",groupOrderLog);
        return groupOrderLogService.update(groupOrderLog);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-30 23:16:13.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return groupOrderLogService.delete(id);
    }


}


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
 * sms_log API控制器
 * Created by huai23 on 2018-10-24 08:29:13.
 */ 
@RestController
@RequestMapping("/api/smsLog")
public class SmsLogRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SmsLogService smsLogService;

    /**
     * 新增实体
     * @param smsLog
     * Created by huai23 on 2018-10-24 08:29:13.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody SmsLogEntity smsLog,HttpServletRequest request, HttpServletResponse response){
        logger.info(" sms_logRestController  add  smsLog = {}",smsLog);
        return smsLogService.add(smsLog);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-10-24 08:29:13.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute SmsLogQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<SmsLogEntity> page = smsLogService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-10-24 08:29:13.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute SmsLogQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = smsLogService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-10-24 08:29:13.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        SmsLogEntity smsLogDB = smsLogService.getById(id);
        if(smsLogDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(smsLogDB);
    }

    /**
     * 根据实体更新
     * @param smsLog
     * Created by huai23 on 2018-10-24 08:29:13.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody SmsLogEntity smsLog,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  smsLog = {}",smsLog);
        return smsLogService.update(smsLog);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-10-24 08:29:13.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return smsLogService.delete(id);
    }


}


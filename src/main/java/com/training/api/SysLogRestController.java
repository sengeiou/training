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
 * sys_log API控制器
 * Created by huai23 on 2018-06-03 15:57:51.
 */ 
@RestController
@RequestMapping("/api/sysLog")
public class SysLogRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysLogService sysLogService;

    /**
     * 新增实体
     * @param sysLog
     * Created by huai23 on 2018-06-03 15:57:51.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody SysLogEntity sysLog,HttpServletRequest request, HttpServletResponse response){
        logger.info(" sys_logRestController  add  sysLog = {}",sysLog);
        return sysLogService.add(sysLog);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-06-03 15:57:51.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute SysLogQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<SysLogEntity> page = sysLogService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-03 15:57:51.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute SysLogQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = sysLogService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-03 15:57:51.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        SysLogEntity sysLogDB = sysLogService.getById(id);
        if(sysLogDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(sysLogDB);
    }

    /**
     * 根据实体更新
     * @param sysLog
     * Created by huai23 on 2018-06-03 15:57:51.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody SysLogEntity sysLog,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  sysLog = {}",sysLog);
        return sysLogService.update(sysLog);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-03 15:57:51.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return sysLogService.delete(id);
    }

    /**
     * 分页查询延期日志
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-06-03 15:57:51.
     */
    @RequestMapping (value = "findDelayLog", method = RequestMethod.GET)
    public ResponseEntity<String> findDelayLog(@ModelAttribute SysLogQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        logger.info("  findDelayLog  SysLogQuery = {}",query);
        Page<SysLogEntity> page = sysLogService.findDelayLog(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 分页查询更换教练日志
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-06-03 15:57:51.
     */
    @RequestMapping (value = "findChangeCoachLog", method = RequestMethod.GET)
    public ResponseEntity<String> findChangeCoachLog(@ModelAttribute SysLogQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<SysLogEntity> page = sysLogService.findChangeCoachLog(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 分页查询微信支付流水日志
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-06-03 15:57:51.
     */
    @RequestMapping (value = "findPayLog", method = RequestMethod.GET)
    public ResponseEntity<String> findPayLog(@ModelAttribute SysLogQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<SysLogEntity> page = sysLogService.findPayLog(query,pageRequest);
        return ResponseUtil.success(page);
    }


}


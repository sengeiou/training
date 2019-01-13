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
 * wxpay_log API控制器
 * Created by huai23 on 2019-01-13 20:31:57.
 */ 
@RestController
@RequestMapping("/api/wxpayLog")
public class WxpayLogRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxpayLogService wxpayLogService;

    /**
     * 新增实体
     * @param wxpayLog
     * Created by huai23 on 2019-01-13 20:31:57.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody WxpayLogEntity wxpayLog,HttpServletRequest request, HttpServletResponse response){
        logger.info(" wxpay_logRestController  add  wxpayLog = {}",wxpayLog);
        return wxpayLogService.add(wxpayLog);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2019-01-13 20:31:57.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute WxpayLogQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<WxpayLogEntity> page = wxpayLogService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-13 20:31:57.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute WxpayLogQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = wxpayLogService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-13 20:31:57.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        WxpayLogEntity wxpayLogDB = wxpayLogService.getById(id);
        if(wxpayLogDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(wxpayLogDB);
    }

    /**
     * 根据实体更新
     * @param wxpayLog
     * Created by huai23 on 2019-01-13 20:31:57.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody WxpayLogEntity wxpayLog,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  wxpayLog = {}",wxpayLog);
        return wxpayLogService.update(wxpayLog);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-13 20:31:57.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return wxpayLogService.delete(id);
    }


}


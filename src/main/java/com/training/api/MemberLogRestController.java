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
 * member_log API控制器
 * Created by huai23 on 2018-06-09 09:16:23.
 */ 
@RestController
@RequestMapping("/api/memberLog")
public class MemberLogRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberLogService memberLogService;

    /**
     * 新增实体
     * @param memberLog
     * Created by huai23 on 2018-06-09 09:16:23.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody MemberLogEntity memberLog,HttpServletRequest request, HttpServletResponse response){
        logger.info(" member_logRestController  add  memberLog = {}",memberLog);
        return memberLogService.add(memberLog);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-06-09 09:16:23.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute MemberLogQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<MemberLogEntity> page = memberLogService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-09 09:16:23.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute MemberLogQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = memberLogService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-09 09:16:23.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        MemberLogEntity memberLogDB = memberLogService.getById(id);
        if(memberLogDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(memberLogDB);
    }

    /**
     * 根据实体更新
     * @param memberLog
     * Created by huai23 on 2018-06-09 09:16:23.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody MemberLogEntity memberLog,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  memberLog = {}",memberLog);
        return memberLogService.update(memberLog);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-09 09:16:23.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return memberLogService.delete(id);
    }


}


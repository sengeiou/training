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
 * member_event API控制器
 * Created by huai23 on 2018-07-31 07:36:32.
 */ 
@RestController
@RequestMapping("/api/memberEvent")
public class MemberEventRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberEventService memberEventService;

    /**
     * 新增实体
     * @param memberEvent
     * Created by huai23 on 2018-07-31 07:36:32.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody MemberEventEntity memberEvent,HttpServletRequest request, HttpServletResponse response){
        logger.info(" member_eventRestController  add  memberEvent = {}",memberEvent);
        return memberEventService.add(memberEvent);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-07-31 07:36:32.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute MemberEventQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<MemberEventEntity> page = memberEventService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-31 07:36:32.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute MemberEventQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = memberEventService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-31 07:36:32.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        MemberEventEntity memberEventDB = memberEventService.getById(id);
        if(memberEventDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(memberEventDB);
    }

    /**
     * 根据实体更新
     * @param memberEvent
     * Created by huai23 on 2018-07-31 07:36:32.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody MemberEventEntity memberEvent,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  memberEvent = {}",memberEvent);
        return memberEventService.update(memberEvent);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-31 07:36:32.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return memberEventService.delete(id);
    }


}


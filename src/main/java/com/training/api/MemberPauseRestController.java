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
 * member_pause API控制器
 * Created by huai23 on 2018-06-27 20:52:15.
 */ 
@RestController
@RequestMapping("/api/memberPause")
public class MemberPauseRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberPauseService memberPauseService;

    /**
     * 新增实体
     * @param memberPause
     * Created by huai23 on 2018-06-27 20:52:15.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody MemberPauseEntity memberPause,HttpServletRequest request, HttpServletResponse response){
        logger.info(" member_pauseRestController  add  memberPause = {}",memberPause);
        return memberPauseService.add(memberPause);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-06-27 20:52:15.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute MemberPauseQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        logger.info(" member_pauseRestController  find  query = {}",query);
        Page<MemberPauseEntity> page = memberPauseService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-27 20:52:15.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute MemberPauseQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = memberPauseService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-27 20:52:15.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        MemberPauseEntity memberPauseDB = memberPauseService.getById(id);
        if(memberPauseDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(memberPauseDB);
    }

    /**
     * 根据实体更新
     * @param memberPause
     * Created by huai23 on 2018-06-27 20:52:15.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody MemberPauseEntity memberPause,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  memberPause = {}",memberPause);
        return memberPauseService.update(memberPause);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-27 20:52:15.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return memberPauseService.delete(id);
    }


}


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
 * medal API控制器
 * Created by huai23 on 2018-07-24 22:48:27.
 */ 
@RestController
@RequestMapping("/api/medal")
public class MedalRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MedalService medalService;

    /**
     * 新增实体
     * @param medal
     * Created by huai23 on 2018-07-24 22:48:27.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody MedalEntity medal,HttpServletRequest request, HttpServletResponse response){
        logger.info(" medalRestController  add  medal = {}",medal);
        return medalService.add(medal);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-07-24 22:48:27.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute MedalQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        logger.info(" medalRestController  find  MedalQuery = {}",query);
        Page<MedalEntity> page = medalService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-24 22:48:27.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute MedalQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = medalService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-24 22:48:27.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        MedalEntity medalDB = medalService.getById(id);
        if(medalDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(medalDB);
    }

    /**
     * 根据实体更新
     * @param medal
     * Created by huai23 on 2018-07-24 22:48:27.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody MedalEntity medal,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  medal = {}",medal);
        return medalService.update(medal);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-24 22:48:27.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return medalService.delete(id);
    }


}


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
 * origin API控制器
 * Created by huai23 on 2019-01-12 13:23:55.
 */ 
@RestController
@RequestMapping("/api/origin")
public class OriginRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OriginService originService;

    /**
     * 新增实体
     * @param origin
     * Created by huai23 on 2019-01-12 13:23:55.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody OriginEntity origin,HttpServletRequest request, HttpServletResponse response){
        logger.info(" originRestController  add  origin = {}",origin);
        return originService.add(origin);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2019-01-12 13:23:55.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute OriginQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<OriginEntity> page = originService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2019-01-12 13:23:55.
     */
    @RequestMapping (value = "findAll", method = RequestMethod.GET)
    public ResponseEntity<String> findAll(@ModelAttribute OriginQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        List<OriginEntity> page = originService.findAll();
        return ResponseUtil.success(page);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-12 13:23:55.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        OriginEntity originDB = originService.getById(id);
        if(originDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(originDB);
    }

    /**
     * 根据实体更新
     * @param origin
     * Created by huai23 on 2019-01-12 13:23:55.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody OriginEntity origin,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  origin = {}",origin);
        return originService.update(origin);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-12 13:23:55.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return originService.delete(id);
    }


}


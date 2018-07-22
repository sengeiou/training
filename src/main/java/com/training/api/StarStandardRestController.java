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
 * star_standard API控制器
 * Created by huai23 on 2018-07-22 20:49:43.
 */ 
@RestController
@RequestMapping("/api/starStandard")
public class StarStandardRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StarStandardService starStandardService;

    /**
     * 新增实体
     * @param starStandard
     * Created by huai23 on 2018-07-22 20:49:43.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody StarStandardEntity starStandard,HttpServletRequest request, HttpServletResponse response){
        logger.info(" star_standardRestController  add  starStandard = {}",starStandard);
        return starStandardService.add(starStandard);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-07-22 20:49:43.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute StarStandardQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<StarStandardEntity> page = starStandardService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-22 20:49:43.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute StarStandardQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = starStandardService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-22 20:49:43.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        StarStandardEntity starStandardDB = starStandardService.getById(id);
        if(starStandardDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(starStandardDB);
    }

    /**
     * 根据实体更新
     * @param starStandard
     * Created by huai23 on 2018-07-22 20:49:43.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody StarStandardEntity starStandard,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  starStandard = {}",starStandard);
        return starStandardService.update(starStandard);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-22 20:49:43.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return starStandardService.delete(id);
    }


}


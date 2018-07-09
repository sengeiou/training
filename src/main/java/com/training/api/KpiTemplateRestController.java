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
 * kpi_template API控制器
 * Created by huai23 on 2018-07-09 22:42:32.
 */ 
@RestController
@RequestMapping("/api/manage/kpiTemplate")
public class KpiTemplateRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KpiTemplateService kpiTemplateService;

    /**
     * 新增实体
     * @param kpiTemplate
     * Created by huai23 on 2018-07-09 22:42:32.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody KpiTemplateEntity kpiTemplate,HttpServletRequest request, HttpServletResponse response){
        logger.info(" kpi_templateRestController  add  kpiTemplate = {}",kpiTemplate);
        return kpiTemplateService.add(kpiTemplate);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-07-09 22:42:32.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute KpiTemplateQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<KpiTemplateEntity> page = kpiTemplateService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-09 22:42:32.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute KpiTemplateQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = kpiTemplateService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-09 22:42:32.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        KpiTemplateEntity kpiTemplateDB = kpiTemplateService.getById(id);
        if(kpiTemplateDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(kpiTemplateDB);
    }

    /**
     * 根据实体更新
     * @param kpiTemplate
     * Created by huai23 on 2018-07-09 22:42:32.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody KpiTemplateEntity kpiTemplate,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  kpiTemplate = {}",kpiTemplate);
        return kpiTemplateService.update(kpiTemplate);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-09 22:42:32.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return kpiTemplateService.delete(id);
    }


}


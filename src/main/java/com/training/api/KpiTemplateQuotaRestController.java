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
 * kpi_template_quota API控制器
 * Created by huai23 on 2018-07-09 22:42:58.
 */ 
@RestController
@RequestMapping("/api/manage/kpiTemplateQuota")
public class KpiTemplateQuotaRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KpiTemplateQuotaService kpiTemplateQuotaService;

    /**
     * 新增实体
     * @param kpiTemplateQuota
     * Created by huai23 on 2018-07-09 22:42:58.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody KpiTemplateQuotaEntity kpiTemplateQuota,HttpServletRequest request, HttpServletResponse response){
        logger.info(" kpi_template_quotaRestController  add  kpiTemplateQuota = {}",kpiTemplateQuota);
        return kpiTemplateQuotaService.add(kpiTemplateQuota);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-07-09 22:42:58.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute KpiTemplateQuotaQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<KpiTemplateQuotaEntity> page = kpiTemplateQuotaService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-09 22:42:58.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute KpiTemplateQuotaQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = kpiTemplateQuotaService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-09 22:42:58.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        KpiTemplateQuotaEntity kpiTemplateQuotaDB = kpiTemplateQuotaService.getById(id);
        if(kpiTemplateQuotaDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(kpiTemplateQuotaDB);
    }

    /**
     * 根据实体更新
     * @param kpiTemplateQuota
     * Created by huai23 on 2018-07-09 22:42:58.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody KpiTemplateQuotaEntity kpiTemplateQuota,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  kpiTemplateQuota = {}",kpiTemplateQuota);
        return kpiTemplateQuotaService.update(kpiTemplateQuota);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-09 22:42:58.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return kpiTemplateQuotaService.delete(id);
    }


}


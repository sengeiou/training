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
 * kpi_staff_detail API控制器
 * Created by huai23 on 2018-11-18 12:13:37.
 */ 
@RestController
@RequestMapping("/api/kpiStaffDetail")
public class KpiStaffDetailRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KpiStaffDetailService kpiStaffDetailService;

    /**
     * 新增实体
     * @param kpiStaffDetail
     * Created by huai23 on 2018-11-18 12:13:37.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody KpiStaffDetailEntity kpiStaffDetail,HttpServletRequest request, HttpServletResponse response){
        logger.info(" kpi_staff_detailRestController  add  kpiStaffDetail = {}",kpiStaffDetail);
        return kpiStaffDetailService.add(kpiStaffDetail);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-11-18 12:13:37.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute KpiStaffDetailQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<KpiStaffDetailEntity> page = kpiStaffDetailService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-11-18 12:13:37.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute KpiStaffDetailQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = kpiStaffDetailService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-11-18 12:13:37.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        KpiStaffDetailEntity kpiStaffDetailDB = kpiStaffDetailService.getById(id);
        if(kpiStaffDetailDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(kpiStaffDetailDB);
    }

    /**
     * 根据实体更新
     * @param kpiStaffDetail
     * Created by huai23 on 2018-11-18 12:13:37.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody KpiStaffDetailEntity kpiStaffDetail,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  kpiStaffDetail = {}",kpiStaffDetail);
        return kpiStaffDetailService.update(kpiStaffDetail);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-11-18 12:13:37.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return kpiStaffDetailService.delete(id);
    }


}


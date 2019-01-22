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
 * measurement API控制器
 * Created by huai23 on 2019-01-22 18:13:44.
 */ 
@RestController
@RequestMapping("/api/measurement")
public class MeasurementRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MeasurementService measurementService;

    /**
     * 新增实体
     * @param measurement
     * Created by huai23 on 2019-01-22 18:13:44.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody MeasurementEntity measurement,HttpServletRequest request, HttpServletResponse response){
        logger.info(" measurementRestController  add  measurement = {}",measurement);
        return measurementService.add(measurement);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2019-01-22 18:13:44.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute MeasurementQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<MeasurementEntity> page = measurementService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-22 18:13:44.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute MeasurementQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = measurementService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-22 18:13:44.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        MeasurementEntity measurementDB = measurementService.getById(id);
        if(measurementDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(measurementDB);
    }

    /**
     * 根据实体更新
     * @param measurement
     * Created by huai23 on 2019-01-22 18:13:44.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody MeasurementEntity measurement,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  measurement = {}",measurement);
        return measurementService.update(measurement);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-22 18:13:44.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return measurementService.delete(id);
    }


}


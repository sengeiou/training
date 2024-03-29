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
 * rotation_chart API控制器
 * Created by huai23 on 2018-11-25 10:40:37.
 */ 
@RestController
@RequestMapping("/api/rotationChart")
public class RotationChartRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RotationChartService rotationChartService;

    /**
     * 新增实体
     * @param rotationChart
     * Created by huai23 on 2018-11-25 10:40:37.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody RotationChartEntity rotationChart,HttpServletRequest request, HttpServletResponse response){
        logger.info(" rotation_chartRestController  add  rotationChart = {}",rotationChart);
        return rotationChartService.add(rotationChart);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-11-25 10:40:37.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute RotationChartQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<RotationChartEntity> page = rotationChartService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-11-25 10:40:37.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute RotationChartQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = rotationChartService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-11-25 10:40:37.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        RotationChartEntity rotationChartDB = rotationChartService.getById(id);
        if(rotationChartDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(rotationChartDB);
    }

    /**
     * 根据实体更新
     * @param rotationChart
     * Created by huai23 on 2018-11-25 10:40:37.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody RotationChartEntity rotationChart,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  rotationChart = {}",rotationChart);
        rotationChart.setChartId(rotationChart.getRotationId());
        return rotationChartService.update(rotationChart);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-11-25 10:40:37.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return rotationChartService.delete(id);
    }


}


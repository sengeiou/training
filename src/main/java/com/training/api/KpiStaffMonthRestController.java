package com.training.api;

import com.training.domain.KpiStaffMonth;
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
 * kpi_staff_month API控制器
 * Created by huai23 on 2018-07-13 23:24:53.
 */ 
@RestController
@RequestMapping("/api/kpiStaffMonth")
public class KpiStaffMonthRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KpiStaffMonthService kpiStaffMonthService;

    /**
     * 新增实体
     * @param kpiStaffMonth
     * Created by huai23 on 2018-07-13 23:24:53.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody KpiStaffMonthEntity kpiStaffMonth,HttpServletRequest request, HttpServletResponse response){
        logger.info(" kpi_staff_monthRestController  add  kpiStaffMonth = {}",kpiStaffMonth);
        return kpiStaffMonthService.add(kpiStaffMonth);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-07-13 23:24:53.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute KpiStaffMonthQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        logger.info(" kpi_staff_monthRestController  find  KpiStaffMonthQuery = {}",query);
        Page<KpiStaffMonth> page = kpiStaffMonthService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-13 23:24:53.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute KpiStaffMonthQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = kpiStaffMonthService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * Created by huai23 on 2018-07-13 23:24:53.
     */ 
    @RequestMapping (value = "getByIdAndMonth", method = RequestMethod.GET)
    public ResponseEntity<String> getByIdAndMonth(@ModelAttribute KpiStaffMonth kpiStaffMonth,HttpServletRequest request, HttpServletResponse response){
        KpiStaffMonth kpiStaffMonthDB = kpiStaffMonthService.getByIdAndMonth(kpiStaffMonth.getStaffId(),kpiStaffMonth.getMonth());
        if(kpiStaffMonthDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(kpiStaffMonthDB);
    }

    /**
     * 根据实体更新
     * @param kpiStaffMonth
     * Created by huai23 on 2018-07-13 23:24:53.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody KpiStaffMonthEntity kpiStaffMonth,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  kpiStaffMonth = {}",kpiStaffMonth);
        return kpiStaffMonthService.update(kpiStaffMonth);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-13 23:24:53.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return kpiStaffMonthService.delete(id);
    }


}


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
 * finance_staff_report API控制器
 * Created by huai23 on 2018-12-02 20:58:24.
 */ 
@RestController
@RequestMapping("/api/financeStaffReport")
public class FinanceStaffReportRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FinanceStaffReportService financeStaffReportService;

    /**
     * 新增实体
     * @param financeStaffReport
     * Created by huai23 on 2018-12-02 20:58:24.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody FinanceStaffReportEntity financeStaffReport,HttpServletRequest request, HttpServletResponse response){
        logger.info(" finance_staff_reportRestController  add  financeStaffReport = {}",financeStaffReport);
        return financeStaffReportService.add(financeStaffReport);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-12-02 20:58:24.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute FinanceStaffReportQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<FinanceStaffReportEntity> page = financeStaffReportService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-12-02 20:58:24.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute FinanceStaffReportQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = financeStaffReportService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-12-02 20:58:24.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        FinanceStaffReportEntity financeStaffReportDB = financeStaffReportService.getById(id);
        if(financeStaffReportDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(financeStaffReportDB);
    }

    /**
     * 根据实体更新
     * @param financeStaffReport
     * Created by huai23 on 2018-12-02 20:58:24.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody FinanceStaffReportEntity financeStaffReport,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  financeStaffReport = {}",financeStaffReport);
        return financeStaffReportService.update(financeStaffReport);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-12-02 20:58:24.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return financeStaffReportService.delete(id);
    }


}


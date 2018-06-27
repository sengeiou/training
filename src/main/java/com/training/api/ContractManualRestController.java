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
 * contract_manual API控制器
 * Created by huai23 on 2018-06-28 02:06:09.
 */ 
@RestController
@RequestMapping("/api/contractManual")
public class ContractManualRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContractManualService contractManualService;

    /**
     * 新增实体
     * @param contractManual
     * Created by huai23 on 2018-06-28 02:06:09.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody ContractManualEntity contractManual,HttpServletRequest request, HttpServletResponse response){
        logger.info(" contract_manualRestController  add  contractManual = {}",contractManual);
        return contractManualService.add(contractManual);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-06-28 02:06:09.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute ContractManualQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<ContractManualEntity> page = contractManualService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-28 02:06:09.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute ContractManualQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = contractManualService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-28 02:06:09.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        ContractManualEntity contractManualDB = contractManualService.getById(id);
        if(contractManualDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(contractManualDB);
    }

    /**
     * 根据实体更新
     * @param contractManual
     * Created by huai23 on 2018-06-28 02:06:09.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody ContractManualEntity contractManual,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  contractManual = {}",contractManual);
        return contractManualService.update(contractManual);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-28 02:06:09.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return contractManualService.delete(id);
    }


}


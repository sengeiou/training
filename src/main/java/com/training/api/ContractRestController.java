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
 * contract API控制器
 * Created by huai23 on 2018-06-06 21:52:04.
 */ 
@RestController
@RequestMapping("/api/contract")
public class ContractRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContractService contractService;

    /**
     * 新增实体
     * @param contract
     * Created by huai23 on 2018-06-06 21:52:04.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody ContractEntity contract,HttpServletRequest request, HttpServletResponse response){
        logger.info(" contractRestController  add  contract = {}",contract);
        return contractService.add(contract);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-06-06 21:52:04.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute ContractQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<ContractEntity> page = contractService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-06 21:52:04.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute ContractQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = contractService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-06 21:52:04.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        ContractEntity contractDB = contractService.getById(id);
        if(contractDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(contractDB);
    }

    /**
     * 根据实体更新
     * @param contract
     * Created by huai23 on 2018-06-06 21:52:04.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody ContractEntity contract,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  contract = {}",contract);
        return contractService.update(contract);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-06 21:52:04.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return contractService.delete(id);
    }


}


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
 * group_buy API控制器
 * Created by huai23 on 2019-01-30 22:53:15.
 */ 
@RestController
@RequestMapping("/api/groupBuy")
public class GroupBuyRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupBuyService groupBuyService;

    /**
     * 新增实体
     * @param groupBuy
     * Created by huai23 on 2019-01-30 22:53:15.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody GroupBuyEntity groupBuy,HttpServletRequest request, HttpServletResponse response){
        logger.info(" group_buyRestController  add  groupBuy = {}",groupBuy);
        return groupBuyService.add(groupBuy);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2019-01-30 22:53:15.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute GroupBuyQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<GroupBuyEntity> page = groupBuyService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-30 22:53:15.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute GroupBuyQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = groupBuyService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-30 22:53:15.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        GroupBuyEntity groupBuyDB = groupBuyService.getById(id);
        if(groupBuyDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(groupBuyDB);
    }

    /**
     * 根据实体更新
     * @param groupBuy
     * Created by huai23 on 2019-01-30 22:53:15.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody GroupBuyEntity groupBuy,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  groupBuy = {}",groupBuy);
        return groupBuyService.update(groupBuy);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-30 22:53:15.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return groupBuyService.delete(id);
    }


}


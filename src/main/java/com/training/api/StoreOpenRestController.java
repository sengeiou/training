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
 * store_open API控制器
 * Created by huai23 on 2018-07-10 20:40:20.
 */ 
@RestController
@RequestMapping("/api/manage/storeOpen")
public class StoreOpenRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StoreOpenService storeOpenService;

    /**
     * 新增实体
     * @param storeOpen
     * Created by huai23 on 2018-07-10 20:40:20.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody StoreOpenEntity storeOpen,HttpServletRequest request, HttpServletResponse response){
        logger.info(" store_openRestController  add  storeOpen = {}",storeOpen);
        return storeOpenService.add(storeOpen);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-07-10 20:40:20.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute StoreOpenQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<StoreOpenEntity> page = storeOpenService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-10 20:40:20.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute StoreOpenQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = storeOpenService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param query
     * Created by huai23 on 2018-07-10 20:40:20.
     */ 
    @RequestMapping (value = "getById", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@ModelAttribute StoreOpenQuery query,HttpServletRequest request, HttpServletResponse response){
        StoreOpenEntity storeOpenDB = storeOpenService.getById(query.getStoreId(),query.getYear());
        if(storeOpenDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(storeOpenDB);
    }

    /**
     * 根据实体更新
     * @param storeOpen
     * Created by huai23 on 2018-07-10 20:40:20.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody StoreOpenEntity storeOpen,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  storeOpen = {}",storeOpen);
        return storeOpenService.update(storeOpen);
    }

    /**
     * 根据ID删除
     * @param storeOpen
     * Created by huai23 on 2018-07-10 20:40:20.
     */ 
    @RequestMapping (value = "delete", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@RequestBody StoreOpenEntity storeOpen,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  storeOpen = {}",storeOpen);
        return storeOpenService.delete(storeOpen.getStoreId(),storeOpen.getYear());
    }


}


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
 * store API控制器
 * Created by huai23 on 2018-05-26 13:43:38.
 */ 
@RestController
@RequestMapping("/api/store")
public class StoreRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StoreService storeService;

    /**
     * 新增实体
     * @param store
     * Created by huai23 on 2018-05-26 13:43:38.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody StoreEntity store,HttpServletRequest request, HttpServletResponse response){
        logger.info(" storeRestController  add  store = {}",store);
        return storeService.add(store);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 13:43:38.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute StoreQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<StoreEntity> page = storeService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:43:38.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute StoreQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = storeService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:43:38.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        StoreEntity storeDB = storeService.getById(id);
        if(storeDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(storeDB);
    }

    /**
     * 根据实体更新
     * @param store
     * Created by huai23 on 2018-05-26 13:43:38.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody StoreEntity store,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  store = {}",store);
        return storeService.update(store);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:43:38.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return storeService.delete(id);
    }


}


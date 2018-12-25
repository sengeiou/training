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
 * biz_unique API控制器
 * Created by huai23 on 2018-12-25 23:24:18.
 */ 
@RestController
@RequestMapping("/api/bizUnique")
public class BizUniqueRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BizUniqueService bizUniqueService;

    /**
     * 新增实体
     * @param bizUnique
     * Created by huai23 on 2018-12-25 23:24:18.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody BizUniqueEntity bizUnique,HttpServletRequest request, HttpServletResponse response){
        logger.info(" biz_uniqueRestController  add  bizUnique = {}",bizUnique);
        return bizUniqueService.add(bizUnique);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-12-25 23:24:18.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute BizUniqueQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<BizUniqueEntity> page = bizUniqueService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-12-25 23:24:18.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute BizUniqueQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = bizUniqueService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-12-25 23:24:18.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        BizUniqueEntity bizUniqueDB = bizUniqueService.getById(id);
        if(bizUniqueDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(bizUniqueDB);
    }

    /**
     * 根据实体更新
     * @param bizUnique
     * Created by huai23 on 2018-12-25 23:24:18.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody BizUniqueEntity bizUnique,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  bizUnique = {}",bizUnique);
        return bizUniqueService.update(bizUnique);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-12-25 23:24:18.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return bizUniqueService.delete(id);
    }


}


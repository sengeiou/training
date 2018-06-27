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
 * role API控制器
 * Created by huai23 on 2018-06-27 15:28:01.
 */ 
@RestController
@RequestMapping("/api/role")
public class RoleRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleService roleService;

    /**
     * 新增实体
     * @param role
     * Created by huai23 on 2018-06-27 15:28:01.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody RoleEntity role,HttpServletRequest request, HttpServletResponse response){
        logger.info(" roleRestController  add  role = {}",role);
        return roleService.add(role);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-06-27 15:28:01.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute RoleQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<RoleEntity> page = roleService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-27 15:28:01.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute RoleQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = roleService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-27 15:28:01.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        RoleEntity roleDB = roleService.getById(id);
        if(roleDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(roleDB);
    }

    /**
     * 根据实体更新
     * @param role
     * Created by huai23 on 2018-06-27 15:28:01.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody RoleEntity role,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  role = {}",role);
        return roleService.update(role);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-27 15:28:01.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return roleService.delete(id);
    }


}


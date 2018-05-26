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
 * member_body API控制器
 * Created by huai23 on 2018-05-26 13:54:03.
 */ 
@RestController
@RequestMapping("/api/memberBody")
public class MemberBodyRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberBodyService memberBodyService;

    /**
     * 新增实体
     * @param memberBody
     * Created by huai23 on 2018-05-26 13:54:03.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody MemberBodyEntity memberBody,HttpServletRequest request, HttpServletResponse response){
        logger.info(" member_bodyRestController  add  memberBody = {}",memberBody);
        return memberBodyService.add(memberBody);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 13:54:03.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute MemberBodyQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<MemberBodyEntity> page = memberBodyService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:54:03.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute MemberBodyQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = memberBodyService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:54:03.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        MemberBodyEntity memberBodyDB = memberBodyService.getById(id);
        if(memberBodyDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(memberBodyDB);
    }

    /**
     * 根据实体更新
     * @param memberBody
     * Created by huai23 on 2018-05-26 13:54:03.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody MemberBodyEntity memberBody,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  memberBody = {}",memberBody);
        return memberBodyService.update(memberBody);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:54:03.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return memberBodyService.delete(id);
    }


}


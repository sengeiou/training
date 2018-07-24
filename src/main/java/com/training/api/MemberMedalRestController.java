package com.training.api;

import com.training.domain.MemberMedal;
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
 * member_medal API控制器
 * Created by huai23 on 2018-07-24 22:31:46.
 */ 
@RestController
@RequestMapping("/api/memberMedal")
public class MemberMedalRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberMedalService memberMedalService;

    /**
     * 新增实体
     * @param memberMedal
     * Created by huai23 on 2018-07-24 22:31:46.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody MemberMedalEntity memberMedal,HttpServletRequest request, HttpServletResponse response){
        logger.info(" member_medalRestController  add  memberMedal = {}",memberMedal);
        return memberMedalService.add(memberMedal);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-07-24 22:31:46.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute MemberMedalQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<MemberMedalEntity> page = memberMedalService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-24 22:31:46.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute MemberMedalQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = memberMedalService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * Created by huai23 on 2018-07-24 22:31:46.
     */ 
    @RequestMapping (value = "getById", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@ModelAttribute MemberMedalQuery query,HttpServletRequest request, HttpServletResponse response){
        MemberMedalEntity memberMedalDB = memberMedalService.getById(query.getMemberId(),query.getMedalId());
        if(memberMedalDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(memberMedalDB);
    }

    /**
     * 根据ID查询实体
     * Created by huai23 on 2018-07-24 22:31:46.
     */
    @RequestMapping (value = "getByMemberId", method = RequestMethod.GET)
    public ResponseEntity<String> getByMemberId(@ModelAttribute MemberMedalQuery query,HttpServletRequest request, HttpServletResponse response){
        List<MemberMedal> memberMedals = memberMedalService.getByMemberId(query.getMemberId());
        return ResponseUtil.success(memberMedals);
    }

    /**
     * 根据实体更新
     * @param memberMedal
     * Created by huai23 on 2018-07-24 22:31:46.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody MemberMedalEntity memberMedal,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  memberMedal = {}",memberMedal);
        return memberMedalService.update(memberMedal);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-24 22:31:46.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return memberMedalService.delete(id);
    }


}


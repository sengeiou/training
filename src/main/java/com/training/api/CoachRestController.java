package com.training.api;

import com.alibaba.fastjson.JSONObject;
import com.training.common.Page;
import com.training.common.PageRequest;
import com.training.domain.Member;
import com.training.entity.MemberEntity;
import com.training.entity.MemberQuery;
import com.training.entity.TrainingEntity;
import com.training.entity.TrainingQuery;
import com.training.service.MemberService;
import com.training.service.TrainingService;
import com.training.util.RequestContextHelper;
import com.training.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * member API控制器
 * Created by huai23 on 2018-05-26 13:39:33.
 */ 
@RestController
@RequestMapping("/api/coach")
public class CoachRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    @Autowired
    private TrainingService trainingService;

    /**
     * 新增实体
     * @param member
     * Created by huai23 on 2018-05-26 13:39:33.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody MemberEntity member,HttpServletRequest request, HttpServletResponse response){
        logger.info(" memberRestController  add  member = {}",member);
        return memberService.add(member);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 13:39:33.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute MemberQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<MemberEntity> page = memberService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:39:33.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute MemberQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = memberService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:39:33.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        MemberEntity memberDB = memberService.getById(id);
        if(!memberDB.getType().equals("C")){
            memberDB = null;
        }
        if(memberDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(memberDB);
    }

    /**
     * 根据实体更新
     * @param member
     * Created by huai23 on 2018-05-26 13:39:33.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody MemberEntity member,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  member = {}",member);
        return memberService.update(member);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:39:33.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return memberService.delete(id);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "memberList", method = RequestMethod.GET)
    public ResponseEntity<String> memberList(@ModelAttribute MemberQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        logger.info("  memberList  MemberQuery = {}",query);
        Page<MemberEntity> page = memberService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 17:09:14.
     */
    @RequestMapping (value = "trainingList", method = RequestMethod.GET)
    public ResponseEntity<String> trainingList(@ModelAttribute TrainingQuery query , @ModelAttribute PageRequest pageRequest, HttpServletRequest request, HttpServletResponse response){
        logger.info("  trainingList  TrainingQuery = {}",query);
        Page<TrainingEntity> page = trainingService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

}


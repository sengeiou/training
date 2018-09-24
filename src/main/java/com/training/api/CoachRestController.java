package com.training.api;

import com.alibaba.fastjson.JSONObject;
import com.sun.corba.se.impl.presentation.rmi.IDLTypesUtil;
import com.training.common.Page;
import com.training.common.PageRequest;
import com.training.domain.Member;
import com.training.domain.StoreData;
import com.training.domain.Training;
import com.training.entity.*;
import com.training.service.MemberService;
import com.training.service.StoreDataService;
import com.training.service.TrainingService;
import com.training.util.IDUtils;
import com.training.util.RequestContextHelper;
import com.training.util.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    @Autowired
    private StoreDataService storeDataService;

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
        Page<Member> page = memberService.find(query,pageRequest);
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
        Page<Member> page = memberService.memberList(query);
        return  ResponseUtil.success(page);
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
        query.setStatus(0);
        Page<Training> page = trainingService.trainingList(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 分页查询
     * @param query
     * Created by huai23 on 2018-05-26 17:09:14.
     */
    @RequestMapping (value = "qrCode", method = RequestMethod.GET)
    public ResponseEntity<String> qrCode(@ModelAttribute TrainingQuery query, HttpServletRequest request, HttpServletResponse response){
        logger.info("  qrCode  TrainingQuery = {}",query);
        return trainingService.qrCode(query);
    }

    /**
     * 分页查询
     * Created by huai23 on 2018-05-26 17:09:14.
     */
    @RequestMapping (value = "saleMoneyList", method = RequestMethod.GET)
    public ResponseEntity<String> saleMoneyList(@ModelAttribute StoreDataQuery query, HttpServletRequest request, HttpServletResponse response){
        logger.info("  saleMoneyList ");
        List<StoreData> storeDataList = storeDataService.staffSaleMoneyList(query);
        JSONObject jo = new JSONObject();
        jo.put("storeDataList", storeDataList);
        return ResponseUtil.success(jo);
    }

}


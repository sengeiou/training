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
 * member_coupon API控制器
 * Created by huai23 on 2018-06-30 10:02:47.
 */ 
@RestController
@RequestMapping("/api/memberCoupon")
public class MemberCouponRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberCouponService memberCouponService;

    /**
     * 新增实体
     * @param memberCoupon
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody MemberCouponEntity memberCoupon,HttpServletRequest request, HttpServletResponse response){
        logger.info(" member_couponRestController  add  memberCoupon = {}",memberCoupon);
        return memberCouponService.add(memberCoupon);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute MemberCouponQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<MemberCouponEntity> page = memberCouponService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute MemberCouponQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = memberCouponService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        MemberCouponEntity memberCouponDB = memberCouponService.getById(id);
        if(memberCouponDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(memberCouponDB);
    }

    /**
     * 根据实体更新
     * @param memberCoupon
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody MemberCouponEntity memberCoupon,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  memberCoupon = {}",memberCoupon);
        return memberCouponService.update(memberCoupon);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return memberCouponService.delete(id);
    }


}


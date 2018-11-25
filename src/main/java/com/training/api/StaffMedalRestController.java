package com.training.api;

import com.training.domain.StaffMedal;
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
 * staff_medal API控制器
 * Created by huai23 on 2018-07-22 23:28:30.
 */ 
@RestController
@RequestMapping("/api/staffMedal")
public class StaffMedalRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StaffMedalService staffMedalService;

    /**
     * 新增实体
     * @param staffMedal
     * Created by huai23 on 2018-07-22 23:28:30.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody StaffMedalEntity staffMedal,HttpServletRequest request, HttpServletResponse response){
        logger.info(" staff_medalRestController  add  staffMedal = {}",staffMedal);
        return staffMedalService.add(staffMedal);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-07-22 23:28:30.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute StaffMedalQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<StaffMedalEntity> page = staffMedalService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-22 23:28:30.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute StaffMedalQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = staffMedalService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * Created by huai23 on 2018-07-22 23:28:30.
     */ 
    @RequestMapping (value = "get", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@ModelAttribute StaffMedalQuery query,HttpServletRequest request, HttpServletResponse response){
        StaffMedalEntity staffMedalDB = staffMedalService.getById(query.getStaffId(),query.getMedalId());
        if(staffMedalDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(staffMedalDB);
    }

    /**
     * 根据ID查询实体
     * Created by huai23 on 2018-07-22 23:28:30.
     */
    @RequestMapping (value = "getByStaffId", method = RequestMethod.GET)
    public ResponseEntity<String> getByStaffId(@ModelAttribute StaffMedalQuery query,HttpServletRequest request, HttpServletResponse response){
        List<StaffMedal> staffMedalList = staffMedalService.getByStaffId(query.getStaffId());
        return ResponseUtil.success(staffMedalList);
    }

    /**
     * 根据实体更新
     * @param staffMedal
     * Created by huai23 on 2018-07-22 23:28:30.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody StaffMedalEntity staffMedal,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  staffMedal = {}",staffMedal);
        return staffMedalService.update(staffMedal);
    }

    /**
     * 根据ID删除
     * Created by huai23 on 2018-07-22 23:28:30.
     */ 
    @RequestMapping (value = "delete", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@RequestBody StaffMedalEntity staffMedal,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  staffMedal = {}",staffMedal);
        return staffMedalService.delete(staffMedal.getStaffId(),staffMedal.getMedalId());
    }


}


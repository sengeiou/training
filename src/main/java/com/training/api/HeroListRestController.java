package com.training.api;

import com.alibaba.fastjson.JSONObject;
import com.training.common.Page;
import com.training.common.PageRequest;
import com.training.domain.Member;
import com.training.domain.Staff;
import com.training.domain.Training;
import com.training.entity.MemberEntity;
import com.training.entity.MemberQuery;
import com.training.entity.TrainingQuery;
import com.training.service.HeroListService;
import com.training.service.MemberService;
import com.training.service.TrainingService;
import com.training.util.ResponseUtil;
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
@RequestMapping("/api/heroList")
public class HeroListRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HeroListService heroListService;

    /**
     * 上课数量排名
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "lesson", method = RequestMethod.GET)
    public ResponseEntity<String> lesson(@ModelAttribute MemberQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        List<Staff> staffList = heroListService.lessonList();
        return ResponseUtil.success(staffList);
    }


    /**
     * 续课金额排名
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 13:39:33.
     */ 
    @RequestMapping (value = "money", method = RequestMethod.GET)
    public ResponseEntity<String> money(@ModelAttribute MemberQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        List<Staff> staffList = heroListService.moneyList();
        return ResponseUtil.success(staffList);
    }

    /**
     * 活跃度排名
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "activeRate", method = RequestMethod.GET)
    public ResponseEntity<String> activeRate(@ModelAttribute MemberQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        List<Staff> staffList = heroListService.activeRateList();
        return ResponseUtil.success(staffList);
    }

    /**
     * 转介绍排名
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "introduce", method = RequestMethod.GET)
    public ResponseEntity<String> introduce(@ModelAttribute MemberQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        List<Staff> staffList = heroListService.introduceList();
        return ResponseUtil.success(staffList);
    }


}


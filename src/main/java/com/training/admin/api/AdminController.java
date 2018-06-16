package com.training.admin.api;

import com.training.domain.Member;
import com.training.domain.Staff;
import com.training.service.MemberService;
import com.training.service.StaffService;
import com.training.util.RequestContextHelper;
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
@RequestMapping("/api/manage")
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    @Autowired
    private StaffService staffService;

    /**
     * 根据手机号码绑定会员
     * @param member
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "bindCoach", method = RequestMethod.POST)
    public ResponseEntity<String> bindCoach(@RequestBody Member member, HttpServletRequest request, HttpServletResponse response){
        logger.info(" memberRestController  bindCoach  member = {}",member);
        Member memberRequest = RequestContextHelper.getMember();
        logger.info(" memberRestController  bindCoach  memberRequest = {}",memberRequest);
        return memberService.bindCoach(member);
    }

    /**
     * 根据实体更新
     * @param staff
     * Created by huai23 on 2018-05-26 13:55:30.
     */
    @RequestMapping (value = "updatePwd", method = RequestMethod.POST)
    public ResponseEntity<String> updatePwd(@RequestBody Staff staff, HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  staff = {}",staff);
        return staffService.updatePwd(staff);
    }

}


package com.training.api;

import com.training.domain.Lesson;
import com.training.domain.Member;
import com.training.domain.Training;
import com.training.service.*;
import com.training.entity.*;
import com.training.common.*;
import com.training.util.RequestContextHelper;
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
 * member API控制器
 * Created by huai23 on 2018-05-26 13:39:33.
 */
@RestController
@RequestMapping("/api/member")
public class MemberRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberCouponService memberCouponService;

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
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "memberMedalList", method = RequestMethod.GET)
    public ResponseEntity<String> memberMedalList(@ModelAttribute MemberQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<Member> page = memberService.memberMedalList(query,pageRequest);
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
     * 根据ID闭环
     * @param id
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "close/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> close(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  close  id = {}",id);
        return memberService.close(id);
    }

    /**
     * 根据ID恢复为意向
     * @param id
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "restore/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> restore(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  restore  id = {}",id);
        return memberService.restore(id);
    }

    /**
     * 根据手机号码发送验证码
     * @param member
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "sendCode", method = RequestMethod.POST)
    public ResponseEntity<String> sendCode(@RequestBody Member member, HttpServletRequest request, HttpServletResponse response){
        logger.info(" memberRestController  sendCode  member = {}",member);
        Member memberRequest = RequestContextHelper.getMember();
        logger.info(" memberRestController  sendCode  memberRequest = {}",memberRequest);
        return memberService.sendCode(member);
    }

    /**
     * 根据手机号码绑定会员
     * @param member
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "bind", method = RequestMethod.POST)
    public ResponseEntity<String> bind(@RequestBody Member member, HttpServletRequest request, HttpServletResponse response){
        logger.info(" memberRestController  bind  member = {}",member);
        Member memberRequest = RequestContextHelper.getMember();
        logger.info(" memberRestController  bind  memberRequest = {}",memberRequest);
        return memberService.bind(member);
    }

    /**
     * 根据ID查询实体
     * @param memberId
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "getValidLessonType/{memberId}", method = RequestMethod.GET)
    public ResponseEntity<String> getValidLessonType(@PathVariable String memberId,HttpServletRequest request, HttpServletResponse response){
        logger.info(" memberRestController  getValidLessonType  memberId = {}",memberId);
        return memberService.getValidLessonType(memberId);
    }

    /**
     * 根据实体更新
     * @param member
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "modify", method = RequestMethod.POST)
    public ResponseEntity<String> modify(@RequestBody MemberEntity member,HttpServletRequest request, HttpServletResponse response){
        logger.info("  modify  member = {}",member);
        return memberService.modify(member);
    }

    /**
     * 根据实体更新
     * @param training
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "signIn", method = RequestMethod.POST)
    public ResponseEntity<String> signIn(@RequestBody Training training, HttpServletRequest request, HttpServletResponse response){
        logger.info("  signIn  training = {}",training);
        return memberService.signIn(training);
    }

    /**
     * 根据ID查询实体
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "getCanUseCardList", method = RequestMethod.GET)
    public ResponseEntity<String> getCanUseCardList(@ModelAttribute Lesson lesson, HttpServletRequest request, HttpServletResponse response){
        logger.info(" memberRestController  getCanUseCardList  lesson = {}",lesson);
        return memberService.getCanUseCardList(lesson);
    }


    /**
     * 根据实体更新
     * @param member
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "updateImage", method = RequestMethod.POST)
    public ResponseEntity<String> updateImage(@RequestBody MemberEntity member,HttpServletRequest request, HttpServletResponse response){
//        logger.info("  updateImage  member = {}",member);
        return memberService.updateImage(member);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-06-30 10:02:47.
     */
    @RequestMapping (value = "couponList", method = RequestMethod.GET)
    public ResponseEntity<String> couponList(@ModelAttribute MemberCouponQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Member member = RequestContextHelper.getMember();
        String memberId = member.getMemberId();
        query.setMemberId(memberId);
        logger.info(" memberRestController  couponList  query = {} , pageRequest = {} ",query,pageRequest);
        return memberCouponService.couponList(query,pageRequest);
    }

    /**
     * 注销微信用户
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "logoff", method = RequestMethod.POST)
    public ResponseEntity<String> logoff(HttpServletRequest request, HttpServletResponse response){
        logger.info("  logoff   ");
        return memberService.logoff();
    }

}


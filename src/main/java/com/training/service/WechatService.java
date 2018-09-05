package com.training.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.training.common.*;
import com.training.dao.MemberCardDao;
import com.training.dao.MemberDao;
import com.training.dao.StaffDao;
import com.training.dao.SysLogDao;
import com.training.domain.KpiStaffMonth;
import com.training.domain.Member;
import com.training.domain.PrePayOrder;
import com.training.domain.User;
import com.training.entity.*;
import com.training.util.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 微信业务操作类
 * Created by huai23 on 2018-05-26 13:33:17.
 */ 
@Service
public class WechatService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    @Autowired
    private CardService cardService;

    @Autowired
    private WechatUtils wechatUtils;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private MemberCardService memberCardService;

    @Autowired
    private SysLogDao sysLogDao;

    @Autowired
    private MemberCardDao memberCardDao;

    @Autowired
    private KpiStaffMonthService kpiStaffMonthService;

    @Autowired
    private JwtUtil jwt;

    public ResponseEntity<String> getMemberByCode(String code) {
        JSONObject jo = new JSONObject();
        String openId = wechatUtils.getOpenIdByCode(code);
//        String openId = "oFg700C3DCUghR7lpn4mJpFAZQvU";
        if(StringUtils.isEmpty(openId)){
            return ResponseUtil.exception("获取openId异常");
        }
        MemberEntity memberEntity = memberService.getByOpenId(openId);
        Member member = new Member();
        if(memberEntity!=null){
            member = new Member();
            member.setMemberId(memberEntity.getMemberId());
            member.setType(memberEntity.getType());
            jo.put("member", memberEntity);
            jo.put("star",2);
            if(memberEntity.getType().equals("C")){
                jo.put("kpiScore","0");
                StaffEntity staffEntity = staffDao.getByOpenId(memberEntity.getOpenId());
                if(staffEntity!=null){
                    jo.put("staffId",staffEntity.getStaffId());
                    jo.put("coachImage","");
                    if(StringUtils.isNotEmpty(staffEntity.getImage())){
                        jo.put("coachImage",staffEntity.getImage());
                    }
                    KpiStaffMonth kpiStaffMonth = kpiStaffMonthService.getByIdAndMonth(staffEntity.getStaffId(),ut.currentKpiMonth());
                    if(kpiStaffMonth!=null){
                        jo.put("kpiScore",kpiStaffMonth.getKpiScore());
                    }
                }
            }
        }else{
            member.setMemberId("");
            member.setType("");
        }
        String subject = JwtUtil.generalSubject(openId,member);
        try {
            String token = jwt.createJWT(Const.JWT_ID, subject, Const.JWT_TTL);
            jo.put("token", token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseUtil.success(jo);
    }

    public ResponseEntity<String> prePayOrder(PrePayOrder prePayOrder) {
        JSONObject jo = new JSONObject();
        Member memberRequest = RequestContextHelper.getMember();
        String openId = memberRequest.getOpenId();

        double fee = 0;
        String money = prePayOrder.getMoney();
        if(StringUtils.isEmpty(money)){
            ResponseUtil.exception("支付金额异常，请稍后重试");
        }
        try{
            fee = ut.doubled(money);
        }catch (Exception e){
            logger.error(" 支付金额含有非法字符 : {} " , money,e);
            ResponseUtil.exception("支付金额含有非法字符，请重试");
        }
        int total_fee = (int)(fee*100);
//        total_fee = 1;
//        CardEntity card = cardService.getById(prePayOrder.getCardId()) ;

        Map<String, String> param = new HashMap<>();
        param.put("openId",openId);
        param.put("total_fee",""+total_fee);

        logger.info(" ============ prePayOrder  param = {}",param);


//        param.put("card",JSON.toJSONString(card));
        Map<String, String> result = wechatUtils.prePayOrder(param);
        if(MapUtils.isEmpty(result)){
            return ResponseUtil.exception("发起支付请求异常");
        }
        jo.putAll(result);
        return  ResponseUtil.success(jo);
    }

    public ResponseEntity<String> prePayDelayOrder(PrePayOrder prePayOrder) {
        JSONObject jo = new JSONObject();
        Member memberRequest = RequestContextHelper.getMember();
        String openId = memberRequest.getOpenId();
        String cardNo = prePayOrder.getCardNo();
        MemberCardEntity memberCardEntity = memberCardDao.getById(cardNo);
        if(memberCardEntity==null){
            ResponseUtil.exception("卡号异常");
        }
        if(memberCardEntity.getType().equals(CardTypeEnum.PT.getKey())||memberCardEntity.getType().equals(CardTypeEnum.TT.getKey())){

        }else{
            return ResponseUtil.exception("此种课卡不能延期");
        }
        if(ut.passDayByDate(memberCardEntity.getEndDate(),ut.currentDate(7)) > 0 && ut.passDayByDate(memberCardEntity.getEndDate(),ut.currentDate(-30)) < 0){

        }else{
            return ResponseUtil.exception("课卡到期前一周才可延期");
        }
        if(StringUtils.isEmpty(memberCardEntity.getMoney())||"0".equals(memberCardEntity.getMoney().trim())){
            ResponseUtil.exception("购卡金额为0，不能付费延期");
        }
        int count = memberCardEntity.getCount();
        int total = memberCardEntity.getTotal();
        double delayMoney = count*Double.parseDouble(memberCardEntity.getMoney())/(total*10);
        if(delayMoney<=0){
            ResponseUtil.exception("支付金额异常");
        }
        int total_fee = (int)(delayMoney*100);
        total_fee = 1;

        String logId = UUID.randomUUID().toString().replaceAll("-","");
        Map<String, String> param = new HashMap<>();
        param.put("openId",openId);
        param.put("cardNo",cardNo);
        param.put("logId",logId);
        param.put("total_fee",""+total_fee);

        SysLogEntity sysLogEntity = new SysLogEntity();
        sysLogEntity.setLogId(logId);
        sysLogEntity.setType(SysLogEnum.YQ.getKey());
        sysLogEntity.setLevel(2);
        sysLogEntity.setLogText(JSON.toJSONString(memberCardEntity));
        sysLogEntity.setContent(JSON.toJSONString(memberCardEntity));
        sysLogEntity.setCreated(new Date());
        int n = sysLogDao.add(sysLogEntity);
        if(n==0){
            ResponseUtil.exception("生成延期订单异常");
        }
        logger.info(" ============ prePayOrder  param = {}",param);
        Map<String, String> result = wechatUtils.prePayDelayOrder(param);
        if(MapUtils.isEmpty(result)){
            return ResponseUtil.exception("发起延期支付请求异常");
        }
        jo.putAll(result);
        return  ResponseUtil.success(jo);
    }

}


package com.training.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.training.common.Const;
import com.training.common.Page;
import com.training.common.PageRequest;
import com.training.dao.MemberDao;
import com.training.domain.Member;
import com.training.domain.PrePayOrder;
import com.training.domain.User;
import com.training.entity.CardEntity;
import com.training.entity.MemberEntity;
import com.training.entity.MemberQuery;
import com.training.util.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        total_fee = 1;
        CardEntity card = cardService.getById(prePayOrder.getCardId()) ;

        Map<String, String> param = new HashMap<>();
        param.put("openId",openId);
        param.put("total_fee",""+total_fee);

        param.put("card",JSON.toJSONString(card));
        Map<String, String> result = wechatUtils.prePayOrder(param);
        if(MapUtils.isEmpty(result)){
            return ResponseUtil.exception("发起支付请求异常");
        }
        jo.putAll(result);
        return  ResponseUtil.success(jo);
    }

}


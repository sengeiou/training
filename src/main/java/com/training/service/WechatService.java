package com.training.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.training.common.Const;
import com.training.common.Page;
import com.training.common.PageRequest;
import com.training.dao.MemberDao;
import com.training.domain.User;
import com.training.entity.MemberEntity;
import com.training.entity.MemberQuery;
import com.training.util.HttpUtils;
import com.training.util.JwtUtil;
import com.training.util.RequestContextHelper;
import com.training.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 微信业务操作类
 * Created by huai23 on 2018-05-26 13:33:17.
 */ 
@Service
public class WechatService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    private String jscode2session = "https://api.weixin.qq.com/sns/jscode2session";
    private String appId = "wx51bc47a8e2de73c2";
    private String secret = "801a7496df7c4cd4506c38bd0c0d7e47";
    
    /**
     * 新增实体
     * @param code
     * Created by huai23 on 2018-05-26 13:33:17.
     */ 
    public String getOpenIdByCode(String code){
        logger.info(" getOpenIdByCode  code1 = {}",code);
        code = JSON.parseObject(code).get("data").toString();
        logger.info(" getOpenIdByCode  code22 = {}",code);
        String openId = null;
        try {
            StringBuilder urlPath = new StringBuilder(jscode2session); // 微信提供的API，这里最好也放在配置文件
            urlPath.append(String.format("?appid=%s", appId));
            urlPath.append(String.format("&secret=%s", secret));
            urlPath.append(String.format("&js_code=%s", code));
            urlPath.append(String.format("&grant_type=%s", "authorization_code")); // 固定值
            String data = HttpUtils.doGet(urlPath.toString()); // java的网络请求，这里是我自己封装的一个工具包，返回字符串
            logger.info("请求结果：",data);
            JSONObject obj = JSON.parseObject(data);
            openId = obj.get("openid").toString();
            String sessionKey = obj.get("session_key").toString();
            logger.info("获得openId: {} ", openId);
            logger.info("获得sessionKey:  {} ",sessionKey);
            if(obj.containsKey("unionid")){
                String unionId = obj.get("unionid").toString();
                logger.info("获得unionId: {} ",unionId);
            }
        } catch (Exception e) {
            logger.error(" getOpenIdByCode ERROR : {}",e.getMessage(),e);
        }
        return openId;
    }

    public ResponseEntity<String> getMemberByCode(String code) {
        String openId = getOpenIdByCode(code);
        MemberEntity memberEntity = memberService.getByOpenId(openId);
        if(memberEntity==null){
            return ResponseUtil.exception("查无会员数据");
        }
        return ResponseUtil.success(memberEntity);
    }

}


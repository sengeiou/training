package com.training.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.training.common.Const;
import com.training.domain.PrePayOrder;
import com.training.domain.User;
import com.training.entity.MemberEntity;
import com.training.service.MemberService;
import com.training.service.WechatService;
import com.training.util.HttpUtils;
import com.training.util.JwtUtil;
import com.training.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户 API控制器
 */
@RestController
@RequestMapping("/api/wechat")
public class WechatRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WechatService wechatService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtUtil jwt;

//    @PostMapping("login")
//    public Object login(@RequestBody String code,HttpServletRequest request, HttpServletResponse response){
//        logger.info(" wxlogin  code11 = {}",code);
//        code = JSON.parseObject(code).get("data").toString();
//        logger.info(" wxlogin  code22 = {}",code);
//        User user = new User();
//        user.setUserId(code);
//        user.setRoleId("1");
//        user.setOrgId("1");
//        user.setCustname("code");
//        logger.info(" login  user = {}",user);
//        String subject = JwtUtil.generalSubject(user);
//        logger.info(" login  subject = {}",subject);
//        String token = null;
//        try {
//            StringBuilder urlPath = new StringBuilder("https://api.weixin.qq.com/sns/jscode2session"); // 微信提供的API，这里最好也放在配置文件
//            urlPath.append(String.format("?appid=%s", "wx51bc47a8e2de73c2"));
//            urlPath.append(String.format("&secret=%s", "801a7496df7c4cd4506c38bd0c0d7e47"));
//            urlPath.append(String.format("&js_code=%s", code));
//            urlPath.append(String.format("&grant_type=%s", "authorization_code")); // 固定值
//            String data = HttpUtils.doGet(urlPath.toString()); // java的网络请求，这里是我自己封装的一个工具包，返回字符串
//            System.out.println("请求结果：" + data);
//            JSONObject obj = JSON.parseObject(data);
//            String openId = obj.get("openid").toString();
//            String sessionKey = obj.get("session_key").toString();
//            System.out.println("获得openId: " + openId);
//            System.out.println("获得sessionKey: " + sessionKey);
//            if(obj.containsKey("unionid")){
//                String unionId = obj.get("unionid").toString();
//                System.out.println("获得unionId: " + unionId);
//            }
//            token = jwt.createJWT(Const.JWT_ID, subject, Const.JWT_TTL);
//            JSONObject jo = new JSONObject();
//            jo.put("token", token);
//            jo.put("openId", openId);
//            return ResponseUtil.success(jo);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return ResponseUtil.unKonwException();
//        }
//    }

    /**
     * 根据ID查询实体
     * @param code
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "code/{code}", method = RequestMethod.GET)
    public ResponseEntity<String> getMemberByCode(@PathVariable String code, HttpServletRequest request, HttpServletResponse response){
//        logger.info("  getMemberByCode  code = {}",code);
        return wechatService.getMemberByCode(code);
    }


    /**
     * 根据ID查询实体
     * @param prePayOrder
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "prepay", method = RequestMethod.POST)
    public ResponseEntity<String> prepay(@RequestBody PrePayOrder prePayOrder, HttpServletRequest request, HttpServletResponse response){
        logger.info("  prepay  prePayOrder = {}",prePayOrder);
        return wechatService.prePayOrder(prePayOrder);
    }

}

package com.training.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class WechatUtils {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static String unifiedorderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    private static String appId = "wxe31b9911ade01879";
    private static String secret = "25ac3e71e931e6d2e538bab01c63b70d";
    private static String mch_id = "1498414262";
    private static String key = "RZf7oGyoUCYBy9RTyatoHKufV1P45zWi";

    public static String unifiedorder(String seq_name) {
//        logger.info(" getOpenIdByCode  code1 = {}",code);
        try {
            String openid = "odERo5IjbhDlNzqBbjWKi39eUEcY";

            String body = "健身会员卡购买";
            String device_info = "1000";
            String nonce_str = "abc123cba321";

            String timeStamp = ""+System.currentTimeMillis();

            String out_trade_no = UUID.randomUUID().toString().replaceAll("-","");
            System.out.println("out_trade_no = "+out_trade_no);
            System.out.println("out_trade_no = "+out_trade_no.length());

            Map<String,String> param = new HashMap();
            param.put("appid",appId);
            param.put("mch_id",mch_id);
            param.put("openid",openid);
            param.put("device_info",device_info);
            param.put("nonce_str",nonce_str);
            param.put("sign_type","MD5");
            param.put("body",body);
            param.put("detail","detail123");
            param.put("attach","attach");
            param.put("out_trade_no",out_trade_no);
            param.put("fee_type","CNY");
            param.put("total_fee","1");
            param.put("spbill_create_ip","127.0.0.1");
            param.put("notify_url","https://training/huai23.com/wechat/pay/callback");
            param.put("trade_type","JSAPI");
            param.put("timeStamp",timeStamp);

            String sign = WXPayUtil.generateSignature(param,key);
            param.put("sign",sign);
            String reqBody = WXPayUtil.mapToXml(param);

            String data = HttpUtils.doPost(unifiedorderUrl,reqBody); // java的网络请求，这里是我自己封装的一个工具包，返回字符串

            Map<String, String> result = WXPayUtil.xmlToMap(data);

            System.out.println("请求结果："+data);
            System.out.println("result："+result);

        } catch (Exception e) {
            e.printStackTrace();
//            logger.error(" getOpenIdByCode ERROR : {}",e.getMessage(),e);
        }
        return "";
    }

    public static void main(String[] args) {
        unifiedorder("1");
    }

}

package com.training.web;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPayUtil;
import com.training.entity.SysLogEntity;
import com.training.service.SysLogService;
import com.training.util.IDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * role API控制器
 * Created by huai23 on 2017-11-03 16:44:48.
 */ 
@Controller
public class WechatController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysLogService sysLogService;

    /**
     * 首页
     * Created by huai23 on 2017-11-03 16:44:48.
     */
    @RequestMapping (value = "/")
    public String root(HttpServletRequest request, HttpServletResponse response){
        logger.info("  IndexController  root  ");
        return "index";
    }

    /**
     * 支付回调接口
     * Created by huai23 on 2018-06-03 16:44:48.
     */
    @RequestMapping (value = "/wechat/pay/callback")
    public String callback(HttpServletRequest request, HttpServletResponse response){
        logger.info("  WechatController  callback  ");
        try{
            request.setCharacterEncoding("UTF-8");
            BufferedReader reader = request.getReader();
            String line = "";
            StringBuffer inputString = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                inputString.append(line);
            }
            request.getReader().close();
            String result = inputString.toString();
            logger.info("----callback接收到的报文---  {}" ,result);
//            System.out.println("callback接收到的报文："+new String(result.getBytes("gbk"),"UTF-8"));
//            System.out.println("callback接收到的报文："+new String(result.getBytes("iso-8859-1"),"UTF-8"));
//            System.out.println("callback接收到的报文："+new String(result.getBytes("iso-8859-1"),"gbk"));
//            System.out.println("callback接收到的报文："+new String(result.getBytes("UTF-8"),"gbk"));
            Map<String, String> data  = WXPayUtil.xmlToMap(result);
            logger.info("----data---  {}" ,data);
            String dataStr = JSON.toJSONString(data);
            SysLogEntity sysLogEntity = new SysLogEntity();
            sysLogEntity.setLogId(IDUtils.getId());
            sysLogEntity.setType("pay");
            sysLogEntity.setLevel(1);
            sysLogEntity.setLogText(dataStr.length()>1900?dataStr.substring(0,1900):dataStr);
            sysLogEntity.setContent(result);
            sysLogEntity.setCreated(new Date());
            sysLogService.add(sysLogEntity);
//            sysLogEntity = sysLogService.getById(sysLogEntity.getLogId());
//            logger.info("----sysLogEntity---  {}" ,JSON.toJSONString(sysLogEntity));
//            System.out.println( " sysLogEntity =  "+JSON.toJSONString(sysLogEntity));
            response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

package com.training.web;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPayUtil;
import com.training.common.OriginEnum;
import com.training.common.SysLogEnum;
import com.training.dao.SysLogDao;
import com.training.domain.GroupOrder;
import com.training.entity.GroupOrderEntity;
import com.training.entity.MemberCardEntity;
import com.training.entity.MemberEntity;
import com.training.entity.SysLogEntity;
import com.training.service.GroupOrderService;
import com.training.service.MemberCardService;
import com.training.service.MemberService;
import com.training.service.SysLogService;
import com.training.util.IDUtils;
import com.training.util.SmsUtil;
import com.training.util.ut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
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

    @Autowired
    private MemberCardService memberCardService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private GroupOrderService groupOrderService;

    @Autowired
    private SysLogDao sysLogDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    SmsUtil smsUtil;

    /**
     * 首页
     * Created by huai23 on 2017-11-03 16:44:48.
     */
    @RequestMapping (value = "/")
    public String root(HttpServletRequest request, HttpServletResponse response){
        logger.info("  IndexController  root  ");
        return "index";
    }

    @RequestMapping (value = "/od/{id}")
    public String order(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
        logger.info("  order  id = {}",id);
        request.setAttribute("id",id);
        return "order/templates/index";
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
            logger.info("----return_code---  {}" ,data.get("return_code"));
            logger.info("----openid---  {}" ,data.get("openid"));
            logger.info("----attach---  {}" ,data.get("attach"));
            String openId = data.get("openid");
            String transactionId = data.get("transaction_id");
            MemberEntity memberEntity = memberService.getByOpenId(openId);
            String logId = data.get("out_trade_no");
            String resultStr = "success";
            SysLogEntity sysLogEntity = new SysLogEntity();
            sysLogEntity.setLogId(IDUtils.getId());
            sysLogEntity.setType(SysLogEnum.PAY.getKey());
            sysLogEntity.setLevel(1);
            if(memberEntity!=null){
                sysLogEntity.setId1(memberEntity.getMemberId());
                sysLogEntity.setStoreId(memberEntity.getStoreId());
                sysLogEntity.setStaffId(memberEntity.getCoachStaffId());
            }else{

            }
            sysLogEntity.setId2(transactionId);

            try{
                SysLogEntity sysLogDB = sysLogDao.getById(logId);
                if(sysLogDB!=null && sysLogDB.getType().equals(SysLogEnum.YQ.getKey())){
                    sysLogEntity.setType(SysLogEnum.YQ.getKey());
                    String str = sysLogDB.getLogText();
                    MemberCardEntity memberCardEntity = JSON.parseObject(str,MemberCardEntity.class);
                    memberCardService.payDelay(memberCardEntity.getCardNo());
                    sysLogEntity.setId1(memberCardEntity.getCardNo());
                    sysLogEntity.setId2("pay");
                    sysLogEntity.setLevel(2);
                    sysLogEntity.setCardNo(memberCardEntity.getCardNo());
                }
            }catch (Exception e){
                resultStr = logId+" : "+e.getMessage();
            }
            String dataStr = JSON.toJSONString(data);

            sysLogEntity.setLogText(dataStr.length()>1900?dataStr.substring(0,1900):dataStr);
            sysLogEntity.setContent(result);
            sysLogEntity.setCreated(new Date());
            sysLogEntity.setRemark(resultStr);
            sysLogService.add(sysLogEntity);
            response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 支付回调接口
     * Created by huai23 on 2018-06-03 16:44:48.
     */
    @RequestMapping (value = "/wechat/pay/group/callback")
    public String groupCallback(HttpServletRequest request, HttpServletResponse response){
        logger.info("  WechatController  groupCallback  ");
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
            logger.info("----return_code---  {}" ,data.get("return_code"));
            logger.info("----openid---  {}" ,data.get("openid"));
            logger.info("----attach---  {}" ,data.get("attach"));
            String openId = data.get("openid");
            String transactionId = data.get("transaction_id");
            String out_trade_no = data.get("out_trade_no");
            String orderId = out_trade_no.split("_")[0];
            logger.info("----out_trade_no---  {}" ,out_trade_no);
            logger.info("----orderId---  {}" ,orderId);
            GroupOrder groupOrder = groupOrderService.getById(orderId);
            MemberEntity memberEntity = memberService.getByPhone(groupOrder.getPhone());
            if(memberEntity==null){
                memberEntity = new MemberEntity();
                memberEntity.setPhone(groupOrder.getPhone());
                memberEntity.setStoreId(groupOrder.getStoreId());
                memberEntity.setGender(groupOrder.getGender());
                memberEntity.setOrigin(OriginEnum.WX.getDesc());
                memberService.add(memberEntity);
            }
            String resultStr = "group_success";
            SysLogEntity sysLogEntity = new SysLogEntity();
            sysLogEntity.setLogId(IDUtils.getId());
            sysLogEntity.setType(SysLogEnum.PAY.getKey());
            sysLogEntity.setLevel(1);
            if(memberEntity!=null){
                sysLogEntity.setId1(memberEntity.getMemberId());
                sysLogEntity.setStoreId(memberEntity.getStoreId());
                sysLogEntity.setStaffId(memberEntity.getCoachStaffId());
            }else{

            }
            sysLogEntity.setId2(transactionId);
            String dataStr = JSON.toJSONString(data);
            sysLogEntity.setLogText(dataStr.length()>1900?dataStr.substring(0,1900):dataStr);
            sysLogEntity.setContent(result);
            sysLogEntity.setCreated(new Date());
            sysLogEntity.setRemark(resultStr);
            sysLogService.add(sysLogEntity);
            response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");

            String sql = "update group_order set status = ? , pay_id = ?, pay_time = ? where order_id = ? ";

            if(groupOrder.getMainFlag().equals("2")){
                String mainId = groupOrder.getMainOrderId();
                jdbcTemplate.update(" update group_order set count = count+1, status = 3 where order_id = ? ",new Object[]{mainId});
                jdbcTemplate.update(sql,new Object[]{"3",transactionId, ut.currentTime(),orderId});

            }else if(groupOrder.getMainFlag().equals("1")){
                jdbcTemplate.update(sql,new Object[]{"2",transactionId, ut.currentTime(),orderId});

            }else if(groupOrder.getMainFlag().equals("0")){
                jdbcTemplate.update(sql,new Object[]{"3",transactionId, ut.currentTime(),orderId});
            }

            jdbcTemplate.update("update group_buy set sale_count = sale_count+1 where buy_id = ? ",new Object[]{groupOrder.getBuyId()});

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}


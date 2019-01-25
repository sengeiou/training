<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.training.config.ContextUtil"%>
<%@ page language="java" import="org.springframework.jdbc.core.JdbcTemplate"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="com.training.domain.User" %>
<%@ page import="org.apache.http.impl.client.DefaultHttpClient" %>
<%@ page import="org.apache.http.client.methods.HttpGet" %>
<%@ page import="org.apache.http.HttpResponse" %>
<%@ page import="org.apache.http.HttpEntity" %>
<%@ page import="org.apache.http.util.EntityUtils" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="com.github.wxpay.sdk.WXPayUtil" %>
<%@ page import="com.training.util.HttpUtils" %>
<%
System.out.println(" ****************     index.jsp  *********  ");
String code  = request.getParameter("code");
    System.out.println(" ****************     code = "+code);
String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx07d9e50873fe1786&secret=ddbc05a576ef96e7c08bdc31e3639d3f&code="+code+"&grant_type=authorization_code";

    DefaultHttpClient httpClient = new DefaultHttpClient();
    HttpGet httpGet = new HttpGet(url);
    HttpResponse httpResponse = httpClient.execute(httpGet);
    HttpEntity httpEntity = httpResponse.getEntity();
    String tokens = EntityUtils.toString(httpEntity, "utf-8");
    System.out.println(" ****************     tokens = "+tokens);

    JSONObject obj = JSON.parseObject(tokens);
    String openId = obj.getString("openid");
    System.out.println(" ****************     openId = "+openId);
    String unifiedorderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    String mch_id = "1284812401";
    String key = "DyCGX2iQOMt1S5spSWdB8wmya7aO3ACj";
    String device_info = "1000";
    String nonce_str = "abc123cba321";
    String out_trade_no = UUID.randomUUID().toString().replaceAll("-","");
    String timeStamp = ""+System.currentTimeMillis()/1000;

    Map<String,String> param = new HashMap();
    param.put("appid","wx07d9e50873fe1786");
    param.put("mch_id",mch_id);
    param.put("openid",openId);
    param.put("device_info",device_info);
    param.put("nonce_str",nonce_str);
    param.put("sign_type","MD5");
    param.put("body",openId);
    param.put("detail","detail123");
    param.put("attach",openId);
    param.put("out_trade_no",out_trade_no);
    param.put("fee_type","CNY");
    param.put("total_fee","1");
    param.put("spbill_create_ip","47.104.252.220");
    param.put("notify_url","https://trainingbj.huai23.com/wechat/pay/group/callback");
    param.put("trade_type","JSAPI");
    String sign = WXPayUtil.generateSignature(param,key);
//            System.out.println("sign："+sign);
    param.put("sign",sign);
    String reqBody = WXPayUtil.mapToXml(param);
            System.out.println("reqBody："+reqBody);
    String data = com.training.util.HttpUtils.doPost(unifiedorderUrl,reqBody); // java的网络请求，这里是我自己封装的一个工具包，返回字符串
    System.out.println("请求结果："+data);
            System.out.println("请求结果："+new String(data.getBytes("gbk"),"UTF-8"));
            System.out.println("请求结果："+new String(data.getBytes("iso-8859-1"),"UTF-8"));
            System.out.println("请求结果："+new String(data.getBytes("iso-8859-1"),"gbk"));
            System.out.println("请求结果："+new String(data.getBytes("UTF-8"),"gbk"));
    Map<String, String> result = WXPayUtil.xmlToMap(data);
    System.out.println("timeStamp="+timeStamp);
    Map<String, String> signMap = new HashMap<>();
    String package1 = "prepay_id="+result.get("prepay_id");
    signMap.put("appId","wx07d9e50873fe1786");
    signMap.put("timeStamp",timeStamp);
    signMap.put("nonceStr",nonce_str);
    signMap.put("package",package1);
    signMap.put("signType","MD5");
    sign = WXPayUtil.generateSignature(signMap,key);



    JdbcTemplate jdbcTemplate = (JdbcTemplate)ContextUtil.getBean("jdbcTemplate");
String id = request.getParameter("id");
if(StringUtils.isNotEmpty(id)){
    session.setAttribute("user",new User());
}
%>
<script>
    function onBridgeReady(){
        WeixinJSBridge.invoke(
            'getBrandWCPayRequest', {
                "appId":"wx07d9e50873fe1786",     //公众号名称，由商户传入
                "timeStamp":"<%= timeStamp %>",         //时间戳，自1970年以来的秒数
                "nonceStr":"<%=nonce_str%>", //随机串
                "package":"<%=package1%>",
                "signType":"MD5",         //微信签名方式：
                "paySign":"<%=sign%>" //微信签名
            },
            function(res){
                if(res.err_msg == "get_brand_wcpay_request:ok" ){
                    // 使用以上方式判断前端返回,微信团队郑重提示：
                    //res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
                }
            });
    }
    if (typeof WeixinJSBridge == "undefined"){
        if( document.addEventListener ){
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        }else if (document.attachEvent){
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    }else{
        onBridgeReady();
    }
</script>
index.jsp
<a href="/static/template/main.jsp">main</a>

<a href="/static/template/index.jsp?id=123">login</a>
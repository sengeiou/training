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
<%@ page import="com.training.util.SHA1" %>
<%
    System.out.println(" ****************     success.jsp  *********  ");
    JdbcTemplate jdbcTemplate = (JdbcTemplate)ContextUtil.getBean("jdbcTemplate");
    String timeStamp = ""+System.currentTimeMillis()/1000;
    String sha1 = "";
    System.out.println("timeStamp = "+timeStamp);
    String id = request.getParameter("id");
    String price = request.getParameter("p");

    String uri = request.getRequestURI();
    System.out.println("uri = "+uri);
    String query = request.getQueryString();
    System.out.println("query = "+query);

    String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx07d9e50873fe1786&secret=ddbc05a576ef96e7c08bdc31e3639d3f";
    DefaultHttpClient client = new DefaultHttpClient();//获取DefaultHttpClient请求
    HttpGet httpGet = new HttpGet(url);//HttpGet将使用Get方式发送请求URL
    JSONObject jsonObject = null;
    HttpResponse resp = client.execute(httpGet);//使用HttpResponse接收client执行httpGet的结果
    HttpEntity entity = resp.getEntity();//从response中获取结果，类型为HttpEntity
    if(entity != null){
        String result = EntityUtils.toString(entity,"UTF-8");//HttpEntity转为字符串类型
        jsonObject = JSON.parseObject(result);//字符串类型转为JSON类型
        String access_token = jsonObject.getString("access_token");
        System.out.println("access_token = "+access_token);
        url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi";
        httpGet = new HttpGet(url);
        resp = client.execute(httpGet);
        entity = resp.getEntity();
        result = EntityUtils.toString(entity,"UTF-8");
        jsonObject = JSON.parseObject(result);
        String ticket = jsonObject.getString("ticket");
        System.out.println("ticket = "+ticket);
        String str = "jsapi_ticket="+ticket+"&noncestr=Wm3WZYTPz0wzccnW&timestamp="+timeStamp+"&url=http://cloud.heyheroes.com/order/templates/index.jsp?"+query;
        str = "jsapi_ticket="+ticket+"&noncestr=Wm3WZYTPz0wzccnW&timestamp="+timeStamp+"&url=http://cloud.heyheroes.com/order/templates/success.jsp?"+query;
        System.out.println(str);
        sha1 = SHA1.encode(str);
        System.out.println("sha1 = "+sha1);
    }

    List buys = jdbcTemplate.queryForList("select * from group_buy where buy_id = ? ",new Object[]{id});
    Map buy = (Map)buys.get(0);

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<meta name="format-detection" content="telephone=no" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="yes" name="apple-touch-fullscreen">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<title>HeyHeroes</title>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
<script>

    wx.config({
        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: 'wx07d9e50873fe1786', // 必填，公众号的唯一标识
        timestamp: '<%= timeStamp %>', // 必填，生成签名的时间戳
        nonceStr: 'Wm3WZYTPz0wzccnW', // 必填，生成签名的随机串
        signature: '<%= sha1 %>',// 必填，签名
        jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ'] // 必填，需要使用的JS接口列表
    });

    wx.ready(function () {
        // 分享给朋友
        wx.onMenuShareAppMessage({
            title: "<%= buy.get("share_title") %>",
            desc: "<%= buy.get("share_desc") %>",
            link: "http://cloud.heyheroes.com/od/<%= id %>?<%= query %>",
            imgUrl: "http://cloud.heyheroes.com/<%= buy.get("share_image") %>",
            success: function () {
                alert('分享给朋友成功');
            },
            cancel: function () {
            }
        });

        // 分享朋友圈
        wx.onMenuShareTimeline({
            title: "<%= buy.get("share_title") %>",
            desc: "<%= buy.get("share_desc") %>",
            link: "http://cloud.heyheroes.com/od/<%= id %>?<%= query %>",
            imgUrl: "http://cloud.heyheroes.com/<%= buy.get("share_image") %>",
            success: function () {
                alert('分享朋友圈成功');
            },
            cancel: function () {
            }
        });

    })

    window.onresize = function () {
        document.documentElement.style.fontSize = document.documentElement.clientWidth / 7.5 + 'px';
    };
    window.onresize();
    window.onresize = function () {
        document.documentElement.style.fontSize = document.documentElement.clientWidth / 7.5 + 'px';
    };
    window.onresize();
    history.pushState(null,null, document.URL);
    window.addEventListener('popstate',function(){
        history.pushState(null,null, document.URL);
    });
</script>
<link rel="stylesheet" href="/order/css/app.css?v=<%= UUID.randomUUID() %>">
<link rel="stylesheet" href="/order/components/humane/themes/bigbox.css?v=<%= UUID.randomUUID() %>">
</head>
<style type="text/css">
    html,
    body {
        background-color: #fff;
    }
</style>
<body>
<div class="flexColumn spage">
    <div class="icon-share"></div>
    <div class="icon-success"></div>
    <div class="spage-title">支付成功</div>
    <div class="spage-sub" style="white-space : nowrap;">支付方式：微信支付</div>
    <div class="spage-sub">支付金额：¥<%= price %></div>
    <div class="spage-message">稍后您将收到订单短信</div>
</div>
<script src="/order/components/jquery/dist/jquery.min.js"></script>
<script src="/order/components/humane/humane.min.js"></script>
<script type="text/javascript">



</script>
</body>
</html>

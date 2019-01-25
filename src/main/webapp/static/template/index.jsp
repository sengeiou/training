<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.apache.http.impl.client.DefaultHttpClient" %>
<%@ page import="org.apache.http.client.methods.HttpGet" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page import="org.apache.http.HttpResponse" %>
<%@ page import="org.apache.http.HttpEntity" %>
<%@ page import="org.apache.http.util.EntityUtils" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="com.training.util.SHA1" %>
<%
System.out.println(" ****************     index.jsp  *********  ");
    String timeStamp = ""+System.currentTimeMillis()/1000;
    String nonce_str = "abc123cba321";
    String sha1 = "";
    System.out.println("timeStamp = "+timeStamp);

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
        String str = "jsapi_ticket="+ticket+"&noncestr=Wm3WZYTPz0wzccnW&timestamp="+timeStamp+"&url=http://cloud.heyheroes.com/static/template/index.jsp?"+query;
        System.out.println(str);
        sha1 = SHA1.encode(str);
        System.out.println("sha1 = "+sha1);
    }

//    Map<String, String> signMap = new HashMap<>();
//    signMap.put("appId","wx07d9e50873fe1786");
//    signMap.put("timeStamp",timeStamp);
//    signMap.put("nonceStr",nonce_str);
//    signMap.put("package",package1);
//    signMap.put("signType","MD5");
//    sign = WXPayUtil.generateSignature(signMap,key);

%>
<html>
<%--<title>测试支付页面-带缩略图</title>--%>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
<script>
    wx.config({
        debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: 'wx07d9e50873fe1786', // 必填，公众号的唯一标识
        timestamp: '<%= timeStamp %>', // 必填，生成签名的时间戳
        nonceStr: 'Wm3WZYTPz0wzccnW', // 必填，生成签名的随机串
        signature: '<%= sha1 %>',// 必填，签名
        jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ'] // 必填，需要使用的JS接口列表
    });

    wx.ready(function () {
        // 分享给朋友
        wx.onMenuShareAppMessage({
            title: "测试支付页面-带缩略图",
            desc: "测试分享内容简介",
            link: "http://cloud.heyheroes.com/static/template/index.jsp?<%= query %>",
            imgUrl: "http://cloud.heyheroes.com/static/static/300300.png",
            success: function () {
                alert('分享成功');
            },
            cancel: function () {
            }
        });
    })

</script>
<body>
<%--<div style="display: none">--%>
    <%--<img src="/static/static/300300.png" />--%>
<%--</div>--%>
<!--此图片必须放在body标签的最前面，且高度必须为屏幕宽度的80%  -->
<!-- 此处放其他元素 -->
<%--<p style ="margin-top:100px;">--%>
index.jsp 分享首页
<p style ="margin-top:100px;">
<a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx07d9e50873fe1786&redirect_uri=http://cloud.heyheroes.com/static/template/index2.jsp&response_type=code&scope=snsapi_base&state=123#wechat_redirect">index2</a>
<p style ="margin-top:100px;">
<%--<img src="/static/static/300300.png" />--%>
</body>
</html>

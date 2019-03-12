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
    System.out.println(" ****************     error.jsp  *********  ");

    String msg = request.getAttribute("msg").toString();
    System.out.println("  msg = "+msg);
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

    <div class="spage-title"><%= msg %></div>

</div>
</body>
</html>

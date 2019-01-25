<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
System.out.println(" ****************     index.jsp  *********  ");
    String timeStamp = ""+System.currentTimeMillis()/1000;
    String nonce_str = "abc123cba321";

//    Map<String, String> signMap = new HashMap<>();
//    signMap.put("appId","wx07d9e50873fe1786");
//    signMap.put("timeStamp",timeStamp);
//    signMap.put("nonceStr",nonce_str);
//    signMap.put("package",package1);
//    signMap.put("signType","MD5");
//    sign = WXPayUtil.generateSignature(signMap,key);

%>
<html>
<title>测试支付页面-带缩略图</title>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>

<script>
    wx.config({
        debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: '', // 必填，公众号的唯一标识
        timestamp: '', // 必填，生成签名的时间戳
        nonceStr: '', // 必填，生成签名的随机串
        signature: '',// 必填，签名
        jsApiList: [] // 必填，需要使用的JS接口列表
    });



</script>
<body>
<img src="/static/static/300300.png" />
<!--此图片必须放在body标签的最前面，且高度必须为屏幕宽度的80%  -->
<!-- 此处放其他元素 -->
<p style ="margin-top:100px;">
index.jsp
<p style ="margin-top:100px;">
<a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx07d9e50873fe1786&redirect_uri=http://cloud.heyheroes.com/static/template/index2.jsp&response_type=code&scope=snsapi_base&state=123#wechat_redirect">index2</a>
<p style ="margin-top:100px;">
<img src="/static/static/300300.png" />
</body>
</html>

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
        String str = "jsapi_ticket="+ticket+"&noncestr=Wm3WZYTPz0wzccnW&timestamp="+timeStamp+"&url=http://cloud.heyheroes.com/order/templates/index.jsp?"+query;
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
                link: "http://cloud.heyheroes.com/order/templates/index.jsp?<%= query %>",
                imgUrl: "http://cloud.heyheroes.com/static/static/300300.png",
                success: function () {
                    alert('分享成功');
                },
                cancel: function () {
                }
            });
        })

        window.onresize = function () {
            document.documentElement.style.fontSize = document.documentElement.clientWidth / 7.5 + 'px';
        };
        window.onresize();
    </script>
    <link rel="stylesheet" href="../css/app.css">
</head>

<style type="text/css">
    html,
    body {
        background-color: #F0EFF0;
    }
</style>

<body>
<!-- 项目图片及名称 -->
<div class="topBox">
    <img class="project-img" src="../img/porject_image.png" />
    <div class="project-title">
        [22店通用]仅售68元，价值800元极速瘦身一对一私教套课
    </div>
</div>


<!-- 开团列表 -->
<div class="flexColumn userBox">
    <div class="flexRow userBox-head">
        <div class="userBox-head-label">36人在拼单</div>
        <div class="flexRow userBox-head-link btn-hover" id="btnOpenModalList">
            <div class="userBox-head-link-label">查看更多</div>
            <div class="userBox-head-link-icon" ></div>
        </div>
    </div>

    <div class="userBox-list">
        <!-- 开团人 -->
        <div class="flexRow userBox-item">
            <div class="flexRow userBox-item-left">
                <img class="userBox-item-left-img" src="../img/headImage.png" />
                <div class="userBox-item-left-name">听听</div>
            </div>
            <div class="flexRow userBox-item-right">
                <div class="userBox-item-right-info">
                    <div class="userBox-item-right-info-top">还差<span>2人</span>拼成</div>
                    <div class="userBox-item-right-info-bottom">剩余29天 20: 43: 30</div>
                </div>
                <div class="userBox-item-right-btn btn-hover btnOpenModalProject">去拼团</div>
            </div>
        </div>
        <!-- 开团人 -->
        <!-- 开团人 -->
        <div class="flexRow userBox-item">
            <div class="flexRow userBox-item-left">
                <img class="userBox-item-left-img" src="../img/headImage.png" />
                <div class="userBox-item-left-name">听听</div>
            </div>
            <div class="flexRow userBox-item-right">
                <div class="userBox-item-right-info">
                    <div class="userBox-item-right-info-top">还差<span>2人</span>拼成</div>
                    <div class="userBox-item-right-info-bottom">剩余29天 20: 43: 30</div>
                </div>
                <div class="userBox-item-right-btn btn-hover btnOpenModalProject">去拼团</div>
            </div>
        </div>
        <!-- 开团人 -->
    </div>
</div>

<!-- 项目简介 -->
<div class="protectBox">
    <div class="protectBox-title">内容简介</div>
    <div class="protectBox-text">舞蹈是一种表演艺术，使用身体来完成各种优雅或高难度的动作，一般有音乐伴奏，以有节奏的动作为主要表现手段的艺术形式。它一般借助音乐，也借助其他的道具。
    </div>
    <img class="protectBox-image" src="../img/porject_image.png"/>
</div>

<!-- 底部按钮 -->
<div class="flexRow bottomBtnBox">
    <a class="flexColumn bottomBtnBox-btn btn-hover">
        <div class="bottomBtnBox-btn-top">￥99</div>
        <div class="bottomBtnBox-btn-top">单独购买</div>
    </a>
    <a class="flexColumn bottomBtnBox-btn bottomBtnBox-btn-right btn-hover">
        <div class="bottomBtnBox-btn-top">￥69</div>
        <div class="bottomBtnBox-btn-top">发起拼团</div>
    </a>
</div>


<!-- 正在拼团提示框 -->
<div class="flexRow cover" id="modalList" style="display:none">
    <div class="flexColumn modal-user">
        <div class="modal-close"  id="btnCloseModalList"></div>
        <div class="modal-user-title">正在拼团</div>
        <div class="modal-userBox">
            <!-- 遍历部分-拼团item -->
            <div class="flexRow modal-userBox-item">
                <div class="flexRow modal-userBox-item-left">
                    <img class="modal-userBox-item-left-head" src="../img/headImage.png"/>
                    <div class="flexColumn modal-userBox-item-left-infoBox">
                        <div class="modal-userBox-item-left-infoBox-top">machiael 还差2人</div>
                        <div class="modal-userBox-item-left-infoBox-bottom">剩余29天 20: 43: 30</div>
                    </div>
                </div>
                <div class="modal-userBox-item-right btn-hover btnOpenModalProject">去拼团</div>
            </div>
            <!-- 遍历部分-拼团item -->

            <!-- 遍历部分-拼团item -->
            <div class="flexRow modal-userBox-item">
                <div class="flexRow modal-userBox-item-left">
                    <img class="modal-userBox-item-left-head" src="../img/headImage.png"/>
                    <div class="flexColumn modal-userBox-item-left-infoBox">
                        <div class="modal-userBox-item-left-infoBox-top">machiael 还差2人</div>
                        <div class="modal-userBox-item-left-infoBox-bottom">剩余29天 20: 43: 30</div>
                    </div>
                </div>
                <div class="modal-userBox-item-right btn-hover btnOpenModalProject">去拼团</div>
            </div>
            <!-- 遍历部分-拼团item -->

        </div>
    </div>
</div>


<!-- 参与拼团提示框 -->
<div class="flexRow cover" id="modalProject"  style="display:none">
    <div class="flexColumn modal-project">
        <div class="modal-close"  id="btnCloseModalProject"></div>
        <div class="modal-project-title">参与XXX的拼单</div>
        <div class="modal-project-sub">仅剩<span>2</span>个名额，29天 20: 43: 30后结束</div>
        <div class="flexRow modal-project-users">
            <img class="modal-project-users-image" src="../img/headImage.png" />
            <img class="modal-project-users-image" src="../img/headImage.png" />
            <img class="modal-project-users-image" src="../img/icon-noUser.png" />
        </div>
        <a class="modal-project-btn btn-hover" href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx07d9e50873fe1786&redirect_uri=http://cloud.heyheroes.com/order/templates/index2.jsp&response_type=code&scope=snsapi_base&state=123#wechat_redirect">参与拼团</a>
    </div>
</div>
</div>

<script src="../components/jquery/dist/jquery.min.js"></script>
<script type="text/javascript">
    $(function(){
        $('#btnOpenModalList').on('click',function(){
            $('#modalList').show();
        })
        $('#btnCloseModalList').on('click',function(){
            $('#modalList').hide();
        })


        $('.btnOpenModalProject').on('click',function(){
            $('#modalList').hide();
            $('#modalProject').show();
        })
        $('#btnCloseModalProject').on('click',function(){
            $('#modalProject').hide();
        })
    });



</script>
</body>

</html>

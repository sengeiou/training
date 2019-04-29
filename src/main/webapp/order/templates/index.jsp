<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.apache.http.impl.client.DefaultHttpClient" %>
<%@ page import="org.apache.http.client.methods.HttpGet" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page import="org.apache.http.HttpResponse" %>
<%@ page import="org.apache.http.HttpEntity" %>
<%@ page import="org.apache.http.util.EntityUtils" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="com.training.util.SHA1" %>
<%@ page import="org.springframework.jdbc.core.JdbcTemplate" %>
<%@ page import="com.training.config.ContextUtil" %>
<%@ page import="com.training.util.ut" %>
<%
System.out.println(" ****************     index.jsp  *********  ");
    String timeStamp = ""+System.currentTimeMillis()/1000;
    System.out.println("timeStamp = "+timeStamp);
    String id = request.getAttribute("id").toString();

    String openId ="";
    boolean fromMicro = false;

    String domain = "http://cloud.heyheroes.com";

    if(request.getAttribute("openId")!=null){
        openId = request.getAttribute("openId").toString();
        fromMicro = true;
        domain = "http://trainingbj.huai23.com";
    }
    System.out.println("fromMicro = "+fromMicro);
    System.out.println("domain = "+domain);

    String uri = request.getRequestURI();
    System.out.println("uri = "+uri);
    String query = request.getQueryString();
    System.out.println("query = "+query);

    JdbcTemplate jdbcTemplate = (JdbcTemplate) ContextUtil.getBean("jdbcTemplate");
    List buys = jdbcTemplate.queryForList("select * from group_buy where buy_id = ? ",new Object[]{id});
    Map buy = (Map)buys.get(0);
    String startDate = buy.get("start_date").toString();
    String endDate = buy.get("end_date").toString();

    if(ut.passDayByDate(startDate,ut.currentDate())>0){
    }
    if(ut.passDayByDate(endDate,ut.currentDate())<0){

    }

    String sha1 = "";
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
        str = "jsapi_ticket="+ticket+"&noncestr=Wm3WZYTPz0wzccnW&timestamp="+timeStamp+"&url="+domain+"/od/"+id+"?"+query;
        System.out.println(str);
        sha1 = SHA1.encode(str);
        System.out.println("sha1 = "+sha1);
    }

    jdbcTemplate.update("update group_buy set view_count = view_count + 1 where buy_id = ?",new Object[]{id});

    String buyId = buy.get("buy_id").toString();
    String init_count = buy.get("init_count").toString();
    String sale_count = buy.get("sale_count").toString();

    int count = Integer.parseInt(init_count)+Integer.parseInt(sale_count);

    int passdays = ut.passDayByDate(ut.currentDate(),endDate);
    if(passdays<0){
        passdays = 1;
    }

    List orders = jdbcTemplate.queryForList("select * from group_order where buy_id = ? and status = 2 and main_flag = '1' ",new Object[]{buyId});
    System.out.println(" orders = "+orders.size());

//    Map<String, String> signMap = new HashMap<>();
//    signMap.put("appId","wx07d9e50873fe1786");
//    signMap.put("timeStamp",timeStamp);
//    signMap.put("nonceStr",nonce_str);
//    signMap.put("package",package1);
//    signMap.put("signType","MD5");
//    sign = WXPayUtil.generateSignature(signMap,key);

    String query2 = query.replaceAll("&","@@@");
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
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
    <script src="/order/components/jquery/dist/jquery.min.js"></script>
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

        $(function(){

            $("#gotoBuy").click(function () {
                var buyId = "<%= buy.get("buy_id") %>";
                var info = buyId+"_0<%= fromMicro==true?"_"+openId:"" %>";
                var url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx07d9e50873fe1786&redirect_uri=http://cloud.heyheroes.com/order/templates/index<%= fromMicro==true?"3":"2" %>.jsp?info="+info+"@_@<%= query2 %>&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
                document.location.href = url;
                return false;
            })

            $("#createPt").click(function () {
                var buyId = "<%= buy.get("buy_id") %>";
                var info = buyId+"_1<%= fromMicro==true?"_"+openId:"" %>";
                var url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx07d9e50873fe1786&redirect_uri=http://cloud.heyheroes.com/order/templates/index<%= fromMicro==true?"3":"2" %>.jsp?info="+info+"@_@<%= query2 %>&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
                document.location.href = url;
                return false;
            })


            $("#gotoPt").click(function () {
                var buyId = "<%= buy.get("buy_id") %>";
                var mainOrderId = $("#mainOrderId").val();
                var info = buyId+"_2_"+mainOrderId<%= fromMicro==true?"_"+openId:"" %>;
                var url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx07d9e50873fe1786&redirect_uri=http://cloud.heyheroes.com/order/templates/index<%= fromMicro==true?"3":"2" %>.jsp?info="+info+"@_@<%= query2 %>&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
                document.location.href = url;
                return false;
            })

        })
    </script>
    <link rel="stylesheet" href="/order/css/app.css?v=<%= UUID.randomUUID() %>">
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
    <img class="project-img" src="<%= buy.get("image") %>" />
    <div class="project-title">
        <%= buy.get("title") %>
    </div>
    <div class="project-sub">共有<span><%= count %></span>位参与</div>
</div>


<!-- 开团列表 -->
<div class="flexColumn userBox">
    <div class="flexRow userBox-head">
        <div class="userBox-head-label"><%= orders.size() %>人在拼单</div>
        <div class="flexRow userBox-head-link btn-hover" id="btnOpenModalList">
            <% if(orders.size() > 2) { %>
            <div class="userBox-head-link-label">查看更多</div>
            <div class="userBox-head-link-icon" ></div>
            <% } %>
        </div>
    </div>

    <div class="userBox-list">
        <%
            for(int i=0;i< (orders.size()>2?2:orders.size());i++){
                Map order = (Map)orders.get(i);
        %>
        <!-- 开团人 -->
        <div class="flexRow userBox-item">
            <div class="flexRow userBox-item-left">
                <img class="userBox-item-left-img" src="/order/img/default_icon.png?v=<%= UUID.randomUUID() %>" />
                <div class="userBox-item-left-name">匿名用户</div>
            </div>
            <div class="flexRow userBox-item-right">
                <div class="userBox-item-right-info">
                    <div class="userBox-item-right-info-top">还差<span>1人</span>拼成</div>
                    <div class="userBox-item-right-info-bottom">剩余<%= passdays %>天</div>
                </div>
                <div class="userBox-item-right-btn btn-hover btnOpenModalProject" orderId="<%= order.get("order_id") %>">去拼团</div>
            </div>
        </div>
        <!-- 开团人 -->
        <% } %>
        <%--<!-- 开团人 -->--%>
        <%--<div class="flexRow userBox-item">--%>
            <%--<div class="flexRow userBox-item-left">--%>
                <%--<img class="userBox-item-left-img" src="../img/headImage.png" />--%>
                <%--<div class="userBox-item-left-name">听听</div>--%>
            <%--</div>--%>
            <%--<div class="flexRow userBox-item-right">--%>
                <%--<div class="userBox-item-right-info">--%>
                    <%--<div class="userBox-item-right-info-top">还差<span>2人</span>拼成</div>--%>
                    <%--<div class="userBox-item-right-info-bottom">剩余29天 20: 43: 30</div>--%>
                <%--</div>--%>
                <%--<div class="userBox-item-right-btn btn-hover btnOpenModalProject">去拼团</div>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<!-- 开团人 -->--%>
    </div>
</div>

<!-- 项目简介 -->
<div class="protectBox">
    <div class="protectBox-title">内容简介</div>
    <div class="protectBox-text"><%= buy.get("content") %></div>
    <%--<img class="protectBox-image" src="/order/img/porject_image.png"/>--%>
</div>

<!-- 底部按钮 -->
<div class="flexRow bottomBtnBox">
    <a class="flexColumn bottomBtnBox-btn btn-hover">
        <div class="bottomBtnBox-btn-top">￥<%= buy.get("price") %></div>
        <div class="bottomBtnBox-btn-top" id="gotoBuy" >单独购买</div>
    </a>
    <a class="flexColumn bottomBtnBox-btn bottomBtnBox-btn-right btn-hover">
        <div class="bottomBtnBox-btn-top">￥<%= buy.get("group_price") %></div>
        <div class="bottomBtnBox-btn-top" id="createPt" >发起拼团</div>
    </a>
</div>


<!-- 正在拼团提示框 -->
<div class="flexRow cover" id="modalList" style="display:none">
    <div class="flexColumn modal-user">
        <div class="modal-close"  id="btnCloseModalList"></div>
        <div class="modal-user-title">正在拼团</div>
        <div class="modal-userBox">

            <%
                for(int i=2;i<orders.size();i++){
                    Map order = (Map)orders.get(i);
            %>

            <!-- 遍历部分-拼团item -->
            <div class="flexRow modal-userBox-item">
                <div class="flexRow modal-userBox-item-left">
                    <img class="modal-userBox-item-left-head" src="/order/img/default_icon.png?v=<%= UUID.randomUUID() %>"/>
                    <div class="flexColumn modal-userBox-item-left-infoBox">
                        <div class="modal-userBox-item-left-infoBox-top">还差1人</div>
                        <div class="modal-userBox-item-left-infoBox-bottom">剩余<%= passdays %>天</div>
                    </div>
                </div>
                <div class="modal-userBox-item-right btn-hover btnOpenModalProject" orderId="<%= order.get("order_id") %>">去拼团</div>
            </div>
            <!-- 遍历部分-拼团item -->
            <% } %>
            <%--<!-- 遍历部分-拼团item -->--%>
            <%--<div class="flexRow modal-userBox-item">--%>
                <%--<div class="flexRow modal-userBox-item-left">--%>
                    <%--<img class="modal-userBox-item-left-head" src="../img/headImage.png"/>--%>
                    <%--<div class="flexColumn modal-userBox-item-left-infoBox">--%>
                        <%--<div class="modal-userBox-item-left-infoBox-top">machiael 还差2人</div>--%>
                        <%--<div class="modal-userBox-item-left-infoBox-bottom">剩余29天 20: 43: 30</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="modal-userBox-item-right btn-hover btnOpenModalProject">去拼团</div>--%>
            <%--</div>--%>
            <%--<!-- 遍历部分-拼团item -->--%>

        </div>
    </div>
</div>


<!-- 参与拼团提示框 -->
<div class="flexRow cover" id="modalProject"  style="display:none">
    <div class="flexColumn modal-project">
        <div class="modal-close"  id="btnCloseModalProject"></div>
        <div class="modal-project-title">参与拼单</div>
        <input id = "mainOrderId" type="hidden" value ="" />
        <div class="modal-project-sub">仅剩<span>1</span>个名额，<%= passdays %>天后结束</div>
        <div class="flexRow modal-project-users">
            <img class="modal-project-users-image" src="/order/img/default_icon.png" />
            <img class="modal-project-users-image" src="/order/img/icon-noUser.png" />
        </div>
        <a id="gotoPt" class="modal-project-btn btn-hover" href="">参与拼团</a>
    </div>
</div>
</div>

<script src="/order/components/jquery/dist/jquery.min.js"></script>
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
            $("#mainOrderId").val($(this).attr("orderId"));
            $('#modalProject').show();
        })
        $('#btnCloseModalProject').on('click',function(){
            $('#modalProject').hide();
            $("#mainOrderId").val('');
        })
    });



</script>
</body>

</html>

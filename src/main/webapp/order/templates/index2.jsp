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
    String info  = request.getParameter("info");
    System.out.println(" ****************     info = "+info);
    String[] infos = info.split("_");
    String buyId = infos[0];
    String type = infos[1];
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
    String access_token = obj.getString("access_token");
    System.out.println(" ****************     access_token = "+access_token);
    System.out.println(" ****************     openId = "+openId);

    JdbcTemplate jdbcTemplate = (JdbcTemplate)ContextUtil.getBean("jdbcTemplate");


    List stores = jdbcTemplate.queryForList("select * from store  ",new Object[]{ });


    List buys = jdbcTemplate.queryForList("select * from group_buy where buy_id =  ? ",new Object[]{buyId});
    Map buy = (Map)buys.get(0);
    String price = buy.get("group_price").toString();
    String mainOrderId = "";
    if(type.equals("0")){
        price = buy.get("price").toString();
    }

    if(type.equals("2")){
        mainOrderId = infos[2];
    }

//    String info_url = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openId+"&lang=zh_CN";
//    httpGet = new HttpGet(info_url);
//    httpResponse = httpClient.execute(httpGet);
//    httpEntity = httpResponse.getEntity();
//    String infos = EntityUtils.toString(httpEntity, "utf-8");
//    System.out.println(" ****************     infos = "+infos);

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
    <script src="../components/jquery/dist/jquery.min.js"></script>
    <script src="../components/humane/humane.min.js"></script>
    <script>
        flag = 0;
        window.onresize = function () {
            document.documentElement.style.fontSize = document.documentElement.clientWidth / 7.5 + 'px';
        };
        window.onresize();
    </script>

    <link rel="stylesheet" href="../css/app.css?v=<%= UUID.randomUUID() %>">
    <link rel="stylesheet" href="../components/humane/themes/bigbox.css?v=<%= UUID.randomUUID() %>">
</head>

<style type="text/css">
    html,
    body {
        background-color: #fff;
    }
</style>

<body>
<!-- 添加信息 -->
<a class="flexRow infoBox btn-hover" id="showAddPanel">
    <div class="flexRow infoBox-left">
        <div class="icon-add"></div>
        <div class="infoBox-left-label">添加您的信息</div>
    </div>
    <div class="infoBox-link"></div>
</a>

<!-- 项目信息 -->
<div class="flexRow projectBox">
    <img class="projectBox-img" align="middle" src="../img/porject_image.png"/>
    <div class="projectBox-info">
        [22店通用]仅售68元，价值800元极速瘦身一对一私教套课
    </div>
</div>

<!-- 支付信息 -->
<div class="flexRow payBox">
    <div class="flexRow payBox-left">
        <div class="payBox-img" ></div>
        <div class="payBox-label">微信支付</div>
    </div>

    <div class="payBox-btn">
    </div>
</div>

<div class="pay-tip">拼团成功后，系统将会将产品体验码发送至您预留的手机号码，您可到店进行体验。</div>

<!-- 底部按钮 -->
<div class="flexRow bottomBtnBox payBottomBtn">
    <div class="flexRow bottomBtnBox-paybtn-info">
        <div class="bottomBtnBox-info-label">实付:</div>
        <div class="bottomBtnBox-info-num">￥<%= price %></div>
    </div>
    <div id="payBtn" class="bottomBtnBox-paybtn btn-hover">立即支付
    </div>
</div>

<!-- 添加信息面板 -->
<div class="flexColumn addPanel formBox " style="display:none">
    <div class="flexRow formBox-item formBox-selection">
        <div class="formBox-item-label">您体验的门店</div>
        <div class="flexRow formBox-item-selection">
            <select name="" class="formBox-selectedBox"  >
                <%
                    for(int i=0;i<stores.size();i++){
                        Map store = (Map)stores.get(i);
                %>
                <option value="<%= store.get("store_id")%>"><%= store.get("name")%></option>
                <% } %>
            </select>
            <div class="formBox-item-selection-label" id="selectedLabel"></div>
            <img class="fromBox-item-selection-icon"  src="../img/icon-selection.png"/>
        </div>
    </div>

    <div class="flexRow formBox-item">
        <div class="formBox-item-label">姓名</div>
        <div class="flexRow formBox-item-box">
            <input id="custname" class="formBox-item-box-input" value=""/>
        </div>
    </div>

    <div class="flexRow formBox-item">
        <div class="formBox-item-label">性别</div>
        <div class="flexRow formBox-item-radioBox">

            <div class="flexRow formBox-item-radioBox-item" data-value="0">
                <div class="radio-btn" data-value="0"></div>
                <div class="radio-label" data-value="0">男</div>
            </div>
            <div class="flexRow formBox-item-radioBox-item" data-value="1">
                <div class="radio-btn radio-btn-selected" data-value="1"></div>
                <div class="radio-label" data-value="1">女</div>
            </div>
            <input class="item-radio" name="gander" type="radio"  style="display:none"/>
        </div>
    </div>

    <div class="flexRow formBox-item">
        <div class="formBox-item-label">手机号</div>
        <div class="flexRow formBox-item-box">
            <input id="" class="formBox-item-box-input" value=""/>
        </div>
    </div>

    <div class="flexRow formBox-item">
        <div class="formBox-item-label">验证码</div>
        <div class="flexRow formBox-item-box">
            <input class="formBox-item-box-input-short" value=""/>
            <div class="formBox-item-box-input-btn btn-hover" id="btnSend">获取验证码</div>
        </div>
    </div>

    <div class="formBox-btn btn-hover" id="btnAdd">保存</div>
</div>

<script type="text/javascript">



    $(function(){

        var timer;
        var defaultCount = 10;
        var count = 0;
        var sending = false;

        /* 显示添加面板 */
        $('#showAddPanel').on('click',function(){
            $('.addPanel').show();
        })

        $('#btnAdd').on('click',function(){
            $('.addPanel').hide();
//            sending = false;
        })

        /* 单选框 */
        $('.formBox-item-radioBox-item').on('click',function(){
            var value = $(this).data('value');
            $('.radio-btn').removeClass('radio-btn-selected');
            $(this).find('.radio-btn').addClass('radio-btn-selected')
            $('.item-radio').val(value)
        })

        /* 下拉框 */
        $('.formBox-selectedBox').on('change',function(){
//            var value=$(this).val();
//            var label = $(this)[0][value].text;
//            $('.item-select').val(value)
//            document.getElementById('selectedLabel').innerHTML = label;
        })

        $('#btnSend').on('click',function(){
            if(sending){
                return humane.log(count + '秒后重发');
            }

            if($("#phone").val()==''){
                alert('请先填写手机号码');
                return;
            }
            sending = true;
            var info = {};
            info.phone= $("#phone").val();
            alert(info.phone)
            $.post("/api/member/sendCode",info,function(result){
                alert(JSON.stringify(result))
//                alert(result.status)
                if(result.status!=200){
                    alert("发送验证码异常，请稍后再试！"+result.msg);
                    setTimeout(function(){
                        sending = false;
                    },1000);
                    return;
                }
                clearInterval(timer);

                count = defaultCount;
                timer = setInterval(function(){
                    document.getElementById('btnSend').innerHTML = count + '秒后重发';
                    count -= 1;
                    if(count < 0){
                        sending = false;
                        clearInterval(timer);
                        count = defaultCount;
                        document.getElementById('btnSend').innerHTML = '获取验证码';
                    }
                },1000)

            }, "json");

        })

        $('#payBtn').on('click',function(){
            if(flag>0){
                return;
            }
            if($("#phone").val()==''){
                alert('请先填写您的信息');
                return;
            }
            flag = 1;
            var order = {};
            order.buyId="<%= buyId %>";
            order.storeId="1";
            order.phone=$("#phone").val();
            order.name=$("#custname").val();
            order.gender="1";
            <%--order.totalFee="<%= price %>";--%>
            order.totalFee="1";
            order.mainFlag="<%= type %>";
            order.mainOrderId="<%= mainOrderId %>";
            order.count="1";
            order.openId="<%= openId %>";

            $.post("/api/groupOrder/addOrder",order,function(result){
//                alert(JSON.stringify(result))
//                alert(result.status)
                if(result.status!=200){
                    alert("创建订单异常，请稍后再试1！");
                    setTimeout(function(){
                        flag = 0;
                    },1000);
                    return;
                }
//                alert(result)
//                alert(result.msg)
                WeixinJSBridge.invoke(
                    'getBrandWCPayRequest', {
                        "appId":"wx07d9e50873fe1786",     //公众号名称，由商户传入
                        "timeStamp":''+result.data.timeStamp,         //时间戳，自1970年以来的秒数
                        "nonceStr":result.data.nonceStr, //随机串
                        "package":result.data.package,
                        "signType":"MD5",         //微信签名方式：
                        "paySign":result.data.paySign //微信签名
                    },
                    function(res){
                        if(res.err_msg == "get_brand_wcpay_request:ok" ){
                            // 使用以上方式判断前端返回,微信团队郑重提示：
                            //res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
                            alert("支付成功："+res);
                            document.location.href="success.jsp";
                        }else{
                            flag = 0;
//                            alert("支付失败："+res);
                        }
                    }
                );

            }, "json");

        })

    });

    function onBridgeReady(){
        <%--WeixinJSBridge.invoke(--%>
            <%--'getBrandWCPayRequest', {--%>
                <%--"appId":"wx07d9e50873fe1786",     //公众号名称，由商户传入--%>
                <%--"timeStamp":"<%= timeStamp %>",         //时间戳，自1970年以来的秒数--%>
                <%--"nonceStr":"<%=nonce_str%>", //随机串--%>
                <%--"package":"<%=package1%>",--%>
                <%--"signType":"MD5",         //微信签名方式：--%>
                <%--"paySign":"<%=sign%>" //微信签名--%>
            <%--},--%>
            <%--function(res){--%>
                <%--if(res.err_msg == "get_brand_wcpay_request:ok" ){--%>
                    <%--// 使用以上方式判断前端返回,微信团队郑重提示：--%>
                    <%--//res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。--%>
                    <%--alert("支付成功："+res);--%>
                <%--}--%>
            <%--}--%>
        <%--);--%>
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
</body>

</html>

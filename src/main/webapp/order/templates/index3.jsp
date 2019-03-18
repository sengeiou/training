<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.training.config.ContextUtil"%>
<%@ page language="java" import="org.springframework.jdbc.core.JdbcTemplate"%>
<%@ page import="com.training.common.Const" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
    System.out.println(" ****************     index.jsp  *********  ");
    String info  = request.getParameter("info");
    System.out.println(" ****************     info = "+info);
    String[] infos = info.split("_");
    String buyId = infos[0];
    String type = infos[1];

    String openId = infos[infos.length-1];
    System.out.println(" ****************   jsp3_openId = "+openId);

//    String access_token = obj.getString("access_token");
//    System.out.println(" ****************     access_token = "+access_token);

    Const.openIds.add(openId);
    JdbcTemplate jdbcTemplate = (JdbcTemplate)ContextUtil.getBean("jdbcTemplate");

    List buys = jdbcTemplate.queryForList("select * from group_buy where buy_id =  ? ",new Object[]{buyId});
    Map buy = (Map)buys.get(0);
    List<Map> storeList = new ArrayList<>();
    if(buy.get("store_list")!=null){
        String[] list = buy.get("store_list").toString().split("#");
        for (int i = 0; i < list.length; i++) {
            if(StringUtils.isNotEmpty(list[i])){
                Map<String,String> map = new HashMap<>();
                map.put("store_id",list[i]);
                map.put("name",list[i]);
                storeList.add(map);
            }
        }
    }
    if(storeList.size()==0){
        Map<String,String> map = new HashMap<>();
        map.put("store_id","无");
        map.put("name","无");
        storeList.add(map);
    }

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

    <script>
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
<!-- 项目信息 -->
<div class="flexRow projectBox">
    <img class="projectBox-img" align="middle" src="../img/porject_image.png"/>
    <div class="projectBox-info">
        <%= buy.get("title") %>
    </div>
</div>
<!-- 添加信息面板 -->
<div class="flexColumn addInfoBox formBox " >
    <div class="flexRow formBox-item formBox-selection">
        <div class="formBox-item-label">您体验的门店</div>
        <div class="flexRow formBox-item-selection">
            <select name="" class="formBox-selectedBox"  >
                <option value="-1">请选择</option>
                <%
                    for(int i=0;i<storeList.size();i++){
                        Map store = (Map)storeList.get(i);
//                        System.out.println(" ====    store_id = "+store.get("store_id")+" , name = "+store.get("name"));
                %>
                <option value="<%= store.get("store_id") %>"><%= store.get("name") %></option>
                <% } %>
            </select>
            <div class="formBox-item-selection-label" id="selectedLabel"></div>
            <img class="formBox-item-selection-icon"  src="../img/icon-selection.png"/>
        </div>
    </div>

    <div class="flexRow formBox-item">
        <div class="formBox-item-label">姓名</div>
        <div class="flexRow formBox-item-box">
            <input class="formBox-item-box-input" id="custname" value=""/>
        </div>
    </div>

    <div class="flexRow formBox-item">
        <div class="formBox-item-label">性别</div>
        <div class="flexRow formBox-item-radioBox">

            <div class="flexRow formBox-item-radioBox-item" data-value="0">
                <div class="radio-btn radio-btn-selected" data-value="0"></div>
                <div class="radio-label" data-value="0">男</div>
            </div>
            <div class="flexRow formBox-item-radioBox-item" data-value="1">
                <div class="radio-btn " data-value="1"></div>
                <div class="radio-label" data-value="1">女</div>
            </div>
            <input class="item-radio"  id="gander" name="gander" value="0" style="display:none"/>
        </div>
    </div>

    <div class="flexRow formBox-item">
        <div class="formBox-item-label">手机号</div>
        <div class="flexRow formBox-item-box">
            <input class="formBox-item-box-input" id="phone" value="" />
        </div>
    </div>

    <div class="flexRow formBox-item">
        <div class="formBox-item-label">验证码</div>
        <div class="flexRow formBox-item-box">
            <input class="formBox-item-box-input-short" id="validCode" value=""/>
            <div class="formBox-item-box-input-btn btn-hover" id="btnSend">获取验证码</div>
        </div>
    </div>

</div>
<!-- 添加信息 -->
<!--
<a class="flexRow infoBox btn-hover" id="showAddPanel">
  <div class="flexRow infoBox-left">
    <div class="icon-add"></div>
    <div class="infoBox-left-label">添加您的信息</div>
  </div>
  <div class="infoBox-link"></div>
</a>
-->



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
<script src="https://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
<script src="../components/jquery/dist/jquery.min.js"></script>
<script src="../components/humane/humane.min.js"></script>
<script type="text/javascript">
    flag = 0;
    $(function(){

        $('input').on('blur',function(){
            setTimeout(function(){
                window.scrollTo(0, 0)
            },100)
        })

        /* 显示添加面板 */
        $('#showAddPanel').on('click',function(){
            $('.addPanel').show();
        })

        $('#btnAdd').on('click',function(){
            $('.addPanel').hide();
        })


        var timer;
        var defaultCount = 60;
        var count = 0;
        var sending = false;
        /* 单选框 */
        $('.formBox-item-radioBox-item').on('click',function(){
            var value = $(this).data('value');
            $('.radio-btn').removeClass('radio-btn-selected');
            $(this).find('.radio-btn').addClass('radio-btn-selected')
            $('.item-radio').val(value)
        })

        /* 下拉框 */
        $('.formBox-selectedBox').on('change',function(){
            var value=$(this).val();
//            alert(value)
            if(value==-1||value=="-1"){
                $('.item-select').val("")
                document.getElementById('selectedLabel').innerHTML = "";
                return;
            }
            var checkText=$(this).find("option:selected").text()
//            alert(checkText)
//            var label = $(this)[0][value].text;
//            alert(label)
            if(checkText.length>10){
                checkText = checkText.substring(0, 9)+"...";
            }
            $('.item-select').val(value);
            document.getElementById('selectedLabel').innerHTML = checkText;
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
            info.openId = '<%= openId %>';
            //alert(info.phone)
            $.post("/api/member/sendOrderCode",info,function(result){
                //alert(JSON.stringify(result))
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
            flag = 1;
            if($("#phone").val()==''){
                alert('请先填写您的信息');
                flag = 0;
                return;
            }
            var storeId = $('.formBox-selectedBox').val();
            if(storeId==""||storeId=="-1"||storeId==-1){
                alert('请先选中您所在的门店');
                flag = 0;
                return;
            }

            var validCode = $.trim($('#validCode').val());
            if(validCode==""){
                alert('请填写手机验证码');
                flag = 0;
                return;
            }

            var gender = $('.item-radio').val();
//            alert("提交成功");
//            flag = 0;
//            return;

            var order = {};
            order.buyId="<%= buyId %>";
            order.storeId=storeId;
            order.phone=$("#phone").val();
            order.name=$("#custname").val();
            order.gender=gender;
            <%--order.totalFee="<%= price %>";--%>
            order.totalFee="<%= price %>";
            order.mainFlag="<%= type %>";
            order.mainOrderId="<%= mainOrderId %>";
            order.count="1";
            order.openId="<%= openId %>";
            order.validCode=validCode;
            order.microTag=1;

            $.post("/api/groupOrder/addOrder",order,function(result){
//                alert(JSON.stringify(result))
//                alert(result.msg)
                if(result.status!=200){
                    alert(result.msg);
                    setTimeout(function(){
                        flag = 0;
                    },1000);
                    return;
                }
//                alert(result)
//                alert(result.msg)
                var params = '?timestamp='+result.data.timeStamp+'&nonceStr='+result.data.nonceStr
                    +'&'+result.data.package+'&signType='+result.data.signType
                    +'&paySign='+result.data.sign+'&orderId='+result.data.orderId+'&pType=0';
                var path = '/pages/wxViewPay/wxViewPay'+params;
                wx.miniProgram.navigateTo({url: path});
                flag = 0;

            }, "json");

        })


    });


</script>
</body>

</html>

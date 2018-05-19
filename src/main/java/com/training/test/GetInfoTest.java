package com.training.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.SmartworkBpmsProcessinstanceListRequest;
import com.dingtalk.api.response.SmartworkBpmsProcessinstanceListResponse;
import com.training.util.HttpHelper;
import com.training.util.OApiException;

import java.util.List;

/**
 * Created by huai23 on 2018/5/14.
 */
public class GetInfoTest {

    public static String getSsoToken() throws OApiException {
        String id = "ding2b15384088c51357";
        String secret = "Kjj5NKpRE4k22wXToqEkwEfPS6LFNayKgN4UpJt5-ha9vo1RnFQWRzjLusarpMcR";
        String url = "https://oapi.dingtalk.com/gettoken?corpid="+id+"&corpsecret=" + secret;
        JSONObject response = HttpHelper.httpGet(url);
        String ssoToken;
        if (response.containsKey("access_token")) {
            ssoToken = response.getString("access_token");
        } else {
            throw new OApiException("Sso_token");
        }
        return ssoToken;

    }


    public static void main(String[] args) throws Exception{
        String access_token = getSsoToken();
        System.out.println(access_token);

//        String access_token = "98f8462911dd32b888c60da24ecd7fde";


        DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
        SmartworkBpmsProcessinstanceListRequest req = new SmartworkBpmsProcessinstanceListRequest();
        req.setProcessCode("PROC-EF6Y0XWVO2-RG59KJS2S58XL1O495ZN2-QABKW8MI-19");
        req.setStartTime(System.currentTimeMillis()-1000*60*60*24*15);
        req.setEndTime(System.currentTimeMillis());
        req.setSize(10L);
        req.setCursor(0L);
        SmartworkBpmsProcessinstanceListResponse rsp = client.execute(req, access_token);
        JSONObject result = JSON.parseObject(rsp.getBody());
        SmartworkBpmsProcessinstanceListResponse.DingOpenResult result1 = rsp.getResult();
        SmartworkBpmsProcessinstanceListResponse.PageResult result2 = result1.getResult();
        List<SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo> data = result2.getList();

        for (SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo item : data){
            System.out.println(" ================================  ");
            System.out.println(""+item.getTitle());
            List<SmartworkBpmsProcessinstanceListResponse.FormComponentValueVo> forms = item.getFormComponentValues();
            for (SmartworkBpmsProcessinstanceListResponse.FormComponentValueVo form : forms){
                System.out.println(form.getName()+" : "+form.getValue());
            }
        }


    }

}

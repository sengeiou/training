package com.training.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.SmartworkBpmsProcessinstanceListRequest;
import com.dingtalk.api.response.SmartworkBpmsProcessinstanceListResponse;
import com.training.util.HttpHelper;
import com.training.util.OApiException;
import com.training.util.ut;

import java.util.Date;
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
        req.setProcessCode("PROC-EF6Y0XWVO2-RG59KJS2S58XL1O495ZN2-QABKW8MI-19");  // 新签合同（次卡私教）

        req.setProcessCode("PROC-FF6YQLE1N2-HESQB9ZYOW7FOIN97KIL1-84X7L0BJ-E");  // 新签合同（月卡私教）


        Long start = System.currentTimeMillis()-1000*60*60*24*35;
        Long end = System.currentTimeMillis()-1000*60*60*24*30;

        System.out.println(" ================================ start "+start);
        System.out.println(" ================================ end "+end);

        System.out.println(" ================================ start = "+ ut.df_day.format(new Date(start)) );
        System.out.println(" ================================ start2 "+ut.df_day.parse("2018-04-01").getTime());

        start = ut.df_day.parse("2011-12-21").getTime();

        req.setStartTime(start);
//        req.setEndTime(System.currentTimeMillis());
        req.setSize(10L);
        req.setCursor(41L);
        SmartworkBpmsProcessinstanceListResponse rsp = client.execute(req, access_token);
        JSONObject result = JSON.parseObject(rsp.getBody());
        SmartworkBpmsProcessinstanceListResponse.DingOpenResult result1 = rsp.getResult();
        SmartworkBpmsProcessinstanceListResponse.PageResult result2 = result1.getResult();



        System.out.println(" ================================  "+result);
        System.out.println(" ================================  result2.getNextCursor() = "+result2.getNextCursor());


        List<SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo> data = result2.getList();
        System.out.println(" ================================  data = "+data.size());

        for (SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo item : data){
            System.out.println(" ================================  ");
            System.out.println("标题 : "+item.getTitle());
            System.out.println("流程实例ID : "+item.getProcessInstanceId());

            List<SmartworkBpmsProcessinstanceListResponse.FormComponentValueVo> forms = item.getFormComponentValues();
            for (SmartworkBpmsProcessinstanceListResponse.FormComponentValueVo form : forms){
                System.out.println(form.getName()+" : "+form.getValue());
            }
        }


    }

}

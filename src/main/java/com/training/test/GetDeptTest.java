package com.training.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.training.util.HttpHelper;
import com.training.util.OApiException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.util.Map;

/**
 * Created by huai23 on 2018/5/14.
 */
public class GetDeptTest {

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
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            // 创建httpget.
            HttpGet httpget = new HttpGet("https://oapi.dingtalk.com/department/list?access_token="+access_token);
            // 执行get请求.
            response = httpclient.execute(httpget);
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            // 打印响应状态
            System.out.println(response.getStatusLine().getStatusCode());
            if (entity != null) {
                // 打印响应内容
                String result = EntityUtils.toString(entity);
                System.out.println("Response content: " + result);
                JSONObject obj = JSON.parseObject(result);
                String department = obj.getString("department");
                JSONArray departments = JSON.parseArray(department);
                for (Object dept :departments){
                    Map item = (Map)dept;
                    System.out.println("id: " + item.get("id").toString()+" , name: " + item.get("name").toString());
                }
            }
        } catch (Exception e) {
            throw e;
        }finally{
            httpclient.close();
            response.close();
        }

    }

}

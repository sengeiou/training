package com.training.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.training.util.HttpHelper;
import com.training.util.OApiException;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huai23 on 2018/5/14.
 */
public class TokenTest {

    public static String getSsoToken() throws OApiException {
        String app_id = "537881128711959";
        String app_secret = "MWJkZTkyYTIyNzI0YTBmNjBkNTk4NTExN2RkZmQ1OThkOTA3MDcxZQ";
        String url = "https://open.youjiuhealth.com/api/session";
        Map param = new HashMap();
        param.put("app_id",app_id);
        param.put("app_secret",app_secret);
        JSONObject response = HttpHelper.httpPost(url,param);
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
        System.out.println("access_token:"+access_token);
        String url = "https://open.youjiuhealth.com/api/reports?device_sn=1106131816684";
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().
                setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpGet.setConfig(requestConfig);
        httpGet.addHeader("accept", "application/vnd.XoneAPI.v2+json");
        httpGet.addHeader("Authorization","Bearer "+access_token);

        System.out.println(httpGet);

        try {
            response = httpClient.execute(httpGet, new BasicHttpContext());
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
                        + ", url=" + url);
                return;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");
                JSONObject result = JSON.parseObject(resultStr);
                System.out.println("result:"+result);
            }
        } catch (IOException e) {
            System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (response != null) try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

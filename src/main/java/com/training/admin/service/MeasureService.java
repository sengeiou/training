package com.training.admin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.training.dao.*;
import com.training.entity.MeasurementEntity;
import com.training.service.MemberService;
import com.training.service.SysLogService;
import com.training.util.HttpHelper;
import com.training.util.OApiException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * staff 核心业务操作类
 * Created by huai23 on 2018-05-26 13:55:30.
 */ 
@Service
public class MeasureService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    String app_id = "537881128711959";
    String app_secret = "MWJkZTkyYTIyNzI0YTBmNjBkNTk4NTExN2RkZmQ1OThkOTA3MDcxZQ";
    String token_url = "https://open.youjiuhealth.com/api/session";

    String device_sn = "1106131816684";
    String query_url = "https://open.youjiuhealth.com/api/reports?device_sn="+device_sn;
    String detail_url = "https://open.youjiuhealth.com/api/reports/";
    private String token;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MeasurementDao measurementDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getToken() {
        if(StringUtils.isNotEmpty(token)){
            return token;
        }else{
            Map param = new HashMap();
            param.put("app_id",app_id);
            param.put("app_secret",app_secret);
            JSONObject response = null;
            try {
                response = HttpHelper.httpPost(token_url,param);
            } catch (OApiException e) {
                e.printStackTrace();
            }
            if (response.containsKey("access_token")) {
                token = response.getString("access_token");
            } else {

            }
        }
        return token;
    }

    public void query(int page) {
        HttpGet httpGet = new HttpGet(query_url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().
                setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpGet.setConfig(requestConfig);
        httpGet.addHeader("accept", "application/vnd.XoneAPI.v2+json");
        httpGet.addHeader("Authorization","Bearer "+getToken());
        try {
            response = httpClient.execute(httpGet, new BasicHttpContext());
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
                        + ", url=" + query_url+"&page="+page);
                return;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");
                JSONObject result = JSON.parseObject(resultStr);
                System.out.println("meta:"+result.getString("meta"));
                JSONArray list = JSON.parseArray(result.getString("data"));
                for (int i = 0; i < list.size(); i++) {
                    JSONObject item = list.getJSONObject(i);
                    System.out.println("item:"+item);
                    JSONObject measurement = JSON.parseObject(item.getString("measurement"));
                    System.out.println("measurement:"+measurement);
                    String id = measurement.getString("id");
                    String device_sn = measurement.getString("device_sn");
                    String gender = measurement.getString("gender");
                    String age = measurement.getString("age");
                    String height = measurement.getString("height");
                    String weight = measurement.getString("weight");
                    String outline = measurement.getString("outline");

                    String phone = measurement.getString("phone");
                    System.out.println("id:"+id+" , phone="+phone);

                    MeasurementEntity measurementEntity = measurementDao.getById(id);
                    if(measurementEntity==null){
                        String sql = "insert into measurement (measurement_id,device_sn,gender,age,height,weight,phone,outline,measurement,created,modified) values (?,?,?,?,?,?,?,?,?,now(),now()) ";
                        jdbcTemplate.update(sql,new Object[]{id,device_sn,gender,age,height,weight,phone,outline,measurement.toJSONString()});
                    }
                    queryDetail(id);
                }
            }
        } catch (IOException e) {
            System.out.println("request url=" + query_url + ", exception, msg=" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (response != null) try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void queryDetail(String id) {
        HttpGet httpGet = new HttpGet(detail_url+id);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().
                setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpGet.setConfig(requestConfig);
        httpGet.addHeader("accept", "application/vnd.XoneAPI.v2+json");
        httpGet.addHeader("Authorization","Bearer "+getToken());
        try {
            response = httpClient.execute(httpGet, new BasicHttpContext());
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
                        + ", url=" + detail_url+id);
                return;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");
                System.out.println("resultStr:"+resultStr);

                JSONObject result = JSON.parseObject(resultStr);
                JSONObject detail = JSON.parseObject(result.getString("data"));

                String measurement = detail.getString("measurement");
                String composition = detail.getString("composition");
                String posture = detail.getString("posture");
                String girth = detail.getString("girth");
                System.out.println("measurement:"+measurement);
                System.out.println("composition:"+composition);
                System.out.println("posture:"+posture);
                System.out.println("girth:"+girth);
                if(composition==null){
                    composition="";
                }
                if(posture==null){
                    posture="";
                }
                if(girth==null){
                    girth="";
                }
                String sql = "update measurement set composition = ? , posture = ? , girth = ? where measurement_id = ? ";
                jdbcTemplate.update(sql,new Object[]{composition,posture,girth,id});
            }
        } catch (IOException e) {
            System.out.println("request url=" + detail_url+id + ", exception, msg=" + e.getMessage());
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


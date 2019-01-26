package com.training.admin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.training.common.PageRequest;
import com.training.dao.*;
import com.training.entity.*;
import com.training.util.HttpHelper;
import com.training.util.IDUtils;
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
import java.util.List;
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
    String query_url = "https://open.youjiuhealth.com/api/reports";
    String detail_url = "https://open.youjiuhealth.com/api/reports/";

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private MemberBodyDao memberBodyDao;

    @Autowired
    private MeasurementDao measurementDao;

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getToken() {
        String token = "";
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
        }
        return token;
    }

    public void queryAll() {
        String token = getToken();
        int page = 1;
        DeviceQuery query = new DeviceQuery();
        PageRequest pageRequest = new PageRequest(1000);
        List<DeviceEntity> deviceEntities = deviceDao.find(query,pageRequest);
        for (DeviceEntity deviceEntity:deviceEntities){
            String device_sn = deviceEntity.getDeviceSn();
            int totalPage = query(token,device_sn,page);
            if(totalPage>1){
                for (int i = 2; i <= totalPage; i++) {
                    query(token,device_sn,i);
                }
            }
        }
    }

    public int query(String token,String deviceSn,int page) {
        logger.info(" query , deviceSn = {} , page = {} ",deviceSn,page);
        int totalPage = 0;
        HttpGet httpGet = new HttpGet(query_url+"?device_sn="+deviceSn+"&page="+page);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().
                setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpGet.setConfig(requestConfig);
        httpGet.addHeader("accept", "application/vnd.XoneAPI.v2+json");
        httpGet.addHeader("Authorization","Bearer "+token);
        try {
            response = httpClient.execute(httpGet, new BasicHttpContext());
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
                        + ", url=" + query_url+"?device_sn="+deviceSn+"&page="+page);
                return totalPage;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");
                JSONObject result = JSON.parseObject(resultStr);
                System.out.println("meta:"+result.getString("meta"));
                JSONObject meta = JSON.parseObject(result.getString("meta"));
                System.out.println("pagination:"+meta.getString("pagination"));
                JSONObject pagination = meta.getJSONObject("pagination");
                totalPage = Integer.parseInt(pagination.getString("total_pages"));

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
                    String start_time = measurement.getString("start_time");
                    String measure_date = start_time.substring(0,7);


                    JSONObject outlineObj = JSON.parseObject(outline);
                    String bmi = outlineObj.getString("bmi");

                    String phone = measurement.getString("phone");
                    System.out.println("id:"+id+" , phone="+phone);
                    MemberEntity memberEntity = memberDao.getByPhone(phone);

                    MeasurementEntity measurementEntity = measurementDao.getById(id);
                    if(measurementEntity==null){
                        String bodyId = "";
                        String memberId = "";
                        if(memberEntity!=null){
                            memberId = memberEntity.getMemberId();
                            bodyId = IDUtils.getId();
                            String coachId = "";
                            StaffEntity staffEntity = staffDao.getById(memberEntity.getCoachStaffId());
                            if(staffEntity!=null&&StringUtils.isNotEmpty(staffEntity.getOpenId())){
                                MemberEntity staff = memberDao.getByOpenId(staffEntity.getOpenId());
                                if(staff!=null){
                                    coachId = staff.getMemberId();
                                }
                            }
                            MemberBodyEntity memberBody = new MemberBodyEntity();
                            memberBody.setBodyId(bodyId);
                            memberBody.setCoachId(coachId);
                            memberBody.setMemberId(memberEntity.getMemberId());
                            memberBody.setBmi(bmi);
                            memberBody.setHeight(height);
                            memberBody.setWeight(weight);
                            memberBody.setMeasurementId(id);
                            int n = memberBodyDao.add(memberBody);
                        }

                        String sql = "insert into measurement (measurement_id,body_id,member_id,device_sn,gender,age,height,weight,phone,outline,measurement,measure_date,start_time,created,modified) values (?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now()) ";
                        jdbcTemplate.update(sql,new Object[]{id,bodyId,memberId,device_sn,gender,age,height,weight,phone,outline,measurement.toJSONString(),measure_date,start_time});
                        queryDetail(token,id);
                    }else if(StringUtils.isEmpty(measurementEntity.getComposition())){
                        queryDetail(token,id);
                    }

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
        return totalPage;
    }


    public void queryDetail(String token,String id) {
        HttpGet httpGet = new HttpGet(detail_url+id);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().
                setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpGet.setConfig(requestConfig);
        httpGet.addHeader("accept", "application/vnd.XoneAPI.v2+json");
        httpGet.addHeader("Authorization","Bearer "+token);
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


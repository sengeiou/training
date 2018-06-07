package com.training.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.SmartworkBpmsProcessinstanceListRequest;
import com.dingtalk.api.response.SmartworkBpmsProcessinstanceListResponse;
import com.training.entity.ContractEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huai23 on 2018/5/14.
 */
public class DingtalkUtil {

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

    public static List<Map> getDepts() throws Exception {
        List<Map> deptList = new ArrayList<>();
        String access_token = getSsoToken();
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            // 创建httpget.
            HttpGet httpget = new HttpGet("https://oapi.dingtalk.com/department/list?access_token="+access_token);

            httpget.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            //设置期望服务端返回的编码
            httpget.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));

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
//                    System.out.println("id: " + item.get("id").toString()+" , name: " + item.get("name").toString());
                    deptList.add(item);
                }
            }
        } catch (Exception e) {
            throw e;
        }finally{
            httpclient.close();
            response.close();
        }
        return deptList;
    }

    public static List<Map> getStaffs(String deptId) throws Exception {
        List<Map> staffList = new ArrayList<>();
        String access_token = getSsoToken();
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            // 创建httpget.
            HttpGet httpget = new HttpGet("https://oapi.dingtalk.com/user/list?access_token="+access_token+"&department_id="+deptId);
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
                String userlist = obj.getString("userlist");
                JSONArray users = JSON.parseArray(userlist);
                for (Object dept :users){
                    Map item = (Map)dept;
//                    System.out.println("userid: " + item.get("userid").toString()+" , name: " + item.get("name").toString()
//                            +" , position: " + item.get("position").toString()+" , mobile: " + item.get("mobile").toString()
//                            +" , email: " + item.get("mobile").toString()+" , extattr: " + item.get("extattr").toString());
                    staffList.add(item);
                }
            }
        } catch (Exception e) {
            throw e;
        }finally{
            httpclient.close();
            response.close();
        }
        return staffList;
    }

    public static List<ContractEntity> getContracts(Long startTime , Long endTime , String processCode, Long cursor) throws Exception {
        String access_token = getSsoToken();
        DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
        SmartworkBpmsProcessinstanceListRequest req = new SmartworkBpmsProcessinstanceListRequest();
        req.setProcessCode(processCode);
        req.setStartTime(startTime);
//        req.setEndTime(endTime);
        req.setSize(10L);
        req.setCursor(cursor);
        SmartworkBpmsProcessinstanceListResponse rsp = client.execute(req, access_token);
        JSONObject result = JSON.parseObject(rsp.getBody());
        SmartworkBpmsProcessinstanceListResponse.DingOpenResult result1 = rsp.getResult();
        SmartworkBpmsProcessinstanceListResponse.PageResult result2 = result1.getResult();
        System.out.println(" ================================  "+result);
        List<SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo> data = result2.getList();
        List<ContractEntity> contractEntityList = new ArrayList<>();
        if(data!=null){
            System.out.println(" ================================  data = "+data.size());
            for (SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo item : data){
                System.out.println(" ================================  ");
                System.out.println(""+item.getTitle());
                ContractEntity contractEntity = converProcessinstanceTeam(item);
                contractEntityList.add(contractEntity);

            }
        }else{
            System.out.println(" ================================  data = null : "+data);

        }
        return contractEntityList;
    }

    private static ContractEntity converProcessinstance(SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo processInstanceTopVo) {
        Map<String,String> contractMap = new HashMap();
        List<SmartworkBpmsProcessinstanceListResponse.FormComponentValueVo> forms = processInstanceTopVo.getFormComponentValues();
        for (SmartworkBpmsProcessinstanceListResponse.FormComponentValueVo form : forms){
            System.out.println(form.getName()+" : "+form.getValue());
            contractMap.put(form.getName(),form.getValue()==null?"":form.getValue());
        }
        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setProcessInstanceId(processInstanceTopVo.getProcessInstanceId());
        contractEntity.setCardType("PT");
        contractEntity.setContractId(contractMap.get("合同编号"));
        contractEntity.setContractName(processInstanceTopVo.getTitle());
        contractEntity.setSignDate(contractMap.get("签约日期"));
        contractEntity.setMemberName(contractMap.get("会员姓名"));
        contractEntity.setGender(contractMap.get("会员性别"));
        contractEntity.setPhone(contractMap.get("会员电话"));
        contractEntity.setType(contractMap.get("合同属性"));
        contractEntity.setTotal(contractMap.get("购买课程"));
        contractEntity.setMoney(contractMap.get("金额（元）"));
        contractEntity.setPayType(contractMap.get("付款方式"));
        if(StringUtils.isNotEmpty(contractMap.get("[\"开始时间\",\"结束时间\"]"))){
            String dateStr = contractMap.get("[\"开始时间\",\"结束时间\"]").replace("[","").replace("]","");
            String[] dates = dateStr.split(",");
            contractEntity.setStartDate(dates[0].replaceAll("\"",""));
            contractEntity.setEndDate(dates[1].replaceAll("\"",""));
        }
        contractEntity.setSalesman(contractMap.get("销售人员"));
        contractEntity.setCoach(contractMap.get("分配教练"));
        contractEntity.setRemark(contractMap.get("备注"));
        contractEntity.setImage(contractMap.get("图片"));
        return contractEntity;
    }

    private static ContractEntity converProcessinstanceTeam(SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo processInstanceTopVo) {
        Map<String,String> contractMap = new HashMap();
        List<SmartworkBpmsProcessinstanceListResponse.FormComponentValueVo> forms = processInstanceTopVo.getFormComponentValues();
        for (SmartworkBpmsProcessinstanceListResponse.FormComponentValueVo form : forms){
            System.out.println(form.getName()+" : "+form.getValue());
            contractMap.put(form.getName(),form.getValue()==null?"":form.getValue());
        }
        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setProcessInstanceId(processInstanceTopVo.getProcessInstanceId());
        contractEntity.setCardType("TM");
        contractEntity.setContractId(contractMap.get("合同编号"));
        contractEntity.setContractName(processInstanceTopVo.getTitle());
        contractEntity.setSignDate(contractMap.get("签约日期"));
        contractEntity.setMemberName(contractMap.get("会员姓名"));
        contractEntity.setGender(contractMap.get("会员性别"));
        contractEntity.setPhone(contractMap.get("会员电话"));
        contractEntity.setType(contractMap.get("合同属性"));
        contractEntity.setTotal(contractMap.get("课程种类"));
        contractEntity.setMoney(contractMap.get("付款金额（元）"));
        contractEntity.setPayType(contractMap.get("付款方式"));
        contractEntity.setStartDate(contractMap.get("开卡日期"));
        contractEntity.setSalesman(contractMap.get("销售人员"));
        contractEntity.setCoach(contractMap.get("分配教练"));
        contractEntity.setRemark(contractMap.get("备注及特殊说明"));
        contractEntity.setImage(contractMap.get("图片"));
        return contractEntity;
    }

}

package com.training.admin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.SmartworkBpmsProcessinstanceListRequest;
import com.dingtalk.api.response.SmartworkBpmsProcessinstanceListResponse;
import com.training.common.CardTypeEnum;
import com.training.common.ProcessCodeEnum;
import com.training.common.ProcessInstanceResultEnum;
import com.training.common.ProcessStatusEnum;
import com.training.entity.ContractEntity;
import com.training.service.ContractService;
import com.training.util.DingtalkUtil;
import com.training.util.ut;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProcessInstanceService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContractService contractService;

    public void getConcract(ProcessCodeEnum processCodeEnum){
        logger.info("start getConcract   time = {} ", ut.currentTime());
        if(StringUtils.isEmpty(processCodeEnum.getCode())){
            logger.info("end getConcract processCodeEnum is  null = {}",processCodeEnum);
            return;
        }
        if(processCodeEnum.getKey().equals(CardTypeEnum.PT.getKey())){
            getPTConcract(processCodeEnum.getCode());
        }
        if(processCodeEnum.getKey().equals(CardTypeEnum.PM.getKey())){
            getPMConcract(processCodeEnum.getCode());
        }
        logger.info("end getConcract end!");
    }

    public void getPTConcract(String processCode){
        logger.info("start getPersonalConcract   time = {} ", ut.currentTime());
        List<ContractEntity> contractEntityList = null;
        Long cursor = 1L;
        try {
            String access_token = DingtalkUtil.getSsoToken();
            String startDate = ut.currentDate(-5);
            System.out.println(" ================================  startDate = "+startDate);
            Long startTime = ut.df_day.parse(startDate).getTime();
            DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
            SmartworkBpmsProcessinstanceListRequest req = new SmartworkBpmsProcessinstanceListRequest();
            req.setProcessCode(processCode);
            req.setStartTime(startTime);
//        req.setEndTime(endTime);
            req.setSize(10L);
            int count = 0;
            do{
                System.out.println(" ================================  cursor = "+cursor);

                req.setCursor(cursor);
                SmartworkBpmsProcessinstanceListResponse rsp = client.execute(req, access_token);
                JSONObject result = JSON.parseObject(rsp.getBody());
                SmartworkBpmsProcessinstanceListResponse.DingOpenResult result1 = rsp.getResult();
                SmartworkBpmsProcessinstanceListResponse.PageResult result2 = result1.getResult();
                System.out.println(" ================================  result = "+result);
                List<SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo> data = result2.getList();
                count = 0;
                if(data!=null){
                    contractEntityList = new ArrayList<>();
                    count = data.size();
                    System.out.println(" ================================  data = "+data.size());
                    for (SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo item : data){
                        System.out.println(" ================================  ");
                        System.out.println(""+item.getTitle());
                        if(item.getStatus().equals(ProcessStatusEnum.COMPLETED.getKey())){
                            if(item.getProcessInstanceResult().equals(ProcessInstanceResultEnum.agree.getKey())){
                                ContractEntity contractEntity = converPT(item);
                                contractEntityList.add(contractEntity);
                            }else{
                                logger.info(" refuse  process : {}  ",item);
                            }
                        }
                    }
                }else{
                    System.out.println(" ================================  data = null : "+data);
                }
                for (int i = 0; i < contractEntityList.size(); i++) {
                    ContractEntity contractEntity = contractEntityList.get(i);
                    ContractEntity contractEntityDB = contractService.getById(contractEntity.getProcessInstanceId());
                    if(contractEntityDB!=null){
                        logger.info(contractEntityDB.getContractName().toString()+"已存在，无需重复添加");
                        continue;
                    }
                    contractService.add(contractEntity);
                }
                cursor++;
            }while(count>0);

        }catch (Exception e){
            logger.error(" getPersonalConcract  ERROR = {} ",e.getMessage(),e);
        }
        logger.info("end getPersonalConcract end!");
    }


    public void getPMConcract(String processCode){
        logger.info("start getPMConcract   time = {} ", ut.currentTime());
        List<ContractEntity> contractEntityList = null;
        Long cursor = 1L;
        try {
            String access_token = DingtalkUtil.getSsoToken();
            String startDate = ut.currentDate(-5);
            System.out.println(" ================================  startDate = "+startDate);
            Long startTime = ut.df_day.parse(startDate).getTime();
            DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
            SmartworkBpmsProcessinstanceListRequest req = new SmartworkBpmsProcessinstanceListRequest();
            req.setProcessCode(processCode);
            req.setStartTime(startTime);
//        req.setEndTime(endTime);
            req.setSize(10L);
            int count = 0;
            do{
                req.setCursor(cursor);
                SmartworkBpmsProcessinstanceListResponse rsp = client.execute(req, access_token);
                JSONObject result = JSON.parseObject(rsp.getBody());
                SmartworkBpmsProcessinstanceListResponse.DingOpenResult result1 = rsp.getResult();
                SmartworkBpmsProcessinstanceListResponse.PageResult result2 = result1.getResult();
                System.out.println(" ================================  "+result);
                List<SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo> data = result2.getList();
                count = 0;
                if(data!=null){
                    contractEntityList = new ArrayList<>();
                    count = data.size();
                    System.out.println(" ================================  data = "+data.size());
                    for (SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo item : data){
                        System.out.println(" ================================  ");
                        System.out.println(""+item.getTitle());
                        if(item.getStatus().equals(ProcessStatusEnum.COMPLETED.getKey())){
                            if(item.getProcessInstanceResult().equals(ProcessInstanceResultEnum.agree.getKey())){
                                ContractEntity contractEntity = converPM(item);
                                contractEntityList.add(contractEntity);
                            }else{
                                logger.info(" refuse  process : {}  ",item);
                            }
                        }
                    }
                }else{
                    System.out.println(" ================================  data = null : "+data);
                    count = 0;
                    contractEntityList = new ArrayList<>();
                }
                for (int i = 0; i < contractEntityList.size(); i++) {
                    ContractEntity contractEntity = contractEntityList.get(i);
                    ContractEntity contractEntityDB = contractService.getById(contractEntity.getProcessInstanceId());
                    if(contractEntityDB!=null){
                        logger.info(contractEntityDB.getContractName().toString()+"已存在，无需重复添加");
                        continue;
                    }
                    try {
                        contractService.add(contractEntity);
                    }catch (Exception e){
                        logger.error(" contractService.add  ERROR = {} ",e.getMessage(),e);
                    }
                }
                cursor++;
            }while(count>0);

        }catch (Exception e){
            logger.error(" getPMConcract  ERROR = {} ",e.getMessage(),e);
        }
        logger.info("end getPMConcract end!");
    }

    private static ContractEntity converPT(SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo processInstanceTopVo) {
        Map<String,String> contractMap = new HashMap();
        List<SmartworkBpmsProcessinstanceListResponse.FormComponentValueVo> forms = processInstanceTopVo.getFormComponentValues();
        for (SmartworkBpmsProcessinstanceListResponse.FormComponentValueVo form : forms){
//            System.out.println(form.getName()+" : "+form.getValue());
            contractMap.put(form.getName(),form.getValue()==null?"":form.getValue());
        }
        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setProcessInstanceId(processInstanceTopVo.getProcessInstanceId());
        contractEntity.setCardType(CardTypeEnum.PT.getKey());
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
        Map<String,String> feature = new HashMap();
        feature.put("storeName",contractMap.get("所在部门"));
        contractEntity.setFeature(JSON.toJSONString(feature));
        contractEntity.setSalesman(contractMap.get("销售人员"));
        contractEntity.setCoach(contractMap.get("分配教练"));
        contractEntity.setRemark(contractMap.get("备注"));
        contractEntity.setImage(contractMap.get("图片"));
        contractEntity.setForm(JSON.toJSONString(processInstanceTopVo));
        return contractEntity;
    }

    private static ContractEntity converPM(SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo processInstanceTopVo){
        Map<String,String> contractMap = new HashMap();
        List<SmartworkBpmsProcessinstanceListResponse.FormComponentValueVo> forms = processInstanceTopVo.getFormComponentValues();
        for (SmartworkBpmsProcessinstanceListResponse.FormComponentValueVo form : forms){
//            System.out.println(form.getName()+" : "+form.getValue());
            contractMap.put(form.getName(),form.getValue()==null?"":form.getValue());
        }
        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setProcessInstanceId(processInstanceTopVo.getProcessInstanceId());
        contractEntity.setCardType(CardTypeEnum.PM.getKey());
        contractEntity.setContractId(contractMap.get("合同编号"));
        contractEntity.setContractName(processInstanceTopVo.getTitle());
        contractEntity.setSignDate(contractMap.get("签约日期"));
        contractEntity.setMemberName(contractMap.get("会员姓名"));
        contractEntity.setGender(contractMap.get("会员性别"));
        contractEntity.setPhone(contractMap.get("会员电话"));
        contractEntity.setType(contractMap.get("合同属性"));
        contractEntity.setMoney(contractMap.get("付款金额（元）"));
        contractEntity.setPayType(contractMap.get("付款方式"));
        if(StringUtils.isNotEmpty(contractMap.get("[\"开始时间\",\"结束时间\"]"))){
            String dateStr = contractMap.get("[\"开始时间\",\"结束时间\"]").replace("[","").replace("]","");
            String[] dates = dateStr.split(",");
            contractEntity.setStartDate(dates[0].replaceAll("\"",""));
            contractEntity.setEndDate(dates[1].replaceAll("\"",""));
            contractEntity.setTotal(dates[2]);
        }
        Map<String,String> feature = new HashMap();
        feature.put("storeName",contractMap.get("所在部门"));
        contractEntity.setFeature(JSON.toJSONString(feature));
        contractEntity.setSalesman(contractMap.get("销售人员"));
        contractEntity.setCoach(contractMap.get("分配教练"));
        contractEntity.setRemark(contractMap.get("备注及特殊说明"));
        contractEntity.setImage(contractMap.get("图片"));
        contractEntity.setForm(JSON.toJSONString(processInstanceTopVo));
        return contractEntity;
    }

}

package com.training.admin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.SmartworkBpmsProcessinstanceListRequest;
import com.dingtalk.api.response.SmartworkBpmsProcessinstanceListResponse;
import com.training.common.*;
import com.training.dao.*;
import com.training.domain.MemberCard;
import com.training.entity.*;
import com.training.service.ContractService;
import com.training.service.MemberCardService;
import com.training.util.DingtalkUtil;
import com.training.util.IDUtils;
import com.training.util.ut;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CreateCardService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private MemberCardService memberCardService;

    @Autowired
    private MemberCardDao memberCardDao;

    @Autowired
    private ContractManualDao contractManualDao;

    public void createCard() {
        ContractQuery query = new ContractQuery();
//        query.setProcessInstanceId("7f31e570-e65d-4f17-aa65-0d53187eafa2");
        query.setStatus(0);
//        query.setContractId("abcdefg");
        PageRequest page = new PageRequest();
        page.setPageSize(1000);
        List<ContractEntity> contractEntityList = contractDao.find(query,page);
        logger.info(" *************  createCard   contractEntityList.size() = {} ", contractEntityList.size());
        for(ContractEntity contractEntity:contractEntityList){
            logger.info("createCard   contractEntity = {} ", contractEntity);
            try {

                if("TK".equals(contractEntity.getCardType())){
                    dealTK(contractEntity);
                    continue;
                }

                if("ZK".equals(contractEntity.getCardType())){
                    dealZK(contractEntity);
                    continue;
                }
                String phone = contractEntity.getPhone();
                if(StringUtils.isEmpty(phone)){
                    ContractEntity contractUpdate = new ContractEntity();
                    contractUpdate.setProcessInstanceId(contractEntity.getProcessInstanceId());
                    contractUpdate.setStatus(-1);
                    contractDao.update(contractUpdate);
                }
                MemberEntity memberDB = memberDao.getByPhone(phone);
                if(memberDB==null){
                    logger.info("createCard  MemberEntity 不存在 ，  phone = {} ", phone);
                    memberDB = new MemberEntity();
                    memberDB.setMemberId(IDUtils.getId());
                    memberDB.setName(contractEntity.getMemberName());
                    memberDB.setPhone(phone);
                    memberDB.setType("M");
                    memberDB.setOrigin("合同生成");
                    memberDB.setRemark("合同编号:"+contractEntity.getContractId());
                    int n = memberDao.add(memberDB);
                    if(n!=1){
                        logger.error("createCard  MemberEntity 创建新会员失败 ，  memberDB = {} ", memberDB);
                        continue;
                    }
                    memberDB = memberDao.getById(memberDB.getMemberId());
                    logger.info("createCard  MemberEntity 创建新会员成功 ，  memberDB = {} ", memberDB);
                }else{
                    // 根据审批信息中的学员姓名更新系统中会员姓名
                    MemberEntity memberUpdate = new MemberEntity();
                    memberUpdate.setMemberId(memberDB.getMemberId());
                    memberUpdate.setName(contractEntity.getMemberName());
                    int n = memberDao.update(memberUpdate);
                    if(n==1){
                        memberDB.setName(contractEntity.getMemberName());
                    }
                }
                if(!memberDB.getType().equals("M")){
                    logger.info("createCard  MemberEntity 不是会员 ，  memberDB = {} ", memberDB);
                    continue;
                }
                if(contractEntity.getCardType().equals(CardTypeEnum.PT.getKey())){
                    createPT(contractEntity,memberDB);
                }
                if(contractEntity.getCardType().equals(CardTypeEnum.PM.getKey())){
                    createPM(contractEntity,memberDB);
                }
                if(contractEntity.getCardType().equals(CardTypeEnum.TT.getKey())){
                    createTT(contractEntity,memberDB);
                }
                if(contractEntity.getCardType().equals(CardTypeEnum.TM.getKey())){
                    createTM(contractEntity,memberDB);
                }

                if(contractEntity.getCardType().equals(CardTypeEnum.ST1.getKey())||contractEntity.getCardType().equals(CardTypeEnum.ST2.getKey())||contractEntity.getCardType().equals(CardTypeEnum.ST3.getKey())){
                    createST(contractEntity,memberDB);
                }

            }catch (Exception e){
                logger.error(" createCard ERROR :  {} ", e.getMessage());
            }

        }
    }

    private void createST(ContractEntity contractEntity, MemberEntity memberDB)  throws Exception {
        logger.info(" ==== createST  contractEntity = {} ", contractEntity);
        MemberEntity coach = checkContract(contractEntity);
        MemberCardEntity memberCardEntity = new MemberCardEntity();
        memberCardEntity.setCardNo(IDUtils.getId());
        memberCardEntity.setCardId(contractEntity.getCardType());
        memberCardEntity.setCoachId(coach.getMemberId());
        memberCardEntity.setStoreId(coach.getStoreId());
        memberCardEntity.setMemberId(memberDB.getMemberId());
        memberCardEntity.setType(contractEntity.getCardType());
        int total = Integer.parseInt(contractEntity.getTotal());
        memberCardEntity.setCount(total);
        memberCardEntity.setTotal(total);
        memberCardEntity.setDays(ut.passDayByDate(contractEntity.getStartDate(),contractEntity.getEndDate()));
        memberCardEntity.setMoney(contractEntity.getMoney());
        memberCardEntity.setStartDate(contractEntity.getStartDate());
        memberCardEntity.setEndDate(contractEntity.getEndDate());
        memberCardEntity.setContractId(contractEntity.getContractId());
        memberCardService.add(memberCardEntity);
        if(StringUtils.isEmpty(memberDB.getCoachStaffId())){
            StaffEntity staffEntity = staffDao.getByPhone(coach.getPhone());
            MemberEntity memberUpdate = new MemberEntity();
            memberUpdate.setMemberId(memberDB.getMemberId());
            memberUpdate.setCoachStaffId(staffEntity.getStaffId());
            int n = memberDao.update(memberUpdate);
        }
        contractEntity.setStatus(1);
        updateContractStatus(contractEntity);
        logger.info(" createPT  success : memberCardEntity = {}  ", memberCardEntity);
    }

    private void dealTK(ContractEntity contractEntity) {
        logger.info(" dealTK contractEntity :  {} ", contractEntity);
        String phone = contractEntity.getPhone();
        SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo processInstanceTopVo = JSON.parseObject(contractEntity.getForm(),SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo.class);
        Map<String,String> contractMap = new HashMap();
        List<SmartworkBpmsProcessinstanceListResponse.FormComponentValueVo> forms = processInstanceTopVo.getFormComponentValues();
        for (SmartworkBpmsProcessinstanceListResponse.FormComponentValueVo form : forms){
            System.out.println(form.getName()+" : "+form.getValue());
            contractMap.put(form.getName(),form.getValue()==null?"":form.getValue());
        }
        String cardNo = contractMap.get("课卡号");
        if(StringUtils.isEmpty(cardNo)){
            logger.error(" dealZK cardNo is null : contractEntity = {} ", contractEntity);
            ContractEntity contractUpdate = new ContractEntity();
            contractUpdate.setProcessInstanceId(contractEntity.getProcessInstanceId());
            contractUpdate.setImage(" dealZK cardNo is null ");
            contractDao.update(contractUpdate);
            return;
        }

        MemberEntity member = memberDao.getByPhone(contractEntity.getPhone());
        if(member==null){
            logger.error(" dealZK member is null : contractMap = {} ", contractMap);
            ContractEntity contractUpdate = new ContractEntity();
            contractUpdate.setProcessInstanceId(contractEntity.getProcessInstanceId());
            contractUpdate.setImage(" dealZK member is null ");
            contractDao.update(contractUpdate);
            return;
        }

        String remark = "退课，日期："+ut.currentTime()+"(合同号："+contractEntity.getContractId()+")";
        MemberCardEntity memberCardEntity = memberCardDao.getById(cardNo);
        if(memberCardEntity==null){
            logger.error(" dealTK memberCardEntity is null : contractMap = {} ", contractMap);
            ContractEntity contractUpdate = new ContractEntity();
            contractUpdate.setProcessInstanceId(contractEntity.getProcessInstanceId());
            contractUpdate.setImage(" dealTK memberCardEntity is null ");
            contractDao.update(contractUpdate);
            return;
        }

        if(memberCardEntity.getMemberId().equals(member.getMemberId())){
            MemberCardEntity memberCardUpdate = new MemberCardEntity();
            memberCardUpdate.setCardNo(cardNo);
            memberCardUpdate.setCount(0);
            memberCardUpdate.setRemark(remark);
            memberCardUpdate.setStatus(-1);
            int n = memberCardDao.update(memberCardUpdate);
            if(n==1){
                logger.info(" ==== dealTK  success : contractEntity = {} ", contractEntity);
                contractEntity.setStatus(1);
                updateContractStatus(contractEntity);
            }else {
                logger.error(" ****  dealTK failed : contractEntity = {} ", contractEntity);
                ContractEntity contractUpdate = new ContractEntity();
                contractUpdate.setProcessInstanceId(contractEntity.getProcessInstanceId());
                contractUpdate.setImage(" dealTK failed");
                contractDao.update(contractUpdate);
            }
        }else{
            logger.error(" dealTK 课卡号与转出会员不一致 : cardNo = {} ， member = {} ", cardNo,member);
            ContractEntity contractUpdate = new ContractEntity();
            contractUpdate.setProcessInstanceId(contractEntity.getProcessInstanceId());
            contractUpdate.setImage(" dealTK 课卡号与会员不一致 : cardNo = "+cardNo+" ， member =  "+member);
            contractDao.update(contractUpdate);
        }
    }

    private void dealZK(ContractEntity contractEntity) {
        logger.info(" dealZK contractEntity :  {} ", contractEntity);
        String phone = contractEntity.getPhone();
        SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo processInstanceTopVo = JSON.parseObject(contractEntity.getForm(),SmartworkBpmsProcessinstanceListResponse.ProcessInstanceTopVo.class);
        Map<String,String> contractMap = new HashMap();
        List<SmartworkBpmsProcessinstanceListResponse.FormComponentValueVo> forms = processInstanceTopVo.getFormComponentValues();
        for (SmartworkBpmsProcessinstanceListResponse.FormComponentValueVo form : forms){
            System.out.println(form.getName()+" : "+form.getValue());
            contractMap.put(form.getName(),form.getValue()==null?"":form.getValue());
        }
        String newPhone = contractMap.get("转入会员电话");
        if(StringUtils.isEmpty(newPhone)){
            logger.error(" dealZK newPhone is null : contractMap = {} ", contractMap);
            return;
        }
        MemberEntity memberFrom = memberDao.getByPhone(phone);
        MemberEntity memberTo = memberDao.getByPhone(newPhone);
        if(memberFrom==null){
            logger.error(" dealZK memberFrom is null : phone = {} ", phone);
            return;
        }
        if(memberTo==null){
            logger.error(" dealZK memberTo is null : newPhone = {} ", newPhone);
            return;
        }
        String cardNo = contractMap.get("课卡号");
        if(StringUtils.isEmpty(cardNo)){
            logger.error(" dealZK cardNo is null : contractEntity = {} ", contractEntity);
            return;
        }
        String remark = "转卡，转出会员："+memberFrom.getName()+"("+memberFrom.getPhone()+")";
        MemberCardEntity memberCardEntity = memberCardDao.getById(cardNo);
        if(memberCardEntity.getMemberId().equals(memberFrom.getMemberId())){
            MemberCardEntity memberCardUpdate = new MemberCardEntity();
            memberCardUpdate.setCardNo(cardNo);
            memberCardUpdate.setMemberId(memberTo.getMemberId());
            memberCardUpdate.setRemark(remark);
            int n = memberCardDao.update(memberCardUpdate);
            if(n==1){
                logger.info(" ==== dealZK  success : contractEntity = {} ", contractEntity);
                contractEntity.setStatus(1);
                updateContractStatus(contractEntity);
            }else {
                logger.error(" ****  dealZK failed : contractEntity = {} ", contractEntity);
            }
        }else{
            logger.error(" dealZK 课卡号与转出会员不一致 : cardNo = {} ， memberFrom = {} ", cardNo,memberFrom);
        }
    }

    @Transactional
    void createPT(ContractEntity contractEntity, MemberEntity memberDB) throws Exception {
        logger.info(" ==== createPT  contractEntity = {} ", contractEntity);
        MemberEntity coach = checkContract(contractEntity);
        MemberCardEntity memberCardEntity = new MemberCardEntity();
        memberCardEntity.setCardNo(IDUtils.getId());
        memberCardEntity.setCardId(CardTypeEnum.PT.getKey());
        memberCardEntity.setCoachId(coach.getMemberId());
        memberCardEntity.setStoreId(coach.getStoreId());
        memberCardEntity.setMemberId(memberDB.getMemberId());
        memberCardEntity.setType(CardTypeEnum.PT.getKey());
        int total = Integer.parseInt(contractEntity.getTotal());
        memberCardEntity.setCount(total);
        memberCardEntity.setTotal(total);
        memberCardEntity.setDays(ut.passDayByDate(contractEntity.getStartDate(),contractEntity.getEndDate()));
        memberCardEntity.setMoney(contractEntity.getMoney());
        memberCardEntity.setStartDate(contractEntity.getStartDate());
        memberCardEntity.setEndDate(contractEntity.getEndDate());
        memberCardEntity.setContractId(contractEntity.getContractId());
        memberCardService.add(memberCardEntity);

        contractEntity.setStatus(1);
        updateContractStatus(contractEntity);

        //todo  check
        if(StringUtils.isEmpty(memberDB.getCoachStaffId())){
            StaffEntity staffEntity = staffDao.getByPhone(coach.getPhone());
            MemberEntity memberUpdate = new MemberEntity();
            memberUpdate.setMemberId(memberDB.getMemberId());
            memberUpdate.setCoachStaffId(staffEntity.getStaffId());
            int n = memberDao.update(memberUpdate);
        }

        logger.info(" createPT  success : memberCardEntity = {}  ", memberCardEntity);
    }

    private MemberEntity checkContract(ContractEntity contractEntity) throws Exception {
        String staffName = contractEntity.getCoach();
        staffName = staffName.replaceAll("（","(").replaceAll("）",")");
        if(staffName.indexOf("(")>0){
            staffName = staffName.substring(0,staffName.indexOf("("));
        }
        StaffQuery query = new StaffQuery();
        query.setCustname(staffName);
        PageRequest page = new PageRequest();
        page.setPageSize(1000);
        List<StaffEntity> staffList = staffDao.find(query,new PageRequest());
        StaffEntity staffEntity = null;
        for (StaffEntity staff : staffList){
            if(staff.getCustname().trim().equals(staffName)){
                staffEntity = staff;
                break;
            }
        }
        if(staffEntity==null){
            logger.error(" checkContract  ERROR :  staffEntity = null , staffName = {}  ", staffName);
            throw new Exception(" "+staffName+" staffEntity = null ");
        }
//        if(staffList.size()!=1){
//            logger.error(" checkContract  ERROR :  staffList.size = {} , staffName = {}  ", staffList.size(), staffName);
//            throw new Exception(" "+staffName+" staffList.size = "+ staffList.size());
//        }
//        StaffEntity staffEntity = staffList.get(0);
        if(StringUtils.isEmpty(staffEntity.getOpenId())){
            logger.error(" checkContract  ERROR :  openId is null , staffEntity = {}  ", staffEntity);
            throw new Exception("openId is null");
        }

        ContractManualEntity contractManualEntity = contractManualDao.getById(contractEntity.getContractId());
        if(contractManualEntity!=null){
            logger.error(" checkContract  ERROR :  contractManualEntity is exist , contractId = {}  ", contractEntity.getContractId());
            throw new Exception("contractManualEntity is exist");
        }

        MemberEntity coach = memberDao.getByOpenId(staffEntity.getOpenId());
        if(coach==null){
            logger.error(" checkContract  ERROR :  coach is null , openId = {}  ", staffEntity.getOpenId());
            throw new Exception("coach is null");
        }
        return coach;
    }

    @Transactional
    void createPM(ContractEntity contractEntity, MemberEntity memberDB)  throws Exception {
        MemberEntity coach = checkContract(contractEntity);
        MemberCardEntity memberCardEntity = new MemberCardEntity();
        memberCardEntity.setCardNo(IDUtils.getId());
        memberCardEntity.setCardId(CardTypeEnum.PM.getKey());
        memberCardEntity.setCoachId(coach.getMemberId());
        memberCardEntity.setStoreId(coach.getStoreId());
        memberCardEntity.setMemberId(memberDB.getMemberId());
        memberCardEntity.setType(CardTypeEnum.PM.getKey());
        int total = Integer.parseInt(contractEntity.getTotal());
        memberCardEntity.setCount(999);
        memberCardEntity.setTotal(999);
        memberCardEntity.setDays(total);
        memberCardEntity.setMoney(contractEntity.getMoney());
        memberCardEntity.setStartDate(contractEntity.getStartDate());
        memberCardEntity.setEndDate(contractEntity.getEndDate());
        memberCardEntity.setContractId(contractEntity.getContractId());
        memberCardService.add(memberCardEntity);

        contractEntity.setStatus(1);
        updateContractStatus(contractEntity);

        if(StringUtils.isEmpty(memberDB.getCoachStaffId())){
            StaffEntity staffEntity = staffDao.getByPhone(coach.getPhone());
            MemberEntity memberUpdate = new MemberEntity();
            memberUpdate.setMemberId(memberDB.getMemberId());
            memberUpdate.setCoachStaffId(staffEntity.getStaffId());
            int n = memberDao.update(memberUpdate);
        }

        logger.info(" createPM  success : memberCardEntity = {}  ", memberCardEntity);
    }

    @Transactional
    void createTT(ContractEntity contractEntity, MemberEntity memberDB) throws Exception{
        logger.info(" ==== createTT  contractEntity = {} ", contractEntity);
//        MemberEntity coach = checkContract(contractEntity);
        MemberCardEntity memberCardEntity = new MemberCardEntity();
        memberCardEntity.setCardNo(IDUtils.getId());
        memberCardEntity.setCardId(CardTypeEnum.TT.getKey());
//        memberCardEntity.setCoachId(coach.getMemberId());
//        memberCardEntity.setStoreId(coach.getStoreId());
        memberCardEntity.setMemberId(memberDB.getMemberId());
        memberCardEntity.setType(CardTypeEnum.TT.getKey());
        int total = Integer.parseInt(contractEntity.getTotal());
        memberCardEntity.setCount(total);
        memberCardEntity.setTotal(total);
        memberCardEntity.setDays(ut.passDayByDate(contractEntity.getStartDate(),contractEntity.getEndDate()));
        memberCardEntity.setMoney(contractEntity.getMoney());
        memberCardEntity.setStartDate(contractEntity.getStartDate());
        memberCardEntity.setEndDate(contractEntity.getEndDate());
        memberCardEntity.setContractId(contractEntity.getContractId());
        memberCardService.add(memberCardEntity);
//        if(StringUtils.isEmpty(memberDB.getCoachStaffId())){
//            StaffEntity staffEntity = staffDao.getByPhone(coach.getPhone());
//            MemberEntity memberUpdate = new MemberEntity();
//            memberUpdate.setMemberId(memberDB.getMemberId());
//            memberUpdate.setCoachStaffId(staffEntity.getStaffId());
//            int n = memberDao.update(memberUpdate);
//        }
        contractEntity.setStatus(1);
        updateContractStatus(contractEntity);
        logger.info(" createTT  success : memberCardEntity = {}  ", memberCardEntity);
    }

    @Transactional
    void createTM(ContractEntity contractEntity, MemberEntity memberDB) {


    }

    private void updateContractStatus(ContractEntity contractEntity) {
        ContractEntity contractUpdate = new ContractEntity();
        contractUpdate.setProcessInstanceId(contractEntity.getProcessInstanceId());
        contractUpdate.setStatus(contractEntity.getStatus());
        contractDao.update(contractUpdate);
    }

}

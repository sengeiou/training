package com.training.admin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.SmartworkBpmsProcessinstanceListRequest;
import com.dingtalk.api.response.SmartworkBpmsProcessinstanceListResponse;
import com.training.common.*;
import com.training.dao.ContractDao;
import com.training.dao.MemberDao;
import com.training.dao.StaffDao;
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

    public void createCard() {
        ContractQuery query = new ContractQuery();
        query.setStatus(0);
        query.setContractId("abcdefg");
        PageRequest page = new PageRequest();
        page.setPageSize(1000);
        List<ContractEntity> contractEntityList = contractDao.find(query,page);
        for(ContractEntity contractEntity:contractEntityList){
            logger.info("createCard   contractEntity = {} ", contractEntity);
            try {
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
            }catch (Exception e){
                logger.error(" createCard ERROR :  {} ", e.getMessage());
            }

        }
    }

    @Transactional
    void createPT(ContractEntity contractEntity, MemberEntity memberDB) throws Exception {
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
        if(staffList.size()!=1){
            logger.error(" checkContract  ERROR :  staffList.size = {} , staffName = {}  ", staffList.size(), staffName);
            throw new Exception(" "+staffName+" staffList.size = "+ staffList.size());
        }
        StaffEntity staffEntity = staffList.get(0);
        if(StringUtils.isEmpty(staffEntity.getOpenId())){
            logger.error(" checkContract  ERROR :  openId is null , staffEntity = {}  ", staffEntity);
            throw new Exception("openId is null");
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

        logger.info(" createPM  success : memberCardEntity = {}  ", memberCardEntity);
    }

    @Transactional
    void createTT(ContractEntity contractEntity, MemberEntity memberDB) {


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

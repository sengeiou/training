package com.training.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.training.dao.*;
import com.training.domain.WechatPayLog;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import com.training.util.ut;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.training.util.ResponseUtil;
import com.training.util.RequestContextHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * sys_log 核心业务操作类
 * Created by huai23 on 2018-06-03 15:57:51.
 */ 
@Service
public class SysLogService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysLogDao sysLogDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private MemberCardDao memberCardDao;

    /**
     * 新增实体
     * @param sysLog
     * Created by huai23 on 2018-06-03 15:57:51.
     */ 
    public ResponseEntity<String> add(SysLogEntity sysLog){
        int n = sysLogDao.add(sysLog);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-03 15:57:51.
     */ 
    public Page<SysLogEntity> find(SysLogQuery query , PageRequest page){
        List<SysLogEntity> sysLogList = sysLogDao.find(query,page);
        Long count = sysLogDao.count(query);
        Page<SysLogEntity> returnPage = new Page<>();
        returnPage.setContent(sysLogList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-03 15:57:51.
     */ 
    public Long count(SysLogQuery query){
        Long count = sysLogDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-03 15:57:51.
     */ 
    public SysLogEntity getById(String id){
        SysLogEntity sysLogDB = sysLogDao.getById(id);
        return sysLogDB;
    }

    /**
     * 根据实体更新
     * @param sysLog
     * Created by huai23 on 2018-06-03 15:57:51.
     */ 
    public  ResponseEntity<String> update(SysLogEntity sysLog){
        int n = sysLogDao.update(sysLog);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-03 15:57:51.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = sysLogDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


    public Page<SysLogEntity> findDelayLog(SysLogQuery query, PageRequest page) {
        query.setType("YQ");
        query.setId2("pay");
        query.setRemark("success");
        List<SysLogEntity> sysLogList = sysLogDao.findDelayLog(query,page);
        for (SysLogEntity sysLogEntity:sysLogList){
            sysLogEntity.setCardNo(sysLogEntity.getId1());
            JSONObject data = JSON.parseObject(sysLogEntity.getLogText());
            String id = data.getString("out_trade_no");
            String total_fee = data.getString("total_fee");
            sysLogEntity.setMoney(ut.getDoubleString(Double.parseDouble(total_fee)/100));
            sysLogEntity.setDate(ut.df_day.format(sysLogEntity.getCreated()));
            logger.info(" out_trade_no = {} ",data.getString("out_trade_no"));
            SysLogEntity subLog = sysLogDao.getById(id);
            if(subLog!=null){
                JSONObject subData = JSON.parseObject(subLog.getLogText());
                String memberId = subData.getString("memberId");
                String type = subData.getString("type");
                sysLogEntity.setCardType(CardTypeEnum.getEnumByKey(type).getDesc());
                MemberEntity memberEntity = memberDao.getById(memberId);
                StoreEntity storeEntity = storeDao.getById(memberEntity.getStoreId());
                sysLogEntity.setMemberId(memberId);
                sysLogEntity.setName(memberEntity.getName());
                sysLogEntity.setStoreId(storeEntity.getStoreId());
                sysLogEntity.setStoreName(storeEntity.getName());
            }
            sysLogEntity.setContent(null);
            sysLogEntity.setLogText(null);
        }
        Long count = sysLogDao.countDelayLog(query);
        Page<SysLogEntity> returnPage = new Page<>();
        returnPage.setContent(sysLogList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    public Page<SysLogEntity> findChangeCoachLog(SysLogQuery query, PageRequest page) {
        query.setType("CC");
        List<SysLogEntity> sysLogList = sysLogDao.find(query,page);
        for (SysLogEntity sysLogEntity:sysLogList){
            String memberId = sysLogEntity.getId1();
            String staffId = sysLogEntity.getId2();
            sysLogEntity.setDate(ut.df_day.format(sysLogEntity.getCreated()));

            String[] ids = sysLogEntity.getLogText().split(",");
            String staffId2 = ids[1];

            MemberEntity memberEntity = memberDao.getById(memberId);
            sysLogEntity.setMemberId(memberId);
            sysLogEntity.setName(memberEntity.getName());
            sysLogEntity.setPhone(memberEntity.getPhone());

            StaffEntity staffEntity1 = staffDao.getById(staffId);
            if(staffEntity1!=null){
                sysLogEntity.setStaffName(staffEntity1.getCustname());
                StoreEntity storeEntity1 = storeDao.getById(staffEntity1.getStoreId());
                sysLogEntity.setStoreId(storeEntity1.getStoreId());
                sysLogEntity.setStoreName(storeEntity1.getName());
            }
            StaffEntity staffEntity2 = staffDao.getById(staffId2);
            if(staffEntity2!=null){
                sysLogEntity.setStaffName2(staffEntity2.getCustname());
                StoreEntity storeEntity2 = storeDao.getById(staffEntity2.getStoreId());
                sysLogEntity.setStoreId2(storeEntity2.getStoreId());
                sysLogEntity.setStoreName2(storeEntity2.getName());
            }

            if(StringUtils.isNotEmpty(sysLogEntity.getOperStaffId())){
                StaffEntity operStaffEntity = staffDao.getById(sysLogEntity.getOperStaffId());
                if(operStaffEntity!=null){
                    sysLogEntity.setOperStaffName(operStaffEntity.getCustname());
                }else {
                    sysLogEntity.setOperStaffName("-");
                }
            }else{
                sysLogEntity.setOperStaffName("-");
            }

            sysLogEntity.setContent(null);
            sysLogEntity.setLogText(null);
        }
        Long count = sysLogDao.count(query);
        Page<SysLogEntity> returnPage = new Page<>();
        returnPage.setContent(sysLogList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    public Page<WechatPayLog> findPayLog(SysLogQuery query, PageRequest pageRequest) {
        if(StringUtils.isEmpty(query.getName())){
            query.setName(null);
        }
        if(StringUtils.isEmpty(query.getPhone())){
            query.setPhone(null);
        }
        query.setType("PAY");
        logger.info("  findPayLog  query2 = {}",query);

        List<SysLogEntity> sysLogList = sysLogDao.findPayLog(query,pageRequest);
        List<WechatPayLog> logList = new ArrayList<>();
        for (SysLogEntity sysLogEntity:sysLogList){
            WechatPayLog wechatPayLog = new WechatPayLog();
            String memberId = sysLogEntity.getId1();
            String transactionId = sysLogEntity.getId2();
            String text = sysLogEntity.getLogText();
            double rate = 0.006;
            JSONObject data = JSON.parseObject(text);
            double money = Double.parseDouble(data.getString("total_fee"))/100;
            double taxFee = money*rate;
            MemberEntity memberEntity = memberDao.getById(memberId);
            if(memberEntity!=null){
                wechatPayLog.setMemberId(memberId);
                wechatPayLog.setName(memberEntity.getName());
                wechatPayLog.setPhone(memberEntity.getPhone());
                StoreEntity storeEntity = storeDao.getById(memberEntity.getStoreId());
                wechatPayLog.setStoreId(storeEntity.getStoreId());
                wechatPayLog.setStoreName(storeEntity.getName());
            }else{
                wechatPayLog.setMemberId("");
                wechatPayLog.setName("");
                wechatPayLog.setPhone("");
                wechatPayLog.setStoreId("");
                wechatPayLog.setStoreName("");
            }
            wechatPayLog.setTransactionId(transactionId);
            wechatPayLog.setTaxRate("0.6%");
            wechatPayLog.setPayFee(ut.getDoubleString(money));
            wechatPayLog.setTaxFee(ut.getDoubleString(taxFee));
            wechatPayLog.setMoney(ut.getDoubleString(money-taxFee));
            wechatPayLog.setPayTime(ut.df_day.format(sysLogEntity.getCreated()));

            logList.add(wechatPayLog);
        }
        Long count = sysLogDao.countPayLog(query);
        Page<WechatPayLog> returnPage = new Page<>();
        returnPage.setContent(logList);
        returnPage.setPage(pageRequest.getPage());
        returnPage.setSize(pageRequest.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }


}


package com.training.dao;

import com.training.repository.*;
import com.training.entity.*;
import com.training.common.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * wxpay_audit_log 数据库操作类
 * Created by huai23 on 2019-01-13 20:31:38.
 */ 
@Service
public class WxpayAuditLogDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxpayAuditLogRepository wxpayAuditLogRepository;

    /**
     * 新增实体
     * @param wxpayAuditLog
     * Created by huai23 on 2019-01-13 20:31:38.
     */ 
    public int add(WxpayAuditLogEntity wxpayAuditLog){
        int n = wxpayAuditLogRepository.add(wxpayAuditLog);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2019-01-13 20:31:38.
     */ 
    public List<WxpayAuditLogEntity> find(WxpayAuditLogQuery query , PageRequest page){
        List<WxpayAuditLogEntity> wxpayAuditLogList = wxpayAuditLogRepository.find(query,page);
        return wxpayAuditLogList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-13 20:31:38.
     */ 
    public Long count(WxpayAuditLogQuery query){
        Long n = wxpayAuditLogRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-13 20:31:38.
     */
    public WxpayAuditLogEntity getById(String id){
        WxpayAuditLogEntity wxpayAuditLogDB = wxpayAuditLogRepository.getById(id);
        return wxpayAuditLogDB;
    }

    /**
     * 根据实体更新
     * @param wxpayAuditLog
     * Created by huai23 on 2019-01-13 20:31:38.
     */ 
    public int update(WxpayAuditLogEntity wxpayAuditLog){
        int n = wxpayAuditLogRepository.update(wxpayAuditLog);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-13 20:31:38.
     */ 
    public int delete(String id){
        int n = wxpayAuditLogRepository.delete(id);
        return n;
    }


}


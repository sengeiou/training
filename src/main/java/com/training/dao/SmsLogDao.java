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
 * sms_log 数据库操作类
 * Created by huai23 on 2018-10-24 08:29:13.
 */ 
@Service
public class SmsLogDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SmsLogRepository smsLogRepository;

    /**
     * 新增实体
     * @param smsLog
     * Created by huai23 on 2018-10-24 08:29:13.
     */ 
    public int add(SmsLogEntity smsLog){
        int n = smsLogRepository.add(smsLog);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-10-24 08:29:13.
     */ 
    public List<SmsLogEntity> find(SmsLogQuery query , PageRequest page){
        List<SmsLogEntity> smsLogList = smsLogRepository.find(query,page);
        return smsLogList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-10-24 08:29:13.
     */ 
    public Long count(SmsLogQuery query){
        Long n = smsLogRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-10-24 08:29:13.
     */ 
    public SmsLogEntity getById(String id){
        SmsLogEntity smsLogDB = smsLogRepository.getById(id);
        return smsLogDB;
    }

    /**
     * 根据实体更新
     * @param smsLog
     * Created by huai23 on 2018-10-24 08:29:13.
     */ 
    public int update(SmsLogEntity smsLog){
        int n = smsLogRepository.update(smsLog);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-10-24 08:29:13.
     */ 
    public int delete(String id){
        int n = smsLogRepository.delete(id);
        return n;
    }


}


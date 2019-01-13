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
 * wxpay_log 数据库操作类
 * Created by huai23 on 2019-01-13 20:31:57.
 */ 
@Service
public class WxpayLogDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxpayLogRepository wxpayLogRepository;

    /**
     * 新增实体
     * @param wxpayLog
     * Created by huai23 on 2019-01-13 20:31:57.
     */ 
    public int add(WxpayLogEntity wxpayLog){
        int n = wxpayLogRepository.add(wxpayLog);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2019-01-13 20:31:57.
     */ 
    public List<WxpayLogEntity> find(WxpayLogQuery query , PageRequest page){
        List<WxpayLogEntity> wxpayLogList = wxpayLogRepository.find(query,page);
        return wxpayLogList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-13 20:31:57.
     */ 
    public Long count(WxpayLogQuery query){
        Long n = wxpayLogRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-13 20:31:57.
     */ 
    public WxpayLogEntity getById(String id){
        WxpayLogEntity wxpayLogDB = wxpayLogRepository.getById(id);
        return wxpayLogDB;
    }

    /**
     * 根据实体更新
     * @param wxpayLog
     * Created by huai23 on 2019-01-13 20:31:57.
     */ 
    public int update(WxpayLogEntity wxpayLog){
        int n = wxpayLogRepository.update(wxpayLog);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-13 20:31:57.
     */ 
    public int delete(String id){
        int n = wxpayLogRepository.delete(id);
        return n;
    }


}


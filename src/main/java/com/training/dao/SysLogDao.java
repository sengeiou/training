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
 * sys_log 数据库操作类
 * Created by huai23 on 2018-06-03 15:57:51.
 */ 
@Service
public class SysLogDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysLogRepository sysLogRepository;

    /**
     * 新增实体
     * @param sysLog
     * Created by huai23 on 2018-06-03 15:57:51.
     */ 
    public int add(SysLogEntity sysLog){
        int n = sysLogRepository.add(sysLog);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-03 15:57:51.
     */ 
    public List<SysLogEntity> find(SysLogQuery query , PageRequest page){
        List<SysLogEntity> sysLogList = sysLogRepository.find(query,page);
        return sysLogList;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-03 15:57:51.
     */
    public List<SysLogEntity> findDelayLog(SysLogQuery query , PageRequest page){
        List<SysLogEntity> sysLogList = sysLogRepository.findDelayLog(query,page);
        return sysLogList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-03 15:57:51.
     */ 
    public Long count(SysLogQuery query){
        Long n = sysLogRepository.count(query);
        return n;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-03 15:57:51.
     */
    public Long countDelayLog(SysLogQuery query){
        Long n = sysLogRepository.countDelayLog(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-03 15:57:51.
     */ 
    public SysLogEntity getById(String id){
        SysLogEntity sysLogDB = sysLogRepository.getById(id);
        return sysLogDB;
    }

    /**
     * 根据实体更新
     * @param sysLog
     * Created by huai23 on 2018-06-03 15:57:51.
     */ 
    public int update(SysLogEntity sysLog){
        int n = sysLogRepository.update(sysLog);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-03 15:57:51.
     */ 
    public int delete(String id){
        int n = sysLogRepository.delete(id);
        return n;
    }


    public List<SysLogEntity> findPayLog(SysLogQuery query, PageRequest page) {
        List<SysLogEntity> sysLogList = sysLogRepository.findPayLog(query,page);
        return sysLogList;
    }

    public Long countPayLog(SysLogQuery query) {
        Long n = sysLogRepository.countPayLog(query);
        return n;
    }
}


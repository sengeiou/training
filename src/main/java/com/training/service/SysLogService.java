package com.training.service;

import com.training.dao.*;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.training.util.ResponseUtil;
import com.training.util.RequestContextHelper;

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


}


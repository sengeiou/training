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
 * sms_log 核心业务操作类
 * Created by huai23 on 2018-10-24 08:29:13.
 */ 
@Service
public class SmsLogService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SmsLogDao smsLogDao;

    /**
     * 新增实体
     * @param smsLog
     * Created by huai23 on 2018-10-24 08:29:13.
     */ 
    public ResponseEntity<String> add(SmsLogEntity smsLog){
        User user = RequestContextHelper.getUser();
        int n = smsLogDao.add(smsLog);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-10-24 08:29:13.
     */ 
    public Page<SmsLogEntity> find(SmsLogQuery query , PageRequest page){
        List<SmsLogEntity> smsLogList = smsLogDao.find(query,page);
        Long count = smsLogDao.count(query);
        Page<SmsLogEntity> returnPage = new Page<>();
        returnPage.setContent(smsLogList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-10-24 08:29:13.
     */ 
    public Long count(SmsLogQuery query){
        Long count = smsLogDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-10-24 08:29:13.
     */ 
    public SmsLogEntity getById(String id){
        SmsLogEntity smsLogDB = smsLogDao.getById(id);
        return smsLogDB;
    }

    /**
     * 根据实体更新
     * @param smsLog
     * Created by huai23 on 2018-10-24 08:29:13.
     */ 
    public  ResponseEntity<String> update(SmsLogEntity smsLog){
        int n = smsLogDao.update(smsLog);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-10-24 08:29:13.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = smsLogDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


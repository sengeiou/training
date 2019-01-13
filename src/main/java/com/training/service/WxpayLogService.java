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
 * wxpay_log 核心业务操作类
 * Created by huai23 on 2019-01-13 20:31:57.
 */ 
@Service
public class WxpayLogService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxpayLogDao wxpayLogDao;

    /**
     * 新增实体
     * @param wxpayLog
     * Created by huai23 on 2019-01-13 20:31:57.
     */ 
    public ResponseEntity<String> add(WxpayLogEntity wxpayLog){
        User user = RequestContextHelper.getUser();
        int n = wxpayLogDao.add(wxpayLog);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2019-01-13 20:31:57.
     */ 
    public Page<WxpayLogEntity> find(WxpayLogQuery query , PageRequest page){
        List<WxpayLogEntity> wxpayLogList = wxpayLogDao.find(query,page);
        Long count = wxpayLogDao.count(query);
        Page<WxpayLogEntity> returnPage = new Page<>();
        returnPage.setContent(wxpayLogList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-13 20:31:57.
     */ 
    public Long count(WxpayLogQuery query){
        Long count = wxpayLogDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-13 20:31:57.
     */ 
    public WxpayLogEntity getById(String id){
        WxpayLogEntity wxpayLogDB = wxpayLogDao.getById(id);
        return wxpayLogDB;
    }

    /**
     * 根据实体更新
     * @param wxpayLog
     * Created by huai23 on 2019-01-13 20:31:57.
     */ 
    public  ResponseEntity<String> update(WxpayLogEntity wxpayLog){
        int n = wxpayLogDao.update(wxpayLog);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-13 20:31:57.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = wxpayLogDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


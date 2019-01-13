package com.training.service;

import com.training.dao.*;
import com.training.domain.Staff;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import com.training.util.ut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.training.util.ResponseUtil;
import com.training.util.RequestContextHelper;

import java.util.List;

/**
 * wxpay_audit_log 核心业务操作类
 * Created by huai23 on 2019-01-13 20:31:38.
 */ 
@Service
public class WxpayAuditLogService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxpayAuditLogDao wxpayAuditLogDao;

    /**
     * 审核
     * @param wxpayAuditLog
     * Created by huai23 on 2019-01-13 20:31:38.
     */ 
    public ResponseEntity<String> audit(WxpayAuditLogEntity wxpayAuditLog){
        Staff staff = RequestContextHelper.getStaff();
        wxpayAuditLog.setType(AuditTypeEnum.SUCCESS.getKey());
        wxpayAuditLog.setAuditTime(ut.currentTime());
        int n = wxpayAuditLogDao.add(wxpayAuditLog);
        if(n==1){
            return ResponseUtil.success("审核成功");
        }
        return ResponseUtil.exception("审核失败");
    }

    /**
     * 取消审核
     * @param wxpayAuditLog
     * Created by huai23 on 2019-01-13 20:31:38.
     */
    public ResponseEntity<String> cancel(WxpayAuditLogEntity wxpayAuditLog){
        Staff staff = RequestContextHelper.getStaff();
        wxpayAuditLog.setType(AuditTypeEnum.CANCEL.getKey());
        wxpayAuditLog.setAuditTime(ut.currentTime());
        int n = wxpayAuditLogDao.add(wxpayAuditLog);
        if(n==1){
            return ResponseUtil.success("取消审核成功");
        }
        return ResponseUtil.exception("取消审核失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2019-01-13 20:31:38.
     */ 
    public Page<WxpayAuditLogEntity> find(WxpayAuditLogQuery query , PageRequest page){
        List<WxpayAuditLogEntity> wxpayAuditLogList = wxpayAuditLogDao.find(query,page);
        Long count = wxpayAuditLogDao.count(query);
        Page<WxpayAuditLogEntity> returnPage = new Page<>();
        returnPage.setContent(wxpayAuditLogList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-13 20:31:38.
     */ 
    public Long count(WxpayAuditLogQuery query){
        Long count = wxpayAuditLogDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-13 20:31:38.
     */ 
    public WxpayAuditLogEntity getById(String id){
        WxpayAuditLogEntity wxpayAuditLogDB = wxpayAuditLogDao.getById(id);
        return wxpayAuditLogDB;
    }

    /**
     * 根据实体更新
     * @param wxpayAuditLog
     * Created by huai23 on 2019-01-13 20:31:38.
     */ 
    public  ResponseEntity<String> update(WxpayAuditLogEntity wxpayAuditLog){
        int n = wxpayAuditLogDao.update(wxpayAuditLog);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-13 20:31:38.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = wxpayAuditLogDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


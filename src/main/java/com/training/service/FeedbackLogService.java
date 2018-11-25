package com.training.service;

import com.training.dao.*;
import com.training.domain.Member;
import com.training.domain.Staff;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import com.training.util.IDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.training.util.ResponseUtil;
import com.training.util.RequestContextHelper;

import java.util.List;

/**
 * feedback_log 核心业务操作类
 * Created by huai23 on 2018-09-26 00:21:14.
 */ 
@Service
public class FeedbackLogService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FeedbackLogDao feedbackLogDao;

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private MemberDao memberDao;

    /**
     * 新增实体
     * @param feedbackLog
     * Created by huai23 on 2018-09-26 00:21:14.
     */ 
    public ResponseEntity<String> add(FeedbackLogEntity feedbackLog){
        feedbackLog.setLogId(IDUtils.getId());
        FeedbackEntity feedbackDB = feedbackDao.getById(feedbackLog.getFeedbackId());
        feedbackLog.setMemberId(feedbackDB.getMemberId());
        Staff staff = RequestContextHelper.getStaff();
        feedbackLog.setStaffId(staff.getStaffId());
        int n = feedbackLogDao.add(feedbackLog);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-09-26 00:21:14.
     */ 
    public Page<FeedbackLogEntity> find(FeedbackLogQuery query , PageRequest page){
        List<FeedbackLogEntity> feedbackLogList = feedbackLogDao.find(query,page);
        Long count = feedbackLogDao.count(query);
        Page<FeedbackLogEntity> returnPage = new Page<>();
        returnPage.setContent(feedbackLogList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-09-26 00:21:14.
     */ 
    public Long count(FeedbackLogQuery query){
        Long count = feedbackLogDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-09-26 00:21:14.
     */ 
    public FeedbackLogEntity getById(String id){
        FeedbackLogEntity feedbackLogDB = feedbackLogDao.getById(id);
        return feedbackLogDB;
    }

    /**
     * 根据实体更新
     * @param feedbackLog
     * Created by huai23 on 2018-09-26 00:21:14.
     */ 
    public  ResponseEntity<String> update(FeedbackLogEntity feedbackLog){
        int n = feedbackLogDao.update(feedbackLog);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-09-26 00:21:14.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = feedbackLogDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}


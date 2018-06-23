package com.training.service;

import com.training.dao.*;
import com.training.domain.Member;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import com.training.util.IDUtils;
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
 * feedback 核心业务操作类
 * Created by huai23 on 2018-05-26 13:54:54.
 */ 
@Service
public class FeedbackService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private MemberDao memberDao;

    /**
     * 新增实体
     * @param feedback
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    public ResponseEntity<String> add(FeedbackEntity feedback){
        feedback.setFeedbackId(IDUtils.getId());
        int n = feedbackDao.add(feedback);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 新增实体
     * @param feedback
     * Created by huai23 on 2018-05-26 13:54:54.
     */
    public ResponseEntity<String> changeCoach(FeedbackEntity feedback){
        Member member = RequestContextHelper.getMember();
        MemberEntity memberEntity = memberDao.getById(member.getMemberId());
        feedback.setFeedbackId(IDUtils.getId());
        feedback.setType("change_coach");
        feedback.setTitle("更换教练申请");
        feedback.setContent(memberEntity.getName()+"于"+ ut.currentDate()+"请求更换教练");
        int n = feedbackDao.add(feedback);
        if(n==1){
            return ResponseUtil.success("您的请求已经收到，稍后会有客服与您联系");
        }
        return ResponseUtil.exception("请求失败，请稍后再试");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    public Page<FeedbackEntity> find(FeedbackQuery query , PageRequest page){
        List<FeedbackEntity> feedbackList = feedbackDao.find(query,page);
        Long count = feedbackDao.count(query);
        Page<FeedbackEntity> returnPage = new Page<>();
        returnPage.setContent(feedbackList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    public Long count(FeedbackQuery query){
        Long count = feedbackDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    public FeedbackEntity getById(String id){
        FeedbackEntity feedbackDB = feedbackDao.getById(id);
        return feedbackDB;
    }

    /**
     * 根据实体更新
     * @param feedback
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    public  ResponseEntity<String> update(FeedbackEntity feedback){
        int n = feedbackDao.update(feedback);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = feedbackDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

